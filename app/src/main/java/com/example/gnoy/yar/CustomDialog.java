package com.example.gnoy.yar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomDialog extends Dialog implements View.OnClickListener{
    private static final int LAYOUT = R.layout.add_music;

    private Context context;

    private EditText editTitle;
    private EditText editArtist;
    private EditText editMessage;

    private Button add_cancel;
    private Button add_submit;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editArtist = (EditText) findViewById(R.id.editArtist);
        editMessage = (EditText) findViewById(R.id.editMessage);

        add_cancel = (Button) findViewById(R.id.add_music_cancel);
        add_submit = (Button) findViewById(R.id.add_music_submit);

        add_cancel.setOnClickListener(this);
        add_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_music_cancel:
                cancel();
                break;
            case R.id.add_music_submit:
                cancel();
                break;
        }
    }
}