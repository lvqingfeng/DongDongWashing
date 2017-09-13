package dongdongwashing.com.ui.CustomerService;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.ui.PersonalCenter.LoginActivity;
import dongdongwashing.com.util.DialogByTwoButton;
import dongdongwashing.com.util.GlobalConsts;

/**
 * Created by 沈 on 2017/3/31.
 */

public class CustomerServiceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView customerServiceBack;
    private RelativeLayout customerServiceOrderRl;
    private RelativeLayout customerServiceFeedbackRl;
    private RelativeLayout customerServiceRl; // 客服电话
    private RelativeLayout complaintsServiceRl; // 投诉电话
    private Intent callPhoneIntent;
    private String callPhoneString, callPhoneType;
    private String id;
    private DialogByTwoButton dialog2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_layout);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(CustomerServiceActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CustomerServiceActivity.this, new String[]{Manifest.permission.CALL_PHONE}, GlobalConsts.MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }

        SharedPreferences spf = CustomerServiceActivity.this.getSharedPreferences("user_info", 0);
        id = spf.getString("uid", "");

        initView();
        initListener();
    }

    /**
     * 初始化
     */
    private void initView() {
        customerServiceBack = (ImageView) findViewById(R.id.customer_service_back);
        customerServiceOrderRl = (RelativeLayout) findViewById(R.id.customer_service_order_rl);
        customerServiceFeedbackRl = (RelativeLayout) findViewById(R.id.customer_service_feedback_rl);
        customerServiceRl = (RelativeLayout) findViewById(R.id.customer_service_rl);
        complaintsServiceRl = (RelativeLayout) findViewById(R.id.complaints_service_rl);
    }

    /**
     * 监听
     */
    private void initListener() {
        customerServiceBack.setOnClickListener(this);
        customerServiceOrderRl.setOnClickListener(this);
        customerServiceFeedbackRl.setOnClickListener(this);
        customerServiceRl.setOnClickListener(this);
        complaintsServiceRl.setOnClickListener(this);
    }

    /**
     * 监听方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customer_service_back:
                CustomerServiceActivity.this.finish();
                break;

            case R.id.customer_service_order_rl: // 订单问题
                startActivity(new Intent(CustomerServiceActivity.this, CommonProblemActivity.class));
                break;

            case R.id.customer_service_feedback_rl: // 意见反馈
                if (id.equals("")) {
                    dialog2 = new DialogByTwoButton(CustomerServiceActivity.this,
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
                            startActivity(new Intent(CustomerServiceActivity.this, LoginActivity.class));
                        }
                    });
                } else {
                    startActivity(new Intent(CustomerServiceActivity.this, FeedBackActivity.class));
                }
                break;

            case R.id.customer_service_rl: // 客服电话
                callPhoneString = GlobalConsts.CALL_CUSTOMER_PHONE;
                callPhoneType = "客服电话";
                callPhone(callPhoneString, callPhoneType);
                break;

            case R.id.complaints_service_rl: // 投诉电话
                callPhoneString = GlobalConsts.CALL_COMPLAINTS_PHONE;
                callPhoneType = "投诉电话";
                callPhone(callPhoneString, callPhoneType);
                break;
        }
    }

    private void callPhone(final String callPhoneString, String callPhoneType) {
        dialog2 = new DialogByTwoButton(CustomerServiceActivity.this, "提示", "是否呼叫" + callPhoneType + " :  " + callPhoneString, "取消", "确定");
        dialog2.show();
        dialog2.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
            @Override
            public void doNegative() {
                dialog2.dismiss();
            }

            @Override
            public void doPositive() {
                callPhoneIntent = new Intent();
                callPhoneIntent.setAction(Intent.ACTION_CALL);
                callPhoneIntent.setData(Uri.parse("tel:" + callPhoneString));
                startActivity(callPhoneIntent);
                dialog2.dismiss();
            }
        });
    }
}
