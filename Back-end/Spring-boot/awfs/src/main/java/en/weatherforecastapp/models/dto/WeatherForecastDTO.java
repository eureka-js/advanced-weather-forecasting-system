package en.weatherforecastapp.models.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
public class WeatherForecastDTO {
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
}
