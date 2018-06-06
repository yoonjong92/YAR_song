package com.example.gnoy.yar;

import android.net.wifi.p2p.WifiP2pManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest{
    final static private String URL = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room";
    private Map<String, String> parameters;

    public DeleteRequest(String id, String password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put(id, "aaa");
        parameters.put(password, "1111");
    }
    @Override
    public  Map<String, String> getParams() {
        return parameters;
    }
}
