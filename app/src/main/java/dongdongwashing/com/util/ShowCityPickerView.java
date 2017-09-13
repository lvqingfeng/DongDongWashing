package dongdongwashing.com.util;

import android.content.Context;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.entity.localcity.ProvinceBean;


/**
 * Created by 沈 on 2016/11/24.
 */

public class ShowCityPickerView {

    private OptionsPickerView cityOptions;
    private Context context;
    // 省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    // 城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    // 区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    private TextView contextView; // 点击显示城市选择器
    private String fileName;

    public ShowCityPickerView(Context context, TextView textView, String fileName) {
        this.context = context;
        this.contextView = textView;
        this.fileName = fileName;
    }

    public void showCityPicker() {

        provinceBeanList.clear();
        //  创建选项选择器
        cityOptions = new OptionsPickerView(context);

        //  获取json数据
        String province_data_json = JsonFileReader.getJson(context, fileName);

        //  解析json数据
        parseJson(province_data_json);

        //  设置三级联动效果
        cityOptions.setPicker(provinceBeanList, cityList, districtList, true);

        // 设置是否循环滚动
        cityOptions.setCyclic(false, false, false);

        // 设置默认选中的三级项目
        cityOptions.setSelectOptions(0, 0, 0);

        /**
         * 监听确定选择按钮
         */
        cityOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                // 返回的分别是三个级别的选中位置
                String city = provinceBeanList.get(options1).getPickerViewText();
                // 最终获取设置后城市
                String address;
                // 如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "上海市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1).getPickerViewText() + "" + districtList.get(options1).get(option2).get(options3);
                } else {
                    address = provinceBeanList.get(options1).getPickerViewText() + "—" + cityList.get(options1).get(option2) + "—" + districtList.get(options1).get(option2).get(options3);
                }
                contextView.setText(address);
                contextView.setTextColor(context.getResources().getColor(R.color.orange));
            }
        });
        cityOptions.setTitle("请选择城市地区");
        cityOptions.show();
    }

    /**
     * 解析城市JSON数据
     *
     * @param str
     */
    private void parseJson(String str) {
        try {
            // 获取JSON中的数组
            JSONArray jsonArray = new JSONArray(str);
            // 遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                // 获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                // 获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                // 获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>(); // 声明存放城市的集合
                districts = new ArrayList<>(); // 声明存放区县集合的集合
                // 遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    // 获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    // 将城市放入集合
                    String cityNmae = cityObject.optString("name");
                    cities.add(cityNmae);
                    district = new ArrayList<>(); // 声明存放区县的集合
                    // 获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    // 遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    // 将区县的集合放入集合
                    districts.add(district);
                }
                // 将存放区县集合的集合放入集合
                districtList.add(districts);
                // 将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击隐藏
     */
    public void hidePickerView() {
        if (cityOptions != null) {
            cityOptions.dismiss();
            cityOptions = null;
        }
    }
}
