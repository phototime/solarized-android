package dev.zotov.phototime.solarized

import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

class PermUnitTest  {
    private val date = Instant.ofEpochSecond(1651679484).atZone(ZoneOffset.UTC).toLocalDateTime()
    private val latitude = 58.000000
    private val longitude = 56.316666
    private val timezone = TimeZone.getTimeZone("Asia/Yekaterinburg")
    private val solarized = Solarized(latitude, longitude, date, timezone)

    @Test
    fun `Morning blue hour`() {
        solarized.blueHour.morning!!.assertTime("04:06-04:46")
    }

    @Test
    fun sunrise() {
        solarized.sunrise!!.assertTime("05:16")
    }

    @Test
    fun `Morning golden hour`() {
        solarized.goldenHour.morning!!.assertTime("04:46-06:13")
    }

    @Test
    fun day() {
        solarized.day!!.assertTime("06:13-20:10")
    }

    @Test
    fun `Evening golden hour`() {
        solarized.goldenHour.evening!!.assertTime("20:10-21:37")
    }

    @Test
    fun sunset() {
        solarized.sunset!!.assertTime("21:05")
    }

    @Test
    fun `Evening blue hour`() {
        solarized.blueHour.evening!!.assertTime("21:37-22:18")
    }
}

class PermUnitTest2  {
    private val date = Instant.ofEpochSecond(1652011132).atZone(ZoneOffset.UTC).toLocalDateTime()
    private val latitude = 58.0368
    private val longitude = 56.2632
    private val timezone = TimeZone.getTimeZone("Asia/Yekaterinburg")
    private val solarized = Solarized(latitude, longitude, date, timezone)

    @Test
    fun `list is not null`() {
        Assert.assertNotNull(solarized.blueHour.morning)
        Assert.assertNotNull(solarized.goldenHour.morning)
        Assert.assertNotNull(solarized.sunrise)
        Assert.assertNotNull(solarized.day)
        Assert.assertNotNull(solarized.goldenHour.evening)
        Assert.assertNotNull(solarized.blueHour.evening)
        Assert.assertNotNull(solarized.sunset)
        Assert.assertNotNull(solarized.list)
    }
}