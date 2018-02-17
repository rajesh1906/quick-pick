package com.quick_pick.Model;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Category {

    private String Status;

    private String error;

    private ArrayList<CateogryData> CateogryData;

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

    public ArrayList<CateogryData> getCateogryData ()
    {
        return CateogryData;
    }

    public void setCateogryData (ArrayList<CateogryData> CateogryData)
    {
        this.CateogryData = CateogryData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", error = "+error+", CateogryData = "+CateogryData+"]";
    }
}
