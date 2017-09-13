package dongdongwashing.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 沈 on 2017/4/13.
 */

public class IntegralAdapter extends FragmentPagerAdapter {

    private List<Fragment> integralFragment;

    public IntegralAdapter(FragmentManager fm, List<Fragment> integralFragment) {
        super(fm);
        this.integralFragment = integralFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return integralFragment.get(position);
    }

    @Override
    public int getCount() {
        return integralFragment.size();
    }
}
