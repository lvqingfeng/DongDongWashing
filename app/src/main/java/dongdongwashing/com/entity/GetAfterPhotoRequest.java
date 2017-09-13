package dongdongwashing.com.entity;

/**
 * Created by 沈 on 2017/9/8.
 */

public class GetAfterPhotoRequest {

    private String isSucess;
    private GetAfterPhotoResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public GetAfterPhotoResult getData() {
        return data;
    }

    public void setData(GetAfterPhotoResult data) {
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
        return "GetAfterPhotoRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class GetAfterPhotoResult {

        private String bihindLeft;
        private String bihindLeftBehind;
        private String bihindLeftHead;
        private String bihindLeftPicture;
        private String bihindRight;
        private String bihindRightBehind;
        private String bihindRightHead;
        private String bihindRightTail;

        public String getBihindLeft() {
            return bihindLeft;
        }

        public void setBihindLeft(String bihindLeft) {
            this.bihindLeft = bihindLeft;
        }

        public String getBihindLeftBehind() {
            return bihindLeftBehind;
        }

        public void setBihindLeftBehind(String bihindLeftBehind) {
            this.bihindLeftBehind = bihindLeftBehind;
        }

        public String getBihindLeftHead() {
            return bihindLeftHead;
        }

        public void setBihindLeftHead(String bihindLeftHead) {
            this.bihindLeftHead = bihindLeftHead;
        }

        public String getBihindLeftPicture() {
            return bihindLeftPicture;
        }

        public void setBihindLeftPicture(String bihindLeftPicture) {
            this.bihindLeftPicture = bihindLeftPicture;
        }

        public String getBihindRight() {
            return bihindRight;
        }

        public void setBihindRight(String bihindRight) {
            this.bihindRight = bihindRight;
        }

        public String getBihindRightBehind() {
            return bihindRightBehind;
        }

        public void setBihindRightBehind(String bihindRightBehind) {
            this.bihindRightBehind = bihindRightBehind;
        }

        public String getBihindRightHead() {
            return bihindRightHead;
        }

        public void setBihindRightHead(String bihindRightHead) {
            this.bihindRightHead = bihindRightHead;
        }

        public String getBihindRightTail() {
            return bihindRightTail;
        }

        public void setBihindRightTail(String bihindRightTail) {
            this.bihindRightTail = bihindRightTail;
        }

        @Override
        public String toString() {
            return "GetAfterPhotoResult{" +
                    "bihindLeft='" + bihindLeft + '\'' +
                    ", bihindLeftBehind='" + bihindLeftBehind + '\'' +
                    ", bihindLeftHead='" + bihindLeftHead + '\'' +
                    ", bihindLeftPicture='" + bihindLeftPicture + '\'' +
                    ", bihindRight='" + bihindRight + '\'' +
                    ", bihindRightBehind='" + bihindRightBehind + '\'' +
                    ", bihindRightHead='" + bihindRightHead + '\'' +
                    ", bihindRightTail='" + bihindRightTail + '\'' +
                    '}';
        }
    }
}
