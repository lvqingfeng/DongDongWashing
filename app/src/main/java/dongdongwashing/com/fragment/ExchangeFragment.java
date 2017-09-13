package dongdongwashing.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.adapter.ExchangeAdapter;

/**
 * Created by 沈 on 2017/4/13.
 */

public class ExchangeFragment extends Fragment {

    private View rootView;
    private RecyclerView exchangeRecyclerView; // 布局
    private TextView exchangePrompt; // 没有订单时的提醒文字
    private ExchangeAdapter exchangeAdapter; // 数据的适配器
    private List<String> exchangeList = new ArrayList<>(); // 数据的集合

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_exchange_layout, container, false);
            initView();
            initData();
        }
        return rootView;
    }

    /**
     * 初始化
     */
    private void initView() {
        exchangeRecyclerView = (RecyclerView) rootView.findViewById(R.id.exchange_RecyclerView);
        exchangePrompt = (TextView) rootView.findViewById(R.id.exchange_prompt);
    }

    /**
     * 从服务器获取数据并适配
     */
    private void initData() {
        if (exchangeList.size() == 0) {
            exchangePrompt.setVisibility(View.VISIBLE);
        } else {
            exchangePrompt.setVisibility(View.GONE);
            exchangeAdapter = new ExchangeAdapter(ExchangeFragment.this.getContext(), exchangeList);
            LinearLayoutManager lm = new LinearLayoutManager(ExchangeFragment.this.getContext());
            lm.setOrientation(OrientationHelper.VERTICAL);
            exchangeRecyclerView.setLayoutManager(lm);
            exchangeRecyclerView.setAdapter(exchangeAdapter);
            exchangeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }
}
