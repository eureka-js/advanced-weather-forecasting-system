package en.weatherforecastapp.models.dto;

import en.weatherforecastapp.models.WeatherForecast;
import en.weatherforecastapp.utilities.ForecastType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
public class WeatherForecastDTO {
    private Long id;
    @NotBlank
    private String city;
    @NotNull
    private Float temperature;
    @NotNull
    private String description;
    @NotNull
    private Timestamp dateTime;
    @NotNull
    @Min(value = 0, message = "UV index must not be a negative integer")
    private Integer uvIndex;
    @NotNull
    @Min(value = 0, message = "Visibility must not be a negative integer")
    private Integer visibility;
    @NotNull
    @Min(value = 0, message = "Humidity must not be a negative integer")
    @Max(value = 100, message = "Humidity must be less or equal to 100")
    private Integer humidity;
    @NotNull
    @Min(value = 0, message = "Wind speed must not be a negative integer")
    private Float windSpeed;
    @NotNull
    private ForecastType forecastType;
    @NotNull
    private Float feelsLikeTemperature;
    @NotNull
    @Min(value = 0, message = "Pressure must not be a negative integer")
    private Integer pressure;


    public WeatherForecastDTO (WeatherForecast wf) {
        this.id = wf.getId();
        this.city = wf.getCity();
        this.temperature = wf.getTemperature();
        this.description = wf.getDescription();
        this.dateTime = wf.getDateTime();
        this.uvIndex = wf.getUVIndex();
        this.visibility = wf.getVisibility();
        this.humidity = wf.getHumidity();
        this.windSpeed = wf.getWindSpeed();
        this.forecastType = wf.getForecastType();
        this.feelsLikeTemperature = wf.getFeelsLikeTemperature();
        this.pressure = wf.getPressure();
    }
}
