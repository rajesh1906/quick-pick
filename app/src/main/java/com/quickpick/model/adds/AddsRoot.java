package com.quickpick.model.adds;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 04-04-2018.
 */
public class AddsRoot {
    private String Status;

    private String error;

    private ArrayList<AddsData> AddsData;

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

    public  ArrayList<AddsData> getAddsData ()
    {
        return AddsData;
    }

    public void setAddsData ( ArrayList<AddsData> AddsData)
    {
        this.AddsData = AddsData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", error = "+error+", AddsData = "+AddsData+"]";
    }
}
