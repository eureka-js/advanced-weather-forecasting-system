package en.weatherforecastapp.utilities;


public class CustomJpaQuery {
    public static final String EXISTS_BY_CITY_AND_DATE_TIME = """
        SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
        FROM "WeatherForecast"
        WHERE "City" = ?1 AND "DateTime" = ?2 LIMIT 1;
    """;
}

