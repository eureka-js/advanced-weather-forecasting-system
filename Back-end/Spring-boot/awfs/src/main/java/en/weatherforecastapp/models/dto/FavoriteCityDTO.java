package en.weatherforecastapp.models.dto;

import en.weatherforecastapp.models.FavoriteCity;
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


    public FavoriteCityDTO(final FavoriteCity fc) {
        this.id = fc.getId();
        this.username = fc.getUser().getUsername();
        this.cityName = fc.getCity().getName();
    }
}
