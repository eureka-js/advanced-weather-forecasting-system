package en.weatherforecastapp.models.dto;

import en.weatherforecastapp.models.WeatherForecastCollection;
import en.weatherforecastapp.utilities.ForecastType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
@AllArgsConstructor
public class WeatherForecastCollectionDTO {
    private Long id;
    @NotBlank
    private String city;
    @NotNull
    private Timestamp dateTime;
    @NotNull
    private ForecastType forecastType;
    @NotNull
    private List<WeatherForecastDTO> weatherForecasts;


    public WeatherForecastCollectionDTO(final WeatherForecastCollection wfc
            , final List<WeatherForecastDTO> weatherForecasts) {
        this.id = wfc.getId();
        this.city = wfc.getCity().getName();
        this.dateTime = wfc.getDateTime();
        this.forecastType = wfc.getForecastType();
        this.weatherForecasts = weatherForecasts;
    }
}
