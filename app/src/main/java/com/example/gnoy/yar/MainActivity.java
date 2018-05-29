package com.example.gnoy.yar;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("서버에서 room list를 받아오세요")
                        .show();
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

        adapter = new RoomListAdapter(getApplicationContext(), roomList);
        listView.setAdapter(adapter);
    }
}
