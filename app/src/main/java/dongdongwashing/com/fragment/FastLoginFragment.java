package dongdongwashing.com.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dongdongwashing.com.R;
import dongdongwashing.com.entity.SMSCode.SmsCodeRequest;
import dongdongwashing.com.entity.SMSCode.SmsCodeResult;
import dongdongwashing.com.ui.MainActivity;
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

public class FastLoginFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private EditText fastLoginUserNameEt; // 密码输入
    private EditText fastLoginPasswordEt; // 验证码输入
    private Button fastLoginVerificationCode; // 获取验证码
    private Button fastLoginBtn; // 登录

    private String fastUserPhone;
    private String FastLoginCode;
    private TimeCount time; // 点击获取验证码后显示60秒倒计时
    private String code = "";
    private DialogByProgress dialogByProgress;
    private DialogByOneButton dialogByOneButton;
    private String fastLoginMsg, fastLoginId;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.FAST_LOGIN_HANDLER:
                    if (dialogByProgress != null && dialogByProgress.isShowing()) {
                        dialogByProgress.dismiss();
                    }
                    SmsCodeRequest fastLoginRequest = (SmsCodeRequest) msg.obj;
                    fastLoginMsg = fastLoginRequest.getMsg();
                    if (fastLoginMsg.equals("用户数据!")) {
                        SmsCodeResult fastLoginResult = fastLoginRequest.getData();
                        fastLoginId = fastLoginResult.getId();
                        SharedPreferences spf = FastLoginFragment.this.getActivity().getSharedPreferences("user_info", 0);
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putString("uid", fastLoginId);
                        editor.commit();
                        UmengUtils.onLogin(fastLoginId);
                        dialogByOneButton = new DialogByOneButton(FastLoginFragment.this.getActivity(),
                                "提示",
                                "登录成功",
                                "确定"
                        );
                        dialogByOneButton.show();
                        dialogByOneButton.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialogByOneButton.dismiss();
                                startActivity(new Intent(FastLoginFragment.this.getActivity(), MainActivity.class));
                                FastLoginFragment.this.getActivity().finish();
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
            rootView = inflater.inflate(R.layout.fragment_fast_login_layout, container, false);
            time = new TimeCount(60000, 1000); // 构造CountDownTimer对象
            dialogByProgress = new DialogByProgress(FastLoginFragment.this.getActivity());
            dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);
            initView();
            initListener();
        }
        return rootView;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        fastLoginUserNameEt = (EditText) rootView.findViewById(R.id.fast_login_username_et); // 密码输入
        fastLoginPasswordEt = (EditText) rootView.findViewById(R.id.fast_login_password_et); // 验证码输入
        fastLoginVerificationCode = (Button) rootView.findViewById(R.id.fast_login_verification_code); // 获取验证码
        fastLoginBtn = (Button) rootView.findViewById(R.id.fast_login_btn); // 登录
    }

    /**
     * 监听
     */
    private void initListener() {
        fastLoginVerificationCode.setOnClickListener(this);
        fastLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fast_login_verification_code: // 获取验证码
                fastUserPhone = fastLoginUserNameEt.getText().toString().trim();
                if ("".equals(fastUserPhone)) {
                    Toast.makeText(this.getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    fastLoginVerificationCode.setClickable(true);
                    fastLoginVerificationCode.setBackgroundResource(R.drawable.background_shape);
                    return;
                } else if (isRegistMobileNO() == false) {
                    Toast.makeText(this.getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    fastLoginVerificationCode.setClickable(true);
                    fastLoginVerificationCode.setBackgroundResource(R.drawable.background_shape);
                    return;
                } else {
                    time.start();
                    String fastLoginCodeUrl = GlobalConsts.REGISTER_CODE_URL + fastUserPhone;
                    Request fastLoginRequest = new Request.Builder().url(fastLoginCodeUrl).build();
                    okHttpClient.newCall(fastLoginRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String fastLoginString = response.body().string();
                            code = fastLoginString.substring(1, fastLoginString.length() - 1);
                        }
                    });
                }
                break;

            case R.id.fast_login_btn: // 登录
                fastUserPhone = fastLoginUserNameEt.getText().toString().trim();
                FastLoginCode = fastLoginPasswordEt.getText().toString().trim();
                if ("".equals(fastUserPhone)) {
                    Toast.makeText(FastLoginFragment.this.getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isRegistMobileNO() == false) {
                    Toast.makeText(FastLoginFragment.this.getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (code.equals("")) {
                    Toast.makeText(FastLoginFragment.this.getActivity(), "请点击获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(FastLoginCode)) {
                    Toast.makeText(FastLoginFragment.this.getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!code.equals(FastLoginCode)) {
                    Toast.makeText(FastLoginFragment.this.getActivity(), "请输入正确验证码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    dialogByProgress.show();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("mobilePhone", fastUserPhone);
                    builder.add("password", "");
                    builder.add("loginType", "1");
                    builder.add("valicateCode", FastLoginCode);
                    Log.d("tes", "手机号---" + fastUserPhone.toString());
                    Log.d("tes", "验证码---" + FastLoginCode.toString());


                    FormBody body = builder.build();
                    Request request = new Request.Builder().url(GlobalConsts.LOGIN_URL).post(body).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String bodyString = response.body().string();
                            Log.d("test", "快速登录---" + bodyString.toString());
                            Gson fastLoginGson = new Gson();
                            SmsCodeRequest fastLoginRequest = fastLoginGson.fromJson(bodyString, SmsCodeRequest.class);
                            Message fastLoginMsg = Message.obtain();
                            fastLoginMsg.what = GlobalConsts.FAST_LOGIN_HANDLER;
                            fastLoginMsg.obj = fastLoginRequest;
                            handler.sendMessage(fastLoginMsg);
                        }
                    });
                }
                break;
        }
    }

    /**
     * 正则验证手机号
     */
    public boolean isRegistMobileNO() {
        Pattern patternRegistMobile = Pattern.compile(GlobalConsts.REGEX_MOBILE, Pattern.CASE_INSENSITIVE);
        Matcher matcherRegistMobile = patternRegistMobile.matcher(fastUserPhone);
        return matcherRegistMobile.matches();
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
            fastLoginVerificationCode.setText("重新验证");
            fastLoginVerificationCode.setClickable(true);
            if (isAdded()) {
                fastLoginVerificationCode.setTextColor(getResources().getColor(R.color.white));
            }
            fastLoginVerificationCode.setBackgroundResource(R.drawable.background_shape);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            fastLoginVerificationCode.setClickable(false);
            fastLoginVerificationCode.setBackgroundResource(R.drawable.background_un_shape);
            if (isAdded()) {
                fastLoginVerificationCode.setTextColor(getResources().getColor(R.color.black));
            }
            fastLoginVerificationCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
