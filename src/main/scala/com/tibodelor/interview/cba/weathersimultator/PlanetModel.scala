package com.tibodelor.interview.cba.weathersimultator

case class Coordinates(latitude: Int, longitude: Int, altitude: Int) {
  def getDistanceFrom(other: Coordinates): Double = {
    Math.sqrt(Math.pow(latitude - other.latitude, 2) + Math.pow(longitude - other.longitude, 2))
  }
}


case class City(name: String, station: WeatherStation)

case class Planet(name: String, size: Int, cities: List[City], stations: List[WeatherStation])

case class WeatherStation(coordinates: Coordinates)