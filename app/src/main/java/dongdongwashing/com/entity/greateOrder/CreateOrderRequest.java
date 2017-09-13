package dongdongwashing.com.entity.greateOrder;

/**
 * Created by 沈 on 2017/5/9.
 */

public class CreateOrderRequest {

    private String isSucess;
    private CreateOrderResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public CreateOrderResult getData() {
        return data;
    }

    public void setData(CreateOrderResult data) {
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
        return "GreateOrderRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
