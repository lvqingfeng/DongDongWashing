package dongdongwashing.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dongdongwashing.com.R;

/**
 * Created by 沈 on 2017/4/13.
 */

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.exchangeViewHolder> {

    private View view;
    private Context context;
    private List<String> exchangeList;
    private LayoutInflater inflater;

    public ExchangeAdapter(Context context, List<String> exchangeList) {
        this.context = context;
        this.exchangeList = exchangeList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ExchangeAdapter.exchangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.exchange_item_adapter_layout, parent, false);
        exchangeViewHolder viewHolder = new exchangeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ExchangeAdapter.exchangeViewHolder holder, int position) {
        holder.exchangeType.setText(exchangeList.get(position));
        holder.exchangeMoney.setText(exchangeList.get(position));
        holder.exchangeNumber.setText(exchangeList.get(position));
        holder.exchangeTime.setText(exchangeList.get(position));
        holder.exchangeNumbering.setText(exchangeList.get(position));
    }

    @Override
    public int getItemCount() {
        return exchangeList == null ? 0 : exchangeList.size();
    }

    class exchangeViewHolder extends RecyclerView.ViewHolder {

        TextView exchangeType; // 兑换方式
        TextView exchangeMoney; // 兑换金额
        TextView exchangeNumber; // 兑换积分
        TextView exchangeTime; // 兑换的订单时间
        TextView exchangeNumbering; // 兑换的订单编号

        public exchangeViewHolder(View itemView) {
            super(itemView);
            exchangeType = (TextView) itemView.findViewById(R.id.exchange_type);
            exchangeMoney = (TextView) itemView.findViewById(R.id.exchange_money);
            exchangeNumber = (TextView) itemView.findViewById(R.id.exchange_number);
            exchangeTime = (TextView) itemView.findViewById(R.id.exchange_time);
            exchangeNumbering = (TextView) itemView.findViewById(R.id.exchange_numbering);
        }
    }
}
