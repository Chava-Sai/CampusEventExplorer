package edu.bu.saichava.campuseventexplorer.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import edu.bu.saichava.campuseventexplorer.R
import edu.bu.saichava.campuseventexplorer.data.CampusEvent
import edu.bu.saichava.campuseventexplorer.data.EventRepository
import edu.bu.saichava.campuseventexplorer.ui.EventDetailScreen
import edu.bu.saichava.campuseventexplorer.ui.EventListScreen

/**
 * Hosts the navigation graph and wires the screens to it.
 *
 * Every navigation decision is made here. The screens themselves receive plain data and plain
 * callbacks, which keeps them independent of Navigation Compose and previewable in isolation.
 */
@Composable
fun CampusEventApp(
    events: List<CampusEvent> = EventRepository.events,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val chooserTitle = stringResource(R.string.share_chooser_title)

    /**
     * Returns to the list. [androidx.navigation.NavController.navigateUp] covers the ordinary
     * back stack; the fallback covers a detail destination opened directly by a deep link, so
     * "Return to events" always has somewhere to go.
     */
    val returnToList: () -> Unit = {
        if (!navController.navigateUp()) {
            navController.navigate(EventList) {
                popUpTo<EventDetail> { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = EventList,
        modifier = modifier
    ) {
        composable<EventList> {
            // The search text lives on this destination's back stack entry. rememberSaveable
            // carries it through configuration changes, and Navigation's saveable state holder
            // restores it when the user comes back from the detail destination.
            var query by rememberSaveable { mutableStateOf("") }

            EventListScreen(
                events = events,
                query = query,
                onQueryChange = { newQuery -> query = newQuery },
                onEventSelected = { eventId ->
                    // Only the identity of the event crosses the navigation boundary.
                    navController.navigate(EventDetail(eventId = eventId)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<EventDetail>(
            // Lets the detail destination be opened directly, e.g.
            // adb shell am start -a android.intent.action.VIEW -d "campusevents://event/3"
            deepLinks = listOf(navDeepLink<EventDetail>(basePath = "campusevents://event"))
        ) { backStackEntry ->
            val route: EventDetail = backStackEntry.toRoute()

            // The event is looked up from the application's collection by id. An id that matches
            // nothing yields null, which the screen renders as a recoverable message.
            val event = events.firstOrNull { it.id == route.eventId }

            EventDetailScreen(
                event = event,
                onBack = returnToList,
                onShare = { event?.let { shareEvent(context, it, chooserTitle) } }
            )
        }
    }
}

/**
 * Launches the system share sheet with an implicit [Intent.ACTION_SEND].
 *
 * The intent names an action and a MIME type but no component, so Android resolves the set of
 * apps that can handle plain text and [Intent.createChooser] lets the user pick one.
 */
private fun shareEvent(context: Context, event: CampusEvent, chooserTitle: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, event.title)
        putExtra(
            Intent.EXTRA_TEXT,
            context.getString(
                R.string.share_text,
                event.title,
                event.time,
                event.location,
                event.description
            )
        )
    }
    context.startActivity(Intent.createChooser(sendIntent, chooserTitle))
}
