package com.semo_prjects.fooddelivery.Restaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semo_prjects.fooddelivery.Admin.MainAdminActivity;
import com.semo_prjects.fooddelivery.Driver.MainDriverActivity;
import com.semo_prjects.fooddelivery.LoginActivity;
import com.semo_prjects.fooddelivery.R;

public class MainRestaurantActivity extends AppCompatActivity {
    private FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth;
    private Button mNewOrderButton;
    private Button mMyOrdersButton;
    private Toolbar mToolbar;
    private String name;
    private FirebaseDatabase mDatabase;




    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    //Checking the user state whenever app starts and send it to login page if he isn't logged in


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_restaurant);
        mAuth=FirebaseAuth.getInstance();
        mNewOrderButton =findViewById(R.id.newOrderButton);
        mMyOrdersButton =findViewById(R.id.myOrdersButton);
        mToolbar=findViewById(R.id.toolbar);
        mDatabase=FirebaseDatabase.getInstance();




        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBarWithRestaurantName();


        mNewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NewOrderActivity.class);
                startActivity(intent);

            }
        });
        mMyOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RestaurantOrderActivity.class);
                startActivity(intent);

            }
        });

    }

    // Connecting the main menu to Main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant_page,menu);
        return true;
    }
    //actions happens when Items of the menu are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logoutDialog(getResources().getString(R.string.logout),getResources().getString(R.string.check_logout));
                return true;

            default:
                return false;
        }
    }

    //method to send user to the log in page
    private void sendToLogin() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
    //method to Logout
    private void logout() {
        mAuth.signOut();
        sendToLogin();
    }
    //to send user to the Admin Page
    private void sendToAdmin() {
        Intent adminIntent = new Intent(getApplicationContext(), MainAdminActivity.class);
        startActivity(adminIntent);
    }
    //to send user to the Driver Page
    private void sendToDriverActivity() {
        Intent adminIntent = new Intent(getApplicationContext(), MainDriverActivity.class);
        startActivity(adminIntent);
    }
    public void logoutDialog(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
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




    private void getSupportActionBarWithRestaurantName() {
        if (currentUser != null) {

            mDatabase.getReference("Accounts").child(currentUser.getUid()).
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            name = dataSnapshot.child("name").getValue(String.class);
                            getSupportActionBar().setTitle(getString(R.string.restarant,name));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

    }
}
