package com.example.gnoy.yar;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.HttpParams;

import static java.lang.Boolean.TRUE;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        //user input을 받아 변수에 저장
        final EditText locationText = (EditText) findViewById(R.id.locationText);
        final EditText titleText = (EditText) findViewById(R.id.titleText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText contactText = (EditText) findViewById(R.id.contactText);

        //Button input
        Button registerButton = (Button) findViewById(R.id.registerButton);

        //when button clicked
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //HttpClient client = new DefaultHttpClient();
                HttpClient httpClient = HttpClientBuilder.create().build();
                final HttpPost post = new HttpPost("https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room");

                String roomLocation = locationText.getText().toString();
                String roomTitle = titleText.getText().toString();              //name
                String roomPassword = passwordText.getText().toString();        //password
                String timestamp = contactText.getText().toString();
                //int timestamp = Integer.parseInt(contactText.getText().toString());   //timestamp
                //long timestamp = System.currentTimeMillis();

                try {
                    ArrayList<NameValuePair> nameValues = new ArrayList<NameValuePair>();
                    nameValues.add(new BasicNameValuePair("name", roomTitle));
                    nameValues.add(new BasicNameValuePair("password", roomPassword));
                    nameValues.add(new BasicNameValuePair("timestamp", timestamp));       //timestamp needed

                    post.setEntity(new UrlEncodedFormEntity(nameValues));
                    HttpResponse response = httpClient.execute(post);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //boolean success = TRUE;
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("방 만들기에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("방 만들기에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }
                        //catch(JSONException e){
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                //RegisterRequest를 call하고 parameter를 넘겨서
                //queue에 넣고 전송
                RegisterRequest registerRequest = new RegisterRequest(roomTitle, roomPassword, timestamp, responseListener);
                //RegisterRequest registerRequest = new RegisterRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
                */
            }
        });
    }
}
