package com.example.fooddelivery.Model;

public class Food {

    private String FoodName;
    private String ImgUrl;
    private Long Price;
    private String Description;


    public Food(){}

    public Food(String foodName, String imgUrl, Long price, String Description) {
        FoodName = foodName;
        ImgUrl = imgUrl;
        Price = price;
        Description = Description;
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

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
