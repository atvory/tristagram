package com.example.tristagram.connections;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class Volleys {
    private static final String IP_SERVIDOR = "192.168.100.31";
    private static final String url ="http://"+IP_SERVIDOR+"/tristagramimages/";

    public static void sendPhotoVolley(String image, String name, Context context) {
        Thread tr = new Thread(new Runnable() {
            @Override
            public void run() {
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                protected Map<String, String> getParams(){
                                    Map<String,String> imageMap = new HashMap<String, String>();
                                    imageMap.put(name,image);
                                    return imageMap;

                                }
                            };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            }
        });
        tr.run();
    }



}
