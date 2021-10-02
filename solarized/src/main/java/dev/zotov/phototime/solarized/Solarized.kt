package dev.zotov.phototime.solarized

import androidx.annotation.FloatRange
import java.time.LocalDateTime

/**
 * Main class that used to calculate sun phases like golden hour, blue hour, sunrise, sunset etc
 *
 * @param latitude the latitude at which the device is located
 * @param longitude the longitude at which the device is located
 * @param date the date that will be used in the calculation of phases
 */
class Solarized(
    @FloatRange(from = 0.0, to = 90.0) val latitude: Double,
    @FloatRange(from = 0.0, to = 180.0) val longitude: Double,
    val date: LocalDateTime
) {

    fun getMorning(): LocalDateTime? {
        return algorithm(
            latitude = latitude,
            longitude = longitude,
            time = DateTime.Morning,
            twilight = Twilight.Official,
            date = date
        )
    }
}