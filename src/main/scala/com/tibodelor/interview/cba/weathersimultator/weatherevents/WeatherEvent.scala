package com.tibodelor.interview.cba.weathersimultator.weatherevents

import java.time.Duration

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
    * @param duration Ellapsed time since the evolution of the event
    * @return New measurement
    */
  def reflectImpactOnMeasurement(measurement: WeatherMeasurement, duration: Duration): WeatherMeasurement



  /**
    * Evolve the event
    * @param duration Ellapsed time since the evolution of the event
    * @return List of events resulting of the evolution
    */
  def evolve(duration: Duration): List[WeatherEvent]

}
