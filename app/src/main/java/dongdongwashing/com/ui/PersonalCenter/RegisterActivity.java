package dongdongwashing.com.ui.PersonalCenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.CoderResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.SMSCode.SmsCodeRequest;
import dongdongwashing.com.entity.SMSCode.SmsCodeResult;
import dongdongwashing.com.ui.MainActivity;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.DialogByTwoButton;
import dongdongwashing.com.util.GlobalConsts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/3/31.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView registerBack; // 返回
    private EditText registerPhoneEt; // 输入注册手机号
    private Button registerVerificationCode; // 获取验证码
    private EditText registerCodeEt; // 输入验证码
    private EditText registerPasswordEt; // 输入密码
    private ImageView registerPasswordShow; // 显示密码
    private Button registerBtn; // 注册

    private boolean show = false;
    private String registerPhone;
    private String registerCode = "";
    private String code = "";
    private String registerPassword;
    private TimeCount time; // 点击获取验证码后显示60秒倒计时

    private DialogByTwoButton dialog;
    private DialogByOneButton dialog1;
    private DialogByProgress dialogPro;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String userId; // 注册后的用户id
    private String SmsCodeMsg; // 注册后的返回信息

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.REGISTER_HANDLER:
                    SmsCodeRequest registerRequest = (SmsCodeRequest) msg.obj;
                    SmsCodeResult registerResult = registerRequest.getData();
                    SmsCodeMsg = registerRequest.getMsg();
                    if (dialogPro != null && dialogPro.isShowing()) {
                        dialogPro.dismiss();
                    }
                    if (SmsCodeMsg.equals("手机号已注册，请选择其它手机号注册")) {
                        dialog = new DialogByTwoButton(RegisterActivity.this,
                                "提示",
                                "手机号已注册，请选择其它手机号注册或直接登录",
                                "取消",
                                "确定"
                        );
                        dialog.show();
                        dialog.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                            @Override
                            public void doNegative() {
                                dialog.dismiss();
                            }

                            @Override
                            public void doPositive() {
                                dialog.dismiss();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                RegisterActivity.this.finish();
                            }
                        });
                    } else if (SmsCodeMsg.equals("注册成功")) {
                        if (dialogPro != null && dialogPro.isShowing()) {
                            dialogPro.dismiss();
                        }
                        userId = registerResult.getId();
                        SharedPreferences spf = RegisterActivity.this.getSharedPreferences("user_info", 0);
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putString("uid", userId);
                        editor.commit();

                        dialog1 = new DialogByOneButton(RegisterActivity.this,
                                "提示",
                                "注册成功",
                                "确定"
                        );
                        dialog1.show();
                        dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog1.dismiss();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                RegisterActivity.this.finish();
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
        setContentView(R.layout.activity_register_layout);
        dialogPro = new DialogByProgress(RegisterActivity.this);
        dialogPro.getWindow().setBackgroundDrawableResource(R.color.transparent);
        time = new TimeCount(60000, 1000); // 构造CountDownTimer对象
        initView();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        registerBack = (ImageView) findViewById(R.id.register_back);
        registerPhoneEt = (EditText) findViewById(R.id.register_phone_et);
        registerVerificationCode = (Button) findViewById(R.id.register_verification_code);
        registerCodeEt = (EditText) findViewById(R.id.register_code_et);
        registerPasswordShow = (ImageView) findViewById(R.id.register_password_show);
        registerBtn = (Button) findViewById(R.id.register_btn);
        registerPasswordEt = (EditText) findViewById(R.id.register_password_et);
        registerPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    registerPasswordShow.setVisibility(View.INVISIBLE);
                } else {
                    registerPasswordShow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 监听
     */
    private void initListener() {
        registerBack.setOnClickListener(this);
        registerVerificationCode.setOnClickListener(this);
        registerPasswordShow.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    /**
     * 监听方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back:
                RegisterActivity.this.finish();
                break;

            case R.id.register_verification_code: // 获取验证码
                registerPhone = registerPhoneEt.getText().toString().trim();
                if ("".equals(registerPhone)) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    registerVerificationCode.setClickable(true);
                    registerVerificationCode.setBackgroundResource(R.drawable.background_shape);
                    return;
                } else if (isRegistMobileNO() == false) {
                    Toast.makeText(RegisterActivity.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                    registerVerificationCode.setClickable(true);
                    registerVerificationCode.setBackgroundResource(R.drawable.background_shape);
                    return;
                } else {
                    time.start();
                    String registerCodeUrl = GlobalConsts.REGISTER_CODE_URL + registerPhone;
                    Request registerRequest = new Request.Builder().url(registerCodeUrl).build();
                    okHttpClient.newCall(registerRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String registerString = response.body().string();
                            code = registerString.substring(1, registerString.length() - 1);
                        }
                    });
                }
                break;

            case R.id.register_password_show: // 显示密码
                doShow();
                break;

            case R.id.register_btn: // 注册
                registerSubmit();
                break;
        }
    }

    /**
     * 提交注册
     */
    private void registerSubmit() {
        registerPhone = registerPhoneEt.getText().toString().trim();
        registerCode = registerCodeEt.getText().toString().trim();
        registerPassword = registerPasswordEt.getText().toString().trim();
        if ("".equals(registerPhone)) {
            Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (isRegistMobileNO() == false) {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (code.equals("")) {
            Toast.makeText(RegisterActivity.this, "请点击获取验证码", Toast.LENGTH_SHORT).show();
            return;
        } else if ("".equals(registerCodeEt.getText().toString().trim())) {
            Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        } else if (!code.equals(registerCode)) {
            Toast.makeText(RegisterActivity.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
            return;
        } else if ("".equals(registerPassword)) {
            Toast.makeText(RegisterActivity.this, "请设置密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (registerPassword.length() < 6) {
            Toast.makeText(RegisterActivity.this, "请设置至少六位数密码", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dialogPro.show();
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("mobilePhone", registerPhone);
            builder.add("password", registerPassword);
            builder.add("loginType", "0");
            builder.add("valicateCode", registerCode);
            FormBody body = builder.build();
            Request request = new Request.Builder().url(GlobalConsts.REGISTER_URL).post(body).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String bodyString = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson registerGson = new Gson();
                            SmsCodeRequest registerRequest = registerGson.fromJson(bodyString, SmsCodeRequest.class);
                            Message registerMsg = Message.obtain();
                            registerMsg.what = GlobalConsts.REGISTER_HANDLER;
                            registerMsg.obj = registerRequest;
                            handler.sendMessage(registerMsg);
                        }
                    });
                }
            });
        }
    }

    /**
     * 正则验证手机号
     */
    public boolean isRegistMobileNO() {
        Pattern patternRegistMobile = Pattern.compile(GlobalConsts.REGEX_MOBILE, Pattern.CASE_INSENSITIVE);
        Matcher matcherRegistMobile = patternRegistMobile.matcher(registerPhone);
        return matcherRegistMobile.matches();
    }

    /**
     * 隐藏或者显示密码
     */
    private void doShow() {
        if (show) {
            // 隐藏密码
            registerPasswordShow.setImageResource(R.mipmap.btn_passno);
            registerPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            show = false;
        } else {
            // 显示密码
            registerPasswordShow.setImageResource(R.mipmap.btn_pass);
            registerPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            show = true;
        }
    }

    /**
     * 倒计时的内部类
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); // 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            registerVerificationCode.setText("重新验证");
            registerVerificationCode.setClickable(true);
            registerVerificationCode.setTextColor(getResources().getColor(R.color.white));
            registerVerificationCode.setBackgroundResource(R.drawable.background_shape);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            registerVerificationCode.setClickable(false);
            registerVerificationCode.setTextColor(getResources().getColor(R.color.black));
            registerVerificationCode.setBackgroundResource(R.drawable.background_un_shape);
            registerVerificationCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
