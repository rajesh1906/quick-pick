package com.quickpick.model.cartdeails;

/**
 * Created by Rajesh Kumar on 20-04-2018.
 */
public class CartdetailsData {

    private String Amount;

    private String ItemName;

    private String qty;

    private String itemid;

    private String date;

    public String getRes_Id() {
        return Res_Id;
    }

    public void setRes_Id(String res_Id) {
        Res_Id = res_Id;
    }

    private String Res_Id;

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getItemName ()
    {
        return ItemName;
    }

    public void setItemName (String ItemName)
    {
        this.ItemName = ItemName;
    }

    public String getQty ()
    {
        return qty;
    }

    public void setQty (String qty)
    {
        this.qty = qty;
    }

    public String getItemid ()
    {
        return itemid;
    }

    public void setItemid (String itemid)
    {
        this.itemid = itemid;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Amount = "+Amount+", ItemName = "+ItemName+", qty = "+qty+", itemid = "+itemid+", date = "+date+"]";
    }
}
