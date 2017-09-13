package dongdongwashing.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.entity.getOrderStateByRecord.OrderStateResult;
import dongdongwashing.com.util.DataConversionByShen;

/**
 * Created by 沈 on 2017/5/11.
 */

public class OrderRecordAdapter extends RecyclerView.Adapter<OrderRecordAdapter.orderRecordViewHolder> {

    private View view;
    private Context context;
    private LayoutInflater inflater;
    private List<OrderStateResult> orderRecordList;
    private String orderStateString, orderStateTimeString;

    public OrderRecordAdapter(Context context, List<OrderStateResult> orderRecordList) {
        this.context = context;
        this.orderRecordList = orderRecordList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public OrderRecordAdapter.orderRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.order_record_item_adapter_layout, null);
        orderRecordViewHolder viewHolder = new orderRecordViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderRecordAdapter.orderRecordViewHolder holder, int position) {
        String orderStateStr = orderRecordList.get(position).getOrderState();
        orderStateString = DataConversionByShen.getOrderStateByRecord(orderStateStr);

        orderStateTimeString = orderRecordList.get(position).getOrderStateTime();
        String[] time = orderStateTimeString.split("[T.]");

        holder.itemOrderRecordState.setText(orderStateString);
        holder.itemOrderRecordTime.setText(time[0] + " " + time[1]);
    }

    @Override
    public int getItemCount() {
        return orderRecordList == null ? 0 : orderRecordList.size();
    }

    class orderRecordViewHolder extends RecyclerView.ViewHolder {

        TextView itemOrderRecordState; // 订单的状态
        TextView itemOrderRecordTime; // 订单时间

        public orderRecordViewHolder(View itemView) {
            super(itemView);
            itemOrderRecordState = (TextView) itemView.findViewById(R.id.item_order_record_state);
            itemOrderRecordTime = (TextView) itemView.findViewById(R.id.item_order_record_time);
        }
    }

}
