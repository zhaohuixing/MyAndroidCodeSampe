package com.xgadget.amazonbuy;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
//import android.util.Log;


import com.amazon.inapp.purchasing.GetUserIdResponse;
import com.amazon.inapp.purchasing.GetUserIdResponse.GetUserIdRequestStatus;
import com.amazon.inapp.purchasing.Item;
import com.amazon.inapp.purchasing.ItemDataResponse;
import com.amazon.inapp.purchasing.Offset;
import com.amazon.inapp.purchasing.PurchaseResponse;
import com.amazon.inapp.purchasing.PurchaseUpdatesResponse;
import com.amazon.inapp.purchasing.PurchasingManager;
import com.amazon.inapp.purchasing.Receipt;
import com.amazon.inapp.purchasing.SubscriptionPeriod;
import com.amazon.inapp.purchasing.BasePurchasingObserver;
import com.xgadget.XSGService.XSGAPIReleaseConfigure;

public class CAmazonPurchaseObserver extends BasePurchasingObserver 
{
	public static final int AMAZON_INAPP_INVALID = -1;
	public static final int AMAZON_INAPP_CONSUMABLE = 0;
	public static final int AMAZON_INAPP_ENTITLED = 1;
	public static final int AMAZON_INAPP_SUBSCRIPTION = 2;
    public static final String AMAZON_INAPP_TYPE_KEY = "AMAZON_INAPP_TYPE_KEY";
    public static final String AMAZON_INAPP_SKU_KEY = "AMAZON_INAPP_SKU_KEY";

	
	
    private static final String OFFSET = "offset";
//    private static final String START_DATE = "startDate";

	protected CAmazonPurchaseObserverDelegate		m_Delegate;
	
	public CAmazonPurchaseObserver(CAmazonPurchaseObserverDelegate	delegate) 
	{
		super(delegate.GetContext());
		// TODO Auto-generated constructor stub
		m_Delegate = delegate;
	}


    /**
     * Invoked once the observer is registered with the Puchasing Manager If the boolean is false, the application is
     * receiving responses from the SDK Tester. If the boolean is true, the application is live in production.
     * 
     * @param isSandboxMode
     *            Boolean value that shows if the app is live or not.
     */
    @Override
    public void onSdkAvailable(final boolean isSandboxMode) 
    {
        XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onSdkAvailable", "onSdkAvailable recieved: Response -" + isSandboxMode);
        PurchasingManager.initiateGetUserIdRequest();
    }

    /**
     * Invoked once the call from initiateGetUserIdRequest is completed.
     * On a successful response, a response object is passed which contains the request id, request status, and the
     * userid generated for your application.
     * 
     * @param getUserIdResponse
     *            Response object containing the UserID
     */
    @Override
    public void onGetUserIdResponse(final GetUserIdResponse getUserIdResponse) 
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onGetUserIdResponse", "onGetUserIdResponse recieved: Response -" + getUserIdResponse);
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onGetUserIdResponse", "RequestId:" + getUserIdResponse.getRequestId());
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onGetUserIdResponse", "IdRequestStatus:" + getUserIdResponse.getUserIdRequestStatus());
        new GetUserIdAsyncTask().execute(getUserIdResponse);
    }

    /**
     * Invoked once the call from initiateItemDataRequest is completed.
     * On a successful response, a response object is passed which contains the request id, request status, and a set of
     * item data for the requested skus. Items that have been suppressed or are unavailable will be returned in a
     * set of unavailable skus.
     * 
     * @param itemDataResponse
     *            Response object containing a set of purchasable/non-purchasable items
     */
    @Override
    public void onItemDataResponse(final ItemDataResponse itemDataResponse) 
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onItemDataRespons", "onItemDataResponse recieved");
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onItemDataRespons", "ItemDataRequestStatus" + itemDataResponse.getItemDataRequestStatus());
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onItemDataRespons", "ItemDataRequestId" + itemDataResponse.getRequestId());
        new ItemDataAsyncTask().execute(itemDataResponse);
    }

    /**
     * Is invoked once the call from initiatePurchaseRequest is completed.
     * On a successful response, a response object is passed which contains the request id, request status, and the
     * receipt of the purchase.
     * 
     * @param purchaseResponse
     *            Response object containing a receipt of a purchase
     */
    @Override
    public void onPurchaseResponse(final PurchaseResponse purchaseResponse) 
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onPurchaseResponse", "onPurchaseResponse recieved");
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onPurchaseResponse", "PurchaseRequestStatus:" + purchaseResponse.getPurchaseRequestStatus());
        new PurchaseAsyncTask().execute(purchaseResponse);
    }

    /**
     * Is invoked once the call from initiatePurchaseUpdatesRequest is completed.
     * On a successful response, a response object is passed which contains the request id, request status, a set of
     * previously purchased receipts, a set of revoked skus, and the next offset if applicable. If a user downloads your
     * application to another device, this call is used to sync up this device with all the user's purchases.
     * 
     * @param purchaseUpdatesResponse
     *            Response object containing the user's recent purchases.
     */
    @Override
    public void onPurchaseUpdatesResponse(final PurchaseUpdatesResponse purchaseUpdatesResponse) 
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onPurchaseUpdatesResponse", "onPurchaseUpdatesRecived recieved: Response -" + purchaseUpdatesResponse);
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onPurchaseUpdatesResponse", "PurchaseUpdatesRequestStatus:" + purchaseUpdatesResponse.getPurchaseUpdatesRequestStatus());
        XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.onPurchaseUpdatesResponse", "RequestID:" + purchaseUpdatesResponse.getRequestId());
        new PurchaseUpdatesAsyncTask().execute(purchaseUpdatesResponse);
    }

    /*
     * Helper method to print out relevant receipt information to the log.
     */
    private void printReceipt(final Receipt receipt) 
    {
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.printReceipt", String.format("Receipt: ItemType: %s Sku: %s SubscriptionPeriod: %s", receipt.getItemType(), receipt.getSku(), receipt.getSubscriptionPeriod()));
    }

    /*
     * Helper method to retrieve the correct key to use with our shared preferences
     */
    private String getKey(final String sku) 
    {
        /*if (sku.equals(m_Delegate.getResources().getString(R.string.consumable_sku))) {
            return ButtonClickerActivity.NUM_CLICKS;
        } else if (sku.equals(m_Delegate.getResources().getString(R.string.entitlement_sku_blue))) {
            return ButtonClickerActivity.BLUE_BUTTON;
        } else if (sku.equals(m_Delegate.getResources().getString(R.string.entitlement_sku_purple))) {
            return ButtonClickerActivity.PURPLE_BUTTON;
        } else if (sku.equals(m_Delegate.getResources().getString(R.string.entitlement_sku_green))) {
            return ButtonClickerActivity.GREEN_BUTTON;
        } else if (sku.equals(m_Delegate.getResources().getString(R.string.parent_subscription_sku)) ||
            sku.equals(m_Delegate.getResources().getString(R.string.child_subscription_sku_monthly))) {
            return ButtonClickerActivity.HAS_SUBSCRIPTION;
        } else {
            return "";
        }*/
    	
    	XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.getKey", sku);
    	
    	return sku;
    }

    private SharedPreferences getSharedPreferencesForCurrentUser() 
    {
        final SharedPreferences settings = m_Delegate.GetSharedPreferences(m_Delegate.GetCurrentUser(), Context.MODE_PRIVATE);
        return settings;
    }
    
    private SharedPreferences.Editor getSharedPreferencesEditor()
    {
        return getSharedPreferencesForCurrentUser().edit();
    }

    /*
     * Started when the Observer receives a GetUserIdResponse. The Shared Preferences file for the returned user id is
     * accessed.
     */
    private class GetUserIdAsyncTask extends AsyncTask<GetUserIdResponse, Void, Boolean> 
    {
        @Override
        protected Boolean doInBackground(final GetUserIdResponse... params) 
        {
            GetUserIdResponse getUserIdResponse = params[0];

            if (getUserIdResponse.getUserIdRequestStatus() == GetUserIdRequestStatus.SUCCESSFUL) 
            {
                final String userId = getUserIdResponse.getUserId();

                // Each UserID has their own shared preferences file, and we'll load that file when a new user logs in.
                m_Delegate.SetCurrentUser(userId);
                return true;
            } 
            else 
            {
                XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.GetUserIdAsyncTask.doInBackground", "onGetUserIdResponse: Unable to get user ID.");
                return false;
            }
        }

        /*
         * Call initiatePurchaseUpdatesRequest for the returned user to sync purchases that are not yet fulfilled.
         */
        @Override
        protected void onPostExecute(final Boolean result) 
        {
            super.onPostExecute(result);
            if (result) 
            {
                PurchasingManager.initiatePurchaseUpdatesRequest(Offset.fromString(m_Delegate.GetApplicationContext().getSharedPreferences(m_Delegate.GetCurrentUser(), Context.MODE_PRIVATE).getString(OFFSET, Offset.BEGINNING.toString())));
            }
        }
    }

    /*
     * Started when the observer receives an Item Data Response.
     * Takes the items and display them in the logs. You can use this information to display an in game
     * storefront for your IAP items.
     */
    private class ItemDataAsyncTask extends AsyncTask<ItemDataResponse, Void, Void> 
    {
        @Override
        protected Void doInBackground(final ItemDataResponse... params) 
        {
            final ItemDataResponse itemDataResponse = params[0];

            switch (itemDataResponse.getItemDataRequestStatus()) 
            {
            case SUCCESSFUL_WITH_UNAVAILABLE_SKUS:
                // Skus that you can not purchase will be here.
                for (final String s : itemDataResponse.getUnavailableSkus()) 
                {
                    XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.ItemDataAsyncTask.doInBackground.SUCCESSFUL_WITH_UNAVAILABLE_SKUS", "Unavailable SKU:" + s);
                }
            case SUCCESSFUL:
                // Information you'll want to display about your IAP items is here
                // In this example we'll simply log them.
                final Map<String, Item> items = itemDataResponse.getItemData();
                for (final String key : items.keySet()) 
                {
                    Item i = items.get(key);
                    XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.ItemDataAsyncTask.doInBackground.SUCCESSFUL", String.format("Item: %s\n Type: %s\n SKU: %s\n Price: %s\n Description: %s\n", i.getTitle(), i.getItemType(), i.getSku(), i.getPrice(), i.getDescription()));
                }
                break;
            case FAILED:
                // On failed responses will fail gracefully.
                XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.ItemDataAsyncTask.doInBackground.FAILED", "FAILED");
                break;

            }

            return null;
        }
    }

    /*
     * Started when the observer receives a Purchase Response
     * Once the AsyncTask returns successfully, the UI is updated.
     */
    private class PurchaseAsyncTask extends AsyncTask<PurchaseResponse, Void, Boolean> 
    {
        @Override
        protected Boolean doInBackground(final PurchaseResponse... params) 
        {
            final PurchaseResponse purchaseResponse = params[0];            
            final String userId = m_Delegate.GetCurrentUser();
            
            if (!purchaseResponse.getUserId().equals(userId)) 
            {
                // currently logged in user is different than what we have so update the state
                m_Delegate.SetCurrentUser(purchaseResponse.getUserId());                
                PurchasingManager.initiatePurchaseUpdatesRequest(Offset.fromString(m_Delegate.GetSharedPreferences(m_Delegate.GetCurrentUser(), Context.MODE_PRIVATE)
                                                                                   .getString(OFFSET, Offset.BEGINNING.toString())));                
            }
            //final SharedPreferences settings = getSharedPreferencesForCurrentUser();
            final SharedPreferences.Editor editor = getSharedPreferencesEditor();
            final Receipt receipt = purchaseResponse.getReceipt();
            String sku = receipt.getSku();
            switch (purchaseResponse.getPurchaseRequestStatus()) 
            {
            case SUCCESSFUL:
                /*
                 * You can verify the receipt and fulfill the purchase on successful responses.
                 */
                switch (receipt.getItemType()) 
                {
                	case CONSUMABLE:
                		{
                            editor.putInt(AMAZON_INAPP_TYPE_KEY, AMAZON_INAPP_CONSUMABLE);
                            editor.putString(AMAZON_INAPP_SKU_KEY, sku);
                		}
                		break;
                	case ENTITLED:
            			{
                            editor.putInt(AMAZON_INAPP_TYPE_KEY, AMAZON_INAPP_ENTITLED);
                            editor.putString(AMAZON_INAPP_SKU_KEY, sku);
            			}
                		break;
                	case SUBSCRIPTION:
        				{
                            editor.putInt(AMAZON_INAPP_TYPE_KEY, AMAZON_INAPP_SUBSCRIPTION);
                            editor.putString(AMAZON_INAPP_SKU_KEY, sku);
        				}
                    	break;
                }
                editor.commit();

                printReceipt(purchaseResponse.getReceipt());
                return true;
            case ALREADY_ENTITLED:
                /*
                 * If the customer has already been entitled to the item, a receipt is not returned.
                 * Fulfillment is done unconditionally, we determine which item should be fulfilled by matching the
                 * request id returned from the initial request with the request id stored in the response.
                 */
            		//final String requestId = purchaseResponse.getRequestId();
            		//editor.putBoolean(m_Delegate.requestIds.get(requestId), true);
            		//editor.commit();
    			{
                    editor.putInt(AMAZON_INAPP_TYPE_KEY, AMAZON_INAPP_CONSUMABLE);
                    editor.putString(AMAZON_INAPP_SKU_KEY, sku);
    			}
            	return true;
            case FAILED:
                /*
                 * If the purchase failed for some reason, (The customer canceled the order, or some other
                 * extraneous circumstance happens) the application ignores the request and logs the failure.
                 */
                XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.PurchaseAsyncTask.doInBackground", "Failed purchase for request");
                editor.putInt(AMAZON_INAPP_TYPE_KEY, AMAZON_INAPP_INVALID);
                return false;
            case INVALID_SKU:
                /*
                 * If the sku that was purchased was invalid, the application ignores the request and logs the failure.
                 * This can happen when there is a sku mismatch between what is sent from the application and what
                 * currently exists on the dev portal.
                 */
                XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.PurchaseAsyncTask.doInBackground", "Invalid Sku for request");
                editor.putInt(AMAZON_INAPP_TYPE_KEY, AMAZON_INAPP_INVALID);
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) 
        {
            super.onPostExecute(success);
            if (success) 
            {
                m_Delegate.UpdateAmazonPurchase();
            }
        }
    }

    /*
     * Started when the observer receives a Purchase Updates Response Once the AsyncTask returns successfully, we'll
     * update the UI.
     */
    private class PurchaseUpdatesAsyncTask extends AsyncTask<PurchaseUpdatesResponse, Void, Boolean> 
    {

        @Override
        protected Boolean doInBackground(final PurchaseUpdatesResponse... params) {
            final PurchaseUpdatesResponse purchaseUpdatesResponse = params[0];
            final SharedPreferences.Editor editor = getSharedPreferencesEditor();
            final String userId = m_Delegate.GetCurrentUser();
            if (!purchaseUpdatesResponse.getUserId().equals(userId)) 
            {
                return false;
            }
            /*
             * If the customer for some reason had items revoked, the skus for these items will be contained in the
             * revoked skus set.
             */
            for (final String sku : purchaseUpdatesResponse.getRevokedSkus()) 
            {
                XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.getKey", "Revoked Sku:" + sku);
                final String key = getKey(sku);
                editor.putBoolean(key, false);
                editor.commit();
            }

            switch (purchaseUpdatesResponse.getPurchaseUpdatesRequestStatus()) 
            {
            case SUCCESSFUL:
                SubscriptionPeriod latestSubscriptionPeriod = null;
                final LinkedList<SubscriptionPeriod> currentSubscriptionPeriods = new LinkedList<SubscriptionPeriod>();
                for (final Receipt receipt : purchaseUpdatesResponse.getReceipts()) 
                {
                    final String sku = receipt.getSku();
                    final String key = getKey(sku);
                    switch (receipt.getItemType()) 
                    {
                    case ENTITLED:
                        /*
                         * If the receipt is for an entitlement, the customer is re-entitled.
                         */
                        editor.putBoolean(key, true);
                        editor.commit();
                        break;
                    case SUBSCRIPTION:
                        /*
                         * Purchase Updates for subscriptions can be done in one of two ways:
                         * 1. Use the receipts to determine if the user currently has an active subscription
                         * 2. Use the receipts to create a subscription history for your customer.
                         * This application checks if there is an open subscription the application uses the receipts
                         * returned to determine an active subscription.
                         * Applications that unlock content based on past active subscription periods, should create
                         * purchasing history for the customer.
                         * For example, if the customer has a magazine subscription for a year,
                         * even if they do not have a currently active subscription,
                         * they still have access to the magazines from when they were subscribed.
                         */
                        final SubscriptionPeriod subscriptionPeriod = receipt.getSubscriptionPeriod();
                        final Date startDate = subscriptionPeriod.getStartDate();
                        /*
                         * Keep track of the receipt that has the most current start date.
                         * Store a container of duplicate subscription periods.
                         * If there is a duplicate, the duplicate is added to the list of current subscription periods.
                         */
                        if (latestSubscriptionPeriod == null ||
                            startDate.after(latestSubscriptionPeriod.getStartDate())) 
                        {
                            currentSubscriptionPeriods.clear();
                            latestSubscriptionPeriod = subscriptionPeriod;
                            currentSubscriptionPeriods.add(latestSubscriptionPeriod);
                        }
                        else if (startDate.equals(latestSubscriptionPeriod.getStartDate())) 
                        {
                            currentSubscriptionPeriods.add(receipt.getSubscriptionPeriod());
                        }

                        break;

                    }
                    printReceipt(receipt);
                }
                /*
                 * Check the latest subscription periods once all receipts have been read, if there is a subscription
                 * with an existing end date, then the subscription is not active.
                 */
          //      if (latestSubscriptionPeriod != null) 
          //      {
          //          boolean hasSubscription = true;
          //          for (SubscriptionPeriod subscriptionPeriod : currentSubscriptionPeriods) 
          //          {
          //              if (subscriptionPeriod.getEndDate() != null) 
          //              {
          //                  hasSubscription = false;
          //                  break;
          //              }
          //          }
          //          editor.putBoolean(ButtonClickerActivity.HAS_SUBSCRIPTION, hasSubscription);
          //          editor.commit();
          //      }

                /*
                 * Store the offset into shared preferences. If there has been more purchases since the
                 * last time our application updated, another initiatePurchaseUpdatesRequest is called with the new
                 * offset.
                 */
                final Offset newOffset = purchaseUpdatesResponse.getOffset();
                editor.putString(OFFSET, newOffset.toString());
                editor.commit();
                if (purchaseUpdatesResponse.isMore()) {
                    XSGAPIReleaseConfigure.LogDebugInfo("CAmazonPurchaseObserver.getKey", "Initiating Another Purchase Updates with offset: " + newOffset.toString());
                    PurchasingManager.initiatePurchaseUpdatesRequest(newOffset);
                }
                return true;
            case FAILED:
                /*
                 * On failed responses the application will ignore the request.
                 */
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) 
        {
            //super.onPostExecute(success);
            //if (success) 
           // {
           //     m_Delegate.update();
           // }
        }
    }
	
}
