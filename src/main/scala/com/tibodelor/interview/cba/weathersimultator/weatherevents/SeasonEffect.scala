package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.{Duration, Instant, LocalDateTime, ZoneOffset}

import com.tibodelor.interview.cba.weathersimultator.{Coordinates, WeatherMeasurement}

/**
  * Simulate seasons
  * The closest to the latitude 50 the hotter and the less difference between season.
  * At the poles the mean temperature are -60 in Winter and +40 in summer
  */
case class SeasonEffect() extends WeatherEvent {

  override def doesImpactArea(coordinates: Coordinates) = true

  override def reflectImpactOnMeasurement(measurement: WeatherMeasurement, currentDate: Instant, forecastDate: Instant): WeatherMeasurement = {
    val latitude = measurement.source.coordinates.latitude

    //referenceDay is 1st of June, the hottest day in the northern hemisphere
    val referenceDay = LocalDateTime.ofInstant(forecastDate, ZoneOffset.UTC).withDayOfMonth(1).withMonth(6).toInstant(ZoneOffset.UTC)

    var distanceFromRefDay = Duration.between(referenceDay, forecastDate).getSeconds / (3600 * 24)

    // We want a value between 0 and 183
    if (Math.abs(distanceFromRefDay) > 183)
      distanceFromRefDay = 365 - Math.abs(distanceFromRefDay)

    val equatorHotEffect = Math.abs(latitude - 50) * 40 / 50 //Represent a fixed variation of max 40 degres has get further from equator
    val seasonEffectRatio = (50 - latitude).toFloat / 50
    val periodInfluence = (183 / 2f - Math.abs(distanceFromRefDay).toFloat) / 183 * 100 * seasonEffectRatio
    val meanTemperature = 30 + periodInfluence - equatorHotEffect

    measurement.copy(temperature = (meanTemperature + measurement.temperature) / 2)
  }

  override def evolve(currentDate: Instant, forecastDate: Instant) = List(this)
}
