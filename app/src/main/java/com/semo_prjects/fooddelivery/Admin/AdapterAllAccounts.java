package com.semo_prjects.fooddelivery.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semo_prjects.fooddelivery.Database.Accounts;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class AdapterAllAccounts extends RecyclerView.Adapter<AdapterAllAccounts.NewViewHolder> {

    private Context mContext;
    private List<Accounts> mAccountsList;
    private List<String> mKeys;
    private AdapterAllAccounts mAdapterAllAccounts;


    public AdapterAllAccounts(){

    }
    public void setConfig(RecyclerView recyclerView, Context context, List<Accounts> accounts, List<String> keys) {
        mContext = context;
        mAdapterAllAccounts =new AdapterAllAccounts(mContext,accounts,keys);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        recyclerView.setAdapter(mAdapterAllAccounts);
    }

    public AdapterAllAccounts(Context context, List<Accounts> accountsList, List<String> keys) {
        this.mContext = context;
        mAccountsList = accountsList;
        mKeys = keys;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_all_accounts_item,parent,false);
        return new AdapterAllAccounts.NewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {

        holder.bind(mAccountsList.get(position),mKeys.get(position));
        /*holder.mTextView.setText("اسم المطعم: "+ mAccountsList.get(position).getName());
        holder.mTextView2.setText("جوال المطعم: "+ mAccountsList.get(position).getNumber());
        holder.mTextView3.setText("ايميل المطعم: "+ mAccountsList.get(position).getEmail());
        holder.mTextView4.setText("باسوورد الدخول: "+ mAccountsList.get(position).getPassword());
        mKeys.get(position);*/
        /*holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Inserting data to intent
                Intent intent=new Intent(mContext,EditAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("mKey",mKeys.get(position));
                intent.putExtra("mName",mAccountsList.get(position).getName());
                intent.putExtra("mNumber",mAccountsList.get(position).getNumber());
                intent.putExtra("mEmail",mAccountsList.get(position).getEmail());
                intent.putExtra("mPassword",mAccountsList.get(position).getPassword());
                intent.putExtra("mPassword",mAccountsList.get(position).getPassword());
                mContext.startActivity(intent);


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mAccountsList.size();
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        private TextView mTextView2;
        private TextView mTextView3;
        private TextView mTextView4;
        private String name="Name: " ;
        private String number;
        private String email;
        private String password="Password: " ;

        CardView mCardView;
        private String key;

        public NewViewHolder(View itemView){
            super(itemView);
            mTextView=itemView.findViewById(R.id.restaurantNameTxtV);
            mTextView2=itemView.findViewById(R.id.customerNameTxtV);
            mTextView3=itemView.findViewById(R.id.customerNumTxtV);
            mTextView4=itemView.findViewById(R.id.locationTxtV);
            mCardView=itemView.findViewById(R.id.mainCardview);
            name=itemView.getResources().getString(R.string.name1);
            number=itemView.getResources().getString(R.string.number);
            email=itemView.getResources().getString(R.string.email);
            password=itemView.getResources().getString(R.string.password);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


        public void bind(Accounts accounts, String key){
            mTextView.setText(itemView.getResources().getString(R.string.name1,accounts.getName()));
            mTextView2.setText(itemView.getResources().getString(R.string.number,accounts.getNumber()));
            mTextView3.setText(itemView.getResources().getString(R.string.email,accounts.getEmail()));
            mTextView4.setText(itemView.getResources().getString(R.string.password,accounts.getPassword()));
            this.key=key;
        }
    }
}
