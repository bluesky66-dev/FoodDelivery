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
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class AdapterAllOrders extends RecyclerView.Adapter<AdapterAllOrders.NewViewHolder> {

    private Context mContext;
    private List<Order> mOrderList;
    private List<String> mKeys;
    private AdapterAllOrders mAdapterAllOrders;
    private String mID;


    public AdapterAllOrders(){

    }
    public void setAllOrders(RecyclerView recyclerView, Context context, List<Order> orders, List<String> keys) {
        mContext = context;
        mAdapterAllOrders =new AdapterAllOrders(mContext,orders,keys);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        recyclerView.setAdapter(mAdapterAllOrders);

    }

    public AdapterAllOrders(Context context, List<Order> orderList, List<String> keys) {
        this.mContext = context;
        mOrderList = orderList;
        mKeys = keys;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_all_order_item,parent,false);
        return new AdapterAllOrders.NewViewHolder(view);
    }






    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {

        new FirebaseDatabaseHelper().extractName(mOrderList.get(position).getDriverUID(),
                new FirebaseDatabaseHelper.ExtractSingleValue() {
            @Override
            public void ValueIsExtracted(String value) {
                holder.mDriverNameTxtV.setText(mContext.getString(R.string.captain1,value));
            }
        });
        holder.bind(mOrderList.get(position),mKeys.get(position));
        holder.mToRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().changeOrderStatus("To Restaurant",mKeys.get(position));
                Toast.makeText(mContext,"To Restaurant",Toast.LENGTH_SHORT).show();
                new FirebaseDatabaseHelper().changeDriverAccepted(mKeys.get(position),true);

            }
        });
        holder.mRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().changeOrderStatus("Restaurant",mKeys.get(position));
                Toast.makeText(mContext,"Restaurant",Toast.LENGTH_SHORT).show();

            }
        });
        holder.mToCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().changeOrderStatus("To Customer",mKeys.get(position));
                Toast.makeText(mContext,"To Customer",Toast.LENGTH_SHORT).show();

            }
        });
        holder.mCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().changeOrderStatus("Customer",mKeys.get(position));
                Toast.makeText(mContext,"Customer",Toast.LENGTH_SHORT).show();

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
        private ImageView mToRestaurant,mRestaurant,mToCustomer,mCustomer;
        private TextView mDriverNameTxtV;



        public NewViewHolder(View itemView){
            super(itemView);
            mRestaurantNameTxtV =itemView.findViewById(R.id.restaurantNameTxtV);
            mCustomerNameTxtV =itemView.findViewById(R.id.customerNameTxtV);
            //mCustomerNumTxtV =itemView.findViewById(R.id.customerNumTxtV);
            //mPriceTxtV =itemView.findViewById(R.id.priceTxtV);
            mCardView=itemView.findViewById(R.id.mainCardview);
            mToRestaurant=itemView.findViewById(R.id.toRestaurantImgV);
            mRestaurant=itemView.findViewById(R.id.restaurantIcon);
            mToCustomer=itemView.findViewById(R.id.toCustomerIcon);
            mCustomer=itemView.findViewById(R.id.customerHomeIcon);
            mDriverNameTxtV=itemView.findViewById(R.id.driverNameTxtV);


        }

        public void setToRestaurant(Order order, String key){
            mRestaurantNameTxtV.setText(order.getRestaurantName());
            mCustomerNameTxtV.setText(itemView.getResources().getString(R.string.SR,
                    order.getName(),order.getNumber(),order.getPrice()));
            //mCustomerNumTxtV.setText("رقم العميل: "+order.getNumber());
            //mPriceTxtV.setText("سعر الطلب: "+order.getmPrice());

            this.key=key;
        }


        public void bind(Order order,String key){
            mRestaurantNameTxtV.setText(order.getRestaurantName());
            mCustomerNameTxtV.setText(itemView.getResources().getString(R.string.SR,
                    order.getName(),order.getNumber(),order.getPrice()));
            //mCustomerNumTxtV.setText("رقم العميل: "+order.getNumber());
            //mPriceTxtV.setText("سعر الطلب: "+order.getmPrice());
            if(order.getOrderStatuse().equals("To Restaurant")){
                mToRestaurant.setImageResource(R.drawable.ic_to_restaurant_red);
            }else {
                mToRestaurant.setImageResource(R.drawable.ic_to_restaurant);
            }
            if(order.getOrderStatuse().equals("Restaurant")){
                mRestaurant.setImageResource(R.drawable.ic_restaurant_red);
            }else {
                mRestaurant.setImageResource(R.drawable.ic_restaurant);
            }
            if(order.getOrderStatuse().equals("To Customer")){
                mToCustomer.setImageResource(R.drawable.ic_to_customer_red);
            }else {
                mToCustomer.setImageResource(R.drawable.ic_to_customer);
            }
            if(order.getOrderStatuse().equals("Customer")){
                mCustomer.setImageResource(R.drawable.ic_customer_home_red);
            }else {
                mCustomer.setImageResource(R.drawable.ic_customer_home);
            }
            this.key=key;
        }


    }
}

// SHA1
//  17:2c:c1:30:4f:3f:d1:31:45:7c:b7:48:ba:21:8f:41:92:1a:ef:70
//   9a:35:bc:94:2a:a6:8f:3c:a0:98:0e:93:fb:38:11:99:a4:24:55:5e
//   9A:35:BC:94:2A:A6:8F:3C:A0:98:0E:93:FB:38:11:99:A4:24:55:5E
