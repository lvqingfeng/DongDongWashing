package dongdongwashing.com.entity.orderEvaluation;

/**
 * Created by 沈 on 2017/5/15.
 */

public class OrderEvaluationResult {

    private String orderID;
    private String washCarSpeed;
    private String washCarService;
    private String washCarQuality;
    private Object remark;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getWashCarSpeed() {
        return washCarSpeed;
    }

    public void setWashCarSpeed(String washCarSpeed) {
        this.washCarSpeed = washCarSpeed;
    }

    public String getWashCarService() {
        return washCarService;
    }

    public void setWashCarService(String washCarService) {
        this.washCarService = washCarService;
    }

    public String getWashCarQuality() {
        return washCarQuality;
    }

    public void setWashCarQuality(String washCarQuality) {
        this.washCarQuality = washCarQuality;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderEvaluationResult{" +
                "orderID='" + orderID + '\'' +
                ", washCarSpeed='" + washCarSpeed + '\'' +
                ", washCarService='" + washCarService + '\'' +
                ", washCarQuality='" + washCarQuality + '\'' +
                ", remark=" + remark +
                '}';
    }
}
