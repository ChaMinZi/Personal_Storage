package com.example.samsung.copy;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.id.button1;

public class ListView1 extends AppCompatActivity {

    String text, imgUrl;
    int [] posi = new int[100000];
    int count;

    int category=-1;
    View category1;
    Button cabtn1;

    private static final String TAG_UIDX="bo_us_idx";
    private static final String TAG_TXT="bo_wr_date";
    private static final String TAG_BSEQ="bo_ur_seq";
    private static final String TAG_UIDX2="us_idx";
    private static final String TAG_IMG="ur_addr";
    private static final String TAG_SEQ="ur_seq";
    private static final String TAG_CA="bo_ca_idx";
    private static final String TAG_IDX = "idx";

    String us_idx;
    ArrayList<HashMap<String, String>> mArrayList2;
    ArrayList<HashMap<String, String>> mArrayList3;

    String url="http://fantasque.codns.com/test_insert_board1.php?";
    EditText plusurl;
    View geturl;
    String plusUrl;

    ListView listview ;//리스트뷰 제작 소스
    ListViewAdapter adapter;

    String jsonURL="http://fantasque.codns.com/json_select_table.php?case=user";
    String jsonURL_url="http://fantasque.dothome.co.kr/test/getjson.php?case=url";
            //"http://fantasque.codns.com/json_select_table.php?case=url";
    String jsonURL_board1="http://fantasque.dothome.co.kr/test/getjson.php?case=board1";
                    //"http://fantasque.codns.com/json_select_table.php?case=board1";
    String jsonURL_category="http://fantasque.codns.com/json_select_table.php?case=category";

    private static String TAG = "phptest_MainActivity";
    String mJsonString2, mJsonString3;
    private static final String TAG_JSON="webnautes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view1);
        setTitle("뷰어");

        final Intent intent=getIntent();
        us_idx=intent.getExtras().getString("us_idx");//로그인한 유저를 식별하는 식별자
        mArrayList2=(ArrayList<HashMap<String, String>>)intent.getSerializableExtra("mArrayList2");
        mArrayList3=(ArrayList<HashMap<String, String>>)intent.getSerializableExtra("mArrayList3");

        //ListView listview ;//리스트뷰 제작 소스
        //ListViewAdapter adapter;

        adapter = new ListViewAdapter() ;

        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        count=0;
        String temp="1";
        for(int i=0;i<mArrayList2.size();i++){
        //for(int i=mArrayList2.size()-1;i>=0;i--){
            if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                for(int j=0;j<mArrayList3.size();j++){
                    if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                            &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                            &&temp.equals("1")){
                        imgUrl=mArrayList3.get(j).get(TAG_IMG);
                        if(text==null){
                            text=" ";
                        }
                        adapter.addItem(imgUrl, text);
                        posi[count] = i;
                        count++;
                        temp = "2";
                    }
                }
                temp="1";
            }
        }

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){//리스트뷰 롱클릭 이벤트
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] caArray=new String[]{"미분류", "배경", "여행", "음식", "운동", "취미"};
                category1=(View) View.inflate(ListView1.this, R.layout.category1, null);
                AlertDialog.Builder dlg=new AlertDialog.Builder(ListView1.this);
                dlg.setTitle("카테고리 변경");
                dlg.setSingleChoiceItems(caArray, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cabtn1=(Button) category1.findViewById(R.id.cabtn1);
                        cabtn1.setText(caArray[which]);
                        String strca=Integer.toString(which);//strca는 카테고리 수치->서버로
                    }
                });
                dlg.setPositiveButton("닫기", null);
                dlg.show();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {//리스트뷰 클릭 이벤트
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                Intent intent=new Intent(getApplicationContext(), ImageViewer.class);

                String boseq=mArrayList2.get(posi[position]).get(TAG_BSEQ);
                count=0;
                for(int i=0;i<mArrayList3.size();i++){
                    if(us_idx.equals(mArrayList3.get(i).get(TAG_UIDX2))&&boseq.equals(mArrayList3.get(i).get(TAG_SEQ))){
                        imgUrl=mArrayList3.get(i).get(TAG_IMG);
                        intent.putExtra("img"+count, imgUrl);
                        count++;
                    }
                }
                intent.putExtra("count", count);
                startActivity(intent);
            }
        }) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//옵션메뉴(카테고리)제작 및 선택 소스
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String temp="1";
        switch (item.getItemId()){
            case R.id.urlicon:
                geturl=(View)View.inflate(ListView1.this, R.layout.geturl, null);
                AlertDialog.Builder dlg=new AlertDialog.Builder(ListView1.this);
                dlg.setTitle("추가할 페이지 URL 입력");
                dlg.setView(geturl);
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                plusurl=(EditText)geturl.findViewById(R.id.plusurl);
                                plusUrl=plusurl.getText().toString();

                                /*String gourl=url+"us_idx="+us_idx+"&url="+plusUrl;
                                Uri uri=Uri.parse(gourl);
                                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);*/

                                ListView1.NetworkTask networkTask=new NetworkTask(url,"us_idx="+us_idx+"&url="+plusUrl);
                                networkTask.execute();

                                //리스트뷰 갱신을 위해 추가한 부분
                                mArrayList2 = new ArrayList<>();
                                ListView1.GetData2 task2=new ListView1.GetData2();
                                task2.execute(jsonURL_board1);

                                mArrayList3 = new ArrayList<>();
                                ListView1.GetData3 task3=new ListView1.GetData3();
                                task3.execute(jsonURL_url);
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
                return true;
            case R.id.category:
                category=-1;
                ListViewAdapter.listViewItemList.clear();
                count=0;
                temp="1";
                for(int i=0;i<mArrayList2.size();i++){
                //for(int i=mArrayList2.size()-1;i>=0;i--){
                    if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                        String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                        text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                        for(int j=0;j<mArrayList3.size();j++){
                            if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                                    &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                                    &&temp.equals("1")){
                                    imgUrl = mArrayList3.get(j).get(TAG_IMG);
                                    if (text == null) {
                                        text = " ";
                                    }
                                adapter.addItem(imgUrl, text);
                                posi[count] = i;
                                count++;
                                temp = "2";
                            }
                        }
                        temp="1";
                    }
                }
                adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "전체", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.category0:
                category=1;
                ListViewAdapter.listViewItemList.clear();
                count=0;
                temp="1";
                for(int i=0;i<mArrayList2.size();i++){
                //for(int i=mArrayList2.size()-1;i>=0;i--){
                    if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                        String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                        text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                        for(int j=0;j<mArrayList3.size();j++){
                            if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                                    &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                                    &&temp.equals("1")){
                                int tempca=Integer.parseInt(mArrayList2.get(i).get(TAG_CA));
                                if(category==tempca) {
                                    imgUrl = mArrayList3.get(j).get(TAG_IMG);
                                    if (text == null) {
                                        text = " ";
                                    }
                                    adapter.addItem(imgUrl, text);
                                    posi[count] = i;
                                    count++;
                                }
                                temp = "2";
                            }
                        }
                        temp="1";
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "미분류", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.category1:
                category=2;
                ListViewAdapter.listViewItemList.clear();
                count=0;
                temp="1";
                for(int i=0;i<mArrayList2.size();i++){
                //for(int i=mArrayList2.size()-1;i>=0;i--){
                    if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                        String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                        text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                        for(int j=0;j<mArrayList3.size();j++){
                            if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                                    &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                                    &&temp.equals("1")){
                                int tempca=Integer.parseInt(mArrayList2.get(i).get(TAG_CA));
                                if(category==tempca) {
                                    imgUrl = mArrayList3.get(j).get(TAG_IMG);
                                    if (text == null) {
                                        text = " ";
                                    }
                                    adapter.addItem(imgUrl, text);
                                    posi[count] = i;
                                    count++;
                                }
                                temp = "2";
                            }
                        }
                        temp="1";
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "배경", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.category2:
                category=3;
                ListViewAdapter.listViewItemList.clear();
                count=0;
                temp="1";
                for(int i=0;i<mArrayList2.size();i++){
                //for(int i=mArrayList2.size()-1;i>=0;i--){
                    if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                        String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                        text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                        for(int j=0;j<mArrayList3.size();j++){
                            if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                                    &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                                    &&temp.equals("1")){
                                int tempca=Integer.parseInt(mArrayList2.get(i).get(TAG_CA));
                                if(category==tempca) {
                                    imgUrl = mArrayList3.get(j).get(TAG_IMG);
                                    if (text == null) {
                                        text = " ";
                                    }
                                    adapter.addItem(imgUrl, text);
                                    posi[count] = i;
                                    count++;
                                }
                                temp = "2";
                            }
                        }
                        temp="1";
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "여행", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.category3:
                category=4;
                ListViewAdapter.listViewItemList.clear();
                count=0;
                temp="1";
                for(int i=0;i<mArrayList2.size();i++){
                //for(int i=mArrayList2.size()-1;i>=0;i--){
                    if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                        String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                        text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                        for(int j=0;j<mArrayList3.size();j++){
                            if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                                    &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                                    &&temp.equals("1")){
                                int tempca=Integer.parseInt(mArrayList2.get(i).get(TAG_CA));
                                if(category==tempca) {
                                    imgUrl = mArrayList3.get(j).get(TAG_IMG);
                                    if (text == null) {
                                        text = " ";
                                    }
                                    adapter.addItem(imgUrl, text);
                                    posi[count] = i;
                                    count++;
                                }
                                temp = "2";
                            }
                        }
                        temp="1";
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "음식", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.category4:
                category=5;
                ListViewAdapter.listViewItemList.clear();
                count=0;
                temp="1";
                for(int i=0;i<mArrayList2.size();i++){
                //for(int i=mArrayList2.size()-1;i>=0;i--){
                    if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                        String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                        text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                        for(int j=0;j<mArrayList3.size();j++){
                            if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                                    &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                                    &&temp.equals("1")){
                                int tempca=Integer.parseInt(mArrayList2.get(i).get(TAG_CA));
                                if(category==tempca) {
                                    imgUrl = mArrayList3.get(j).get(TAG_IMG);
                                    if (text == null) {
                                        text = " ";
                                    }
                                    adapter.addItem(imgUrl, text);
                                    posi[count] = i;
                                    count++;
                                }
                                temp = "2";
                            }
                        }
                        temp="1";
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "운동", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.category5:
                category=6;
                ListViewAdapter.listViewItemList.clear();
                count=0;
                temp="1";
                for(int i=0;i<mArrayList2.size();i++){
                //for(int i=mArrayList2.size()-1;i>=0;i--){
                    if (us_idx.equals(mArrayList2.get(i).get(TAG_UIDX))) {
                        String boseq=mArrayList2.get(i).get(TAG_BSEQ);
                        text="작성일자: "+mArrayList2.get(i).get(TAG_TXT);
                        for(int j=0;j<mArrayList3.size();j++){
                            if(us_idx.equals(mArrayList3.get(j).get(TAG_UIDX2))
                                    &&boseq.equals(mArrayList3.get(j).get(TAG_SEQ))
                                    &&temp.equals("1")){
                                int tempca=Integer.parseInt(mArrayList2.get(i).get(TAG_CA));
                                if(category==tempca) {
                                    imgUrl = mArrayList3.get(j).get(TAG_IMG);
                                    if (text == null) {
                                        text = " ";
                                    }
                                    adapter.addItem(imgUrl, text);
                                    posi[count] = i;
                                    count++;
                                }
                                temp = "2";
                            }
                        }
                        temp="1";
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "취미", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


//이하는 url을 서버로 보내기 위한 함수
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

    //DB 읽어오기
    private class GetData2 extends AsyncTask<String, Void, String>{//JSON 받기
        ProgressDialog progressDialog;
        String errorString=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=ProgressDialog.show(ListView1.this, "Please Wait", null, true, true);
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

            progressDialog=ProgressDialog.show(ListView1.this, "Please Wait", null, true, true);
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
}