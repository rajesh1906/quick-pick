package com.quick_pick.Model;

/**
 * Created by Rajesh Kumar on 16-02-2018.
 */

public class RestaurantData {
    private String CityId;

    private String CityName;

    public String getCityId ()
    {
        return CityId;
    }

    public void setCityId (String CityId)
    {
        this.CityId = CityId;
    }

    public String getCityName ()
    {
        return CityName;
    }

    public void setCityName (String CityName)
    {
        this.CityName = CityName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CityId = "+CityId+", CityName = "+CityName+"]";
    }
}
