package project.major.itemsniper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.major.itemsniper.Reusables.PopUp;

//Actvity to show the item
public class ItemActivity extends ViewActivity{

    private ImageView image;
    private TextView name;
    private TextView description;
    private TextView category;
    private String imageUrl;
    private TextView distributedBy;
    private String vendor_id;
    private TextView price;
    private String id;
    private ArrayList<Comment> comments;
    private ListView commentListView;
    private Button comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view_layout);

        initialize();
    }

    private void getExtras(){
        Intent i = getIntent();
        Bundle extras = i.getExtras();

        this.name.setText(i.getStringExtra("name"));
        this.description.setText(i.getStringExtra("description"));
        this.imageUrl = "http://"+i.getStringExtra("url");
        this.category.setText(i.getStringExtra("category"));
        this.distributedBy.setText(i.getStringExtra("distributed_by"));
        this.vendor_id = i.getStringExtra("vendor_id");
        this.price.setText(i.getIntExtra("price", -1)+"");
        this.id = i.getStringExtra("id");

        downloadProductImage();
        getComments();
    }

    private void initialize(){

        this.image = (ImageView)findViewById(R.id.item_view_image);
        this.name = (TextView)findViewById(R.id.item_name);
        this.description = (TextView)findViewById(R.id.item_description);
        this.category = (TextView)findViewById(R.id.item_category);
        this.price = (TextView)findViewById(R.id.item_pricee);
        this.distributedBy= (TextView)findViewById(R.id.item_vendor);
        this.comment = (Button)findViewById(R.id.leave_comment);

        this.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopUp.Manipulator m = new PopUp.Manipulator() {
                    @Override
                    public void manipulate(View v) {

                        Button b = (Button) v.findViewById(R.id.submit_comment);
                        final EditText e = (EditText)v.findViewById(R.id.comment_field);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //The username and passwords match
                                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);

                                String first = preferences.getString("firstName", "Anonymous");
                                String last = preferences.getString("lastName","Person");
                                submitComment(first+" "+last,e.getText().toString(),id);


                                Toast.makeText(getApplicationContext(), "Submitting...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };


                PopUp p = PopUp.createInstance(R.layout.leave_comment_layout, m, true);
                p.show(getSupportFragmentManager(), "Comment");

            }
        });

        getExtras();
    }

    private void downloadProductImage(){

        Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image.setImageBitmap(response.createScaledBitmap(response,300,300,false));
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        ImageRequest request = new ImageRequest(this.imageUrl,listener,0,0,ImageView.ScaleType.CENTER_CROP,null,errorListener);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    //
    private void getComments() {

        HashMap<String, String> params = new HashMap<>();
        params.put("get", "true");
        params.put("product_id", this.id);

       Response.Listener<String> listener = new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   Log.i("RESPONSE:",response);
                   JSONObject o = new JSONObject(response);

                   if (o.getBoolean("success")) {
                       JSONArray r = o.getJSONArray("rows");

                       comments = new ArrayList<>();


                       for (int i = 0; i < r.length(); i++) {

                           Comment x = new Comment(r.getJSONObject(i));
                           comments.add(x);

                       }

                       updateCommentList();
                   } else {
                       return;
                   }


               } catch (JSONException e) {
                   e.printStackTrace();

               }

           }
       };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };

            CommentRequest c = new CommentRequest(params, listener, errorListener);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(c);

        }


    private void updateCommentList(){

        commentListView = (ListView)findViewById(R.id.comments);
        commentListView.setAdapter(new CommentAdapter(this.comments, getApplicationContext()));
    }

    private void submitComment(String user,String description,String product_id){

        HashMap<String,String> params = new HashMap<>();

        params.put("add","true");
        params.put("user",user);
        params.put("description",description);
        params.put("product_id",product_id);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    Log.i("RESPONSE:",response);
                    JSONObject o = new JSONObject(response);

                    if (o.getBoolean("success")) {

                        Toast.makeText(getApplicationContext(),"Comment added",Toast.LENGTH_LONG).show();
                        getComments();

                    } else {
                        Toast.makeText(getApplicationContext(),"Failed to add comment",Toast.LENGTH_LONG).show();
                        return;
                    }
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

        CommentRequest c = new CommentRequest(params,listener,errorListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(c);
    }

    private void downloadVendorImage(){

    }
}
