package illuminometer.com.example.android.mytestble.fragmentTest.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import illuminometer.com.example.android.mytestble.R;

public class FragmentActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    TabLayout tabLayout;
    ViewPager  viewPager;
    MyViewPagerAdapter adapter;
    private ArrayList<Fragment> fragmentArrayList;
    private String[] titles=new String[]{"FC","F","Lux","Tem"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initView();
    }
    public void initView(){
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.myviewpager);
        fragmentArrayList=new ArrayList<>();
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for(String title:titles){
            tabLayout.addTab(tabLayout.newTab().setTag(title));
        }
        fragmentArrayList.add(new FCFragment());
        fragmentArrayList.add(new FFragment());
        fragmentArrayList.add(new LuxFragment());
        fragmentArrayList.add(new TemFragment());
        adapter=new MyViewPagerAdapter(getSupportFragmentManager(),fragmentArrayList,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
