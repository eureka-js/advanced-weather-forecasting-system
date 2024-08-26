package en.weatherforecastapp.controllers.rest;

import en.weatherforecastapp.models.dto.FavoriteCityDTO;
import en.weatherforecastapp.models.dto.WeatherForecastCollectionDTO;
import en.weatherforecastapp.services.CityService;
import en.weatherforecastapp.services.WeatherForecastService;
import en.weatherforecastapp.utilities.ForecastType;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("weatherforecastapp")
@AllArgsConstructor
@Validated
public class Controller {
    private final WeatherForecastService wfService;
    private final CityService cityService;


    @GetMapping("/api/weather/current/{city}")
    public ResponseEntity<WeatherForecastCollectionDTO> getCurrentForecastForACity(@NotBlank @PathVariable final String city) {
        try {
            final WeatherForecastCollectionDTO weatherForecast = wfService
                .getWForecastCollByCity(city, ForecastType.Current, wfService::createCurrWForecastColl)
                .orElseThrow(NotFoundException::new);

            return ResponseEntity.ok(weatherForecast);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/api/weather/hourly/{city}")
    public ResponseEntity<WeatherForecastCollectionDTO> getHourlyForecastForACity(@NotBlank @PathVariable final String city) {
        try {
            final WeatherForecastCollectionDTO wForecastColl = wfService
                .getWForecastCollByCity(city, ForecastType.Hourly, wfService::createHourlyWForecastColl)
                .orElseThrow(NotFoundException::new);

            return ResponseEntity.ok(wForecastColl);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/api/weather/daily/{city}")
    public ResponseEntity<WeatherForecastCollectionDTO> getDailyForecastForACity(@NotBlank @PathVariable final String city) {
        try {
            final WeatherForecastCollectionDTO wForecastColl = wfService
                    .getWForecastCollByCity(city, ForecastType.Daily, wfService::createDailyWForecastColl)
                    .orElseThrow(NotFoundException::new);

            return ResponseEntity.ok(wForecastColl);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/api/user/favorite")
    public ResponseEntity<String> addFavoriteCity(@Valid @RequestBody final FavoriteCityDTO body) {
        // @NotBlank annotation in FavoriteCityDTO seems to not work for username while it does work for cityName.
        // Or there could be some other reason. (Date: 2024-08-24)
        if (body.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return cityService.addFavoriteCity(body)
                ? ResponseEntity.status(HttpStatus.CREATED).body("{ \"message\": \"Favorite city successfully added\" }")
                : ResponseEntity.status(HttpStatus.CONFLICT).body("{ \"message\": \"Favorite city already added\" }");
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"message\": \"" + e.getMessage() + "\" }");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/api/weather/favorites")
    public ResponseEntity<List<WeatherForecastCollectionDTO>> getFavoriteCityForecasts(@NotBlank @RequestParam final String username) {
        try {
            return ResponseEntity.ok(wfService.getFavoriteCityWForecasts(username));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/api/user/favorites/{username}")
    public ResponseEntity<List<FavoriteCityDTO>> getFavoriteCities(@NotBlank @PathVariable final String username) {
        try {
            return ResponseEntity.ok(cityService.getFavoriteCities(username));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/api/user/favorite/{id}")
    public ResponseEntity<String> removeFavoriteCity(@NotBlank @PathVariable final Long id) {
        try {
            return cityService.removeFavoriteCity(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
