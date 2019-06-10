package com.example.timetablemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import adapter.MyPagerAdapter;
import adapter.myDbAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import ca.antonious.materialdaypicker.MaterialDayPicker;

public class HomeActivity extends AppCompatActivity implements ListPopulateHandle {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private long db_id = 0;
    public static ListPopulateHandle listPopulateHandle;
    public List<Subject> dbList = new ArrayList<>();

    public static List<FragmentListener> fragmentListeners = new ArrayList<>();
    public final List<Fragment> fragments = getFragments();
    private myDbAdapter dbAdapter;
    public DatabaseFetchTask databaseFetchTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        dbAdapter = new myDbAdapter(this);
        databaseFetchTask = new DatabaseFetchTask();
        databaseFetchTask.execute();

        setSupportActionBar(toolbar);
        listPopulateHandle = this;
        setFragmentListeners();

        final MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myPagerAdapter);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                viewPager.setCurrentItem(7);
                break;
            case Calendar.MONDAY:
                viewPager.setCurrentItem(1);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(2);
                break;
            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(3);
                break;
            case Calendar.THURSDAY:
                viewPager.setCurrentItem(4);
                break;
            case Calendar.FRIDAY:
                viewPager.setCurrentItem(5);
                break;
            case Calendar.SATURDAY:
                viewPager.setCurrentItem(6);
                break;
        }
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
        return fList;
    }

    public void setFragmentListeners() {
        for (int i = 0; i < fragments.size(); i++) {
            fragmentListeners.add((FragmentListener) fragments.get(i));
        }
    }


    @Override
    public void clickHandle(String sub_name, String time, List<MaterialDayPicker.Weekday> weekdays) {

        for (int pos = 0; pos < weekdays.size(); pos++) {
            db_id = dbAdapter.insertData(sub_name, time, String.valueOf(weekdays.get(pos)));
            Log.d("elementId", String.valueOf(db_id));
        }

        for (int i = 0; i < fragmentListeners.size(); i++) {
            FragmentListener fragmentListener = fragmentListeners.get(i);
            for (int k = 0; k < weekdays.size(); k++) {
                if ((fragmentListener.getFragmentName()).equals(String.valueOf(weekdays.get(k)))) {
                    fragmentListener.populateList(db_id, sub_name, time, String.valueOf(weekdays.get(k)));
                }
            }
        }

    }

    public void initialiseFrag() {
        for (int index = 0; index < fragmentListeners.size(); index++) {
            FragmentListener fragmentListener = fragmentListeners.get(index);
            for (int secondIndex = 0; secondIndex < dbList.size(); secondIndex++) {
                if ((dbList.get(secondIndex).getDayOfWeek()).equals(fragmentListener.getFragmentName())) {
                    fragmentListener.populateList(dbList.get(secondIndex).getElement_id(), dbList.get(secondIndex).getSubjectName(), dbList.get(secondIndex).getTime(), dbList.get(secondIndex).getDayOfWeek());
                }
            }
        }
    }

    //ASYNCHRONOUS TASK INNER CLASS;
    public class DatabaseFetchTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor = dbAdapter.fetch();
            if (cursor.getCount() == 0) {
                Log.d("Empty Database", String.valueOf(cursor.getCount()));
                return null;
            }

            dbList = new ArrayList<>();
            while (cursor.moveToNext()) {
                dbList.add(new Subject(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("DB LIST SIZE", String.valueOf(dbList.size()));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initialiseFrag();
                }
            }, 50);


        }
    }

}
