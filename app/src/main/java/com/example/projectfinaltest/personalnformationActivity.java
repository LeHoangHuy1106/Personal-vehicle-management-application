package com.example.projectfinaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class personalnformationActivity extends AppCompatActivity {

    EditText edtHoTen, edtNgaySinh, edtSdt, edtNgheNghiep;
    Button btnInfoXong, btnInfoXoa;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalnformation);
        init();
        chonNgay(edtNgaySinh);
        btnInfoXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertInfo();
            }

        });


    }

    private void insertInfo() {
        Intent i= getIntent();
        String hoten = edtHoTen.getText().toString();
        String ngaysinh = edtNgaySinh.getText().toString();
        String sdt = edtSdt.getText().toString();
        String nghenghiep= edtNgheNghiep.getText().toString();
         email = i.getStringExtra("email");
        if(hoten.equals(""))
        {
            Toast.makeText(personalnformationActivity.this,"Bạn cần nhập họ tên",Toast.LENGTH_LONG).show();
        }
        else if(ngaysinh.equals(""))
        {
            Toast.makeText(personalnformationActivity.this,"Bạn cần nhập ngày sinh",Toast.LENGTH_LONG).show();
        }
        else if(sdt.equals(""))
        {
            Toast.makeText(personalnformationActivity.this,"Bạn cần nhập số điện thoại",Toast.LENGTH_LONG).show();
        }
        else if(nghenghiep.equals(""))
        {
            Toast.makeText(personalnformationActivity.this,"Bạn cần nhâọ nghề nghiệp",Toast.LENGTH_LONG).show();
        }
        else
        {
            person ps= new person(email,hoten,ngaysinh,sdt,nghenghiep);
            FirebaseDatabase database= FirebaseDatabase.getInstance();
            DatabaseReference myRef= database.getReference();
            myRef.child(method.nameGmail(email)).child("PersonInfo").setValue(ps);
            Toast.makeText(personalnformationActivity.this,"Thêm thông tin thành công",Toast.LENGTH_LONG).show();
            Intent j = new Intent(personalnformationActivity.this,MainActivity.class);
            j.putExtra("email",email);
            startActivity(j);
        }




    }

    void init()
    {
        edtHoTen= findViewById(R.id.edtHoTen);
        edtNgaySinh= findViewById(R.id.edtNgaySinh);
        edtSdt= findViewById(R.id.edtSdt);
        edtNgheNghiep = findViewById(R.id.edtNgheNghiep);
        btnInfoXoa= findViewById(R.id.btnInfoXoa);
        btnInfoXong= findViewById(R.id.btnInfoXong);
    }
    void chonNgay( EditText edt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        edt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(personalnformationActivity.this, new DatePickerDialog.OnDateSetListener() {
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


}