package dev.zotov.phototime.solarized

import org.junit.*
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

class NewYorkUnitTest {
    private val date = Instant.ofEpochSecond(1651680967).atZone(ZoneOffset.UTC).toLocalDateTime()
    private val latitude = 40.69307
    private val longitude = -73.87842
    private val solarized = Solarized(latitude, longitude, date)


    @Test
    fun `Morning blue hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Anguilla"))
        solarized.blueHour.morning!!.assertTime("05:09-05:32")
    }

    @Test
    fun sunrise() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Anguilla"))
        solarized.sunrise!!.assertTime("05:50")
    }

    @Test
    fun `Morning golden hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Anguilla"))
        solarized.goldenHour.morning!!.assertTime("05:32-06:28")
    }

    @Test
    fun day() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Anguilla"))
        solarized.day!!.assertTime("06:28-19:16")
    }

    @Test
    fun `Evening golden hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Anguilla"))
        solarized.goldenHour.evening!!.assertTime("19:16-20:12")
    }

    @Test
    fun sunset() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Anguilla"))
        solarized.sunset!!.assertTime("19:53")
    }

    @Test
    fun `Evening blue hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Anguilla"))
        solarized.blueHour.evening!!.assertTime("20:12-20:35")
    }
}