package com.quickpick.model.menu.menunew;

/**
 * Created by Rajesh Kumar on 12-03-2018.
 */

public class Item {

    private Integer Item_Id;

    private String ItemName;

    private String Amount;

    private String NumberofQtys;

    private String RestaurantID;

    private String menuId;

    private String SubMenuId;

    private Object SubMenuName;

    private Object MenuName;

    public Integer getItem_Id() {
        return Item_Id;
    }

    public void setItem_Id(Integer item_Id) {
        Item_Id = item_Id;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getNumberofQtys() {
        return NumberofQtys;
    }

    public void setNumberofQtys(String numberofQtys) {
        NumberofQtys = numberofQtys;
    }

    public String getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        RestaurantID = restaurantID;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getSubMenuId() {
        return SubMenuId;
    }

    public void setSubMenuId(String subMenuId) {
        SubMenuId = subMenuId;
    }

    public Object getSubMenuName() {
        return SubMenuName;
    }

    public void setSubMenuName(Object subMenuName) {
        SubMenuName = subMenuName;
    }

    public Object getMenuName() {
        return MenuName;
    }

    public void setMenuName(Object menuName) {
        MenuName = menuName;
    }





}
