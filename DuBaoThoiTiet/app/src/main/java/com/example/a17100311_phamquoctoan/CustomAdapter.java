package com.example.a17100311_phamquoctoan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList ;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_listview,null);
        ThoiTiet thoiTiet = arrayList.get(position);
        TextView tvday =convertView.findViewById(R.id.tvNgay);
        TextView tvTrangThai =convertView.findViewById(R.id.tvTrangThai);
        TextView max =convertView.findViewById(R.id.tvMax);
        TextView min =convertView.findViewById(R.id.tvMin);
        ImageView img = convertView.findViewById(R.id.imageTrangThai);
        tvday.setText(thoiTiet.Day);
        tvTrangThai.setText(thoiTiet.status);
        max.setText(thoiTiet.max);
        min.setText(thoiTiet.min);

        Glide.with(context).load("http://openweathermap.org/img/wn/"+thoiTiet.image+".png").into(img);

        return convertView;
    }
}
