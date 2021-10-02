package dev.zotov.phototime.solarized

import java.time.LocalDateTime

sealed class SunPhase {
    /**
     * When first light come to viewer. Angle is -18
     * @property date when sun is 18deg below horizon
     */
    data class FirstLight(val date: LocalDateTime): SunPhase()

    /**
     * When the Sun is at a significant depth below the horizon but the viewer can see light. Angle is -6...-4
     * @property start when sun is 6deg below horizon
     * @property end when sun is 4deg below horizon
     */
    data class BlueHour(val start: LocalDateTime, val end: LocalDateTime): SunPhase()

    /**
     * When the upper rim of the Sun appears on the horizon in the morning. Angle is 0.83deg
     * @property date when sun is 0.83deg above horizon
     */
    data class Sunrise(val date: LocalDateTime): SunPhase()

    /**
     * Daylight is redder and softer than when the sun is higher in the sky. Angle is -4...6
     * @property start when sun is 4deg below horizon
     * @property end when sun is 6deg above horizon
     */
    data class GoldenHour(val start: LocalDateTime, val end: LocalDateTime): SunPhase()

    /**
     * No soft light, just usual day light. Angle is 6...6 (diff sides)
     * @property start when sun is 6deg above horizon (one side)
     * @property end when sun is 6deg above horizon (another side)
     */
    data class Day(val start: LocalDateTime, val end: LocalDateTime): SunPhase()

    /**
     * When the Sun below the horizon. Angle is -35.0 / 60.0
     * @property date when sun is 35.0 / 60.0deg below horizon
     */
    data class Sunset(val date: LocalDateTime): SunPhase()

    /**
     * When the Sun below the horizon. Angle is -18.0
     * @property date when sun is 18.0 below horizon
     */
    data class LastLight(val date: LocalDateTime): SunPhase()
}

/**
 * List, containing all sun phases during daytime
 */
data class SunPhaseList(
    val firstLight: SunPhase.FirstLight,
    val morningBlueHour: SunPhase.BlueHour,
    val sunrise: SunPhase.Sunrise,
    val morningGoldenHour: SunPhase.GoldenHour,
    val day: SunPhase.Day,
    val eveningGoldenHour: SunPhase.GoldenHour,
    val sunset: SunPhase.Sunset,
    val eveningBlueHour: SunPhase.BlueHour,
    val lastLight: SunPhase.LastLight,
)

