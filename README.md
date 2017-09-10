# Weather Simulator

This Simulator simulates the weather on the planet Zorg.

Zorg has similar property to Earth but not really... 
Here the special property of Zorgs :
 - Zorg is a flat planet (it sounds weird but we never stop progress...)
 - Zorg has a coordinate system going from 0 to 100
 - Zorg is equipped of weather station at every lat and long coordinates
 - Pollution is a problem in major cities of Zorg, it does affect the temperature and tends to spread
 - Zorg is subject to global warming, it's temperature is going up over the years
 - It is much hotter at the latitude 50 (middle of Zorg)
 - Similar to Earth, Zorg has seasons, the further from the center, the bigger effect with opposite effect depending on the hemisphere
 
## Technical details

I have implemented each change of weather as a weather events. Evolution is represented by a sequence of WeatherSnapshot.
Potentially any new effect can be implement by implementing a WeatherEvent (Like a volcano, the effect of the wind, ...)
I have implemented 3 of them : Global Warming, Seasons and Pollution

##How to run
`sbt  run` FTW!

More options can be found when you run `sbt run --help`:

```
Usage: sbt run [options]
  --help                   prints this usage text
  -n, --nbIterations <nbIterations>
                           Number of data points to generate per city
  --gap <duration>         Gap between reports.
            |"PT20.345S" -- parses as "20.345 seconds"
            |"PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
            |"PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
            |"P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
            |"P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
            |"P-6H3M"    -- parses as "-6 hours and +3 minutes"
            |"-P6H3M"    -- parses as "-6 hours and -3 minutes"
            |"-P-6H+3M"  -- parses as "+6 hours and -3 minutes
  --nbCities <nbCities>    Number of Cities
  ```