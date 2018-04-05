package com.quickpick.model.adds;

/**
 * Created by Rajesh Kumar on 04-04-2018.
 */
public class AddsData {
    private String Firsturls;

    private String Secondurls;

    private String sl_no;

    public String getFirsturls ()
    {
        return Firsturls;
    }

    public void setFirsturls (String Firsturls)
    {
        this.Firsturls = Firsturls;
    }

    public String getSecondurls ()
    {
        return Secondurls;
    }

    public void setSecondurls (String Secondurls)
    {
        this.Secondurls = Secondurls;
    }

    public String getSl_no ()
    {
        return sl_no;
    }

    public void setSl_no (String sl_no)
    {
        this.sl_no = sl_no;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Firsturls = "+Firsturls+", Secondurls = "+Secondurls+", sl_no = "+sl_no+"]";
    }
}
