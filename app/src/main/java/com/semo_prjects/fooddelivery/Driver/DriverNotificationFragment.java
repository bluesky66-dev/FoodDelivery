package com.semo_prjects.fooddelivery.Driver;


import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import com.google.firebase.database.ServerValue;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DriverNotificationFragment extends Fragment {
    private Window mWindow;
    private TextView mTextView;



    public DriverNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_notification, container, false);
        mWindow=getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWindow.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.orders_background));
            mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        mTextView=view.findViewById(R.id.date);



        new FirebaseDatabaseHelper().addValueTest(ServerValue.TIMESTAMP);

        new FirebaseDatabaseHelper().extractSingleValueTest("Test", new FirebaseDatabaseHelper.ExtractSingleValueTest() {
            @Override
            public void ValueIsExtractedTest(long value) {
                SimpleDateFormat sfd = new SimpleDateFormat("ddMMyy");
                mTextView.setText(sfd.format(new Date(value)));
            }
        });

        return view;

    }

}
