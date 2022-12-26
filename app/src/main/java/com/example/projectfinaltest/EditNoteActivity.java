package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
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

public class EditNoteActivity extends AppCompatActivity {

    EditText edtKmNote,edtDiaChiNote, edtDungTichNote, edtGhiChuNote, edtSoTienNote;
    TextView tvGioNote, tvNgayNote;
    Spinner snXeNote, snLoaiNote;
    String xeNote, loaiNote;
    Button btnThemNote, btnXoaNote;
    private ArrayList<String> arrayXeNote  = new ArrayList<>();
    private    ArrayList<String> arrayLoaiNote= new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;

    String email;
    myNote myNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        init();
        getEmail();
        createSpinner();
        Empty();
        chonNgay(tvNgayNote);
        ChonGio(tvGioNote);


        btnThemNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNote my = new myNote(loaiNote,xeNote,tvGioNote.getText().toString(),tvNgayNote.getText().toString(),edtKmNote.getText().toString(),edtDiaChiNote.getText().toString(),edtDungTichNote.getText().toString(),edtGhiChuNote.getText().toString(),edtSoTienNote.getText().toString(),myNote.getStt());

                //      myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").child(String.valueOf(maxid+1)).setValue(myVehicles);
                myRef.child(method.nameGmail(email)).child("ListNote").child("list").child(String.valueOf(my.getStt())).setValue(my);


                Toast.makeText(EditNoteActivity.this,"Thêm Note thành công",Toast.LENGTH_SHORT).show();
                finish();


            }
        });
        btnXoaNote.setOnClickListener(new View.OnClickListener() {
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




    void chonNgay( TextView edt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        edt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(EditNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
    void ChonGio(TextView tv)
    {
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tv.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }
    private void createSpinner() {
        //......Spinner loại xe......//

        arrayXeNote= new ArrayList<>();
        myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(arrayXeNote!=null||!arrayXeNote.isEmpty())
                {
                    int i=0;

                    arrayXeNote.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        myVehicles myxe= dataSnapshot.getValue(myVehicles.class);
                        arrayXeNote.add(myxe.TenXe);
                        i++;

                    }
                    ArrayAdapter arrayAdapterXeNote= new ArrayAdapter(EditNoteActivity.this, android.R.layout.simple_spinner_item,arrayXeNote);
                    arrayAdapterXeNote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    snXeNote.setAdapter(arrayAdapterXeNote);




                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });




        snXeNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xeNote= arrayXeNote.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //......Spinner lịch hẹn theo tháng.....//
        arrayLoaiNote= new ArrayList<String>();
        arrayLoaiNote.add("Loại ghi chú");
        arrayLoaiNote.add("đổ xăng");
        arrayLoaiNote.add("sửa chửa");
        arrayLoaiNote.add("thay nhớt");

        ArrayAdapter arrayAdapterLoaiNote= new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayLoaiNote);
        arrayAdapterLoaiNote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snLoaiNote.setAdapter(arrayAdapterLoaiNote);
        snLoaiNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaiNote= arrayLoaiNote.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    void Empty(){
        Intent i = getIntent();
        myNote= (myNote)i.getSerializableExtra("note");
        snXeNote.setSelection(arrayXeNote.indexOf(myNote.getXeNote()));
        snLoaiNote.setSelection(arrayLoaiNote.indexOf(myNote.getLoaiNote()));
        tvGioNote.setText(myNote.getGioNote());
        tvNgayNote.setText(myNote.getNgayNote());
        edtKmNote.setText(myNote.getKmNote());
        edtDiaChiNote.setText(myNote.getDiaDiemNote());
        edtDungTichNote.setText(myNote.getLitNote());
        edtSoTienNote.setText(myNote.getSoTienNote());
        edtGhiChuNote.setText(myNote.getNoteNote());



    }




    void init()
    {
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference();
        snXeNote= findViewById(R.id.snXeNote);
        snLoaiNote= findViewById(R.id.snLoaiNote);
        tvGioNote= findViewById(R.id.edtGioNote);
        tvNgayNote= findViewById(R.id.edtNgayNote);
        edtKmNote= findViewById(R.id.edtKmNote);
        edtDiaChiNote= (EditText)findViewById(R.id.edtDiaDiemNote);
        edtDungTichNote= findViewById(R.id.edtDungTichNote);
        edtGhiChuNote= findViewById(R.id.edtNoteNote);
        edtSoTienNote = findViewById(R.id.edtTienNote);
        btnThemNote= findViewById(R.id.btnThemNote);
        btnXoaNote= findViewById(R.id.btnHuyNote);

        edtKmNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditNoteActivity.this,"Bạn không để edit Km hiện tại ở đây",Toast.LENGTH_LONG).show();
                edtKmNote.setEnabled(false);

            }
        });





    }

}