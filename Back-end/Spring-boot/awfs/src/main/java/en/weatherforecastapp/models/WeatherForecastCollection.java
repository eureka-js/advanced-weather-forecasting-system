package en.weatherforecastapp.models;

import en.weatherforecastapp.utilities.ForecastType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "\"WeatherForecastCollection\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecastCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;
    @Column(name = "\"City\"", nullable = false)
    private String city;
    @Column(name = "\"DateTime\"", nullable = false)
    private Timestamp dateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "\"ForecastType\"", nullable = false)
    private ForecastType forecastType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"weatherForecastCollection\"")
    private List<WeatherForecast> weatherForecasts = new ArrayList<>();
}
