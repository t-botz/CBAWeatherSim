package com.tibodelor.interview.cba.weathersimultator

import java.time.{Duration, Instant}

import com.tibodelor.interview.cba.weathersimultator.weatherevents.{GlobalWarming, Pollution, SeasonEffect, WeatherEvent}

import scala.collection.GenSeq
import scala.util.Random


object Generator {

  def generatePlanet(planetSize: Int, nbCity: Int): Planet = {
    val stations = generateStations(planetSize)
    val cities = generateCities(nbCity, stations)
    Planet("Zorg", planetSize, cities, stations)
  }

  private[weathersimultator] def generateCities(nbCity: Int, existingStations: List[WeatherStation]): List[City] = {
    Random
      .shuffle(existingStations)
      .filter(_.coordinates.altitude < 0)
      .take(nbCity)
      .map(City(generateName(), _))
  }

  private[weathersimultator] def generateName(): String = Random.alphanumeric.take(Random.nextInt(20) + 5).mkString

  private[weathersimultator] def generateStations(maxCoordinate: Int): scala.List[WeatherStation] =
    (for {
      i <- 0 to maxCoordinate
      j <- 0 to maxCoordinate
    } yield WeatherStation(Coordinates(i, j, generateAltitude()))).toList

  private[weathersimultator] def generateAltitude() = generateGaussian(0, 10000).toInt

  private[weathersimultator] def generateGaussian(mean: Double, deviation: Double): Double = Random.nextGaussian() * deviation + mean

  def generateWeatherSnapshot(planet: Planet, startDate: Instant): WeatherSnapshot = {
    val trainingPeriod = Duration.ofDays(10)
    WeatherSnapshot(planet, startDate.minus(trainingPeriod), generateEvents(planet), generateMeasurements(planet)).forecast(startDate)
  }

  private[weathersimultator] def generateMeasurements(planet: Planet): GenSeq[WeatherMeasurement] = planet.stations.map(WeatherMeasurement(_, generateGaussian(15, 15).toFloat, generateGaussian(1500, 200).toFloat, generateHumidity()))

  private[weathersimultator] def generateHumidity() = {
    val humidity = generateGaussian(50, 50).toInt
    if (humidity > 100) 100 else if (humidity < 0) 0 else humidity
  }

  private[weathersimultator] def generateEvents(planet: Planet): GenSeq[WeatherEvent] = List(
    GlobalWarming(1.2d),
    SeasonEffect()
  ) :+ Pollution(planet.cities.zip(Stream.continually(Random.nextInt(100)).take(10)).toMap)
}
