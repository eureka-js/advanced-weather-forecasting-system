package en.weatherforecastapp.controllers.rest;

import en.weatherforecastapp.models.dto.WeatherForecastDTO;
import en.weatherforecastapp.services.WeatherForecastService;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;


@RestController
@RequestMapping("weatherforecastapp")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {
    private final WeatherForecastService wfService;


    @GetMapping("/api/weather/current/{city}")
    public ResponseEntity<WeatherForecastDTO> getCurrentForecastForACity(@PathVariable final String city) {
        try {
            WeatherForecastDTO weatherForecast = wfService.getWeatherForecast(city).orElseThrow(NotFoundException::new);
            // Mock value for when I go over the web service call limit
            /*WeatherForecastDTO weatherForecast = new WeatherForecastDTO("Orlando", 36.2F
                    , "Very warm with a slight rise in street crime"
                    , Timestamp.from(Instant.ofEpochSecond(1723939370)), 6, 15000);*/

            return ResponseEntity.ok(weatherForecast);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
