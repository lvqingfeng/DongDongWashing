package dongdongwashing.com.entity;

/**
 * Created by 沈 on 2017/8/4.
 */

public class OrderStateRequest {

    private String isSucess;
    private OrderStateResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public OrderStateResult getData() {
        return data;
    }

    public void setData(OrderStateResult data) {
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
        return "OrderStateRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class OrderStateResult {

        private String id;
        private String orderState;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        @Override
        public String toString() {
            return "OrderStateResult{" +
                    "id='" + id + '\'' +
                    ", orderState='" + orderState + '\'' +
                    '}';
        }
    }
}
