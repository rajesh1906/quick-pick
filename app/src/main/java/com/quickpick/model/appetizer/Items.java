package com.quickpick.model.appetizer;

/**
 * Created by Rajesh kumar on 17-02-2018.
 */

public class Items {
    private String RestaurantID;

    private String Amount;

    private String MenuName;

    private String NumberofQtys;

    private String ItemName;

    private String menuId;

    private String Item_Id;

    public String getRestaurantID ()
    {
        return RestaurantID;
    }

    public void setRestaurantID (String RestaurantID)
    {
        this.RestaurantID = RestaurantID;
    }

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getMenuName ()
    {
        return MenuName;
    }

    public void setMenuName (String MenuName)
    {
        this.MenuName = MenuName;
    }

    public String getNumberofQtys ()
    {
        return NumberofQtys;
    }

    public void setNumberofQtys (String NumberofQtys)
    {
        this.NumberofQtys = NumberofQtys;
    }

    public String getItemName ()
    {
        return ItemName;
    }

    public void setItemName (String ItemName)
    {
        this.ItemName = ItemName;
    }

    public String getMenuId ()
    {
        return menuId;
    }

    public void setMenuId (String menuId)
    {
        this.menuId = menuId;
    }

    public String getItem_Id ()
    {
        return Item_Id;
    }

    public void setItem_Id (String Item_Id)
    {
        this.Item_Id = Item_Id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RestaurantID = "+RestaurantID+", Amount = "+Amount+", MenuName = "+MenuName+", NumberofQtys = "+NumberofQtys+", ItemName = "+ItemName+", menuId = "+menuId+", Item_Id = "+Item_Id+"]";
    }
}
