package com.quick_pick.Model.menu;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Menu_Items {
    private ArrayList<Menu> Menu;

    private String Status;

    private String error;

    public ArrayList<Menu> getMenu ()
    {
        return Menu;
    }

    public void setMenu (ArrayList<Menu> Menu)
    {
        this.Menu = Menu;
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
        return "ClassPojo [Menu = "+Menu+", Status = "+Status+", error = "+error+"]";
    }
}
