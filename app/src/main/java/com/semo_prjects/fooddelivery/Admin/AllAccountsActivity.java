package com.semo_prjects.fooddelivery.Admin;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.semo_prjects.fooddelivery.Database.Accounts;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class AllAccountsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_accounts);
        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView=findViewById(R.id.recyclerView);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.manageaccounts));

        new FirebaseDatabaseHelper().showAccounts(new FirebaseDatabaseHelper.AccountsDataStatus() {
            @Override
            public void DataIsLoaded(List<Accounts> accounts, List<String> keys) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                new AdapterAllAccounts().setConfig(mRecyclerView,AllAccountsActivity.this,accounts,keys);
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


