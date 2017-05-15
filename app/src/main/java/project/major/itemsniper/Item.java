package project.major.itemsniper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import org.json.JSONObject;

/**
 * Created by Kurt on 5/5/2017.
 */
public class Item implements Response.Listener<Bitmap>,Response.ErrorListener {

    //Details
    private String name;
    private String description;
    private String distributedBy;
    private int price;
    private String category;
    private String id;
    private String vendorId;
    private double longitude;
    private double latitude;
    private int attempts = 0;
    private Context context;

    //Where to go on clicking the item
    private String clickUrl;

    //Image
    private Bitmap image;
    private int drawable_id;
    private Drawable drawable;
    private String imageUrl;

    ///CONSTRUCTORS///////
    public Item(String name,String clickUrl,@Nullable Bitmap image,int price){

        this.name = name;
        this.clickUrl = clickUrl;
        this.image = image;
        this.price = price;
    }


    private void getImageBitmap(){

        if(this.attempts >= 3){
            return;

        }

        ImageRequest request = new ImageRequest("http://"+this.imageUrl,this,100,100, ImageView.ScaleType.FIT_CENTER,null,this);
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(request);
    }

    public Item(JSONObject o,Context c){

        try{
            this.price = o.getInt("price");
            this.category = o.getString("product_category");
            this.id = o.getString("product_id");
            this.description = o.getString("description");
            this.name = o.getString("product_name");
            this.longitude = o.getDouble("longitude");
            this.latitude = o.getDouble("latitude");
            this.vendorId = o.getString("vendor_id");
            this.imageUrl = o.getString("url");
            this.context = c;

            //Load the bitmap for the image
            getImageBitmap();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Item(){

    }
    public Item(String name,String clickUrl,@Nullable int drawable_id,int price){

        this.name = name;
        this.clickUrl = clickUrl;
        this.drawable_id = drawable_id;
        this.price = price;
    }
    public Item(String name,String clickUrl,@Nullable Drawable drawable,int price){

        this.name = name;
        this.clickUrl = clickUrl;
        this.drawable = drawable;
        this.price = price;
    }


/////////////////////// GETTERS AND SETTERS/////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDistributedBy() {
        return distributedBy;
    }

    public void setDistributedBy(String distributedBy) {
        this.distributedBy = distributedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDrawable_id() {
        return drawable_id;
    }

    public void setDrawable_id(int drawable_id) {
        this.drawable_id = drawable_id;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        error.printStackTrace();
        attempts++;
        getImage();

    }
    @Override
    public void onResponse(Bitmap response) {
        this.image = response;
        Log.i("Success","got an image");
    }

}
