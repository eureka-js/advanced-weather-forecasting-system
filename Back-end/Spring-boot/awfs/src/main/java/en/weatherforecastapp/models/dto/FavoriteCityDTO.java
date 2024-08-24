package en.weatherforecastapp.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class FavoriteCityDTO {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String cityName;
}
