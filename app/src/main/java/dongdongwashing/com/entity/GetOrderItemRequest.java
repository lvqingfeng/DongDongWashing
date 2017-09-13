package dongdongwashing.com.entity;

import java.util.List;

/**
 * Created by 沈 on 2017/7/19.
 */

public class GetOrderItemRequest {

    private String isSucess;
    private List<GetOrderItemResult> data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public List<GetOrderItemResult> getData() {
        return data;
    }

    public void setData(List<GetOrderItemResult> data) {
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
        return "GetOrderItemRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class GetOrderItemResult {
        private String id;
        private String itemName;
        private String itemPrice;
        private String createdDate;
        private String itemOrder;
        private String fAareCode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getItemOrder() {
            return itemOrder;
        }

        public void setItemOrder(String itemOrder) {
            this.itemOrder = itemOrder;
        }

        public String getfAareCode() {
            return fAareCode;
        }

        public void setfAareCode(String fAareCode) {
            this.fAareCode = fAareCode;
        }

        @Override
        public String toString() {
            return "GetOrderItemResult{" +
                    "id='" + id + '\'' +
                    ", itemName='" + itemName + '\'' +
                    ", itemPrice='" + itemPrice + '\'' +
                    ", createdDate='" + createdDate + '\'' +
                    ", itemOrder='" + itemOrder + '\'' +
                    ", fAareCode='" + fAareCode + '\'' +
                    '}';
        }
    }
}
