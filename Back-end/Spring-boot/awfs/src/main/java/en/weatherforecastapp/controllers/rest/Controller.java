package en.weatherforecastapp.controllers.rest;

import en.weatherforecastapp.models.dto.WeatherForecastDTO;
import en.weatherforecastapp.services.WeatherForecastService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("weatherforecastapp")
@AllArgsConstructor
public class Controller {
    private final WeatherForecastService wfService;


    @GetMapping("/api/weather/current/{city}")
    public ResponseEntity<WeatherForecastDTO> getCurrentForecastForACity(@PathVariable final String city) {
        try {
            Optional<WeatherForecastDTO> weatherForecast = wfService.getWeatherForecast(city);

            return weatherForecast.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
