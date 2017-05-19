package project.major.itemsniper;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kurt on 5/13/2017.
 */
public class CommentRequest extends StringRequest {

    private static String url = "http://www.topnhotch.com/itemsniper/Comment.php";
    private HashMap<String,String> params;
    public CommentRequest(HashMap<String,String> params,Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }
}
