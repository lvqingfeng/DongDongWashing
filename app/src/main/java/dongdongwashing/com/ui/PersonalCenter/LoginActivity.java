package dongdongwashing.com.ui.PersonalCenter;

import android.content.Intent;
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

import dongdongwashing.com.R;
import dongdongwashing.com.adapter.LoginAdapter;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.fragment.CommonLoginFragment;
import dongdongwashing.com.fragment.FastLoginFragment;

/**
 * Created by 沈 on 2017/3/29.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private List<Fragment> loginFragment; // fragment集合
    private LoginAdapter loginAdapter; // fragment管理器
    private ImageView loginBack; // 返回
    private TextView registerTv; // 注册
    private RadioGroup loginRg;
    private RadioButton commonRb, fastRb;
    private TextView loginView1, loginView2;
    private ViewPager loginViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login_layout);

        initView();
        initFragment();
        initListener();
        onListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        loginBack = (ImageView) findViewById(R.id.login_back);
        registerTv = (TextView) findViewById(R.id.register_tv);
        loginRg = (RadioGroup) findViewById(R.id.login_rg);
        commonRb = (RadioButton) findViewById(R.id.common_rb);
        fastRb = (RadioButton) findViewById(R.id.fast_rb);
        loginView1 = (TextView) findViewById(R.id.login_view1);
        loginView2 = (TextView) findViewById(R.id.login_view2);
        loginViewPager = (ViewPager) findViewById(R.id.login_view_pager);
    }


    /**
     * 初始化fragment
     */
    private void initFragment() {
        loginFragment = new ArrayList<>();
        loginFragment.add(new CommonLoginFragment()); // 普通登录
        loginFragment.add(new FastLoginFragment()); // 快速登录
        loginAdapter = new LoginAdapter(this.getSupportFragmentManager(), loginFragment);
        loginViewPager.setAdapter(loginAdapter);
    }

    /**
     * 监听
     */
    private void initListener() {
        loginBack.setOnClickListener(this);
        registerTv.setOnClickListener(this);
    }

    /**
     * 监听方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back: // 返回
                LoginActivity.this.finish();
                break;

            case R.id.register_tv: // 注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    /**
     * 控件页面联动
     */
    private void onListener() {
        loginRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.common_rb:
                        loginViewPager.setCurrentItem(0);
                        commonRb.setTextColor(getResources().getColor(R.color.orange));
                        fastRb.setTextColor(getResources().getColor(R.color.black));
                        loginView1.setBackgroundResource(R.color.orange);
                        loginView2.setBackgroundResource(R.color.white);
                        break;

                    case R.id.fast_rb:
                        loginViewPager.setCurrentItem(1);
                        commonRb.setTextColor(getResources().getColor(R.color.black));
                        fastRb.setTextColor(getResources().getColor(R.color.orange));
                        loginView1.setBackgroundResource(R.color.white);
                        loginView2.setBackgroundResource(R.color.orange);
                        break;
                }
            }
        });

        loginViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        commonRb.setChecked(true);
                        loginView1.setBackgroundResource(R.color.orange);
                        loginView2.setBackgroundResource(R.color.white);
                        break;

                    case 1:
                        fastRb.setChecked(true);
                        loginView1.setBackgroundResource(R.color.white);
                        loginView2.setBackgroundResource(R.color.orange);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
