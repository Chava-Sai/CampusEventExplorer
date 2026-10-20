package edu.bu.saichava.campuseventexplorer

import edu.bu.saichava.campuseventexplorer.data.EventRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Guards the two invariants the rest of the app depends on: stable ids, and id-based lookup. */
class EventRepositoryTest {

    @Test
    fun repositoryHasAtLeastEightEvents() {
        assertTrue(EventRepository.events.size >= 8)
    }

    @Test
    fun everyEventIdIsUnique() {
        val ids = EventRepository.events.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun lookupByIdFindsTheMatchingEvent() {
        val expected = EventRepository.events.first()
        val found = EventRepository.events.firstOrNull { it.id == expected.id }
        assertEquals(expected, found)
    }

    @Test
    fun lookupByUnknownIdReturnsNull() {
        assertNull(EventRepository.events.firstOrNull { it.id == -1L })
    }

    @Test
    fun filteringMatchesTitleOrCategoryCaseInsensitively() {
        val query = "tech"
        val matches = EventRepository.events.filter {
            it.title.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
        }
        assertTrue(matches.isNotEmpty())
        assertTrue(matches.all { it.category.equals("Technology", ignoreCase = true) })
    }
}
