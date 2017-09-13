package dongdongwashing.com.entity.getUserOrder;

/**
 * Created by 沈 on 2017/5/9.
 */

public class GetUserOrderResult {

    private AccountResult account;
    private String orderNumber;
    private String longitude;
    private String latitude;
    private String address;
    private String carNumber;
    private String orderType;
    private Object orderTime;
    private Object appointmenttime;
    private double orderTotal;
    private String orderState;
    private String orderAccountID;
    private String remark;
    private String id;
    private String createdDate;
    private String updatedDate;
    private boolean deleted;

    public AccountResult getAccount() {
        return account;
    }

    public void setAccount(AccountResult account) {
        this.account = account;
    }

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

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderAccountID() {
        return orderAccountID;
    }

    public void setOrderAccountID(String orderAccountID) {
        this.orderAccountID = orderAccountID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
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
        return "GetUserOrderResult{" +
                "account=" + account +
                ", orderNumber='" + orderNumber + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderTime=" + orderTime +
                ", appointmenttime=" + appointmenttime +
                ", orderTotal=" + orderTotal +
                ", orderState='" + orderState + '\'' +
                ", orderAccountID='" + orderAccountID + '\'' +
                ", remark='" + remark + '\'' +
                ", id='" + id + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
