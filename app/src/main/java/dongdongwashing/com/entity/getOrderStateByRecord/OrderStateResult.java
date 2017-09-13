package dongdongwashing.com.entity.getOrderStateByRecord;

/**
 * Created by 沈 on 2017/5/12.
 */

public class OrderStateResult {

    private String orderNumber;
    private String orderState;
    private String orderStateTime;
    private Object carUserName;
    private Object carTelePhone;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateTime() {
        return orderStateTime;
    }

    public void setOrderStateTime(String orderStateTime) {
        this.orderStateTime = orderStateTime;
    }

    public Object getCarUserName() {
        return carUserName;
    }

    public void setCarUserName(Object carUserName) {
        this.carUserName = carUserName;
    }

    public Object getCarTelePhone() {
        return carTelePhone;
    }

    public void setCarTelePhone(Object carTelePhone) {
        this.carTelePhone = carTelePhone;
    }

    @Override
    public String toString() {
        return "OrderStateResult{" +
                "orderNumber='" + orderNumber + '\'' +
                ", orderState='" + orderState + '\'' +
                ", orderStateTime='" + orderStateTime + '\'' +
                ", carUserName=" + carUserName +
                ", carTelePhone=" + carTelePhone +
                '}';
    }
}
