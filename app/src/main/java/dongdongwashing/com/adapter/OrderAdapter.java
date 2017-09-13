package dongdongwashing.com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;

import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.entity.getUserOrder.GetUserOrderResult;
import dongdongwashing.com.util.DataConversionByShen;

/**
 * Created by 沈 on 2017/4/10.
 */

public class OrderAdapter extends BaseRecyclerAdapter<OrderAdapter.orderViewHolder> {

    private View view;
    private Context context;
    private List<GetUserOrderResult> orderList;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;
    private String orderStateString, orderTime;

    public OrderAdapter(Context context, List<GetUserOrderResult> orderList) {
        this.context = context;
        this.orderList = orderList;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onClick(int position);

        void onLongClick(int position);

    }

    public void setData(List<GetUserOrderResult> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public orderViewHolder getViewHolder(View view) {
        return new orderViewHolder(view, false);
    }

    @Override
    public orderViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        view = inflater.inflate(R.layout.order_item_adapter_layout, parent, false);
        orderViewHolder viewHolder = new orderViewHolder(view, true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(orderViewHolder holder, final int position, boolean isItem) {
        String orderTypeStr = orderList.get(position).getOrderType(); // 获取订单的类型
        if (orderTypeStr.equals("0")) {
            holder.orderType.setText("上门洗车");
        } else if (orderTypeStr.equals("1")) {
            holder.orderType.setText("预约洗车");
        }

        orderStateString = DataConversionByShen.getOrderStateMsg(orderList.get(position).getOrderState());

        if ("待支付".equals(orderStateString)) {
            holder.orderTypeMessage.setTextColor(Color.parseColor("#ff0000"));
            // holder.orderTypeMessage.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else if ("已完成".equals(orderStateString)) {
            holder.orderTypeMessage.setTextColor(Color.parseColor("#30d123"));
        } else if ("进行中".equals(orderStateString)) {
            holder.orderTypeMessage.setTextColor(Color.parseColor("#0066ff"));
        } else if ("已预约".equals(orderStateString)) {
            holder.orderTypeMessage.setTextColor(Color.parseColor("#ec6941"));
        } else if ("已取消".equals(orderStateString)) {
            holder.orderTypeMessage.setTextColor(Color.parseColor("#010101"));
        }

        orderTime = orderList.get(position).getCreatedDate();
        String[] time = orderTime.split("[T.]");
        holder.orderDate.setText(time[0]);
        holder.orderTime.setText(time[1]);
        holder.orderTypeMessage.setText(orderStateString);
        holder.orderNumber.setText(orderList.get(position).getOrderNumber());
        holder.orderLocation.setText(orderList.get(position).getAddress());
        holder.orderCarPlate.setText(orderList.get(position).getCarNumber());
        holder.orderCarType.setText(orderList.get(position).getAccount().getCarModel());
        holder.orderCarColor.setText(orderList.get(position).getAccount().getCarColor());

        // Glide.with(context).load(orderList.get(position)).into(holder.orderIv);

        /**
         * 点击监听
         */
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            /**
             * 点击长按监听
             */
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getAdapterItemCount() {
        return orderList == null ? 0 : orderList.size();
    }

    /**
     * ViewHolder类
     */
    class orderViewHolder extends RecyclerView.ViewHolder {

        TextView orderType; // 清洗的类型
        TextView orderTypeMessage; // 清洗的状态
        TextView orderDate; // 订单的日期
        TextView orderTime; // 订单的时间
        TextView orderNumber; // 订单编号
        TextView orderLocation; // 订单生成的位置
        TextView orderCarPlate; // 订单生成的车牌
        TextView orderCarType; // 订单生成的车型
        TextView orderCarColor; // 订单生成的车颜色
        ImageView orderIv; // 订单生成后的车辆洗前洗后的照片

        public orderViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                orderType = (TextView) itemView.findViewById(R.id.order_type);
                orderTypeMessage = (TextView) itemView.findViewById(R.id.order_type_message);
                orderDate = (TextView) itemView.findViewById(R.id.order_date);
                orderTime = (TextView) itemView.findViewById(R.id.order_time);
                orderNumber = (TextView) itemView.findViewById(R.id.order_number);
                orderLocation = (TextView) itemView.findViewById(R.id.order_location);
                orderCarPlate = (TextView) itemView.findViewById(R.id.order_car_plate);
                orderCarType = (TextView) itemView.findViewById(R.id.order_car_type);
                orderCarColor = (TextView) itemView.findViewById(R.id.order_car_color);
                orderIv = (ImageView) itemView.findViewById(R.id.order_iv);
            }
        }
    }
}
