package project.major.itemsniper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Kurt on 5/12/2017.
 */
public class Search {
    ///////!!!!!THIS IS WHERE THE ACTUAL SEARCHING IS DONE!!!!
    public static void searchForItem(final String query,
                                      Response.Listener<String> listener,
                                      Response.ErrorListener errorListener,
                                      final Context c){

        Log.i("SEARCH:", "Preparing search.....");
        HashMap params = new HashMap<String,String>();
        params.put("query",query);
        //Create an item request
        ItemRequest getItem = new ItemRequest(params,listener,errorListener);

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(getItem);
        Log.i("SEARCH:", "Searching please wait for response.....");
    }
}
