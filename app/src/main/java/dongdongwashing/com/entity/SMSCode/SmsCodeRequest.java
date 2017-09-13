package dongdongwashing.com.entity.SMSCode;

/**
 * Created by 沈 on 2017/4/26.
 */

public class SmsCodeRequest {

    private String isSucess;
    private SmsCodeResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public SmsCodeResult getData() {
        return data;
    }

    public void setData(SmsCodeResult data) {
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
        return "SmsCodeRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
