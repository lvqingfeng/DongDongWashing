package dongdongwashing.com.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.meiyou.jet.annotation.JFindView;
import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.process.Jet;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import de.hdodenhof.circleimageview.CircleImageView;
import dongdongwashing.com.R;
import dongdongwashing.com.adapter.MyGridViewAdapter;
import dongdongwashing.com.adapter.OrderItemAdapter;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.CancelOrderRequest;
import dongdongwashing.com.entity.CheckOrderItemRequest;
import dongdongwashing.com.entity.GetOrderItemRequest;
import dongdongwashing.com.entity.GetServiceTimeRequest;
import dongdongwashing.com.entity.GetStaffMsgRequest;
import dongdongwashing.com.entity.OrderStateRequest;
import dongdongwashing.com.entity.RefundRequest;
import dongdongwashing.com.entity.ServiceCarRequest;
import dongdongwashing.com.entity.getAccountInfo.GetAccountRequest;
import dongdongwashing.com.entity.getAccountInfo.GetAccountResult;
import dongdongwashing.com.entity.uploadImage.UploadImageRequest;
import dongdongwashing.com.ui.CustomerService.CustomerServiceActivity;
import dongdongwashing.com.ui.Integral.IntegralActivity;
import dongdongwashing.com.ui.Order.OrderActivity;
import dongdongwashing.com.ui.Order.OrderDetermineActivity;
import dongdongwashing.com.ui.PersonalCenter.EditProfileActivity;
import dongdongwashing.com.ui.PersonalCenter.LoginActivity;
import dongdongwashing.com.ui.Settings.SettingsActivity;
import dongdongwashing.com.ui.Wallet.WalletActivity;
import dongdongwashing.com.util.AMapUtil;
import dongdongwashing.com.util.BigImgActivity;
import dongdongwashing.com.util.DataConversionByShen;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.DialogByTwoButton;
import dongdongwashing.com.util.Downloader;
import dongdongwashing.com.util.GlobalConsts;
import dongdongwashing.com.util.ImageUtils;
import dongdongwashing.com.util.IsCameraCanUse;
import dongdongwashing.com.util.ScreenUtils;
import dongdongwashing.com.util.UmengUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener, LocationSource, AMapLocationListener, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, AMap.InfoWindowAdapter {

    public static final String TAG = "test";
    @JFindView(R.id.Tool_Bar)
    @JFindViewOnClick(R.id.Tool_Bar)
    private Toolbar toolBar;
    @JFindView(R.id.personal_center_iv)
    @JFindViewOnClick(R.id.personal_center_iv)
    private ImageView personalCenterIv; // 个人信息中心
    @JFindView(R.id.message_iv)
    @JFindViewOnClick(R.id.message_iv)
    private ImageView messageIv; // 消息中心
    private TextView infoWindowTv; // 图钉显示自定义文字
    private boolean isHaveService = true; // 所在区域是否开通了服务
    private String countyCode; // 获取区县码
    private int startTime, endTime, mHour, reservation;
    private MapView mapView; // 高德地图
    private Marker screenMarker = null; // 图钉
    private CameraPosition mCameraPosition;
    private AMap aMap; // 地图控制器

    private List<ServiceCarRequest.ServiceCarResult> serviceCarResult = new ArrayList<>(); // 接收开始工作的司机位置信息
    private List<LatLng> carsLatLng = new ArrayList<>(); // 车辆的经纬度
    private MarkerOptions carMarker;
    private double lng = 0.0;
    private double lat = 0.0;

    private View personalCenterView, infoWindow; // 左侧个人中心
    private PopupWindow personalCenterPW; // 左侧个人中心的弹窗
    private CircleImageView myHead; // 设置头像
    private TextView loginTv; // 登录
    private RelativeLayout orderRl, walletRl, serviceRl, settingsRl;
    private RelativeLayout integralRl; // 积分商城
    private RelativeLayout shareRl; // 分享
    private UMShareListener mShareListener; // 分享的监听
    private ShareAction mShareAction; // 分享弹窗
    private UMImage image;
    private SharedPreferences editUserInfoSP;
    private String userNameString, numberPlateString, headPictureString, phoneString;
    @JFindView(R.id.map_location_iv)
    @JFindViewOnClick(R.id.map_location_iv)
    private ImageView mapLocationIv; // 定位
    @JFindView(R.id.now_btn)
    @JFindViewOnClick(R.id.now_btn)
    private RadioButton nowBtn; // 现在按钮
    @JFindView(R.id.appointment_btn)
    @JFindViewOnClick(R.id.appointment_btn)
    private RadioButton appointmentBtn; // 预约按钮
    @JFindView(R.id.show_check_time_rl)
    private RelativeLayout showCheckTimeRl; // 弹出的预约时间页面
    @JFindView(R.id.line2)
    private TextView line2;
    @JFindView(R.id.appointment_time_tv)
    @JFindViewOnClick(R.id.appointment_time_tv)
    private TextView appointmentTimeTv; // 选择时间以及显示的控件
    @JFindView(R.id.main_my_address_tv)
    @JFindViewOnClick(R.id.main_my_address_tv)
    private TextView mainMyAddressTv; // 输入我的位置
    @JFindView(R.id.main_number_plate_et)
    private EditText mainNumberPlateEt; // 输入我的车牌
    @JFindView(R.id.call_service_btn)
    @JFindViewOnClick(R.id.call_service_btn)
    private Button callServiceBtn; // 呼叫服务
    @JFindView(R.id.cancel_service_btn)
    @JFindViewOnClick(R.id.cancel_service_btn)
    private Button cancelServiceBtn; // 取消服务
    @JFindView(R.id.main_ll1)
    private LinearLayout mainLl1; // 现在及预约下单复选框布局
    @JFindView(R.id.main_ll2)
    private LinearLayout mainLl2; // 地址及车牌信息布局
    @JFindView(R.id.main_ll3)
    private RelativeLayout mainLl3; // 接单后司机信息布局
    @JFindView(R.id.staff_by_order_head)
    private CircleImageView staffByOrderHead; // 司机头像
    @JFindView(R.id.staff_name_by_order)
    private TextView staffNameByOrder; // 司机姓名
    @JFindView(R.id.staff_id_by_order)
    private TextView staffIdByOrder; // 司机编号
    @JFindView(R.id.staff_by_order_iv1)
    private ImageView staffByOrderIv1; // 司机的评价等级
    @JFindView(R.id.staff_by_order_iv2)
    private ImageView staffByOrderIv2;
    @JFindView(R.id.staff_by_order_iv3)
    private ImageView staffByOrderIv3;
    @JFindView(R.id.staff_by_order_iv4)
    private ImageView staffByOrderIv4;
    @JFindView(R.id.staff_by_order_iv5)
    private ImageView staffByOrderIv5;
    @JFindView(R.id.staff_by_order_speed)
    private TextView staffByOrderSpeed; // 司机到达现场时间
    @JFindView(R.id.staff_by_order_item)
    private TextView staffByOrderItem; // 清洗的项目
    @JFindView(R.id.staff_by_order_state)
    private TextView staffByOrderState; // 订单状态

    private String OrdeWay; // 订单类型 1为普通订单 2为预约订单
    private String appointmentTimeString; // 预约时间
    private String mainMyAddressString; // 我的位置
    private String mainNumberPlateString; // 车牌
    private String id, orderNumber;
    private DialogByOneButton dialog;
    private DialogByTwoButton dialog2, dialogByApk;
    private DialogByProgress dialogByProgress;

    private View CancelOrderView;
    private PopupWindow CancelOrderPW; // 取消订单弹窗
    private String cancelTypeString = ""; // 点击类型的字符串
    private CheckBox cancelCB1, cancelCB2, cancelCB3, cancelCB4, cancelCB5, cancelCB6;
    private EditText cancelEt;

    private View serviceModeView; // 服务车型的弹窗布局
    private PopupWindow serviceModePW;
    private View washingItemView; // 清洗项目的订单布局
    private PopupWindow washingItemPW;
    private RecyclerView orderItemRV; // 清洗项目的布局
    private List<GetOrderItemRequest.GetOrderItemResult> getOrderItemResultList; // 获取所在区域的清洗项目的数据
    private OrderItemAdapter orderItemAdapter; // 清洗项目的适配器

    private String orderId = ""; // 接收友盟推送的消息
    private String orderState = ""; // 接收友盟推送的消息

    private SharedPreferences serviceModelSp, spfById;
    private EditText remarksEt; // 备注输入框
    private ImageView cameraIv; // 拍照
    private Button orderItemCancelBtn, orderItemDetermineBtn; // 确定与取消
    private List<String> orderItemIdList = new ArrayList<>(); // 清洗项目集合
    private double finalOrderItemPrice; // 清洗项目的总价钱
    private String appointmentString;
    private String latitudeString, longitudeString; // 生成订单后获取最后的经纬度

    private List<String> orderItemList = new ArrayList<>(); // 清洗项目文字集合
    private String orderString;
    private String orderItemString; // 最终的清洗项目数据
    private String orderItemType;

    public static String SDPATH = Environment.getExternalStorageDirectory() + "/hsyfm/recordimg/";
    public static File destDir = new File(SDPATH);
    private boolean isShowingPW;
    private View cameraView; // 拍照的布局
    private PopupWindow cameraPW; // 拍照的PopupWindow
    private TextView cameraCancelBtn, cameraDetermineBtn; // 取消保存按钮
    private GridView cameraGridView; // 图片加载器
    private Bitmap bmp; // 导入临时图片
    private List<String> aboutCarImage = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> imageItem; // 照片集合
    private List<String> imageList = new ArrayList<>(); // 外网可查看的图片集合
    private MyGridViewAdapter adapter;
    private Button realLocationCamera, realLocationAlbum, realLocationCancel;
    private PopupWindow displayPW;
    private String imagePath; // 选择图片路径
    private String imageString; // 上传图片成功后的图片路径
    private File imageFile; // 保存图片的文件

    private long mExitTime;
    private SearchBroadCastReceiver searchBroadCastReceiver; // 根据搜索的地址返回的广播接收者
    private OrderAcceptBroadCastReceiver orderAcceptBroadCastReceiver;
    private OkHttpClient okHttpClient = new OkHttpClient();

    // 经纬度，城市
    private String city;
    private double Latitude, Longitude; // 获取经度、纬度

    // 移动图钉获取定位的地址信息（逆地理）
    private LatLonPoint latLonPoint;
    private GeocodeSearch geocoderSearch; // 根据图钉获取位置信息
    private ExecutorService mExecutorService;

    private Marker geoMarker;
    private GeocodeSearch geocoderSearchAddress; // 根据位置信息移动图钉
    private String addressName;

    // 定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器

    // 标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    private ArrayList<String> daysList = new ArrayList<>(); // 日期的集合
    private ArrayList<String> hours;
    private ArrayList<ArrayList<String>> hoursList = new ArrayList<>(); // 小时的集合
    ArrayList<String> minute;
    ArrayList<List<String>> minutes;
    ArrayList<List<List<String>>> minutesList = new ArrayList<>(); // 分钟的集合

    private OptionsPickerView showDateOptions; // 显示时间选择器
    private String versionName, newVersionName, apkUrl;
    private Downloader downloadAPK;

    private static final int TAKE_PICTURES_UPLOAD = 1901, ALBUM_UPLOAD = 1902, MY_PERMISSIONS_REQUEST_CALL_PHONE = 1903, MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1904;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.UPLOAD_IMAGE_ORDER_HANDLER: // 上传图片
                    UploadImageRequest uploadImageRequest = (UploadImageRequest) msg.obj;
                    HideDialogByProgress();
                    String uploadImageString = uploadImageRequest.getErrmsg();
                    if (uploadImageString.equals("上传成功")) {
                        imageString = uploadImageRequest.getFileName(); // 获取图片上传后的路径
                        imagePath = GlobalConsts.DONG_DONG_IMAGE_URL + imageString;
                        if (aboutCarImage != null && !aboutCarImage.isEmpty()) {
                            aboutCarImage.add(imagePath);
                            adapter = new MyGridViewAdapter(aboutCarImage, MainActivity.this);
                            cameraGridView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            imageList.add(0, imagePath);
                            adapter = new MyGridViewAdapter(imageList, MainActivity.this);
                            cameraGridView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;

                case GlobalConsts.GET_ACCOUNT_INFO_HANDLER: // 获取用户信息
                    GetAccountRequest getAccountRequest = (GetAccountRequest) msg.obj;
                    HideDialogByProgress();
                    String getAccountStr = getAccountRequest.getMsg();
                    if (getAccountStr.equals("用户信息")) {
                        GetAccountResult getAccountResult = getAccountRequest.getData();
                        headPictureString = getAccountResult.getHeadPicture();
                        userNameString = getAccountResult.getUserName();
                        numberPlateString = getAccountResult.getCarNumber();
                        phoneString = getAccountResult.getMobilePhone();
                        if (numberPlateString != null && !"".equals(numberPlateString)) { // 车牌号
                            mainNumberPlateEt.setText(numberPlateString);
                        } else {
                            mainNumberPlateEt.setText("");
                        }
                    }
                    break;

                case GlobalConsts.GET_ORDER_ITEM_BY_COUNTY_HANDLER: // 根据所在区县获取服务项目
                    GetOrderItemRequest getOrderItemRequest = (GetOrderItemRequest) msg.obj;
                    HideDialogByProgress();
                    String getOrderItemState = getOrderItemRequest.getIsSucess();
                    if (getOrderItemState.equals("true")) {
                        getOrderItemResultList = getOrderItemRequest.getData();
                        orderItemAdapter = new OrderItemAdapter(MainActivity.this, getOrderItemResultList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        layoutManager.setOrientation(OrientationHelper.VERTICAL);
                        orderItemRV.setLayoutManager(layoutManager);
                        orderItemRV.setAdapter(orderItemAdapter);
                        orderItemRV.setItemAnimator(new DefaultItemAnimator());
                    }
                    break;

                case GlobalConsts.CHECK_ORDER_ITEM_BY_COUNTY_HANDLER: // 检查所在区域有未开通服务
                    CheckOrderItemRequest checkOrderItemRequest = (CheckOrderItemRequest) msg.obj;
                    Log.d(TAG, "获取当前区域的区县码------------" + checkOrderItemRequest.toString());
                    String checkState = checkOrderItemRequest.getIsSucess();
                    if (checkState.equals("true")) {
                        isHaveService = true;
                        countyCode = checkOrderItemRequest.getData();
                        if (countyCode != null && !TextUtils.isEmpty(countyCode)) {
                            getServiceTimeByCountyCode(); // 根据区县码获取当前区域的服务时间
                        }
                    } else if (checkState.equals("false")) {
                        isHaveService = false;
                        countyCode = "";
                    }
                    break;

                case GlobalConsts.CANCEL_ORDER_HANDLER: // 订单取消
                    CancelOrderRequest cancelOrderRequest = (CancelOrderRequest) msg.obj;
                    HideDialogByProgress();
                    if (CancelOrderPW != null && CancelOrderPW.isShowing()) {
                        CancelOrderPW.dismiss();
                    }
                    String cancelOrderMsg = cancelOrderRequest.getMsg();
                    if (cancelOrderMsg.equals("取消成功!")) {
                        dialog = new DialogByOneButton(MainActivity.this, "提示", "订单取消成功", "确定");
                        dialog.show();
                        dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog.dismiss();
                                hideCancelBtn();
                                getOrderStateByUid();
                                submitRefund(); // 提交退款申请
                            }
                        });
                    }
                    break;

                case GlobalConsts.SUBMIT_REFUND_HANDLER: // 提交退款申请
                    RefundRequest refundRequest = (RefundRequest) msg.obj;
                    String refundString = refundRequest.getIsSucess();
                    if (refundString.equals("true")) {
                        dialog = new DialogByOneButton(MainActivity.this, "提示", "正在为您办理退款，请耐心等候", "确定");
                        dialog.show();
                        dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog.dismiss();
                            }
                        });
                    }
                    break;

                case GlobalConsts.GET_ORDER_STATE_BY_UID_HANDLER: // 根据用户id获取订单的状态
                    OrderStateRequest orderStateRequest = (OrderStateRequest) msg.obj;
                    String orderStateStr = orderStateRequest.getIsSucess();
                    if (orderStateStr.equals("true")) {
                        OrderStateRequest.OrderStateResult orderStateResult = orderStateRequest.getData();
                        orderId = orderStateResult.getId();
                        orderState = orderStateResult.getOrderState();
                        if ("1".equals(orderState)) {
                            hideCancelBtn();
                        } else if ("2".equals(orderState)) {
                            showCancelBtn();
                        } else if ("3".equals(orderState)) {
                            showOrderByStaff();
                            getStaffMsgByOrderId(orderId); // 根据订单id获取司机的信息
                        } else if ("4".equals(orderState)) {
                            showOrderByStaff();
                            getStaffMsgByOrderId(orderId); // 根据订单id获取司机的信息
                        } else if ("5".equals(orderState)) {
                            hideCancelBtn();
                        } else if ("6".equals(orderState)) {
                            hideCancelBtn();
                        }
                    } else {
                        hideCancelBtn();
                        orderState = "";
                    }
                    break;

                case GlobalConsts.GET_STAFF_MSG_BY_ORDER_ID_HANDLER:
                    GetStaffMsgRequest getStaffMsgRequest = (GetStaffMsgRequest) msg.obj;
                    String getStaffMsgState = getStaffMsgRequest.getIsSucess();
                    if (getStaffMsgState.equals("true")) {
                        GetStaffMsgRequest.GetStaffMsgResult getStaffMsgResult = getStaffMsgRequest.getData();
                        String staffByOrderItemStr = String.valueOf(getStaffMsgResult.getOterItemList());
                        Glide.with(MainActivity.this).load(GlobalConsts.DONG_DONG_IMAGE_URL + getStaffMsgResult.getHeadImage()).into(staffByOrderHead);
                        String[] staffByOrderSpeedStr = getStaffMsgResult.getRidingTime().split("\\.");
                        staffNameByOrder.setText(getStaffMsgResult.getDispalyName());
                        staffIdByOrder.setText(getStaffMsgResult.getUserID());
                        staffByOrderSpeed.setText(staffByOrderSpeedStr[0].trim() + " 分钟"); // 司机到达现场时间
                        staffByOrderItem.setText(staffByOrderItemStr.substring(1, staffByOrderItemStr.length() - 1)); // 清洗的项目
                        staffByOrderState.setText("已接单");
                        if ("1".equals(getStaffMsgResult.getEvaluation())) {
                            staffByOrderIv1.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv2.setImageResource(R.drawable.evaluation_un);
                            staffByOrderIv3.setImageResource(R.drawable.evaluation_un);
                            staffByOrderIv4.setImageResource(R.drawable.evaluation_un);
                            staffByOrderIv5.setImageResource(R.drawable.evaluation_un);
                        } else if ("2".equals(getStaffMsgResult.getEvaluation())) {
                            staffByOrderIv1.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv2.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv3.setImageResource(R.drawable.evaluation_un);
                            staffByOrderIv4.setImageResource(R.drawable.evaluation_un);
                            staffByOrderIv5.setImageResource(R.drawable.evaluation_un);
                        } else if ("3".equals(getStaffMsgResult.getEvaluation())) {
                            staffByOrderIv1.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv2.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv3.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv4.setImageResource(R.drawable.evaluation_un);
                            staffByOrderIv5.setImageResource(R.drawable.evaluation_un);
                        } else if ("4".equals(getStaffMsgResult.getEvaluation())) {
                            staffByOrderIv1.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv2.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv3.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv4.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv5.setImageResource(R.drawable.evaluation_un);
                        } else if ("5".equals(getStaffMsgResult.getEvaluation())) {
                            staffByOrderIv1.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv2.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv3.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv4.setImageResource(R.drawable.evaluation_c);
                            staffByOrderIv5.setImageResource(R.drawable.evaluation_c);
                        }
                    }
                    break;

                case GlobalConsts.GET_SERVICE_CAR_HANDLER:
                    ServiceCarRequest serviceCarRequest = (ServiceCarRequest) msg.obj;
                    String serviceCarString = serviceCarRequest.getMsg();
                    if (serviceCarString.equals("服务人员例表")) {
                        serviceCarResult = serviceCarRequest.getData();
                        for (int i = 0; i < serviceCarResult.size(); i++) {
                            carsLatLng.add(new LatLng(Double.parseDouble(serviceCarResult.get(i).getLatitude()), Double.parseDouble(serviceCarResult.get(i).getLongitude())));
                        }

                        for (int j = 0; j < carsLatLng.size(); j++) {
                            carMarker = new MarkerOptions();
                            lng = Double.valueOf(carsLatLng.get(j).longitude);
                            lat = Double.valueOf(carsLatLng.get(j).latitude);
                            carMarker.position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.sevicer_car));
                            aMap.addMarker(carMarker);
                        }
                    }
                    break;

                case GlobalConsts.GET_SERVICE_TIME_BY_COUNTY_CODE_HANDLER:
                    GetServiceTimeRequest getServiceTimeRequest = (GetServiceTimeRequest) msg.obj;
                    String getServiceTimeState = getServiceTimeRequest.getIsSucess();
                    if (TextUtils.equals("true", getServiceTimeState)) {
                        GetServiceTimeRequest.GetServiceTimeResult getServiceTimeResult = getServiceTimeRequest.getData();
                        startTime = getServiceTimeResult.getStartTime();
                        endTime = getServiceTimeResult.getEndTime();
                        showTripPickerView(); // 显示预约时间的选择器
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Jet.bind(this);

        dialogByProgress = new DialogByProgress(MainActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        imageFile = new File(destDir, getPhotoFileName());
        spfById = MainActivity.this.getSharedPreferences("user_info", 0);
        id = spfById.getString("uid", "");
        if (!TextUtils.isEmpty(id)) {
            getUserInfoById(id);
            getOrderStateByUid(); // 获取当前用户是否有订单以及当前的订单状态
        }

        versionName = DataConversionByShen.getVersionName(MainActivity.this, versionName); // 获取app版本号比较并获取更新链接下载更新
        Intent intent = getIntent();
        newVersionName = intent.getStringExtra("newVersionName");
        apkUrl = intent.getStringExtra("apkUrl");
        downloadApk(versionName, newVersionName, apkUrl);

        mapView = (MapView) findViewById(R.id.map_View);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
        aMap.getUiSettings().setMyLocationButtonEnabled(true); // 是否显示定位按钮
        aMap.getUiSettings().setZoomControlsEnabled(false); // 取消右侧放大缩小按钮
        aMap.getUiSettings().setRotateGesturesEnabled(false); // 取消地图手势旋转
        MyLocationStyle myLocationStyle = new MyLocationStyle(); // 设置定位显示的小图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE); // 定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter();
                aMap.moveCamera(CameraUpdateFactory.zoomTo(14));  // 设置缩放级别
            }
        });

        // 添加自定义的图钉并可拖拽定位
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(Latitude, Longitude));
        markerOptions.visible(true);
        aMap.addMarker(markerOptions);
        aMap.setOnCameraChangeListener(this);

        // 根据经纬度获取地址
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        geocoderSearchAddress = new GeocodeSearch(MainActivity.this);

        imageItem = new ArrayList<HashMap<String, Object>>(); // TODO 通过适配器实现，载入默认图片添加图片加号，SimpleAdapter参数imageItem为数据源 R.layout.griditem_addpic为照片的布局
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        imageList = new ArrayList<String>();
        imageList.add(getResources().getResourceName(R.layout.griditem_addpic));

        mShareListener = new CustomShareListener(this); // TODO 友盟分享
        image = new UMImage(MainActivity.this, R.drawable.um_share);
        image.setThumb(image);
        UMWeb web = new UMWeb("http://sj.qq.com/myapp/detail.htm?apkName=dongdongwashing.com");
        web.setTitle("动动美车");
        web.setThumb(image);
        web.setDescription("动动美车分享");
        mShareAction = new ShareAction(MainActivity.this).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ).withMedia(web).setCallback(mShareListener);
    }

    /**
     * 根据id获取用户资料并赋值
     */
    private void getUserInfoById(String id) {
        dialogByProgress.show();
        Request request = new Request.Builder().url(GlobalConsts.GET_ACCOUNT_INFO_URL + id).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson getAccountGson = new Gson();
                GetAccountRequest getAccountRequest = getAccountGson.fromJson(bodyString, GetAccountRequest.class);
                Message getAccountMsg = Message.obtain();
                getAccountMsg.what = GlobalConsts.GET_ACCOUNT_INFO_HANDLER;
                getAccountMsg.obj = getAccountRequest;
                handler.sendMessage(getAccountMsg);
            }
        });
    }

    /**
     * 在屏幕中心添加一个Marker,当地图移动式图钉不动
     */
    private void addMarkerInScreenCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        screenMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromResource(R.mipmap.purple_pin)));
        screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y); // 设置Marker在屏幕上,不跟随地图移动
        aMap.setInfoWindowAdapter(this); // 设置自定义气泡
        startJumpAnimation(); // 图钉跳动动画
    }

    /**
     * 显示附近已经开始工作的服务人员车辆信息
     */
    private void addServiceCar(String longitude, String latitude) {
        Request request = new Request.Builder().url(GlobalConsts.DONG_DONG_URL + "api/Staff/GetTodayWorkUser?lon=" + longitude + "&lat=" + latitude).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson serviceCarGson = new Gson();
                ServiceCarRequest serviceCarRequest = serviceCarGson.fromJson(bodyString, ServiceCarRequest.class);
                Message serviceCarMsg = Message.obtain();
                serviceCarMsg.what = GlobalConsts.GET_SERVICE_CAR_HANDLER;
                serviceCarMsg.obj = serviceCarRequest;
                handler.sendMessage(serviceCarMsg);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        spfById = MainActivity.this.getSharedPreferences("user_info", 0);
        id = spfById.getString("uid", "");
        if (!TextUtils.isEmpty(id)) {
            getUserInfoById(id);
            getOrderStateByUid();
        } else {
            mainNumberPlateEt.setText(""); // 车牌
            loginTv.setText("登录/注册"); // 登录或者昵称
            myHead.setBackgroundResource(R.mipmap.head);
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this.getApplicationContext()); // 初始化AMapLocationClient，并绑定监听
            mLocationClient.setLocationListener(this);
            mLocationOption = new AMapLocationClientOption(); // 初始化定位参数
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy); // 设置定位精度
            mLocationOption.setMockEnable(true);  // 是否允许模拟位置
            mLocationOption.setNeedAddress(true);  // 是否返回地址信息
            mLocationOption.setOnceLocation(true); // 是否只定位一次
            mLocationOption.setWifiActiveScan(true); // 设置是否强制刷新WIFI，默认为强制刷新
            // mLocationOption.setInterval(2000); // 定位时间间隔
            mLocationClient.setLocationOption(mLocationOption); // 给定位客户端对象设置定位参数
            mLocationClient.startLocation();
        }
    }

    /**
     * 定位后的回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                aMapLocation.getLocationType(); // 获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getAddress(); // 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息
                Longitude = aMapLocation.getLongitude(); // 获取经度
                Latitude = aMapLocation.getLatitude(); // 获取纬度
                Log.d(TAG, "初始化地图时的定位经纬度---------" + Longitude + "------------" + Latitude);
                city = aMapLocation.getCity(); // 获取所在城市
                if (isFirstLoc) { // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                    mListener.onLocationChanged(aMapLocation); // TODO 点击定位按钮 能够将地图的中心移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()))); // 将地图移动到定位点
                    StringBuffer addressBuffer = new StringBuffer(); // 获取定位信息
                    addressBuffer.append(aMapLocation.getProvince() + "" + aMapLocation.getDistrict() + "" + aMapLocation.getStreet() + "" + aMapLocation.getStreetNum());
                    mainMyAddressTv.setText(addressBuffer);
                    addServiceCar(String.valueOf(Longitude), String.valueOf(Latitude)); // 获取当前区域一开始工作的服务人员的地址信息
                    isFirstLoc = false;
                }
            } else { // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("test", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                String errorCodeString = String.valueOf(aMapLocation.getErrorCode());
                if (errorCodeString.equals("12")) { // 定位权限
                    dialog2 = new DialogByTwoButton(MainActivity.this, "提示", "定位失败，请查看定位权限是否授权", "取消", "确定");
                    dialog2.show();
                    dialog2.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                        @Override
                        public void doNegative() {
                            dialog2.dismiss();
                        }

                        @Override
                        public void doPositive() {
                            dialog2.dismiss();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent);
                        }
                    });
                } else if (errorCodeString.equals("13") | errorCodeString.equals("2") | errorCodeString.equals("4")) {
                    dialog2 = new DialogByTwoButton(MainActivity.this, "提示", "定位失败，请检查网络后重试", "取消", "确定");
                    dialog2.show();
                    dialog2.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                        @Override
                        public void doNegative() {
                            dialog2.dismiss();
                        }

                        @Override
                        public void doPositive() {
                            dialog2.dismiss();
                            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                        }
                    });
                }
            }
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
    }

    /**
     * 注册广播接收者
     */
    @Override
    protected void onStart() {
        super.onStart();
        searchBroadCastReceiver = new SearchBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SEARCH_ADDRESS");
        MainActivity.this.registerReceiver(searchBroadCastReceiver, filter);

        orderAcceptBroadCastReceiver = new OrderAcceptBroadCastReceiver();
        IntentFilter orderFilter = new IntentFilter();
        orderFilter.addAction("ORDER_TO_BE");
        orderFilter.addAction("ORDER_ACCEPT");
        orderFilter.addAction("ORDER_PROCESSING");
        orderFilter.addAction("ORDER_COMPLETED");
        MainActivity.this.registerReceiver(orderAcceptBroadCastReceiver, orderFilter);
    }

    /**
     * 在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        // MobclickAgent.onPause(this); // 友盟统计
    }

    /**
     * 在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        // MobclickAgent.onResume(this); // 友盟统计
    }

    /**
     * 在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
        if (mExecutorService != null) {
            mExecutorService.shutdownNow();
        }
        MainActivity.this.unregisterReceiver(searchBroadCastReceiver);
        MainActivity.this.unregisterReceiver(orderAcceptBroadCastReceiver);
    }

    /**
     * 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 监听方法
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.now_btn: // 现在按钮
                OrdeWay = "1";
                nowBtn.setChecked(true);
                appointmentBtn.setChecked(false);
                nowBtn.setBackgroundResource(R.drawable.map_check_button_shape);
                appointmentBtn.setBackgroundResource(R.drawable.map_uncheck_button_shape);
                showCheckTimeRl.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                callServiceBtn.setText("呼叫服务");
                break;

            case R.id.appointment_btn: // 预约按钮
                OrdeWay = "2";
                nowBtn.setChecked(false);
                appointmentBtn.setChecked(true);
                nowBtn.setBackgroundResource(R.drawable.map_uncheck_button_shape);
                appointmentBtn.setBackgroundResource(R.drawable.map_check_button_shape);
                showCheckTimeRl.setVisibility(View.VISIBLE);
                line2.setVisibility(View.VISIBLE);
                callServiceBtn.setText("预约服务");
                break;

            case R.id.appointment_time_tv: // 预约时间选择器
                showDateOptions.show();
                break;

            case R.id.main_my_address_tv: // 位置栏点击
                Intent intent1 = new Intent(MainActivity.this, PoiKeywordSearchActivity.class);
                intent1.putExtra("city", city);
                intent1.putExtra("positionMessage", mainMyAddressTv.getText().toString().trim());
                if (Build.VERSION.SDK_INT >= 21) {
                    startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, mainMyAddressTv, "shareNames").toBundle());
                } else {
                    startActivity(intent1);
                }
                break;

            case R.id.personal_center_iv: // 显示个人中心的侧边栏
                showPersonalPW();
                break;

            case R.id.message_iv: // 消息通知中心
                startActivity(new Intent(MainActivity.this, InformationActivity.class));
                break;

            case R.id.call_service_btn: // 呼叫服务
                SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
                mHour = Integer.parseInt(sdf.format(new Date())); // 获取系统当前小时数
                mHour++;
                if (TextUtils.isEmpty(id)) {
                    dialog2 = new DialogByTwoButton(MainActivity.this, "提示", "请先登录账号", "取消", "确定");
                    dialog2.show();
                    dialog2.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                        @Override
                        public void doNegative() {
                            dialog2.dismiss();
                        }

                        @Override
                        public void doPositive() {
                            dialog2.dismiss();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });
                    return;
                } else if (isHaveService == false) {
                    dialog = new DialogByOneButton(MainActivity.this, "提示", "当前区域内暂未开通清洗服务", "确定");
                    dialog.show();
                    dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                        @Override
                        public void doPositive() {
                            dialog.dismiss();
                        }
                    });
                    return;
                } else if (nowBtn.isChecked() == true) {
                    OrdeWay = "1";
                    mainMyAddressString = mainMyAddressTv.getText().toString().trim(); // 我的位置
                    mainNumberPlateString = mainNumberPlateEt.getText().toString().trim(); // 车牌
                    if ("".equals(mainMyAddressString)) {
                        Toast.makeText(MainActivity.this, "当前位置无法获取，请检查网络后重试", Toast.LENGTH_SHORT).show();
                        return;
                    } else if ("".equals(mainNumberPlateString)) {
                        Toast.makeText(MainActivity.this, "请输入您的车牌号", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (DataConversionByShen.isCarNumberNO(mainNumberPlateString) == false) {
                        Toast.makeText(MainActivity.this, "请输入正确的车牌号", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (mHour < startTime | mHour > endTime) {
                        Toast.makeText(MainActivity.this, "当前区域服务时间为" + startTime + ":00" + " ~ " + endTime + ":00" + "， 如需要服务请选择预约下单", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        serviceModelSp = MainActivity.this.getSharedPreferences("service_model", 0);
                        String serviceString = serviceModelSp.getString("model", "");
                        if (serviceString.equals("true")) {
                            showOrderPW(); // 弹出清洗项目的弹窗
                        } else {
                            showServiceModelPW(); // 弹出服务车型弹窗
                        }
                    }
                } else if (appointmentBtn.isChecked() == true) {
                    OrdeWay = "2";
                    appointmentTimeString = appointmentTimeTv.getText().toString().trim(); // 预约时间
                    mainMyAddressString = mainMyAddressTv.getText().toString().trim(); // 我的位置
                    mainNumberPlateString = mainNumberPlateEt.getText().toString().trim(); // 车牌
                    String[] appointmentDay = appointmentTimeString.split(":");
                    String appointmentTime1 = appointmentDay[0].trim(); // 日期
                    String appointmentTime2 = appointmentDay[1].trim();
                    reservation = Integer.parseInt(appointmentTime2.substring(0, appointmentTime2.length() - 1));
                    if (TextUtils.equals("请选择预约时间", appointmentTimeString)) {
                        Toast.makeText(MainActivity.this, "请选择预约洗车的时间", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.equals("今天", appointmentTime1) && mHour > reservation) { // 当前时间小于预约时间
                        Toast.makeText(MainActivity.this, "预约下单时间不能小于当前的时间", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(mainMyAddressString)) {
                        Toast.makeText(MainActivity.this, "当前位置无法获取，请检查网络后重试", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(mainNumberPlateString)) {
                        Toast.makeText(MainActivity.this, "请输入您的车牌号", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (DataConversionByShen.isCarNumberNO(mainNumberPlateString) == false) {
                        Toast.makeText(MainActivity.this, "请输入正确的车牌号", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        serviceModelSp = MainActivity.this.getSharedPreferences("service_model", 0);
                        String serviceString = serviceModelSp.getString("model", "");
                        if (TextUtils.equals("true", serviceString)) {
                            showOrderPW(); // 弹出清洗项目的弹窗
                        } else {
                            showServiceModelPW(); // 弹出服务车型弹窗
                        }
                    }
                }
                break;

            case R.id.cancel_service_btn: // 取消服务
                ShowCancelSinglePW(); // 取消订单服务的弹窗
                break;

            case R.id.cancel_cb_1:
                cancelCB2.setChecked(false);
                cancelCB3.setChecked(false);
                cancelCB4.setChecked(false);
                cancelCB5.setChecked(false);
                cancelCB6.setChecked(false);
                break;

            case R.id.cancel_cb_2:
                cancelCB1.setChecked(false);
                cancelCB3.setChecked(false);
                cancelCB4.setChecked(false);
                cancelCB5.setChecked(false);
                cancelCB6.setChecked(false);
                break;

            case R.id.cancel_cb_3:
                cancelCB1.setChecked(false);
                cancelCB2.setChecked(false);
                cancelCB4.setChecked(false);
                cancelCB5.setChecked(false);
                cancelCB6.setChecked(false);
                break;

            case R.id.cancel_cb_4:
                cancelCB1.setChecked(false);
                cancelCB2.setChecked(false);
                cancelCB3.setChecked(false);
                cancelCB5.setChecked(false);
                cancelCB6.setChecked(false);
                break;

            case R.id.cancel_cb_5:
                cancelCB1.setChecked(false);
                cancelCB2.setChecked(false);
                cancelCB3.setChecked(false);
                cancelCB4.setChecked(false);
                cancelCB6.setChecked(false);
                break;

            case R.id.cancel_cb_6:
                cancelCB1.setChecked(false);
                cancelCB2.setChecked(false);
                cancelCB3.setChecked(false);
                cancelCB4.setChecked(false);
                cancelCB5.setChecked(false);
                break;

            case R.id.cancel_order_btn_1: // 取消服务弹窗的取消按钮
                if (CancelOrderPW != null && CancelOrderPW.isShowing()) {
                    CancelOrderPW.dismiss();
                    cancelTypeString = "";
                }
                break;

            case R.id.cancel_order_btn_2: // 取消服务弹窗的确定按钮
                if (cancelCB1.isChecked() == false && cancelCB2.isChecked() == false && cancelCB3.isChecked() == false && cancelCB4.isChecked() == false && cancelCB5.isChecked() == false && cancelCB6.isChecked() == false) {
                    Toast.makeText(MainActivity.this, "请至少选择一项取消服务的原因", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (cancelCB1.isChecked() == true) {
                        cancelTypeString = "1";
                    }
                    if (cancelCB2.isChecked() == true) {
                        cancelTypeString = "2";
                    }
                    if (cancelCB3.isChecked() == true) {
                        cancelTypeString = "3";
                    }
                    if (cancelCB4.isChecked() == true) {
                        cancelTypeString = "4";
                    }
                    if (cancelCB5.isChecked() == true) {
                        cancelTypeString = "5";
                    }
                    if (cancelCB6.isChecked() == true) {
                        cancelTypeString = "6";
                    }
                    dialogByProgress.show();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("cancelType", cancelTypeString);
                    builder.add("cancelDes", cancelEt.getText().toString().trim());
                    builder.add("accountId", id);
                    builder.add("orderId", orderId);
                    FormBody body = builder.build();
                    Request request = new Request.Builder().url(GlobalConsts.CANCEL_ORDER_URL).post(body).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String bodyString = response.body().string();
                            Gson cancelOrderGson = new Gson();
                            CancelOrderRequest cancelOrderRequest = cancelOrderGson.fromJson(bodyString, CancelOrderRequest.class);
                            Message cancelOrderMsg = Message.obtain();
                            cancelOrderMsg.what = GlobalConsts.CANCEL_ORDER_HANDLER;
                            cancelOrderMsg.obj = cancelOrderRequest;
                            handler.sendMessage(cancelOrderMsg);
                        }
                    });
                }
                break;

            case R.id.camera_iv: // 弹出实景定位拍照的弹窗
                if (washingItemPW != null && washingItemPW.isShowing()) {
                    showCameraPW();
                }
                break;

            case R.id.camera_cancel_btn: // 实景定位弹窗取消
                if (cameraPW != null && cameraPW.isShowing()) {
                    cameraPW.dismiss();
                }
                break;

            case R.id.camera_determine_btn: // 实景定位弹窗保存按钮
                if (imageList.size() == 1) {
                    if (cameraPW != null && cameraPW.isShowing()) {
                        cameraPW.dismiss();
                    }
                } else {
                    dialog = new DialogByOneButton(MainActivity.this, "提示", "照片数据保存成功", "确定");
                    dialog.show();
                    dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                        @Override
                        public void doPositive() {
                            if (imageList.size() == 2) { // 上传照片一张以上
                                for (int i = 0; i < imageList.size(); i++) {
                                    aboutCarImage.add(imageList.get(i));
                                }
                            } else if (imageList.size() > 2) {
                                for (int i = 0; i < imageList.size(); i++) {
                                    aboutCarImage.add(imageList.get(i));
                                }
                            }
                            if (cameraPW != null && cameraPW.isShowing()) {
                                cameraPW.dismiss();
                                dialog.dismiss();
                            }
                        }
                    });
                }
                break;

            case R.id.real_location_camera: // 拍照
                if (!isSDCard()) break;
                if (IsCameraCanUse.isCameraCanUse() == true) {
                    takePhone(); // 拍照获取
                    destroyPopupWindow();
                } else {
                    Toast.makeText(MainActivity.this, "请设置摄像头拍摄权限！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.real_location_album: // 从相册获取照片
                choosePhone(); // 相册获取
                destroyPopupWindow();
                break;

            case R.id.real_location_cancel: // 拍照或相册选择弹出框的取消按钮
                if (displayPW != null && displayPW.isShowing()) {
                    displayPW.dismiss();
                }
                break;

            case R.id.order_item_cancel_btn: // 订单的项目选择页面取消
                if (washingItemPW != null && washingItemPW.isShowing()) {
                    cleanString();
                    washingItemPW.dismiss();
                }
                break;

            case R.id.order_item_determine_btn: // 订单的项目选择页面确定
                orderItemIdList = orderItemAdapter.getOrderItemIdList();
                orderItemList = orderItemAdapter.getOrderItemList();
                Log.d(TAG, "清洗项目名称--------------" + orderItemIdList.toString());
                Log.d(TAG, "清洗项目总价--------------" + orderItemList.toString());
                if (orderItemList.size() == 0) {
                    Toast.makeText(MainActivity.this, "请至少选择一项清洗项目", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    for (int i = 0; i < orderItemIdList.size(); i++) { // 获取清洗的总价钱
                        finalOrderItemPrice += Double.parseDouble(String.valueOf(orderItemIdList.get(i)));
                    }
                    for (int j = 0; j < orderItemList.size(); j++) { // 获取清洗项目
                        orderString += orderItemList.get(j) + "、";
                    }
                    String ord = String.valueOf(orderString.charAt(0));
                    if (ord.equals("n")) {
                        orderItemString = orderString.substring(4, orderString.length() - 1); // 清洗项目
                    } else {
                        orderItemString = orderString.substring(0, orderString.length() - 1);
                    }

                    if (appointmentBtn.isChecked() == true) {
                        appointmentString = appointmentTimeTv.getText().toString().trim(); // 预约时间
                        orderItemType = "1";
                    } else {
                        appointmentString = "";
                        orderItemType = "0";
                    }
                    Intent orderIntent = new Intent(MainActivity.this, OrderDetermineActivity.class);
                    orderIntent.putExtra("orderTime", appointmentString); // 预约时间
                    orderIntent.putExtra("orderAddress", mainMyAddressTv.getText().toString().trim()); // 我的位置
                    orderIntent.putExtra("orderItem", orderItemString); // 车身清洗项目文字
                    orderIntent.putStringArrayListExtra("orderItemList", (ArrayList<String>) orderItemList); // 清洗项目数据集合
                    orderIntent.putStringArrayListExtra("orderItemIdList", (ArrayList<String>) orderItemIdList); // 清洗项目的单价集合
                    orderIntent.putExtra("orderRemarks", remarksEt.getText().toString().trim()); // 备注
                    orderIntent.putExtra("orderTotal", String.valueOf(finalOrderItemPrice)); // 清洗项目的总价
                    orderIntent.putExtra("id", id); // 用户的id
                    orderIntent.putExtra("Longitude", longitudeString); // 订单的经度
                    orderIntent.putExtra("Latitude", latitudeString); // 订单的纬度
                    orderIntent.putExtra("OrderType", orderItemType); // 清洗订单的类型
                    orderIntent.putExtra("carNumber", mainNumberPlateEt.getText().toString().trim()); // 车牌
                    orderIntent.putExtra("userName", userNameString); // 姓名
                    orderIntent.putExtra("userPhone", phoneString); // 手机号
                    orderIntent.putStringArrayListExtra("photorealist", (ArrayList<String>) aboutCarImage); // 照片的数据集合
                    startActivityForResult(orderIntent, GlobalConsts.SUBMIT_REFUND);
                    washingItemPW.dismiss();
                    aboutCarImage.clear();
                    imageList.clear();
                    finalOrderItemPrice = 0;
                    cleanString();
                }
                break;

            case R.id.my_head: // 个人中心编辑资料
                if (TextUtils.isEmpty(id)) {
                    dialog = new DialogByOneButton(MainActivity.this, "提示", "请先登录或注册", "确定");
                    dialog.show();
                    dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                        @Override
                        public void doPositive() {
                            dialog.dismiss();
                            hidePersonalCenterPW();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                    personalCenterPW.dismiss();
                }
                break;

            case R.id.login_tv: // 登录注册
                if (TextUtils.isEmpty(id)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    personalCenterPW.dismiss();
                } else if (!TextUtils.isEmpty(id)) {
                    loginTv.setClickable(false);
                }
                break;

            case R.id.order_rl: // 订单
                if (TextUtils.isEmpty(id)) {
                    dialog = new DialogByOneButton(MainActivity.this, "提示", "请先登录或注册", "确定");
                    dialog.show();
                    dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                        @Override
                        public void doPositive() {
                            dialog.dismiss();
                            hidePersonalCenterPW();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this, OrderActivity.class));
                    personalCenterPW.dismiss();
                }
                break;

            case R.id.wallet_rl: // 钱包
                startActivity(new Intent(MainActivity.this, WalletActivity.class));
                hidePersonalCenterPW();
                break;

            case R.id.service_rl: // 客服
                startActivity(new Intent(MainActivity.this, CustomerServiceActivity.class));
                hidePersonalCenterPW();
                break;

            case R.id.settings_rl: // 设置
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                hidePersonalCenterPW();
                break;

            case R.id.integral_rl: // 积分商城
                startActivity(new Intent(MainActivity.this, IntegralActivity.class));
                hidePersonalCenterPW();
                break;
        }
    }

    /**
     * 取消订单服务的弹窗
     */
    private void ShowCancelSinglePW() {
        CancelOrderView = View.inflate(MainActivity.this, R.layout.cancel_order_layout, null);
        int width = ScreenUtils.getScreenWidth(MainActivity.this) / 10 * 9;
        CancelOrderPW = new PopupWindow(CancelOrderView, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        backgroundAlpha(0.8f);

        cancelCB1 = (CheckBox) CancelOrderView.findViewById(R.id.cancel_cb_1);
        cancelCB2 = (CheckBox) CancelOrderView.findViewById(R.id.cancel_cb_2);
        cancelCB3 = (CheckBox) CancelOrderView.findViewById(R.id.cancel_cb_3);
        cancelCB4 = (CheckBox) CancelOrderView.findViewById(R.id.cancel_cb_4);
        cancelCB5 = (CheckBox) CancelOrderView.findViewById(R.id.cancel_cb_5);
        cancelCB6 = (CheckBox) CancelOrderView.findViewById(R.id.cancel_cb_6);
        cancelEt = (EditText) CancelOrderView.findViewById(R.id.cancel_et);
        Button cancelOrderBtn1 = (Button) CancelOrderView.findViewById(R.id.cancel_order_btn_1); // 取消
        Button cancelOrderBtn2 = (Button) CancelOrderView.findViewById(R.id.cancel_order_btn_2); // 确定

        cancelCB1.setOnClickListener(this);
        cancelCB2.setOnClickListener(this);
        cancelCB3.setOnClickListener(this);
        cancelCB4.setOnClickListener(this);
        cancelCB5.setOnClickListener(this);
        cancelCB6.setOnClickListener(this);
        cancelOrderBtn1.setOnClickListener(this);
        cancelOrderBtn2.setOnClickListener(this);

        CancelOrderPW.setFocusable(true);
        CancelOrderPW.setOutsideTouchable(true);
        CancelOrderPW.setBackgroundDrawable(new ColorDrawable(0x000000));
        CancelOrderPW.setAnimationStyle(android.R.style.Animation_InputMethod);
        CancelOrderPW.showAtLocation(CancelOrderView, Gravity.CENTER, 0, 0);
        CancelOrderPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                cancelTypeString = "";
            }
        });
    }

    /**
     * 服务车型提示的弹窗
     */
    private void showServiceModelPW() {
        serviceModeView = View.inflate(MainActivity.this, R.layout.service_model_layout, null);
        int width = ScreenUtils.getScreenWidth(MainActivity.this) / 10 * 9;
        serviceModePW = new PopupWindow(serviceModeView, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        backgroundAlpha(0.4f);

        ImageView serviceModelIv = (ImageView) serviceModeView.findViewById(R.id.service_model_iv);
        final CheckBox serviceModelCB = (CheckBox) serviceModeView.findViewById(R.id.service_model_CB);
        TextView serviceModelBtn = (TextView) serviceModeView.findViewById(R.id.service_model_btn);

        serviceModelCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceModelCB.isChecked() == true) {
                    serviceModelSp = MainActivity.this.getSharedPreferences("service_model", 0);
                    SharedPreferences.Editor editor = serviceModelSp.edit();
                    editor.putString("model", "true");
                    editor.commit();
                } else if (serviceModelCB.isChecked() == false) {
                    serviceModelSp = MainActivity.this.getSharedPreferences("service_model", 0);
                    SharedPreferences.Editor editor1 = serviceModelSp.edit();
                    editor1.putString("model", "false");
                    editor1.commit();
                }
            }
        });

        serviceModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderPW();
                serviceModePW.dismiss();
            }
        });

        int serviceWidth = ScreenUtils.getScreenWidth(MainActivity.this);
        int serviceHeight = ScreenUtils.getScreenHeight(MainActivity.this);

        Glide.with(MainActivity.this).load(R.drawable.service_model).override(serviceWidth, serviceHeight).into(serviceModelIv);

        serviceModePW.setFocusable(true);
        serviceModePW.setOutsideTouchable(true);
        serviceModePW.setBackgroundDrawable(new ColorDrawable(0x000000));
        serviceModePW.setAnimationStyle(android.R.style.Animation_InputMethod);
        serviceModePW.showAtLocation(serviceModeView, Gravity.CENTER, 0, 0);
        serviceModePW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 订单确认的弹窗
     */
    private void showOrderPW() {
        dialogByProgress.show();
        washingItemView = View.inflate(MainActivity.this, R.layout.washing_item_layout, null);
        int width = ScreenUtils.getScreenWidth(MainActivity.this) / 10 * 9;
        washingItemPW = new PopupWindow(washingItemView, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        backgroundAlpha(0.6f);

        orderItemRV = (RecyclerView) washingItemView.findViewById(R.id.order_item_RecyclerView); // 清洗项目的布局
        remarksEt = (EditText) washingItemView.findViewById(R.id.remarks_et);
        cameraIv = (ImageView) washingItemView.findViewById(R.id.camera_iv);
        orderItemCancelBtn = (Button) washingItemView.findViewById(R.id.order_item_cancel_btn);
        orderItemDetermineBtn = (Button) washingItemView.findViewById(R.id.order_item_determine_btn);

        remarksEt.setOnClickListener(this);
        cameraIv.setOnClickListener(this);
        orderItemCancelBtn.setOnClickListener(this);
        orderItemDetermineBtn.setOnClickListener(this);

        washingItemPW.setFocusable(true);
        washingItemPW.setOutsideTouchable(true);
        washingItemPW.setBackgroundDrawable(new ColorDrawable(0x000000));
        washingItemPW.setAnimationStyle(android.R.style.Animation_InputMethod);
        washingItemPW.showAtLocation(washingItemView, Gravity.CENTER, 0, 0);
        washingItemPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                cleanString(); // 清除数据
            }
        });

        /**
         * 根据经纬度获取当地的清洗项目数据
         */
        Request request = new Request.Builder().url(GlobalConsts.GET_ORDER_ITEM_BY_COUNTY_URL + latitudeString + "&Longitude=" + longitudeString).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson getOrderItemGson = new Gson();
                GetOrderItemRequest getOrderItemRequest = getOrderItemGson.fromJson(bodyString, GetOrderItemRequest.class);
                Message getOrderItemMsg = Message.obtain();
                getOrderItemMsg.what = GlobalConsts.GET_ORDER_ITEM_BY_COUNTY_HANDLER;
                getOrderItemMsg.obj = getOrderItemRequest;
                handler.sendMessage(getOrderItemMsg);
            }
        });
    }

    /**
     * 显示实景位置拍照的弹窗
     */
    private void showCameraPW() {
        cameraView = View.inflate(MainActivity.this, R.layout.pw_camera_layout, null);
        cameraPW = new PopupWindow(cameraView, ScreenUtils.getScreenWidth(MainActivity.this) / 10 * 9, ViewGroup.LayoutParams.WRAP_CONTENT);
        backgroundAlpha(0.2f);

        cameraCancelBtn = (TextView) cameraView.findViewById(R.id.camera_cancel_btn);
        cameraDetermineBtn = (TextView) cameraView.findViewById(R.id.camera_determine_btn);
        cameraGridView = (GridView) cameraView.findViewById(R.id.camera_grid_view);

        cameraCancelBtn.setOnClickListener(this);
        cameraDetermineBtn.setOnClickListener(this);

        cameraPW.setFocusable(true);
        cameraPW.setOutsideTouchable(true);
        cameraPW.setBackgroundDrawable(new ColorDrawable(0x000000));
        cameraPW.setAnimationStyle(android.R.style.Animation_InputMethod);
        cameraPW.showAtLocation(cameraView, Gravity.CENTER, 0, 0);
        cameraPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        if (aboutCarImage != null && !aboutCarImage.isEmpty()) { // 已拍照一张或一张以上
            for (int i = 0; i < aboutCarImage.size(); i++) {
                for (int j = aboutCarImage.size() - 1; j > i; j--) {
                    if (aboutCarImage.get(i).equals(aboutCarImage.get(j))) {
                        aboutCarImage.remove(i);
                    }
                }
            }
            adapter = new MyGridViewAdapter(aboutCarImage, MainActivity.this);
            cameraGridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            cameraGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (aboutCarImage.size() == 6) {
                        if (position == aboutCarImage.size() - 1) {
                            Toast.makeText(MainActivity.this, "图片数量已满5张", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    } else {
                        if (position != aboutCarImage.size() - 1) {
                            Intent intent = new Intent(MainActivity.this, BigImgActivity.class);
                            intent.putExtra("activity", aboutCarImage.get(position));
                            startActivity(intent);
                        } else if (position == aboutCarImage.size() - 1) {
                            adapter = new MyGridViewAdapter(aboutCarImage, MainActivity.this);
                            cameraGridView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            showSelectPW();
                        }
                    }
                }
            });
        } else {
            imageList.clear();
            imageList.add(getResources().getResourceName(R.layout.griditem_addpic));
            adapter = new MyGridViewAdapter(imageList, MainActivity.this);
            cameraGridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            cameraGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == imageList.size() - 1 && imageList.size() <= 5) { // 点击图片位置为+ 0对应0张图片
                        showSelectPW(); // 底部弹出弹窗
                    } else if (position != imageList.size() - 1 && imageList.size() <= 5) {
                        Intent intent = new Intent(MainActivity.this, BigImgActivity.class);
                        intent.putExtra("activity", imageList.get(position));
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "图片数量已满5张", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 底部弹出拍照或相册取出照片的弹窗
     */
    private void showSelectPW() {
        View popupView = View.inflate(MainActivity.this, R.layout.real_location_pw_layout, null);
        displayPW = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        realLocationCamera = (Button) popupView.findViewById(R.id.real_location_camera);
        realLocationAlbum = (Button) popupView.findViewById(R.id.real_location_album);
        realLocationCancel = (Button) popupView.findViewById(R.id.real_location_cancel);

        realLocationCamera.setOnClickListener(this);
        realLocationAlbum.setOnClickListener(this);
        realLocationCancel.setOnClickListener(this);

        displayPW.setFocusable(true);
        displayPW.setOutsideTouchable(true);
        displayPW.setAnimationStyle(android.R.style.Animation_InputMethod);
        displayPW.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示左侧个人设置中心
     */
    private void showPersonalPW() {
        personalCenterView = View.inflate(MainActivity.this, R.layout.personal_center_pw, null);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels / 2;
        personalCenterPW = new PopupWindow(personalCenterView, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        backgroundAlpha(0.5f);

        myHead = (CircleImageView) personalCenterView.findViewById(R.id.my_head);
        loginTv = (TextView) personalCenterView.findViewById(R.id.login_tv);
        orderRl = (RelativeLayout) personalCenterView.findViewById(R.id.order_rl);
        walletRl = (RelativeLayout) personalCenterView.findViewById(R.id.wallet_rl);
        serviceRl = (RelativeLayout) personalCenterView.findViewById(R.id.service_rl);
        settingsRl = (RelativeLayout) personalCenterView.findViewById(R.id.settings_rl);
        integralRl = (RelativeLayout) personalCenterView.findViewById(R.id.integral_rl);
        shareRl = (RelativeLayout) personalCenterView.findViewById(R.id.share_rl);
        shareRl.setOnClickListener(new View.OnClickListener() {  // 分享的点击监听
            @Override
            public void onClick(View v) {
                if (personalCenterPW != null && personalCenterPW.isShowing()) {
                    personalCenterPW.dismiss();
                }
                ShareBoardConfig config = new ShareBoardConfig();
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
                mShareAction.open(config);
            }
        });

        if (TextUtils.isEmpty(id)) { // 当前未登录账号
            loginTv.setText("登录/注册"); // 登录或者昵称显示
            myHead.setBackgroundResource(R.mipmap.head);
        } else {
            if (userNameString != null && !TextUtils.isEmpty(userNameString)) { // 姓名
                loginTv.setText(userNameString);
            } else if (phoneString != null && !TextUtils.isEmpty(phoneString) && phoneString.length() == 11) { // 联系电话
                loginTv.setText(phoneString.substring(0, 3) + "****" + phoneString.substring(7, 11));
            } else {
                loginTv.setText("用户");
            }

            if (headPictureString != null && !TextUtils.isEmpty(headPictureString)) {
                Glide.with(MainActivity.this).load(GlobalConsts.DONG_DONG_IMAGE_URL + headPictureString).into(myHead);
            } else {
                myHead.setBackgroundResource(R.mipmap.head);
            }
        }

        myHead.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        orderRl.setOnClickListener(this);
        walletRl.setOnClickListener(this);
        serviceRl.setOnClickListener(this);
        settingsRl.setOnClickListener(this);
        integralRl.setOnClickListener(this);

        personalCenterPW.setFocusable(true);
        personalCenterPW.setOutsideTouchable(true);
        personalCenterPW.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        personalCenterPW.setAnimationStyle(R.style.AnimationLeftFade);
        personalCenterPW.showAtLocation(personalCenterView, Gravity.LEFT, 0, 0);
        personalCenterPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     *  设置添加屏幕的背景透明度 
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0  
        MainActivity.this.getWindow().setAttributes(lp);
        // MainActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 清除数据
     */
    private void cleanString() {
        orderItemIdList.clear();
        orderItemList.clear();
        aboutCarImage.clear();
        orderString = "";
        orderItemString = "";
    }

    /**
     * 检查拍照权限
     */
    private void takePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            takePhoto();
        }
    }

    /**
     * 使用拍照设置头像
     */
    private void takePhoto() {
        imageFile = new File(destDir, getPhotoFileName());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, TAKE_PICTURES_UPLOAD);
    }

    /**
     * 设置拍照后的照片格式
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh：mm：ss.SSS");
        return sdf.format(date) + ".jpg";
    }

    /**
     * 检查访问相册权限
     */
    public void choosePhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            choosePhoto();
        }
    }

    /**
     * 从相册选取图片
     */
    public void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, ALBUM_UPLOAD);
    }

    /**
     * 获取图片路径 响应startActivityForResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURES_UPLOAD: // 拍照选择照片
                if (resultCode == RESULT_OK) {
                    try {
                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(imageFile));
                        saveCropPic(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case ALBUM_UPLOAD: // 相册选择照片
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        saveCropPic(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case GlobalConsts.SUBMIT_REFUND:
                if (resultCode == RESULT_OK) {
                    orderNumber = data.getStringExtra("ORDER_NUMBER");
                }
                break;
        }
    }

    /**
     * 把裁剪后的图片保存到sdcard上并上传至服务器通过adapter适配后显示
     */
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        Bitmap bi = ImageUtils.imageZoom(bmp); // 点击图片显示效果
        bi.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(imageFile);
            fis.write(baos.toByteArray());
            upImgFile(imageFile); // 将图片文件上传服务
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断SD卡是否存在
     */
    private boolean isSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            return true;
        } else {
            Toast.makeText(MainActivity.this, "SD卡不存在", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 图片上传至服务器并显示
     */
    private void upImgFile(File imageFile) {
        dialogByProgress.show();
        String BOUNDARY = UUID.randomUUID().toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), imageFile);
        MultipartBody multipartBody = new MultipartBody.Builder(BOUNDARY).setType(MultipartBody.FORM).addFormDataPart("", imageFile.getName(), body).build();
        Request request = new Request.Builder().url(GlobalConsts.UPLOAD_IMAGE_URL).post(multipartBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson uploadImageGson = new Gson();
                UploadImageRequest uploadImageRequest = uploadImageGson.fromJson(bodyString, UploadImageRequest.class);
                Message uploadMsg = Message.obtain();
                uploadMsg.what = GlobalConsts.UPLOAD_IMAGE_ORDER_HANDLER;
                uploadMsg.obj = uploadImageRequest;
                handler.sendMessage(uploadMsg);
            }
        });
    }

    /**
     * 预约时间选择器
     */
    private void showTripPickerView() {
        showDateOptions = new OptionsPickerView(this);
        daysList.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 2);
        String day1 = "今天";
        String day2 = "明天";
        String day3 = sdf.format(c.getTime());
        List<String> days = new ArrayList<>();
        days.add(day1);
        days.add(day2);
        days.add(day3);
        for (String day : days) {
            daysList.add(day); // 日期的数据添加
            hours = new ArrayList<>(); // 分钟的数据添加
            minutes = new ArrayList<>();
            for (int i = startTime; i <= endTime; i++) {
                hours.add(String.valueOf(i) + "点");
                minute = new ArrayList<>(); // 秒的数据添加
                String showMinutes1 = String.valueOf(00 + "0分");
                String showMinutes2 = String.valueOf(10 + "分");
                String showMinutes3 = String.valueOf(20 + "分");
                String showMinutes4 = String.valueOf(30 + "分");
                String showMinutes5 = String.valueOf(40 + "分");
                String showMinutes6 = String.valueOf(50 + "分");
                minute.add(showMinutes1);
                minute.add(showMinutes2);
                minute.add(showMinutes3);
                minute.add(showMinutes4);
                minute.add(showMinutes5);
                minute.add(showMinutes6);
                minutes.add(minute);
            }
            minutesList.add(minutes);
            hoursList.add(hours);
        }
        showDateOptions.setPicker(daysList, hoursList, minutesList, true);
        showDateOptions.setCyclic(false, false, false);
        showDateOptions.setSelectOptions(0, 0, 0);
        showDateOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String showDate = daysList.get(options1) + " : " + hoursList.get(options1).get(option2) + " : " + minutesList.get(options1).get(option2).get(options3);
                appointmentTimeTv.setText(showDate);
            }
        });
        showDateOptions.setTitle("请选择预约时间");
    }

    /**
     * 6.0版本手机权限用户拒绝后接受回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto(); // 拍摄
            } else {
                Toast.makeText(MainActivity.this, "请为软件开启拍摄权限", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto(); // 访问相册
            } else {
                Toast.makeText(MainActivity.this, "请为软件开启访问相册权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        screenMarker.hideInfoWindow();
    }

    /**
     * 图钉移动后获取经纬度
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        mCameraPosition = cameraPosition;
        double latitudeDb = mCameraPosition.target.latitude; // 获取经度
        double longitudeDb = mCameraPosition.target.longitude; // 获取纬度
        latitudeString = String.valueOf(latitudeDb);
        longitudeString = String.valueOf(longitudeDb);
        // Log.d(TAG, "定位后的经纬度----" + latitudeString + "-------" + longitudeString);
        setCameraPosition(latitudeDb, longitudeDb);

        Request request = new Request.Builder().url(GlobalConsts.CHECK_ORDER_ITEM_BY_COUNTY_URL + longitudeString + "&Latitude=" + latitudeString).build(); // 根据区县码判断该区域有未开通清洗服务
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson checkOrderItemGson = new Gson();
                CheckOrderItemRequest checkOrderItemRequest = checkOrderItemGson.fromJson(bodyString, CheckOrderItemRequest.class);
                Message checkOrderItemMsg = Message.obtain();
                checkOrderItemMsg.what = GlobalConsts.CHECK_ORDER_ITEM_BY_COUNTY_HANDLER;
                checkOrderItemMsg.obj = checkOrderItemRequest;
                handler.sendMessage(checkOrderItemMsg);
            }
        });

        startJumpAnimation();
        screenMarker.showInfoWindow(); // 显示自定义文字
    }

    /**
     * 根据图钉移动后获取的经纬度查询具体的文字地址信息
     */
    private void setCameraPosition(double latitudeString, double longitudeString) {
        latLonPoint = new LatLonPoint(latitudeString, longitudeString);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 50, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    /**
     * 图钉移动式获取定位的地址信息
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                String ProvinceLength = regeocodeResult.getRegeocodeAddress().getProvince(); // 城市
                String DistrictLength = regeocodeResult.getRegeocodeAddress().getProvince(); // 区域
                int cityLength = Integer.parseInt(String.valueOf(ProvinceLength.length() + DistrictLength.length()));
                String cityMessage = addressName.substring(cityLength, addressName.length());
                mainMyAddressTv.setText(cityMessage);
            }
        }
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 自定义图钉顶部标题栏
     */
    @Override
    public View getInfoWindow(Marker marker) {
        infoWindow = getLayoutInflater().inflate(R.layout.marker_info_window_layout, null);
        infoWindowTv = (TextView) infoWindow.findViewById(R.id.info_window_tv);
        infoWindowTv.setText("下单为您的爱车洗洗吧...");
        if (isHaveService == true) {
            if (TextUtils.isEmpty(id)) {
                infoWindowTv.setText("下单为您的爱车洗洗吧...");
                hideCancelBtn();
            } else {
                if (TextUtils.equals(orderState, "1")) { // 已下单，待支付
                    infoWindowTv.setText("下单为您的爱车洗洗吧...");
                    hideCancelBtn();
                } else if (TextUtils.equals(orderState, "2")) { // 已支付，待接单
                    infoWindowTv.setText("正在为您安排工作人员接单,请稍后...");
                    showCancelBtn();
                } else if (TextUtils.equals(orderState, "3")) { // 已接单
                    infoWindowTv.setText("订单已接收,待服务人员上门清洗...");
                    showOrderByStaff();
                } else if (TextUtils.equals(orderState, "4")) { // 正在进行中
                    infoWindowTv.setText("爱车正在清洗中...");
                    showOrderByStaff();
                } else if (TextUtils.equals(orderState, "5")) { // 已完成
                    infoWindowTv.setText("下单为您的爱车洗洗吧...");
                    hideCancelBtn();
                } else if (TextUtils.equals(orderState, "6")) { // 已取消
                    infoWindowTv.setText("下单为您的爱车洗洗吧...");
                    hideCancelBtn();
                } else {
                    infoWindowTv.setText("下单为您的爱车洗洗吧...");
                }
            }
        } else if (isHaveService == false) {
            infoWindowTv.setText("当前区域暂未开通服务...");
        }
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    /**
     * 分享的内部类
     */
    private class CustomShareListener implements UMShareListener {

        private WeakReference<MainActivity> mActivity;

        public CustomShareListener(MainActivity mainActivity) {
            mActivity = new WeakReference(mainActivity);
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            if (share_media.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), share_media + "分享成功", Toast.LENGTH_SHORT).show();
            } else {
                if (share_media != SHARE_MEDIA.MORE
                        && share_media != SHARE_MEDIA.SMS
                        && share_media != SHARE_MEDIA.EMAIL
                        && share_media != SHARE_MEDIA.FLICKR
                        && share_media != SHARE_MEDIA.FOURSQUARE
                        && share_media != SHARE_MEDIA.TUMBLR
                        && share_media != SHARE_MEDIA.POCKET
                        && share_media != SHARE_MEDIA.PINTEREST
                        && share_media != SHARE_MEDIA.LINKEDIN
                        && share_media != SHARE_MEDIA.INSTAGRAM
                        && share_media != SHARE_MEDIA.GOOGLEPLUS
                        && share_media != SHARE_MEDIA.YNOTE
                        && share_media != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), share_media + "分享成功", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (share_media != SHARE_MEDIA.MORE
                    && share_media != SHARE_MEDIA.SMS
                    && share_media != SHARE_MEDIA.EMAIL
                    && share_media != SHARE_MEDIA.FLICKR
                    && share_media != SHARE_MEDIA.FOURSQUARE
                    && share_media != SHARE_MEDIA.TUMBLR
                    && share_media != SHARE_MEDIA.POCKET
                    && share_media != SHARE_MEDIA.PINTEREST
                    && share_media != SHARE_MEDIA.LINKEDIN
                    && share_media != SHARE_MEDIA.INSTAGRAM
                    && share_media != SHARE_MEDIA.GOOGLEPLUS
                    && share_media != SHARE_MEDIA.YNOTE
                    && share_media != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), share_media + "分享失败，请先安装或打开软件", Toast.LENGTH_SHORT).show();
                if (throwable != null) {
                }
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(mActivity.get(), share_media + "取消分享", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    /**
     * 搜索后的地址返回地图显示
     */
    private class SearchBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String addressString = intent.getStringExtra("searchName");
            GeocodeQuery query = new GeocodeQuery(addressString, null); // 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
            geocoderSearchAddress.getFromLocationNameAsyn(query); // 设置同步地理编码请求
            geocoderSearchAddress.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult geocodeResult, int rCode) {

                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
                    if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                        if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
                            GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
                            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(AMapUtil.convertToLatLng(address.getLatLonPoint()), 17));
                            screenMarker.setPosition(AMapUtil.convertToLatLng(address.getLatLonPoint()));
                            mainMyAddressTv.setText(addressString);
                            if (screenMarker != null) {
                                screenMarker.destroy();
                                addMarkerInScreenCenter();
                            } else {
                                addMarkerInScreenCenter();
                            }
                        }
                    }
                }
            });
        }
    }

    private class OrderAcceptBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "ORDER_TO_BE": // 下订单后待接单
                    orderId = intent.getStringExtra("ORDER_ID_TO_BE");
                    orderState = intent.getStringExtra("ORDER_STATE_TO_BE");
                    showCancelBtn();
                    break;

                case "ORDER_ACCEPT": // 已接收订单
                    orderId = intent.getStringExtra("ORDER_ID_ACCEPT");
                    orderState = intent.getStringExtra("ORDER_STATE_ACCEPT");
                    Log.d(TAG, "接收友盟推送的消息------订单号======" + orderId.toString());
                    Log.d(TAG, "接收友盟推送的消息------订单状态======" + orderState.toString());
                    showOrderByStaff();
                    getStaffMsgByOrderId(orderId);
                    break;

                case "ORDER_PROCESSING": // 订单进行中
                    orderId = intent.getStringExtra("ORDER_ID_PROCESSING");
                    orderState = intent.getStringExtra("ORDER_STATE_PROCESSING");
                    showOrderByStaff();
                    getStaffMsgByOrderId(orderId);
                    break;

                case "ORDER_COMPLETED": // 订单已完成
                    orderId = intent.getStringExtra("ORDER_ID_COMPLETED");
                    orderState = intent.getStringExtra("ORDER_STATE_COMPLETED");
                    hideCancelBtn();
                    break;
            }
        }
    }

    /**
     * 根据用户id获取当前的用户订单状态
     */
    private void getOrderStateByUid() {
        Request request = new Request.Builder().url(GlobalConsts.GET_ORDER_STATE_BY_UID_URL + id).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson orderStateGson = new Gson();
                OrderStateRequest orderStateRequest = orderStateGson.fromJson(bodyString, OrderStateRequest.class);
                Message orderStateMsg = Message.obtain();
                orderStateMsg.what = GlobalConsts.GET_ORDER_STATE_BY_UID_HANDLER;
                orderStateMsg.obj = orderStateRequest;
                handler.sendMessage(orderStateMsg);
            }
        });
    }

    /**
     * 根据订单id获取司机信息
     */
    private void getStaffMsgByOrderId(String orderId) {
        Request request = new Request.Builder().url(GlobalConsts.GET_STAFF_MSG_BY_ORDER_ID_URL + orderId).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson getStaffMsgGson = new Gson();
                GetStaffMsgRequest getStaffMsgRequest = getStaffMsgGson.fromJson(bodyString, GetStaffMsgRequest.class);
                Message getStaffMsg = Message.obtain();
                getStaffMsg.what = GlobalConsts.GET_STAFF_MSG_BY_ORDER_ID_HANDLER;
                getStaffMsg.obj = getStaffMsgRequest;
                handler.sendMessage(getStaffMsg);
            }
        });
    }

    /**
     * 取消订单后提交退款申请
     */
    private void submitRefund() {
        Request request = new Request.Builder().url(GlobalConsts.DONG_DONG_URL + "api/AliaPay/RefundPay?userId=" + id + "&orderNumber=" + orderNumber).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson refundGson = new Gson();
                RefundRequest refundRequest = refundGson.fromJson(bodyString, RefundRequest.class);
                Message refundMsg = Message.obtain();
                refundMsg.what = GlobalConsts.SUBMIT_REFUND_HANDLER;
                refundMsg.obj = refundRequest;
                handler.sendMessage(refundMsg);
            }
        });
    }

    /**
     * 根据区县码获取当前区域的服务时间
     */
    private void getServiceTimeByCountyCode() {
        Request request = new Request.Builder().url(GlobalConsts.GET_SERVICE_TIME_BY_COUNTY_CODE_URL + countyCode).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson getServiceTimeGson = new Gson();
                GetServiceTimeRequest getServiceTimeRequest = getServiceTimeGson.fromJson(bodyString, GetServiceTimeRequest.class);
                Message getServiceTimeMsg = Message.obtain();
                getServiceTimeMsg.what = GlobalConsts.GET_SERVICE_TIME_BY_COUNTY_CODE_HANDLER;
                getServiceTimeMsg.obj = getServiceTimeRequest;
                handler.sendMessage(getServiceTimeMsg);
            }
        });
    }

    // 图钉跳动动画
    private void startJumpAnimation() {
        if (screenMarker != null) {
            final LatLng latLng = screenMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this, 125);
            LatLng target = aMap.getProjection().fromScreenLocation(point);
            Animation animation = new TranslateAnimation(target); // 使用TranslateAnimation,填写一个需要移动的目标点
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            animation.setDuration(600); // 整个移动所需要的时间
            screenMarker.setAnimation(animation); // 设置动画
            screenMarker.startAnimation(); // 开始动画
        }
    }

    // dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 销毁弹窗
    private void destroyPopupWindow() {
        displayPW.dismiss();
        isShowingPW = true;
    }

    private void HideDialogByProgress() {
        if (dialogByProgress != null && dialogByProgress.isShowing()) {
            dialogByProgress.dismiss();
        }
    }

    private void hidePersonalCenterPW() {
        if (personalCenterPW != null && personalCenterPW.isShowing()) {
            personalCenterPW.dismiss();
        }
    }

    private void showCancelBtn() {
        mainLl1.setVisibility(View.GONE); // 现在及预约的复选框
        mainLl2.setVisibility(View.GONE); // 地址及车辆信息
        mainLl3.setVisibility(View.GONE); // 接单后司机信息
        callServiceBtn.setVisibility(View.GONE); // 呼叫服务
        cancelServiceBtn.setVisibility(View.VISIBLE); // 取消服务
    }

    private void hideCancelBtn() {
        mainLl1.setVisibility(View.VISIBLE);
        mainLl2.setVisibility(View.VISIBLE);
        mainLl3.setVisibility(View.GONE);
        callServiceBtn.setVisibility(View.VISIBLE);
        cancelServiceBtn.setVisibility(View.GONE);
    }

    private void showOrderByStaff() {
        mainLl1.setVisibility(View.GONE);
        mainLl2.setVisibility(View.GONE);
        mainLl3.setVisibility(View.VISIBLE);
        callServiceBtn.setVisibility(View.GONE);
        cancelServiceBtn.setVisibility(View.GONE);
    }

    private void downloadApk(String versionName, String newVersionName, final String apkUrl) { // 判断当前是否有新版本，有则提示并下载更新
        if (dialog2 != null && dialog2.isShowing()) {
            dialog2.dismiss();
            if (!versionName.equals(newVersionName)) {
                dialogByApk = new DialogByTwoButton(MainActivity.this, "提示", "有新版本可以更新啦", "残忍拒绝", "下载更新");
                dialogByApk.show();
                dialogByApk.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                    @Override
                    public void doNegative() {
                        dialogByApk.dismiss();
                    }

                    @Override
                    public void doPositive() {
                        dialogByApk.dismiss();
                        downloadAPK = new Downloader(MainActivity.this);
                        downloadAPK.downloadAPK(apkUrl, "debug.apk");
                    }
                });
            }
        }
    }

    /**
     * 监听屏幕BACK键，判断是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出该程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                UmengUtils.onLogout();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
