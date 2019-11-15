package com.semo_prjects.fooddelivery.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.util.List;

public class AdapterRestaurantOrders extends RecyclerView.Adapter<AdapterRestaurantOrders.NewViewHolder> {

    private Context mContext;
    private List<Order> mOrderList;
    private List<String> mKeys;
    private AdapterRestaurantOrders mAdapterRestaurantOrders;
    public AdapterRestaurantOrders(){

    }
    public void setMyOrders(RecyclerView recyclerView, Context context, List<Order> orders, List<String> keys) {
        mContext = context;
        mAdapterRestaurantOrders =new AdapterRestaurantOrders(mContext,orders,keys);
        recyclerView.setLayoutManager(new GridLayoutManager(context,1));
        recyclerView.setAdapter(mAdapterRestaurantOrders);
    }

    public AdapterRestaurantOrders(Context context, List<Order> orderList, List<String> keys) {
        this.mContext = context;
        mOrderList = orderList;
        mKeys = keys;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.cardview_my_order_item,parent,false);
        return new AdapterRestaurantOrders.NewViewHolder(view);
    }





    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {
        
        holder.bind(mOrderList.get(position),mKeys.get(position));

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




        public NewViewHolder(View itemView){
            super(itemView);
            mRestaurantNameTxtV =itemView.findViewById(R.id.restaurantNameTxtV);
            mCustomerNameTxtV =itemView.findViewById(R.id.customerNameTxtV);
            //mCustomerNumTxtV =itemView.findViewById(R.id.customerNumTxtV);
            //mPriceTxtV =itemView.findViewById(R.id.priceTxtV);
            mToRestaurant=itemView.findViewById(R.id.toRestaurantImgV);
            mRestaurant=itemView.findViewById(R.id.restaurantIcon);
            mToCustomer=itemView.findViewById(R.id.toCustomerIcon);
            mCustomer=itemView.findViewById(R.id.customerHomeIcon);

            mCardView=itemView.findViewById(R.id.mainCardview);
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
