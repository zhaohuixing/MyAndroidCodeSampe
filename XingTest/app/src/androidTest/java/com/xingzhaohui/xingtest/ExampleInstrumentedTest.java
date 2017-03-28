package com.xingzhaohui.xingtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.xingzhaohui.xingtest.mainmodule.data.AppJSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.xingzhaohui.xingtest", appContext.getPackageName());
    }

    @Test
    public void testAppJSONHelper()  throws Exception {
        Log.d("AndroifText MyUnitTestLog:", "testAppJSONHelper is called");
        String jsonString = "{\"root\":[{\"child1\":1},{\"child2\":2},{\"child3\":3}]}";
        try {
            JSONObject root = new JSONObject(jsonString);
            assertNotNull(root);

            //Get Array
            JSONArray jsonArray = AppJSONHelper.getJSONArray("root", root);
            assertNotNull(jsonArray);
            assertEquals(jsonArray.length(), 3);
            Log.d("AndroifText MyUnitTestLog:", "testAppJSONHelper step 1 AppJSONHelper.getJSONArray call OK!");

            //Get Child1
            JSONObject child1 = AppJSONHelper.getJSONObject(jsonArray, 0);
            assertNotNull(child1);
            Log.d("AndroifText MyUnitTestLog:", "testAppJSONHelper step 2 AppJSONHelper.getJSONObject call with index 0 OK!");
            int v1 = AppJSONHelper.getInt("child1", child1);
            assertEquals(v1, 1);
            Log.d("AndroifText MyUnitTestLog:", "testAppJSONHelper step 3 AppJSONHelper.getInt call with key child1 OK!");

            //Get Child2
            JSONObject child2 = AppJSONHelper.getJSONObject(jsonArray, 1);
            assertNotNull(child2);
            Log.d("MyUnitTestLog:", "testAppJSONHelper step 4 AppJSONHelper.getJSONObject call with index 1 OK!");
            int v2 = AppJSONHelper.getInt("child2", child2);
            assertEquals(v2, 2);
            Log.d("AndroifText MyUnitTestLog:", "testAppJSONHelper step 5 AppJSONHelper.getInt call with key child2 OK!");

            //Get Child3
            JSONObject child3 = AppJSONHelper.getJSONObject(jsonArray, 2);
            assertNotNull(child3);
            Log.d("AndroifText MyUnitTestLog:", "testAppJSONHelper step 6 AppJSONHelper.getJSONObject call with index 2 OK!");
            int v3 = AppJSONHelper.getInt("child3", child3);
            assertEquals(v3, 3);
            Log.d("AndroifText MyUnitTestLog:", "testAppJSONHelper step 7 AppJSONHelper.getInt call with key child3 OK!");

        } catch (JSONException e) {
            Log.d("AndroifText  testAppJSONHelper case exception:", e.toString());
            assertFalse("AndroifText testAppJSONHelper handling failed!", false);
        }
    }

}
