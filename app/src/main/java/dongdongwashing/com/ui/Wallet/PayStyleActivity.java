package dongdongwashing.com.ui.Wallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;

/**
 * Created by 沈 on 2017/3/31.
 */

public class PayStyleActivity extends BaseActivity implements View.OnClickListener {

    private ImageView payStyleBack;
    private RelativeLayout walletPayStyle01Rl;
    private RelativeLayout walletPayStyle02Rl;
    private RelativeLayout walletPayStyle03Rl;
    private TextView walletPayStyle01Tv;
    private TextView walletPayStyle02Tv;
    private TextView walletPayStyle03Tv;

    private SharedPreferences payStyleSp;
    private String payStyleString;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_style_layout);

        getPayStyle();
        initView();
        initListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 获取支付方式
     */
    private void getPayStyle() {
        payStyleSp = PayStyleActivity.this.getSharedPreferences("pay_style", 0);
        payStyleString = payStyleSp.getString("pay", "");
    }

    /**
     * 初始化
     */
    private void initView() {
        payStyleBack = (ImageView) findViewById(R.id.pay_style_back);
        walletPayStyle01Rl = (RelativeLayout) findViewById(R.id.wallet_pay_style_01_rl);
        walletPayStyle02Rl = (RelativeLayout) findViewById(R.id.wallet_pay_style_02_rl);
        walletPayStyle03Rl = (RelativeLayout) findViewById(R.id.wallet_pay_style_03_rl);
        walletPayStyle01Tv = (TextView) findViewById(R.id.wallet_pay_style_01_tv);
        walletPayStyle02Tv = (TextView) findViewById(R.id.wallet_pay_style_02_tv);
        walletPayStyle03Tv = (TextView) findViewById(R.id.wallet_pay_style_03_tv);

        if (!payStyleString.equals("")) {
            if (payStyleString.equals("balance")) {
                walletPayStyle01Tv.setVisibility(View.VISIBLE);
                walletPayStyle02Tv.setVisibility(View.INVISIBLE);
                walletPayStyle03Tv.setVisibility(View.INVISIBLE);
            } else if (payStyleString.equals("wechat")) {
                walletPayStyle01Tv.setVisibility(View.INVISIBLE);
                walletPayStyle02Tv.setVisibility(View.VISIBLE);
                walletPayStyle03Tv.setVisibility(View.INVISIBLE);
            } else if (payStyleString.equals("alipay")) {
                walletPayStyle01Tv.setVisibility(View.INVISIBLE);
                walletPayStyle02Tv.setVisibility(View.INVISIBLE);
                walletPayStyle03Tv.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 监听
     */
    private void initListener() {
        payStyleBack.setOnClickListener(this);
        walletPayStyle01Rl.setOnClickListener(this);
        walletPayStyle02Rl.setOnClickListener(this);
        walletPayStyle03Rl.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_style_back:
                PayStyleActivity.this.finish();
                break;

            case R.id.wallet_pay_style_01_rl:
                payStyleSp = PayStyleActivity.this.getSharedPreferences("pay_style", 0);
                SharedPreferences.Editor editor = payStyleSp.edit();
                editor.putString("pay", "balance");
                editor.commit();

                walletPayStyle01Tv.setVisibility(View.VISIBLE);
                walletPayStyle02Tv.setVisibility(View.INVISIBLE);
                walletPayStyle03Tv.setVisibility(View.INVISIBLE);

                Toast.makeText(PayStyleActivity.this, "选择余额为支付方式", Toast.LENGTH_SHORT).show();
                break;

            case R.id.wallet_pay_style_02_rl:
                payStyleSp = PayStyleActivity.this.getSharedPreferences("pay_style", 0);
                SharedPreferences.Editor editor1 = payStyleSp.edit();
                editor1.putString("pay", "wechat");
                editor1.commit();

                walletPayStyle01Tv.setVisibility(View.INVISIBLE);
                walletPayStyle02Tv.setVisibility(View.VISIBLE);
                walletPayStyle03Tv.setVisibility(View.INVISIBLE);

                Toast.makeText(PayStyleActivity.this, "选择微信为支付方式", Toast.LENGTH_SHORT).show();
                break;

            case R.id.wallet_pay_style_03_rl:
                payStyleSp = PayStyleActivity.this.getSharedPreferences("pay_style", 0);
                SharedPreferences.Editor editor2 = payStyleSp.edit();
                editor2.putString("pay", "alipay");
                editor2.commit();

                walletPayStyle01Tv.setVisibility(View.INVISIBLE);
                walletPayStyle02Tv.setVisibility(View.INVISIBLE);
                walletPayStyle03Tv.setVisibility(View.VISIBLE);

                Toast.makeText(PayStyleActivity.this, "选择支付宝为支付方式", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
