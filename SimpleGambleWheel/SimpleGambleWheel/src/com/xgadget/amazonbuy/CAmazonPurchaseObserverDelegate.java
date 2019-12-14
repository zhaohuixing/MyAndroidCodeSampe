/**
 * 
 */
package com.xgadget.amazonbuy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zhaohuixing
 *
 */
public interface CAmazonPurchaseObserverDelegate 
{
	public Context	GetContext();
    public SharedPreferences GetSharedPreferences(String key, int mode);
    public String GetCurrentUser();
    public void SetCurrentUser(String currentUser);
    public Context GetApplicationContext();
    public void UpdateAmazonPurchase();
}

