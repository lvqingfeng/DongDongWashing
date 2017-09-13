package dongdongwashing.com.ui.Wallet;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;

/**
 * Created by 沈 on 2017/3/31.
 */

public class WalletBalanceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView balanceBack;
    private TextView balanceQuestionTv; // 常见问题
    private TextView balanceTv; // 余额

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_balance_layout);

        initView();
        initListener();
    }

    /**
     * 初始化
     */
    private void initView() {
        balanceBack = (ImageView) findViewById(R.id.balance_back);
        balanceQuestionTv = (TextView) findViewById(R.id.balance_question_tv);
        balanceTv = (TextView) findViewById(R.id.balance_tv);
    }

    /**
     * 监听
     */
    private void initListener() {
        balanceBack.setOnClickListener(this);
        balanceQuestionTv.setOnClickListener(this);
    }

    /**
     * 监听方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balance_back:
                WalletBalanceActivity.this.finish();
                break;

            case R.id.balance_tv: // 常见问题

                break;
        }
    }
}
