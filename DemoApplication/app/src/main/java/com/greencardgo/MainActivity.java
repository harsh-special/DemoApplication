package com.greencardgo;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.Fragment.AboutUsFragment;
import com.Fragment.DisclaimerFragment;
import com.Fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpToolber();
        initNavigationDrawer();

        fragmentTransaction(HomeFragment.newInstance(MainActivity.this));
        fragment = HomeFragment.newInstance(MainActivity.this);
        drawerLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }


    private void setUpToolber() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
//                    setTitlebarText(getResources().getString(R.string.menu));
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
//                    setTitlebarText(mTitleText);
                }
            };
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {

                    case R.id.home:
//                        Toast.makeText(getApplicationContext(), "HomeFragment", Toast.LENGTH_SHORT).show();
                        fragmentTransaction(HomeFragment.newInstance(MainActivity.this));
                        fragment = DisclaimerFragment.newInstance(MainActivity.this);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.disclaimer:
//                        Toast.makeText(getApplicationContext(), "HomeFragment", Toast.LENGTH_SHORT).show();
                        fragmentTransaction(DisclaimerFragment.newInstance(MainActivity.this));
                        fragment = DisclaimerFragment.newInstance(MainActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.about_us:
//                        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                        fragmentTransaction(AboutUsFragment.newInstance(MainActivity.this));
                        fragment = AboutUsFragment.newInstance(MainActivity.this);
                        drawerLayout.closeDrawers();
                        break;

                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            // do something here
            fragmentTransaction(HomeFragment.newInstance(MainActivity.this));
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setTitlebarText(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fragmentTransaction(Fragment fragment) {
        try {
//            Share.selectedFragment = fragment;
            if (fragment != null) {
                String mTitleText = fragment.getArguments().getString("mTitleText");
                setTitlebarText(mTitleText);
                getSupportFragmentManager()
                        .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();

    }
}
