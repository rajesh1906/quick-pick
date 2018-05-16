package com.quickpick.model.customizedmenu;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 12-05-2018.
 */
public class Root {

    private ArrayList<SubItems> subItems;

    private String Header;

    public ArrayList<SubItems> getSubItems ()
    {
        return subItems;
    }

    public void setSubItems (ArrayList<SubItems> subItems)
    {
        this.subItems = subItems;
    }

    public String getHeader ()
    {
        return Header;
    }

    public void setHeader (String Header)
    {
        this.Header = Header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subItems = "+subItems+", Header = "+Header+"]";
    }
}
