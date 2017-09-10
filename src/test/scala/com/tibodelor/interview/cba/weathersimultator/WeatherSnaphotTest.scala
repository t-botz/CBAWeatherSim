package com.tibodelor.interview.cba.weathersimultator

import java.time.temporal.ChronoUnit
import java.time.{Duration, Instant}

import com.tibodelor.interview.cba.weathersimultator.weatherevents.{GlobalWarming, WeatherEvent}
import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite

class WeatherSnaphotTest extends FunSuite {

  test("Weather Snapshot shouldn't change in time if no weather event") {
    val planet = Generator.generatePlanet(10, 2)
    val now = Instant.now()
    val snapshot = Generator.generateWeatherSnapshot(planet, now).copy(events = Seq.empty)

    val forecastSnapshot = snapshot.forecast(now.plus(3, ChronoUnit.DAYS))

    assert(forecastSnapshot.events == snapshot.events)
    assert(forecastSnapshot.measurements == snapshot.measurements)
    assert(forecastSnapshot.planet == snapshot.planet)
  }

  test("Weather Snapshot should change according to simple event") {
    val globalWarming = new GlobalWarming(1d)

    val planet = Generator.generatePlanet(10, 2)
    val now = Instant.now()
    val snapshot = Generator.generateWeatherSnapshot(planet, now).copy(events = List(globalWarming))

    val forecastSnapshot = snapshot.forecast(now.plus(365, ChronoUnit.DAYS))

    assert(forecastSnapshot.events == snapshot.events)
    assert(forecastSnapshot.planet == snapshot.planet)

    //All measurement should be 1 degres higher in temperature
    implicit val floatEquality = TolerantNumerics.tolerantFloatEquality(0.01f)
    snapshot.measurements.foreach(m => {
      val forecastMeasurement = forecastSnapshot.measurements.find(fm => m.source == fm.source).get
      assert(forecastMeasurement.temperature == m.temperature + 1)
    })
  }

}
