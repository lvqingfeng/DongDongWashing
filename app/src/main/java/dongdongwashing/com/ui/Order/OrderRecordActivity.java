package dongdongwashing.com.ui.Order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.adapter.OrderRecordAdapter;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.getOrderStateByRecord.OrderStateRequest;
import dongdongwashing.com.entity.getOrderStateByRecord.OrderStateResult;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.GlobalConsts;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/5/10.
 */

public class OrderRecordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView orderRecordBack;
    private TextView orderEvaluation; // 评价
    private TextView orderNumberByRecord;
    private TextView orderTimeByRecord;
    private RecyclerView orderRecordRecyclerView;
    private String orderNumberString, orderTimeString, orderStateId, orderState;
    private OrderRecordAdapter adapter;
    private List<OrderStateResult> orderRecordList = new ArrayList<>();
    private String orderTimeStr, orderTime1, orderTime2;

    private DialogByProgress dialogByProgress;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.GET_ORDER_STATE_BY_RECORD_HANDLER:
                    OrderStateRequest orderStateRequest = (OrderStateRequest) msg.obj;
                    if (dialogByProgress != null && dialogByProgress.isShowing()) {
                        dialogByProgress.dismiss();
                    }
                    String orderStateString = orderStateRequest.getMsg();
                    if (orderStateString.equals("订单状态记录")) {
                        orderRecordList = orderStateRequest.getData();
                        adapter = new OrderRecordAdapter(OrderRecordActivity.this, orderRecordList);
                        LinearLayoutManager lm = new LinearLayoutManager(OrderRecordActivity.this);
                        lm.setOrientation(OrientationHelper.VERTICAL);
                        orderRecordRecyclerView.setLayoutManager(lm);
                        orderRecordRecyclerView.setAdapter(adapter);
                        orderRecordRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record_layout);

        dialogByProgress = new DialogByProgress(OrderRecordActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        Intent intent = getIntent();
        orderNumberString = intent.getStringExtra("order_number");
        orderTimeStr = intent.getStringExtra("order_time");
        orderStateId = intent.getStringExtra("order_id"); // 获取当前订单的id
        orderState = intent.getStringExtra("order_state");

        String[] time = orderTimeStr.split("[T.]"); // TODO 字符串多个分割符截取
        orderTime1 = time[0];
        orderTime2 = time[1];
        orderTimeString = orderTime1 + " " + orderTime2;

        initView();
        initListener();
        getOrderByRecord(orderStateId);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        orderRecordBack = (ImageView) findViewById(R.id.order_record_back);
        orderEvaluation = (TextView) findViewById(R.id.order_evaluation);
        orderNumberByRecord = (TextView) findViewById(R.id.order_number_by_record);
        orderTimeByRecord = (TextView) findViewById(R.id.order_time_by_record);
        orderRecordRecyclerView = (RecyclerView) findViewById(R.id.order_record_recyclerView);

        if (!TextUtils.isEmpty(orderNumberString) && !TextUtils.isEmpty(orderTimeString)) {
            orderNumberByRecord.setText(orderNumberString);
            orderTimeByRecord.setText(orderTimeString);
        }

        if (orderState.equals("5")) {
            orderEvaluation.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 监听
     */
    private void initListener() {
        orderRecordBack.setOnClickListener(this);
        orderEvaluation.setOnClickListener(this);
    }

    /**
     * 根据订单号查询订单状态记录
     */
    private void getOrderByRecord(String orderStateId) {
        dialogByProgress.show();
        Request request = new Request.Builder().url(GlobalConsts.GET_ORDER_STATE_BY_RECORD_URL + orderStateId).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                // Log.d("test", "当前订单的状态----" + bodyString.toString());
                Gson orderStateGson = new Gson();
                OrderStateRequest orderStateRequest = orderStateGson.fromJson(bodyString, OrderStateRequest.class);
                Message orderStateMsg = Message.obtain();
                orderStateMsg.what = GlobalConsts.GET_ORDER_STATE_BY_RECORD_HANDLER;
                orderStateMsg.obj = orderStateRequest;
                handler.sendMessage(orderStateMsg);
            }
        });
    }

    /**
     * 实现监听
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_record_back:
                OrderRecordActivity.this.finish();
                break;

            case R.id.order_evaluation:
                Intent intent = new Intent(OrderRecordActivity.this, OrderEvaluationActivity.class);
                intent.putExtra("ORDER_ID", orderStateId);
                startActivity(intent);
                break;
        }
    }
}
