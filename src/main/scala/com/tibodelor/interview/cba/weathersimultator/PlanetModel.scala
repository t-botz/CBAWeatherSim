package com.tibodelor.interview.cba.weathersimultator

case class Coordinates(latitude: Int, longitude:Int, altitude: Int)

case class City (name: String, station: WeatherStation)

case class Planet(name:String, size: Int, cities: List[City], stations: List[WeatherStation])

case class WeatherStation(coordinates: Coordinates)
