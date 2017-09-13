package dongdongwashing.com.ui.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.adapter.OrderAdapter;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.entity.getUserOrder.GetUserOrderRequest;
import dongdongwashing.com.entity.getUserOrder.GetUserOrderResult;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.GlobalConsts;
import dongdongwashing.com.view.CustomFooterView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 沈 on 2017/3/31.
 */

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView orderBack; // 返回
    private LinearLayout orderNoData;

    private int upOrDown; // 判断动作
    private int mLoadCount = 0;
    public static long lastRefreshTime; // 下拉刷新结束的时间

    private XRefreshView orderXV; // 刷新控件
    private RecyclerView orderRecyclerView; // 订单布局
    private OrderAdapter orderAdapter; // 订单适配器
    private List<GetUserOrderResult> orderList = new ArrayList<>(); // 当前用户全部订单的集合
    private String orderNumberString, orderTimeString, orderStateId; // 获取订单编号查询订单进程记录

    private String id;
    private DialogByProgress dialogByProgress;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalConsts.GET_USER_ORDER_HANDLER:
                    final GetUserOrderRequest getUserOrderRequest = (GetUserOrderRequest) msg.obj;
                    HideDialogByProgress();
                    String getUserOrderState = getUserOrderRequest.getIsSucess();
                    orderList = getUserOrderRequest.getData();
                    if (TextUtils.equals("true", getUserOrderState)) {
                        if (orderList.size() != 0) {

                            orderNoData.setVisibility(View.GONE);
                            orderXV.setVisibility(View.VISIBLE);

                            if (upOrDown == 1) {
                                orderAdapter.setData(orderList);
                                orderXV.stopRefresh();
                            } else if (upOrDown == 2) {
                                orderAdapter.setData(orderList);
                                mLoadCount = orderList.size();
                                if (mLoadCount >= orderList.size()) {
                                    orderXV.setLoadComplete(true);
                                } else {
                                    orderXV.stopLoadMore();
                                }
                            }

                            orderAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    orderNumberString = orderList.get(position).getOrderNumber();
                                    orderTimeString = orderList.get(position).getCreatedDate();
                                    orderStateId = orderList.get(position).getId();
                                    Intent intent = new Intent(OrderActivity.this, OrderRecordActivity.class);
                                    intent.putExtra("order_number", orderNumberString);
                                    intent.putExtra("order_time", orderTimeString);
                                    intent.putExtra("order_id", orderStateId); // 获取订单的id
                                    intent.putExtra("order_state", orderList.get(position).getOrderState());
                                    startActivity(intent);
                                }

                                @Override
                                public void onLongClick(int position) {

                                }
                            });
                        } else {
                            orderNoData.setVisibility(View.VISIBLE);
                            orderXV.setVisibility(View.GONE);
                        }
                    } else {
                        orderNoData.setVisibility(View.VISIBLE);
                        orderXV.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_layout);

        dialogByProgress = new DialogByProgress(OrderActivity.this);
        dialogByProgress.getWindow().setBackgroundDrawableResource(R.color.transparent);

        SharedPreferences spf = OrderActivity.this.getSharedPreferences("user_info", 0);
        id = spf.getString("uid", "");

        initView();
        initListener();

        initRefresh();
    }

    /**
     * 初始化刷新控件
     */
    private void initRefresh() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderRecyclerView.setLayoutManager(layoutManager);
        orderAdapter = new OrderAdapter(OrderActivity.this, orderList);
        orderRecyclerView.setAdapter(orderAdapter);

        orderXV.setPullRefreshEnable(true); // 设置是否可以下拉刷新
        orderXV.setPullLoadEnable(true);  // 设置是否可以上拉加载
        orderXV.setAutoRefresh(true); // 设置可以自动刷新
        orderXV.restoreLastRefreshTime(lastRefreshTime); // 设置上次刷新的时间
        orderXV.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
                requestData();
                upOrDown = 1;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        lastRefreshTime = orderXV.getLastRefreshTime();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                upOrDown = 2;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                }, 500);
            }
        });
        requestData();
    }

    private void requestData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (orderAdapter.getCustomLoadMoreView() == null) {
                    orderAdapter.setCustomLoadMoreView(new CustomFooterView(OrderActivity.this));
                }
                orderAdapter.setData(orderList);
            }
        }, 1000);
    }

    /**
     * 初始化
     */
    private void initView() {
        orderBack = (ImageView) findViewById(R.id.order_back);
        orderNoData = (LinearLayout) findViewById(R.id.order_no_data);
        orderXV = (XRefreshView) findViewById(R.id.order_XV);
        orderRecyclerView = (RecyclerView) findViewById(R.id.order_recyclerView);
    }

    /**
     * 从服务器获取数据并适配
     */
    private void initData() {
        dialogByProgress.show();
        Request request = new Request.Builder().url(GlobalConsts.GET_USER_ORDER_URL + id).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bodyString = response.body().string();
                Log.d("test", "订单记录---------------" + bodyString.toString());
                Gson getUserOrderGson = new Gson();
                GetUserOrderRequest getUserOrderRequest = getUserOrderGson.fromJson(bodyString, GetUserOrderRequest.class);
                Message getUserOrderMsg = Message.obtain();
                getUserOrderMsg.what = GlobalConsts.GET_USER_ORDER_HANDLER;
                getUserOrderMsg.obj = getUserOrderRequest;
                handler.sendMessage(getUserOrderMsg);
            }
        });
    }

    /**
     * 监听
     */
    private void initListener() {
        orderBack.setOnClickListener(this);
    }

    /**
     * 实现监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_back: // 返回
                OrderActivity.this.finish();
                break;
        }
    }

    private void HideDialogByProgress() {
        if (dialogByProgress != null && dialogByProgress.isShowing()) {
            dialogByProgress.dismiss();
        }
    }

}
