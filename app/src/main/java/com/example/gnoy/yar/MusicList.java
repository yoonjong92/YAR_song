package com.example.gnoy.yar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public class MusicList extends AppCompatActivity implements View.OnClickListener{

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView mlistview;
    ArrayList<MusicDto> list;
    MyAdapter adapter;
    String ID;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_eric);

        Intent refIntent = this.getIntent();

        String name = refIntent.getStringExtra("name");
        String intro = refIntent.getStringExtra("intro");
        ID = refIntent.getStringExtra("ID");

        TextView Room_Name = (TextView) findViewById(R.id.selected_item_textview);
        Room_Name.setText(name);
        TextView Room_Intro = (TextView) findViewById(R.id.intro);
        Room_Intro.setText(intro);

        mlistview = (ListView)findViewById(R.id.listview);

        //데이터를 저장하게 되는 리스트
        list = new ArrayList<>();

        url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/song?room_id=" + ID;
        MusicList.NetworkTask networkTask = new MusicList.NetworkTask(url, null);
        networkTask.execute();

        MyAdapter adapter = new MyAdapter(this,list);
        mlistview.setAdapter(adapter);

        Button submit_button = (Button)findViewById(R.id.submit_button);
        submit_button.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MusicList.NetworkTask networkTask = new MusicList.NetworkTask(url, null);
                networkTask.execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void onClick(View view) {
        View oParentView;
        String position;
        int p;
        switch (view.getId()){
            case R.id.submit_button:
                CustomDialog dialog = new CustomDialog(this, ID);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        MusicList.NetworkTask networkTask = new MusicList.NetworkTask(url, null);
                        networkTask.execute();
                    }
                });
                break;
            case R.id.show_message:
                oParentView = (View)view.getParent();
                position = (String) oParentView.getTag();
                p = Integer.parseInt(position);
                String message = list.get(p).getMessage();
                Toast.makeText(MusicList.this, message, Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_button:
                oParentView = (View)view.getParent();
                position = (String) oParentView.getTag();
                p = Integer.parseInt(position);
                String id = list.get(p).getId();
                DeleteDialog dialogD = new DeleteDialog(this, id, false);
                dialogD.show();
                dialogD.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        MusicList.NetworkTask networkTask = new MusicList.NetworkTask(url, null);
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
            list = new ArrayList<>();

            for (int i=0; i < ItemsArray.size(); i++){
                JSONObject ItemObject = (JSONObject) ItemsArray.get(i);
                String title= "" + ItemObject.get("name");
                String ID = "" + ItemObject.get("song_id");
                String artist = "" + ItemObject.get("artist");
                String message = "" + ItemObject.get("message");
                String timeStamp = "" + ItemObject.get("timestamp");
                System.out.println(timeStamp);
                Double tmp = Double.parseDouble(timeStamp);
                long time = Math.round(tmp);
                MusicDto music = new MusicDto();
                music.setId(ID);
                music.setTitle(title);
                music.setArtist(artist);
                music.setMessage(message);
                music.setTime(time);
                music.MessageClickListener = MusicList.this;
                music.DeleteClickListener = MusicList.this;
                list.add(music);
            }

            list.sort(new Comparator<MusicDto>() {
                @Override
                public int compare(MusicDto o1, MusicDto o2) {
                    if (o1.getTime() < o2.getTime()) return -1;
                    else if(o1.getTime() == o2.getTime()) return 0;
                    else return 1;
                }
            });

            return "asfaflk";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String item = s;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mlistview = (ListView)findViewById(R.id.listview);
                    MyAdapter adapter = new MyAdapter (MusicList.this,list);
                    mlistview.setAdapter(adapter);
                }
            });
            Toast.makeText(MusicList.this, "신청곡 목록이 갱신되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
