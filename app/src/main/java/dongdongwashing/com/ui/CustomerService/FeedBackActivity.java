package dongdongwashing.com.ui.CustomerService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.feedBack.FeedBackRequest;
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
 * Created by 沈 on 2017/4/1.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    private ImageView feedbackBack; // 返回
    private EditText feedBackEt; // 文字输入框
    private EditText feedBackPhoneOrEmail; // 电话输入框
    private Button feedBackBtn; // 提交
    private String id, feedBackString;
    private String feedBackPhoneString = "";

    private DialogByProgress dialogByProgress;
    private DialogByTwoButton dialog2;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.FEED_BACK_HANDLER:
                    FeedBackRequest feedBackRequest = (FeedBackRequest) msg.obj;
                    String feedBackString = feedBackRequest.getMsg();
                    if (feedBackString.equals("反馈成功")) {
                        if (dialogByProgress != null && dialogByProgress.isShowing()) {
                            dialogByProgress.dismiss();
                        }
                        dialog2 = new DialogByTwoButton(FeedBackActivity.this,
                                "提示",
                                "您的意见已收到，再次感谢您的支持，我们会努力做得更好",
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
                                FeedBackActivity.this.finish();
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
        setContentView(R.layout.activity_feedback_layout);

        dialogByProgress = new DialogByProgress(FeedBackActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        SharedPreferences spf = FeedBackActivity.this.getSharedPreferences("user_info", 0);
        id = spf.getString("uid", "");

        initView();
        initListener();
    }

    /**
     * 初始化
     */
    private void initView() {
        feedbackBack = (ImageView) findViewById(R.id.feedback_back);
        feedBackEt = (EditText) findViewById(R.id.feed_back_et);
        feedBackPhoneOrEmail = (EditText) findViewById(R.id.feed_back_phone_or_email);
        feedBackBtn = (Button) findViewById(R.id.feed_back_btn);
    }

    /**
     * 监听
     */
    private void initListener() {
        feedbackBack.setOnClickListener(this);
        feedBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_back:
                FeedBackActivity.this.finish();
                break;

            case R.id.feed_back_btn:
                feedBackString = feedBackEt.getText().toString().trim();
                feedBackPhoneString = feedBackPhoneOrEmail.getText().toString().trim();
                if ("".equals(feedBackString)) {
                    Toast.makeText(FeedBackActivity.this, "意见内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    dialogByProgress.show();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("telePhoneMail", feedBackPhoneString);
                    builder.add("des", feedBackString);
                    builder.add("userID", id);
                    FormBody body = builder.build();
                    final Request request = new Request.Builder().url(GlobalConsts.FEED_BACK_URL).post(body).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String bodyString = response.body().string();
                            Log.d("test", "意见反馈---" + bodyString.toString());
                            Gson feedBacKGson = new Gson();
                            FeedBackRequest feedBackRequest = feedBacKGson.fromJson(bodyString, FeedBackRequest.class);
                            Message feedBackMsg = Message.obtain();
                            feedBackMsg.what = GlobalConsts.FEED_BACK_HANDLER;
                            feedBackMsg.obj = feedBackRequest;
                            handler.sendMessage(feedBackMsg);
                        }
                    });
                }
                break;
        }
    }

    /**
     * 判断点击位置 实现输入框外的键盘隐藏效果
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
