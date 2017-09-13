package dongdongwashing.com.entity.changOrderState;

/**
 * Created by 沈 on 2017/5/21.
 */

public class OrderData {

    private String orderId;
    private OrderResult order;
    private String orderSate;
    private Object remark;
    private String id;
    private String createdDate;
    private String updatedDate;
    private boolean deleted;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderResult getOrder() {
        return order;
    }

    public void setOrder(OrderResult order) {
        this.order = order;
    }

    public String getOrderSate() {
        return orderSate;
    }

    public void setOrderSate(String orderSate) {
        this.orderSate = orderSate;
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
        return "OrderData{" +
                "orderId='" + orderId + '\'' +
                ", order=" + order +
                ", orderSate='" + orderSate + '\'' +
                ", remark=" + remark +
                ", id='" + id + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
