package com.example.fooddelivery.Model;

public class Food {

    private String FoodName;
    private String ImgUrl;
    private String Price;


    public Food(){}

    public Food(String foodName, String imgUrl, String price) {
        FoodName = foodName;
        ImgUrl = imgUrl;
        Price = price;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
