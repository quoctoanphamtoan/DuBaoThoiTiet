package com.example.a17100311_phamquoctoan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    String tenThanhPho="";
    ImageView imgBack;
    TextView tvName;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> mangThoiTiet;
//    ImageView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mapping();
        onClick();
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        Log.d("kq: ",city);

        if(city.equals("")){
            tenThanhPho="Saigon";
            get7day(tenThanhPho);

        }
        else {
            tenThanhPho=city;
            get7day(city);
        }



    }

    private void onClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void mapping(){
        imgBack = (ImageView)findViewById(R.id.imgBack);
        tvName = (TextView)findViewById(R.id.tvTenThanhPho);
        lv = (ListView)findViewById(R.id.listView);
        mangThoiTiet = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(MainActivity2.this,mangThoiTiet);
        lv.setAdapter(customAdapter);

    }
    public void get7day(String data){
        String URL = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=46f072034d22605211ff818c936f6fb6";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Log.d("ketqua","Json: "+response);
                            JSONObject jsonObject =new JSONObject(response);
                            JSONObject jsonCity = jsonObject.getJSONObject("city");
                            String name = jsonCity.getString("name");
                            tvName.setText(name);
                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i<jsonArrayList.length();i++){
                                JSONObject jsonObjectlist = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectlist.getString("dt");
                                long l  =  Long.valueOf(ngay);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);
//                                JSONObject main = jsonArrayList.get
                                JSONObject jsonObjectNhietDo = jsonObjectlist.getJSONObject("main");
                                String max  = jsonObjectNhietDo.getString("temp_max");
                                String min  = jsonObjectNhietDo.getString("temp_min");
                                Double a = Double.valueOf(max);
                                String MAX =  String.valueOf(a.intValue());
                                Double b = Double.valueOf(min);
                                String MIN =  String.valueOf(b.intValue());
                                JSONArray jsonArrayWeather =jsonObjectlist.getJSONArray("weather");
                                JSONObject jsonObjectWeather =jsonArrayWeather.getJSONObject(0);
                                String status =jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");
                                mangThoiTiet.add(new ThoiTiet(Day,status,icon,MAX,MIN));

                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this,"Khong co du lieu",Toast.LENGTH_LONG);
                    }

        }
        );
        requestQueue.add(stringRequest);
    }
}