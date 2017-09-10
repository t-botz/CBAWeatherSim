package com.tibodelor.interview.cba.weathersimultator

import java.time._

case class WeatherAppArgs(nbIterations: Int = 10, gap: Duration = Duration.parse("P1D" ), nbCities: Int = 10)

object WeatherSimulator {

  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[WeatherAppArgs]("sbt run") {
      head("weather_simu", "0.1")
      note("Simulate the weather on Planet Zorgs")

      help("help").text("prints this usage text")

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
      case Some(config) => runSimulator(config)
      case None => // arguments are bad, error message will have been displayed
    }
  }

  private def runSimulator(config: WeatherAppArgs) = {
    val formatter = new ReportFormatter()
    val planet = Generator.generatePlanet(100, config.nbCities)
    (0 to config.nbIterations).foldLeft(Generator.generateWeatherSnapshot(planet, Instant.now())) {
      (status, _) => {
        formatter.format(status).foreach(println)
        println
        status.forecast(status.date.plus(config.gap))
      }
    }
  }
}
