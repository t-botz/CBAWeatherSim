package com.tibodelor.interview.cba.weathersimultator

object TestUtil {
  val weatherStations: List[WeatherStation] = Generator.generateStations(100)
  val city: List[City] = Generator.generateCities(10, weatherStations)
}
