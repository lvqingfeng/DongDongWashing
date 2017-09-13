package dongdongwashing.com.ui.Wallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;

/**
 * Created by 沈 on 2017/3/31.
 */

public class WalletActivity extends BaseActivity implements View.OnClickListener {

    private ImageView walletBack; // 返回
    private RelativeLayout walletPayStyleRl; // 支付方式
    private TextView walletPayStyle; // 支付方式的显示文字
    private RelativeLayout walletBalanceRl; // 余额
    private TextView walletBalance; // 余额的显示文字

    private SharedPreferences payStyleSpf;
    private String payStyleString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_layout);

        getPayStyle();
        initView();
        initListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getPayStyle();
        if (!payStyleString.equals("")) {
            if (payStyleString.equals("balance")) {
                walletPayStyle.setText("使用余额支付");
            } else if (payStyleString.equals("wechat")) {
                walletPayStyle.setText("使用微信支付");
            } else if (payStyleString.equals("alipay")) {
                walletPayStyle.setText("使用支付宝支付");
            }
        }
    }

    /**
     * 获取支付方式并显示
     */
    private void getPayStyle() {
        payStyleSpf = WalletActivity.this.getSharedPreferences("pay_style", 0);
        payStyleString = payStyleSpf.getString("pay", "");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        walletBack = (ImageView) findViewById(R.id.wallet_back);
        walletPayStyleRl = (RelativeLayout) findViewById(R.id.wallet_pay_style_rl);
        walletPayStyle = (TextView) findViewById(R.id.wallet_pay_style);
        walletBalanceRl = (RelativeLayout) findViewById(R.id.wallet_balance_rl);
        walletBalance = (TextView) findViewById(R.id.wallet_balance);

        if (payStyleString.equals("balance")) {
            walletPayStyle.setText("使用余额支付");
        } else if (payStyleString.equals("wechat")) {
            walletPayStyle.setText("使用微信支付");
        } else if (payStyleString.equals("alipay")) {
            walletPayStyle.setText("使用支付宝支付");
        }
    }

    /**
     * 监听
     */
    private void initListener() {
        walletBack.setOnClickListener(this);
        walletPayStyleRl.setOnClickListener(this);
        walletBalanceRl.setOnClickListener(this);
    }

    /**
     * 监听方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wallet_back:
                WalletActivity.this.finish();
                break;

            case R.id.wallet_pay_style_rl: // 支付方式
                startActivity(new Intent(WalletActivity.this, PayStyleActivity.class));
                break;

            case R.id.wallet_balance_rl: // 余额
                startActivity(new Intent(WalletActivity.this, WalletBalanceActivity.class));
                break;
        }
    }
}
