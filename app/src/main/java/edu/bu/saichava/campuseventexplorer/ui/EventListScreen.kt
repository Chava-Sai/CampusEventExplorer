package edu.bu.saichava.campuseventexplorer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bu.saichava.campuseventexplorer.R
import edu.bu.saichava.campuseventexplorer.data.CampusEvent
import edu.bu.saichava.campuseventexplorer.data.EventRepository
import edu.bu.saichava.campuseventexplorer.ui.theme.CampusEventExplorerTheme

/** Identifies the search field to UI tests. */
const val SearchFieldTestTag: String = "searchField"

/**
 * The event list destination.
 *
 * This composable is deliberately free of navigation types: it receives the data to show and
 * reports what the user did through [onEventSelected] and [onQueryChange]. The caller decides
 * what those gestures mean.
 *
 * @param events every event in the application's collection.
 * @param query the current search text, owned by the caller.
 * @param onQueryChange invoked with the new text as the user types.
 * @param onEventSelected invoked with the id of the event the user tapped.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    events: List<CampusEvent>,
    query: String,
    onQueryChange: (String) -> Unit,
    onEventSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    // The visible list is derived from the search text rather than stored alongside it, so there
    // is exactly one source of truth and the two can never disagree.
    val visibleEvents = remember(events, query) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            events
        } else {
            events.filter { event ->
                event.title.contains(trimmed, ignoreCase = true) ||
                    event.category.contains(trimmed, ignoreCase = true)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.list_title)) })
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                label = { Text(stringResource(R.string.search_label)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag(SearchFieldTestTag)
            )

            if (visibleEvents.isEmpty()) {
                EmptyResults(query = query)
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // The event id is the stable key, so Compose can track an item across
                    // filtering instead of re-using state by position.
                    items(items = visibleEvents, key = { event -> event.id }) { event ->
                        EventRow(
                            event = event,
                            onClick = { onEventSelected(event.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EventRow(
    event: CampusEvent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = event.category,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = event.time,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EmptyResults(query: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_matches, query.trim()),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EventListScreenPreview() {
    CampusEventExplorerTheme {
        EventListScreen(
            events = EventRepository.events,
            query = "",
            onQueryChange = {},
            onEventSelected = {}
        )
    }
}
