package com.example.gnoy.yar;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class RegisterActivity extends Dialog implements View.OnClickListener{
    private static final int LAYOUT = R.layout.activity_register;

    private Context context;
    private EditText nameText;
    private EditText messageText;
    private EditText passwordText;

    private String Result;

    public RegisterActivity(@NonNull Context context) {
        super(context);
        this.context = context;
        this.Result = "fail";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //user input을 받아 변수에 저장
        nameText = (EditText) findViewById(R.id.roomTitle);
        messageText = (EditText) findViewById(R.id.roomMessage);
        passwordText = (EditText) findViewById(R.id.roomPassword);

        //Button input
        Button registerButton = (Button) findViewById(R.id.create_submit);
        Button closeButton = (Button) findViewById(R.id.create_cancel);

        registerButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_cancel:
                cancel();
                break;
            case R.id.create_submit:
                System.out.print("here1");
                // URL 설정.
                String url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room";
                // AsyncTask를 통해 HttpURLConnection 수행.
                ContentValues values = new ContentValues();
                values.put("roomTitle",nameText.getText().toString());
                values.put("roomMessage",messageText.getText().toString());
                values.put("roomPassword",passwordText.getText().toString());
                values.put("timestamp",Long.toString(System.currentTimeMillis()));
                RegisterActivity.NetworkTask networkTask = new RegisterActivity.NetworkTask(url, values);
                networkTask.execute();
                break;
        }
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
            String roomMessage = values.getAsString("roomMessage");              //message
            String roomPassword = values.getAsString("roomPassword");         //password
            String timestamp = values.getAsString("timestamp"); ;



            try {
                ArrayList<NameValuePair> nameValues = new ArrayList<NameValuePair>();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("name", roomTitle);
                jsonObject.put("message", roomMessage);
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
                try {
                    String message = finalResult.getJSONObject("body").getString("message");
                    if (message.equals("success")) {
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                                dialogbuilder.setMessage("신청방 생성에 성공했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }, 0);
                        Result = "success";
                    } else {
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                                dialogbuilder.setMessage("신청방 생성에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }, 0);
                    }
                }
                catch (JSONException e){
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                            dialogbuilder.setMessage("신청방 생성에 실패했습니다.")
                                    .setNegativeButton("확인", null)
                                    .create()
                                    .show();
                        }
                    }, 0);
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


            return Result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("success"))
                RegisterActivity.this.cancel();
        }
    }

}