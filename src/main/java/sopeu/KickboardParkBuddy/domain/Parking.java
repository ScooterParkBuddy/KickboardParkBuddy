package sopeu.KickboardParkBuddy.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parking {
    @Id
    @GeneratedValue
    @Column(name = "parking_id")
    private Long id;

    private String placeName;
    private String address;
    private Double lat;
    private Double lng;

    public Parking(String placeName, String address, Double lat, Double lng) {
        this.placeName = placeName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
}
