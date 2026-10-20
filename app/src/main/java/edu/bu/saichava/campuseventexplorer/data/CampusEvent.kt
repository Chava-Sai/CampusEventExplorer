package edu.bu.saichava.campuseventexplorer.data

/**
 * A single campus event.
 *
 * [id] is the stable identity of an event: it is what the LazyColumn keys list items on and
 * what travels through navigation, so it must be unique and must never change for a given event.
 */
data class CampusEvent(
    val id: Long,
    val title: String,
    val category: String,
    val location: String,
    val time: String,
    val description: String
)
