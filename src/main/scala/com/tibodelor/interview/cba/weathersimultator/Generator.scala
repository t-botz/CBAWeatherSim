package com.tibodelor.interview.cba.weathersimultator

import scala.util.Random


object Generator {

  def generatePlanet(planetSize: Int, nbCity: Int): Planet = {
    val stations = generateStations(planetSize)
    val cities = generateCities(nbCity, stations)
    Planet("Zorg", planetSize, cities, stations)
  }

  private def generateCities(nbCity: Int, existingStations: List[WeatherStation]): List[City] = {
    Random
      .shuffle(existingStations)
      .filter(_.coordinates.altitude < 0)
      .take(nbCity)
      .map(City(generateName(), _))
  }

  private def generateName(): String = Random.alphanumeric.take(Random.nextInt(20) + 5).mkString

  private def generateStations(maxCoordinate: Int): scala.List[WeatherStation] =
    (for {
      i <- 0 to maxCoordinate
      j <- 0 to maxCoordinate
    } yield WeatherStation(Coordinates(i, j, generateAltitude()))).toList


  private def generateAltitude() = (Random.nextGaussian() * 10000).toInt
}
