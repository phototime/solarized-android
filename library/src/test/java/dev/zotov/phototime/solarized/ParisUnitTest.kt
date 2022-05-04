package dev.zotov.phototime.solarized

import org.junit.*
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

class ParisUnitTest {
    private val date = Instant.ofEpochSecond(1651680967).atZone(ZoneOffset.UTC).toLocalDateTime()
    private val latitude = 48.85881
    private val longitude = 2.32003
    private val solarized = Solarized(latitude, longitude, date)


    @Test
    fun `Morning blue hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"))
        solarized.blueHour.morning!!.assertTime("05:36-06:04")
    }

    @Test
    fun sunrise() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"))
        solarized.sunrise!!.assertTime("06:25")
    }

    @Test
    fun `Morning golden hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"))
        solarized.goldenHour.morning!!.assertTime("06:04-07:10")
    }

    @Test
    fun day() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"))
        solarized.day!!.assertTime("07:10-20:25")
    }

    @Test
    fun `Evening golden hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"))
        solarized.goldenHour.evening!!.assertTime("20:25-21:31")
    }

    @Test
    fun sunset() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"))
        solarized.sunset!!.assertTime("21:08")
    }

    @Test
    fun `Evening blue hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("EET"))
        solarized.blueHour.evening!!.assertTime("21:31-21:59")
    }
}