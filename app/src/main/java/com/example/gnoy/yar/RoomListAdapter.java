package com.example.gnoy.yar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {
    private Context context;
    private List<Room> roomList;

    public RoomListAdapter(Context context, List<Room> roomList){
        this.context = context;
        this.roomList = roomList;
    }
    @Override
    public int getCount() {
        return roomList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.room, null);
        TextView roomName = (TextView) v.findViewById(R.id.roomName);
        TextView roomIntro = (TextView) v.findViewById(R.id.roomIntro);

        roomName.setText(roomList.get(position).getRoomName());
        roomIntro.setText(roomList.get(position).getRoomIntro());

        Button deleteButton = (Button) v.findViewById(R.id.deleteR_button);
        deleteButton.setOnClickListener(roomList.get(position).DeleteClickListener);

        v.setTag("" +  position);
        return v;
    }
}
