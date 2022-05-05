package dev.zotov.phototime.solarized

import org.junit.*
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

class ParisUnitTest {
    private val date = Instant.ofEpochSecond(1651680967).atZone(ZoneOffset.UTC).toLocalDateTime()
    private val latitude = 48.85881
    private val longitude = 2.32003
    private val timeZone = TimeZone.getTimeZone("Europe/Paris")
    private val solarized = Solarized(latitude, longitude, date, timeZone)

    @Test
    fun `Morning blue hour`() {
        solarized.blueHour.morning!!.assertTime("05:36-06:04")
    }

    @Test
    fun sunrise() {
        solarized.sunrise!!.assertTime("06:25")
    }

    @Test
    fun `Morning golden hour`() {
        solarized.goldenHour.morning!!.assertTime("06:04-07:10")
    }

    @Test
    fun day() {
        solarized.day!!.assertTime("07:10-20:25")
    }

    @Test
    fun `Evening golden hour`() {
        solarized.goldenHour.evening!!.assertTime("20:25-21:31")
    }

    @Test
    fun sunset() {
        solarized.sunset!!.assertTime("21:08")
    }

    @Test
    fun `Evening blue hour`() {
        solarized.blueHour.evening!!.assertTime("21:31-21:59")
    }
}