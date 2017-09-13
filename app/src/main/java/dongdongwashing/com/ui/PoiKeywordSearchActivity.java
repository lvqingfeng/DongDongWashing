package dongdongwashing.com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.adapter.SearchAdapter;
import dongdongwashing.com.com.BaseActivity;
import dongdongwashing.com.util.DialogByOneButton;
import dongdongwashing.com.util.DialogByProgress;
import dongdongwashing.com.util.ShowCityPickerView;

/**
 * Created by 沈 on 2017/4/18.
 */

public class PoiKeywordSearchActivity extends BaseActivity implements View.OnClickListener, PoiSearch.OnPoiSearchListener {

    private AutoCompleteTextView searchAuto; // 文字输入框
    private ImageView searchEditNameClear; // 清除文字
    private TextView searchOrCancel; // 取消或搜索
    private TextView areaTvShow; // 地区筛选
    private ShowCityPickerView showCityPickerView;
    private TextView locationMessageTv; // 定位地址
    private ListView searchListView; // 数据布局
    private String city, positionMessage;
    private ArrayList<PoiItem> poiItems = new ArrayList<>(); // 搜索到的数据集合
    private SearchAdapter adapter;
    private DialogByProgress dialog; // 菊花
    private DialogByOneButton promptDialog;

    private String keyWord = ""; // 要输入的poi搜索关键字
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query; // Poi查询条件类
    private PoiSearch poiSearch; // POI搜索

    private Intent searchIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poikey_search_layout);

        dialog = new DialogByProgress(PoiKeywordSearchActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        Intent intent = getIntent();
        city = intent.getStringExtra("city"); // 获取的定位城市
        positionMessage = intent.getStringExtra("positionMessage"); // 获取的定位地址

        initView();
        initData();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        searchAuto = (AutoCompleteTextView) findViewById(R.id.search_auto); // 文字输入框
        searchEditNameClear = (ImageView) findViewById(R.id.search_edit_name_clear); // 清除文字
        searchOrCancel = (TextView) findViewById(R.id.search_or_cancel); // 取消或搜索
        locationMessageTv = (TextView) findViewById(R.id.location_message_tv); // 定位地址
        searchListView = (ListView) findViewById(R.id.search_list_view); // 数据布局

        areaTvShow = (TextView) findViewById(R.id.area_tv_show); // 地区筛选的三级联动
        showCityPickerView = new ShowCityPickerView(this, areaTvShow, "province_data.json");

        searchAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchEditNameClear.setVisibility(View.VISIBLE);
                    searchOrCancel.setText("搜索");
                    searchOrCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!"".equals(searchAuto.getText().toString().trim())) {
                                keyWord = searchAuto.getText().toString().trim();
                                doSearchQuery(keyWord); // 执行搜索功能
                                dialog.show();
                            }
                        }
                    });
                } else if (s.length() == 0) {
                    searchEditNameClear.setVisibility(View.GONE);
                    searchOrCancel.setText("取消");
                    searchOrCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PoiKeywordSearchActivity.this.finish();
                        }
                    });
                }

                String newText = s.toString().trim();
                if (!TextUtils.isEmpty(newText)) {
                    InputtipsQuery inputQuery = new InputtipsQuery(newText, searchAuto.getText().toString().trim());
                    Inputtips inputTips = new Inputtips(PoiKeywordSearchActivity.this, inputQuery);
                    inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                        @Override
                        public void onGetInputtips(List<Tip> tipList, int rCode) {
                            if (rCode == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                                List<String> listString = new ArrayList<String>();
                                for (int i = 0; i < tipList.size(); i++) {
                                    listString.add(tipList.get(i).getName());
                                }
                                ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.route_inputs, listString);
                                searchAuto.setAdapter(aAdapter);
                                aAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    inputTips.requestInputtipsAsyn();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 根据定位地址获取当前区域的相关地址
     */
    private void initData() {
        areaTvShow.setText(city);
        locationMessageTv.setText(positionMessage);
        if (!TextUtils.isEmpty(positionMessage)) {
            doSearchQuery(positionMessage); // 执行附近地址搜索
        }
    }

    /**
     * 携带关键字搜索相关地址
     */
    private void doSearchQuery(String keyWord) {
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", city);
        query.setPageSize(15);
        query.setPageNum(currentPage);
        query.setCityLimit(true);
        poiSearch = new PoiSearch(PoiKeywordSearchActivity.this, query);
        poiSearch.setOnPoiSearchListener(PoiKeywordSearchActivity.this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 监听
     */
    private void initListener() {
        searchEditNameClear.setOnClickListener(this); // 清除文字
        searchOrCancel.setOnClickListener(this); // 搜索或取消
        areaTvShow.setOnClickListener(this); // 地区筛选
    }

    /**
     * 实现监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_edit_name_clear: // 清除输入框数字
                searchAuto.setText("");
                break;

            case R.id.search_or_cancel:
                PoiKeywordSearchActivity.this.finish(); // 取消
                break;

            case R.id.area_tv_show:
                showCityPickerView.showCityPicker();
                break;
        }
    }

    /**
     * POI信息查询回调方法
     *
     * @param result
     * @param rCode
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) { // 搜索poi的结果
                if (result.getQuery().equals(query)) { // 是否是同一条
                    poiResult = result;
                    dialog.dismiss();
                    // 取得搜索到的poiitems有多少页
                    poiItems = poiResult.getPois(); // 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys(); // 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        final List<String> poiItemsList = new ArrayList<>();
                        for (int j = 0; j < poiItems.size(); j++) {
                            poiItemsList.add(poiItems.get(j).toString());
                        }
                        adapter = new SearchAdapter(PoiKeywordSearchActivity.this, poiItemsList);
                        searchListView.setAdapter(adapter);
                        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                dialog.show();
                                String poiItemsString = poiItemsList.get(position);
                                searchIntent.setAction("SEARCH_ADDRESS");
                                searchIntent.putExtra("searchName", poiItemsString);
                                sendBroadcast(searchIntent);
                                PoiKeywordSearchActivity.this.finish();
                            }
                        });
                    } else if (poiItems.size() == 0) {
                        promptDialog = new DialogByOneButton(PoiKeywordSearchActivity.this,
                                "提示",
                                "未搜索到相关地址，请重新输入详细地址",
                                "确定"
                        );
                        promptDialog.show();
                        promptDialog.setClicklistener(new DialogByOneButton.ClickListenerInterface() {
                            @Override
                            public void doPositive() {
                                promptDialog.dismiss();
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int rCode) {

    }
}
