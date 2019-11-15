package com.semo_prjects.fooddelivery.Restaurant;

import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class RestaurantOrderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;
    private Window mWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        mAuth=FirebaseAuth.getInstance();
        mRecyclerView=findViewById(R.id.recyclerView);
        mWindow=getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWindow.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.orders_background));
            mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }




        new FirebaseDatabaseHelper().showRestaurantOrders(new FirebaseDatabaseHelper.OrdersDataStatus() {
            @Override
            public void DataIsLoaded(List<Order> orders, List<String> keys) {
                findViewById(R.id.progressBar2).setVisibility(View.GONE);
                new AdapterRestaurantOrders().setMyOrders(mRecyclerView,RestaurantOrderActivity.this,orders,keys);
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



