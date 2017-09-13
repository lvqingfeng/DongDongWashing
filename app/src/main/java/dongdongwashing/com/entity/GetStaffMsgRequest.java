package dongdongwashing.com.entity;

import java.util.List;

/**
 * Created by 沈 on 2017/8/7.
 */

public class GetStaffMsgRequest {

    private String isSucess;
    private GetStaffMsgResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public GetStaffMsgResult getData() {
        return data;
    }

    public void setData(GetStaffMsgResult data) {
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
        return "GetStaffMsgRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class GetStaffMsgResult {

        private String headImage;
        private String userID;
        private String dispalyName;
        private List<String> oterItemList;
        private String evaluation;
        private String ridingTime;

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getDispalyName() {
            return dispalyName;
        }

        public void setDispalyName(String dispalyName) {
            this.dispalyName = dispalyName;
        }

        public List<String> getOterItemList() {
            return oterItemList;
        }

        public void setOterItemList(List<String> oterItemList) {
            this.oterItemList = oterItemList;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }

        public String getRidingTime() {
            return ridingTime;
        }

        public void setRidingTime(String ridingTime) {
            this.ridingTime = ridingTime;
        }

        @Override
        public String toString() {
            return "GetStaffMsgResult{" +
                    "headImage='" + headImage + '\'' +
                    ", userID='" + userID + '\'' +
                    ", dispalyName='" + dispalyName + '\'' +
                    ", oterItemList=" + oterItemList +
                    ", evaluation='" + evaluation + '\'' +
                    ", ridingTime='" + ridingTime + '\'' +
                    '}';
        }
    }
}
