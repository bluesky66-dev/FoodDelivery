package com.semo_prjects.fooddelivery.Database;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private List<Order> mOrders=new ArrayList<>();
    private List<Accounts> mAccounts=new ArrayList<>();
    FirebaseUser mUser=FirebaseAuth.getInstance().getCurrentUser();
    public FirebaseDatabaseHelper(){
        mDatabase=FirebaseDatabase.getInstance();
    }


    public interface OrdersDataStatus {
        void DataIsLoaded(List<Order> orders, List<String> keys);
        void DataIsInserted();
        void DataIsDeleted();
        void DataIsUpdated();

    }
    public interface AccountsDataStatus{
        void DataIsLoaded(List<Accounts> accounts, List<String> keys);
        void DataIsInserted();
        void DataIsDeleted();
        void DataIsUpdated();

    }
    public interface ExtractSingleValue{
        void ValueIsExtracted(String value);
    }
    public interface ExtractSingleValueTest{
        void ValueIsExtractedTest(long value);
    }




    public void addNewOrder(Order order,String key,final OrdersDataStatus dataStatus){

        mDatabase.getReference("Orders").child(key).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void changeOrderStatus(String status,String orderID){

        mDatabase.getReference("Orders").child(orderID).child("orderStatuse").setValue(status);
    }

    public void changeDriverAccepted(String key,Boolean value){

        mDatabase.getReference("Orders").child(key).child("driverAccepted").setValue(value);
    }
    public void changeStringValue(String parentKey,String childKey,String value){

        mDatabase.getReference("Orders").child(parentKey).child(childKey).setValue(value);
    }




    public void checkDriverAccepted(Boolean driverAccepted, final OrdersDataStatus ordersDataStatus){
        mDatabase.getReference().child("Orders").orderByChild("driverAccepted").equalTo(driverAccepted).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mOrders.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Order order=keyNode.getValue(Order.class);
                    mOrders.add(order);
                }
                ordersDataStatus.DataIsLoaded(mOrders,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showRestaurantOrders(final OrdersDataStatus ordersDataStatus){
        Query query=mDatabase.getReference().child("Orders").orderByChild("restaurantUID").equalTo(mUser.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Order order=keyNode.getValue(Order.class);
                    mOrders.add(order);
                }
                ordersDataStatus.DataIsLoaded(mOrders,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showDriverOrders(final OrdersDataStatus ordersDataStatus){

        mDatabase.getReference().child("Orders").orderByChild("driverUID").equalTo(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mOrders.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Order order=keyNode.getValue(Order.class);
                    mOrders.add(order);
                }
                ordersDataStatus.DataIsLoaded(mOrders,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addNewAccount(Accounts accounts,String key,final AccountsDataStatus dataStatus){

        mDatabase.getReference("Accounts").child(key).setValue(accounts).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateAccount(Accounts accounts,String key,final AccountsDataStatus dataStatus){
        mDatabase.getReference("Accounts").child(key).setValue(accounts).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteAccount(String key,final AccountsDataStatus dataStatus){

        mDatabase.getReference("Accounts").child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }

    public void changeAccountStatus(String status){
        mDatabase.getReference("Accounts").child(mUser.getUid()).child("status").setValue(status);
    }
    public void checkAccountStatus(Boolean driverAccepted, final OrdersDataStatus ordersDataStatus){
        mDatabase.getReference().child("Accounts").child(mUser.getUid()).child("status").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void extractSingleValue(final String key, final ExtractSingleValue extractSingleValue){
        mDatabase.getReference("Accounts").child(mUser.getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.child(key).getValue(String.class);
                        extractSingleValue.ValueIsExtracted(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
    }
    public void extractSingleValueTest(final String key, final ExtractSingleValueTest extractSingleValueTest){
        mDatabase.getReference().
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long value = dataSnapshot.child(key).getValue(long.class);
                        extractSingleValueTest.ValueIsExtractedTest(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
    }
    public void extractName(final String key, final ExtractSingleValue extractSingleValue){
        mDatabase.getReference("Accounts").child(key).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.child("name").getValue(String.class);
                        extractSingleValue.ValueIsExtracted(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
    }


    public void showAccounts(final AccountsDataStatus dataStatus){

        mDatabase.getReference("Accounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAccounts.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Accounts accounts =keyNode.getValue(Accounts.class);
                    mAccounts.add(accounts);
                }
                dataStatus.DataIsLoaded(mAccounts,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showDriversAccounts(final AccountsDataStatus dataStatus){

        Query query=mDatabase.getReference().child("Accounts").orderByChild("accountType").equalTo("Driver");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAccounts.clear();
                List<String> keys=new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    Accounts accounts =keyNode.getValue(Accounts.class);
                    if(!accounts.getStatus().equals("Off Work")) {
                        mAccounts.add(accounts);
                        keys.add(keyNode.getKey());
                    }
                }

                dataStatus.DataIsLoaded(mAccounts,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addValueTest(Object m){{
    mDatabase.getReference("Test").setValue(m);
    }
    }

}
