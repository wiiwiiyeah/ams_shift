package com.example.fenson.myfaktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;


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
        final TextView result1 = (TextView)findViewById(R.id.text_result1);
        final TextView result2 = (TextView)findViewById(R.id.text_result2);
        final TextView result3 = (TextView)findViewById(R.id.text_result3);
        String URL ="http://asiapocc.000webhostapp.com/selectShift.php";   // Request a string response from the provided URL.

        RequestBody BODY = new FormBody.Builder().add("id","2").add("name","Y.J.Chen").build();

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
                        android.util.Log.v("tagFK", "Error");
                    }
                });

            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                final String rsp_string = response.body().string();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        android.util.Log.v("tagFK", "Succeed");
                        try {
                            JSONObject rsp_json = new JSONObject(rsp_string);
                            android.util.Log.v("tagFK","In the try");
                            JSONArray P_data_array = rsp_json.getJSONArray("Personal_Data");
                            int length_array = P_data_array.length();
                            String[] JS_id = new String[length_array];
                            String[] JS_name = new String[length_array];
                            String[] JS_phone = new String[length_array];
                            for (int i=0;i<P_data_array.length();i++) {
                                JSONObject JS_OB_in_array = P_data_array.getJSONObject(i);
                                JS_id[i] = JS_OB_in_array.getString("id");
                                JS_name[i] = JS_OB_in_array.getString("name");
                                JS_phone[i]= JS_OB_in_array.getString("phone");
                            }
                            String result_id = Arrays.toString(JS_id);
                            String result_name = Arrays.toString(JS_name);
                            String result_phone = Arrays.toString(JS_phone);
                            result1.setText(result_id);
                            result2.setText(result_name);
                            result3.setText(result_phone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
//---------------------------------callback method-------------------------------------------------


    }

}
