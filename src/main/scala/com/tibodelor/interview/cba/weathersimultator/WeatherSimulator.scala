package com.tibodelor.interview.cba.weathersimultator

import java.time._

case class WeatherAppArgs(nbIterations: Int = 10, gap: Duration = Duration.parse("P1D"), nbCities: Int = 10)

object WeatherSimulator {

  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[WeatherAppArgs]("sbt run") {
      head("weather_simu", "0.1")
      note("Simulate the weather on Planet Zorgs")
      opt[Unit]('h', "help")
        .action((_, c) => {
          showUsage()
          c
        })

      opt[Int]('n', "nbIterations")
        .valueName("<nbIterations>")
        .text("Number of data points to generate per city")
        .action((x, args) => args.copy(nbIterations = x))

      opt[String]("gap")
        .valueName("<duration>")
        .action((x, args) => args.copy(gap = Duration.parse(x)))
        .text(
          """Gap between reports.
            |"PT20.345S" -- parses as "20.345 seconds"
            |"PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
            |"PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
            |"P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
            |"P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
            |"P-6H3M"    -- parses as "-6 hours and +3 minutes"
            |"-P6H3M"    -- parses as "-6 hours and -3 minutes"
            |"-P-6H+3M"  -- parses as "+6 hours and -3 minutes""")


      opt[Int]("nbCities")
        .valueName("<nbCities>")
        .action((x, args) => args.copy(nbCities = x))
        .text("Number of Cities")
    }

    parser.parse(args, WeatherAppArgs()) match {
      case Some(config) => println(config)
      case None => // arguments are bad, error message will have been displayed
    }
  }
}
