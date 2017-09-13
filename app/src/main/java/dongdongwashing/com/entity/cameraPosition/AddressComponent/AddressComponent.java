package dongdongwashing.com.entity.cameraPosition.AddressComponent;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 沈 on 2017/4/18.
 */

public class AddressComponent implements Serializable {

    private String country;
    private String province;
    private List<Object> city;
    private String citycode;
    private String district;
    private String adcode;
    private String township;
    private String towncode;
    private Object neighborhood;
    private Object building;
    private StreetNumber streetNumber;
    private List<Object> businessAreas;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<Object> getCity() {
        return city;
    }

    public void setCity(List<Object> city) {
        this.city = city;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getTowncode() {
        return towncode;
    }

    public void setTowncode(String towncode) {
        this.towncode = towncode;
    }

    public Object getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Object neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Object getBuilding() {
        return building;
    }

    public void setBuilding(Object building) {
        this.building = building;
    }

    public StreetNumber getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(StreetNumber streetNumber) {
        this.streetNumber = streetNumber;
    }

    public List<Object> getBusinessAreas() {
        return businessAreas;
    }

    public void setBusinessAreas(List<Object> businessAreas) {
        this.businessAreas = businessAreas;
    }

    @Override
    public String toString() {
        return "AddressComponent{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city=" + city +
                ", citycode='" + citycode + '\'' +
                ", district='" + district + '\'' +
                ", adcode='" + adcode + '\'' +
                ", township='" + township + '\'' +
                ", towncode='" + towncode + '\'' +
                ", neighborhood=" + neighborhood +
                ", building=" + building +
                ", streetNumber=" + streetNumber +
                ", businessAreas=" + businessAreas +
                '}';
    }
}
