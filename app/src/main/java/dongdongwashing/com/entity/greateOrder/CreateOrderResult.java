package dongdongwashing.com.entity.greateOrder;

/**
 * Created by 沈 on 2017/5/9.
 */

public class CreateOrderResult {

    private String orderNumber;
    private String longitude;
    private String latitude;
    private String address;
    private String carNumber;
    private String orderType;
    private Object orderTime;
    private Object appointmenttime;
    private float orderTotal;
    private String orderState;
    private Object account;
    private String orderAccountID;
    private Object remark;
    private String id;
    private String createdDate;
    private String updatedDate;
    private boolean deleted;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Object getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Object orderTime) {
        this.orderTime = orderTime;
    }

    public Object getAppointmenttime() {
        return appointmenttime;
    }

    public void setAppointmenttime(Object appointmenttime) {
        this.appointmenttime = appointmenttime;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Object getAccount() {
        return account;
    }

    public void setAccount(Object account) {
        this.account = account;
    }

    public String getOrderAccountID() {
        return orderAccountID;
    }

    public void setOrderAccountID(String orderAccountID) {
        this.orderAccountID = orderAccountID;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "GreateOrderResult{" +
                "orderNumber='" + orderNumber + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderTime=" + orderTime +
                ", appointmenttime=" + appointmenttime +
                ", orderTotal=" + orderTotal +
                ", orderState='" + orderState + '\'' +
                ", account=" + account +
                ", orderAccountID='" + orderAccountID + '\'' +
                ", remark=" + remark +
                ", id='" + id + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
