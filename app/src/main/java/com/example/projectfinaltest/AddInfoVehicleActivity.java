package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class AddInfoVehicleActivity extends AppCompatActivity {

    EditText edtTenXe,edtHangXe,edtBienSo,edtKmBanDau;
    TextView tvNgaySoHuu,tvkmHienTai, tvSoLanThayNho;
    Spinner snLoaiXe, snLichTheoThang,snLichTheoKm;
    String loaixe, lichtheothang, lichtheokm;
    Button btnXong, btnXoa;
    ArrayList<String> arrayLoaiXe,arrayLichTheoThang,arrayLichTheoKm;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int maxid;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_vehicle);
        init();
        getEmail();
        Empty();
        createSpinner();
        chonNgay(tvNgaySoHuu);
        getIdMax();


        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập tên xe "+(edtTenXe.getText().toString().equals("")),Toast.LENGTH_SHORT).show();
                if(edtTenXe.getText().toString().equals(""))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập tên xe",Toast.LENGTH_SHORT).show();
                }
                else if(edtHangXe.getText().toString().equals(""))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập hãng xe",Toast.LENGTH_SHORT).show();
                }

                else if(edtBienSo.getText().toString().equals(""))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập biển số xe",Toast.LENGTH_SHORT).show();
                }
                else if(edtKmBanDau.getText().toString().equals(""))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập Km ban đầu",Toast.LENGTH_SHORT).show();
                }
                else if(tvNgaySoHuu.getText().toString().equals(""))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập ngày mua",Toast.LENGTH_SHORT).show();
                }

                else if(loaixe.equals(arrayLoaiXe.get(0)))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập loại xe",Toast.LENGTH_SHORT).show();
                }
                else if(arrayLichTheoKm.equals(arrayLichTheoKm.get(0)))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập nhắc thay nhớt theo km",Toast.LENGTH_SHORT).show();
                }
                else if(arrayLichTheoThang.equals(arrayLichTheoThang.get(0)))
                {
                    Toast.makeText(AddInfoVehicleActivity.this,"bạn chưa nhập nhắc thay nhớt theo tháng",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    myVehicles myVehicles= new myVehicles(edtTenXe.getText().toString(),loaixe,edtHangXe.getText().toString(),edtBienSo.getText().toString(),tvNgaySoHuu.getText().toString(),Integer.parseInt(edtKmBanDau.getText().toString()),Integer.parseInt(edtKmBanDau.getText().toString()),Integer.parseInt(tvSoLanThayNho.getText().toString()),lichtheothang,lichtheokm,maxid+1);
                    myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").child(String.valueOf(maxid+1)).setValue(myVehicles);
                    myRef.child(method.nameGmail(email)).child("ListVehicles").child("id").setValue(maxid+1);
                    Toast.makeText(AddInfoVehicleActivity.this,"Thêm Xe thành công",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Empty();
                finish();
            }

        });

    }
    void getEmail()
    {
        Intent i= getIntent();
        email = i.getStringExtra("email");

    }


    private void getIdMax() {
        DatabaseReference myMaxID = database.getReference();
        myMaxID.child(method.nameGmail(email)).child("ListVehicles").child(("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if( snapshot.getValue(int.class)==null)
                {
                    myMaxID.child(method.nameGmail(email)).child("ListVehicles").child(("id")).setValue(0);
                }
                else
                {
                    maxid= snapshot.getValue(int.class);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    void chonNgay( TextView edt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        edt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AddInfoVehicleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        c.set(year, month,dayOfMonth);
                        edt.setText(simpleDateFormat.format(c.getTime()));
                    } ;
                }, year, month, day);
                dpd.show();
            }

        });
    }

    private void createSpinner() {
        //......Spinner loại xe......//
        arrayLoaiXe= new ArrayList<String>();
        arrayLoaiXe.add("Chọn loại xe");
        arrayLoaiXe.add("Xe máy");
        arrayLoaiXe.add("Ô tô");

        ArrayAdapter arrayAdapterLoaiXe= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayLoaiXe);
        arrayAdapterLoaiXe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snLoaiXe.setAdapter(arrayAdapterLoaiXe);
        snLoaiXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaixe= arrayLoaiXe.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //......Spinner lịch hẹn theo tháng.....//
         arrayLichTheoThang= new ArrayList<String>();
        arrayLichTheoThang.add("Lịch Thay Nhớt theo tháng");
        arrayLichTheoThang.add("1 tháng");
        arrayLichTheoThang.add("2 tháng");
        arrayLichTheoThang.add("3 tháng");
        arrayLichTheoThang.add("4 tháng");

        ArrayAdapter arrayAdapterLichTheoThang= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayLichTheoThang);
        arrayAdapterLichTheoThang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snLichTheoThang.setAdapter(arrayAdapterLichTheoThang);
        snLichTheoThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lichtheothang= arrayLichTheoThang.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //......Spinner lịch hẹn theo tháng.....//
       arrayLichTheoKm= new ArrayList<String>();
        arrayLichTheoKm.add("Lịch Thay Nhớt theo Km");
        arrayLichTheoKm.add("300 km");
        arrayLichTheoKm.add("500 km");
        arrayLichTheoKm.add("700km");
        arrayLichTheoKm.add("1000km");

        ArrayAdapter arrayAdapterLichTheoKm= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayLichTheoKm);
        arrayAdapterLichTheoKm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snLichTheoKm.setAdapter(arrayAdapterLichTheoKm);
        snLichTheoKm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lichtheokm= arrayLichTheoKm.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    void Empty(){
        edtTenXe.setText("");
        edtHangXe.setText("");
        tvNgaySoHuu.setText("");
        edtBienSo.setText("");
        edtKmBanDau.setText("");
        tvkmHienTai.setText(edtKmBanDau.getText().toString());
        tvSoLanThayNho.setText("0");
        snLoaiXe.setSelection(0);
        snLichTheoThang.setSelection(0);
        snLichTheoKm.setSelection(0);


    }




    void init()
    {
        edtTenXe= findViewById(R.id.edtXeNote);
        edtHangXe= findViewById(R.id.edtInfoHangXe);
        edtBienSo= findViewById(R.id.edtInfoBienSo);
        edtKmBanDau= findViewById(R.id.edtKmNote);
        tvNgaySoHuu= findViewById(R.id.edtNgayNote);
        tvkmHienTai= findViewById(R.id.edtDiaDiemNote);
        tvSoLanThayNho= findViewById(R.id.edtDungTichNote);
        snLoaiXe= findViewById(R.id.edtLoaiNote);
        snLichTheoKm=findViewById(R.id.snInfoLichHenTheoKm);
        snLichTheoThang= findViewById(R.id.snInfoLịchHenTheoThang);
        btnXoa=  findViewById(R.id.btnHuyNote);
        btnXong = findViewById(R.id.btnThemNote);
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();

        tvkmHienTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddInfoVehicleActivity.this,"Bạn không cần nhập giá trị này",Toast.LENGTH_LONG).show();
            }
        });




    }
}