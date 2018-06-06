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

public class CustomDialog extends Dialog implements View.OnClickListener{
    private static final int LAYOUT = R.layout.add_music;

    private Context context;
    private String room_id;

    private EditText editTitle;
    private EditText editArtist;
    private EditText editMessage;

    private Button add_cancel;
    private Button add_submit;

    public CustomDialog(@NonNull Context context, String room_id) {
        super(context);
        this.context = context;
        this.room_id = room_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editArtist = (EditText) findViewById(R.id.editArtist);
        editMessage = (EditText) findViewById(R.id.editMessage);

        add_cancel = (Button) findViewById(R.id.add_music_cancel);
        add_submit = (Button) findViewById(R.id.add_music_submit);

        add_cancel.setOnClickListener(this);
        add_submit.setOnClickListener(this);
        System.out.println(room_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_music_cancel:
                cancel();
                break;
            case R.id.add_music_submit:
                String url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/song";

                // AsyncTask를 통해 HttpURLConnection 수행.
                ContentValues values = new ContentValues();
                values.put("name",editTitle.getText().toString());
                values.put("room_id", room_id);
                values.put("artist",editArtist.getText().toString());
                values.put("message",editMessage.getText().toString());
                values.put("timestamp",Long.toString(System.currentTimeMillis()));
                CustomDialog.NetworkTask networkTask = new CustomDialog.NetworkTask(url, values);
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

            String musicTitle = values.getAsString("name");
            String ID = values.getAsString("room_id");
            String musicArtist = values.getAsString("artist");
            String musicMessage = values.getAsString("message");
            String timestamp = values.getAsString("timestamp");

            try {
                System.out.print("here1");
                ArrayList<NameValuePair> nameValues = new ArrayList<NameValuePair>();

                JSONObject jsonObject = new JSONObject();

                System.out.println(ID);

                jsonObject.put("name", musicTitle);
                jsonObject.put("room_id", ID);
                jsonObject.put("artist", musicArtist);
                jsonObject.put("message", musicMessage);
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
                                dialogbuilder.setMessage("음악 신청에 성공했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }, 0);
                    } else {
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                                dialogbuilder.setMessage("음악 신청에 실패했습니다.")
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
                            dialogbuilder.setMessage("음악 신청에 실패했습니다.")
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


            return "asfaflk";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}