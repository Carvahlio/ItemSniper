package project.major.itemsniper;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by carva on 10/5/2017.
 */

public class ParserTask extends AsyncTask<String,Integer, List<List<HashMap<String,String>>>>{

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
        JSONObject jObject;
        List<List<HashMap<String,String>>> routes = null;

        try{
            jObject = new JSONObject(jsonData[0]);
            Log.d("ParserTask",jsonData[0]);
            DataParser parser = new DataParser();
            Log.d("ParserTask", parser.toString());

            routes = parser.parse(jObject);
            Log.d("ParserTask","Executing routes");
            Log.d("ParserTask",routes.toString());
        }catch (Exception e){
            Log.d("ParserTask", e.toString());
            e.printStackTrace();
        }
        return routes;
    }

    protected void onPostExecute(List<List<HashMap<String,String>>>result){
       try{
           ArrayList<LatLng> points;
           PolylineOptions lineOptions = null;

           // Traversing through all the routes
           for (int i = 0; i < result.size(); i++) {
               points = new ArrayList<>();
               lineOptions = new PolylineOptions();

               // Fetching i-th route
               List<HashMap<String, String>> path = result.get(i);

               // Fetching all the points in i-th route
               for (int j = 0; j < path.size(); j++) {
                   HashMap<String, String> point = path.get(j);

                   double lat = Double.parseDouble(point.get("lat"));
                   double lng = Double.parseDouble(point.get("lng"));
                   LatLng position = new LatLng(lat, lng);

                   points.add(position);
               }

               // Adding all the points in the route to LineOptions
               lineOptions.addAll(points);
               lineOptions.width(10);
               lineOptions.color(Color.RED);

               Log.d("onPostExecute","onPostExecute lineoptions decoded");

           }

           // Drawing polyline in the Google Map for the i-th route
           if(lineOptions != null) {
               MapActivity.polyLine = MapActivity.mMap.addPolyline(lineOptions);

               //MapActivity.polylines.add(MapActivity.mMap.addPolyline(lineOptions));
           }
           else {
               Log.d("onPostExecute","without Polylines drawn");
           }

       }catch (Exception e){
           e.printStackTrace();
       }

    }
}
