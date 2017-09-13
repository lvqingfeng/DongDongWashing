package dongdongwashing.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 沈 on 2017/3/31.
 */

public class LoginAdapter extends FragmentPagerAdapter {

    private List<Fragment> loginFragment;

    public LoginAdapter(FragmentManager fm, List<Fragment> loginFragment) {
        super(fm);
        this.loginFragment = loginFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return loginFragment.get(position);
    }

    @Override
    public int getCount() {
        return loginFragment.size();
    }
}
