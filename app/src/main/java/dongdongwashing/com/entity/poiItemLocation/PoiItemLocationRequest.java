package dongdongwashing.com.entity.poiItemLocation;

import java.util.List;

/**
 * Created by 沈 on 2017/4/18.
 */

public class PoiItemLocationRequest {


    /**
     * formatted_address : 北京市丰台区天天乐超市
     * province : 北京市
     * citycode : 010
     * city : 北京市
     * district : 丰台区
     * township : []
     * neighborhood : {"name":[],"type":[]}
     * building : {"name":[],"type":[]}
     * adcode : 110106
     * street : []
     * number : []
     * location : 116.404803,39.839617
     * level : 兴趣点
     */

    private String formatted_address;
    private String province;
    private String citycode;
    private String city;
    private String district;
    private NeighborhoodBean neighborhood;
    private BuildingBean building;
    private String adcode;
    private String location;
    private String level;
    private List<?> township;
    private List<?> street;
    private List<?> number;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public NeighborhoodBean getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(NeighborhoodBean neighborhood) {
        this.neighborhood = neighborhood;
    }

    public BuildingBean getBuilding() {
        return building;
    }

    public void setBuilding(BuildingBean building) {
        this.building = building;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<?> getTownship() {
        return township;
    }

    public void setTownship(List<?> township) {
        this.township = township;
    }

    public List<?> getStreet() {
        return street;
    }

    public void setStreet(List<?> street) {
        this.street = street;
    }

    public List<?> getNumber() {
        return number;
    }

    public void setNumber(List<?> number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PoiItemLocationRequest{" +
                "formatted_address='" + formatted_address + '\'' +
                ", province='" + province + '\'' +
                ", citycode='" + citycode + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", neighborhood=" + neighborhood +
                ", building=" + building +
                ", adcode='" + adcode + '\'' +
                ", location='" + location + '\'' +
                ", level='" + level + '\'' +
                ", township=" + township +
                ", street=" + street +
                ", number=" + number +
                '}';
    }

    public static class NeighborhoodBean {
        private List<?> name;
        private List<?> type;

        public List<?> getName() {
            return name;
        }

        public void setName(List<?> name) {
            this.name = name;
        }

        public List<?> getType() {
            return type;
        }

        public void setType(List<?> type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "NeighborhoodBean{" +
                    "name=" + name +
                    ", type=" + type +
                    '}';
        }
    }

    public static class BuildingBean {
        private List<?> name;
        private List<?> type;

        public List<?> getName() {
            return name;
        }

        public void setName(List<?> name) {
            this.name = name;
        }

        public List<?> getType() {
            return type;
        }

        public void setType(List<?> type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "BuildingBean{" +
                    "name=" + name +
                    ", type=" + type +
                    '}';
        }
    }
}
