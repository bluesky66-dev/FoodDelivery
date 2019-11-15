package com.semo_prjects.fooddelivery.Admin;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.semo_prjects.fooddelivery.Database.Accounts;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class AdapterDriversAccounts extends RecyclerView.Adapter<AdapterDriversAccounts.NewViewHolder> {

    private Context mContext;
    private List<Accounts> mAccountsList;
    private List<String> mKeys;
    private List<Order> mOrderList;
    private AdapterDriversAccounts mAdapterDriversAccounts;
    private String orderID;




    public AdapterDriversAccounts(){

    }

    public void setDriversAccounts(RecyclerView recyclerView, Context context, List<Accounts> accounts, List<String> keys,
                                   String orderID) {
        mContext = context;
        this.orderID =orderID;
        mAdapterDriversAccounts =new AdapterDriversAccounts(mContext,accounts,keys,orderID);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        recyclerView.setAdapter(mAdapterDriversAccounts);
    }


    public AdapterDriversAccounts(Context context, List<Accounts> accountsList, List<String> keys,String orderID) {
        this.mContext = context;
        mAccountsList = accountsList;
        mKeys = keys;
        this.orderID =orderID;



    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_drivers_accounts_item,parent,false);
        return new AdapterDriversAccounts.NewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {
        holder.bind(mAccountsList.get(position),mKeys.get(position));
        if(mAccountsList.get(position).getStatus().equals("Busy")) {
            holder.mDriverStatusImgV.setImageResource(R.drawable.ic_busy);
        }

        try {
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        FirebaseDatabase.getInstance().getReference("Orders")
                                .child(orderID).child("driverUID").setValue(mKeys.get(position));
                        Toast.makeText(mContext,"Transferred to Driver "+
                                mAccountsList.get(position).getName()+"successfully",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return mAccountsList.size();
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;

        CardView mCardView;
        private String key;
        private TextView mNumberOfOrdersTxtV;
        private ImageView mDriverStatusImgV;

        public NewViewHolder(View itemView){
            super(itemView);
            mTextView=itemView.findViewById(R.id.restaurantNameTxtV);
            mCardView=itemView.findViewById(R.id.mainCardview);
            mNumberOfOrdersTxtV=itemView.findViewById(R.id.numberOfOrdersTxtV);
            mDriverStatusImgV=itemView.findViewById(R.id.driverStatus);
        }

        public void bind(Accounts accounts, String key){
            mTextView.setText(itemView.getResources().getString(R.string.captain1,accounts.getName()));
            this.key=key;
        }
    }
}
