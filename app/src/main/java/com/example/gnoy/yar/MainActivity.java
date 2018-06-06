package com.example.gnoy.yar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //roomlist
    private ListView listView;
    private RoomListAdapter adapter;
    private List<Room> roomList;

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

        //새로고침
        Button refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //execute task that receive room list from server
                //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //builder.setMessage("서버에서 room list를 받아오세요")
                //       .show();


                // URL 설정.
                String url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/room";

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        roomList = new ArrayList<Room>();

        roomList.add(new Room("카페 그랑", "챔스 토토", "20명"));
        roomList.add(new Room("퀴즈노스", "지금은", "10명"));
        roomList.add(new Room("라쿠치나", "새벽 12시", "11명"));
        roomList.add(new Room("비비큐", "망했다", "22명"));
        roomList.add(new Room("투썸플레이스", "언제자냐", "3명"));

        //실제 json 파싱해서 이 부분에서 데이터 넣어주면 됨
        //revised needed

        adapter = new RoomListAdapter(getApplicationContext(), roomList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        //String item = String.valueOf(parent.getItemAtPosition(i));
                        //System.out.println(item);
                        //Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                        //방 id 넘긴다.
                        Intent musiclistIntent = new Intent(MainActivity.this, MusicList.class);
                        MainActivity.this.startActivity(musiclistIntent);
                    }
                }
        );
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

            String result; // 요청 결과를 저장할 변수.
            RequestURLHttpConnection requestHttpURLConnection = new RequestURLHttpConnection();
            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String item = s;
            Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
        }
    }

}
