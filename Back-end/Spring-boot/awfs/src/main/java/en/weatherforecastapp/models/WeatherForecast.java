package en.weatherforecastapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity(name = "WeatherForecast")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "City", nullable = false)
    private String city;
    @Column(name = "Temperature", nullable = false)
    private Float temperature;
    @Column(name = "Description", nullable = false)
    private String description;
    @Column(name = "DateTime", nullable = false)
    private LocalDate dateTime;
    @Column(name = "UVIndex", nullable = false)
    private Integer UVIndex;
    @Column(name = "Visibility", nullable = false)
    private Integer visibility;
}
