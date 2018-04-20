package com.quickpick.model.cartdeails;

import java.util.ArrayList;

/**
 * Created by Rajesh Kumar on 20-04-2018.
 */
public class CartDataRoot {
    private String Status;

    private ArrayList<CartdetailsData> CartdetailsData;

    private String error;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public ArrayList<CartdetailsData> getCartdetailsData ()
    {
        return CartdetailsData;
    }

    public void setCartdetailsData (ArrayList<CartdetailsData> CartdetailsData)
    {
        this.CartdetailsData = CartdetailsData;
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
        return "ClassPojo [Status = "+Status+", CartdetailsData = "+CartdetailsData+", error = "+error+"]";
    }
}
