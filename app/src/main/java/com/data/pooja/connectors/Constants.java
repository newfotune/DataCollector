package com.data.pooja.connectors;

/**
 * Created by Nwoke Fortune Chiemeziem on 1/24/2017.
 * @version 1.0
 * Class that stores the url's to the php scripts.
 */
public class Constants {
    private static final String ROOT_URL = "https://winter2017.000webhostapp.com/datacollector/operations/";

    public static final String URL_UPLOAD = ROOT_URL+"uploadEntity.php";
    public static final String URL_LOGIN = ROOT_URL+"userLogin.php";
    public static final String URL_REGISTER = ROOT_URL+"registerUser.php";
    //should not be instantiated
    private Constants() {}
}
