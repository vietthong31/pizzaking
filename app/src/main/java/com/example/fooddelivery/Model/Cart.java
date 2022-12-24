package com.example.fooddelivery.Model;

public class Cart {
    String currentDate;
    String CurrentTime;
    String fNameCart;
    String fPriceCart;
    String totalQuantity;
    Long totalPrice;

    public Cart(){}

    public Cart(String currentDate, String CurrentTime, String fNameCart, String fPriceCart, String totalQuantity, Long totalPrice) {
        this.currentDate = currentDate;
        this.CurrentTime = CurrentTime;
        this.fNameCart = fNameCart;
        this.fPriceCart = fPriceCart;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String GetCurrentTime() {
        return CurrentTime;
    }

    public void setGetCurrentTime(String getCurrentTime) {
        this.CurrentTime = getCurrentTime;
    }

    public String getfNameCart() {
        return fNameCart;
    }

    public void setfNameCart(String fNameCart) {
        this.fNameCart = fNameCart;
    }

    public String getfPriceCart() {
        return fPriceCart;
    }

    public void setfPriceCart(String fPriceCart) {
        this.fPriceCart = fPriceCart;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
