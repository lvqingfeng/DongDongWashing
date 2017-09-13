package dongdongwashing.com.entity.localcity;

import java.io.Serializable;

/**
 * Created by 沈 on 2016/10/27.
 */
public class DiQu implements Serializable {

    private String id;
    private String name;

    public DiQu() {
    }

    public DiQu(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
