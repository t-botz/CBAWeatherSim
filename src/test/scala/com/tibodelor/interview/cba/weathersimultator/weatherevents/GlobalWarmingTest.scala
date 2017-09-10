package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.Instant
import java.time.temporal.ChronoUnit

import com.tibodelor.interview.cba.weathersimultator.{Coordinates, TestUtil, WeatherMeasurement}
import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FunSuite

import scala.util.Random

class GlobalWarmingTest extends FunSuite {

  private val globalWarming = GlobalWarming(5d)

  test("Global Warming is a global effect") {
    assert(globalWarming.doesImpactArea(Coordinates(Random.nextInt(), Random.nextInt(), Random.nextInt())))
  }

  test("Global Warming is a constant effect") {
    val now = Instant.now()
    assert(globalWarming.evolve(now, now) == List(globalWarming))
  }

  test("Global Warming works on 5 years") {
    val now = Instant.now()
    val measurement = WeatherMeasurement(TestUtil.weatherStations.head, 10f, 1000f, 50)
    implicit val floatEquality: Equality[Float] = TolerantNumerics.tolerantFloatEquality(0.01f)

    //Apply 1 time 5 years
    val after5Years = globalWarming.reflectImpactOnMeasurement(measurement, now, now.plus(365 * 5, ChronoUnit.DAYS))
    assert(after5Years.temperature == 35)

    //Apply 5 times 1 year
    val after5YearsDivided = (1 to 5).foldLeft(measurement) {
      (m, i) => globalWarming.reflectImpactOnMeasurement(m, now.plus(365 * (i - 1), ChronoUnit.DAYS), now.plus(365 * i, ChronoUnit.DAYS))
    }
    assert(after5YearsDivided.temperature == 35)
  }
}
