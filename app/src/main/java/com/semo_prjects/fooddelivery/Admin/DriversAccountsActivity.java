package com.semo_prjects.fooddelivery.Admin;

import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.semo_prjects.fooddelivery.Database.Accounts;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class DriversAccountsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private String orderID;
    private Window mWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_accounts);
        mRecyclerView=findViewById(R.id.recyclerView);
        mWindow=getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWindow.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.orders_background));
            mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }



        //Extracting data from intent
        Intent intent=getIntent();
        try{
            orderID=intent.getExtras().getString("orderID");

        }catch (Exception e){

        }
            new FirebaseDatabaseHelper().showDriversAccounts(new FirebaseDatabaseHelper.AccountsDataStatus() {
                @Override
                public void DataIsLoaded(List<Accounts> accounts, List<String> keys) {
                    findViewById(R.id.progressBar4).setVisibility(View.GONE);
                    new AdapterDriversAccounts().setDriversAccounts(mRecyclerView, DriversAccountsActivity.this, accounts, keys,orderID);
                }

                @Override
                public void DataIsInserted() {

                }

                @Override
                public void DataIsDeleted() {

                }

                @Override
                public void DataIsUpdated() {

                }
            });



    }


}
