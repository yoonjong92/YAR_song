package com.example.gnoy.yar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MusicList extends AppCompatActivity implements View.OnClickListener{

    ListView mlistview;
    ArrayList<MusicDto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_eric);

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


        //리스트뷰의 어댑터를 지정해준다.
        mlistview.setAdapter(adapter);

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String message = list.get(position).getMessage();

            }
        }) ;


        Button result_button = (Button)findViewById(R.id.result_button);

        result_button.setOnClickListener(this);

    }

    public void onClick(View view) {
        CustomDialog dialog = new CustomDialog(this);
        dialog.show();
    }

    public  void addMusicList(String Music_Title, String Music_Singer){

        MusicDto musicDto = new MusicDto();
        musicDto.setTitle(Music_Title);
        musicDto.setArtist(Music_Singer);
        musicDto.setMessage("호롤로로로로로로로로로로롤");
        list.add(musicDto);
    }
}
