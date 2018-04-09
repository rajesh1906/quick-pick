package com.quickpick.model.restaurant_category;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class RestaurantData {
    private String Res_id;

    private String restaurantname;

    private String PreparationTimeDuration;
    private String restaurantnameurl;

    public String getPreparationTimeDuration() {
        return PreparationTimeDuration;
    }

    public void setPreparationTimeDuration(String preparationTimeDuration) {
        PreparationTimeDuration = preparationTimeDuration;
    }



    private String sl_no;

    public String getRes_id ()
    {
        return Res_id;
    }

    public void setRes_id (String Res_id)
    {
        this.Res_id = Res_id;
    }

    public String getRes_Name ()
    {
        return restaurantname;
    }

    public void setRes_Name (String Res_Name)
    {
        this.restaurantname = Res_Name;
    }

    public String getSl_no ()
    {
        return sl_no;
    }

    public void setSl_no (String sl_no)
    {
        this.sl_no = sl_no;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public String getRestaurantnameurl() {
        return restaurantnameurl;
    }

    public void setRestaurantnameurl(String restaurantnameurl) {
        this.restaurantnameurl = restaurantnameurl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Res_id = "+Res_id+", Res_Name = "+restaurantname+", sl_no = "+sl_no+"]";
    }
}
