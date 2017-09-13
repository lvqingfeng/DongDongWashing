package dongdongwashing.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dongdongwashing.com.R;

/**
 * Created by 沈 on 2017/4/18.
 */

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<String> SearchResult;

    public SearchAdapter(Context context, List<String> searchResult) {
        this.context = context;
        this.SearchResult = searchResult;
    }

    @Override
    public int getCount() {
        return SearchResult == null ? 0 : SearchResult.size();
    }

    @Override
    public Object getItem(int position) {
        return SearchResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.search_item_adapter_layout, null);
            holder.searchTv = (TextView) convertView.findViewById(R.id.search_tv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.searchTv.setText(SearchResult.get(position));
        return convertView;
    }

    class ViewHolder {
        private TextView searchTv;
    }
}
