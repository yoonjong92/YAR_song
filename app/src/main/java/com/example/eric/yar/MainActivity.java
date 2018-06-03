package com.example.eric.yar;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listview;
    ArrayList<MusicDto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView)findViewById(R.id.listview);

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
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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