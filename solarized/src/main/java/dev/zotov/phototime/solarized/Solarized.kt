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

    val list: SunPhaseList?
        get() {
            val blueHourMorningStartDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-6.0),
            ) ?: return null

            val blueHourMorningEndDate = algorithm(
                // and goldenHourMorningStartDate too
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
            ) ?: return null

            val goldenHourMorningEndDate = algorithm(
                // and dayStartDate too
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
            ) ?: return null

            val goldenHourEveningStartDate = algorithm(
                // and dayEndDate too
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
            ) ?: return null

            val goldenHourEveningEndDate = algorithm(
                // and blueHourEveningStartDate too
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
            ) ?: return null

            val blueHourEveningEndDate = algorithm(
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-6.0),
            ) ?: return null

            return SunPhaseList(
                firstLight = firstLight ?: return null,
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
                lastLight = lastLight ?: return null,
            )
        }

    /** [SunPhase.GoldenHour] daylight is redder and softer than when the sun is higher in the sky */
    val goldenHour = object : TwiceADaySunPhases<SunPhase.GoldenHour> {
        private fun base(time: DateTime): SunPhase.GoldenHour? {
            val goldenHourStartDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
            ) ?: return null
            val goldenHourEndDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
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

    /** [SunPhase.BlueHour] when the Sun is at a significant depth below the horizon but the viewer can see light */
    val blueHour = object : TwiceADaySunPhases<SunPhase.BlueHour> {
        private fun base(time: DateTime): SunPhase.BlueHour? {
            val blueHourStartDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-6.0),
            ) ?: return null
            val blueHourEndDate = algorithm(
                time = time,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-4.0),
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


    /** [SunPhase.FirstLight] when first light come to viewer */
    val firstLight: SunPhase.FirstLight?
        get() {
            val firstLightDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Astronomical,
            ) ?: return null
            return SunPhase.FirstLight(firstLightDate)
        }

    /** [SunPhase.Sunrise] when the upper rim of the Sun appears on the horizon in the morning */
    val sunrise: SunPhase.Sunrise?
        get() {
            val sunriseDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(-0.83),
            ) ?: return null
            return SunPhase.Sunrise(sunriseDate)
        }


    /** [SunPhase.Day] no soft light, just usual day light */
    val day: SunPhase.Day?
        get() {
            val dayStartDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
            ) ?: return null
            val dayEndDate = algorithm(
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Custom(6.0),
            ) ?: return null
            return SunPhase.Day(dayStartDate, dayEndDate)
        }

    /** [SunPhase.Sunset] when the Sun below the horizon */
    val sunset: SunPhase.Sunset?
        get() {
            val sunsetDate = algorithm(
                time = DateTime.Evening,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Official,
            ) ?: return null
            return SunPhase.Sunset(sunsetDate)
        }

    /** [SunPhase.LastLight] When last light visible to viewer */
    val lastLight: SunPhase.LastLight?
        get() {
            val sunsetDate = algorithm(
                time = DateTime.Morning,
                date = date,
                latitude = latitude,
                longitude = longitude,
                twilight = Twilight.Astronomical,
            ) ?: return null
            return SunPhase.LastLight(sunsetDate)
        }
}

interface TwiceADaySunPhases<T : SunPhase> {
    val morning: T?
    val evening: T?
}