package en.weatherforecastapp.models.dto;

import en.weatherforecastapp.models.WeatherForecast;
import en.weatherforecastapp.utilities.ForecastType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class WeatherForecastCollectionDTO {
    @NotBlank
    private String city;
    @NotNull
    private Timestamp dateTime;
    @NotNull
    private ForecastType forecastType;
    @NotNull
    private List<WeatherForecastDTO> weatherForecasts;
}
