package dongdongwashing.com.com;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.IOException;

import dongdongwashing.com.entity.BindTokenRequest;
import dongdongwashing.com.entity.PushRequest;
import dongdongwashing.com.ui.MainActivity;
import dongdongwashing.com.util.GlobalConsts;
import dongdongwashing.com.util.NetUtil;
import dongdongwashing.com.util.UmengUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/3/31.
 */

public class washApplication extends Application {

    private static Context context;
    private static Application mApplication;
    public static int mNetWorkState;
    private Intent orderIntent = new Intent();

    public static synchronized Application getInstance() {
        return mApplication;
    }

    private SharedPreferences spf;
    private String devicetoken, userID, orderId, orderState, isDevicetoken;
    private String AppType = "1";
    private String UserType = "1";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.BIND_DEVICE_TOKEN_HANDLER:
                    BindTokenRequest bindTokenRequest = (BindTokenRequest) msg.obj;
                    if (bindTokenRequest.getIsSucess().equals("true")) {
                        SharedPreferences tokenSpf = mApplication.getSharedPreferences("user_info", 0);
                        SharedPreferences.Editor editor = tokenSpf.edit();
                        editor.putString("device_token", "true");
                        editor.commit();
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mApplication = this;
        initData();

        spf = mApplication.getSharedPreferences("user_info", 0);
        userID = spf.getString("uid", "");
        isDevicetoken = spf.getString("device_token", "");

        TbsDownloader.needDownload(getApplicationContext(), false);
        QbSdk.allowThirdPartyAppDownload(true);
        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, null);

        UMShareAPI.get(this); // 友盟分享
        UmengUtils.initUmeng(); // 初始化友盟统计

        PushAgent mPushAgent = PushAgent.getInstance(this); // 友盟消息推送
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); // 声音
        mPushAgent.register(new IUmengRegisterCallback() { // 注册友盟推送服务，每次调用register方法都会回调该接口
            @Override
            public void onSuccess(String deviceToken) { // 当注册成功后会返回device token
                Log.d("test", "动动美车客户端-友盟推送注册成功后返回的deviceToken----" + deviceToken);
                if (!TextUtils.isEmpty(userID)) {
                    if (TextUtils.isEmpty(isDevicetoken)) {
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("devicetoken", deviceToken);
                        builder.add("userID", userID);
                        builder.add("appType", AppType);
                        builder.add("userType", UserType);
                        FormBody body = builder.build();
                        Request request = new Request.Builder().url(GlobalConsts.BIND_DEVICE_TOKEN_URL).post(body).build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String bodyString = response.body().string();
                                Gson bindTokenGson = new Gson();
                                BindTokenRequest bindTokenRequest = bindTokenGson.fromJson(bodyString, BindTokenRequest.class);
                                Message bindTokenMsg = Message.obtain();
                                bindTokenMsg.what = GlobalConsts.BIND_DEVICE_TOKEN_HANDLER;
                                bindTokenMsg.obj = bindTokenRequest;
                                handler.sendMessage(bindTokenMsg);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        /**
         * 友盟推送消息的点击方法
         */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                // {"MsgType":"Order","Data":{"OrderId":"B1873B86-8A18-4D70-A9A4-C3369D8B1F8F","OrderState":"3"}}
                Log.d("test", "接收友盟推送的消息-----" + uMessage.custom.toString());
                Gson pushGson = new Gson();
                PushRequest pushRequest = pushGson.fromJson(uMessage.custom, PushRequest.class);
                PushRequest.PushResult pushResult = pushRequest.getData();
                orderId = pushResult.getOrderId();
                orderState = pushResult.getOrderState();

                if ("2".equals(orderState)) { // 下订单后待接单
                    orderIntent.setAction("ORDER_TO_BE");
                    orderIntent.putExtra("ORDER_ID_TO_BE", orderId);
                    orderIntent.putExtra("ORDER_STATE_TO_BE", orderState);
                    washApplication.getContext().sendBroadcast(orderIntent);
                }

                if ("3".equals(orderState)) { // 已接收订单
                    orderIntent.setAction("ORDER_ACCEPT");
                    orderIntent.putExtra("ORDER_ID_ACCEPT", orderId);
                    orderIntent.putExtra("ORDER_STATE_ACCEPT", orderState);
                    washApplication.getContext().sendBroadcast(orderIntent);
                }

                if ("4".equals(orderState)) { // 订单进行中
                    orderIntent.setAction("ORDER_PROCESSING");
                    orderIntent.putExtra("ORDER_ID_PROCESSING", orderId);
                    orderIntent.putExtra("ORDER_STATE_PROCESSING", orderState);
                    washApplication.getContext().sendBroadcast(orderIntent);
                }

                if ("5".equals(orderState)) { // 订单已完成
                    orderIntent.setAction("ORDER_COMPLETED");
                    orderIntent.putExtra("ORDER_ID_COMPLETED", orderId);
                    orderIntent.putExtra("ORDER_STATE_COMPLETED", orderState);
                    washApplication.getContext().sendBroadcast(orderIntent);
                }
            }

            @Override
            public void launchApp(Context context, UMessage uMessage) {
                super.launchApp(context, uMessage);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        Thread.setDefaultUncaughtExceptionHandler(restartHandler);

    }

    /**
     * 创建服务用于捕获崩溃异常
     */
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp(); // 发生崩溃异常时,重启应用
        }
    };

    public void restartApp() {
        Intent intent = new Intent(mApplication, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mApplication.startActivity(intent);
        // android.os.Process.killProcess(android.os.Process.myPid());  // 结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }

    /**
     * 分享各个平台的appkey
     */ {
        PlatformConfig.setSinaWeibo("3157033235", "7b750e9c49d639be1f60f36ffb0b746d", "http://sns.whalecloud.com/sina2/callback"); // 新浪微博 2017/4/30
        PlatformConfig.setQQZone("1106114480", "ZT68pvUmLSDODhbr"); // QQ空间 2017/4/30
        PlatformConfig.setWeixin("wx01de9ada30b9757c", "4ec4ee2d4092dd4fef9ea3b840715701"); // 微信

        Config.DEBUG = true; // 友盟分享错误日志 tag = umengsocial
    }

    public static Context getContext() {
        return context;
    }

    private void initData() {
        mNetWorkState = NetUtil.getNetWorkState(this);
    }
}
