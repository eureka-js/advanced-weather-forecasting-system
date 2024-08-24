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
    @Column(name = "\"DateTime\"", nullable = false)
    private Timestamp dateTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "\"ForecastType\"", nullable = false)
    private ForecastType forecastType;

    @ManyToOne
    @JoinColumn(name = "\"IdCity\"")
    private City city;
    @OneToMany(mappedBy = "weatherForecastCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeatherForecast> weatherForecasts = new ArrayList<>();
}
