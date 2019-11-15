package com.semo_prjects.fooddelivery;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semo_prjects.fooddelivery.Admin.MainAdminActivity;
import com.semo_prjects.fooddelivery.Driver.MainDriverActivity;
import com.semo_prjects.fooddelivery.Restaurant.MainRestaurantActivity;

public class MainFlashyActivity extends AppCompatActivity {

    FirebaseUser mUser=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase mDatabase;
    private ProgressBar mProgress;


    @Override
    protected void onStart() {
        super.onStart();
        if(mUser==null){
            //Intent
            sendToLogin();
        }else{
            FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();
            mDatabase= FirebaseDatabase.getInstance();
            mDatabase.getReference("Accounts").child(mUser.getUid()).
                    child("accountType").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String accountType=dataSnapshot.getValue(String.class);
                    if(accountType.equals("Driver")){
                        sendToDriverActivity();
                    }else if(accountType.equals("Admin")){
                        sendToAdmin();
                    }else if(accountType.equals("Restaurant")){
                        sendToMain();
                    }else {
                        Toast.makeText(getApplicationContext(), "Nothing found", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("test", "Failed to read value.", databaseError.toException());

                }

            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flashy);

    }

    //method to send user to the log in page
    private void sendToLogin() {
        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    //to send user to the Admin Page
    private void sendToAdmin() {
        Intent adminIntent = new Intent(getApplicationContext(), MainAdminActivity.class);
        startActivity(adminIntent);
        finish();

    }
    //to send user to the Driver Page
    private void sendToDriverActivity() {
        Intent adminIntent = new Intent(getApplicationContext(), MainDriverActivity.class);
        startActivity(adminIntent);
        finish();

    }
    //to send user to the Main Page
    private void sendToMain() {
        Intent mainIntent = new Intent(getApplicationContext(), MainRestaurantActivity.class);
        startActivity(mainIntent);
        finish();
    }


}
