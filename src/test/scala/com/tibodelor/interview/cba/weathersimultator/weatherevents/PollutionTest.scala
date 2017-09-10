package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.Instant
import java.time.temporal.ChronoUnit

import com.tibodelor.interview.cba.weathersimultator._
import org.scalatest.FunSuite

class PollutionTest extends FunSuite {

  test("Polluted cities generate pollution") {
    val station = WeatherStation(Coordinates(1, 1, 1))
    val bla = City("bla", station)
    val pollution = Pollution(Map(bla -> 50))

    assert(pollution.doesImpactArea(bla.station.coordinates))

    val now = Instant.now()
    val later = now.plus(3, ChronoUnit.DAYS)
    val oldMeasurement = WeatherMeasurement(station, 1f, 1f, 1)
    val newMeasurement = pollution.reflectImpactOnMeasurement(oldMeasurement, now, later)

    assert(oldMeasurement == newMeasurement)

    val events: List[WeatherEvent] = pollution.evolve(now, later)

    assert(events.head == pollution)
    assert(events(1).asInstanceOf[PollutionLevel].coordinates == station.coordinates)
    val expectedLevel = 3 * 24 * 50
    assert(events(1).asInstanceOf[PollutionLevel].level == expectedLevel)
    for (i <- 2 to 5) {
      assert(events(i).asInstanceOf[PollutionLevel].coordinates.getDistanceFrom(station.coordinates) <= 1)
      assert(events(i).asInstanceOf[PollutionLevel].level == expectedLevel / 2)
    }

  }
}
