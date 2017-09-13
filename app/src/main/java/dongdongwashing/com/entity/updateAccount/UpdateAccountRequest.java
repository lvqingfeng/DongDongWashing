package dongdongwashing.com.entity.updateAccount;

/**
 * Created by 沈 on 2017/4/28.
 */

public class UpdateAccountRequest {

    private String isSucess;
    private UpdateAccountResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public UpdateAccountResult getData() {
        return data;
    }

    public void setData(UpdateAccountResult data) {
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
        return "UpdateAccountRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
