package com.example.gestion.tareas.D_infraestructure.util;

public final class KeysData {
    private KeysData(){

    }
    private static final String MAP_RESPONSE_BAD_REQUEST = "mapResponseBadRequest";
    private static final String MAP_VALUE_TRUE = "mapValueTrue";
    private static final String MAP_RESPONSE_SUCCESS = "mapResponseSuccess";
    public static String getBadRequest(){
        return MAP_RESPONSE_BAD_REQUEST;
    }
    public static String getValueTrue(){
        return MAP_VALUE_TRUE;
    }
    public static String getResponseSuccess(){
        return MAP_RESPONSE_SUCCESS;
    }
}
