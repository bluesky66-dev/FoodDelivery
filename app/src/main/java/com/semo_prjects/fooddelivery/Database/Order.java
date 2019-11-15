package com.semo_prjects.fooddelivery.Database;

public class Order {
    private String mName;
    private String mNumber;
    private String mRestaurantName;
    private String mLocation;
    private String mPrice;
    private String mRestaurantUID;
    private String mDriverUID;
    private String mOrderStatuse;
    private int mOrderNumber;
    private Boolean mDriverAccepted;
    private String mCancellationReason;



    public Order(String name, String number, String restaurantName, String location, String price,
                 String restaurantUID, String driverUID, String orderStatuse,
                 int orderNumber, Boolean driverAccepted, String cancellationReason) {
        mName = name;
        mNumber = number;
        mRestaurantName = restaurantName;
        mLocation = location;
        mPrice = price;
        mRestaurantUID = restaurantUID;
        mDriverUID = driverUID;
        mOrderStatuse = orderStatuse;
        mOrderNumber = orderNumber;
        mDriverAccepted = driverAccepted;
        mCancellationReason = cancellationReason;
    }

    public Order() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        mRestaurantName = restaurantName;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getRestaurantUID() {
        return mRestaurantUID;
    }

    public void setRestaurantUID(String restaurantUID) {
        mRestaurantUID = restaurantUID;
    }

    public String getDriverUID() {
        return mDriverUID;
    }

    public void setDriverUID(String driverUID) {
        mDriverUID = driverUID;
    }

    public String getOrderStatuse() {
        return mOrderStatuse;
    }

    public void setOrderStatuse(String orderStatuse) {
        mOrderStatuse = orderStatuse;
    }

    public int getOrderNumber() {
        return mOrderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        mOrderNumber = orderNumber;
    }

    public Boolean getDriverAccepted() {
        return mDriverAccepted;
    }

    public void setDriverAccepted(Boolean driverAccepted) {
        mDriverAccepted = driverAccepted;
    }

    public String getCancellationReason() {
        return mCancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        mCancellationReason = cancellationReason;
    }
}


