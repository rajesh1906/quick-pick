package com.quickpick.model.restaurant_category;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Restaurant_names {
    private String Status;

    private String error;

    private ArrayList<com.quickpick.model.restaurant_category.RestaurantData> RestaurantData;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public ArrayList<com.quickpick.model.restaurant_category.RestaurantData> getRestaurantData ()
    {
        return RestaurantData;
    }

    public void setRestaurantData (ArrayList<com.quickpick.model.restaurant_category.RestaurantData> RestaurantData)
    {
        this.RestaurantData = RestaurantData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", error = "+error+", RestaurantData = "+RestaurantData+"]";
    }
}
