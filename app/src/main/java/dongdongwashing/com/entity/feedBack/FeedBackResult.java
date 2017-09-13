package dongdongwashing.com.entity.feedBack;

/**
 * Created by 沈 on 2017/5/10.
 */

public class FeedBackResult {

    private Object telePhoneMail;
    private String des;
    private String userID;
    private Object remark;
    private String id;
    private String createdDate;
    private String updatedDate;
    private boolean deleted;

    public Object getTelePhoneMail() {
        return telePhoneMail;
    }

    public void setTelePhoneMail(Object telePhoneMail) {
        this.telePhoneMail = telePhoneMail;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "FeedBackResult{" +
                "telePhoneMail=" + telePhoneMail +
                ", des='" + des + '\'' +
                ", userID='" + userID + '\'' +
                ", remark=" + remark +
                ", id='" + id + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
