package en.weatherforecastapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "\"FavoriteCity\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "\"IdUser\"")
    private User user;
    @ManyToOne
    @JoinColumn(name = "\"IdCity\"")
    private City city;
}
