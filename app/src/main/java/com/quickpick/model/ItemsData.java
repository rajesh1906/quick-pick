package com.quickpick.model;

/**
 * Created by Rajesh Kumar on 05-04-2018.
 */
public class ItemsData {
    private String  Description="";

    private String Amount="";

    private String RestaurantID="";

    private String NumberofQtys="";

    private String SubmenuId="";

    private String ItemName="";

    private String SubMenuName="";

    private String Item_Id="";

    private String ItemUrl="";

    public String  getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getRestaurantID ()
    {
        return RestaurantID;
    }

    public void setRestaurantID (String RestaurantID)
    {
        this.RestaurantID = RestaurantID;
    }

    public String getNumberofQtys ()
    {
        return NumberofQtys;
    }

    public void setNumberofQtys (String NumberofQtys)
    {
        this.NumberofQtys = NumberofQtys;
    }

    public String getSubmenuId ()
    {
        return SubmenuId;
    }

    public void setSubmenuId (String SubmenuId)
    {
        this.SubmenuId = SubmenuId;
    }

    public String getItemName ()
    {
        return ItemName;
    }

    public void setItemName (String ItemName)
    {
        this.ItemName = ItemName;
    }

    public String getSubMenuName ()
    {
        return SubMenuName;
    }

    public void setSubMenuName (String SubMenuName)
    {
        this.SubMenuName = SubMenuName;
    }

    public String getItem_Id ()
    {
        return Item_Id;
    }

    public void setItem_Id (String Item_Id)
    {
        this.Item_Id = Item_Id;
    }

    public String getItemUrl ()
    {
        return ItemUrl;
    }

    public void setItemUrl (String ItemUrl)
    {
        this.ItemUrl = ItemUrl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Description = "+Description+", Amount = "+Amount+", RestaurantID = "+RestaurantID+", NumberofQtys = "+NumberofQtys+", SubmenuId = "+SubmenuId+", ItemName = "+ItemName+", SubMenuName = "+SubMenuName+", Item_Id = "+Item_Id+", ItemUrl = "+ItemUrl+"]";
    }
}
