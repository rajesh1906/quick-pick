package com.quick_pick.Model.Appetizer;

import java.util.ArrayList;

/**
 * Created by Rajesh kumar on 17-02-2018.
 */

public class Appetizer {
    private ArrayList<Items> Items;

    private String Status;

    private String error;

    public ArrayList<Items> getItems ()
    {
        return Items;
    }

    public void setItems (ArrayList<Items> Items)
    {
        this.Items = Items;
    }

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

    @Override
    public String toString()
    {
        return "ClassPojo [Items = "+Items+", Status = "+Status+", error = "+error+"]";
    }
}
