In Spring Boot under the resources directory (as of tag v2.0):
  - create a file named "application-hidden.properties" (hidden from git) with content:
    - api.openweatherkey=[key]
    - api.weatherbitkey=[key]
  - OpenWeather is being used for requesting city information (free plan)
  - Weatherbit is not being used within the code enymore because of the request limit of 50 requests per day (free plan)
  - Open-Meteo is being used for forecasts but it dosn't require a key
