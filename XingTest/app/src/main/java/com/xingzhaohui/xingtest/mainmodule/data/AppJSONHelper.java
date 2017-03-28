package com.xingzhaohui.xingtest.mainmodule.data;
/**
 /**
 * The helper class for JSON data handling
 *
 * Created by zhaohuixing.
 */


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class AppJSONHelper {
    protected static void LogFunction(String key, String value) {
        Log.v(key, value);
    }

    public static Date ConvertMillisecondsToDate(long milsec) {
        Date time = new Date(milsec);
        return time;
    }


    public static String getString(String key, JSONObject jsonObject) {
        String value = "";
        if(jsonObject == null)
            return value;

        try {
            value = jsonObject.getString(key);
            LogFunction("AppJSONHelper.getString("+key+") Succeeded:", value);
        }
        catch (JSONException e) {
            LogFunction("AppJSONHelper.gGetString("+key+") failed:", e.toString());
        }
        return value;
    }

    public static Date getDate(String key, JSONObject jsonObject) {
        Date date = null;
        try {
            String dateString = jsonObject.getString(key);
            long nTime = Long.parseLong(dateString);
            date = AppJSONHelper.ConvertMillisecondsToDate(nTime);
            LogFunction("AppJSONHelper.getDate("+key+") Succeeded:", date.toString());
        } catch (JSONException e) {
            LogFunction("AppJSONHelper.getDate("+key+") failed:", e.toString());;
        }
        return date;
    }

    public static double getDouble(String key, JSONObject jsonObject) {
        double value = 0.0;

        if(jsonObject == null)
            return value;

        try {
            value = jsonObject.getDouble(key);
            LogFunction("AppJSONHelper.getDouble("+key+") Succeeded:", Double.valueOf(value).toString());
        }
        catch (JSONException e) {
            LogFunction("AppJSONHelper.getDouble("+key+") failed:", e.toString());
        }
        return value;
    }

    public static int getInt(String key, JSONObject jsonObject) {
        int value = 0;

        if(jsonObject == null)
            return value;

        try {
            value = jsonObject.getInt(key);
            LogFunction("AppJSONHelper.getInt(" + key + ") Succeeded:", Integer.valueOf(value).toString());
        }
        catch (JSONException e) {
            LogFunction("AppJSONHelper.getInt("+key+") failed:", e.toString());
        }
        return value;
    }

    public static Boolean getBoolean(String key, JSONObject jsonObject) {
        Boolean value = false;

        if(jsonObject == null)
            return value;

        try {
            value = jsonObject.getBoolean(key);
            LogFunction("AppJSONHelper.getBoolean(" + key + ") Succeeded:", String.valueOf(value));
        }
        catch (JSONException e) {
            LogFunction("AppJSONHelper.getBoolean("+key+") failed:", e.toString());
        }
        return value;
    }

    public static JSONArray getJSONArray(String key, JSONObject jsonObject) {
        JSONArray value = null;
        if(jsonObject == null)
            return value;

        try {
            value = jsonObject.getJSONArray(key);
            LogFunction("AppJSONHelper.getJSONArray(" + key + ") Succeeded:", value.toString());
        }
        catch (JSONException e) {
            LogFunction("AppJSONHelper.getJSONArray("+key+") failed:", e.toString());
        }

        return value;
    }

    public static JSONObject getJSONObject(String key, JSONObject jsonObject) {
        JSONObject value = null;
        if(jsonObject == null)
            return value;

        try {
            value = jsonObject.getJSONObject(key);
            LogFunction("AppJSONHelper.getJSONObject(" + key + ") Succeeded:", value.toString());
        }
        catch (JSONException e) {
            LogFunction("AppJSONHelper.getJSONObject("+key+") failed:", e.toString());
        }

        return value;
    }

    public static JSONObject getJSONObject(JSONArray jsonArray, int index) {
        JSONObject value = null;
        if (jsonArray == null || jsonArray.length() == 0)
            return value;

        try {
            value = jsonArray.getJSONObject(index);
            LogFunction("AppJSONHelper.getJSONObject from JSONArray Succeeded:", value.toString());
        }
        catch (JSONException e) {
            LogFunction("AppJSONHelper.getJSONObject from JSONArray failed:", e.toString());
        }

        return value;
    }

    public static ArrayList<String> getKeySet(JSONObject jsonObject) {
        ArrayList<String> keyList= null;
        if(jsonObject != null) {
            Iterator keyIterator = jsonObject.keys();
            if(keyIterator != null && keyIterator.hasNext() == true) {
                keyList = new ArrayList<String>();
                while (keyIterator.hasNext() == true) {
                    String key = (String) keyIterator.next();
                    keyList.add(key);
                }
            }
        }

        return keyList;
    }
}
