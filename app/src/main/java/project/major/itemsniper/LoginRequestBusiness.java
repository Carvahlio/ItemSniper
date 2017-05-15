package project.major.itemsniper;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carva on 14/5/2017.
 */

public class LoginRequestBusiness extends StringRequest {
    public static String LOGIN_URL = "http://www.topnhotch.com/itemsniper/LoginBusiness.php";
    private HashMap<String,String> params;
    private String email;
    private String password;


    public LoginRequestBusiness(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST,LOGIN_URL, listener, errorListener);
        this.email=email;
        this.password=password;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        return params;
    }
}
