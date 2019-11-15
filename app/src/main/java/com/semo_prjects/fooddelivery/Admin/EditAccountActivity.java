package com.semo_prjects.fooddelivery.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.semo_prjects.fooddelivery.Database.Accounts;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class EditAccountActivity extends AppCompatActivity {

    private EditText mEmailText;
    private EditText mPasswordText;
    private EditText mConfirmPasswordText;
    private EditText mNameText;
    private EditText mphoneText;
    private Button mUpdateButton;
    private Button mDeleteButton;
    private ProgressBar mProgressBar;
    private String mKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        //Initializing the variables
        mEmailText =  findViewById(R.id.emailText);
        mPasswordText = findViewById(R.id.passwordText);
        mConfirmPasswordText =  findViewById(R.id.confirmPasswordText);
        mNameText =  findViewById(R.id.nameText);
        mphoneText =  findViewById(R.id.phoneText);
        mUpdateButton = findViewById(R.id.updateButton);
        mDeleteButton = findViewById(R.id.deleteButton);
        mProgressBar = findViewById(R.id.signupProgress);

        Intent intent=getIntent();

        mNameText.setText(intent.getExtras().getString("mName"));
        mphoneText.setText(intent.getExtras().getString("mNumber"));
        mEmailText.setText(intent.getExtras().getString("mEmail"));
        mPasswordText.setText(intent.getExtras().getString("mPassword"));
        mConfirmPasswordText.setText(intent.getExtras().getString("mPassword"));
        mKey=intent.getExtras().getString("mKey");



        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPasswordText.getText().toString().equals(mConfirmPasswordText.getText().toString())) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Accounts accounts = new Accounts();
                    accounts.setName(mNameText.getText().toString());
                    accounts.setEmail(mEmailText.getText().toString());
                    accounts.setNumber(mphoneText.getText().toString());
                    accounts.setPassword(mPasswordText.getText().toString());
                    new FirebaseDatabaseHelper().updateAccount(accounts, mKey, new FirebaseDatabaseHelper.AccountsDataStatus() {
                        @Override
                        public void DataIsLoaded(List<Accounts> accounts, List<String> keys) {

                        }

                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }

                        @Override
                        public void DataIsUpdated() {

                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.dataisupdated),Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.INVISIBLE);

                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.passwordandconfirmp),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
