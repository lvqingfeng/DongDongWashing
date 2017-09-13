package dongdongwashing.com.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dongdongwashing.com.R;
import dongdongwashing.com.entity.SMSCode.SmsCodeRequest;
import dongdongwashing.com.entity.SMSCode.SmsCodeResult;
import dongdongwashing.com.ui.MainActivity;
import dongdongwashing.com.ui.PersonalCenter.ForgetPasswordActivity;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.GlobalConsts;
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

public class CommonLoginFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private EditText loginUserNameEt; // 输入手机号
    private ImageView loginUserNameClear; // 清空手机号
    private ImageView loginPassWordShow; // 输入密码
    private EditText loginPassWordEt; // 显示密码
    private TextView forgetPasswordTv; // 忘记密码
    private Button loginBtn; // 登录

    private boolean show = false;
    private String userName; // 获取输入的手机号
    private String loginPassword; // 获取输入的密码

    private DialogByOneButton dialogByOneButton;
    private DialogByProgress dialogByProgress;
    private String commonLoginMsg, commonLoginId;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.COMMON_LOGIN_HANDLER:
                    if (dialogByProgress != null && dialogByProgress.isShowing()) {
                        dialogByProgress.dismiss();
                    }
                    SmsCodeRequest commonLoginRequest = (SmsCodeRequest) msg.obj;
                    commonLoginMsg = commonLoginRequest.getMsg();
                    if (commonLoginMsg.equals("用户数据!")) {
                        SmsCodeResult commonLoginResult = commonLoginRequest.getData();
                        commonLoginId = commonLoginResult.getId();
                        SharedPreferences.Editor editor = CommonLoginFragment.this.getActivity().getSharedPreferences("user_info", 0).edit();
                        editor.putString("uid", commonLoginId);
                        editor.apply();
                        UmengUtils.onLogin(commonLoginId);
                        dialogByOneButton = new DialogByOneButton(CommonLoginFragment.this.getActivity(), "提示", "登录成功", "确定");
                        dialogByOneButton.show();
                        dialogByOneButton.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialogByOneButton.dismiss();
                                startActivity(new Intent(CommonLoginFragment.this.getActivity(), MainActivity.class));
                                CommonLoginFragment.this.getActivity().finish();
                            }
                        });
                    } else if (commonLoginMsg.equals("数据为空!")) {
                        dialogByOneButton = new DialogByOneButton(CommonLoginFragment.this.getActivity(), "提示", "账号或密码输入错误，请重新输入", "确定");
                        dialogByOneButton.show();
                        dialogByOneButton.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialogByOneButton.dismiss();
                            }
                        });
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_common_login_layout, container, false);
            dialogByProgress = new DialogByProgress(CommonLoginFragment.this.getActivity());
            dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);
            initView(rootView);
            initListener();
        }
        return rootView;
    }

    /**
     * 初始化控件
     *
     * @param rootView
     */
    private void initView(View rootView) {
        loginUserNameClear = (ImageView) rootView.findViewById(R.id.login_username_clear);
        loginPassWordShow = (ImageView) rootView.findViewById(R.id.login_password_show);
        forgetPasswordTv = (TextView) rootView.findViewById(R.id.forget_password_tv);
        loginBtn = (Button) rootView.findViewById(R.id.login_btn);

        /**
         * 手机号码输入实现动态监听
         */
        loginUserNameEt = (EditText) rootView.findViewById(R.id.login_username_et);
        loginUserNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    loginUserNameClear.setVisibility(View.INVISIBLE);
                } else {
                    loginUserNameClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * 密码输入实现动态监听
         */
        loginPassWordEt = (EditText) rootView.findViewById(R.id.login_password_et);
        loginPassWordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    loginPassWordShow.setVisibility(View.INVISIBLE);
                } else {
                    loginPassWordShow.setVisibility(View.VISIBLE);
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
        loginUserNameEt.setOnClickListener(this);
        loginUserNameClear.setOnClickListener(this);
        loginPassWordEt.setOnClickListener(this);
        loginPassWordShow.setOnClickListener(this);
        forgetPasswordTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    /**
     * 监听方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back:
                this.getActivity().finish();
                break;

            case R.id.forget_password_tv: // 忘记密码
                startActivity(new Intent(CommonLoginFragment.this.getActivity(), ForgetPasswordActivity.class));
                break;

            case R.id.login_btn: // 登录
                loginSubmit();
                break;

            case R.id.login_username_clear:
                loginUserNameEt.setText("");
                break;

            case R.id.login_password_show:
                doShow();
                break;
        }
    }

    /**
     * 登录
     */
    private void loginSubmit() {
        userName = loginUserNameEt.getText().toString().trim();
        loginPassword = loginPassWordEt.getText().toString().trim();
        if ("".equals(userName)) {
            Toast.makeText(this.getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (isRegistMobileNO() == false) {
            Toast.makeText(this.getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if ("".equals(loginPassword)) {
            Toast.makeText(this.getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dialogByProgress.show();
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("mobilePhone", userName);
            builder.add("password", loginPassword);
            builder.add("loginType", "0");
            builder.add("valicateCode", "");
            FormBody body = builder.build();
            Request request = new Request.Builder().url(GlobalConsts.LOGIN_URL).post(body).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String bodyString = response.body().string();
                    Gson commonLoginGson = new Gson();
                    SmsCodeRequest commonLoginRequest = commonLoginGson.fromJson(bodyString, SmsCodeRequest.class);
                    Message commonLoginMsg = Message.obtain();
                    commonLoginMsg.what = GlobalConsts.COMMON_LOGIN_HANDLER;
                    commonLoginMsg.obj = commonLoginRequest;
                    handler.sendMessage(commonLoginMsg);
                }
            });
        }
    }

    /**
     * 正则验证手机号
     */
    public boolean isRegistMobileNO() {
        Pattern patternRegistMobile = Pattern.compile(GlobalConsts.REGEX_MOBILE, Pattern.CASE_INSENSITIVE);
        Matcher matcherRegistMobile = patternRegistMobile.matcher(userName);
        return matcherRegistMobile.matches();
    }

    /**
     * 隐藏或者显示密码
     */
    private void doShow() {
        if (show) {
            // 隐藏密码
            loginPassWordShow.setImageResource(R.mipmap.btn_passno);
            loginPassWordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            show = false;
        } else {
            // 显示密码
            loginPassWordShow.setImageResource(R.mipmap.btn_pass);
            loginPassWordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            show = true;
        }
    }
}
