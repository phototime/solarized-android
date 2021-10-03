package dev.zotov.phototime.solarized

import org.junit.Test
import org.junit.Assert.*
import java.time.Instant
import java.time.ZoneOffset

class AlgorithmUnitTest {

    private fun test(time: DateTime, twilight: Twilight, expected: Long) {
        val date = Instant.ofEpochSecond(1549918775).atZone(ZoneOffset.UTC).toLocalDateTime()
        val expectedDate = Instant.ofEpochSecond(expected).atZone(ZoneOffset.UTC).toLocalDateTime()
        val latitude = 47.49801
        val longitude = 19.03991

        val actual = algorithm(
            time = time,
            twilight = twilight,
            date = date,
            latitude = latitude,
            longitude = longitude
        )
        assertEquals(expectedDate, actual)
    }

    @Test
    fun testOfficial() {
        test(DateTime.Morning, Twilight.Official, 1549864694)
        test(DateTime.Evening, Twilight.Official, 1549900731)
    }

    @Test
    fun testCivil() {
        test(DateTime.Morning, Twilight.Civil, 1549862676)
        test(DateTime.Evening, Twilight.Civil, 1549902746)
    }

    @Test
    fun testAstronomical() {
        test(DateTime.Morning, Twilight.Astronomical, 1549858363)
        test(DateTime.Evening, Twilight.Astronomical, 1549907056)
    }

    @Test
    fun testNautical() {
        test(DateTime.Morning, Twilight.Nautical, 1549860503)
        test(DateTime.Evening, Twilight.Nautical, 1549904917)
    }

    @Test
    fun testCustom() {
        test(DateTime.Morning, Twilight.Custom(-8.0), 1549861946)
        test(DateTime.Morning, Twilight.Custom(-4.0), 1549863414)
        test(DateTime.Morning, Twilight.Custom(6.0), 1549867266)

        test(DateTime.Evening, Twilight.Custom(6.0), 1549898164)
        test(DateTime.Evening, Twilight.Custom(-4.0), 1549902009)
        test(DateTime.Evening, Twilight.Custom(-8.0), 1549903475)
    }
}