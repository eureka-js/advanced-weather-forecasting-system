-- Mock values for testing purposes

SET @dateFormat = 'yyyy, MM, dd';


INSERT INTO "WeatherForecast" ("City", "Temperature", "Description", "DateTime",  "UVIndex", "Visibility")
VALUES ('London', 32.5, 'Slightly warm with a lingering threat of never ending rain'
    , PARSEDATETIME('2024, 08, 17', @dateFormat), 5, 10000);

INSERT INTO "WeatherForecast" ("City", "Temperature", "Description", "DateTime",  "UVIndex", "Visibility")
VALUES ('Florida', 36.2, 'Very warm with a slight rise in street crime'
    , PARSEDATETIME('2024, 08, 17', @dateFormat), 6, 15000);

INSERT INTO "WeatherForecast" ("City", "Temperature", "Description", "DateTime",  "UVIndex", "Visibility")
VALUES ('Tokyo', 37.1, 'Very warm and humid with a slightly lower tide than usual'
    , PARSEDATETIME('2024, 08, 17', @dateFormat), 7, 20000);
