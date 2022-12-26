package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditPersonaInfomationActivity extends AppCompatActivity {


    EditText edtHoTen, edtNgaySinh, edtSdt, edtNgheNghiep;
    Button btnInfoXong, btnInfoXoa;
    private String email;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private person person= new person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalnformation);
        init();
        chonNgay(edtNgaySinh);
        getEmail();
        empty();

        btnInfoXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                insertInfo();

            }
        });
        btnInfoXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    void getEmail()
    {
        Intent i= getIntent();
        email = i.getStringExtra("email");

    }
    void empty()
    {
        myRef.child(method.nameGmail(email)).child("PersonInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                person = snapshot.getValue(person.class);
                edtHoTen.setText(person.getHoTen());
                edtNgaySinh.setText(person.getNgaySinh());
                edtSdt.setText(person.getSdt());
                edtNgheNghiep.setText(person.getNgheNghiep());
                if(person!=null)
                {
                    Toast.makeText(EditPersonaInfomationActivity.this,"có person",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void insertInfo() {

        String hoten = edtHoTen.getText().toString();
        String ngaysinh = edtNgaySinh.getText().toString();
        String sdt = edtSdt.getText().toString();
        String nghenghiep= edtNgheNghiep.getText().toString();

        if(hoten.equals(""))
        {
            Toast.makeText(EditPersonaInfomationActivity.this,"Bạn cần nhập họ tên",Toast.LENGTH_LONG).show();
        }
        else if(ngaysinh.equals(""))
        {
            Toast.makeText(EditPersonaInfomationActivity.this,"Bạn cần nhập ngày sinh",Toast.LENGTH_LONG).show();
        }
        else if(sdt.equals(""))
        {
            Toast.makeText(EditPersonaInfomationActivity.this,"Bạn cần nhập số điện thoại",Toast.LENGTH_LONG).show();
        }
        else if(nghenghiep.equals(""))
        {
            Toast.makeText(EditPersonaInfomationActivity.this,"Bạn cần nhâọ nghề nghiệp",Toast.LENGTH_LONG).show();
        }
        else
        {
            person ps= new person(email,hoten,ngaysinh,sdt,nghenghiep);
            FirebaseDatabase database= FirebaseDatabase.getInstance();
            DatabaseReference myRef= database.getReference();
            myRef.child(method.nameGmail(email)).child("PersonInfo").setValue(ps);
            Toast.makeText(EditPersonaInfomationActivity.this,"Sửa thông tin thành công",Toast.LENGTH_LONG).show();
            finish();

        }




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
                DatePickerDialog dpd = new DatePickerDialog(EditPersonaInfomationActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    void init()
    {
        edtHoTen= findViewById(R.id.edtHoTen);
        edtNgaySinh= findViewById(R.id.edtNgaySinh);
        edtSdt= findViewById(R.id.edtSdt);
        edtNgheNghiep = findViewById(R.id.edtNgheNghiep);
        btnInfoXoa= findViewById(R.id.btnInfoXoa);
        btnInfoXong= findViewById(R.id.btnInfoXong);
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
    }

}