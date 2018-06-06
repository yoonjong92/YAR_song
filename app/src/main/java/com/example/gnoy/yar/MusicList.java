package com.example.gnoy.yar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class MusicList extends AppCompatActivity implements View.OnClickListener{

    ListView mlistview;
    ArrayList<MusicDto> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_eric);

        Intent refIntent = this.getIntent();

        String loc = refIntent.getStringExtra("loc");
        String ID = refIntent.getStringExtra("ID");

        TextView Room_Name = (TextView) findViewById(R.id.selected_item_textview);
        Room_Name.setText(loc);

        mlistview = (ListView)findViewById(R.id.listview);

        //데이터를 저장하게 되는 리스트
        list = new ArrayList<>();
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");
        addMusicList("봄이 좋냐", "10cm");

        addMusicList("뿜뿜", "모모랜드");
        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        MyAdapter adapter = new MyAdapter(this,list);
        mlistview.setAdapter(adapter);

        //String url = "https://3jpiuxn3xl.execute-api.ap-northeast-2.amazonaws.com/yar/yar/song?room_id=" + ID;
        //MusicList.NetworkTask networkTask = new MusicList.NetworkTask(url, null);
        //networkTask.execute();

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String message = list.get(position).getMessage();


            }
        }) ;


        Button submit_button = (Button)findViewById(R.id.submit_button);

        submit_button.setOnClickListener(this);

    }

    public void onClick(View view) {
        View oParentView;
        String position;
        int p;
        switch (view.getId()){
            case R.id.submit_button:
                CustomDialog dialog = new CustomDialog(this);
                dialog.show();
                break;
            case R.id.show_message:
                System.out.println("qkjanscjkansfkjanmcnxc");
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

                break;
        }

    }

    public  void addMusicList(String Music_Title, String Music_Singer){

        MusicDto musicDto = new MusicDto();
        musicDto.setTitle(Music_Title);
        musicDto.setArtist(Music_Singer);
        musicDto.setMessage("호롤로로로로로로로로로로롤");
        musicDto.MessageClickListener = MusicList.this;
        musicDto.DeleteClickListener = MusicList.this;
        list.add(musicDto);
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
                String ID = "" + ItemObject.get("ID");
                String artist = "" + ItemObject.get("artist");
                String message = "" + ItemObject.get("message");
                MusicDto music = new MusicDto();
                music.setId(ID);
                music.setTitle(title);
                music.setArtist(artist);
                music.setMessage(message);
                music.MessageClickListener = MusicList.this;
                music.DeleteClickListener = MusicList.this;

                list.add(music);
            }

            return "asfaflk";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String item = s;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
            Toast.makeText(MusicList.this, item, Toast.LENGTH_SHORT).show();
        }
    }
}
