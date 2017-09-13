package dongdongwashing.com.entity.localcity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 沈 on 2016/10/27.
 */

public class Province implements Serializable {

    private String id;
    private String name;
    private List<City> citys;

    public Province() {
        super();
    }

    public Province(String id, String name, List<City> citys) {
        super();
        this.id = id;
        this.name = name;
        this.citys = citys;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return name;
    }
}
