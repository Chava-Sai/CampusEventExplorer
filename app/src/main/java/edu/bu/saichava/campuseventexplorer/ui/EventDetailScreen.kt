package edu.bu.saichava.campuseventexplorer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bu.saichava.campuseventexplorer.R
import edu.bu.saichava.campuseventexplorer.data.CampusEvent
import edu.bu.saichava.campuseventexplorer.data.EventRepository
import edu.bu.saichava.campuseventexplorer.ui.theme.CampusEventExplorerTheme

/**
 * The event detail destination.
 *
 * [event] is nullable on purpose: the destination is reached with an id, and an id that matches
 * nothing — a stale link, or a deep link typed by hand — must show a recoverable message instead
 * of crashing. Like the list screen, this composable knows nothing about navigation.
 *
 * @param event the event the route's id resolved to, or `null` if it resolved to nothing.
 * @param onBack invoked by the app-bar Up button and by the recovery button.
 * @param onShare invoked by the Share event button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    event: CampusEvent?,
    onBack: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = event?.title ?: stringResource(R.string.detail_title_fallback),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.up_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (event == null) {
            EventUnavailable(
                onBack = onBack,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            EventDetailContent(
                event = event,
                onShare = onShare,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun EventDetailContent(
    event: CampusEvent,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = event.category,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

        HorizontalDivider()

        DetailField(
            label = stringResource(R.string.label_when),
            value = event.time
        )
        DetailField(
            label = stringResource(R.string.label_where),
            value = event.location
        )

        HorizontalDivider()

        DetailField(
            label = stringResource(R.string.label_about),
            value = event.description
        )

        FilledTonalButton(
            onClick = onShare,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.share_event))
        }
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

/** Shown when the route carried an id that no event in the collection matches. */
@Composable
private fun EventUnavailable(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.event_unavailable),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(R.string.event_unavailable_body),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(stringResource(R.string.return_to_events))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EventDetailScreenPreview() {
    CampusEventExplorerTheme {
        EventDetailScreen(
            event = EventRepository.events.first(),
            onBack = {},
            onShare = {}
        )
    }
}

@Preview(showBackground = true, name = "Unavailable")
@Composable
private fun EventUnavailablePreview() {
    CampusEventExplorerTheme {
        EventDetailScreen(
            event = null,
            onBack = {},
            onShare = {}
        )
    }
}
