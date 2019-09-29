package com.example.samsung.copy;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "us_id";
    private static final String TAG_IDX = "idx";
    private static final String TAG_PW ="us_pw";
    String mJsonString;
    ArrayList<HashMap<String, String>> mArrayList;//유저 테이블

    private static final String TAG_UIDX="bo_us_idx";
    private static final String TAG_BSEQ="bo_ur_seq";
    private static final String TAG_TXT="bo_wr_date";
    private static final String TAG_CA="bo_ca_idx";
    String mJsonString2;
    ArrayList<HashMap<String, String>> mArrayList2;//보드 테이블

    private static final String TAG_UIDX2="us_idx";
    private static final String TAG_IMG="ur_addr";
    private static final String TAG_SEQ="ur_seq";
    String mJsonString3;
    ArrayList<HashMap<String, String>> mArrayList3;//url 테이블

    String jsonURL="http://fantasque.dothome.co.kr/test/getjson.php?case=user";
            //"http://fantasque.codns.com/json_select_table.php?case=user";
    String jsonURL_url="http://fantasque.dothome.co.kr/test/getjson.php?case=url";
                    //"http://fantasque.codns.com/json_select_table.php?case=url";
    String jsonURL_board1="http://fantasque.dothome.co.kr/test/getjson.php?case=board1";
                            //"http://fantasque.codns.com/json_select_table.php?case=board1";
    String jsonURL_category="http://fantasque.dothome.co.kr/test/getjson.php?case=category";
                                    //"http://fantasque.codns.com/json_select_table.php?case=category";

    String idx, id, password, email, name, birth;
    String readid, readpassword;
    String temp1, temp2;

    Button joinbtn;
    View getjoin;
    EditText idEText, passwordEText, emailEText, nameEText, passwordETextc, birthEText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("로그인");

        mArrayList = new ArrayList<>();
        GetData task=new GetData();
        task.execute(jsonURL);

        mArrayList2 = new ArrayList<>();
        GetData2 task2=new GetData2();
        task2.execute(jsonURL_board1);

        mArrayList3 = new ArrayList<>();
        GetData3 task3=new GetData3();
        task3.execute(jsonURL_url);

        joinbtn=(Button)findViewById(R.id.joinbtn);//회원가입 소스
        joinbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getjoin=(View)View.inflate(MainActivity.this, R.layout.getjoin, null);
                AlertDialog.Builder dlg2=new AlertDialog.Builder(MainActivity.this);
                dlg2.setTitle("회원가입");
                dlg2.setView(getjoin);
                dlg2.setPositiveButton("확인",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                idEText=(EditText)getjoin.findViewById(R.id.idEText);
                                emailEText=(EditText)getjoin.findViewById(R.id.emailEText);
                                nameEText=(EditText)getjoin.findViewById(R.id.nameEText);
                                birthEText=(EditText)getjoin.findViewById(R.id.birthEText);
                                passwordEText=(EditText)getjoin.findViewById(R.id.passwordEText);
                                passwordETextc=(EditText)getjoin.findViewById(R.id.passwordETextc);

                                temp1=passwordEText.getText().toString();
                                temp2=passwordETextc.getText().toString();

                                if(temp1.equals(temp2)){
                                    id=idEText.getText().toString();
                                    password=passwordEText.getText().toString();
                                    email=emailEText.getText().toString();
                                    name=nameEText.getText().toString();
                                    birth=birthEText.getText().toString();

                                    int check=0;
                                    for(int i=0;i<mArrayList.size();i++){
                                        if(id.equals(mArrayList.get(i).get(TAG_ID))){
                                            check=1;
                                            Toast.makeText(getApplicationContext(), "중복된 아이디가 존재합니다", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if(check==0) {
                                        if (birth.length() == 8) {
                                            //받은 회원정보들(String)을 서버로 보내기
                                            //String temp = "{\"us_id\""+":"+"\""+id+"\""+ "," + "\"us_pw\""+":" + "\"" + password + "\"" +
                                            //        "," + "\"us_name\""+":" + "\"" + name + "\"" + "," + "\"us_birth\""+":" + "\"" + birth + "\"" +
                                            //        "," + "\"us_email\""+":" + "\"" + email + "\"" + "}";
                                            //네트워크 연결
                                            /*
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put("us_id", id);
                                            contentValues.put("us_pw", password);
                                            contentValues.put("us_name", name);
                                            contentValues.put("us_birth", birth);
                                            contentValues.put("us_email", email);
                                            */

                                            NetworkTask networkTask = new NetworkTask("http://fantasque.codns.com/test_insert_user.php?", "id="+id+"&pw="+password+"&name="+name+"&birth="+birth+"&email="+email);
                                            networkTask.execute();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "생년월일은 8자리입니다(예>19900101)", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주십시오", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dlg2.setNegativeButton("취소", null);
                dlg2.show();
            }
        });
    }

    public void onButton1Clicked(View v){//로그인 소스
        EditText idEditText=(EditText) findViewById(R.id.idEditText);
        EditText passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        readid=idEditText.getText().toString();
        readpassword=passwordEditText.getText().toString();

        for(int i=0;i<mArrayList.size();i++) {
            if(readid.equals(mArrayList.get(i).get(TAG_ID))){
                id=mArrayList.get(i).get(TAG_ID);
                password=mArrayList.get(i).get(TAG_PW);
                idx=mArrayList.get(i).get(TAG_IDX);
            }
        }

        if(readid.equals(id)&&readpassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ListView1.class);
            intent.putExtra("us_idx", idx);
            intent.putExtra("mArrayList2", mArrayList2);
            intent.putExtra("mArrayList3", mArrayList3);
            startActivity(intent);
            finish();
        }
        else{
            //Toast.makeText(getApplicationContext(), id+"\n"+password, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "아이디나 비번이 틀렸습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private class GetData extends AsyncTask<String, Void, String>{//JSON 받기
        ProgressDialog progressDialog;
        String errorString=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            mJsonString = result;
            showResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL=params[0];

            try{
                URL url=new URL(serverURL);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode=httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - "+responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();
                }
                else{
                    inputStream=httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder sb=new StringBuilder();
                String line;

                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error", e);
                errorString=e.toString();

                return null;
            }
        }
    }

    private void showResult(){
        try{
            JSONObject jsonObject=new JSONObject(mJsonString);
            JSONArray jsonArray=jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item=jsonArray.getJSONObject(i);

                String tempid=item.getString(TAG_ID);//tempid에 TAG_ID란 변수로 저장된 json값을 저장
                String tempidx=item.getString(TAG_IDX);
                String temppw=item.getString(TAG_PW);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, tempid);
                hashMap.put(TAG_IDX, tempidx);
                hashMap.put(TAG_PW, temppw);

                mArrayList.add(i, hashMap);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }
    }

    private class GetData2 extends AsyncTask<String, Void, String>{//JSON 받기
        ProgressDialog progressDialog;
        String errorString=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            mJsonString2 = result;
            showResult2();
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL=params[0];

            try{
                URL url=new URL(serverURL);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode=httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - "+responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();
                }
                else{
                    inputStream=httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder sb=new StringBuilder();
                String line;

                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error", e);
                errorString=e.toString();

                return null;
            }
        }
    }

    private void showResult2(){
        try{
            JSONObject jsonObject=new JSONObject(mJsonString2);
            JSONArray jsonArray=jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item=jsonArray.getJSONObject(i);

                String tempid=item.getString(TAG_TXT);
                String tempuidx=item.getString(TAG_UIDX);
                String tempseq=item.getString(TAG_BSEQ);
                String tempca=item.getString(TAG_CA);
                String tempidx=item.getString(TAG_IDX);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_TXT, tempid);
                hashMap.put(TAG_UIDX, tempuidx);
                hashMap.put(TAG_BSEQ, tempseq);
                hashMap.put(TAG_CA, tempca);
                hashMap.put(TAG_IDX, tempidx);

                mArrayList2.add(i, hashMap);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }
    }

    private class GetData3 extends AsyncTask<String, Void, String>{//JSON 받기
        ProgressDialog progressDialog;
        String errorString=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            mJsonString3 = result;
            showResult3();
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL=params[0];

            try{
                URL url=new URL(serverURL);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode=httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - "+responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();
                }
                else{
                    inputStream=httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder sb=new StringBuilder();
                String line;

                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error", e);
                errorString=e.toString();

                return null;
            }
        }
    }

    private void showResult3(){
        try{
            JSONObject jsonObject=new JSONObject(mJsonString3);
            JSONArray jsonArray=jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject item=jsonArray.getJSONObject(i);

                String tempudx=item.getString(TAG_UIDX2);
                String tempimg=item.getString(TAG_IMG);
                String tempseq=item.getString(TAG_SEQ);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_UIDX2, tempudx);
                hashMap.put(TAG_IMG, tempimg);
                hashMap.put(TAG_SEQ, tempseq);

                mArrayList3.add(i, hashMap);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult: ", e);
        }
    }

    //서버로 회원가입 데이터 보내기
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private String values;

        public NetworkTask(String url, String values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    /*public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection1 requestHttpURLConnection1 = new RequestHttpURLConnection1();
            result = requestHttpURLConnection1.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvoutput.setText(s);
        }
    }*/
}