package illuminometer.com.example.android.mytestble.fragmentTest.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by android on 2017/11/20.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
     public  ArrayList<Fragment>  fragmentArray;
     public String[] titles;
    public MyViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentArray,String[] titles) {
        super(fm);
        this.fragmentArray=fragmentArray;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArray.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragmentArray.size();
    }
}
