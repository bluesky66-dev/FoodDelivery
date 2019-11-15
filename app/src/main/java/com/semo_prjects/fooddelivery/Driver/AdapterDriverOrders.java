package com.semo_prjects.fooddelivery.Driver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class AdapterDriverOrders extends RecyclerView.Adapter<AdapterDriverOrders.NewViewHolder> {

    private Context mContext;
    private List<Order> mOrderList;
    private List<String> mKeys;
    private AdapterDriverOrders mAdapterAllOrders;
    private String mID;


    public AdapterDriverOrders(){

    }
    public void setDriverOrders(RecyclerView recyclerView, Context context, List<Order> orders, List<String> keys) {
        mContext = context;
        mAdapterAllOrders =new AdapterDriverOrders(mContext,orders,keys);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        recyclerView.setAdapter(mAdapterAllOrders);
    }

    public AdapterDriverOrders(Context context, List<Order> orderList, List<String> keys) {
        this.mContext = context;
        mOrderList = orderList;
        mKeys = keys;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_driver_orders_item,parent,false);
        return new AdapterDriverOrders.NewViewHolder(view);
    }






    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {
        
        holder.bind(mOrderList.get(position),mKeys.get(position));
        holder.mOrderStatusRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mToRestaurantTxtV.getText().toString().equals("Customer")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                    builder.setPositiveButton(mContext.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            
                        }
                    });
                    builder.setNegativeButton(mContext.getResources().getString(R.string.no),null);
                    builder.setTitle(mContext.getResources().getString(R.string.order_delivered));
                    builder.setMessage(mContext.getResources().getString(R.string.did_you_deliver_the_order));
                    //builder.setIcon(R.drawable.ic_log_out_smaller);
                    builder.show();

                }else {
                    new FirebaseDatabaseHelper().changeOrderStatus(holder.mToRestaurantTxtV.getText().toString(),mKeys.get(position));

                }
            }
        });
        holder.mViewOrdder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer mBuffer=new StringBuffer();
                mBuffer.append(R.string.restaurant_name);
                mBuffer.append(mOrderList.get(position).getRestaurantName());
                mBuffer.append("\n");
                mBuffer.append(R.string.customer_name);
                mBuffer.append(mOrderList.get(position).getName());
                mBuffer.append("\n");
                mBuffer.append(R.string.customer_number);
                mBuffer.append(mOrderList.get(position).getNumber());
                mBuffer.append("\n");
                mBuffer.append(R.string.order_cost);
                mBuffer.append(mOrderList.get(position).getPrice());
                mBuffer.append("\n");
                mBuffer.append(R.string.customer_location);
                mBuffer.append(mOrderList.get(position).getLocation());
                mBuffer.append("\n");

                final SpannableString s = new SpannableString(mBuffer);

                Linkify.addLinks(s,Linkify.ALL);
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setNegativeButton(mContext.getResources().getString(R.string.cancel),null);
                builder.setTitle(mContext.getResources().getString(R.string.order_info));
                TextView textView = new TextView(mContext);
                textView.setPadding(50,20,50,20);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(s);
                builder.setView(textView);
                builder.show();

            }
        });
        holder.mPhoneImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = mOrderList.get(position).getNumber();
                    Uri uri = Uri.parse(s);
                    Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
                    mContext.startActivity(callIntent);
                }catch (Exception e){
                    Toast.makeText(mContext,"Code not completed yet",Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.mMabImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=mOrderList.get(position).getLocation();
                String g=s.substring(s.indexOf("http"));
                Uri uri = Uri.parse(g);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);

            }
        });
        holder.mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().changeDriverAccepted(mKeys.get(position),true);
            }
        });
        holder.mRefuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mAcceptanceLL.setVisibility(View.INVISIBLE);
                holder.mCancelLL.setVisibility(View.VISIBLE);
            }
        });

        holder.mDeleteImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mOrderLL.setVisibility(View.INVISIBLE);
                holder.mCancelLL.setVisibility(View.VISIBLE);
            }
        });

        holder.mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(holder.mCancllationReasonET.getText().toString().trim())){
                    new FirebaseDatabaseHelper().changeStringValue(mKeys.get(position),"driverUID","Pending");
                    new FirebaseDatabaseHelper().changeDriverAccepted(mKeys.get(position),false);
                    new FirebaseDatabaseHelper().changeStringValue(mKeys.get(position),"orderStatus","Pending");
                    new FirebaseDatabaseHelper().changeStringValue(mKeys.get(position),"cancellationReason",holder.mCancllationReasonET.getText().toString());
                }else {
                    Toast.makeText(mContext,mContext.getResources().getString(R.string.clarify_cancellation_reason_please),Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mCancelLL.setVisibility(View.INVISIBLE);
                holder.mOrderLL.setVisibility(View.VISIBLE);
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
        private ImageView mToRestaurantImgV,mViewOrdder,mMabImgV,mPhoneImgV, mDeleteImgV;
        private TextView mToRestaurantTxtV;
        private Button mAcceptButton, mRefuseButton, mSendButton, mCancelButton;
        private TextView mNewOrderTxtV;
        private EditText mCancllationReasonET;
        private LinearLayout mCancelLL,mAcceptanceLL,mOrderLL;
        private RelativeLayout mOrderStatusRL;
        private String pureLocation;



        public NewViewHolder(View itemView){
            super(itemView);
            mRestaurantNameTxtV =itemView.findViewById(R.id.restaurantNameTxtV);
            mCustomerNameTxtV =itemView.findViewById(R.id.customerNameTxtV);
            //mCustomerNumTxtV =itemView.findViewById(R.id.customerNumTxtV);
            //mPriceTxtV =itemView.findViewById(R.id.priceTxtV);
            mCardView=itemView.findViewById(R.id.mainCardview);
            mToRestaurantImgV=itemView.findViewById(R.id.toRestaurantImgV);
            mToRestaurantTxtV=itemView.findViewById(R.id.toRestaurantTxtV);
            mMabImgV=itemView.findViewById(R.id.mapImgV);
            mPhoneImgV=itemView.findViewById(R.id.phoneImgV);
            mDeleteImgV =itemView.findViewById(R.id.deleteImgV);
            mAcceptButton =itemView.findViewById(R.id.acceptButton);
            mRefuseButton =itemView.findViewById(R.id.refuseButton);
            mSendButton =itemView.findViewById(R.id.sendButton);
            mCancelButton =itemView.findViewById(R.id.cancelButton);
            mNewOrderTxtV=itemView.findViewById(R.id.newOrderTxtV);
            mCancllationReasonET=itemView.findViewById(R.id.cancelReasonET);
            mCancelLL=itemView.findViewById(R.id.cancelLL);
            mAcceptanceLL=itemView.findViewById(R.id.acceptanceLL);
            mOrderLL=itemView.findViewById(R.id.orderLL);
            mOrderStatusRL=itemView.findViewById(R.id.orderStatusRL);
            mViewOrdder=itemView.findViewById(R.id.viewImgV);


        }


        public void setToRestaurant(Order order, String key){
            mRestaurantNameTxtV.setText(order.getRestaurantName());
            mCustomerNameTxtV.setText(itemView.getResources().getString(R.string.SR,
                    order.getName(),order.getNumber(),order.getPrice()));
            //
            //mCustomerNumTxtV.setText("رقم العميل: "+order.getNumber());
            //mPriceTxtV.setText("سعر الطلب: "+order.getmPrice());
            this.key=key;
        }


        public void bind(Order order,String key){
            mRestaurantNameTxtV.setText(order.getRestaurantName());
            mCustomerNameTxtV.setText(itemView.getResources().getString(R.string.SR,
                    order.getName(),order.getNumber(),order.getPrice()));
            //
            //mCustomerNumTxtV.setText("رقم العميل: "+order.getNumber());
            //mPriceTxtV.setText("سعر الطلب: "+order.getmPrice());
            if(order.getOrderStatuse().equals("Pending")){
                mToRestaurantImgV.setImageResource(R.drawable.ic_to_restaurant);
                mToRestaurantTxtV.setText(itemView.getResources().getString(R.string.to_restaurant));
            }else if(order.getOrderStatuse().equals("To Restaurant")){
                mToRestaurantImgV.setImageResource(R.drawable.ic_restaurant);
                mToRestaurantTxtV.setText(itemView.getResources().getString(R.string.at_restaurant));
            }else if(order.getOrderStatuse().equals("Restaurant")){
                mToRestaurantImgV.setImageResource(R.drawable.ic_to_customer);
                mToRestaurantTxtV.setText(itemView.getResources().getString(R.string.to_customer));
            }else if(order.getOrderStatuse().equals("To Customer")){
                mToRestaurantImgV.setImageResource(R.drawable.ic_customer_home);
                mToRestaurantTxtV.setText(itemView.getResources().getString(R.string.at_customer));
            }else if(order.getOrderStatuse().equals("Customer")){

            }

            if(order.getDriverAccepted().equals(false)){
                mOrderLL.setVisibility(View.INVISIBLE);
                mAcceptanceLL.setVisibility(View.VISIBLE);
            }
            this.key=key;
        }


    }
}
