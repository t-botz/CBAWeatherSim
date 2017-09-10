package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.{Duration, Instant}

import com.tibodelor.interview.cba.weathersimultator.{Coordinates, WeatherMeasurement}

/**
  * An event affecting our weather measurements
  */
trait WeatherEvent {

  /**
    * Analyss whether an area is impacted or not by the event
    * @param coordinates Cooardintes of the ared
    * @return True if the area is impacted, false otherwise
    */
  def doesImpactArea(coordinates: Coordinates): Boolean


  /**
    * Apply events to area concerned
    * @param measurement measurement before the event
    * @param currentDate Date of the initial state
    * @param forecastDate Date to forecast to
    * @return New measurement
    */
  def reflectImpactOnMeasurement(measurement: WeatherMeasurement, currentDate: Instant, forecastDate: Instant): WeatherMeasurement



  /**
    * Evolve the event
    * @param currentDate Date of the initial state
    * @param forecastDate Date to forecast to
    * @return List of events resulting of the evolution
    */
  def evolve(currentDate: Instant, forecastDate: Instant): List[WeatherEvent]

}
