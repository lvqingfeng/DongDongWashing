package dongdongwashing.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import dongdongwashing.com.R;

/**
 * Created by 沈 on 2017/5/23.
 */

public class AboutCarAdapter extends RecyclerView.Adapter<AboutCarAdapter.aboutCarViewHolder> {

    private Context context;
    private List<String> photoList;
    private View view;
    private LayoutInflater inflater;

    public AboutCarAdapter(Context context, List<String> photoList) {
        this.context = context;
        this.photoList = photoList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AboutCarAdapter.aboutCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.about_car_photo_item_adapter_layout, null);
        aboutCarViewHolder viewHolder = new aboutCarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AboutCarAdapter.aboutCarViewHolder holder, int position) {
        String photoString = photoList.get(position % photoList.size());
        Glide.with(context).load(photoString).into(holder.aboutCarPhotoIv);
    }

    @Override
    public int getItemCount() {
        return photoList == null ? 0 : photoList.size();
    }

    class aboutCarViewHolder extends RecyclerView.ViewHolder {

        ImageView aboutCarPhotoIv;

        public aboutCarViewHolder(View itemView) {
            super(itemView);
            aboutCarPhotoIv = (ImageView) itemView.findViewById(R.id.about_car_photo_iv);
        }
    }
}
