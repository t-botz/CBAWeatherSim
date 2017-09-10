package com.tibodelor.interview.cba.weathersimultator

import java.text.DecimalFormat

class ReportFormatter {

  val temperatureFormatter = new DecimalFormat("+#.0;-#")
  val pressureFormatter = new DecimalFormat("#.0")
  val humidityFormatter = new DecimalFormat("#")

  def format(snap: WeatherSnapshot): Iterable[String] =
    snap.planet.cities.map(city => {
      val measurement = snap.measurements.find(_.source == city.station).get
      s"${city.name}|${city.station.coordinates}|${snap.date}|${deduceCondition(measurement)}|" +
        s"${temperatureFormatter.format(measurement.temperature)}|" +
        s"${pressureFormatter.format(measurement.pressure)}|" +
        s"${measurement.humidity}"
    }
    )

  def deduceCondition(measurement: WeatherMeasurement): String =
    if(measurement.humidity > 50)
      if(measurement.temperature < 0)
        "Snow"
      else
        "Rain"
    else
      "Sunny"
}
