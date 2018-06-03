package com.example.gnoy.yar;

import android.content.ContentValues;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class RequestURLHttpConnection {
    public String request(String _url){

        // HttpURLConnection 참조 변수.
        HttpURLConnection urlConn = null;

        /**
         * 2. HttpURLConnection을 통해 web의 데이터를 가져온다.
         * */
        try{
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();

            // [2-1]. urlConn 설정.
//            urlConn.setRequestMethod("GET"); // URL 요청에 대한 메소드 설정 : POST.
  //          urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            urlConn.setRequestProperty("Content-Type", "application/json");
            //urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");
/*
            // [2-2]. parameter 전달 및 데이터 읽어오기.
            String strParams = _params; //sbParams에 정리한 파라미터들을 스트링으로 저장. 예)id=id1&pw=123;
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8")); // 출력 스트림에 출력.
            os.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
            os.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.
*/
            // [2-3]. 연결 요청 확인.
            // 실패 시 null을 리턴하고 메서드를 종료.
            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            // [2-4]. 읽어온 결과물 리턴.
            // 요청한 URL의 출력물을 BufferedReader로 받는다.
            InputStream responseBody = urlConn.getInputStream();

            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

            JSONParser jsonParser = new JSONParser();

            JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBodyReader);

            JSONArray ItemsArray = (JSONArray) jsonObject.get("Items");

            JSONObject ItemObject = (JSONObject) ItemsArray.get(0);

            String value= "" + ItemObject.get("password");

            return value;

        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }

        return null;

    }
}

