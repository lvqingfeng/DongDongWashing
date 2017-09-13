package dongdongwashing.com.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;

import java.util.List;

import dongdongwashing.com.R;
import dongdongwashing.com.util.DialogByTwoButton;
import me.xiaopan.sketch.SketchImageView;

/**
 * Created by 26732 on 2016/12/12.
 */
public class MyGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> imagePath;

    public MyGridViewAdapter(List<String> imagePath, Context context) {
        this.imagePath = imagePath;
        this.context = context;
    }


    @Override
    public int getCount() {
        return imagePath.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.griditem_addpic, null);
            holder = new ViewHolder();
            holder.photoIv = (SketchImageView) convertView.findViewById(R.id.sketch_image_view);
            holder.deleteIv = (ImageView) convertView.findViewById(R.id.grid_delete_iv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        if (position == imagePath.size() - 1) {
            holder.photoIv.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.layout.griditem_addpic));
        } else {
            Glide.with(context).load(imagePath.get(position)).into(holder.photoIv);
            MyGridViewAdapter.this.notifyDataSetChanged();
        }

        if (position == imagePath.size() - 1) {
            holder.deleteIv.setVisibility(View.GONE);
            holder.photoIv.setImageResource(R.mipmap.plus);
        } else {
            holder.deleteIv.setVisibility(View.VISIBLE);
        }

        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogByTwoButton deleteBanner = new DialogByTwoButton(context,
                        "提示",
                        "确认移除已添加图片吗？",
                        "取消",
                        "确定"
                );
                deleteBanner.show();
                deleteBanner.setClicklistener(new DialogByTwoButton.ClickListenerInterface() {
                    @Override
                    public void doNegative() {
                        deleteBanner.dismiss();
                    }

                    @Override
                    public void doPositive() {
                        imagePath.remove(position);
                        MyGridViewAdapter.this.notifyDataSetChanged();
                        deleteBanner.dismiss();
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {
        SketchImageView photoIv;
        ImageView deleteIv;
    }

}
