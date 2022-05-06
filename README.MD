# Solarized
[![](https://jitpack.io/v/phototime/solarized-android.svg)](https://jitpack.io/#phototime/solarized-android)


This is Android library to calculate sun phases like golden hour, blue hour, sunrise, sunset etc

## Features
- first / last light
- golden hour
- blue hour
- sunrise / sunset
- day


## How to use?
```kotlin
import dev.zotov.phototime.solarized

// all sun phases
val list = Solarized(latitude, longitude, date).list

// golden and blue hour (morning and evening)
val morningGoldenHour = Solarized(latitude, longitude, date).goldenHour.morning
val eveningGoldenHour = Solarized(latitude, longitude, date).goldenHour.evening
val morningBlueHour = Solarized(latitude, longitude, date).blueHour.morning
val eveningBlueHour = Solarized(latitude, longitude, date).blueHour.evening

// first and last light
val firstLight = Solarized(latitude, longitude, date).firstLight
val lastLight = Solarized(latitude, longitude, date).lastLight

// sunrise and sunset
val sunrise = Solarized(latitude, longitude, date).sunrise
val sunset = Solarized(latitude, longitude, date).sunset

// day time
val day = Solarized(latitude, longitude, date).day

println(morningGoldenHour.start) // LocalDateTime at UTC
```

## How to install?
**Step 1**. Add the JitPack repository to your top-level build file 
```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
**Step 2**. Add the dependency to your module
```gradle
dependencies {
  implementation 'com.github.phototime:solarized-android:1.0.7'
}
```

## Algorithm
Realization by [@BinaryBirds](https://github.com/BinaryBirds), [original repo](https://github.com/BinaryBirds/Sunlight)

## License

[WTFPL](LICENSE) - Do what the fuck you want to.
