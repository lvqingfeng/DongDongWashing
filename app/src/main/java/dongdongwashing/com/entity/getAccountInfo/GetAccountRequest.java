package dongdongwashing.com.entity.getAccountInfo;

/**
 * Created by 沈 on 2017/4/28.
 */

public class GetAccountRequest {

    private String isSucess;
    private GetAccountResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public GetAccountResult getData() {
        return data;
    }

    public void setData(GetAccountResult data) {
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
        return "GetAccountRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
