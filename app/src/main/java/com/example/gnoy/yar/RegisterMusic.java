
package com.example.gnoy.yar;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.HttpParams;

import static java.lang.Boolean.TRUE;

public class RegisterMusic extends AppCompatActivity {

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
                System.out.print("here1");
                // URL 설정.
                String url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room";

                // AsyncTask를 통해 HttpURLConnection 수행.
                ContentValues values = new ContentValues();
                values.put("roomTitle",titleText.getText().toString());
                values.put("roomPassword",passwordText.getText().toString());
                values.put("timestamp",Long.toString(System.currentTimeMillis()));
                RegisterMusic.NetworkTask networkTask = new RegisterMusic.NetworkTask(url, values);
                networkTask.execute();
            }
        });

    }
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            //HttpClient client = new DefaultHttpClient();
            HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpPost post = new HttpPost(url);

            post.addHeader( "Content-Type" , "application/json" );

            String roomTitle = values.getAsString("roomTitle");              //name
            String roomPassword = values.getAsString("roomPassword");         //password
            String timestamp = values.getAsString("timestamp"); ;



            try {
                ArrayList<NameValuePair> nameValues = new ArrayList<NameValuePair>();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("name", roomTitle);
                jsonObject.put("password", roomPassword);
                jsonObject.put("timestamp", timestamp);
                String json = jsonObject.toString();

                post.setEntity(new StringEntity(json, "UTF-8"));
                HttpResponse response = httpClient.execute(post);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null;) {
                    builder.append(line).append("\n");
                }
                JSONTokener tokener = new JSONTokener(builder.toString());
                JSONObject finalResult = new JSONObject(tokener);
                System.out.println(finalResult.toString());
                String message = finalResult.getJSONObject("body").getString("message");
                if (message.equals("success")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(RegisterMusic.this);
                            dialogbuilder.setMessage("방 만들기에 성공했습니다.")
                                    .setNegativeButton("확인", null)
                                    .create()
                                    .show();
                        }
                    });

                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(RegisterMusic.this);
                            dialogbuilder.setMessage("방 만들기에 실패했습니다.")
                                    .setNegativeButton("확인", null)
                                    .create()
                                    .show();
                        }
                    });
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return "asfaflk";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}