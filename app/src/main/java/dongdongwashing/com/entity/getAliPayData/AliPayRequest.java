package dongdongwashing.com.entity.getAliPayData;

/**
 * Created by 沈 on 2017/5/18.
 */

public class AliPayRequest {

    private String isSucess;
    private AliPayResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public AliPayResult getData() {
        return data;
    }

    public void setData(AliPayResult data) {
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
        return "AliPayRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
