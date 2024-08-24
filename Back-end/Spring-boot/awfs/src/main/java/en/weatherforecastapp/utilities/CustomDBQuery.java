package en.weatherforecastapp.utilities;


public class CustomDBQuery {
    // While correct, it does not seem to work with JPA using query parameters.
    //  (it does work with hardcoded values instead of using query parameters) (Date: 2024-08-24)
    public static final String WFCOLL_FIND_WITH_MAX_DATE_TIME_BY_CITY_AND_FORECAST_TYPE = """
        WITH cteCity AS (
            SELECT "Id"
            FROM "City"
            WHERE "Name" = ?1
            LIMIT 1
        ), cteMaxDateTime AS (
            SELECT MAX(wfc."DateTime") AS MaxDateTime, cteC."Id"
            FROM "WeatherForecastCollection" AS wfc INNER JOIN
                cteCity AS cteC ON wfc."IdCity" = cteC."Id"
            WHERE wfc."ForecastType" = ?2
            GROUP BY cteC."Id"
        )
        SELECT wfc.*
        FROM "WeatherForecastCollection" AS wfc INNER JOIN
            cteMaxDateTime AS cm ON wfc."DateTime" = cm.MaxDateTime
        WHERE  wfc."IdCity" = cm."Id";
    """;

    // A temporary replacement for WFCOLL_FIND_WITH_MAX_DATE_TIME_BY_CITY_AND_FORECAST_TYPE query
    //  (Works with JPA using query parameters) (Date: 2024-08-24)
    public static final String TEMPORARY_NON_CTE = """
        SELECT *
        FROM "WeatherForecastCollection"
        WHERE "DateTime" = (
            SELECT MAX("DateTime")
            FROM "WeatherForecastCollection"
            WHERE "ForecastType" = ?2 AND "IdCity" = (
                SELECT "Id"
                FROM "City"
                WHERE "Name" = ?1
                LIMIT 1
            )
        ) AND "IdCity" = (
                SELECT "Id"
                FROM "City"
                WHERE "Name" = ?1
                LIMIT 1
            );
    """;

    public static final String USER_GET_ID_BY_USERNAME= """
        SELECT "Id"
        FROM "User"
        WHERE "Username" = ?1
        LIMIT 1;
    """;

    public static final String FAV_CITY_GET_CITY_NAME_BY_USER_ID= """
        SELECT c."Name"
        FROM "FavoriteCity" AS fc INNER JOIN
            "City" AS c ON fc."IdCity" = c."Id"
        WHERE fc."IdUser" = ?1;
    """;
}

