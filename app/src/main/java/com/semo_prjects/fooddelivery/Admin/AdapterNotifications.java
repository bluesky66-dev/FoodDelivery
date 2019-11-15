package com.semo_prjects.fooddelivery.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.NewViewHolder> {

    private Context mContext;
    private List<Order> mOrderList;
    private List<String> mKeys;
    private AdapterNotifications mAdapterNotifications;
    private String mID;





    public AdapterNotifications(){

    }
    public void setConfig(RecyclerView recyclerView,Context context,List<Order> orders,List<String> keys) {
        mContext = context;
        mAdapterNotifications =new AdapterNotifications(mContext,orders,keys);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        recyclerView.setAdapter(mAdapterNotifications);

    }

    public AdapterNotifications(Context context, List<Order> orderList, List<String> keys) {
        this.mContext = context;
        mOrderList = orderList;
        mKeys = keys;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_notifications_item,parent,false);
        return new AdapterNotifications.NewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {

        holder.bind(mOrderList.get(position),mKeys.get(position));
        /*final String S1=holder.mRestaurantNameTxtV.getText().toString();
        final String S2=holder.mCustomerNameTxtV.getText().toString();
        final String S3=holder.mCustomerNumTxtV.getText().toString();
        final String S4=holder.mLocationTxtV.getText().toString();
        final String S5=holder.mPriceTxtV.getText().toString();*/




        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Order order=new Order(mOrderList.get(position).getName(),mOrderList.get(position).getNumber()
                        ,mOrderList.get(position).getRestaurantName(),mOrderList.get(position).getLocation(),
                        mOrderList.get(position).getmPrice());*/

                StringBuffer mBuffer=new StringBuffer();
                mBuffer.append("Restaurant Name: "+mOrderList.get(position).getRestaurantName()+"\n");
                mBuffer.append("Customer Name: "+mOrderList.get(position).getName()+"\n");
                mBuffer.append("Customer Number: "+mOrderList.get(position).getNumber()+"\n");
                mBuffer.append("Order Cost: "+mOrderList.get(position).getPrice()+" SR\n\n");
                mBuffer.append("Customer Location: "+mOrderList.get(position).getLocation());


                final SpannableString s = new SpannableString(mBuffer);

                Linkify.addLinks(s,Linkify.ALL);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setPositiveButton("Choose The Driver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(mContext,DriversAccountsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("orderID",mKeys.get(position));
                        mContext.startActivity(intent);

                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.setTitle("Order Information");
                TextView textView = new TextView(mContext);
                textView.setPadding(50,20,50,20);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(s);
                builder.setView(textView);
                builder.show();







               /*ClipboardManager clipboard=(ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);

                StringBuilder textMessage = new StringBuilder();
                textMessage.append(S1+"\n");
                textMessage.append(S2+"\n");
                textMessage.append(S3+"\n\n");
                textMessage.append(S4);

                ClipData clip = ClipData.newPlainText("simple text", textMessage.toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(v.getContext(),"Copied Successfully",Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }




    public static class NewViewHolder extends RecyclerView.ViewHolder{
        private TextView mRestaurantNameTxtV;
        private TextView mCustomerNameTxtV;
        //private TextView mCustomerNumTxtV;
        //private TextView mPriceTxtV;
        private CardView mCardView;
        private String key;
        private TextView mLocation;
        private TextView mWaitingAcceptance;



        public NewViewHolder(View itemView){
            super(itemView);
            mRestaurantNameTxtV =itemView.findViewById(R.id.restaurantNameTxtV);
            mCustomerNameTxtV =itemView.findViewById(R.id.customerNameTxtV);
            //mCustomerNumTxtV =itemView.findViewById(R.id.customerNumTxtV);
            //mPriceTxtV =itemView.findViewById(R.id.priceTxtV);
            mCardView=itemView.findViewById(R.id.mainCardview);
            mLocation=itemView.findViewById(R.id.location);
            mWaitingAcceptance =itemView.findViewById(R.id.waitingAcceptance);


        }

        public void bind(Order order,String key){
            mRestaurantNameTxtV.setText(order.getRestaurantName());
            mCustomerNameTxtV.setText(itemView.getResources().getString(R.string.SR,
                    order.getName(),order.getNumber(),order.getPrice()));
            //mCustomerNumTxtV.setText("رقم العميل: "+order.getNumber());
            //mPriceTxtV.setText("سعر الطلب: "+order.getmPrice());
            mLocation.setText(order.getLocation());
            if(!order.getDriverUID().equals("Pending")){
                new FirebaseDatabaseHelper().extractName(order.getDriverUID(),
                        new FirebaseDatabaseHelper.ExtractSingleValue() {
                            @Override
                            public void ValueIsExtracted(String value) {
                                mWaitingAcceptance.setText(itemView.getResources().getString(R.string.waitingforcaptain,value));
                            }
                        });

                mLocation.setVisibility(View.INVISIBLE);
                mWaitingAcceptance.setVisibility(View.VISIBLE);
            }else if(!order.getCancellationReason().equals("None")){
                mLocation.setVisibility(View.INVISIBLE);
                mWaitingAcceptance.setVisibility(View.VISIBLE);
                mWaitingAcceptance.setText(order.getCancellationReason());
            }

            this.key=key;
        }


    }
}
