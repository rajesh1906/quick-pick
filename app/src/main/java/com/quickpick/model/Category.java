package com.quickpick.model;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Category {

    private String Status;

    private String error;

    private ArrayList<com.quickpick.model.CateogryData> CateogryData;

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

    public ArrayList<com.quickpick.model.CateogryData> getCateogryData ()
    {
        return CateogryData;
    }

    public void setCateogryData (ArrayList<com.quickpick.model.CateogryData> CateogryData)
    {
        this.CateogryData = CateogryData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", error = "+error+", CateogryData = "+CateogryData+"]";
    }
}
