package dongdongwashing.com.ui.Order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dongdongwashing.com.R;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.GetAfterPhotoRequest;
import dongdongwashing.com.entity.GetBeforePhotoRequest;
import dongdongwashing.com.entity.orderEvaluation.OrderEvaluationRequest;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.GlobalConsts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/5/15.
 */

public class OrderEvaluationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView evaluationBack;
    private EditText evaluationEt;
    private Button evaluationBtn;
    private CheckBox speed1, speed2, speed3, speed4, speed5;
    private CheckBox service1, service2, service3, service4, service5;
    private CheckBox quality1, quality2, quality3, quality4, quality5;
    private String orderId, washCarSpeed = "", washCarService = "", washCarQuality = "";

    private ScrollView evaluationSv;
    private CircleImageView evaluationBeforeCiv;
    private List<String> washBeforePhotoList = new ArrayList<>(); // 洗前的照片数据
    private List<String> washAfterPhotoList = new ArrayList<>(); // 洗后的照片数据

    private DialogByProgress dialogByProgress;
    private DialogByOneButton dialog;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.CREATE_ORDER_EVALUATION_HANDLER:
                    OrderEvaluationRequest orderEvaluationRequest = (OrderEvaluationRequest) msg.obj;
                    HideDialogByProgress();
                    String orderEvaluationState = orderEvaluationRequest.getIsSucess();
                    if (TextUtils.equals("true", orderEvaluationState)) {
                        dialog = new DialogByOneButton(OrderEvaluationActivity.this, "提示", "谢谢您的评价，我们会继续努力，只为更好的服务", "确定");
                        dialog.show();
                        dialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                dialog.dismiss();
                                OrderEvaluationActivity.this.finish();
                            }
                        });
                    } else {

                    }
                    break;

                case GlobalConsts.GET_WASH_BEFORE_PHOTO_HANDLER: // 获取洗前照片
                    GetBeforePhotoRequest getBeforePhotoRequest = (GetBeforePhotoRequest) msg.obj;
                    HideDialogByProgress();
                    if (TextUtils.equals("true", getBeforePhotoRequest.getIsSucess())) {
                        GetBeforePhotoRequest.GetBeforePhotoResult getBeforePhotoResult = getBeforePhotoRequest.getData();
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontLeft());
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontLeftBehind());
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontLeftHead());
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontLeftPicture());
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontRight());
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontRightBehind());
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontRightHead());
                        washBeforePhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getBeforePhotoResult.getFrontRightTail());
                        if (washBeforePhotoList != null && !washBeforePhotoList.isEmpty()) {
                            Glide.with(OrderEvaluationActivity.this).load(washBeforePhotoList.get(0)).into(evaluationBeforeCiv);
                        }
                    }
                    break;

                case GlobalConsts.GET_WASH_AFTER_PHOTO_HANDLER: // 获取洗后照片
                    GetAfterPhotoRequest getAfterPhotoRequest = (GetAfterPhotoRequest) msg.obj;
                    HideDialogByProgress();
                    if (TextUtils.equals("true", getAfterPhotoRequest.getIsSucess())) {
                        GetAfterPhotoRequest.GetAfterPhotoResult getAfterPhotoResult = getAfterPhotoRequest.getData();
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindLeft());
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindLeftBehind());
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindLeftHead());
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindLeftPicture());
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindRight());
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindRightBehind());
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindRightHead());
                        washAfterPhotoList.add(GlobalConsts.DONG_DONG_IMAGE_URL + getAfterPhotoResult.getBihindRightTail());
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // TODO 弹出软键盘后不顶出底部控件
        setContentView(R.layout.activity_evaluation_layout);

        dialogByProgress = new DialogByProgress(OrderEvaluationActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        Intent intent = getIntent();
        orderId = intent.getStringExtra("ORDER_ID");

        if (orderId != null && !TextUtils.isEmpty(orderId)) {
            getWashPhotoByBefore();
            getWashPhotoByAfter();
        }

        initView();
        initListener();
    }

    /**
     * 获取当前订单的洗前照片
     */
    private void getWashPhotoByBefore() {
        dialogByProgress.show();
        Request request = new Request.Builder().url(GlobalConsts.GET_WASH_BEFORE_PHOTO_URL + orderId).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson beforeGson = new Gson();
                GetBeforePhotoRequest getBeforePhotoRequest = beforeGson.fromJson(bodyString, GetBeforePhotoRequest.class);
                Message beforeMsg = Message.obtain();
                beforeMsg.what = GlobalConsts.GET_WASH_BEFORE_PHOTO_HANDLER;
                beforeMsg.obj = getBeforePhotoRequest;
                handler.sendMessage(beforeMsg);
            }
        });
    }

    /**
     * 获取当前订单的洗后照片
     */
    private void getWashPhotoByAfter() {
        dialogByProgress.show();
        Request request = new Request.Builder().url(GlobalConsts.GET_WASH_AFTER_PHOTO_URL + orderId).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Gson afterGson = new Gson();
                GetAfterPhotoRequest getAfterPhotoRequest = afterGson.fromJson(bodyString, GetAfterPhotoRequest.class);
                Message afterMsg = Message.obtain();
                afterMsg.what = GlobalConsts.GET_WASH_AFTER_PHOTO_HANDLER;
                afterMsg.obj = getAfterPhotoRequest;
                handler.sendMessage(afterMsg);
            }
        });
    }

    /**
     * 初始化
     */
    private void initView() {
        evaluationBack = (ImageView) findViewById(R.id.evaluation_back);
        evaluationBeforeCiv = (CircleImageView) findViewById(R.id.evaluation_before_civ);
        evaluationEt = (EditText) findViewById(R.id.evaluation_et);
        evaluationBtn = (Button) findViewById(R.id.evaluation_btn);
        speed2 = (CheckBox) findViewById(R.id.speed_2);
        speed3 = (CheckBox) findViewById(R.id.speed_3);
        speed4 = (CheckBox) findViewById(R.id.speed_4);
        speed5 = (CheckBox) findViewById(R.id.speed_5);
        service2 = (CheckBox) findViewById(R.id.service_2);
        service3 = (CheckBox) findViewById(R.id.service_3);
        service4 = (CheckBox) findViewById(R.id.service_4);
        service5 = (CheckBox) findViewById(R.id.service_5);
        quality2 = (CheckBox) findViewById(R.id.quality_2);
        quality3 = (CheckBox) findViewById(R.id.quality_3);
        quality4 = (CheckBox) findViewById(R.id.quality_4);
        quality5 = (CheckBox) findViewById(R.id.quality_5);
        evaluationSv = (ScrollView) findViewById(R.id.evaluation_sv);
        evaluationSv.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        evaluationSv.setFocusable(true);
        evaluationSv.setFocusableInTouchMode(true);
        evaluationSv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    /**
     * 监听
     */
    private void initListener() {
        evaluationBack.setOnClickListener(this);
        evaluationBtn.setOnClickListener(this);
        evaluationBeforeCiv.setOnClickListener(this);
        speed2.setOnClickListener(this);
        speed3.setOnClickListener(this);
        speed4.setOnClickListener(this);
        speed5.setOnClickListener(this);
        service2.setOnClickListener(this);
        service3.setOnClickListener(this);
        service4.setOnClickListener(this);
        service5.setOnClickListener(this);
        quality2.setOnClickListener(this);
        quality3.setOnClickListener(this);
        quality4.setOnClickListener(this);
        quality5.setOnClickListener(this);
    }

    /**
     * 实现监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluation_back:
                OrderEvaluationActivity.this.finish();
                break;

            case R.id.evaluation_btn:
                washCarSpeed = washCarSpeed.equals("") ? "1" : washCarSpeed;
                washCarService = washCarService.equals("") ? "1" : washCarService;
                washCarQuality = washCarQuality.equals("") ? "1" : washCarQuality;

                dialogByProgress.show();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("orderID", orderId);
                builder.add("washCarSpeed", washCarSpeed);
                builder.add("washCarService", washCarService);
                builder.add("washCarQuality", washCarQuality);
                builder.add("remark", evaluationEt.getText().toString().trim());
                final FormBody body = builder.build();
                Request request = new Request.Builder().url(GlobalConsts.CREATE_ORDER_EVALUATION_URL).post(body).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String bodyString = response.body().string();
                        Gson orderEvaluationGson = new Gson();
                        OrderEvaluationRequest orderEvaluationRequest = orderEvaluationGson.fromJson(bodyString, OrderEvaluationRequest.class);
                        Message orderEvaluationMsg = Message.obtain();
                        orderEvaluationMsg.what = GlobalConsts.CREATE_ORDER_EVALUATION_HANDLER;
                        orderEvaluationMsg.obj = orderEvaluationRequest;
                        handler.sendMessage(orderEvaluationMsg);
                    }
                });
                break;

            case R.id.speed_2:
                speed2.setChecked(true);
                speed3.setChecked(false);
                speed4.setChecked(false);
                speed5.setChecked(false);
                if (speed2.isChecked() == true) {
                    washCarSpeed = "2";
                } else {
                    washCarSpeed = "";
                }
                break;

            case R.id.speed_3:
                speed2.setChecked(true);
                speed3.setChecked(true);
                speed4.setChecked(false);
                speed5.setChecked(false);
                if (speed3.isChecked() == true) {
                    washCarSpeed = "3";
                } else {
                    washCarSpeed = "";
                }
                break;

            case R.id.speed_4:
                speed2.setChecked(true);
                speed3.setChecked(true);
                speed4.setChecked(true);
                speed5.setChecked(false);
                if (speed4.isChecked() == true) {
                    washCarSpeed = "4";
                } else {
                    washCarSpeed = "";
                }
                break;

            case R.id.speed_5:
                speed2.setChecked(true);
                speed3.setChecked(true);
                speed4.setChecked(true);
                speed5.setChecked(true);
                if (speed5.isChecked() == true) {
                    washCarSpeed = "5";
                } else {
                    washCarSpeed = "";
                }
                break;

            case R.id.service_2:
                service2.setChecked(true);
                service3.setChecked(false);
                service4.setChecked(false);
                service5.setChecked(false);
                if (service2.isChecked() == true) {
                    washCarService = "2";
                } else {
                    washCarService = "";
                }
                break;

            case R.id.service_3:
                service2.setChecked(true);
                service3.setChecked(true);
                service4.setChecked(false);
                service5.setChecked(false);
                if (service3.isChecked() == true) {
                    washCarService = "3";
                } else {
                    washCarService = "";
                }
                break;

            case R.id.service_4:
                service2.setChecked(true);
                service3.setChecked(true);
                service4.setChecked(true);
                service5.setChecked(false);
                if (service4.isChecked() == true) {
                    washCarService = "4";
                } else {
                    washCarService = "";
                }
                break;

            case R.id.service_5:
                service2.setChecked(true);
                service3.setChecked(true);
                service4.setChecked(true);
                service5.setChecked(true);
                if (service5.isChecked() == true) {
                    washCarService = "5";
                } else {
                    washCarService = "";
                }
                break;

            case R.id.quality_2:
                quality2.setChecked(true);
                quality3.setChecked(false);
                quality4.setChecked(false);
                quality5.setChecked(false);
                if (quality2.isChecked() == true) {
                    washCarQuality = "2";
                } else {
                    washCarQuality = "";
                }
                break;

            case R.id.quality_3:
                quality2.setChecked(true);
                quality3.setChecked(true);
                quality4.setChecked(false);
                quality5.setChecked(false);
                if (quality3.isChecked() == true) {
                    washCarQuality = "3";
                } else {
                    washCarQuality = "";
                }
                break;

            case R.id.quality_4:
                quality2.setChecked(true);
                quality3.setChecked(true);
                quality4.setChecked(true);
                quality5.setChecked(false);
                if (quality4.isChecked() == true) {
                    washCarQuality = "4";
                } else {
                    washCarQuality = "";
                }
                break;

            case R.id.quality_5:
                quality2.setChecked(true);
                quality3.setChecked(true);
                quality4.setChecked(true);
                quality5.setChecked(true);
                if (quality5.isChecked() == true) {
                    washCarQuality = "5";
                } else {
                    washCarQuality = "";
                }
                break;

            case R.id.evaluation_before_civ:
                Intent intent = new Intent(OrderEvaluationActivity.this, WashPhotoActivity.class);
                intent.putStringArrayListExtra("WASH_BEFORE_PHOTO", (ArrayList<String>) washBeforePhotoList);
                intent.putStringArrayListExtra("WASH_AFTER_PHOTO", (ArrayList<String>) washAfterPhotoList);
                startActivity(intent);
                break;
        }
    }

    private void HideDialogByProgress() {
        if (dialogByProgress != null && dialogByProgress.isShowing()) {
            dialogByProgress.dismiss();
            dialogByProgress = null;
        }
    }
}
