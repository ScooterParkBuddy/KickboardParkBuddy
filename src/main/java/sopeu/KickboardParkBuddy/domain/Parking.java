package sopeu.KickboardParkBuddy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_id")
    private Long id;

    private String placeName;
    private String address;

    @Column(columnDefinition = "POINT")
    private Point location;

    private Double longitude;
    private Double latitude;

    public Parking(String placeName, String address, Point point, Double longitude, Double latitude) {
        this.placeName = placeName;
        this.address = address;
        this.location = point;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
