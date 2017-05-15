package project.major.itemsniper;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kurt on 5/1/2017.
 */

//Base class that registration activities inherit from
public abstract class RegisterActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener {

    //the url to the php script to register the user in the database
    protected String REGISTER_URL = "http://www.topnhotch.com/itemsniper/Register.php";

    private RequestQueue queue;
    private Response.Listener listener;
    private Response.ErrorListener errorListener;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.queue = Volley.newRequestQueue(getApplicationContext());

        this.listener = this;
        this.errorListener = this;

    }

    protected void changeUrl(String url){
        this.REGISTER_URL = url;
    }

    protected void registerUser(HashMap<String,String> params){

        RegisterRequest request = new RegisterRequest(params,this,this);
        this.queue.add(request);
    }

    private class RegisterRequest extends StringRequest{

        private Map<String,String> params;

        public RegisterRequest(HashMap<String,String> p,Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST, REGISTER_URL, listener, errorListener);
            params = p;
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return this.params;
        }
    }
}
