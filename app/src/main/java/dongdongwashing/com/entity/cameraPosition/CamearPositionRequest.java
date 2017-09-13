package dongdongwashing.com.entity.cameraPosition;

import java.io.Serializable;

/**
 * Created by 沈 on 2017/4/17.
 */

public class CamearPositionRequest implements Serializable {

    private String status;
    private String info;
    private String infocode;
    private CamearPositionResult regeocode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public CamearPositionResult getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(CamearPositionResult regeocode) {
        this.regeocode = regeocode;
    }

    @Override
    public String toString() {
        return "CamearPositionRequest{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", infocode='" + infocode + '\'' +
                ", regeocode=" + regeocode +
                '}';
    }
}
