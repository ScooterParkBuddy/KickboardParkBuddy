package sopeu.KickboardParkBuddy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accident_id")
    private Long id;

    private Double longitude;  //경도
    private Double latitude;  //위도

    private Long danger;

    public Accident(Double longitude, Double latitude, Long danger) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.danger = danger;
    }
}

