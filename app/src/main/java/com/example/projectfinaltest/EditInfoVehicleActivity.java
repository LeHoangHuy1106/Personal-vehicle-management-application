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

public class EditInfoVehicleActivity extends AppCompatActivity {

    EditText edtTenXe,edtHangXe,edtBienSo,edtKmBanDau;
    TextView tvNgaySoHuu,tvkmHienTai, tvSoLanThayNho;
    Spinner snLoaiXe, snLichTheoThang,snLichTheoKm;
    String loaixe, lichtheothang, lichtheokm;
    Button btnXong, btnXoa;
    private  ArrayList<String> arrayLoaiXe= new ArrayList<>();
    private ArrayList<String>  arrayLichTheoThang = new ArrayList<>();
    private ArrayList<String> arrayLichTheoKm= new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    int maxid;
    String email;
    myVehicles myXe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_vehicle);

        init();
        getEmail();
        Empty();
        chonNgay(tvNgaySoHuu);
        getIdMax();


        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTenXe.getText().toString().equals(""))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p t??n xe",Toast.LENGTH_SHORT).show();
                }
                else if(edtHangXe.getText().toString().equals(""))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p h??ng xe",Toast.LENGTH_SHORT).show();
                }

                else if(edtBienSo.getText().toString().equals(""))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p bi???n s??? xe",Toast.LENGTH_SHORT).show();
                }
                else if(edtKmBanDau.getText().toString().equals(""))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p Km ban ?????u",Toast.LENGTH_SHORT).show();
                }
                else if(tvNgaySoHuu.getText().toString().equals("ch???n ng??y mua xe"))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p ng??y mua",Toast.LENGTH_SHORT).show();
                }

                else if(loaixe.equals(arrayLoaiXe.get(0)))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p lo???i xe",Toast.LENGTH_SHORT).show();
                }
                else if(arrayLichTheoKm.equals(arrayLichTheoKm.get(0)))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p nh???c thay nh???t theo km",Toast.LENGTH_SHORT).show();
                }
                else if(arrayLichTheoThang.equals(arrayLichTheoThang.get(0)))
                {
                    Toast.makeText(EditInfoVehicleActivity.this,"b???n ch??a nh???p nh???c thay nh???t theo th??ng",Toast.LENGTH_SHORT).show();
                }
                else
                {
               //     myXe= new myVehicles(edtTenXe.getText().toString(),loaixe,edtHangXe.getText().toString(),edtBienSo.getText().toString(),tvNgaySoHuu.getText().toString(),edtKmBanDau.getText().toString(),edtKmBanDau.getText().toString(),tvSoLanThayNho.getText().toString(),lichtheothang,lichtheokm,myXe.getStt());
                    myVehicles myVehicles= new myVehicles(edtTenXe.getText().toString(),loaixe,edtHangXe.getText().toString(),edtBienSo.getText().toString(),tvNgaySoHuu.getText().toString(),Integer.parseInt(edtKmBanDau.getText().toString()),Integer.parseInt(edtKmBanDau.getText().toString()),Integer.parseInt(tvSoLanThayNho.getText().toString()),lichtheothang,lichtheokm,myXe.getStt());
                    myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").child(String.valueOf(myXe.getStt())).setValue(myXe);

                    Toast.makeText(EditInfoVehicleActivity.this,"S???a th??ng tin Xe th??nh c??ng",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empty();
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
                DatePickerDialog dpd = new DatePickerDialog(EditInfoVehicleActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        //......Spinner lo???i xe......//
        arrayLoaiXe= new ArrayList<String>();
        arrayLoaiXe.add("Ch???n lo???i xe");
        arrayLoaiXe.add("Xe m??y");
        arrayLoaiXe.add("?? t??");

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
        //......Spinner l???ch h???n theo th??ng.....//
        arrayLichTheoThang= new ArrayList<String>();
        arrayLichTheoThang.add("L???ch Thay Nh???t theo th??ng");
        arrayLichTheoThang.add("1 th??ng");
        arrayLichTheoThang.add("2 th??ng");
        arrayLichTheoThang.add("3 th??ng");
        arrayLichTheoThang.add("4 th??ng");

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
        //......Spinner l???ch h???n theo th??ng.....//
        arrayLichTheoKm= new ArrayList<String>();
        arrayLichTheoKm.add("L???ch Thay Nh???t theo Km");
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

        createSpinner();
        Intent i= getIntent();
        myXe= (myVehicles) i.getSerializableExtra("xe");

        edtTenXe.setText(myXe.getTenXe());
        edtHangXe.setText(myXe.getHangXe());
        tvNgaySoHuu.setText(myXe.getNgaySoHuu());
        edtBienSo.setText(myXe.getBienSo());
        edtKmBanDau.setText(myXe.getKmHienTai()+"");
        tvkmHienTai.setText(myXe.getKmHienTai()+"");
        tvSoLanThayNho.setText(myXe.getSoLanThaiNho()+"");
       Toast.makeText(EditInfoVehicleActivity.this,"soluong haha"+myXe.getStt(),Toast.LENGTH_LONG).show();
        snLoaiXe.setSelection(arrayLoaiXe.indexOf(myXe.getLoaiXe()));
        snLichTheoThang.setSelection(arrayLichTheoThang.indexOf(myXe.getNhacTheoNgay()));
        snLichTheoKm.setSelection(arrayLichTheoKm.indexOf(myXe.getNhacTheoKm()));


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
        snLichTheoThang= findViewById(R.id.snInfoL???chHenTheoThang);
        btnXoa=  findViewById(R.id.btnHuyNote);
        btnXong = findViewById(R.id.btnThemNote);
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        tvkmHienTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditInfoVehicleActivity.this,"B???n kh??ng c???n nh???p gi?? tr??? n??y",Toast.LENGTH_LONG).show();
            }
        });


    }


}