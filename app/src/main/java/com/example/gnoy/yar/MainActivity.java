package com.example.gnoy.yar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //roomlist
    private ListView listView;
    private RoomListAdapter adapter;
    private List<Room> roomList;
    String url;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //방만들기
        TextView registerButton = (TextView) findViewById(R.id.registerButton);

        //setOnClickListener는 버튼 누르면 동작하고, intent로 실행하는 activity 바꿀 수 있는 듯.
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        roomList = new ArrayList<Room>();

        url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room";

        roomList = new ArrayList<Room>();
        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new RoomListAdapter(getApplicationContext(), roomList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        Intent musiclistIntent = new Intent(MainActivity.this, MusicList.class);
                        String loc = roomList.get(i).roomLocation;
                        String ID = roomList.get(i).roomID;

                        musiclistIntent.putExtra("loc", loc);
                        musiclistIntent.putExtra("ID", ID);

                        MainActivity.this.startActivity(musiclistIntent);
                    }
                }
        );

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.NetworkTask networkTask = new MainActivity.NetworkTask(url, null);
                networkTask.execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    public void onClick(View view){
        View oParentView;
        String position;
        int p;
        switch (view.getId()) {
            case R.id.deleteR_button:
                oParentView = (View)view.getParent();
                position = (String) oParentView.getTag();
                p = Integer.parseInt(position);
                String ID = roomList.get(p).roomID;
                DeleteDialog dialog = new DeleteDialog(this, ID, true);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        MainActivity.NetworkTask networkTask = new MainActivity.NetworkTask(url, null);
                        networkTask.execute();
                    }
                });
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

            JSONArray ItemsArray; // 요청 결과를 저장할 변수.
            RequestURLHttpConnection requestHttpURLConnection = new RequestURLHttpConnection();
            ItemsArray = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.
            System.out.println(ItemsArray.size());
            roomList = new ArrayList<Room>();

            for (int i=0; i < ItemsArray.size(); i++){
                JSONObject ItemObject = (JSONObject) ItemsArray.get(i);
                String loc= "" + ItemObject.get("name");
                String ID = "" + ItemObject.get("room_id");

                Room tmpRoom = new Room(loc, "title", ID);
                tmpRoom.DeleteClickListener = MainActivity.this;

                roomList.add(tmpRoom);
            }

            return "asfaflk";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView = (ListView) findViewById(R.id.listView);
                    adapter = new RoomListAdapter(getApplicationContext(), roomList);
                    listView.setAdapter(adapter);
                }
            });
            Toast.makeText(MainActivity.this, "신청방 목록이 갱신되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

}
