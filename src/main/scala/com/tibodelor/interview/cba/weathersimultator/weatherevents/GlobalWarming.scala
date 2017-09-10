package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.Duration

import com.tibodelor.interview.cba.weathersimultator.{Coordinates, WeatherMeasurement}

case class GlobalWarming(temperatureRisePerYear: Double) extends WeatherEvent {
  val temperatureRisePerSec: Double = temperatureRisePerYear / (60 * 60 * 24 * 365)

  override def doesImpactArea(coordinates: Coordinates): Boolean = true

  override def evolve(duration: Duration): List[WeatherEvent] = List(this)

  override def reflectImpactOnMeasurement(measurement: WeatherMeasurement, duration: Duration): WeatherMeasurement = {
    measurement.copy(temperature = (measurement.temperature + duration.getSeconds * temperatureRisePerSec).toFloat)
  }
}
