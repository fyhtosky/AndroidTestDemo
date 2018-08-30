package com.example.administrator.diycode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidtestdemo.R;
import com.example.administrator.androidtestdemo.activity.GankActivity;
import com.example.administrator.androidtestdemo.activity.MainActivity;
import com.example.administrator.androidtestdemo.activity.MovieActivity;
import com.example.administrator.androidtestdemo.manager.PackageInfoManager;
import com.example.administrator.diycode.fragment.GankFragment;
import com.example.administrator.diycode.fragment.MoiveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiycodeMainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private GankFragment gankFragment;
    private MoiveFragment moiveFragment;
    private GankFragment testFragment;
    private int mCurrentPosition = 0;
    private boolean isToolbarFirstClick = true;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diycode_main);
        ButterKnife.bind(this);
        initMenu();
        initViewPager();
    }

    private void initViewPager() {
        gankFragment=GankFragment.newInstance();
        moiveFragment=MoiveFragment.newInstance();
        testFragment=GankFragment.newInstance();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] types = {"美女", "电影", "娱乐"};

            @Override
            public Fragment getItem(int position) {
                if (position == 0)
                    return gankFragment;
                if (position == 1)
                    return moiveFragment;
                if (position == 2)
                    return testFragment;
                return MoiveFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return types[position];
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        viewPager.setCurrentItem(mCurrentPosition);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initMenu() {
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_post) {
                    openActivity(MovieActivity.class);
                    return true;

                } else if (id == R.id.nav_collect) {
                    openActivity(GankActivity.class);
                   return true;
                } else if (id == R.id.nav_about) {
                    openActivity(AudioActivity.class);
                } else if (id == R.id.nav_setting) {
//                    openActivity(MainActivity.class);
                    PackageInfoManager.startThridApp(DiycodeMainActivity.this,"com.wisemen.hhb");
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // 双击 666
        final GestureDetector detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                quickToTop();   // 快速返回头部
                return super.onDoubleTap(e);
            }
        });

        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isToolbarFirstClick) {
                    toastShort("双击标题栏快速返回顶部");
                    isToolbarFirstClick = false;
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickToTop();
            }
        });
        loadMenuData();
    }

    private void loadMenuData() {
        View headerView = navView.getHeaderView(0);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.nav_header_image);
        TextView username = (TextView) headerView.findViewById(R.id.nav_header_name);
        TextView tagline = (TextView) headerView.findViewById(R.id.nav_header_tagline);
        username.setText("VanHua");
        tagline.setText("在线");
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("已登录",Toast.LENGTH_SHORT);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        return true;
//    }
    // 快速返回顶部
    private void quickToTop() {
        switch (mCurrentPosition) {
            case 0:
                gankFragment.quickToTop();
                break;
            case 1:
                moiveFragment.quickToTop();
                break;
            case 2:
                testFragment.quickToTop();
                break;
        }
    }






    protected void openActivity(Class<?> cls) {
        openActivity(this, cls);
    }

    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
    /**
     * 发出一个短Toast
     *
     * @param text 内容
     */
    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 发出一个长toast提醒
     *
     * @param text 内容
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }


    private void toast(final String text, final int duration) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text, duration);
                    } else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                }
            });
        }
    }
}
