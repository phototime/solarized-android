package dev.zotov.phototime.solarized

import org.junit.Assert
import java.time.LocalDateTime

private fun LocalDateTime.formatTime(): String {
    var hour = this.hour.toString()
    var minute = this.minute.toString()
    if (hour.length == 1) hour = "0$hour"
    if (minute.length == 1) minute = "0$minute"
    return "$hour:$minute"
}

fun SunPhase.time(): String = when (this) {
    is SunPhase.FirstLight -> this.date.formatTime()
    is SunPhase.BlueHour -> "${this.start.formatTime()}-${this.end.formatTime()}"
    is SunPhase.Sunrise ->  this.date.formatTime()
    is SunPhase.GoldenHour -> "${this.start.formatTime()}-${this.end.formatTime()}"
    is SunPhase.Day -> "${this.start.formatTime()}-${this.end.formatTime()}"
    is SunPhase.Sunset -> this.date.formatTime()
    is SunPhase.LastLight -> this.date.formatTime()
}

fun SunPhase.assertTime(time: String) = Assert.assertEquals(this.time(), time)