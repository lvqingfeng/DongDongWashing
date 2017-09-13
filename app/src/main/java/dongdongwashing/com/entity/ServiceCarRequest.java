package dongdongwashing.com.entity;

import java.util.List;

/**
 * Created by 沈 on 2017/8/11.
 */

public class ServiceCarRequest {

    private String isSucess;
    private List<ServiceCarResult> data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public List<ServiceCarResult> getData() {
        return data;
    }

    public void setData(List<ServiceCarResult> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ServiceCarRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class ServiceCarResult {

        private String staffId;
        private String isWork;
        private String longitude;
        private String latitude;

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }

        public String getIsWork() {
            return isWork;
        }

        public void setIsWork(String isWork) {
            this.isWork = isWork;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            return "ServiceCarResult{" +
                    "staffId='" + staffId + '\'' +
                    ", isWork='" + isWork + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    '}';
        }
    }
}
