package dongdongwashing.com.ui.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.io.IOException;

import dongdongwashing.com.entity.getApk.ApkRequest;
import dongdongwashing.com.entity.getApk.ApkResult;
import dongdongwashing.com.ui.MainActivity;
import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.util.DataConversionByShen;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.DialogByTwoButton;
import dongdongwashing.com.util.Downloader;
import dongdongwashing.com.util.GlobalConsts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static dongdongwashing.com.R.id.camera;
import static dongdongwashing.com.R.id.setting_service_agreement_rl;

/**
 * Created by 沈 on 2017/3/31.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView settingsBack, settingsCheckUpdateIv;
    private RelativeLayout settingsAccountAndSecurityRl; // 账号与安全
    private RelativeLayout settingServiceModelRl; // 服务车型
    private RelativeLayout settingServiceAgreementRl; // 服务协议
    private RelativeLayout settingsCheckUpdateRl; // 检查更新
    private RelativeLayout settingsAboutUsRl; // 关于我们
    private RelativeLayout settingsVersionRl; // 版本
    private Button signOutBtn; // 退出登录
    private PopupWindow signOutPW; // 退出登录的Popupwindow
    private Button exitLoginBt, exitLoginCancelBt;
    private String versionName, newVersionName, apkUrl;
    private Downloader downloadAPK;
    private DialogByTwoButton dialogByTwoButton;
    private DialogByProgress dialogByProgress;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.GET_APK_HANDLER:
                    ApkRequest apkRequest = (ApkRequest) msg.obj;
                    String apkString = apkRequest.getMsg();
                    if (apkString.equals("下载成功")) {
                        ApkResult apkResult = apkRequest.getData();
                        newVersionName = apkResult.getVersion();
                        apkUrl = GlobalConsts.DONG_DONG_IMAGE_URL + apkResult.getSysPath();
                        if (dialogByProgress != null && dialogByProgress.isShowing()) {
                            dialogByProgress.dismiss();
                            if (!versionName.equals(newVersionName)) {
                                settingsCheckUpdateIv.setVisibility(View.VISIBLE);
                            } else {
                                settingsCheckUpdateIv.setVisibility(View.GONE);
                            }
                        }
                    }
                    break;
            }
        }
    };

    private RadioButton realTimeTrafficOpen, realTimeTrafficClose; // 实时路况
    private RadioButton soundEffectOpen, soundEffectClose; // 音效提示

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_layout);

        dialogByProgress = new DialogByProgress(SettingsActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        initView();
        getVersionName();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        settingsBack = (ImageView) findViewById(R.id.settings_back); // 返回
        settingsCheckUpdateIv = (ImageView) findViewById(R.id.settings_check_update_iv);
        settingsAccountAndSecurityRl = (RelativeLayout) findViewById(R.id.settings_account_and_security_rl); // 账号与安全
        settingServiceModelRl = (RelativeLayout) findViewById(R.id.setting_service_model_rl); // 服务车型
        settingServiceAgreementRl = (RelativeLayout) findViewById(setting_service_agreement_rl); // 服务协议
        settingsCheckUpdateRl = (RelativeLayout) findViewById(R.id.settings_check_update_rl); // 检查更新
        settingsAboutUsRl = (RelativeLayout) findViewById(R.id.settings_about_us_rl); // 关于我们
        settingsVersionRl = (RelativeLayout) findViewById(R.id.settings_version_rl); // 版本
        signOutBtn = (Button) findViewById(R.id.sign_out_btn); // 退出登录
        realTimeTrafficOpen = (RadioButton) findViewById(R.id.real_time_traffic_open);
        realTimeTrafficClose = (RadioButton) findViewById(R.id.real_time_traffic_close);
        soundEffectOpen = (RadioButton) findViewById(R.id.sound_effect_open);
        soundEffectClose = (RadioButton) findViewById(R.id.sound_effect_close);
    }

    /**
     * 监听
     */
    private void initListener() {
        settingsBack.setOnClickListener(this);
        settingsAccountAndSecurityRl.setOnClickListener(this);
        settingServiceModelRl.setOnClickListener(this);
        settingServiceAgreementRl.setOnClickListener(this);
        settingsCheckUpdateRl.setOnClickListener(this);
        settingsAboutUsRl.setOnClickListener(this);
        realTimeTrafficOpen.setOnClickListener(this);
        realTimeTrafficClose.setOnClickListener(this);
        soundEffectOpen.setOnClickListener(this);
        soundEffectClose.setOnClickListener(this);
        signOutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_back:
                SettingsActivity.this.finish();
                break;

            case R.id.settings_account_and_security_rl: // 账号与安全
                startActivity(new Intent(SettingsActivity.this, AccountAndSecurityActivity.class));
                break;

            case R.id.setting_service_model_rl: // 服务车型
                startActivity(new Intent(SettingsActivity.this, ServiceModelActivity.class));
                break;

            case R.id.setting_service_agreement_rl: // 服务协议
                startActivity(new Intent(SettingsActivity.this, ServiceAgreementActivity.class));
                break;

            case R.id.settings_check_update_rl: // 检查更新
                if (!versionName.equals(newVersionName)) {
                    dialogByTwoButton = new DialogByTwoButton(SettingsActivity.this, "提示", "有新版本可以更新,是否下载", "取消", "确定");
                    dialogByTwoButton.show();
                    dialogByTwoButton.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                        @Override
                        public void doNegative() {
                            dialogByTwoButton.dismiss();
                        }

                        @Override
                        public void doPositive() {
                            dialogByTwoButton.dismiss();
                            downloadAPK = new Downloader(SettingsActivity.this);
                            downloadAPK.downloadAPK(apkUrl, "debug.apk");
                        }
                    });
                } else {
                    final DialogByOneButton dialog = new DialogByOneButton(SettingsActivity.this, "提示", "当前已是最新版本，无需更新", "确定");
                    dialog.show();
                    dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                        @Override
                        public void doPositive() {
                            dialog.dismiss();
                        }
                    });
                }
                break;

            case R.id.settings_about_us_rl: // 关于我们
                startActivity(new Intent(SettingsActivity.this, AboutUsActivity.class));
                break;

            case R.id.settings_version_rl: // 版本

                break;

            case R.id.sign_out_btn: // 退出登录
                showSignOutPw();
                break;
        }
    }

    /**
     * 获取版本号
     */
    private void getVersionName() {
        versionName = DataConversionByShen.getVersionName(SettingsActivity.this, versionName);
        dialogByProgress.show();
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

    /**
     * 退出登录
     */
    private void showSignOutPw() {
        View signOutPopopView = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.exit_login_pw, null);

        signOutPW = new PopupWindow(signOutPopopView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        signOutPW.setFocusable(true);
        signOutPW.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        signOutPW.setAnimationStyle(android.R.style.Animation_InputMethod);
        signOutPW.showAtLocation(signOutPopopView, Gravity.BOTTOM, 0, 0);

        exitLoginBt = (Button) signOutPopopView.findViewById(R.id.exit_login_bt);
        exitLoginCancelBt = (Button) signOutPopopView.findViewById(R.id.exit_login_cancel_bt);

        /**
         * 退出登录
         */
        exitLoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutPW.dismiss();
                dialogByTwoButton = new DialogByTwoButton(SettingsActivity.this,
                        "提示",
                        "您确定要退出登录吗？",
                        "取消",
                        "确定"
                );
                dialogByTwoButton.show();
                dialogByTwoButton.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                    @Override
                    public void doNegative() {
                        dialogByTwoButton.dismiss();
                    }

                    @Override
                    public void doPositive() {
                        dialogByTwoButton.dismiss();
                        SharedPreferences sdf = SettingsActivity.this.getSharedPreferences("user_info", 0);
                        if (sdf != null) {
                            sdf.edit().clear().commit();
                        }
                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        SettingsActivity.this.finish();
                    }
                });
            }
        });

        /**
         * 取消
         */
        exitLoginCancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutPW.dismiss();
            }
        });
    }
}
