package dev.zotov.phototime.solarized

import org.junit.*
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

class TokyoUnitTest {
    private val date = Instant.ofEpochSecond(1651680967).atZone(ZoneOffset.UTC).toLocalDateTime()
    private val latitude = 35.6757
    private val longitude = 139.7714
    private val solarized = Solarized(latitude, longitude, date)


    @Test
    fun `Morning blue hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
        solarized.blueHour.morning!!.assertTime("04:08-04:29")
    }

    @Test
    fun sunrise() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
        solarized.sunrise!!.assertTime("04:46")
    }

    @Test
    fun `Morning golden hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
        solarized.goldenHour.morning!!.assertTime("04:29-05:21")
    }

    @Test
    fun day() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
        solarized.day!!.assertTime("05:21-17:54")
    }

    @Test
    fun `Evening golden hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
        solarized.goldenHour.evening!!.assertTime("17:54-18:46")
    }

    @Test
    fun sunset() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
        solarized.sunset!!.assertTime("18:28")
    }

    @Test
    fun `Evening blue hour`() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
        solarized.blueHour.evening!!.assertTime("18:46-19:07")
    }
}