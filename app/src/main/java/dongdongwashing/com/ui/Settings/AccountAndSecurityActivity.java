package dongdongwashing.com.ui.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.getAccountInfo.GetAccountRequest;
import dongdongwashing.com.entity.getAccountInfo.GetAccountResult;
import dongdongwashing.com.ui.PersonalCenter.LoginActivity;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.DialogByTwoButton;
import dongdongwashing.com.util.GlobalConsts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/4/1.
 */

public class AccountAndSecurityActivity extends BaseActivity implements View.OnClickListener {

    private ImageView accountAndSecurityBack; // 返回
    private RelativeLayout accountAndSecurityPhoneRl; // 设置手机号
    private TextView accountAndSecurityPhone; // 显示手机号
    private RelativeLayout settingsPasswordsRl; // 密码设置
    private DialogByProgress dialogByProgress;
    private DialogByOneButton dialog1;
    private DialogByTwoButton dialog2;
    private SharedPreferences spf;
    private String id;
    private String getAccountString;
    private String phoneString;
    private String phoneStr;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.CHANGE_PHONE_HANDLER:
                    GetAccountRequest getAccountRequest = (GetAccountRequest) msg.obj;
                    if (dialogByProgress != null && dialogByProgress.isShowing()) {
                        dialogByProgress.dismiss();
                    }
                    getAccountString = getAccountRequest.getMsg();
                    if (getAccountString.equals("用户信息")) {
                        GetAccountResult getAccountResult = getAccountRequest.getData();
                        phoneStr = getAccountResult.getMobilePhone();
                        phoneString = phoneStr.substring(0, 3) + "****" + phoneStr.substring(7, 11);
                        accountAndSecurityPhone.setText(phoneString);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_and_security_layout);
        dialogByProgress = new DialogByProgress(AccountAndSecurityActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        spf = AccountAndSecurityActivity.this.getSharedPreferences("user_info", 0);
        id = spf.getString("uid", "");

        initView();
        initData();
        initListener();
    }

    /**
     * 初始化
     */
    private void initView() {
        accountAndSecurityBack = (ImageView) findViewById(R.id.account_and_security_back);
        accountAndSecurityPhoneRl = (RelativeLayout) findViewById(R.id.account_and_security_phone_rl);
        accountAndSecurityPhone = (TextView) findViewById(R.id.account_and_security_phone);
        settingsPasswordsRl = (RelativeLayout) findViewById(R.id.settings_passwords_rl);
    }

    /**
     * 根据id获取参数并赋值
     */
    private void initData() {
        if (!id.equals("")) {
            dialogByProgress.show();
            Request request = new Request.Builder().url(GlobalConsts.GET_ACCOUNT_INFO_URL + id).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String bodyString = response.body().string();
                    Gson getAcountGson = new Gson();
                    GetAccountRequest getAccountRequest = getAcountGson.fromJson(bodyString, GetAccountRequest.class);
                    Message getAccountMsg = Message.obtain();
                    getAccountMsg.what = GlobalConsts.CHANGE_PHONE_HANDLER;
                    getAccountMsg.obj = getAccountRequest;
                    handler.sendMessage(getAccountMsg);
                }
            });
        }
    }

    /**
     * 监听
     */
    private void initListener() {
        accountAndSecurityBack.setOnClickListener(this);
        accountAndSecurityPhoneRl.setOnClickListener(this);
        settingsPasswordsRl.setOnClickListener(this);
    }

    /**
     * 监听方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_and_security_back: // 返回
                AccountAndSecurityActivity.this.finish();
                break;

            case R.id.account_and_security_phone_rl: // 修改手机号
                if (id.equals("")) {
                    showDialog();
                } else {
                    Intent intent = new Intent(AccountAndSecurityActivity.this, ReplacePhoneActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("OLD_PHONE", phoneString);
                    startActivity(intent);
                    AccountAndSecurityActivity.this.finish();
                }
                break;

            case R.id.settings_passwords_rl: // 修改密码
                if (id.equals("")) {
                    showDialog();
                } else {
                    startActivity(new Intent(AccountAndSecurityActivity.this, ResetPasswordActivity.class));
                    AccountAndSecurityActivity.this.finish();
                }
                break;
        }
    }

    /**
     * 未登录时提醒
     */
    public void showDialog() {
        dialog2 = new DialogByTwoButton(AccountAndSecurityActivity.this,
                "提示",
                "请先登录或注册",
                "取消",
                "确定"
        );
        dialog2.show();
        dialog2.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
            @Override
            public void doNegative() {
                dialog2.dismiss();
            }

            @Override
            public void doPositive() {
                dialog2.dismiss();
                startActivity(new Intent(AccountAndSecurityActivity.this, LoginActivity.class));
                AccountAndSecurityActivity.this.finish();
            }
        });
    }
}
