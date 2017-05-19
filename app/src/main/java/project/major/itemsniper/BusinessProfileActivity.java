package project.major.itemsniper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import project.major.itemsniper.CustomPagerAdapter;
import project.major.itemsniper.Item;
import project.major.itemsniper.ItemPictureFragment;
import project.major.itemsniper.ItemRequest;
import project.major.itemsniper.R;

/**
 * Created by Kurt on 5/14/2017.
 */
public class BusinessProfileActivity extends AppCompatActivity {

    private Button upload;
    private ViewPager pager;
    private ImageView profilePic;
    private CustomPagerAdapter adapter;
    private ArrayList<Fragment> products;
    private ArrayList<String> productUrls;
    private String id;
    private ArrayList<Bitmap> display_pic_bitmaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_profile_layout);
    }

    private void init(){

        this.upload = (Button)findViewById(R.id.upload);
        this.profilePic = (ImageView)findViewById(R.id.profile_pic);
        this.pager = (ViewPager)findViewById(R.id.business_pager);
        this.products = new ArrayList<>();
        this.adapter = new CustomPagerAdapter(getSupportFragmentManager(),this.products);
        this.pager.setAdapter(this.adapter);

    }

    private void downloadProductUrls(){

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response:", response);
                try{
                    JSONObject o = new JSONObject(response);
                    JSONArray rows = o.getJSONArray("rows");

                    for(int i = 0; i < rows.length();i++){
                        JSONObject x = rows.getJSONObject(i);
                        productUrls.add(x.getString("url"));
                    }
                    loadDisplayPics();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };

        HashMap<String,String> params = new HashMap<>();
        params.put("product_id",this.id);
        params.put("fordisplay", "true");

        ItemRequest request = new ItemRequest(params,listener,errorListener);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    //LOAD THE ACTUAL IMAGES FROM THE DISPLAY PIC URLS
    private void loadDisplayPics(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final int count = 0;
        for(String url : productUrls){

            Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap b) {
                    display_pic_bitmaps.add(b);
                    int[] dimens = Item.scaleProportional(b, 3, 500, 500);
                    products.add(ItemPictureFragment.creatInstance(b.createScaledBitmap(b, dimens[0], dimens[1], false)));
                    adapter.setDataSource(products);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            };
            ImageRequest request = new ImageRequest(url,listener,0,0, ImageView.ScaleType.FIT_CENTER,null,errorListener);
            queue.add(request);
        }
    }
}
