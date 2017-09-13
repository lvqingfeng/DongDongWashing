package dongdongwashing.com.entity;

/**
 * Created by 沈 on 2017/8/25.
 */

public class SaveAliPayRequest {

    private String isSucess;
    private SaveAliPayResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public SaveAliPayResult getData() {
        return data;
    }

    public void setData(SaveAliPayResult data) {
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
        return "SaveAliPayRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class SaveAliPayResult {

        private String userId;
        private String orderNumber;
        private String payTranNo;
        private String total;
        private String refudDes;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getPayTranNo() {
            return payTranNo;
        }

        public void setPayTranNo(String payTranNo) {
            this.payTranNo = payTranNo;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getRefudDes() {
            return refudDes;
        }

        public void setRefudDes(String refudDes) {
            this.refudDes = refudDes;
        }

        @Override
        public String toString() {
            return "SaveAliPayResult{" +
                    "userId='" + userId + '\'' +
                    ", orderNumber='" + orderNumber + '\'' +
                    ", payTranNo='" + payTranNo + '\'' +
                    ", total='" + total + '\'' +
                    ", refudDes='" + refudDes + '\'' +
                    '}';
        }
    }
}
