package dongdongwashing.com.entity.getOrderStateByRecord;

import java.util.List;

/**
 * Created by 沈 on 2017/5/12.
 */

public class OrderStateRequest {

    private String isSucess;
    private List<OrderStateResult> data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public List<OrderStateResult> getData() {
        return data;
    }

    public void setData(List<OrderStateResult> data) {
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
}
