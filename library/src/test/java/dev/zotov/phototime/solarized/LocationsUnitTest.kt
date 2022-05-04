package dev.zotov.phototime.solarized

import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocationsUnitTest {

    @Test
    fun `Test Perm city`() {
        val date = Instant.ofEpochSecond(1651657703).atZone(ZoneOffset.UTC).toLocalDateTime()
        val solarized = Solarized(58.0149, 56.2467, date)
        Assert.assertNotNull(solarized.list)
    }
}