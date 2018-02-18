package com.quickpick.model.menu;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class Menu_Items {
    private ArrayList<com.quickpick.model.menu.Menu> Menu;

    private String Status;

    private String error;

    public ArrayList<com.quickpick.model.menu.Menu> getMenu ()
    {
        return Menu;
    }

    public void setMenu (ArrayList<com.quickpick.model.menu.Menu> Menu)
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
