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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.client.methods.HttpEntityEnclosingRequestBase;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class DeleteDialog extends Dialog implements View.OnClickListener{
    private static final int LAYOUT = R.layout.delete_dialog;

    private Context context;
    private String room_id;
    private String song_id;
    private String url;
    private String type;

    private EditText editPassword;

    private Button delete_cancel;
    private Button delete_request;
    private boolean RorS;
    private String Result;

    public DeleteDialog(@NonNull Context context, String id, boolean RorS) {
        super(context);
        this.context = context;
        this.RorS = RorS;
        this.Result = "fail";
        if(RorS) {
            this.room_id = id;
            this.url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room";
            this.type = "신청방";
        }
        else {
            this.song_id = id;
            this.url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/song";
            this.type = "신청곡";
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        TextView delete_text = (TextView) findViewById(R.id.delete_text);
        if(RorS)
            delete_text.setText("신청방 삭제하기");
        else
            delete_text.setText("음악 삭제하기");

        editPassword = (EditText) findViewById(R.id.delete_Password);

        delete_cancel = (Button) findViewById(R.id.delete_cancel);
        delete_request = (Button) findViewById(R.id.delete_request);

        delete_cancel.setOnClickListener(this);
        delete_request.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_cancel:
                cancel();
                break;
            case R.id.delete_request:
                // AsyncTask를 통해 HttpURLConnection 수행.
                ContentValues values = new ContentValues();
                if(RorS)
                    values.put("room_id", room_id);
                else
                    values.put("song_id", song_id);
                values.put("password",editPassword.getText().toString());
                DeleteDialog.NetworkTask networkTask = new DeleteDialog.NetworkTask(url, values);
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
            final HttpDeleteWithBody delete = new HttpDeleteWithBody(url);

            delete.addHeader( "Content-Type" , "application/json" );
            String dID;
            if(RorS)
                dID = values.getAsString("room_id");
            else
                dID = values.getAsString("song_id");
            String password = values.getAsString("password");

            try {
                System.out.print("here1");
                ArrayList<NameValuePair> nameValues = new ArrayList<NameValuePair>();

                JSONObject jsonObject = new JSONObject();
                if(RorS)
                    jsonObject.put("room_id", dID);
                else
                    jsonObject.put("song_id", dID);
                jsonObject.put("password", password);
                String json = jsonObject.toString();

                delete.setEntity(new StringEntity(json, "UTF-8"));
                HttpResponse response = httpClient.execute(delete);
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
                    if (message.equals("delete success")) {
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
                                dialogbuilder.setMessage(type + " 삭제에 성공했습니다.")
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
                                dialogbuilder.setMessage(type + " 삭제에 실패했습니다.")
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
                            dialogbuilder.setMessage(type + " 삭제에 실패했습니다.")
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
            System.out.println(s);
            if(s.equals("success"))
                DeleteDialog.this.cancel();
        }
    }

    class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";
        public String getMethod() { return METHOD_NAME; }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }
        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }
        public HttpDeleteWithBody() { super(); }
    }
}