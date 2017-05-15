package project.major.itemsniper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Kurt on 3/26/2017.
 */
public class SignInFragment extends Fragment {

    //Sign In page on screen buttons
    private Button signIn;
    private Button signInBusi;
    private Button consumerSignUp;
    private Button businessSignUp;
    private EditText email;
    private EditText password;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);


        if(preferences.getBoolean("loggedIn",false)){

            //Person is already logged in
            Intent i = new Intent(getContext(),MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }else{
            //Person not logged in
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //THIS IS WHERE THE UI IS LOADED
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.sign_in_fragment_layout, container, false);

        //Fetch on screen buttons
        signIn= (Button)view.findViewById(R.id.sign_in_button);
        signInBusi = (Button) view.findViewById(R.id.sign_in_business_button);
        consumerSignUp = (Button)view.findViewById(R.id.consumer_sign_up);
        businessSignUp = (Button)view.findViewById(R.id.business_sign_up);

        //Fetch fields
        email = (EditText)view.findViewById(R.id.email_edit_text);
        password = (EditText)view.findViewById(R.id.password_edit_text);

        //Register on click listeners for them
        registerButtonListeners();

        return view;
    }



    private void loadUserData(){

        Intent i = new Intent(getContext(),MainActivity.class);
        startActivity(i);
    }

    //Do this when the consumer button is clicked
    private void onConsumerClicked(){

        //Start the activity to register the consumer
        Intent intent = new Intent(getContext(),ConsumerRegisterActivity.class);
        startActivity(intent);
    }

    //Do this when the business button is clicked
    private void onBusinessClicked(){
        //Intent iMap = new Intent(getContext(),MapActivity.class);
        //Intent iUploadPic = new Intent(getContext(),UploadPictureActivity.class);
        Intent busi = new Intent(getContext(),ItemUploadActivity.class);
        Toast.makeText(getContext(),"business clicked", Toast.LENGTH_LONG).show();
        startActivity(busi);
        //startActivity(iUploadPic);
    }


    private void registerButtonListeners(){

        //Register the listener for sign in button
        if(signIn != null){

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //The action we take here
                    onSignInClicked();
                }
            });
        }

        if(signInBusi != null){
            signInBusi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(),"Business sign in pressed", Toast.LENGTH_SHORT).show();
                    loginInBusiness();
                }
            });
        }
        //Register listener for consumer button
        if(consumerSignUp != null){

            consumerSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onConsumerClicked();
                }
            });

        }

        //Register listener for bussiness button
        if(businessSignUp != null){

            businessSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBusinessClicked();
                }
            });
        }
    }

    //Do this when the sign in button is clicked
    private void onSignInClicked(){
        loginInUser();
    }

    //Log the user in
    private void loginInUser(){

        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject o = new JSONObject(response);
                    if(o.getBoolean("success")){


                        Log.i("Response",response);

                        //The username and passwords match
                        SharedPreferences.Editor preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                        JSONObject user = o.getJSONArray("user").getJSONObject(0);
                        preferences.putString("email",user.getString("email"));
                        preferences.putString("firstName", user.getString("FName"));
                        preferences.putString("lastName", user.getString("LName"));
                        preferences.putBoolean("loggedIn", true);
                        preferences.commit();

                        loadUserData();



                    }else{
                        Toast.makeText(getContext(),"Invalid Login",Toast.LENGTH_LONG).show();

                    }
                }catch(Exception e){
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

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String enteredEmail = email.getText().toString();
        String enteredPass = password.getText().toString();
        LoginRequest login = new LoginRequest(enteredEmail,enteredPass,listener,errorListener);
        queue.add(login);

    }
    private void loginInBusiness(){

        Response.Listener<String> listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject o = new JSONObject(response);
                    if(o.getBoolean("success")){


                        Log.i("Response",response);

                        //The username and passwords match
                        SharedPreferences.Editor preferences = getActivity().getSharedPreferences("businessLogin", Context.MODE_PRIVATE).edit();
                        JSONObject vendor = o.getJSONArray("rows").getJSONObject(0);
                        preferences.putString("email",vendor.getString("email"));
                        preferences.putString("name", vendor.getString("name"));
                        //preferences.putString("lastName", vendor.getString("LName"));
                        preferences.putBoolean("loggedIn", true);
                        preferences.commit();

                        loadUserData();



                    }else{
                        Toast.makeText(getContext(),"Invalid Login",Toast.LENGTH_LONG).show();

                    }
                }catch(Exception e){
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

        RequestQueue queue = Volley.newRequestQueue(getContext());

        String enteredEmail = email.getText().toString();
        String enteredPass = password.getText().toString();
        LoginRequestBusiness login = new LoginRequestBusiness(enteredEmail,enteredPass,listener,errorListener);
        queue.add(login);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
