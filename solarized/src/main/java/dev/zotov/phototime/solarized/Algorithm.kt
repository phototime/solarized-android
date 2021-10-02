package dev.zotov.phototime.solarized

import androidx.annotation.FloatRange
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import kotlin.math.*

/**
 * The core of the entire library.
 * Used to calculate everything using the power of math
 *
 * The algorithm is based on the Paul Schlacter algorithm
 * More info can be found [here](http://www.stjarnhimlen.se/comp/riset.html)
 *
 */
//internal class Algorithm(
//    @FloatRange(from = 0.0, to = 90.0) val latitude: Double,
//    @FloatRange(from = 0.0, to = 180.0) val longitude: Double,
//    val date: Date,
//    val twilight: Twilight,
//    val dateTime: DateTime,
//) {
//    /** a day number since 0 jan 2000, 0:00 UT */
//    private val d: Int
//
//    /** Sidereal Time at Greenwich at 00:00 Universal Time  */
//    private val gst0: Double
//
//    /** Local Sidereal Time */
//    private val localSiderealTime: Double
//
//    /** Right Ascension of The Sun */
//    private val sunRA: Double
//
//    /** Declination of The Sun */
//    private val sunDec: Double
//
//    /** radius of The Sun */
//    private val sunR: Double
//
//    init {
//        d = calculateD()
//
//        // Calculate gst0
//        gst0 = ((180.0 + 356.047_0 + 282.940_4) + (0.985_600_258_5 + 4.70935e-5) * d).reduceToAngle()
//
//        // Calculate siderealTime
//        localSiderealTime = (gst0 + 180 + longitude).reduceToAngle()
//        println("localSiderealTime = $localSiderealTime")
//
//        // Calculate lon and sunR
//        val E = M + e.degrees * sin(M.radians) * (1 + e * cos(M.radians))
//        val x = cos(E.radians) - e
//        val y = sqrt(1 - e * e) * sin(E.radians)
//        sunR = sqrt(x * x + y * y)
//        println("sunR = $sunR")
//        val v = atan2(y, x).degrees
//        var lon = v + w
//        if (lon >= 360) lon -= 360
//
//        // Calculate Right Ascension of The Sun
//        val xs = sunR * cos(lon.radians)
//        val ys = sunR * sin(lon.radians)
//        val xe = xs
//        val ye = ys * cos(oblEcl.radians)
//        val ze = ys * sin(oblEcl.radians)
//        sunRA = atan2(ye, xe).degrees
//        sunDec = atan2(ze, sqrt(xe * xe + ye * ye)).degrees
//        println("sunRA = $sunRA")
//        println("sunDec = $sunDec")
//    }
//
//    private fun calculateD(): Int {
//        val calendar = Calendar.getInstance()
//        calendar.timeZone = TimeZone.getTimeZone("UTC")
//        calendar.time = date
//        val y = calendar.get(Calendar.YEAR)
//        val m = calendar.get(Calendar.MONTH)
//        val D = calendar.get(Calendar.DATE)
//
//        return 367 * y - ((7 * (y + ((m + 9) / 12))) / 4) + (275 * m / 9) + D - 730_530
//    }
//
//    private val e: Double
//        get() = 0.016_709 - 1.151e-9 * d
//
//    /** longitude of perihelion (degrees) */
//    private val w: Double
//        get() = 282.9404 + 4.70935E-5 * d
//
//    /** mean anomaly (degrees) */
//    private val M: Double
//        get() = (356.0470 + 0.9856002585 * d).reduceToAngle()
//
//    private val oblEcl: Double
//        get() = 23.439_3 - 3.563E-7 * d
//
//
//    fun calculate(): Date? {
//        val tSouth = 12 - (localSiderealTime - sunRA).reduceTo180Angle() / 15
//        val sRadius = 0.266_6 / sunR
//
//        var alt = twilight.degrees
//        if (twilight == Twilight.Official) alt -= sRadius
//
//        println(alt)
//        println(latitude)
//        println(sunDec)
//        val cost = (sin(alt.radians) - sin(latitude.radians) * sin(sunDec.radians)) / (cos(latitude.radians) * cos(sunDec.radians))
//        if (cost < 1 && cost > -1) {
//            val t = acos(cost).degrees / 15
//            val value = if (dateTime == DateTime.Morning) tSouth - t else tSouth + t
//            val calendar = Calendar.getInstance()
//            calendar.time = date
//            calendar.add(Calendar.SECOND, (value * 3_600).toInt())
//            return calendar.time
////            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusSeconds(().toLong()).
//        }
//
//        return null
//    }
//}

internal object Algorithm {
    fun calculate(
        time: DateTime,
        date: LocalDateTime,
        latitude: Double,
        longitude: Double,
        twilight: Twilight
    ): LocalDateTime? {
        val zenith = -1 * twilight.degrees + 90
        val day = date.atZone(ZoneOffset.UTC).dayOfYear
        println(day)

        // longitude to hour value and calculate an approx. time
        val lngHour = longitude / 15
        val hourTime = if (time == DateTime.Morning) 6.0 else 18.0
        val t = day + (hourTime - lngHour) / 24

        // Calculate the suns mean anomaly
        val M = (0.9856 * t) - 3.289

        // Calculate the sun's true longitude
        val subexpression1 = 1.916 * sin(M.radians)
        val subexpression2 = 0.020 * sin(2 * M.radians)
        var L = M + subexpression1 + subexpression2 + 282.634
        L = L.normalise(360.0)

        // sun's right ascension
        var RA = atan(0.91764 * tan(L.radians)).degrees
        RA = RA.normalise(360.0)

        // RA value needs to be in the same quadrant as L
        val Lquadrant = floor(L / 90) * 90
        val RAquadrant = floor(RA / 90) * 90
        RA += (Lquadrant - RAquadrant)
        // RA into hours
        RA /= 15

        // declination
        val sinDec = 0.39782 * sin(L.radians)
        val cosDec = cos(asin(sinDec))

        // local hour angle
        val cosH = (cos(zenith.radians) - (sinDec * sin(latitude.radians))) / (cosDec * cos(latitude.radians))

        // no transition
        println(cosH)
        if (cosH > 1 || cosH < -1) return null

        val tempH = if (time == DateTime.Morning) 360 - acos(cosH).degrees else acos(cosH).degrees
        val H = tempH / 15.0

        // local mean time of rising
        val T = H + RA - (0.06571 * t) - 6.622

        val UT = (T - lngHour).normalise(24.0)

        val hour = floor(UT).toInt()
        val minute = floor((UT - hour) * 60.0).toInt()
        val second = ((((UT - hour) * 60) - minute) * 60.0).toInt()
        val shouldBeYesterday = lngHour > 0 && UT > 12 && time == DateTime.Morning
        val shouldBeTomorrow = lngHour < 0 && UT < 12 && time == DateTime.Evening
        val setDate = when {
            shouldBeYesterday -> date.minusDays(1)
            shouldBeTomorrow -> date.plusDays(1)
            else -> date
        }

        return setDate.withHour(hour).withMinute(minute).withSecond(second)
    }
}


/**
 * Enum used to refine the query and determine for what time calculations are required
 * Calculations such as golden hour, sunrise/sunset, blue hour occur twice a day: in the morning and in the evening
 */
internal enum class DateTime {
    Morning,
    Evening,
}

internal fun Double.reduceToAngle(): Double = this - 360 * floor(this / 360)

internal fun Double.reduceTo180Angle(): Double {
    val value = this / 360 + 1 / 2
    return this - 360 * floor(value)
}

internal val Double.radians: Double get() = this * PI / 180

internal val Double.degrees: Double get() = this * 180 / PI

internal fun Double.normalise(maximum: Double): Double {
    var value = this
    while (value < 0) value += maximum
    while (value > maximum) value -= maximum
    return value
}

