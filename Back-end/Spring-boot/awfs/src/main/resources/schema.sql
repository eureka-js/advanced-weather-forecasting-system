CREATE TABLE IF NOT EXISTS "WeatherForecast" (
    "Id" INT PRIMARY KEY AUTO_INCREMENT,
    "City" VARCHAR(256) NOT NULL,
    "Temperature" REAL NOT NULL,
    "Description" VARCHAR(128) NOT NULL,
    "DateTime" TIMESTAMP NOT NULL,
    "UVIndex" INTEGER NOT NULL,
    "Visibility" INTEGER NOT NULL,
    "Humidity" INTEGER NOT NULL,
    "WindSpeed" FLOAT NOT NULL,
    "ForecastType" ENUM('Current', 'Hourly', 'Daily') NOT NULL,
    "FeelsLikeTemperature" FLOAT NOT NULL,
    "Pressure" INTEGER NOT NULL,

    "IdWeatherForecastCollection" LONG,
    FOREIGN KEY ("IdWeatherForecastCollection") REFERENCES "WeatherForecastCollection"("Id") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "WeatherForecastCollection" (
    "Id" INT PRIMARY KEY AUTO_INCREMENT,
    "City" VARCHAR(256) NOT NULL,
    "DateTime" TIMESTAMP NOT NULL,
    "ForecastType" ENUM('Hourly', 'Daily') NOT NULL,
    "Forecasts" LONG ARRAY NOT NULL
);