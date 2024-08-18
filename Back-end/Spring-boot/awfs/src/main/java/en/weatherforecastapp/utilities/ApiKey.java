package en.weatherforecastapp.utilities;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Data
@ConfigurationProperties(prefix = "api")
public class ApiKey {
    private String openWeatherKey;
    private String weatherbitKey;
}
