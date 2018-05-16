package com.quickpick.model.customizedmenu;

/**
 * Created by Rajesh Kumar on 12-05-2018.
 */
public class SubItems {
    private String amount;

    private String description;

    private String Image;

    private String subItem_name;

    public String getItem_Id() {
        return Item_Id;
    }

    public void setItem_Id(String item_Id) {
        Item_Id = item_Id;
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

    private String Item_Id;
    private String NumberofQtys;
    private String RestaurantID;


    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImage ()
    {
        return Image;
    }

    public void setImage (String Image)
    {
        this.Image = Image;
    }

    public String getSubItem_name ()
    {
        return subItem_name;
    }

    public void setSubItem_name (String subItem_name)
    {
        this.subItem_name = subItem_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", description = "+description+", Image = "+Image+", subItem_name = "+subItem_name+"]";
    }
}
