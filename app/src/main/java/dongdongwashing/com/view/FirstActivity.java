package dongdongwashing.com.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import dongdongwashing.com.com.BaseActivity;

/**
 * Created by 沈 on 2017/4/26.
 */

public class FirstActivity extends BaseActivity {

    private SharedPreferences sharedPreferences;
    private String isFirstString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        isFirstString = sharedPreferences.getString("isFirstRun", "");

        if ("".equals(isFirstString)) { // 第一次运行
            Intent intent = new Intent(FirstActivity.this, FirstServiceAgreementActivity.class);
            startActivity(intent);
            FirstActivity.this.finish();
        } else {
            Intent intent = new Intent(FirstActivity.this, FlashActivity.class);
            startActivity(intent);
            FirstActivity.this.finish();
        }
    }
}
