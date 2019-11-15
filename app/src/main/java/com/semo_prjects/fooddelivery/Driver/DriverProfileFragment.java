package com.semo_prjects.fooddelivery.Driver;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.LoginActivity;
import com.semo_prjects.fooddelivery.R;


public class DriverProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Window mWindow;
    private ImageView mLogoutImgV;
    private RelativeLayout mNumberOfOrdersRltvL;
    private RelativeLayout mTotalDeleviryRevenueRltvL;
    private RelativeLayout mStatisticsRltvL;
    private RelativeLayout mSettingsRltvL;
    private ImageView mProfileImgV;
    private Button mStatusImgV;
    private TextView mDriverNameTxtV;



    public DriverProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_profile, container, false);
        mAuth=FirebaseAuth.getInstance();
        mLogoutImgV=view.findViewById(R.id.logoutImgV);
        mNumberOfOrdersRltvL =view.findViewById(R.id.numberOfOrdersRltvL);
        mTotalDeleviryRevenueRltvL =view.findViewById(R.id.totalDeleviryRevenueRltvL);
        mStatisticsRltvL=view.findViewById(R.id.statisticsRltvL);
        mSettingsRltvL=view.findViewById(R.id.settingsRltvL);
        mProfileImgV =view.findViewById(R.id.profileImgV);
        mStatusImgV=view.findViewById(R.id.statusButton);
        mDriverNameTxtV =view.findViewById(R.id.driverNameTxtV);

        new FirebaseDatabaseHelper().extractSingleValue("name", new FirebaseDatabaseHelper.ExtractSingleValue() {
            @Override
            public void ValueIsExtracted(String value) {
                mDriverNameTxtV.setText(value);

            }
        });
        new FirebaseDatabaseHelper().extractSingleValue("status", new FirebaseDatabaseHelper.ExtractSingleValue() {
            @Override
            public void ValueIsExtracted(String value) {
                if(value.equals("Available")) {
                    mStatusImgV.setBackgroundResource(R.drawable.ic_available);
                }else if(value.equals("Busy")) {
                    mStatusImgV.setBackgroundResource(R.drawable.ic_busy);
                }else if(value.equals("Off Work")) {
                    mStatusImgV.setBackgroundResource(R.drawable.ic_off_work);
                }

            }
        });

        mWindow=getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWindow.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.navigation_background));
            mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mStatusImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(),mStatusImgV);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_statuse, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.available:
                                mStatusImgV.setBackgroundResource(R.drawable.ic_available);
                                new FirebaseDatabaseHelper().changeAccountStatus("Available");
                                return true;

                            case R.id.busy:
                                mStatusImgV.setBackgroundResource(R.drawable.ic_busy);
                                new FirebaseDatabaseHelper().changeAccountStatus("Busy");
                                return true;

                            case R.id.offWork:
                                mStatusImgV.setBackgroundResource(R.drawable.ic_off_work);
                                new FirebaseDatabaseHelper().changeAccountStatus("Off Work");
                                return true;
                            default:
                                return false;

                        }

                    }
                });

                popup.show(); //showing popup menu
            }
        });


        mLogoutImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog(getResources().getString(R.string.logout),getResources().getString(R.string.check_logout));
            }
        });

        mNumberOfOrdersRltvL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mTotalDeleviryRevenueRltvL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mStatisticsRltvL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mSettingsRltvL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        return view;

    }

    public void logoutDialog(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no),null);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_log_out_smaller);
        builder.show();
    }

    //method to signout
    private void logout() {
        mAuth.signOut();
        sendToLogin();

    }
    //method to send user to the log in page
    private void sendToLogin() {
        Intent loginIntent = new Intent(getContext(), LoginActivity.class);
        startActivity(loginIntent);
        getActivity().finish();
    }

}
