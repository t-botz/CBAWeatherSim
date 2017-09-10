package com.tibodelor.interview.cba.weathersimultator

import java.time.{Duration, Instant}

import com.tibodelor.interview.cba.weathersimultator.weatherevents.WeatherEvent

import scala.collection.GenSeq

case class WeatherMeasurement(source: WeatherStation, temperature: Float, pressure: Float, humidity: Int)

/**
  * A snapshot of the planet
  * @param planet The planet
  * @param date Date/Time when the snapshot taken
  * @param events The weather events going on at that time
  * @param measurements Measurements taken by all stations
  */
case class WeatherSnapshot(planet: Planet, date: Instant, events: GenSeq[WeatherEvent], measurements: GenSeq[WeatherMeasurement]) {

  /**
    * Predict how the weather going to evolve
    * @param forecastTime Time in the future to forecsat for
    * @return The forecast snapshot
    */
  def forecast(forecastTime: Instant): WeatherSnapshot = {

    val newMeasurments =
      measurements
        .par
        .map(m =>
          (m, events
            .par
            .filter(e => e.doesImpactArea(m.source.coordinates)))
        )
        .map { case (m, eventsMatching) =>
          eventsMatching.foldLeft(m) { (measurement, event) => event.reflectImpactOnMeasurement(measurement, date, forecastTime) }
        }

    val newEvents = events.par.flatMap(_.evolve(date, forecastTime))

    WeatherSnapshot(planet, forecastTime, newEvents, newMeasurments)
  }
}
