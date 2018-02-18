package com.quickpick.model.menu;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Menu {
    private String RestaurantID;

    private String MenuName;

    private String MenuId;

    public String getRestaurantID ()
    {
        return RestaurantID;
    }

    public void setRestaurantID (String RestaurantID)
    {
        this.RestaurantID = RestaurantID;
    }

    public String getMenuName ()
    {
        return MenuName;
    }

    public void setMenuName (String MenuName)
    {
        this.MenuName = MenuName;
    }

    public String getMenuId ()
    {
        return MenuId;
    }

    public void setMenuId (String MenuId)
    {
        this.MenuId = MenuId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RestaurantID = "+RestaurantID+", MenuName = "+MenuName+", MenuId = "+MenuId+"]";
    }
}
