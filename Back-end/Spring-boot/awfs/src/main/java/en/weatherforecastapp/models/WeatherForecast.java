package en.weatherforecastapp.models;

import en.weatherforecastapp.utilities.ForecastType;
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
    @Column(name = "\"Humidity\"", nullable = false)
    private Integer humidity;
    @Column(name = "\"WindSpeed\"", nullable = false)
    private Float windSpeed;
    @Enumerated(EnumType.STRING)
    @Column(name = "\"ForecastType\"", nullable = false)
    private ForecastType forecastType;
    @Column(name = "\"FeelsLikeTemperature\"", nullable = false)
    private Float feelsLikeTemperature;
    @Column(name = "\"Pressure\"", nullable = false)
    private Integer pressure;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"weatherForecastCollection\"")
    private WeatherForecastCollection weatherForecastCollection;
}
