package dongdongwashing.com.ui.Integral;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dongdongwashing.com.R;
import dongdongwashing.com.adapter.IntegralAdapter;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.fragment.ExchangeFragment;
import dongdongwashing.com.fragment.IntegralsFragment;

/**
 * Created by 沈 on 2017/4/13.
 */

public class IntegralActivity extends BaseActivity implements View.OnClickListener {

    private List<Fragment> integralFragment;
    private IntegralAdapter integralAdapter;

    private ImageView integralBack; // 返回
    private CircleImageView integralHead; // 头像
    private TextView integralNickName; // 昵称
    private TextView integralLevelTv; // 级别
    private TextView integralId; // ID号
    private TextView integralNameTv; // 姓名
    private TextView integralPhoneTv; // 电话
    private RadioGroup integralRg; // 按钮父容器
    private RadioButton integralRb; // 积分
    private RadioButton exchangeRb; // 兑换
    private TextView integralNumber; // 积分显示数字
    private ImageView integralIv1; // 倒三角1
    private ImageView integralIv2; // 倒三角2
    private ViewPager integralViewPager; // fragment布局

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_integral_layout);

        initView();
        initFragment();
        initListener();
        onListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        integralBack = (ImageView) findViewById(R.id.integral_back); // 返回
        integralHead = (CircleImageView) findViewById(R.id.integral_head); // 头像
        integralNickName = (TextView) findViewById(R.id.integral_nick_name); // 昵称
        integralLevelTv = (TextView) findViewById(R.id.integral_level_tv); // 级别
        integralId = (TextView) findViewById(R.id.integral_id); // ID号
        integralNameTv = (TextView) findViewById(R.id.integral_name_tv); // 姓名
        integralPhoneTv = (TextView) findViewById(R.id.integral_phone_tv); // 电话
        integralRg = (RadioGroup) findViewById(R.id.integral_rg); // 按钮父容器
        integralRb = (RadioButton) findViewById(R.id.integral_rb); // 积分
        exchangeRb = (RadioButton) findViewById(R.id.exchange_rb); // 兑换
        integralNumber = (TextView) findViewById(R.id.integral_number); // 积分显示数字
        integralIv1 = (ImageView) findViewById(R.id.integral_iv1); // 倒三角1
        integralIv2 = (ImageView) findViewById(R.id.integral_iv2); // 倒三角2
        integralViewPager = (ViewPager) findViewById(R.id.integral_view_pager); // fragment布局
    }

    /**
     * 实例化碎片布局
     */
    private void initFragment() {
        integralFragment = new ArrayList<>();
        integralFragment.add(new IntegralsFragment());
        integralFragment.add(new ExchangeFragment());
        integralAdapter = new IntegralAdapter(this.getSupportFragmentManager(), integralFragment);
        integralViewPager.setAdapter(integralAdapter);
    }

    /**
     * 监听
     */
    private void initListener() {
        integralBack.setOnClickListener(this);
    }

    /**
     * 联动
     */
    private void onListener() {
        integralRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.integral_rb:
                        integralViewPager.setCurrentItem(0);
                        integralIv1.setVisibility(View.VISIBLE);
                        integralIv2.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.exchange_rb:
                        integralViewPager.setCurrentItem(1);
                        integralIv1.setVisibility(View.INVISIBLE);
                        integralIv2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        integralViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        integralRb.setChecked(true);
                        integralIv1.setVisibility(View.VISIBLE);
                        integralIv2.setVisibility(View.INVISIBLE);
                        break;

                    case 1:
                        exchangeRb.setChecked(true);
                        integralIv1.setVisibility(View.INVISIBLE);
                        integralIv2.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 实现监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.integral_back:
                IntegralActivity.this.finish();
                break;
        }
    }
}
