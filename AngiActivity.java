package com.example.appangicungduoc.AnGi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appangicungduoc.MainActivity;
import com.example.appangicungduoc.R;
import com.example.appangicungduoc.ThongKe.ThongkeActivity;

import java.util.ArrayList;

public class AngiActivity extends AppCompatActivity {

    Button Tim1Mon,Tim1Bua;
    EditText Gia;
    CheckBox cbMonChinh,cbMonPhu,cbCanh;
    Boolean kt1=false,kt2=false,kt3=false;
    int soNguoi,tien;
    String loai="rỗng";
    Spinner songuoi;
    int dem=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angi);
        anhXa();
        setSpinner();
        checkCbb();
        Tim1Mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dem>1)
                    Toast.makeText(AngiActivity.this,"Vui lòng chọn 1 loại hoặc không chọn!",Toast.LENGTH_SHORT).show();
                else
                    {
                    try {
                        xuLyTT();
                        if(soNguoi>=1&&soNguoi<=5) {
                            Intent intent = new Intent(AngiActivity.this, AnGiHienThi1.class);
                            intent.putExtra("tien", tien);
                            intent.putExtra("soNguoi", soNguoi);
                            intent.putExtra("loai", loai);
                            finish();
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(AngiActivity.this,"Kiểm tra lại số người!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(AngiActivity.this,"Kiểm tra lại dữ liệu vừa nhập!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Tim1Bua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    xuLyTT();
                    Intent intent = new Intent(AngiActivity.this, AnGiHienThi2.class);
                    intent.putExtra("tien", tien);
                    intent.putExtra("soNguoi",soNguoi);
                    intent.putExtra("loai",loai);
                    intent.putExtra("yc1",kt1);
                    intent.putExtra("yc2",kt2);
                    intent.putExtra("yc3",kt3);
                    finish();
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(AngiActivity.this,"Kiểm tra lại dữ liệu vừa nhập!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void anhXa()
    {
        Tim1Bua=findViewById(R.id.btTim1Bua);
        Tim1Mon=findViewById(R.id.btTim1Mon);
        Gia=findViewById(R.id.txtSoTienTimMon);
        cbCanh=findViewById(R.id.cbMonCanh);
        cbMonChinh=findViewById(R.id.cbMonChinh);
        cbMonPhu=findViewById(R.id.cbMonPhu);
        songuoi=findViewById(R.id.spinnerNguoi);
    }
    void checkCbb()
    {
        cbMonChinh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    kt1=true;
                    dem++;
                }
                else{
                    kt1=false;
                    dem--;
                }

            }
        });
        cbMonPhu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    kt2=true;
                    dem++;
                }
                else {
                    kt2=false;
                    dem--;
                }

            }
        });
        cbCanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    kt3=true;
                    dem++;
                }
                else {
                    kt3=false;
                    dem--;
                }

            }
        });
    }
    void xuLyTT()
    {
      tien=Integer.parseInt(Gia.getText().toString());

      if(kt1==true)
          loai=cbMonChinh.getText().toString().trim();
      if(kt2==true)
            loai=cbMonPhu.getText().toString().trim();
      if(kt3==true)
            loai=cbCanh.getText().toString().trim();
    }
    void setSpinner()
    {
        ArrayList<Integer> nguoi;
        nguoi=new ArrayList<>();
        nguoi.add(1);
        nguoi.add(2);
        nguoi.add(3);
        nguoi.add(4);
        nguoi.add(5);
        ArrayAdapter<Integer> adapter=new ArrayAdapter<>(AngiActivity.this, android.R.layout.simple_list_item_1,nguoi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        songuoi.setAdapter(adapter);

        songuoi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                soNguoi=Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}