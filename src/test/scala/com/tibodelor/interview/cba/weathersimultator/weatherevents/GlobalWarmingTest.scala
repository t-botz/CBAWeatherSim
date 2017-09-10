package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.Duration

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
    assert(globalWarming.evolve(Duration.ofDays(5)) == List(globalWarming))
  }

  test("Global Warming works on 5 years") {
    val measurement = WeatherMeasurement(TestUtil.weatherStations.head, 10f, 1000f, 50)
    implicit val floatEquality: Equality[Float] = TolerantNumerics.tolerantFloatEquality(0.01f)

    //Apply 1 time 5 years
    val after5Years = globalWarming.reflectImpactOnMeasurement(measurement, Duration.ofDays(365 * 5))
    assert(after5Years.temperature == 35)

    //Apply 5 times 1 year
    val after5YearsDivided = (1 to 5).foldLeft(measurement) {
      (m, _) => globalWarming.reflectImpactOnMeasurement(m, Duration.ofDays(365))
    }
    assert(after5YearsDivided.temperature == 35)
  }
}
