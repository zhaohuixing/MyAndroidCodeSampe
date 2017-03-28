package com.xingzhaohui.xingtest.mainmodule.model;

/**
 * The Web Service Manager encapsulating RESTful API from android-async-http library
 * This is as Model in MVC architecture
 *
 * Created by zhaohuixing.
 */

import com.loopj.android.http.*;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class AppWebServiceManager /*implements RESTAPI_AppDXProductListQueryHandler.RESTAPI_AppDXProductListReceiver,
                                    RESTAPI_AppDXProductProfileQueryHandler.RESTAPI_AppDXProductProfileReceiver*/ {

    private AsyncHttpClient m_RESTfulClient;
    private AppWebServiceDelegate m_Delegate = null;

    public static String GetCurrentURL() /*GetAppDirectMarketBaseURL()*/ {
        return "https://devices.byappdirect.com/api/marketplace/v1/";
    }
/*
    public static String GetAppDirectProductFileBaseURL() {
        return "https://devices.byappdirect.com/api/marketplace/v1/products/";
    }

    public static String GetAppDirectProductListBaseURL() {
        return GetAppDirectMarketBaseURL() + "listing";
    }

    public static String GetAppDirectProductFileURL(String productID) {
        return GetAppDirectProductFileBaseURL() + productID;
    }

*/
    public AppWebServiceManager(AppWebServiceDelegate delegate) {
        m_RESTfulClient = new AsyncHttpClient();
        m_Delegate = delegate;
    }
/*
    public void QueryProductList(RequestParams params) {
        String getRequest = WebServiceManager.GetAppDirectProductListBaseURL();
        RESTAPI_AppDXProductListQueryHandler getHandler = new RESTAPI_AppDXProductListQueryHandler(this);
        m_RESTfulClient.get(getRequest, params, getHandler);
    }

    public void QueryProductProfile(String productID) {
        String getRequest = WebServiceManager.GetAppDirectProductFileURL(productID);
        RESTAPI_AppDXProductProfileQueryHandler getHandler = new RESTAPI_AppDXProductProfileQueryHandler(this);
        m_RESTfulClient.get(getRequest, null, getHandler);
    }
*/
    /*
    * RESTAPI_AppDXProductListQueryHandler.AppDXProductListRecevier method:
    * Receive and process the online product list REST GET result
    *
    * */
//    @Override
/*    public void onReceiveProductList(JSONArray responses) {
        if(m_Delegate != null)
            m_Delegate.onProductListLoaded(responses);
    }
*/
    /*
    * RESTAPI_AppDXProductListQueryHandler.AppDXProductListRecevier method:
    * Receive and process the online product list REST GET result
    *
    * */
//    @Override
/*    public void onReceiveProductList(JSONObject response) {
        //Log.d("product list JSONObject:", response.toString());
    }
*/
    /*
    * RESTAPI_AppDXProductListQueryHandler.AppDXProductListRecevier method:
    * Handle the online product list REST GET query failure
    *
    * */
//    @Override
/*    public void onProductListFailed() {

    }
*/
    /*
    * RESTAPI_AppDXProductProfileQueryHandler.RESTAPI_AppDXProductProfileReceiver method:
    * Receive and process the online product profile REST GET result
    *
    * */
//    @Override
/*    public void onReceiveProductProfile(JSONObject product) {
        if(m_Delegate != null)
            m_Delegate.onProductProfileLoaded(product);
    }
 */

    /*
    * RESTAPI_AppDXProductProfileQueryHandler.RESTAPI_AppDXProductProfileReceiver method:
    * Handle the online product profile REST GET query failure
    *
    * */
//    @Override
/*    public void onProductProfileFailed() {

    }
*/
    //
    //Delegate to receive webservice RESTful call result
    //
    public interface AppWebServiceDelegate {
        //void onProductListLoaded(JSONArray responses);
        //void onProductProfileLoaded(JSONObject product);
    }

}
