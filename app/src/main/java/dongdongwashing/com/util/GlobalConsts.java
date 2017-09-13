package dongdongwashing.com.util;

/**
 * Created by 沈 on 2017/3/31.
 */

public class GlobalConsts {

    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wx01de9ada30b9757c";

    // 高德地图WEB服务的KEY
    public static final String AMAP_WEB_KEY = "80f026b4511ae3902b42ee16c8b8db40";

    public static final String DONG_DONG_IMAGE_URL = "http://api.hongkeg.com"; // 服务器的照片地址地址

    // 客服及投诉电话
    public static final String CALL_CUSTOMER_PHONE = "010-56183199";
    public static final String CALL_COMPLAINTS_PHONE = "010-56183199-01";

    /**
     * 接口数据
     */
    public static final String DONG_DONG_URL = "http://api.hongkeg.com/"; // 服务器地址
    public static final String DONG_DONG_INTRANET_URL = "http://192.168.2.147:8023/"; // 服务器内网地址

    public static final String REGISTER_CODE_URL = DONG_DONG_URL + "SMS/GetSms?telephone="; // 获取短信验证码
    public static final String REGISTER_URL = DONG_DONG_URL + "api/Account/Register"; // 注册接口
    public static final String LOGIN_URL = DONG_DONG_URL + "api/Account/CheckLogin"; // 登录接口
    public static final String UPLOAD_IMAGE_URL = DONG_DONG_URL + "api/Account/UploadFile"; // 上传头像
    public static final String UPLOAD_ACCOUNT_URL = DONG_DONG_URL + "api/Account/UpdateAccount"; // 编辑个人资料
    public static final String GET_ACCOUNT_INFO_URL = DONG_DONG_URL + "api/Account/GetAccountInfo?id="; // 获取个人资料信息
    public static final String CHANGE_PHONE_URL = DONG_DONG_URL + "api/Account/ChangePhone"; // 更改手机号码
    public static final String CHANGE_PASSWORD_URL = DONG_DONG_URL + "api/Account/ChangePassword"; // 更改密码
    public static final String CREATE_ORDER_URL = DONG_DONG_URL + "api/Order/CreateOrder"; // 生成订单
    public static final String GET_USER_ORDER_URL = DONG_DONG_URL + "api/Order/GetUserOrder?userID="; // 查询订单
    public static final String FEED_BACK_URL = DONG_DONG_URL + "api/Feedback"; // 意见反馈
    public static final String FORGET_PASSWORD_URL = DONG_DONG_URL + "api/Account/ForgetPassword"; // 忘记密码
    public static final String GET_ORDER_STATE_BY_RECORD_URL = DONG_DONG_URL + "api/Order/OrderStateRecord?orderid="; // 根据订单id查询当前订单的状态
    public static final String CREATE_ORDER_STATE_URL = DONG_DONG_URL + "api/Order/CreateOrderStateRecord"; // 修改订单的进行状态
    public static final String CREATE_ORDER_EVALUATION_URL = DONG_DONG_URL + "api/Order/CreateOrderEvaluation"; // 评价
    public static final String GET_APK_URL = DONG_DONG_URL + "api/Apk/GetApk"; // 获取服务器APK文件
    public static final String GET_ALIPAY_DATA_URL = DONG_DONG_URL + "api/AliaPay/GetPayParam"; // 获取支付宝秘钥等数据
    public static final String WE_CHAT_PAY_URL = DONG_DONG_URL + "api/WinPay/PayProduct"; // 微信支付请求获取数据
    public static final String GET_ORDER_ITEM_BY_COUNTY_URL = DONG_DONG_URL + "api/Order/GetFareaCodeProject?Latitude="; // 获取所在区县的清洗项目
    public static final String CHECK_ORDER_ITEM_BY_COUNTY_URL = DONG_DONG_URL + "api/Order/IsFareaCode?Longitude="; // 检查所在区县是否开通清洗服务
    public static final String BIND_DEVICE_TOKEN_URL = DONG_DONG_URL + "api/Umeng/BindDevicetoken"; // 绑定友盟推送token
    public static final String CANCEL_ORDER_URL = DONG_DONG_URL + "api/Order/CancelOrder"; // 用户取消订单
    public static final String GET_ORDER_STATE_BY_UID_URL = DONG_DONG_URL + "api/Order/GetUserOrderAll?userId="; // 根据用户id获取订单状态
    public static final String GET_STAFF_MSG_BY_ORDER_ID_URL = DONG_DONG_URL + "api/Order/GetOrderJiDanMessage?orderId="; // 根据订单号获取接单司机的信息
    public static final String SAVE_ALI_PAY_URL = DONG_DONG_URL + "api/AliaPay/SaveAlipay"; // 保存支付宝支付成功后的信息
    public static final String GET_SERVICE_TIME_BY_COUNTY_CODE_URL = DONG_DONG_URL + "api/StaffManager/GetServiceTime?areaCode="; // 根据区县码获取当前区域的服务时间
    public static final String GET_WASH_BEFORE_PHOTO_URL = DONG_DONG_URL + "api/Staff/GetStaffFrontPictures?orderid="; // 获取当前订单洗前的照片
    public static final String GET_WASH_AFTER_PHOTO_URL = DONG_DONG_URL + "api/Staff/GetStaffBihindPictures?orderid="; // 获取当前订单洗后的照片

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 正则验证：车牌
     */
    public static final String REGEX_CAR_NUMBER = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";

    /**
     * handler常量值
     */
    public static final int OPEN_PICTURE = 1899;
    public static final int UPLOAD_IMAGE_REQUEST = 1900;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1901;
    public static final int SUBMIT_REFUND = 1905;
    public static final int REGISTER_HANDLER = 1;
    public static final int COMMON_LOGIN_HANDLER = 2;
    public static final int FAST_LOGIN_HANDLER = 3;
    public static final int UPLOAD_IMAGE_HANDLER = 4;
    public static final int UPLOAD_ACCOUNT_HANDLER = 5;
    public static final int GET_ACCOUNT_INFO_HANDLER = 6;
    public static final int CHANGE_PHONE_HANDLER = 7;
    public static final int CHANGE_PASSWORD_HANDLER = 8;
    public static final int UPLOAD_IMAGE_ORDER_HANDLER = 9;
    public static final int CREATE_ORDER_HANDLER = 10;
    public static final int GET_USER_ORDER_HANDLER = 11;
    public static final int FEED_BACK_HANDLER = 12;
    public static final int FORGET_PASSWORD_HANDLER = 13;
    public static final int GET_ORDER_STATE_BY_RECORD_HANDLER = 14;
    public static final int CREATE_ORDER_STATE_HANDLER = 15;
    public static final int CREATE_ORDER_EVALUATION_HANDLER = 16;
    public static final int GET_APK_HANDLER = 17;
    public static final int GET_ALIPAY_DATA_HANDLER = 18;
    public static final int ALI_PAY_HANDLER = 19;
    public static final int WE_CHAT_PAY_HANDLER = 20;
    public static final int GET_ORDER_ITEM_BY_COUNTY_HANDLER = 21;
    // public static final int PUSH_NOTIFICATION_HANDLER = 22;
    public static final int BIND_DEVICE_TOKEN_HANDLER = 23;
    public static final int CHECK_ORDER_ITEM_BY_COUNTY_HANDLER = 24;
    public static final int CANCEL_ORDER_HANDLER = 25;
    public static final int GET_ORDER_STATE_BY_UID_HANDLER = 26;
    public static final int GET_STAFF_MSG_BY_ORDER_ID_HANDLER = 27;
    public static final int GET_SERVICE_CAR_HANDLER = 28;
    public static final int SAVE_ALI_PAY_HANDLER = 29;
    public static final int SUBMIT_REFUND_HANDLER = 30;
    public static final int GET_SERVICE_TIME_BY_COUNTY_CODE_HANDLER = 31;
    public static final int GET_WASH_BEFORE_PHOTO_HANDLER = 32;
    public static final int GET_WASH_AFTER_PHOTO_HANDLER = 33;

    /**
     * 正则表达式：验证手机号
     */
    //public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
    //public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$";
    public static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";
    //public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\\\d{8}$";
}
