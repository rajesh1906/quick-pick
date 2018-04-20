package com.quickpick.presenter.services.Network;

/**
 * Created by ashok on 18/02/18.
 */

  public class APIS {
//      <string name="base_host_name">http://www.jklogistics.in/QuikPickApi/</string>

//   <string name="getCities">displayCitysdataNames</string>
//   <string name="getResturants">displayRestaurantNames</string>
//   <string name="getCategory">GettingCateogry</string>
//   <string name="getRestaurant_id">gettingRestaurantId</string>
//   <string name="getDisplayItems">displayItemsData</string>
//   <string name="displayMenusData">displayMenusData</string>
    public static String  BASEURL="http://www.jklogistics.in/QuikPickApi/";
    public static String  CITIES="displayCitysdataNames";
    public static String  Category="GettingCateogry";
    public static String  Restarant_id="gettingRestaurantId";
    public static String Restarents="displayRestaurantNames";
    public static String  DisplayItems="displayItemsData";
    public static String  DisplayMenusData="displayMenusData";
    public static String Defaultrestarents = "displayRestaurantNamesCityBasedWithOutCategory";
    public static String RestarentsBasedCategory = "displayRestaurantNamesCityBasedWithCategory";
    public static String displayItemsSearchData = "displayItemsSearchData";
    public static String displayItemsDataMenuBased = "displayItemsDataMenuBased";
    public static String MenuLoading = "MenuLoading";
    public static String DefultRestaurantLoading = "DefultRestaurantLoading";
    public static String GettingResDataBasedOnLat = "GettingResDataBasedOnLat";
    public static String AddsLoading = "AddsLoading";
    public static String SubMenusLoading = "SubMenusLoading";
    public static String AllItemsLoading = "AllItemsLoading";
    public static String SIGNUP = "LoginSave";
    public static String SIGNIN = "LogingGettingData";
    public static String CARTDETAILS = "GettingCartdetailsdata";
    public static String ADDCART = "Cartdataadding";
    //public static String PAYMENT_URL="http://www.jklogistics.in/QuikPickApi/SubmitingItemsthroughpaymentbasedonpaypal?itemsid=1,2&qty=2,1&TypeWay=Pickup&loging=1";
    public static String PAYMENT_URL="http://www.jklogistics.in/QuikPickApi/SubmitingItemsthroughpaymentbasedonpaypal?items=[{%22itemsid%22:%221%22,%22qty%22:%222%22},{%22itemsid%22:%222%22,%22qty%22:%223%22}]&RestaurantID=1&TypeWay=%22eat%22&loging=1\n" +
            "items=[{\"itemsid\":\"1\",\"qty\":\"2\"},{\"itemsid\":\"2\",\"qty\":\"3\"}]";


}
