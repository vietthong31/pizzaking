package com.example.fooddelivery.Model;

public class Cart {
    String CurrentDate;
    String CurrentTime;
    String FoodName;
    Long Price;
    String TotalQuantity;
    Long TotalPrice;

    public Cart(){}

    public Cart(String CurrentDate, String CurrentTime, String FoodName, Long Price, String TotalQuantity, Long TotalPrice) {
        this.CurrentDate = CurrentDate;
        this.CurrentTime = CurrentTime;
        this.FoodName = FoodName;
        this.Price = Price;
        this.TotalQuantity = TotalQuantity;
        this.TotalPrice = TotalPrice;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public Long getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Long TotalPrice) {
        this.TotalPrice = TotalPrice;
    }
}
