package en.weatherforecastapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity(name = "\"WeatherForecast\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;
    @Column(name = "\"City\"", nullable = false)
    private String city;
    @Column(name = "\"Temperature\"", nullable = false)
    private Float temperature;
    @Column(name = "\"Description\"", nullable = false)
    private String description;
    @Column(name = "\"DateTime\"", nullable = false)
    private Timestamp dateTime;
    @Column(name = "\"UVIndex\"", nullable = false)
    private Integer UVIndex;
    @Column(name = "\"Visibility\"", nullable = false)
    private Integer visibility;
}
