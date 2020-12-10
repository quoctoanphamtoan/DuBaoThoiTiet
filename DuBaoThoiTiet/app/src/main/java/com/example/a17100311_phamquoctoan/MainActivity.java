package com.example.a17100311_phamquoctoan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnSearch;
    Button btnChange;
    EditText txtSearch;
    TextView tvThanhPho;
    TextView tvQuocGia;
    TextView tvName;
    TextView tvTrangThai;
    TextView tvDoAm;
    TextView tvNhietDo;
    TextView tvMay;
    TextView tvGio;
    TextView tvNgayCapNhat;
    TextView tvDate;
    ImageView imgIcon;
    String CITY ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        GetCurrentWeatherData("Saigon");
        onClick();

    }
    public void mapping(){
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnChange = (Button)findViewById(R.id.btnChange);
        txtSearch =(EditText)findViewById(R.id.txtSearch);
        tvName =(TextView) findViewById(R.id.tvName);
        tvTrangThai=(TextView) findViewById(R.id.tvTrangThai);
        tvDoAm=(TextView) findViewById(R.id.tvDoAm);
        tvNhietDo=(TextView) findViewById(R.id.txtNhietDo);
        tvMay=(TextView) findViewById(R.id.tvMay);
        tvGio=(TextView) findViewById(R.id.tvGio);
        tvDate=(TextView) findViewById(R.id.tvDay);
        tvNgayCapNhat=(TextView) findViewById(R.id.tvDay);
        imgIcon=(ImageView) findViewById(R.id.imgIcon);
        tvQuocGia=(TextView) findViewById(R.id.tvQuocGia);


    }
    public void onClick(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = txtSearch.getText().toString();
                if (city.equals("")){
                    CITY = "Saigon";
                    GetCurrentWeatherData(CITY);
                }else{
                    GetCurrentWeatherData(city);
                }


            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,MainActivity2.class);
                String city = txtSearch.getText().toString();
                intent.putExtra("city",city);
                startActivity(intent);
            }
        });
    }
    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String URL =  "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=46f072034d22605211ff818c936f6fb6";
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("KetQua",response);
                        try {
                            JSONObject jsonObject  = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            tvName.setText(name);
                            long l  =  Long.valueOf(day);
                            Date date = new Date(l*1000L);

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            tvDate.setText(Day);
                            JSONArray jsonArray =  jsonObject.getJSONArray("weather");
                            JSONObject jsonWeather = jsonArray.getJSONObject(0);
                            String status =jsonWeather.getString("main");
                            String icon = jsonWeather.getString("icon");
//                            http://openweathermap.org/img/wn/
                            Glide.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+icon+".png").into(imgIcon);
                            tvTrangThai.setText(status);

                            JSONObject maimJson =  jsonObject.getJSONObject("main");
                            String nhietdo = maimJson.getString("temp");
                            String doAm = maimJson.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String NHIETDO =  String.valueOf(a.intValue());
                            tvNhietDo.setText(NHIETDO+"C");
                            tvDoAm.setText(doAm+"/%");
                            JSONObject windJson = jsonObject.getJSONObject("wind");
                            String gio =windJson.getString("speed");
                            tvGio.setText(gio+"m/s");
                            JSONObject cloudJson = jsonObject.getJSONObject("clouds");
                            String may = cloudJson.getString("all");
                            tvMay.setText(may);
                            JSONObject jsonsys= jsonObject.getJSONObject("sys");
                            String country = jsonsys.getString("country");
                            tvQuocGia.setText(country);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

}