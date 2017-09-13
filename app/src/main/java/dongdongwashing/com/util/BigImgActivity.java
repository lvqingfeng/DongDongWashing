package dongdongwashing.com.util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.display.ZoomInImageDisplayer;
import me.xiaopan.sketch.feature.zoom.ImageZoomer;

public class BigImgActivity extends BaseActivity {

    private SketchImageView sketchImageView;
    private String img; // 传过来的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);
        sketchImageView = (SketchImageView) findViewById(R.id.image_head);
        Intent intent = getIntent();
        img = intent.getStringExtra("activity");
        sketchImageView.getOptions().setImageDisplayer(new ZoomInImageDisplayer());
        if (img != null) {
            sketchImageView.displayImage(img);
        }
        sketchImageView.setSupportZoom(true);
        sketchImageView.getImageZoomer().setOnViewTapListener(new ImageZoomer.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                finish();
            }
        });
    }
}
