package dongdongwashing.com.entity.getApk;

/**
 * Created by 沈 on 2017/5/17.
 */

public class ApkRequest {

    private String isSucess;
    private ApkResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public ApkResult getData() {
        return data;
    }

    public void setData(ApkResult data) {
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
        return "ApkRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
