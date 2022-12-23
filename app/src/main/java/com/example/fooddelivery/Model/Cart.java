package com.example.fooddelivery.Model;

public class Cart {
    String fNameCart;
    String imgCart;
    Long priceCart;

    public Cart(){}

    public Cart(String fNameCart, String imgCart, Long priceCart) {
        this.fNameCart = fNameCart;
        this.imgCart = imgCart;
        this.priceCart = priceCart;
    }

    public String getfNameCart() {
        return fNameCart;
    }

    public void setfNameCart(String fNameCart) {
        this.fNameCart = fNameCart;
    }

    public String getImgCart() {
        return imgCart;
    }

    public void setImgCart(String imgCart) {
        this.imgCart = imgCart;
    }

    public Long getPriceCart() {
        return priceCart;
    }

    public void setPriceCart(Long priceCart) {
        this.priceCart = priceCart;
    }
}
