package dev.zotov.phototime.solarized

import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class SolarizedUnitTest {

    private val date = Instant.ofEpochSecond(1549918775).atZone(ZoneOffset.UTC).toLocalDateTime()
    private val latitude = 47.49801
    private val longitude = 19.03991
    private val timeZone = TimeZone.getTimeZone("CEST")
    private val solarized = Solarized(latitude, longitude, date, timeZone)

    private fun getDate(epochSeconds: Long): LocalDateTime {
        return Instant.ofEpochSecond(epochSeconds).atZone(ZoneOffset.UTC).toLocalDateTime()
    }

    @Test
    fun testList() {
        val expected = SunPhaseList(
            firstLight = SunPhase.FirstLight(date = getDate(1549859430)),
            morningBlueHour = SunPhase.BlueHour(
                start = getDate(1549861982),
                end = getDate(1549863414),
            ),
            sunrise = SunPhase.Sunrise(date = getDate(1549864601)),
            morningGoldenHour = SunPhase.GoldenHour(
                start = getDate(1549863414),
                end = getDate(1549867266)
            ),
            day = SunPhase.Day(
                start = getDate(1549867266),
                end = getDate(1549898164),
            ),
            eveningGoldenHour = SunPhase.GoldenHour(
                start = getDate(1549898164),
                end = getDate(1549902009),
            ),
            sunset = SunPhase.Sunset(date = getDate(1549900731)),
            eveningBlueHour = SunPhase.BlueHour(
                start = getDate(1549902009),
                end = getDate(1549903439),
            ),
            lastLight = SunPhase.LastLight(date = getDate(1549859430)),
        )

        Assert.assertEquals(expected, solarized.list)
    }
}