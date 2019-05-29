package com.example.timetablemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;


import adapter.MyPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.antonious.materialdaypicker.MaterialDayPicker;

public class HomeActivity extends AppCompatActivity implements ListPopulateHandle{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    public static ListPopulateHandle listPopulateHandle;

    public List<FragmentListener> fragmentListeners = new ArrayList<>();
    public final List<Fragment> fragments = getFragments();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        listPopulateHandle = this;


        final MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(1000);

        //noinspection deprecation
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPage;

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {


                if (i == ViewPager.SCROLL_STATE_IDLE) {

                    if (currentPage == 0)
                        viewPager.setCurrentItem(myPagerAdapter.getCount() - 2, false);
                    else if (currentPage == myPagerAdapter.getCount() - 1)
                        viewPager.setCurrentItem(1, false);
                }
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            Intent intent = new Intent(this, NewSubject.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(MyFragment.newInstance("SUNDAY"));
        fList.add(MyFragment.newInstance("MONDAY"));
        fList.add(MyFragment.newInstance("TUESDAY"));
        fList.add(MyFragment.newInstance("WEDNESDAY"));
        fList.add(MyFragment.newInstance("THURSDAY"));
        fList.add(MyFragment.newInstance("FRIDAY"));
        fList.add(MyFragment.newInstance("SATURDAY"));
        fList.add(MyFragment.newInstance("SUNDAY"));
        fList.add(MyFragment.newInstance("MONDAY"));

        for(int i = 0 ; i < fList.size(); i++){
            fragmentListeners.add((FragmentListener) fList.get(i));
        }

        return fList;
    }




    @Override
    public void clickHandle(String sub_name, String time, List<MaterialDayPicker.Weekday> weekdays) {
        fragmentListeners.get(3).action();
    }
}
