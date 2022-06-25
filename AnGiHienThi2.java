package com.example.appangicungduoc.AnGi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appangicungduoc.DanhSach.DSMonActivity;
import com.example.appangicungduoc.Database;
import com.example.appangicungduoc.MainActivity;
import com.example.appangicungduoc.MonAnModel;
import com.example.appangicungduoc.MyAdapter;
import com.example.appangicungduoc.R;

import java.util.ArrayList;
import java.util.Random;

public class AnGiHienThi2 extends AppCompatActivity {

    ListView dsMonAn;
    TextView textView, tvTrong, tvnguyenlieu;
    Button ok, huy;
    int vt;
    int soNguoi, tongTien, dem = 0, demyc = 0, giaBuaAn = 0;
    String loai, dsNguyenLieu = "",dsNguyenLieu2="";
    Boolean yc1, yc2, yc3;
    Random random = new Random();
    SQLiteDatabase db;
    ArrayList<MonAnModel> data;
    MyAdapter adapter;
    String DB_NAME = "anGiCungDuoc.sqlite";

    SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_gi_hien_thi2);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        anhXa();
        getTT();

        data = new ArrayList<>();
        adapter = new MyAdapter(AnGiHienThi2.this, data);
        dsMonAn.setAdapter(adapter);
        timMon(tongTien, soNguoi);

        adapter.notifyDataSetChanged();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                thongbao();
                Intent intent = new Intent(AnGiHienThi2.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnGiHienThi2.this, AngiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void thongbao() {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(AnGiHienThi2.this,"My Notification");
        builder.setContentTitle("Nguyên liệu cần mua:");
        builder.setContentText(dsNguyenLieu2);
        builder.setSmallIcon(R.drawable.giohang);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AnGiHienThi2.this);
        managerCompat.notify(1,builder.build());
    }
    void anhXa() {
        dsMonAn = findViewById(R.id.lvBuaAnMenu);
        ok = findViewById(R.id.btBuaAnOK);
        textView = findViewById(R.id.textView10);
        tvTrong = findViewById(R.id.txtDSTrong);
        tvnguyenlieu = findViewById(R.id.txtNguyenLieu);
        huy = findViewById(R.id.btHuy);
    }
    void getTT() {
        Intent intent = getIntent();
        tongTien = intent.getIntExtra("tien", 0);
        soNguoi = intent.getIntExtra("soNguoi", 0);
        loai = intent.getStringExtra("loai");

        yc1 = intent.getBooleanExtra("yc1", false);
        yc2 = intent.getBooleanExtra("yc2", false);
        yc3 = intent.getBooleanExtra("yc3", false);
    }
    void timMon(int _tongTien, int _soNguoi) {
        int demMon = 0;
        data.clear();
        double soDu = _tongTien / _soNguoi;
        int idMonAn, giaMon;
        String nguyenLieu, phanLoai, tenMon;
        database = Database.initDatabase(this, DB_NAME);
        if (loai.equals("rỗng") == true) {
            try {
                Cursor cursor = database.rawQuery("Select * from MonAn where Gia <= '" + soDu + "' and Gia >= '6000' and Loai like '%" + "Món chính" + "%' ", null);
                dem = cursor.getCount();
                if (dem > 0) {
                    vt = random.nextInt(dem);
                    cursor.moveToPosition(vt);
                    idMonAn = cursor.getInt(0);
                    tenMon = cursor.getString(1);
                    phanLoai = cursor.getString(2);
                    giaMon = cursor.getInt(3);
                    nguyenLieu = cursor.getString(4).toString();
                    data.add(new MonAnModel(idMonAn, tenMon, phanLoai, giaMon * _soNguoi, nguyenLieu));
                    demMon++;
                    soDu = soDu - giaMon;
                    giaBuaAn += giaMon*_soNguoi;
                    dsNguyenLieu = nguyenLieu;
                    dsNguyenLieu2 = nguyenLieu;
                }
                cursor = database.rawQuery("Select * from MonAn where Gia <= '" + soDu + "' and Loai like '%" + "Món rau" + "%' ", null);
                dem = cursor.getCount();
                if (dem > 0) {
                    vt = random.nextInt(dem);
                    cursor.moveToPosition(vt);
                    idMonAn = cursor.getInt(0);
                    tenMon = cursor.getString(1);
                    phanLoai = cursor.getString(2);
                    giaMon = cursor.getInt(3);
                    nguyenLieu = cursor.getString(4).toString();
                    data.add(new MonAnModel(idMonAn, tenMon, phanLoai, giaMon * _soNguoi, nguyenLieu));
                    demMon++;
                    soDu = soDu - giaMon;
                    giaBuaAn += giaMon*_soNguoi;
                    dsNguyenLieu = dsNguyenLieu + "\n" + nguyenLieu;
                    dsNguyenLieu2 = dsNguyenLieu2 + ", " + nguyenLieu;
                }
                cursor = database.rawQuery("Select * from MonAn where Gia <= '" + soDu + "' and Loai like '%" + "Món phụ" + "%' ", null);
                dem = cursor.getCount();

                if (dem > 0) {
                    vt = random.nextInt(dem);
                    cursor.moveToPosition(vt);
                    idMonAn = cursor.getInt(0);
                    tenMon = cursor.getString(1);
                    phanLoai = cursor.getString(2);
                    giaMon = cursor.getInt(3);
                    nguyenLieu = cursor.getString(4).toString();
                    data.add(new MonAnModel(idMonAn, tenMon, phanLoai, giaMon * _soNguoi, nguyenLieu));
                    demMon++;
                    soDu = soDu - giaMon;
                    giaBuaAn += giaMon*_soNguoi;
                    dsNguyenLieu = dsNguyenLieu + "\n" + nguyenLieu;
                    dsNguyenLieu2 = dsNguyenLieu2 + ", " + nguyenLieu;
                }
                cursor.close();
                if (demMon == 0) {
                    textView.setVisibility(View.INVISIBLE);
                    tvTrong.setVisibility(View.VISIBLE);
                    dsMonAn.setVisibility(View.INVISIBLE);
                    tvnguyenlieu.setVisibility(View.INVISIBLE);
                } else {
                    tvnguyenlieu.setText("Nguyên liệu cần có:\n" + dsNguyenLieu +"\nSố tiền: "+giaBuaAn);
                    textView.setVisibility(View.VISIBLE);
                    tvTrong.setVisibility(View.INVISIBLE);
                    dsMonAn.setVisibility(View.VISIBLE);
                    tvnguyenlieu.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                textView.setVisibility(View.INVISIBLE);
                tvTrong.setVisibility(View.VISIBLE);
                dsMonAn.setVisibility(View.INVISIBLE);
                tvnguyenlieu.setVisibility(View.INVISIBLE);
            }
        } else {
            try {
                if (yc1 == true) {
                    demyc++;
                    Cursor cursor = database.rawQuery("Select * from MonAn where Gia <= '" + soDu + "' and Loai like '%" + "Món chính" + "%' ", null);
                    dem = cursor.getCount();

                    if (dem > 0) {
                        vt = random.nextInt(dem);
                        cursor.moveToPosition(vt);
                        idMonAn = cursor.getInt(0);
                        tenMon = cursor.getString(1);
                        phanLoai = cursor.getString(2);
                        giaMon = cursor.getInt(3);
                        nguyenLieu = cursor.getString(4).toString();
                        data.add(new MonAnModel(idMonAn, tenMon, phanLoai, giaMon * _soNguoi, nguyenLieu));
                        demMon++;
                        soDu = soDu - giaMon;
                        giaBuaAn += giaMon*_soNguoi;
                        dsNguyenLieu = nguyenLieu;
                        dsNguyenLieu2 = nguyenLieu;
                    }
                }
                if (yc2 == true) {
                    demyc++;
                    Cursor cursor = database.rawQuery("Select * from MonAn where Gia <= '" + soDu + "' and Loai like '%" + "Món phụ" + "%' ", null);
                    dem = cursor.getCount();

                    if (dem > 0) {
                        vt = random.nextInt(dem);
                        cursor.moveToPosition(vt);
                        idMonAn = cursor.getInt(0);
                        tenMon = cursor.getString(1);
                        phanLoai = cursor.getString(2);
                        giaMon = cursor.getInt(3);
                        nguyenLieu = cursor.getString(4).toString();
                        data.add(new MonAnModel(idMonAn, tenMon, phanLoai, giaMon * _soNguoi, nguyenLieu));
                        demMon++;
                        soDu = soDu - giaMon;
                        giaBuaAn += giaMon*_soNguoi;
                        dsNguyenLieu = dsNguyenLieu + "\n" + nguyenLieu;
                        dsNguyenLieu2 = dsNguyenLieu2 + ", " + nguyenLieu;
                    }
                }
                if (yc3 == true) {
                    demyc++;
                    Cursor cursor = database.rawQuery("Select * from MonAn where Gia <= '" + soDu + "' and Loai like '%" + "Món rau" + "%' ", null);
                    dem = cursor.getCount();

                    if (dem > 0) {
                        vt = random.nextInt(dem);
                        cursor.moveToPosition(vt);
                        idMonAn = cursor.getInt(0);
                        tenMon = cursor.getString(1);
                        phanLoai = cursor.getString(2);
                        giaMon = cursor.getInt(3);
                        nguyenLieu = cursor.getString(4).toString();
                        data.add(new MonAnModel(idMonAn, tenMon, phanLoai, giaMon * _soNguoi, nguyenLieu));
                        demMon++;
                        soDu = soDu - giaMon;
                        giaBuaAn += giaMon*_soNguoi;
                        dsNguyenLieu = dsNguyenLieu + "\n" + nguyenLieu;
                        dsNguyenLieu2 = dsNguyenLieu2 + ", " + nguyenLieu;
                    }
                }

                if (demMon != demyc) {
                    textView.setVisibility(View.INVISIBLE);
                    tvTrong.setVisibility(View.VISIBLE);
                    dsMonAn.setVisibility(View.INVISIBLE);
                    tvnguyenlieu.setVisibility(View.INVISIBLE);
                } else {
                    tvnguyenlieu.setText("Nguyên liệu cần có:\n" + dsNguyenLieu +"\nSố tiền: "+giaBuaAn);
                    textView.setVisibility(View.VISIBLE);
                    tvTrong.setVisibility(View.INVISIBLE);
                    dsMonAn.setVisibility(View.VISIBLE);
                    tvnguyenlieu.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                textView.setVisibility(View.INVISIBLE);
                tvTrong.setVisibility(View.VISIBLE);
                dsMonAn.setVisibility(View.INVISIBLE);
                tvnguyenlieu.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void insertData() {
        db = openOrCreateDatabase("anGiCungDuoc.sqlite", MODE_PRIVATE, null);

        String sql = " INSERT INTO ThongKe VALUES (date('now')," + giaBuaAn + ") ";
        String sql2 = " UPDATE ThongKe SET Tien=Tien+ " + giaBuaAn + " WHERE Date=date('now')";
        try {
            db.execSQL(sql);
            Toast.makeText(this, "Đã thêm chi tiêu", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            db.execSQL(sql2);
            Toast.makeText(this, "Đã cập nhật chi tiêu", Toast.LENGTH_SHORT).show();
        }
    }
}