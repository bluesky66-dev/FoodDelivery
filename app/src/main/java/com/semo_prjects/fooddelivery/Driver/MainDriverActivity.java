package com.semo_prjects.fooddelivery.Driver;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.semo_prjects.fooddelivery.R;

public class MainDriverActivity extends AppCompatActivity {

    private DriverProfileFragment mDriverProfileFragment;
    private DriverOrdersFragment mDriverOrdersFragment;
    private DriverNotificationFragment mDriverNotificationFragment;
    private BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_notification:
                    setFragment(mDriverNotificationFragment);
                    return true;
                case R.id.navigation_orders:
                    setFragment(mDriverOrdersFragment);
                    return true;
                case R.id.navigation_profile:
                    setFragment(mDriverProfileFragment);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);
        mBottomNavigationView = findViewById(R.id.mainNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mDriverProfileFragment = new DriverProfileFragment();
        mDriverOrdersFragment = new DriverOrdersFragment();
        mDriverNotificationFragment = new DriverNotificationFragment();
        setFragment(mDriverNotificationFragment);

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
}