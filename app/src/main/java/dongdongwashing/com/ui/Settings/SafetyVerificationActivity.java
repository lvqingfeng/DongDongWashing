package dongdongwashing.com.ui.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.updateAccount.UpdateAccountRequest;
import dongdongwashing.com.entity.updateAccount.UpdateAccountResult;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.GlobalConsts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/4/4.
 */

public class SafetyVerificationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView safetyVerificationBack; // 返回
    private EditText safetyVerificationPhoneEt; // 手机号输入框
    private Button safetyVerificationCode; // 点击获取验证码
    private EditText safetyVerificationCodeEt; // 验证码输入框
    private Button safetyVerificationBtn; // 确认修改手机号

    private boolean show = false;
    private TimeCount time; // 点击获取验证码后显示60秒倒计时
    private String safetyVerificationPhoneString;
    private String safetyVerificationCodeString;

    private DialogByProgress dialogByProgress;
    private DialogByOneButton dialog1;
    private String id;
    private String oldPhoneString;
    private String code = "";

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.CHANGE_PHONE_HANDLER:
                    UpdateAccountRequest changPhoneRequest = (UpdateAccountRequest) msg.obj;
                    String changPhoneString = changPhoneRequest.getMsg();
                    if (dialogByProgress != null && dialogByProgress.isShowing()) {
                        dialogByProgress.dismiss();
                    }

                    if (changPhoneString.equals("设置手机号")) {
                        dialog1 = new DialogByOneButton(SafetyVerificationActivity.this,
                                "提示",
                                "手机号修改成功",
                                "确定"
                        );
                        dialog1.show();
                        dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog1.dismiss();
                                startActivity(new Intent(SafetyVerificationActivity.this, AccountAndSecurityActivity.class));
                                SafetyVerificationActivity.this.finish();
                            }
                        });
                    } else if (changPhoneString.equals("原手机号不正确")) {
                        dialog1 = new DialogByOneButton(SafetyVerificationActivity.this,
                                "提示",
                                "此手机号码已经绑定，请更换新的手机号",
                                "确定"
                        );
                        dialog1.show();
                        dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog1.dismiss();
                            }
                        });
                    } else if (changPhoneString.equals("新的手机号与旧手机号相同，请换另外一个手机号")) {
                        dialog1 = new DialogByOneButton(SafetyVerificationActivity.this,
                                "提示",
                                "新的手机号与旧手机号相同，请换另外一个手机号",
                                "确定"
                        );
                        dialog1.show();
                        dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog1.dismiss();
                            }
                        });
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_verification_layout);

        dialogByProgress = new DialogByProgress(SafetyVerificationActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        oldPhoneString = intent.getStringExtra("OLD_PHONE");

        time = new TimeCount(60000, 1000); // 构造CountDownTimer对象
        initView();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        safetyVerificationBack = (ImageView) findViewById(R.id.safety_verification_back); // 返回
        safetyVerificationPhoneEt = (EditText) findViewById(R.id.safety_verification_phone_et); // 手机号输入框
        safetyVerificationCode = (Button) findViewById(R.id.safety_verification_code); // 点击获取验证码
        safetyVerificationCodeEt = (EditText) findViewById(R.id.safety_verification_code_et); // 验证码输入框
        safetyVerificationBtn = (Button) findViewById(R.id.safety_verification_btn); // 确认修改手机号
    }

    /**
     * 监听
     */
    private void initListener() {
        safetyVerificationBack.setOnClickListener(this);
        safetyVerificationCode.setOnClickListener(this);
        safetyVerificationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.safety_verification_back:
                SafetyVerificationActivity.this.finish();
                break;

            case R.id.safety_verification_code:
                safetyVerificationPhoneString = safetyVerificationPhoneEt.getText().toString().trim();
                if ("".equals(safetyVerificationPhoneString)) {
                    Toast.makeText(SafetyVerificationActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isRegistMobileNO() == false) {
                    Toast.makeText(SafetyVerificationActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    time.start();
                    Request request = new Request.Builder().url(GlobalConsts.REGISTER_CODE_URL + safetyVerificationPhoneString).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String bodyString = response.body().string();
                            code = bodyString.substring(1, bodyString.length() - 1);
                        }
                    });

                }
                break;

            case R.id.safety_verification_btn:
                safetyVerificationPhoneString = safetyVerificationPhoneEt.getText().toString().trim();
                safetyVerificationCodeString = safetyVerificationCodeEt.getText().toString().trim();
                if ("".equals(safetyVerificationPhoneString)) {
                    Toast.makeText(SafetyVerificationActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isRegistMobileNO() == false) {
                    Toast.makeText(SafetyVerificationActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (code.equals("")) {
                    Toast.makeText(SafetyVerificationActivity.this, "请点击获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(safetyVerificationCodeString)) {
                    Toast.makeText(SafetyVerificationActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!safetyVerificationCodeString.equals(code)) {
                    Toast.makeText(SafetyVerificationActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    dialogByProgress.show();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("id", id);
                    builder.add("telephone", oldPhoneString);
                    builder.add("newTelephone", safetyVerificationPhoneString);
                    FormBody body = builder.build();
                    Request request = new Request.Builder().url(GlobalConsts.CHANGE_PHONE_URL).post(body).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String bodyString = response.body().string();
                            Gson changPhoneGson = new Gson();
                            UpdateAccountRequest changPhoneRequest = changPhoneGson.fromJson(bodyString, UpdateAccountRequest.class);
                            Message changPhoneMsg = Message.obtain();
                            changPhoneMsg.what = GlobalConsts.CHANGE_PHONE_HANDLER;
                            changPhoneMsg.obj = changPhoneRequest;
                            handler.sendMessage(changPhoneMsg);
                        }
                    });

                }
                break;
        }
    }

    /**
     * 时间倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); // 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() { // 计时完毕时触发
            safetyVerificationCode.setText("重新验证");
            safetyVerificationCode.setClickable(true);
            safetyVerificationCode.setTextColor(getResources().getColor(R.color.white));
            safetyVerificationCode.setBackgroundResource(R.drawable.background_shape);
        }

        @Override
        public void onTick(long millisUntilFinished) { // 计时过程显示
            safetyVerificationCode.setClickable(false);
            safetyVerificationCode.setTextColor(getResources().getColor(R.color.black));
            safetyVerificationCode.setBackgroundResource(R.drawable.background_un_shape);
            safetyVerificationCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    /**
     * 正则验证手机号
     */
    public boolean isRegistMobileNO() {
        Pattern patternRegistMobile = Pattern.compile(GlobalConsts.REGEX_MOBILE, Pattern.CASE_INSENSITIVE);
        Matcher matcherRegistMobile = patternRegistMobile.matcher(safetyVerificationPhoneString);
        return matcherRegistMobile.matches();
    }
}
