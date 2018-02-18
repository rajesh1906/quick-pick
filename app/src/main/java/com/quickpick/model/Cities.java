package com.quickpick.model;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 16-02-2018.
 */

public class Cities {
    private String Status;

    private String error;

    private ArrayList<com.quickpick.model.RestaurantData> RestaurantData;

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

    public ArrayList<com.quickpick.model.RestaurantData> getRestaurantData ()
    {
        return RestaurantData;
    }

    public void setRestaurantData (ArrayList<com.quickpick.model.RestaurantData> RestaurantData)
    {
        this.RestaurantData = RestaurantData;
    }
}
