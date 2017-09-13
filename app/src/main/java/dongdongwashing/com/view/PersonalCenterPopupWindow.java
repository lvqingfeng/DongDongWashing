package dongdongwashing.com.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import dongdongwashing.com.R;
import dongdongwashing.com.ui.CustomerService.CustomerServiceActivity;
import dongdongwashing.com.ui.Order.OrderActivity;
import dongdongwashing.com.ui.PersonalCenter.EditProfileActivity;
import dongdongwashing.com.ui.PersonalCenter.LoginActivity;
import dongdongwashing.com.ui.Settings.SettingsActivity;
import dongdongwashing.com.ui.Wallet.WalletActivity;

/**
 * Created by 沈 on 2017/4/12.
 */

public class PersonalCenterPopupWindow extends PopupWindow implements View.OnClickListener {

    // personalCenterPW = new PersonalCenterPopupWindow(MainActivity.this, width, ViewGroup.LayoutParams.MATCH_PARENT, nickNameString);

    private Context context;
    private View personalCenterView;
    private CircleImageView myHead; // 设置头像
    private TextView loginTv; // 登录
    private RelativeLayout orderRl, walletRl, serviceRl, settingsRl;
    private RelativeLayout integralRl; // 积分商城
    private Intent intent = new Intent();

    public PersonalCenterPopupWindow(final Context context, int width, int height, String nickNameString) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        personalCenterView = inflater.inflate(R.layout.personal_center_pw, null);

        this.setContentView(personalCenterView);
        this.setWidth(width);
        this.setHeight(height);

        initView();
        initListener();
        setloginTvText(loginTv, nickNameString);
    }

    /**
     * 初始化
     */
    private void initView() {
        myHead = (CircleImageView) personalCenterView.findViewById(R.id.my_head);
        loginTv = (TextView) personalCenterView.findViewById(R.id.login_tv);
        orderRl = (RelativeLayout) personalCenterView.findViewById(R.id.order_rl);
        walletRl = (RelativeLayout) personalCenterView.findViewById(R.id.wallet_rl);
        serviceRl = (RelativeLayout) personalCenterView.findViewById(R.id.service_rl);
        settingsRl = (RelativeLayout) personalCenterView.findViewById(R.id.settings_rl);
        integralRl = (RelativeLayout) personalCenterView.findViewById(R.id.integral_rl);
    }

    /**
     * 监听
     */
    private void initListener() {
        myHead.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        orderRl.setOnClickListener(this);
        walletRl.setOnClickListener(this);
        serviceRl.setOnClickListener(this);
        settingsRl.setOnClickListener(this);
        integralRl.setOnClickListener(this);
    }

    /**
     * 实现监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_head: // 个人中心编辑资料
                context.startActivity(new Intent(context, EditProfileActivity.class));
                break;

            case R.id.login_tv: // 登录注册
                context.startActivity(new Intent(context, LoginActivity.class));
                break;

            case R.id.order_rl: // 订单
                context.startActivity(new Intent(context, OrderActivity.class));
                break;

            case R.id.wallet_rl: // 钱包
                context.startActivity(new Intent(context, WalletActivity.class));
                break;

            case R.id.service_rl: // 客服
                context.startActivity(new Intent(context, CustomerServiceActivity.class));
                break;

            case R.id.settings_rl: // 设置
                context.startActivity(new Intent(context, SettingsActivity.class));
                break;

            case R.id.integral_rl: // 积分商城
                intent.setAction("");
//                intent.putStringArrayListExtra("", );
//                context.sendBroadcast();
                break;
        }
    }


    /**
     * 设置登录的文字
     *
     * @param loginTv
     * @param nickNameString
     */
    private void setloginTvText(TextView loginTv, String nickNameString) {
        if (!nickNameString.equals("")) {
            loginTv.setText(nickNameString);
        } else {
            loginTv.setText("登录/注册");
        }
    }
}
