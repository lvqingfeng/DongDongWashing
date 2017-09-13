package dongdongwashing.com.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.ui.MainActivity;
import dongdongwashing.com.util.ScreenUtils;

/**
 * Created by 沈 on 2017/4/21.
 */

public class FirstServiceAgreementActivity extends BaseActivity {

    private ImageView firstServiceAgreementIv1;
    private ImageView firstServiceAgreementIv2;
    private ImageView firstServiceAgreementIv3;
    private ImageView firstServiceAgreementIv4;
    private Button firstServiceAgreementBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_service_agreement_layout);

        firstServiceAgreementIv1 = (ImageView) findViewById(R.id.first_service_agreement_iv_1);
        firstServiceAgreementIv2 = (ImageView) findViewById(R.id.first_service_agreement_iv_2);
        firstServiceAgreementIv3 = (ImageView) findViewById(R.id.first_service_agreement_iv_3);
        firstServiceAgreementIv4 = (ImageView) findViewById(R.id.first_service_agreement_iv_4);
        firstServiceAgreementBtn = (Button) findViewById(R.id.first_service_agreement_btn);

        int width = ScreenUtils.getScreenWidth(FirstServiceAgreementActivity.this);
        int height = ScreenUtils.getScreenHeight(FirstServiceAgreementActivity.this);

        Glide.with(FirstServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_01)
                .override(width, height)
                .into(firstServiceAgreementIv1);

        Glide.with(FirstServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_02)
                .override(width, height)
                .into(firstServiceAgreementIv2);

        Glide.with(FirstServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_03)
                .override(width, height)
                .into(firstServiceAgreementIv3);

        Glide.with(FirstServiceAgreementActivity.this)
                .load(R.drawable.service_agreement_04)
                .override(width, height)
                .into(firstServiceAgreementIv4);

        firstServiceAgreementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences spf = FirstServiceAgreementActivity.this.getSharedPreferences("share", MODE_PRIVATE);
                SharedPreferences.Editor editor = spf.edit();
                editor.putString("isFirstRun", "true");
                editor.commit();

                startActivity(new Intent(FirstServiceAgreementActivity.this, MainActivity.class));
                FirstServiceAgreementActivity.this.finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
