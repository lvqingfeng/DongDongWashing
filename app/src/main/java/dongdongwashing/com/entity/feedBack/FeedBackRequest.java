package dongdongwashing.com.entity.feedBack;

/**
 * Created by 沈 on 2017/5/10.
 */

public class FeedBackRequest {

    private String isSucess;
    private FeedBackResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public FeedBackResult getData() {
        return data;
    }

    public void setData(FeedBackResult data) {
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
        return "FeedBackRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
