package dev.zotov.phototime.solarized

import androidx.annotation.FloatRange

/**
 * @param latitude the latitude at which the device is located
 * @param longitude the longitude at which the device is located
 */
class Solarized(
    @FloatRange(from = 0.0, to = 90.0) val latitude: Double,
    @FloatRange(from = 0.0, to = 180.0) val longitude: Double
) {

}