package dongdongwashing.com.entity.orderEvaluation;

/**
 * Created by 沈 on 2017/5/15.
 */

public class OrderEvaluationRequest {

    private String isSucess;
    private OrderEvaluationResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public OrderEvaluationResult getData() {
        return data;
    }

    public void setData(OrderEvaluationResult data) {
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
        return "OrderEvaluationRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
