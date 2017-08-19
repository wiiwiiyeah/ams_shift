package com.example.fenson.myfaktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    OkHttpClient OHC = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String URL ="http://asiapocc.000webhostapp.com/selectShift.php";   // Request a string response from the provided URL.
        final TextView result = (TextView)findViewById(R.id.text_result);
        // did not use--------------
//        HashMap map_body = new HashMap<>();
//        map_body.put("id","1");
        // did not use--------------
        RequestBody BODY = new FormBody.Builder().add("id","1").build();
//        RequestBody BODY = new FormBody.Builder().addEncoded("id","1").build();   //same as add


        Request REQ = new Request.Builder().url(URL).post(BODY).build();
        Call call = OHC.newCall(REQ);

//---------------------------------call method-----------------------------------------------------
        //did not work.
//        try {
//            Response response = OHC.newCall(REQ).execute();
//            final String rest = response.body().string();
//            result.setText(rest);
//        } catch (IOException e) {
//            android.util.Log.v("TAG_FK", "Error");
//        }
//---------------------------------call method-----------------------------------------------------

//---------------------------------callback method-------------------------------------------------
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        android.util.Log.v("TAG_FK", "Error");
                    }
                });

            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                final String rest = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        android.util.Log.v("TAG_FK", "Succeed");
                        result.setText(rest);
                    }
                });

            }
        });
//---------------------------------callback method-------------------------------------------------
    }
}
