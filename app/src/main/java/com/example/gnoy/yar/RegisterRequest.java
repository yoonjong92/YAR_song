package com.example.gnoy.yar;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    /* register host domain address */
    // receive registration parameter
    // send parameter to host
    final static private String URL = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room";
    private Map<String, String> parameters;
    /*public RegisterRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }*/

    public RegisterRequest(String name, String password, String timestamp, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("password", password);
        parameters.put("timestamp", timestamp);
    }
    /*
    public RegisterRequest(Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
    }
    */

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
/*
    public Map<String, Integer> getParameters_t() {
        parameters_t.put("timestamp", 1231);
        parameters_t = new HashMap<>();
        return parameters_t;
    }
*/
}
