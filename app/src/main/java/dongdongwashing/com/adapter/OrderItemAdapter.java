package dongdongwashing.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.entity.GetOrderItemRequest;

/**
 * Created by 沈 on 2017/7/19.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private View view;
    private Context context;
    private LayoutInflater inflater;
    private List<GetOrderItemRequest.GetOrderItemResult> getOrderItemResultList;
    private List<String> orderItemIdList; // 清洗项目价钱集合
    private List<String> orderItemList; // 清洗项目文字集合
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    public OrderItemAdapter(Context context, List<GetOrderItemRequest.GetOrderItemResult> getOrderItemResultList) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.getOrderItemResultList = getOrderItemResultList;
        orderItemIdList = new ArrayList<>();
        orderItemList = new ArrayList<>();
    }

    public List<String> getOrderItemIdList() {
        return orderItemIdList;
    }

    public List<String> getOrderItemList() {
        return orderItemList;
    }

    @Override
    public OrderItemAdapter.OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.order_washing_item_adapter_layout, null);
        OrderItemViewHolder viewHolder = new OrderItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderItemAdapter.OrderItemViewHolder holder, final int position) {
        holder.orderItemName.setText(getOrderItemResultList.get(position).getItemName());
        holder.orderItemPrice.setText(getOrderItemResultList.get(position).getItemPrice() + " 元");
        holder.orderItemCb.setTag(position);
        holder.orderItemCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) buttonView.getTag();
                if (isChecked) {
                    mCheckStates.put(pos, true);
                    orderItemIdList.add(0, getOrderItemResultList.get(position).getItemPrice());
                    orderItemList.add(0, getOrderItemResultList.get(position).getItemName());
                } else {
                    mCheckStates.delete(pos);
                    orderItemIdList.remove(getOrderItemResultList.get(position).getItemPrice());
                    orderItemList.remove(getOrderItemResultList.get(position).getItemName());
                }
            }
        });
        holder.orderItemCb.setChecked(mCheckStates.get(position, false));
    }

    @Override
    public int getItemCount() {
        return getOrderItemResultList == null ? 0 : getOrderItemResultList.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {

        TextView orderItemName; // 清洗项目名称
        TextView orderItemPrice; // 清洗项目价钱
        CheckBox orderItemCb; // 清洗项目选择

        public OrderItemViewHolder(View itemView) {
            super(itemView);
            orderItemName = (TextView) itemView.findViewById(R.id.order_item_name);
            orderItemPrice = (TextView) itemView.findViewById(R.id.order_item_price);
            orderItemCb = (CheckBox) itemView.findViewById(R.id.order_item_cb);
        }
    }
}
