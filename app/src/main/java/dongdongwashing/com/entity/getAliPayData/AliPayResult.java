package dongdongwashing.com.entity.getAliPayData;

/**
 * Created by 沈 on 2017/5/18.
 */

public class AliPayResult {

    private String appID;
    private String key;
    private String mid;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "AliPayResult{" +
                "appID='" + appID + '\'' +
                ", key='" + key + '\'' +
                ", mid='" + mid + '\'' +
                '}';
    }
}
