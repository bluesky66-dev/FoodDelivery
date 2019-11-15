package com.semo_prjects.fooddelivery.Admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.semo_prjects.fooddelivery.R;

public class MainAdminActivity extends AppCompatActivity {
    private AdminProfileFragment mAdminProfileFragment;
    private AllOrdersFragment mAllOrdersFragment;
    private NotificationFragment mNotificationFragment;
    private BottomNavigationView mBottomNavigationView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_notification:
                    setFragment(mNotificationFragment);
                    return true;
                case R.id.navigation_orders:
                    setFragment(mAllOrdersFragment);
                    return true;
                case R.id.navigation_profile:
                    setFragment(mAdminProfileFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        mBottomNavigationView= findViewById(R.id.mainNavigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mAdminProfileFragment =new AdminProfileFragment();
        mAllOrdersFragment=new AllOrdersFragment();
        mNotificationFragment=new NotificationFragment();
        setFragment(mNotificationFragment);

    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame,fragment);
        fragmentTransaction.commit();
    }


}
