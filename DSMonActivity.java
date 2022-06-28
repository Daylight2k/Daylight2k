package com.example.appangicungduoc.DanhSach;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.appangicungduoc.Database;
import com.example.appangicungduoc.MonAnModel;
import com.example.appangicungduoc.MyAdapter;
import com.example.appangicungduoc.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DSMonActivity extends AppCompatActivity {
    Button btThem;
    ImageButton btTimKiem;
    EditText txtTk;
    ListView listView;
    ArrayList<MonAnModel> data;
    MyAdapter adapter;
    String DB_NAME="anGiCungDuoc.sqlite";
    SQLiteDatabase database=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsmon);

        anhXa();
        loadlistview();

        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(DSMonActivity.this, MainActivityThemMon.class);
                startActivity(intent);
            }
        });

        btTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data=new ArrayList<>();
                adapter=new MyAdapter(DSMonActivity.this,data);
                listView.setAdapter(adapter);

                database = Database.initDatabase(DSMonActivity.this, DB_NAME);
                Cursor cursor = database.rawQuery("Select * from MonAn where TenMon like '%" + txtTk.getText().toString().trim() + "%' or Loai like '%"+txtTk.getText().toString().trim() +"%' ",null);
                data.clear();
                //duyet du lieu
                while (cursor.moveToNext())
                {
                    int id=cursor.getInt(0);
                    String name=cursor.getString(1);
                    String loai=cursor.getString(2);
                    int gia=cursor.getInt(3);
                    String nguyenlieu=cursor.getString(4);
                    data.add(new MonAnModel(id,name,loai,gia,nguyenlieu));
                }
                cursor.close();
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(DSMonActivity.this, ThongTinMon.class);

                intent.putExtra("id",data.get(i).getId());
                intent.putExtra("ten", data.get(i).getTenMon());
                intent.putExtra("loai", data.get(i).getLoai());
                intent.putExtra("gia", String.valueOf(data.get(i).getGia()));
                intent.putExtra("anh",data.get(i).idAnh);
                intent.putExtra("nguyenLieu", data.get(i).getNguyenLieu());


                startActivity(intent);

            }
        });
    }

    private void anhXa()
    {
        listView=findViewById(R.id.lvdanhsach);
        btThem=findViewById(R.id.btThem);
        btTimKiem=findViewById(R.id.btTimKiem);
        txtTk=findViewById(R.id.txtTimKiem);

    }

    private void loadlistview()
    {
        data=new ArrayList<>();
        adapter=new MyAdapter(DSMonActivity.this,data);
        listView.setAdapter(adapter);
        showAllContast();

    }
    private void showAllContast() {
        database = Database.initDatabase(this, DB_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM MonAn",null);
        data.clear();
        //duyet du lieu
        while (cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String loai=cursor.getString(2);
            int gia=cursor.getInt(3);
            String nguyenlieu=cursor.getString(4);
            data.add(new MonAnModel(id,name,loai,gia,nguyenlieu));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

}