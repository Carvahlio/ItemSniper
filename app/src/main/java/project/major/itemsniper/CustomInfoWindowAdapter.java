package project.major.itemsniper;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by carva on 6/5/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final LayoutInflater inflater;
    private ArrayList<Item> items;

    public CustomInfoWindowAdapter(LayoutInflater mInflater,ArrayList<Item> items) {

        this.inflater = mInflater;
        this.items = items;
    }

    public CustomInfoWindowAdapter(LayoutInflater mInflater) {

        this.inflater = mInflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        //Inflates custom layout for marker info window
        final  View infoWin = inflater.inflate(R.layout.info_window_layout,null);

        //Instanciate and assign layout components
       ImageView itemPic = (ImageView) infoWin.findViewById(R.id.infoWinMarkerImage);

        TextView markerVendor = (TextView) infoWin.findViewById(R.id.infoWinMarkerVendor);

        TextView markerAvail = (TextView) infoWin.findViewById(R.id.infoWinMarkerAvail);


        //Set layout components

            Bitmap b = matchImage(marker);
            if(b != null){
                itemPic.setImageBitmap(b);
            }else{
                itemPic.setImageResource(R.drawable.utech_test);
            }

            markerVendor.setText(marker.getTitle());
            markerAvail.setText(marker.getSnippet());

        return infoWin;
    }

    private Bitmap matchImage(Marker m){
        double lat = m.getPosition().latitude;
        double lo = m.getPosition().longitude;

        for(Item i : this.items){

            if(i.getLatitude() == lat && i.getLongitude() == lo){
                try{
                    return i.getImage().createScaledBitmap(i.getImage(),200,200,false);
                }catch (Exception e) {
                    Log.i("Null",i.getName() + "was null");
                }
            }
        }
        return null;
    }


    public static Item matchImage(MyItem m,ArrayList<Item> items){
        double lat = m.getPosition().latitude;
        double lo = m.getPosition().longitude;

        for(Item i : items){

            if(i.getLatitude() == lat && i.getLongitude() == lo){
                try{
                    return i;
                }catch (Exception e) {
                    Log.i("Null",i.getName() + "was null");
                }
            }
        }
        return null;
    }

    public static Item matchImage(Marker m,ArrayList<Item> items){
        double lat = m.getPosition().latitude;
        double lo = m.getPosition().longitude;

        for(Item i : items){

            if(i.getLatitude() == lat && i.getLongitude() == lo){
                try{
                    return i;
                }catch (Exception e) {
                    Log.i("Null",i.getName() + "was null");
                }
            }
        }
        return null;
    }

}
