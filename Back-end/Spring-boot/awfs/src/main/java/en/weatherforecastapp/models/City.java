package en.weatherforecastapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "\"City\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;
    @Column(name = "\"Name\"", nullable = false)
    private String name;
    @Column(name = "\"Latitude\"", nullable = false)
    private Float latitude;
    @Column(name = "\"Longitude\"", nullable = false)
    private Float longitude;
}
