package dongdongwashing.com.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import dongdongwashing.com.R;
import dongdongwashing.com.entity.getApk.ApkRequest;
import dongdongwashing.com.entity.getApk.ApkResult;
import dongdongwashing.com.ui.MainActivity;
import dongdongwashing.com.util.GlobalConsts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/6/14.
 */

public class FlashActivity extends Activity implements View.OnClickListener {

    private int count = 3;
    private SharedPreferences spf;
    private ImageView flashIv; // 背景照片
    private RelativeLayout flashRl;
    private TextView flashTv2; // 倒计时文字
    private String id, type;
    private String newVersionName, apkUrl;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.GET_APK_HANDLER: // 更新app
                    ApkRequest apkRequest = (ApkRequest) msg.obj;
                    String apkString = apkRequest.getMsg();
                    if (apkString.equals("下载成功")) {
                        ApkResult apkResult = apkRequest.getData();
                        newVersionName = apkResult.getVersion();
                        apkUrl = GlobalConsts.DONG_DONG_IMAGE_URL + apkResult.getSysPath();
                    }
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(ContextCompat.getColor(FlashActivity.this, R.color.wel_status_color));
        }

        getVersionNameByService(); // 获取服务器版本号及下载地址

        spf = FlashActivity.this.getSharedPreferences("user_info", 0);
        id = spf.getString("uid", "");
        type = spf.getString("type", "");

        initView();
        initData();
        initListener();
    }

    /**
     * 获取服务器版本号及下载地址
     */
    private void getVersionNameByService() {
        Request request = new Request.Builder().url(GlobalConsts.GET_APK_URL).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson apkGson = new Gson();
                ApkRequest apkRequest = apkGson.fromJson(bodyString, ApkRequest.class);
                Message apkMsg = Message.obtain();
                apkMsg.what = GlobalConsts.GET_APK_HANDLER;
                apkMsg.obj = apkRequest;
                handler.sendMessage(apkMsg);
            }
        });
    }

    private void initData() {
        flashRl.getBackground().setAlpha(100); // 设置背景透明度 0~255透明度值 ，0为完全透明，255为不透明

//        int width = ScreenUtils.getScreenWidth(FlashActivity.this);
//        int height = ScreenUtils.getScreenHeight(FlashActivity.this);

        WindowManager windowManager = FlashActivity.this.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        Glide.with(FlashActivity.this).load(R.drawable.wel).override(width, height).fitCenter().into(flashIv);

        count = 3;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flashTv2.setText(String.valueOf(count));
                count = count - 1;
                if (count >= 1) {
                    handler.postDelayed(this, 850);
                }
            }
        }, 850);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                intent.putExtra("newVersionName", newVersionName);
                intent.putExtra("apkUrl", apkUrl);
                startActivity(intent);
                FlashActivity.this.finish();
            }
        }, 3400);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        flashIv = (ImageView) findViewById(R.id.flash_iv);
        flashRl = (RelativeLayout) findViewById(R.id.flash_rl);
        flashTv2 = (TextView) findViewById(R.id.flash_tv2);
    }

    /**
     * 监听
     */
    private void initListener() {
        flashRl.setOnClickListener(this);
    }

    /**
     * 实现监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flash_rl:
                handler.removeCallbacksAndMessages(null);
                Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                intent.putExtra("newVersionName", newVersionName);
                intent.putExtra("apkUrl", apkUrl);
                startActivity(intent);
                FlashActivity.this.finish();
                break;
        }
    }
}
