package dongdongwashing.com.entity;

/**
 * Created by 沈 on 2017/9/8.
 */

public class GetBeforePhotoRequest {

    private String isSucess;
    private GetBeforePhotoResult data;
    private String msg;

    public String getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(String isSucess) {
        this.isSucess = isSucess;
    }

    public GetBeforePhotoResult getData() {
        return data;
    }

    public void setData(GetBeforePhotoResult data) {
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
        return "GetBeforePhotoRequest{" +
                "isSucess='" + isSucess + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public class GetBeforePhotoResult {

        private String frontLeft;
        private String frontLeftBehind;
        private String frontLeftHead;
        private String frontLeftPicture;
        private String frontRight;
        private String frontRightBehind;
        private String frontRightHead;
        private String frontRightTail;

        public String getFrontLeft() {
            return frontLeft;
        }

        public void setFrontLeft(String frontLeft) {
            this.frontLeft = frontLeft;
        }

        public String getFrontLeftBehind() {
            return frontLeftBehind;
        }

        public void setFrontLeftBehind(String frontLeftBehind) {
            this.frontLeftBehind = frontLeftBehind;
        }

        public String getFrontLeftHead() {
            return frontLeftHead;
        }

        public void setFrontLeftHead(String frontLeftHead) {
            this.frontLeftHead = frontLeftHead;
        }

        public String getFrontLeftPicture() {
            return frontLeftPicture;
        }

        public void setFrontLeftPicture(String frontLeftPicture) {
            this.frontLeftPicture = frontLeftPicture;
        }

        public String getFrontRight() {
            return frontRight;
        }

        public void setFrontRight(String frontRight) {
            this.frontRight = frontRight;
        }

        public String getFrontRightBehind() {
            return frontRightBehind;
        }

        public void setFrontRightBehind(String frontRightBehind) {
            this.frontRightBehind = frontRightBehind;
        }

        public String getFrontRightHead() {
            return frontRightHead;
        }

        public void setFrontRightHead(String frontRightHead) {
            this.frontRightHead = frontRightHead;
        }

        public String getFrontRightTail() {
            return frontRightTail;
        }

        public void setFrontRightTail(String frontRightTail) {
            this.frontRightTail = frontRightTail;
        }

        @Override
        public String toString() {
            return "GetBeforePhotoResult{" +
                    "frontLeft='" + frontLeft + '\'' +
                    ", frontLeftBehind='" + frontLeftBehind + '\'' +
                    ", frontLeftHead='" + frontLeftHead + '\'' +
                    ", frontLeftPicture='" + frontLeftPicture + '\'' +
                    ", frontRight='" + frontRight + '\'' +
                    ", frontRightBehind='" + frontRightBehind + '\'' +
                    ", frontRightHead='" + frontRightHead + '\'' +
                    ", frontRightTail='" + frontRightTail + '\'' +
                    '}';
        }
    }
}
