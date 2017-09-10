package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.{LocalDateTime, ZoneOffset}
import java.time.temporal.ChronoUnit

import com.tibodelor.interview.cba.weathersimultator.{City, Coordinates, WeatherMeasurement, WeatherStation}
import org.scalatest.FunSuite

class SeasonEffectTest extends FunSuite {

  test("Weather should be cold on North pole in Winter") {
    val station = WeatherStation(Coordinates(0, 0, 1))
    val seasonEffect = SeasonEffect()

    val oldMeasurement = WeatherMeasurement(station, 0, 1f, 1)
    val firstDecember = LocalDateTime.of(2017, 12, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)
    val newMeasurement = seasonEffect.reflectImpactOnMeasurement(oldMeasurement, firstDecember, firstDecember.plus(1, ChronoUnit.DAYS))


    assert(newMeasurement.temperature < - 10)
  }

  test("Weather should be hot on North pole in Summer") {
    val station = WeatherStation(Coordinates(0, 0, 1))
    val seasonEffect = SeasonEffect()

    val oldMeasurement = WeatherMeasurement(station, 10, 1f, 1)
    val firstJune = LocalDateTime.of(2017, 6, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)
    val newMeasurement = seasonEffect.reflectImpactOnMeasurement(oldMeasurement, firstJune, firstJune.plus(1, ChronoUnit.DAYS))


    assert(newMeasurement.temperature > 10)
  }

  test("Weather should be hot at the equator in Summer") {
    val station = WeatherStation(Coordinates(50, 0, 1))
    val seasonEffect = SeasonEffect()

    val oldMeasurement = WeatherMeasurement(station, 20, 1f, 1)
    val firstJune = LocalDateTime.of(2017, 6, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)
    val newMeasurement = seasonEffect.reflectImpactOnMeasurement(oldMeasurement, firstJune, firstJune.plus(1, ChronoUnit.DAYS))


    assert(newMeasurement.temperature > 20)
  }

  test("Weather should be cold at the South Pole in Summer") {
    val station = WeatherStation(Coordinates(100, 0, 1))
    val seasonEffect = SeasonEffect()

    val oldMeasurement = WeatherMeasurement(station, 10, 1f, 1)
    val firstJune = LocalDateTime.of(2017, 6, 1, 0, 0, 0).toInstant(ZoneOffset.UTC)
    val newMeasurement = seasonEffect.reflectImpactOnMeasurement(oldMeasurement, firstJune, firstJune.plus(1, ChronoUnit.DAYS))


    assert(newMeasurement.temperature < 0)
  }
}
