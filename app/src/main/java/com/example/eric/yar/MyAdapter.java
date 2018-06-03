package com.example.eric.yar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MyAdapter extends BaseAdapter implements View.OnClickListener{

    List<MusicDto> list;
    LayoutInflater inflater;
    Activity activity;

    public MyAdapter() {
    }

    public MyAdapter(Activity activity, List<MusicDto> list) {
        this.list = list;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            convertView.setTag(position);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(list.get(position).getTitle());

        TextView artist = (TextView) convertView.findViewById(R.id.artist);
        artist.setText(list.get(position).getArtist());

        Button messageButton = (Button) convertView.findViewById(R.id.show_message);
        messageButton.setOnClickListener(this);

        Button deleteButton = (Button) convertView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        return convertView;
    }

    public void onClick(View v)
    {
        // 111번 라인에서 저장한 tag(position)을 꺼내온다.
        int position = (Integer) v.getTag();

        // 리스트에서 position에 맞는 데이터를 받아온다.
        String message = list.get(position).getMessage();
    }

    private static final BitmapFactory.Options options = new BitmapFactory.Options();

}