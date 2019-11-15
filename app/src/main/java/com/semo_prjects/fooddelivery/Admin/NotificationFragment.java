package com.semo_prjects.fooddelivery.Admin;


import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.util.List;


public class NotificationFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    private Window mWindow;



    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        mAuth=FirebaseAuth.getInstance();
        mRecyclerView=view.findViewById(R.id.recyclerView);
        mProgressBar=view.findViewById(R.id.progressBar2);
        mWindow=getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWindow.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.orders_background));
            mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        new FirebaseDatabaseHelper().checkDriverAccepted(false,new FirebaseDatabaseHelper.OrdersDataStatus() {
            @Override
            public void DataIsLoaded(List<Order> orders, List<String> keys) {

                new AdapterNotifications().setConfig(mRecyclerView,getContext(),orders,keys);
                mProgressBar.setVisibility(View.INVISIBLE);
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



        return view;

    }

}
