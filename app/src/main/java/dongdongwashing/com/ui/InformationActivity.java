package dongdongwashing.com.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;

/**
 * Created by 沈 on 2017/4/5.
 */

public class InformationActivity extends BaseActivity {

    private ImageView informationBack;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_layout);

        informationBack = (ImageView) findViewById(R.id.information_back);
        informationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationActivity.this.finish();
            }
        });
    }
}
