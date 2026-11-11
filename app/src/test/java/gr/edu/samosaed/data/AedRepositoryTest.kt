package gr.edu.samosaed.data

import org.junit.Assert.*
import org.junit.Test

class AedRepositoryTest {
    @Test fun allAreasIncludeEveryPointExactlyOnce() {
        val all = AedRepository.filterByArea(Area.ALL)
        val byArea = Area.entries.filter { it != Area.ALL }
            .flatMap { AedRepository.filterByArea(it) }
        assertEquals(all.map { it.id }.toSet(), byArea.map { it.id }.toSet())
        assertEquals(all.size, all.distinctBy { it.id }.size)
    }

    @Test fun regionWithoutEntriesReturnsEmptyList() {
        assertTrue(AedRepository.filterByArea(Area.MARATHOKAMPOS).isEmpty())
    }

    @Test fun detailLookupHandlesValidMissingAndNullIdentifiers() {
        val point = AedRepository.filterByArea(Area.ALL).first()
        assertEquals(point, AedRepository.findById(point.id))
        assertNull(AedRepository.findById("UNKNOWN"))
        assertNull(AedRepository.findById(null))
    }
}
