package dev.zotov.phototime.solarized

import androidx.annotation.FloatRange
import java.time.LocalDateTime
import java.util.*

/**
 * Main class that used to calculate sun phases like golden hour, blue hour, sunrise, sunset etc
 *
 * @param latitude the latitude at which the device is located
 * @param longitude the longitude at which the device is located
 * @param date the date that will be used in the calculation of phases
 * @param timeZone of LocalDateTime
 */
class Solarized(
    @FloatRange(from = -90.0, to = 90.0) val latitude: Double,
    @FloatRange(from = -180.0, to = 180.0) val longitude: Double,
    val date: LocalDateTime,
    val timeZone: TimeZone = TimeZone.getDefault()
) {

    @Suppress("unused")
    val list: SunPhaseList?
        get() {
            val blueHourMorningStartDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-7.9),
                timeZone = timeZone,
            ) ?: return null

            val blueHourMorningEndDate = algorithm(
                // and goldenHourMorningStartDate too
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
                timeZone = timeZone,
            ) ?: return null

            val goldenHourMorningEndDate = algorithm(
                // and dayStartDate too
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
                timeZone = timeZone,
            ) ?: return null

            val goldenHourEveningStartDate = algorithm(
                // and dayEndDate too
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
                timeZone = timeZone,
            ) ?: return null

            val goldenHourEveningEndDate = algorithm(
                // and blueHourEveningStartDate too
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
                timeZone = timeZone,
            ) ?: return null

            val blueHourEveningEndDate = algorithm(
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-7.9),
                timeZone = timeZone,
            ) ?: return null

            return SunPhaseList(
                firstLight = firstLight,
                morningBlueHour = SunPhase.BlueHour(
                    start = blueHourMorningStartDate,
                    end = blueHourMorningEndDate
                ),
                sunrise = sunrise ?: return null,
                morningGoldenHour = SunPhase.GoldenHour(
                    start = blueHourMorningEndDate,
                    end = goldenHourMorningEndDate
                ),
                day = SunPhase.Day(
                    start = goldenHourMorningEndDate,
                    end = goldenHourEveningStartDate,
                ),
                eveningGoldenHour = SunPhase.GoldenHour(
                    start = goldenHourEveningStartDate,
                    end = goldenHourEveningEndDate,
                ),
                sunset = sunset ?: return null,
                eveningBlueHour = SunPhase.BlueHour(
                    start = goldenHourEveningEndDate,
                    end = blueHourEveningEndDate,
                ),
                lastLight = lastLight,
            )
        }

    @Suppress("unused")
    /** [SunPhase.GoldenHour] daylight is redder and softer than when the sun is higher in the sky */
    val goldenHour = object : TwiceADaySunPhases<SunPhase.GoldenHour> {
        private fun base(time: DateTime): SunPhase.GoldenHour? {
            val goldenHourStartDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
                timeZone = timeZone,
            ) ?: return null
            val goldenHourEndDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
                timeZone = timeZone,
            ) ?: return null

            return if (time == DateTime.Evening)
                SunPhase.GoldenHour(goldenHourStartDate, goldenHourEndDate)
            else
                SunPhase.GoldenHour(goldenHourEndDate, goldenHourStartDate)
        }

        /** Morning golden hour */
        override val morning: SunPhase.GoldenHour?
            get() = base(DateTime.Morning)

        /** Morning golden hour */
        override val evening: SunPhase.GoldenHour?
            get() = base(DateTime.Evening)
    }

    @Suppress("unused")
    /** [SunPhase.BlueHour] when the Sun is at a significant depth below the horizon but the viewer can see light */
    val blueHour = object : TwiceADaySunPhases<SunPhase.BlueHour> {
        private fun base(time: DateTime): SunPhase.BlueHour? {
            val blueHourStartDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
                timeZone = timeZone,
            ) ?: return null
            val blueHourEndDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-7.9),
                timeZone = timeZone,
            ) ?: return null

            return if (time == DateTime.Evening)
                SunPhase.BlueHour(blueHourStartDate, blueHourEndDate)
            else
                SunPhase.BlueHour(blueHourEndDate, blueHourStartDate)
        }

        /** Morning golden hour */
        override val morning: SunPhase.BlueHour?
            get() = base(DateTime.Morning)

        /** Morning golden hour */
        override val evening: SunPhase.BlueHour?
            get() = base(DateTime.Evening)
    }


    @Suppress("unused")
    /** [SunPhase.FirstLight] when first light come to viewer */
    val firstLight: SunPhase.FirstLight?
        get() {
            val firstLightDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Astronomical,
                timeZone = timeZone,
            ) ?: return null
            return SunPhase.FirstLight(firstLightDate)
        }

    @Suppress("unused")
    /** [SunPhase.Sunrise] when the upper rim of the Sun appears on the horizon in the morning */
    val sunrise: SunPhase.Sunrise?
        get() {
            val sunriseDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-0.83),
                timeZone = timeZone,
            ) ?: return null
            return SunPhase.Sunrise(sunriseDate)
        }


    @Suppress("unused")
    /** [SunPhase.Day] no soft light, just usual day light */
    val day: SunPhase.Day?
        get() {
            val dayStartDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
                timeZone = timeZone,
            ) ?: return null
            val dayEndDate = algorithm(
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
                timeZone = timeZone,
            ) ?: return null
            return SunPhase.Day(dayStartDate, dayEndDate)
        }

    @Suppress("unused")
    /** [SunPhase.Sunset] when the Sun below the horizon */
    val sunset: SunPhase.Sunset?
        get() {
            val sunsetDate = algorithm(
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Official,
                timeZone = timeZone,
            ) ?: return null
            return SunPhase.Sunset(sunsetDate)
        }

    @Suppress("unused")
    /** [SunPhase.LastLight] When last light visible to viewer */
    val lastLight: SunPhase.LastLight?
        get() {
            val sunsetDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Astronomical,
                timeZone = timeZone,
            ) ?: return null
            return SunPhase.LastLight(sunsetDate)
        }
}

interface TwiceADaySunPhases<T : SunPhase> {
    val morning: T?
    val evening: T?
}