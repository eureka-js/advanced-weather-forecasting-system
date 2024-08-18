package en.weatherforecastapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
public class WeatherForecastDTO {
    private String city;
    private Float temperature;
    private String description;
    private Timestamp dateTime;
    private Integer UVIndex;
    private Integer visibility;
}
