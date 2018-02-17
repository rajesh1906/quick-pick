package com.quick_pick.Model;

/**
 * Created by Rajesh Kumar on 17-02-2018.
 */

public class CateogryData {
    private String Id;

    private String Category_Name;

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getCategory_Name ()
    {
        return Category_Name;
    }

    public void setCategory_Name (String Category_Name)
    {
        this.Category_Name = Category_Name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Id = "+Id+", Category_Name = "+Category_Name+"]";
    }
}
