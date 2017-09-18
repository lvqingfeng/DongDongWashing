package dongdongwashing.com.ui.Order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.AliPayDataRequest;
import dongdongwashing.com.entity.SaveAliPayRequest;
import dongdongwashing.com.entity.changOrderState.OrderStateRequest;
import dongdongwashing.com.entity.getAliPayData.AliPayRequest;
import dongdongwashing.com.entity.getAliPayData.AliPayResult;
import dongdongwashing.com.entity.greateOrder.CreateOrderRequest;
import dongdongwashing.com.entity.greateOrder.CreateOrderResult;
import dongdongwashing.com.entity.orderPhoto.ImageFileName;
import dongdongwashing.com.entity.orderPhoto.OrderItem;
import dongdongwashing.com.entity.weChatPay.WeChatPayRequest;
import dongdongwashing.com.entity.weChatPay.WeChatPayResult;
import dongdongwashing.com.util.CoverFlowViewPager;
import dongdongwashing.com.util.DataConversionByShen;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.DialogByTwoButton;
import dongdongwashing.com.util.GlobalConsts;
import dongdongwashing.com.util.OrderInfoUtil2_0;
import dongdongwashing.com.util.PayResult;
import dongdongwashing.com.util.ScreenUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/4/6.
 */

public class OrderDetermineActivity extends BaseActivity implements View.OnClickListener {

    private ImageView determineOrderBack, determineOrderBack2;
    private TextView determineOrderCancel; // 取消订单
    private RelativeLayout determineOrderRl; // 预约时间的父布局
    private TextView orderTime; // 预约时间
    private TextView orderAddress; // 我的位置
    private TextView orderName; // 客户姓名
    private TextView orderPhone; // 联系电话
    private TextView orderNumberPlate; // 车牌
    private TextView orderItem; // 清洗项目
    private TextView orderRemarks; // 备注
    private CircleImageView aboutCarPhoto; // 车辆周围照片
    private TextView determineOrderTotal; // 清洗项目的总价
    private Button determineOrderBtn; // 确认按钮

    private String userName, userPhone;
    private String Longitude; // 接收的经度信息
    private String Latitude; // 接收的纬度信息
    private String CarNumber; // 接收的车牌信息
    private String OrderType; // 接收的订单类型信息
    private String OrderTime; // 订单下单的时间
    private String id; // 用户下单的id
    private String appointmentTime, AppointmentTime = ""; // 接收的预约时间
    private String AddressString; // 接收的我的位置
    private String orderItemString; // 接收的车身清洗项目
    private String orderRemarksString; // 接收的备注
    private String orderTotalString; // 接收的清洗项目的总价
    private float orderTotal; // 价钱转换
    private ArrayList<String> orderItemList = new ArrayList<>(); // 清洗项目的数据集合
    private ArrayList<String> orderItemIdList = new ArrayList<>(); // 清洗项目的单价集合
    private List<String> photoAroundList = new ArrayList<>(); // 车辆周围的照片集合
    private List<String> aroundImageList = new ArrayList<>(); // 车辆周围的照片集合
    private String photoListStr = ""; // 照片集合
    private ImageFileName image;
    private OrderItem orderItemNameOrPrice;
    private String orderItemListStr;
    private Gson orderGson = new Gson();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private ImageView payWayBalance;
    private ImageView payWayWechat;
    private ImageView payWayAli;
    private Button payWayBtn;

    private View payView; // 支付方式的布局
    private PopupWindow payPw; // 支付方式的弹窗

    private String orderNumber; // 生成的订单编号
    private String orderAccountID; // 生成的订单id
    private String orderTotalStr; // 生成的订单价钱
    private String createOrderTimeStr, createOrderTime; // 生成的订单时间
    private String payWay; // 支付方式
    private String payState; // 支付成功或者失败的状态
    private SharedPreferences payWaySp;
    private String payWayString; // 支付方式的偏好设置信息

    public static String APPID = ""; // 支付宝支付id
    public static String RSA2_PRIVATE = "";
    private String tradeNo; // 支付宝交易流水号
    private PopupWindow paySuccessPW, payFailurePW; // 支付成功或失败的弹窗
    private View paySuccessView, payFailureView;

    private CoverFlowViewPager mCover; // 照片的画廊左右滑动显示控件
    private List<View> pictureList = new ArrayList<>(); // 照片的集合数据

    private IWXAPI weChatApi;
    private PayReq weChatRequest;
    private WeChatBroadcastReceiver weChatBroadcastReceiver;

    private DialogByTwoButton dialog2;
    private DialogByProgress dialogByProgress;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.GET_ALIPAY_DATA_HANDLER: // 获取支付宝支付的秘钥信息
                    AliPayRequest aliPayRequest = (AliPayRequest) msg.obj;
                    AliPayResult aliPayResult = aliPayRequest.getData();
                    String aliPayState = aliPayRequest.getIsSucess();
                    if (TextUtils.equals("true", aliPayState)) {
                        APPID = aliPayResult.getAppID();
                        RSA2_PRIVATE = aliPayResult.getKey();
                    }
                    break;

                case GlobalConsts.CREATE_ORDER_HANDLER: // 生成订单
                    CreateOrderRequest createOrderRequest = (CreateOrderRequest) msg.obj;
                    String createOrderString = createOrderRequest.getMsg();
                    if (TextUtils.equals("下单成功", createOrderString)) {
                        determineOrderBack.setVisibility(View.GONE);
                        determineOrderBack2.setVisibility(View.VISIBLE);
                        determineOrderCancel.setVisibility(View.VISIBLE);
                        if (dialogByProgress != null && dialogByProgress.isShowing()) {
                            dialogByProgress.dismiss();
                        }
                        CreateOrderResult createOrderResult = createOrderRequest.getData();
                        orderNumber = createOrderResult.getOrderNumber(); // 获取生成的订单编号
                        orderAccountID = createOrderResult.getId(); // 获取生成的订单id
                        orderTotalStr = String.valueOf(createOrderResult.getOrderTotal()); // 获取生成的订单价钱
                        createOrderTime = createOrderResult.getCreatedDate();
                        String[] time = createOrderTime.split("[T.]");
                        createOrderTimeStr = time[0] + " " + time[1]; // 获取生成订单的时间
                        showPayPw();
                    }
                    break;

                case GlobalConsts.ALI_PAY_HANDLER: // 支付宝支付后的回调
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    AliPayDataRequest aliPayDataRequest = orderGson.fromJson(payResult.getResult(), AliPayDataRequest.class);
                    tradeNo = aliPayDataRequest.getAlipay_trade_app_pay_response().getTrade_no();
                    final String payStatus = payResult.getResultStatus();
                    if (payPw != null && payPw.isShowing()) {
                        payPw.dismiss();
                        if (TextUtils.equals("9000", payStatus)) {
                            saveAliPay(); // 保存支付成功后的信息
                        } else {
                            showPayFailurePW(); // 支付失败的弹窗
                        }
                    }
                    break;

                case GlobalConsts.SAVE_ALI_PAY_HANDLER: // 支付宝支付成功后保存的信息，方便退款使用
                    SaveAliPayRequest saveAliPayRequest = (SaveAliPayRequest) msg.obj;
                    HideDialogByProgress();
                    String saveAliPayState = saveAliPayRequest.getIsSucess();
                    if (TextUtils.equals("true", saveAliPayState)) {
                        showPaySuccessPW(); // 支付成功的弹窗
                    } else {
                        showPaySuccessPW();
                    }
                    break;

                case GlobalConsts.WE_CHAT_PAY_HANDLER: // 微信支付请求
                    WeChatPayRequest weChatPayRequest = (WeChatPayRequest) msg.obj;
                    String weChatPayMsgStr = weChatPayRequest.getMsg();
                    if (TextUtils.equals("调用支付客端参数", weChatPayMsgStr)) {
                        if (dialogByProgress != null && dialogByProgress.isShowing()) {
                            dialogByProgress.dismiss();
                            WeChatPayResult weChatPayResult = weChatPayRequest.getData();
                            weChatApi = WXAPIFactory.createWXAPI(OrderDetermineActivity.this, null);
                            weChatApi.registerApp(weChatPayResult.getAppid());
                            weChatRequest = new PayReq();
                            weChatRequest.appId = weChatPayResult.getAppid();
                            weChatRequest.partnerId = weChatPayResult.getPartnerid();
                            weChatRequest.prepayId = weChatPayResult.getPrepayid();
                            weChatRequest.packageValue = weChatPayResult.getPackage1();
                            weChatRequest.nonceStr = weChatPayResult.getNoncestr();
                            weChatRequest.timeStamp = weChatPayResult.getTimestamp();
                            weChatRequest.sign = weChatPayResult.getSign();
                            weChatApi.sendReq(weChatRequest);
                        }
                    }
                    break;

                case GlobalConsts.CREATE_ORDER_STATE_HANDLER: // 修改订单状态
                    OrderStateRequest changeOrderStateRequest = (OrderStateRequest) msg.obj;
                    String changeOrderStateStr = changeOrderStateRequest.getMsg();
                    if (TextUtils.equals("订单状态成功", changeOrderStateStr)) {
                        String changeOrderStateIng = changeOrderStateRequest.getData().getOrderSate();
                        if (TextUtils.equals("2", changeOrderStateIng)) { // 支付成功，待接单
                            Intent intent = new Intent();
                            intent.putExtra("ORDER_NUMBER", orderNumber);
                            setResult(RESULT_OK, intent);
                            OrderDetermineActivity.this.finish();
                        } else if (TextUtils.equals("6", changeOrderStateIng)) { // 订单取消
                            OrderDetermineActivity.this.finish();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determine_order_layout);

        dialogByProgress = new DialogByProgress(OrderDetermineActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        getPayStyle();
        initView();
        initData();
        getAliPayData();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        weChatBroadcastReceiver = new WeChatBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("WE_CHAT_PAY_SUCCESS");
        filter.addAction("WE_CHAT_PAY_FAILURE");
        registerReceiver(weChatBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(weChatBroadcastReceiver);
    }

    /**
     * 获取默认的支付方式
     */
    private void getPayStyle() {
        payWaySp = OrderDetermineActivity.this.getSharedPreferences("pay_style", 0);
        payWayString = payWaySp.getString("pay", "");
    }

    /**
     * 初始化
     */
    private void initView() {
        determineOrderBack = (ImageView) findViewById(R.id.determine_order_back); // 返回
        determineOrderBack2 = (ImageView) findViewById(R.id.determine_order_back2); // 取消订单的返回
        determineOrderCancel = (TextView) findViewById(R.id.determine_order_cancel); // 取消订单
        determineOrderRl = (RelativeLayout) findViewById(R.id.determine_order_rl);
        orderTime = (TextView) findViewById(R.id.order_time); // 预约时间
        orderAddress = (TextView) findViewById(R.id.order_address); // 我的位置
        orderName = (TextView) findViewById(R.id.order_name); // 客户姓名
        orderPhone = (TextView) findViewById(R.id.order_phone); // 联系电话
        orderNumberPlate = (TextView) findViewById(R.id.order_number_plate); // 车牌
        orderItem = (TextView) findViewById(R.id.order_item); // 清洗项目
        orderRemarks = (TextView) findViewById(R.id.order_remarks); // 备注
        aboutCarPhoto = (CircleImageView) findViewById(R.id.about_car_photo); // 车辆周围照片
        mCover = (CoverFlowViewPager) findViewById(R.id.cover); // 图片轮播控件
        determineOrderTotal = (TextView) findViewById(R.id.determine_order_total); // 清洗项目的总价
        determineOrderBtn = (Button) findViewById(R.id.determine_order_btn); // 确认按钮
    }

    /**
     * 注入参数
     */
    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Longitude = intent.getStringExtra("Longitude"); // 接收的经度信息
        Latitude = intent.getStringExtra("Latitude"); // 接收的纬度信息
        AddressString = intent.getStringExtra("orderAddress"); // 地址
        CarNumber = intent.getStringExtra("carNumber"); // 接收的车牌信息
        userName = intent.getStringExtra("userName"); // 接收的用户名称
        userPhone = intent.getStringExtra("userPhone"); // 接收的用户手机号
        OrderType = intent.getStringExtra("OrderType"); // 接收的订单类型信息
        appointmentTime = intent.getStringExtra("orderTime"); // 预约时间
        orderItemString = intent.getStringExtra("orderItem"); // 清洗的项目
        orderItemList = intent.getStringArrayListExtra("orderItemList"); // 清洗项目的数据集合
        orderItemIdList = intent.getStringArrayListExtra("orderItemIdList"); // 清洗项目的单价数据集合
        photoAroundList = intent.getStringArrayListExtra("photorealist"); // 车辆周围照片集合
        orderRemarksString = intent.getStringExtra("orderRemarks"); // 备注
        orderTotalString = intent.getStringExtra("orderTotal"); // 总价
        for (int i = 0; i < photoAroundList.size() - 1; i++) {
            aroundImageList.add(photoAroundList.get(i).substring(22, 59));
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        OrderTime = format.format(new Date(System.currentTimeMillis()));

        if (photoAroundList != null && photoAroundList.isEmpty()) {
            aboutCarPhoto.setVisibility(View.GONE);
        } else {
            aboutCarPhoto.setVisibility(View.VISIBLE); // TODO 照片轮播显示
            Glide.with(OrderDetermineActivity.this).load(photoAroundList.get(0)).into(aboutCarPhoto);
            aboutCarPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCover.setVisibility(View.VISIBLE);
                    aboutCarPhoto.setVisibility(View.GONE);
                    for (int i = 0; i < photoAroundList.size() - 1; i++) {
                        ImageView img = new ImageView(OrderDetermineActivity.this);
                        Glide.with(OrderDetermineActivity.this).load(photoAroundList.get(i)).into(img);
                        pictureList.add(img);
                    }
                    mCover.setViewList(pictureList);
                }
            });
        }

        if (TextUtils.isEmpty(appointmentTime)) {
            determineOrderRl.setVisibility(View.GONE);
        } else {
            String appointmentTimeString = appointmentTime.substring(0, 2);
            if (TextUtils.equals("今天", appointmentTimeString)) {
                String todayTime1 = sdf.format(new Date(System.currentTimeMillis())); // TODO 获取系统当前时间
                String[] splitTodayTime = appointmentTime.split(":");
                String todayTime2 = splitTodayTime[1].trim();
                String todayHour = DataConversionByShen.getTimeByHour(todayTime2).trim();
                String todayTime3 = splitTodayTime[2].trim();
                String todayMinute = DataConversionByShen.getTimeByMinute(todayTime3).trim();
                AppointmentTime = todayTime1 + " " + todayHour + ":" + todayMinute + ":00";
            } else if (TextUtils.equals("明天", appointmentTimeString)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 1);
                String tomorrowTime1 = sdf.format(c.getTime());
                String[] splitTomorrowTime = appointmentTime.split(":");
                String tomorrowTime2 = splitTomorrowTime[1].trim();
                String tomorrowHour = DataConversionByShen.getTimeByHour(tomorrowTime2).trim();
                String tomorrowTime3 = splitTomorrowTime[2].trim();
                String tomorrowMinute = DataConversionByShen.getTimeByMinute(tomorrowTime3).trim();
                AppointmentTime = tomorrowTime1 + " " + tomorrowHour + ":" + tomorrowMinute + ":00";
            } else {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 2);
                String acquiredTime1 = sdf.format(c.getTime());
                String[] splitAcquiredTime = appointmentTime.split(":");
                String acquiredTime2 = splitAcquiredTime[1].trim();
                String acquiredHour = DataConversionByShen.getTimeByHour(acquiredTime2).trim();
                String acquiredTime3 = splitAcquiredTime[2].trim();
                String acquiredMinute = DataConversionByShen.getTimeByMinute(acquiredTime3).trim();
                AppointmentTime = acquiredTime1 + " " + acquiredHour + ":" + acquiredMinute + ":00";
            }
        }

        if (TextUtils.isEmpty(orderRemarksString)) {
            orderRemarks.setText("无");
        } else {
            orderRemarks.setText(orderRemarksString);
        }

        orderTime.setText(appointmentTime);
        orderName.setText(userName);
        orderPhone.setText(userPhone);
        orderNumberPlate.setText(CarNumber);
        orderAddress.setText(AddressString);
        orderItem.setText(orderItemString);
        determineOrderTotal.setText((orderTotalString) + "0");
    }

    /**
     * 获取支付宝秘钥信息
     */
    private void getAliPayData() {
        Request request = new Request.Builder().url(GlobalConsts.GET_ALIPAY_DATA_URL).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson aliPayGson = new Gson();
                AliPayRequest alipayRequest = aliPayGson.fromJson(bodyString, AliPayRequest.class);
                Message aliPayMsg = Message.obtain();
                aliPayMsg.what = GlobalConsts.GET_ALIPAY_DATA_HANDLER;
                aliPayMsg.obj = alipayRequest;
                handler.sendMessage(aliPayMsg);
            }
        });
    }


    /**
     * 监听
     */
    private void initListener() {
        determineOrderBack.setOnClickListener(this);
        determineOrderCancel.setOnClickListener(this);
        determineOrderBtn.setOnClickListener(this);
    }

    /**
     * 实现监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.determine_order_back:
                OrderDetermineActivity.this.finish();
                break;

            case R.id.determine_order_cancel: // 取消订单
                dialog2 = new DialogByTwoButton(OrderDetermineActivity.this, "提示", "订单尚未支付，确定取消该订单吗？", "取消", "确定");
                dialog2.show();
                dialog2.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                    @Override
                    public void doNegative() {
                        dialog2.dismiss();
                    }

                    @Override
                    public void doPositive() {
                        changOrderState("6", orderAccountID); // 将订单改为取消订单
                    }
                });
                break;

            case R.id.determine_order_btn: // 确认点击生成订单号
                List<ImageFileName> list = new ArrayList<>(); // TODO 照片数据转换
                if (photoAroundList != null && !photoAroundList.isEmpty()) {
                    for (int i = 0; i < aroundImageList.size(); i++) {
                        image = new ImageFileName();
                        image.setImageFileName(aroundImageList.get(i));
                        list.add(image);
                    }
                    photoListStr = orderGson.toJson(list);
                }

                List<OrderItem> orderItemListData = new ArrayList<>(); // TODO 清洗项目数据转换  Log.d("test", "订单的数据---" + orderItemList.toString()); Log.d("test", "价钱的数据---" + orderItemIdList.toString());
                if (!orderItemList.isEmpty() && !orderItemIdList.isEmpty()) {
                    for (int i = 0; i < orderItemList.size(); i++) {
                        orderItemNameOrPrice = new OrderItem();
                        orderItemNameOrPrice.setItemName(orderItemList.get(i));
                        orderItemNameOrPrice.setItemPrice(orderItemIdList.get(i));
                        orderItemListData.add(orderItemNameOrPrice);
                    }
                    orderItemListStr = orderGson.toJson(orderItemListData);
                }

                dialogByProgress.show();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("longitude", Longitude);
                builder.add("latitude", Latitude);
                builder.add("address", AddressString);
                builder.add("carNumber", CarNumber);
                builder.add("orderType", OrderType);
                builder.add("orderTime", OrderTime);
                builder.add("appointmenttime", AppointmentTime);
                builder.add("photorealist", photoListStr);
                builder.add("orderItems", orderItemListStr);
                builder.add("orderTotal", String.valueOf(Float.parseFloat(orderTotalString))); // 订单总价做数据转换
                builder.add("orderAccountID", id);
                builder.add("remark", orderRemarksString);
                builder.add("orderState", "1");

                final FormBody body = builder.build();
                Request request = new Request.Builder().url(GlobalConsts.CREATE_ORDER_URL).post(body).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String bodyString = response.body().string();
                        Gson createOrderGson = new Gson();
                        CreateOrderRequest createOrderRequest = createOrderGson.fromJson(bodyString, CreateOrderRequest.class);
                        Message createOrderMsg = Message.obtain();
                        createOrderMsg.what = GlobalConsts.CREATE_ORDER_HANDLER;
                        createOrderMsg.obj = createOrderRequest;
                        handler.sendMessage(createOrderMsg);
                    }
                });
                break;

            case R.id.pay_way_Balance:  // 余额支付
                payWay = "1";
                payWayBalance.setBackgroundResource(R.mipmap.check);
                payWayWechat.setBackgroundResource(R.mipmap.uncheck);
                payWayAli.setBackgroundResource(R.mipmap.uncheck);
                break;

            case R.id.pay_way_wechat:  // 微信支付
                payWay = "2";
                payWayBalance.setBackgroundResource(R.mipmap.uncheck);
                payWayWechat.setBackgroundResource(R.mipmap.check);
                payWayAli.setBackgroundResource(R.mipmap.uncheck);
                break;

            case R.id.pay_way_ali:  // 支付宝支付
                payWay = "3";
                payWayBalance.setBackgroundResource(R.mipmap.uncheck);
                payWayWechat.setBackgroundResource(R.mipmap.uncheck);
                payWayAli.setBackgroundResource(R.mipmap.check);
                break;

            case R.id.pay_way_btn:  // 确定支付
                if (payWayString.equals("") && payWay.equals("")) { // 如果快捷支付方式未设置
                    Toast.makeText(OrderDetermineActivity.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (TextUtils.equals("3", payWay)) { // 支付宝支付
                        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, "0.01", orderNumber, createOrderTimeStr);
                        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                        String sign = OrderInfoUtil2_0.getSign(params, RSA2_PRIVATE, rsa2);
                        final String orderInfo = orderParam + "&" + sign;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PayTask aliPay = new PayTask(OrderDetermineActivity.this);
                                Map<String, String> aliPayResult = aliPay.payV2(orderInfo, true);
                                Message aliPayMsg = Message.obtain();
                                aliPayMsg.what = GlobalConsts.ALI_PAY_HANDLER;
                                aliPayMsg.obj = aliPayResult;
                                handler.sendMessage(aliPayMsg);
                            }
                        }).start();
                    } else if (TextUtils.equals("2", payWay)) { // 微信支付
                        if (DataConversionByShen.isWeixinAvilible(OrderDetermineActivity.this) == false) {
                            Toast.makeText(OrderDetermineActivity.this, "请先安装微信", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            dialogByProgress.show();
                            String orderTotalString = (orderTotalStr.substring(0, orderTotalStr.length() - 2)) + "00";
                            FormBody.Builder weChatBuilder = new FormBody.Builder();
                            weChatBuilder.add("productName", "动动美车-上门清洗汽车服务");
                            weChatBuilder.add("orderNumber", orderNumber);
                            weChatBuilder.add("money", orderTotalString);
                            final FormBody weChatBody = weChatBuilder.build();
                            Request weChatRequest = new Request.Builder().url(GlobalConsts.WE_CHAT_PAY_URL).post(weChatBody).build();
                            okHttpClient.newCall(weChatRequest).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    final String weChatBody = response.body().string();
                                    Gson weChatGson = new Gson();
                                    WeChatPayRequest weChatRequest = weChatGson.fromJson(weChatBody, WeChatPayRequest.class);
                                    Message weChatMsg = Message.obtain();
                                    weChatMsg.what = GlobalConsts.WE_CHAT_PAY_HANDLER;
                                    weChatMsg.obj = weChatRequest;
                                    handler.sendMessage(weChatMsg);
                                }
                            });
                        }
                    }
                }
                break;
        }
    }

    /**
     * 底部支付页面弹窗
     */
    private void showPayPw() {
        payView = View.inflate(OrderDetermineActivity.this, R.layout.pw_pay_way_layout, null);
        payPw = new PopupWindow(payView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        payWay = "";

        payWayBalance = (ImageView) payView.findViewById(R.id.pay_way_Balance);
        payWayWechat = (ImageView) payView.findViewById(R.id.pay_way_wechat);
        payWayAli = (ImageView) payView.findViewById(R.id.pay_way_ali);
        payWayBtn = (Button) payView.findViewById(R.id.pay_way_btn);

        if (payWayString.equals("balance")) {
            payWayBalance.setBackgroundResource(R.mipmap.check);
            payWay = "1";
        } else if (payWayString.equals("wechat")) {
            payWayWechat.setBackgroundResource(R.mipmap.check);
            payWay = "2";
        } else if (payWayString.equals("alipay")) {
            payWayAli.setBackgroundResource(R.mipmap.check);
            payWay = "3";
        }

        payWayBalance.setOnClickListener(this);
        payWayWechat.setOnClickListener(this);
        payWayAli.setOnClickListener(this);
        payWayBtn.setOnClickListener(this);

        payPw.setFocusable(true);
        payPw.setOutsideTouchable(true);
        payPw.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        payPw.setAnimationStyle(android.R.style.Animation_InputMethod);
        payPw.showAtLocation(payView, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.2f);
        payPw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 支付成功或失败后变更当前订单的状态
     */
    private void changOrderState(String payState, String orderAccountID) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("orderState", payState);
        builder.add("orderID", orderAccountID);
        final FormBody body = builder.build();
        Request request = new Request.Builder().url(GlobalConsts.CREATE_ORDER_STATE_URL).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson changeOrderStateGson = new Gson();
                OrderStateRequest changeOrderStateRequest = changeOrderStateGson.fromJson(bodyString, OrderStateRequest.class);
                Message changeOrderStateMsg = Message.obtain();
                changeOrderStateMsg.what = GlobalConsts.CREATE_ORDER_STATE_HANDLER;
                changeOrderStateMsg.obj = changeOrderStateRequest;
                handler.sendMessage(changeOrderStateMsg);
            }
        });
    }

    /**
     * 支付成功的弹窗
     */
    private void showPaySuccessPW() {
        paySuccessView = View.inflate(OrderDetermineActivity.this, R.layout.pw_pay_success_layout, null);
        int width = ScreenUtils.getScreenWidth(OrderDetermineActivity.this) / 10 * 9;
        paySuccessPW = new PopupWindow(paySuccessView, width, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView paySuccessBtn = (TextView) paySuccessView.findViewById(R.id.pay_success_btn);
        paySuccessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paySuccessPW != null && paySuccessPW.isShowing()) {
                    paySuccessPW.dismiss();
                    payState = "2";
                    changOrderState(payState, orderAccountID);
                }
            }
        });

        paySuccessPW.setFocusable(true);
        paySuccessPW.setOutsideTouchable(true);
        paySuccessPW.setBackgroundDrawable(new ColorDrawable(0x000000));
        paySuccessPW.setAnimationStyle(android.R.style.Animation_InputMethod);
        paySuccessPW.showAtLocation(paySuccessView, Gravity.CENTER, 0, 0);
        backgroundAlpha(0.2f);
        paySuccessPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 支付失败的弹窗
     */
    private void showPayFailurePW() {
        payFailureView = View.inflate(OrderDetermineActivity.this, R.layout.pw_pay_failure_layout, null);
        int width = ScreenUtils.getScreenWidth(OrderDetermineActivity.this) / 10 * 9;
        payFailurePW = new PopupWindow(payFailureView, width, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button payCancelBtn = (Button) payFailureView.findViewById(R.id.pay_cancel_btn); // 取消支付
        Button payFailureBtn = (Button) payFailureView.findViewById(R.id.pay_failure_btn); // 继续支付

        payCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payFailurePW != null && payFailurePW.isShowing()) {
                    payFailurePW.dismiss();
                    payState = "6";
                    changOrderState(payState, orderAccountID);
                }
            }
        });

        payFailureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayPw();
                if (payFailurePW != null && payFailurePW.isShowing()) {
                    payFailurePW.dismiss();
                }
            }
        });

        payFailurePW.setFocusable(true);
        payFailurePW.setOutsideTouchable(true);
        payFailurePW.setBackgroundDrawable(new ColorDrawable(0x000000));
        payFailurePW.setAnimationStyle(android.R.style.Animation_InputMethod);
        payFailurePW.showAtLocation(payFailureView, Gravity.CENTER, 0, 0);
        backgroundAlpha(0.2f);
        payFailurePW.setOnDismissListener(new PopupWindow.OnDismissListener() {
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
        WindowManager.LayoutParams lp = OrderDetermineActivity.this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0  
        OrderDetermineActivity.this.getWindow().setAttributes(lp);
        OrderDetermineActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 微信支付后的状态结果
     */
    private class WeChatBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "WE_CHAT_PAY_SUCCESS":
                    if (payPw != null && payPw.isShowing()) {
                        payPw.dismiss();
                        showPaySuccessPW();
                    }
                    break;

                case "WE_CHAT_PAY_FAILURE":
                    if (payPw != null && payPw.isShowing()) {
                        payPw.dismiss();
                        showPayFailurePW();
                    }
                    break;
            }
        }
    }

    /**
     * 支付宝支付成功后保存数据，方便处理退款业务
     */
    private void saveAliPay() {
        if (dialogByProgress != null && !dialogByProgress.isShowing()) {
            dialogByProgress.show();
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userId", id);
        builder.add("orderNumber", orderNumber);
        builder.add("payTranNo", tradeNo);
        builder.add("total", "0.01");
        builder.add("refudDes", "申请退款");
        FormBody body = builder.build();
        Request request = new Request.Builder().url(GlobalConsts.SAVE_ALI_PAY_URL).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                SaveAliPayRequest saveAliPayRequest = orderGson.fromJson(bodyString, SaveAliPayRequest.class);
                Message saveAliPayMsg = Message.obtain();
                saveAliPayMsg.what = GlobalConsts.SAVE_ALI_PAY_HANDLER;
                saveAliPayMsg.obj = saveAliPayRequest;
                handler.sendMessage(saveAliPayMsg);
            }
        });
    }

    private void HideDialogByProgress() {
        if (dialogByProgress != null && dialogByProgress.isShowing()) {
            dialogByProgress.dismiss();
        }
    }
}
