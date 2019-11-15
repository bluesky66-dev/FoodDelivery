package com.semo_prjects.fooddelivery.Admin;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.semo_prjects.fooddelivery.Database.Accounts;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

import static android.media.CamcorderProfile.get;

public class SignupActivity extends AppCompatActivity {
    //Declaring Variables
    String[] array=getResources().getStringArray(R.array.account_type_array);
    private Spinner mAccountTypeSpinner;
    private EditText mEmailText;
    private EditText mPasswordText;
    private EditText mConfirmPasswordText;
    private EditText mNameText;
    private EditText mphoneText;
    private Button mSigniupButton;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser=FirebaseAuth.getInstance().getCurrentUser();
    private String mAccountType;
    private String mStatus;
    String adminEmail=mUser.getEmail();
    String adminPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Initializing the variables
        mAccountTypeSpinner =  findViewById(R.id.accountTypeSpinner);
        mEmailText =  findViewById(R.id.emailText);
        mPasswordText = findViewById(R.id.passwordText);
        mConfirmPasswordText =  findViewById(R.id.confirmPasswordText);
        mNameText =  findViewById(R.id.nameText);
        mphoneText =  findViewById(R.id.phoneText);
        mStatus="Off Work";
        mSigniupButton = findViewById(R.id.signupButton);
        mProgressBar = findViewById(R.id.signupProgress);
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mProgressBar.getIndeterminateDrawable().setColorFilter(0xFF9FAF00, PorterDuff.Mode.SRC_IN);
        mProgressBar.getProgressDrawable().setColorFilter(0xFF9FAF00, PorterDuff.Mode.SRC_IN);
        new FirebaseDatabaseHelper().extractSingleValue("password", new FirebaseDatabaseHelper.ExtractSingleValue() {
            @Override
            public void ValueIsExtracted(String value) {
                adminPassword=value;
            }
        });




// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 android.R.layout.simple_spinner_item,array);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mAccountTypeSpinner.setAdapter(adapter);


        mAccountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mAccountType=array[position];
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                mAccountTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.choes_account_type) , Toast.LENGTH_LONG).show();

            }
        });


        //Activating the Signup button
        mSigniupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extracting the pure text
                /*final String email = mEmailText.getText().toString();
                final String password = mPasswordText.getText().toString();
                String confirmPassword = mConfirmPasswordText.getText().toString();
                final String name = mNameText.getText().toString();
                final String number = mphoneText.getText().toString();*/
                final String email = mEmailText.getText().toString();
                final String password = mPasswordText.getText().toString();
                String confirmPassword = mConfirmPasswordText.getText().toString();
                final String name = mNameText.getText().toString();
                final String number = mphoneText.getText().toString();
                //checking if the extracted email and password are not empty
                // then make the progress bar visible
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) & !TextUtils.isEmpty(confirmPassword)
                        & !TextUtils.isEmpty(name)& !TextUtils.isEmpty(number)& !mAccountType.equals("Choose Account Type")) {
                    if (password.equals(confirmPassword)) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        //creating email and password data in firebase then checking success
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                        Accounts accounts;

                                        accounts= new Accounts(name, number, email, password,mAccountType,mStatus);


                                        /*mReference.child(mUser.getUid()).setValue(accounts);
                                        Toast.makeText(getApplicationContext(), "Restaurant Account Registered Successfully", Toast.LENGTH_SHORT).show();*/
                                        new FirebaseDatabaseHelper().addNewAccount(accounts, mUser.getUid(), new FirebaseDatabaseHelper.AccountsDataStatus() {
                                            @Override
                                            public void DataIsLoaded(List<Accounts> accounts, List<String> keys) {

                                            }

                                            @Override
                                            public void DataIsInserted() {
                                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.account_rigistered_successfully,mAccountType), Toast.LENGTH_SHORT).show();
                                                mAuth.signOut();
                                                mAuth.signInWithEmailAndPassword(adminEmail, adminPassword);
                                                //invisible the progress bar
                                                mProgressBar.setVisibility(View.INVISIBLE);
                                                finish();


                                            }

                                            @Override
                                            public void DataIsDeleted() {

                                            }

                                            @Override
                                            public void DataIsUpdated() {

                                            }
                                        });




                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(SignupActivity.this, "Error is: " + error, Toast.LENGTH_LONG).show();

                                }


                            }
                        });


                    } else {
                        Toast.makeText(SignupActivity.this, getResources().getString(R.string.passwordandconfirmp), Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.complete_all_boxes), Toast.LENGTH_LONG).show();
                }

            }
        });


    }


}