CREATE TABLE IF NOT EXISTS "WeatherForecast" (
    "Id" INT PRIMARY KEY AUTO_INCREMENT,
    "City" VARCHAR(256) NOT NULL,
    "Temperature" REAL NOT NULL,
    "Description" VARCHAR(128) NOT NULL,
    "DateTime" DATE NOT NULL,
    "UVIndex" INTEGER NOT NULL,
    "Visibility" INTEGER NOT NULL
);