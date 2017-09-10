package com.tibodelor.interview.cba.weathersimultator

import org.scalatest.FunSuite

class GeneratorTest extends FunSuite{

  test("Generator should generate coherent Planet"){
    val planet = Generator.generatePlanet(10,3)

    assert(planet.cities.size == 3)
    assert(planet.size == 10)
    assert(planet.stations.size == 121) // Each latitude, longitude pair has its station (from 0 to 10)

    // I can find back the station for all cities
    assert(planet.cities.forall(c => planet.stations.contains(c.station)))

    planet.stations.foreach { s =>
      val c = s.coordinates
      assert(c.latitude >= 0)
      assert(c.latitude <= 10)
      assert(c.longitude >= 0)
      assert(c.longitude <= 10)
    }

    //altitude could be validated this way, but still flaky because of randomness
    //assert(planet.stations.count(s => s.coordinates.altitude < 10000 && s.coordinates.altitude > -10000) > 70)
  }
}
