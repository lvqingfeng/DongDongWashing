package dongdongwashing.com.ui.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.util.ScreenUtils;

/**
 * Created by 沈 on 2017/4/21.
 */

public class ServiceAgreementActivity extends BaseActivity {

    private ImageView serviceAgreementBack;
    private ImageView serviceAgreementIv1;
    private ImageView serviceAgreementIv2;
    private ImageView serviceAgreementIv3;
    private ImageView serviceAgreementIv4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_agreement_layout);

        serviceAgreementBack = (ImageView) findViewById(R.id.service_agreement_back);
        serviceAgreementIv1 = (ImageView) findViewById(R.id.service_agreement_iv_1);
        serviceAgreementIv2 = (ImageView) findViewById(R.id.service_agreement_iv_2);
        serviceAgreementIv3 = (ImageView) findViewById(R.id.service_agreement_iv_3);
        serviceAgreementIv4 = (ImageView) findViewById(R.id.service_agreement_iv_4);

        int wihth = ScreenUtils.getScreenWidth(ServiceAgreementActivity.this);
        int heigth = ScreenUtils.getScreenHeight(ServiceAgreementActivity.this);

        Glide.with(ServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_01)
                .override(wihth, heigth)
                .into(serviceAgreementIv1);

        Glide.with(ServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_02)
                .override(wihth, heigth)
                .into(serviceAgreementIv2);

        Glide.with(ServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_03)
                .override(wihth, heigth)
                .into(serviceAgreementIv3);

        Glide.with(ServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_04)
                .override(wihth, heigth)
                .into(serviceAgreementIv4);

        serviceAgreementBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceAgreementActivity.this.finish();
            }
        });
    }
}
