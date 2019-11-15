package com.semo_prjects.fooddelivery;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semo_prjects.fooddelivery.Admin.MainAdminActivity;
import com.semo_prjects.fooddelivery.Driver.MainDriverActivity;
import com.semo_prjects.fooddelivery.Restaurant.MainRestaurantActivity;

public class LoginActivity extends AppCompatActivity {

    //Declaring Variables
    private EditText mEmailText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private ProgressBar mLoginProgress;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing the variables
        mEmailText = (EditText) findViewById(R.id.emailText);
        mPasswordText = (EditText) findViewById(R.id.passwordText);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginProgress = (ProgressBar) findViewById(R.id.loginProgress);
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();

        mLoginProgress.getIndeterminateDrawable().setColorFilter(0xFF9FAF00, PorterDuff.Mode.SRC_IN);
        mLoginProgress.getProgressDrawable().setColorFilter(0xFF9FAF00, PorterDuff.Mode.SRC_IN);


        //tst

        //Activating the Login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extracting the pure text
                final String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();
                //checking if the extracted email and password are not empty
                // then make the progress bar visible
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mLoginProgress.setVisibility(View.VISIBLE);
                    //comparing the inputs with the data in firebase then checking correctness
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                mUser=FirebaseAuth.getInstance().getCurrentUser();
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
                                        mLoginProgress.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.w("test", "Failed to read value.", databaseError.toException());
                                        Toast.makeText(LoginActivity.this, "Error: " + databaseError.toException(), Toast.LENGTH_LONG).show();
                                        mLoginProgress.setVisibility(View.INVISIBLE);

                                    }

                                });


                            } else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                                mLoginProgress.setVisibility(View.INVISIBLE);
                            }




                        }
                    });
                }
            }
        });
    }


    //to send user to the Main Page
    private void sendToMain() {
        Intent mainIntent = new Intent(LoginActivity.this, MainRestaurantActivity.class);
        startActivity(mainIntent);
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
}

//keytool -exportcert -alias testt -keystore D:\tmp\testKeyStore\MyButton.keystore.jks -list -v
        //keytool -list -v -keystore ~/Users/osamah_71/Desktop/testtt.keystore.jks -alias testtt -storepass 123456 -keypass 123456






