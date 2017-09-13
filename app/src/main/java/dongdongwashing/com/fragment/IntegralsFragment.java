package dongdongwashing.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dongdongwashing.com.R;

/**
 * Created by 沈 on 2017/4/13.
 */

public class IntegralsFragment extends Fragment {

    private View rootView;
    private ImageView integralsImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_integrals_layout, container, false);
            integralsImageView = (ImageView) rootView.findViewById(R.id.integrals_image_view);
            Glide.with(IntegralsFragment.this).load(R.mipmap.integral_iv).into(integralsImageView);
        }
        return rootView;
    }
}
