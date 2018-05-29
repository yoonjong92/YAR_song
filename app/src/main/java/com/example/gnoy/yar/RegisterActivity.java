package com.example.gnoy.yar;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Boolean.TRUE;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //user input을 받아 변수에 저장
        final EditText locationText = (EditText) findViewById(R.id.locationText);
        final EditText titleText = (EditText) findViewById(R.id.titleText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText contactText = (EditText) findViewById(R.id.contactText);

        //Button input
        Button registerButton = (Button) findViewById(R.id.registerButton);

        //when button clicked
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String roomLocation = locationText.getText().toString();
                String roomTitle = titleText.getText().toString();
                String roomPassword = passwordText.getText().toString();
                String roomContact = contactText.getText().toString();

                //Response.Listener<String> responseListener = new Response.Listener<String>(){
                //    @Override
                //    public void onResponse(String response){
                        try{
                            //JSONObject jsonResponse = new JSONObject(response);
                            //boolean success = jsonResponse.getBoolean("success");
                            boolean success = TRUE;
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("방 만들기에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("방 만들기에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }
                        //catch(JSONException e){
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    //}
                //};
                //RegisterRequest를 call하고 parameter를 넘겨서
                //queue에 넣고 network로 전송
            }
        });
    }
}
