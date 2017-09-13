package dongdongwashing.com.entity.cameraPosition.AddressComponent;

import java.io.Serializable;

/**
 * Created by 沈 on 2017/4/18.
 */

public class StreetNumber implements Serializable {

    private String street;
    private String number;
    private String location;
    private String direction;
    private String distance;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "StreetNumber{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", location='" + location + '\'' +
                ", direction='" + direction + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
