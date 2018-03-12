package com.quickpick.model.menu.menunew;

/**
 * Created by Rajesh Kumar on 12-03-2018.
 */

public class MenuDatum {

    private Integer Item_Id;

    private String ItemName;

    private String Amount;

    private String NumberofQtys;

    private String menuId;

    private String MenuName;
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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }




}
