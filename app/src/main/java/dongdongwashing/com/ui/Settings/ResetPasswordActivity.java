package dongdongwashing.com.ui.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.updateAccount.UpdateAccountRequest;
import dongdongwashing.com.ui.PersonalCenter.LoginActivity;
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
 * Created by 沈 on 2017/4/4.
 */

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView resetPasswordBack; // 返回
    private EditText oldPasswordEt; // 旧密码输入框
    private ImageView oldPasswordShow; // 显示旧密码
    private EditText newPasswordEt; // 新密码输入框
    private ImageView newPasswordShow; // 显示新密码
    private EditText confirmPasswordEt; // 确认密码输入框
    private ImageView confirmPasswordShow; // 显示确认密码
    private Button resetPasswordBtn; // 确认修改

    private String oldPassword, newPassword, confirmPassword;
    private boolean show = false;
    private SharedPreferences spf;
    private String id;

    private DialogByOneButton dialog1;
    private DialogByTwoButton dialog2;
    private DialogByProgress dialogByProgress;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.CHANGE_PASSWORD_HANDLER:
                    UpdateAccountRequest changPasswordRequest = (UpdateAccountRequest) msg.obj;
                    String requestString = changPasswordRequest.getMsg();
                    if (requestString.equals("设置密码")) {
                        if (dialogByProgress != null && dialogByProgress.isShowing()) {
                            dialogByProgress.dismiss();
                            dialog1 = new DialogByOneButton(ResetPasswordActivity.this,
                                    "提示",
                                    "密码修改成功",
                                    "确定"
                            );
                            dialog1.show();
                            dialog1.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                                @Override
                                public void doPositive() {
                                    dialog1.dismiss();
                                    ResetPasswordActivity.this.finish();
                                }
                            });
                        }
                    } else if (requestString.equals("输入的旧密码不正确!")) {
                        if (dialogByProgress != null && dialogByProgress.isShowing()) {
                            dialogByProgress.dismiss();
                            dialog1 = new DialogByOneButton(ResetPasswordActivity.this,
                                    "提示",
                                    "输入的旧密码不正确，请确认后出现修改",
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
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pasword_layout);

        dialogByProgress = new DialogByProgress(ResetPasswordActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        spf = ResetPasswordActivity.this.getSharedPreferences("user_info", 0);
        id = spf.getString("uid", "");
        Log.d("test", "当前登录的用户id---" + id.toString());

        initView();
        initListener();
    }

    /**
     * 初始化
     */
    private void initView() {
        resetPasswordBack = (ImageView) findViewById(R.id.reset_password_back); // 返回
        oldPasswordEt = (EditText) findViewById(R.id.old_password_et); // 旧密码输入框
        oldPasswordShow = (ImageView) findViewById(R.id.old_password_show); // 显示旧密码
        newPasswordEt = (EditText) findViewById(R.id.new_password_et); // 新密码输入框
        newPasswordShow = (ImageView) findViewById(R.id.new_password_show); // 显示新密码
        confirmPasswordEt = (EditText) findViewById(R.id.confirm_password_et); // 确认密码输入框
        confirmPasswordShow = (ImageView) findViewById(R.id.confirm_password_show); // 显示确认密码
        resetPasswordBtn = (Button) findViewById(R.id.reset_password_btn); // 确认修改
    }

    /**
     * 监听
     */
    private void initListener() {
        resetPasswordBack.setOnClickListener(this);
        oldPasswordShow.setOnClickListener(this);
        newPasswordShow.setOnClickListener(this);
        confirmPasswordShow.setOnClickListener(this);
        resetPasswordBtn.setOnClickListener(this);
    }

    /**
     * 实现监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_password_back:
                ResetPasswordActivity.this.finish();
                break;

            case R.id.old_password_show:
                showOrHidePassWord(oldPasswordShow, oldPasswordEt);
                break;

            case R.id.new_password_show:
                showOrHidePassWord(newPasswordShow, newPasswordEt);
                break;

            case R.id.confirm_password_show:
                showOrHidePassWord(confirmPasswordShow, confirmPasswordEt);
                break;

            case R.id.reset_password_btn:
                oldPassword = oldPasswordEt.getText().toString().trim();
                newPassword = newPasswordEt.getText().toString().trim();
                confirmPassword = confirmPasswordEt.getText().toString().trim();
                if (id.equals("")) {
                    dialog2 = new DialogByTwoButton(ResetPasswordActivity.this,
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
                            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            ResetPasswordActivity.this.finish();
                        }
                    });
                } else {
                    if ("".equals(oldPassword)) {
                        Toast.makeText(ResetPasswordActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else if ("".equals(newPassword)) {
                        Toast.makeText(ResetPasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (JudgeOldPassword() == true) {
                        Toast.makeText(ResetPasswordActivity.this, "旧密码与新密码不能一致", Toast.LENGTH_SHORT).show();
                        return;
                    } else if ("".equals(confirmPassword)) {
                        Toast.makeText(ResetPasswordActivity.this, "请输入确认密码", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (JudgePassword() == false) {
                        Toast.makeText(ResetPasswordActivity.this, "新密码与确认密码请保持一致", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        dialogByProgress.show();
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("id", id);
                        builder.add("oldPwd", oldPassword);
                        builder.add("newPwd", newPassword);
                        builder.add("crmPwd", confirmPassword);
                        final FormBody body = builder.build();
                        Request request = new Request.Builder().url(GlobalConsts.CHANGE_PASSWORD_URL).post(body).build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String bodyString = response.body().string();
                                Gson changPasswordGson = new Gson();
                                UpdateAccountRequest changPasswordRequest = changPasswordGson.fromJson(bodyString, UpdateAccountRequest.class);
                                Message changPasswordMsg = Message.obtain();
                                changPasswordMsg.what = GlobalConsts.CHANGE_PASSWORD_HANDLER;
                                changPasswordMsg.obj = changPasswordRequest;
                                handler.sendMessage(changPasswordMsg);
                            }
                        });
                    }
                }
                break;
        }
    }

    /**
     * 隐藏或显示密码
     *
     * @param iv
     * @param et
     */
    private void showOrHidePassWord(ImageView iv, EditText et) {
        if (show) {
            iv.setImageResource(R.mipmap.btn_passno);
            et.setTransformationMethod(PasswordTransformationMethod.getInstance());
            show = false;
        } else {
            iv.setImageResource(R.mipmap.btn_pass);
            et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            show = true;
        }
    }

    /**
     * 判断旧密码与新密码是否一致
     */
    public boolean JudgeOldPassword() {
        oldPassword = oldPasswordEt.getText().toString().trim();
        newPassword = newPasswordEt.getText().toString().trim();
        return newPassword.equals(oldPassword);
    }

    /**
     * 判断新密码与确认密码是否相等
     */
    public boolean JudgePassword() {
        newPassword = newPasswordEt.getText().toString().trim();
        confirmPassword = confirmPasswordEt.getText().toString().trim();
        return newPassword.equals(confirmPassword);
    }
}
