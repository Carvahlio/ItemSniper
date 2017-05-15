package project.major.itemsniper;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kurt on 5/12/2017.
 */


public class QueryParser {

    //Parse the json results coming from the search
    public static ArrayList<Item> parseItemResults(String response,Context c){

        ArrayList<Item> items = new ArrayList<>();

        if(response == null || response.equalsIgnoreCase("")){
            return null;
        }
        try {
            JSONArray rows = new JSONObject(response).getJSONArray("rows");

            for(int i = 0; i < rows.length() ;i++){
                items.add(new Item(rows.getJSONObject(i),c));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }
}
