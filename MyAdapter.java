package com.example.thoitiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTietModel> thoiTietModels;

    public MyAdapter(Context context, ArrayList<ThoiTietModel> thoiTietModels) {
        this.context = context;
        this.thoiTietModels = thoiTietModels;
    }

    @Override
    public int getCount() {
        return thoiTietModels.size();
    }

    @Override
    public Object getItem(int i) {
        return thoiTietModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.custom,viewGroup,false);

        ImageView image=view.findViewById(R.id.anh1);
        TextView txtNgay=view.findViewById(R.id.ngay);
        TextView txtNhietDo=view.findViewById(R.id.nhietdo);

        image.setImageResource(thoiTietModels.get(i).anh);
        txtNgay.setText(thoiTietModels.get(i).ngay);
        txtNhietDo.setText(thoiTietModels.get(i).nhietDo);
        return view;
    }
}
