package com.smartcarpark.smartcarparking;

public class Constants {

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    //URLs
    private static final String ROOT_URL = "http://ec2-13-232-39-50.ap-south-1.compute.amazonaws.com/php/Api.php?apicall=";
    public static final String URL_VALIDATE_USER_LOGIN = ROOT_URL + "validate_user_login";
    public static final String URL_GET_ALL_ZONES_INFO = ROOT_URL + "get_all_zones_info";
    public static final String URL_GET_SLOTS_INFO_IN_ZONE = ROOT_URL + "get_slots_info_per_zone";

    //API Params
    public static final String API_USER_ID = "user_id";
    public static final String API_PASSWORD = "password";
    public static final String API_ZONES = "zones";
    public static final String API_ZONE_ID = "zone_id";
    public static final String API_SLOTS_OCCUPIED = "slots_occupied";
    public static final String API_SLOTS_AVAILABLE = "slots_available";
    public static final String API_SLOTS_PER_ZONE = "slots_per_zone";

    //REST Response
    public static final String ERROR_RESPONSE = "error";
    public static final String MESSAGE_RESPONSE = "msg";

    //INTENT
    public static final String INTENT_ZONE_ID = "zone_id";
    public static final String API_SLOT_ID = "slot_id";
    public static final String API_IS_AVAILABLE = "is_available";
}
