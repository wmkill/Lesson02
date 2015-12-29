package com.dever.lesson02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Callback {

    private ListView lv;
    private Call call;
    private ItemAdapter adapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.main_list);
        list = new ArrayList<>();
        adapter = new ItemAdapter(this);
        lv.setAdapter(adapter);
        OkHttpClient client = new OkHttpClient();
        Request build = new Request.Builder().url("http://m2.qiushibaike.com/article/list/suggest?page=1").get().build();
        call = client.newCall(build);

        call.enqueue(this);
    }

    /**
     * 失败（在非UI线程中执行）
     * @param request
     * @param e
     */
    @Override
    public void onFailure(Request request, IOException e) {
        e.printStackTrace();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"网络问题",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 成功（在非UI线程中执行）
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(Response response) throws IOException {
        final String string = response.body().string();

        try {
            JSONObject object = new JSONObject(string);

            JSONArray items = object.getJSONArray("items");
            Log.d("onResponse", "onResponse: "+items);
            final List<Item> list = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                list.add(new Item(items.getJSONObject(i)));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    adapter.addAll(list);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        call.cancel();//取消请求
    }
}
