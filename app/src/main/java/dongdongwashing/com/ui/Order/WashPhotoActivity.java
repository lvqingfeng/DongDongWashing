package dongdongwashing.com.ui.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.meiyou.jet.annotation.JFindView;
import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.process.Jet;

import java.util.ArrayList;
import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.util.CoverFlowViewPager;

public class WashPhotoActivity extends BaseActivity implements View.OnClickListener {

    @JFindView(R.id.wash_photo_back)
    @JFindViewOnClick(R.id.wash_photo_back)
    private ImageView washPhotoBack;

    @JFindView(R.id.wash_before_cover)
    private CoverFlowViewPager evaluationBeforeCover; // 洗前照片的轮播控件
    private List<String> washBeforePhotoList = new ArrayList<>(); // 洗前的照片数据
    private List<View> washBeforeViews = new ArrayList<>();

    @JFindView(R.id.wash_after_cover)
    private CoverFlowViewPager evaluationAfterCover; // 洗后照片的轮播控件
    private List<String> washAfterPhotoList = new ArrayList<>(); // 洗后的照片数据
    private List<View> washAfterViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash);
        Jet.bind(this);

        Intent intent = getIntent();
        washBeforePhotoList = intent.getStringArrayListExtra("WASH_BEFORE_PHOTO");
        washAfterPhotoList = intent.getStringArrayListExtra("WASH_AFTER_PHOTO");

        for (String washBeforeView : washBeforePhotoList) {
            ImageView view = new ImageView(WashPhotoActivity.this);
            Glide.with(WashPhotoActivity.this).load(washBeforeView).into(view);
            washBeforeViews.add(view);
        }
        evaluationBeforeCover.setViewList(washBeforeViews);

        for (String washAfterView : washAfterPhotoList) {
            ImageView view = new ImageView(WashPhotoActivity.this);
            Glide.with(WashPhotoActivity.this).load(washAfterView).into(view);
            washAfterViews.add(view);
        }
        evaluationAfterCover.setViewList(washAfterViews);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wash_photo_back:
                WashPhotoActivity.this.finish();
                break;
        }
    }
}
