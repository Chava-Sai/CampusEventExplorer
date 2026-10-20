package edu.bu.saichava.campuseventexplorer.navigation

import kotlinx.serialization.Serializable

/**
 * The type-safe destinations of the application.
 *
 * Each route is a Kotlin type rather than a hand-written string, so the compiler checks that a
 * destination is navigated to with exactly the arguments it declares. Routes are serialized by
 * Navigation Compose, which is why they are annotated [Serializable].
 */

/** The start destination: the searchable list of campus events. */
@Serializable
data object EventList

/**
 * The detail destination.
 *
 * It carries only the identity of the event, never the [edu.bu.saichava.campuseventexplorer.data.CampusEvent]
 * itself. The destination re-reads the event from the application's event collection using this id.
 */
@Serializable
data class EventDetail(
    val eventId: Long
)
