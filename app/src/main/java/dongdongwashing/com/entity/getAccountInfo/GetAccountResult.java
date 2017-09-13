package dongdongwashing.com.entity.getAccountInfo;

/**
 * Created by 沈 on 2017/4/28.
 */

public class GetAccountResult {

    private String id;
    private boolean enabled;
    private String userName;
    private String displayName;
    private String sex;
    private String carNumber;
    private String carModel;
    private String carColor;
    private String headPicture;
    private String mobilePhone;
    private String password;
    private Object loginType;
    private Object valicateCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getLoginType() {
        return loginType;
    }

    public void setLoginType(Object loginType) {
        this.loginType = loginType;
    }

    public Object getValicateCode() {
        return valicateCode;
    }

    public void setValicateCode(Object valicateCode) {
        this.valicateCode = valicateCode;
    }

    @Override
    public String toString() {
        return "GetAccountResult{" +
                "id='" + id + '\'' +
                ", enabled=" + enabled +
                ", userName='" + userName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", sex='" + sex + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carColor='" + carColor + '\'' +
                ", headPicture='" + headPicture + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", password='" + password + '\'' +
                ", loginType=" + loginType +
                ", valicateCode=" + valicateCode +
                '}';
    }
}
