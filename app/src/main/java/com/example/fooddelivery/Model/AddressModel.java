package com.example.fooddelivery.Model;

public class AddressModel {
    String userAddess;
    boolean isSelected;

    public AddressModel() {
    }

    public AddressModel(String userAddess, boolean isSelected) {
        this.userAddess = userAddess;
        this.isSelected = isSelected;
    }

    public String getUserAddess() {
        return userAddess;
    }

    public void setUserAddess(String userAddess) {
        this.userAddess = userAddess;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
