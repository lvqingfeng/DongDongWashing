package dongdongwashing.com.entity.weChatPay;

/**
 * Created by 沈 on 2017/6/1.
 */

public class WeChatPayRequest {

    private String isSucess;
    private WeChatPayResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public WeChatPayResult getData() {
        return data;
    }

    public void setData(WeChatPayResult data) {
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
        return "WeChatPayRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
