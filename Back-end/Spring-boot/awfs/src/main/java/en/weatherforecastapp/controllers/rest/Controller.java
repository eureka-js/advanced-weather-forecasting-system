package en.weatherforecastapp.controllers.rest;

import en.weatherforecastapp.models.dto.WeatherForecastDTO;
import en.weatherforecastapp.services.WeatherForecastService;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("weatherforecastapp")
@AllArgsConstructor
public class Controller {
    private final WeatherForecastService wfService;


    @GetMapping("/api/weather/current/{city}")
    public ResponseEntity<WeatherForecastDTO> getCurrentForecastForACity(@PathVariable final String city) {
        try {
            WeatherForecastDTO weatherForecast = wfService.getWeatherForecast(city).orElseThrow(NotFoundException::new);

            return ResponseEntity.ok(weatherForecast);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
