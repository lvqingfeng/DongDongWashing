package dongdongwashing.com.entity.getUserOrder;

import java.util.List;

/**
 * Created by 沈 on 2017/5/9.
 */

public class GetUserOrderRequest {

    private String isSucess;
    private String msg;
    private List<GetUserOrderResult> data;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GetUserOrderResult> getData() {
        return data;
    }

    public void setData(List<GetUserOrderResult> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UesrOrderRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
