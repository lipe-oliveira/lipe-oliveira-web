package com.behl.cdm_02;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HerokuService extends Activity{

    final String url = "https://bibiboom-backend.herokuapp.com";

    public Context context;

    public String[] RESPONSE = new String[3];

    RequestQueue queue;

    public HerokuService(Context context) {
        queue = Volley.newRequestQueue(context);
        this.context = context;

    };

    public String[] get_response(){
        return this.RESPONSE;
    }
    public interface  post_authenticate_interface{
        void onSucess();
    }

    public void post_authenticate(final String email, final String passw, final post_authenticate_interface callback) {

        Log.i("CAMPOS", email + " " + passw);

        try {
            
            String link = url + "/users/authenticate";

            StringRequest postRequest = new StringRequest(Request.Method.POST, link,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);

                            try {
                                JSONObject jo = new JSONObject(response);

                                if(jo.has("user")){
                                    Log.i("aux", "Tem email!");
                                    Log.i("aux", String.valueOf(jo.get("user")));


                                    JSONObject jo_user = (JSONObject) jo.get("user");

                                    if(jo_user.has("name")){
                                        Log.i("aux", "Tem name!");

                                        RESPONSE[0] = jo_user.get("name").toString();
                                        RESPONSE[1] = jo_user.get("email").toString();
                                        RESPONSE[2] = passw;

                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            callback.onSucess();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null || error.networkResponse != null) {

                                VolleyLog.e("ERROR: ", error.getMessage());

                            }
                            // error
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", passw);

                    return params;
                }
            };

            queue.add(postRequest);


        } catch (Exception ex) {
            Log.i("EXCEPTION", ex.getMessage());
        }
    }


    public JsonObjectRequest get(String linked){
        try{
            final String link = url + linked;

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, link, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response: ", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Response error: ", error.getLocalizedMessage());
                        }
                    });
            return getRequest;
        }
        catch (Exception error){
            return null;
        }
    }

    public StringRequest post_register(final String name, final String email, final String passw) {
        String link = url + "/users/register";

        try{
            StringRequest postRequest = new StringRequest(Request.Method.POST, link,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.i("Error.Response", Objects.requireNonNull(error.getLocalizedMessage()));
                        }
                    }
            )
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", passw);

                    Sqlite sql = new Sqlite(context);
                    sql.insereDado(name, email, passw);


                    return params;
                }
            };
            queue.add(postRequest);

            Log.i("post ", postRequest.toString());

            return postRequest;
        }
        catch (Exception ex){
            Log.i("EXCEPTION", ex.getMessage());
        }

        return null;
    }



}
