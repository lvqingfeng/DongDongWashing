package dongdongwashing.com.ui.CustomerService;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.ui.Settings.ServiceModelActivity;
import dongdongwashing.com.util.ScreenUtils;

/**
 * Created by 沈 on 2017/4/24.
 */

public class CommonProblemActivity extends BaseActivity {

    private ImageView commonProblemBack, commonProblemIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_problem_layout);

        commonProblemBack = (ImageView) findViewById(R.id.common_problem_back);
        commonProblemIv = (ImageView) findViewById(R.id.common_problem_iv);

        int wihth = ScreenUtils.getScreenWidth(CommonProblemActivity.this);
        int heigth = ScreenUtils.getScreenHeight(CommonProblemActivity.this);

        Glide.with(CommonProblemActivity.this)
                .load(R.drawable.common_problem)
                .override(wihth, heigth)
                .into(commonProblemIv);

        commonProblemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonProblemActivity.this.finish();
            }
        });
    }
}
