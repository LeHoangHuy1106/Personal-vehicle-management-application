package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
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

public class AddNoteActivity extends AppCompatActivity {

    EditText edtKmNote,edtDiaChiNote, edtDungTichNote, edtGhiChuNote, edtSoTienNote;
    TextView tvGioNote, tvNgayNote;
    Spinner snXeNote, snLoaiNote;
    String xeNote, loaiNote;
    Button btnThemNote, btnXoaNote;
    private ArrayList<String> arrayXeNote  = new ArrayList<>();
    private    ArrayList<String> arrayLoaiNote= new ArrayList<>();
    private  int kmHienTai;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int maxid;
    String email;
    myVehicles myXeUpDate = new myVehicles();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        init();

        getEmail();
        Empty();
        createSpinner();
        chonNgay(tvNgayNote);
        ChonGio(tvGioNote);
        getIdMax();







        btnThemNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if( arrayXeNote.size()==0)
                {
                    Toast.makeText(AddNoteActivity.this,"Vui lòng thêm xe của bạn để tạo ghi chú",Toast.LENGTH_SHORT).show();
                }
                else  if(loaiNote.equals(arrayLoaiNote.get(0)))
                {
                    Toast.makeText(AddNoteActivity.this,"Nhập loại ghi chú",Toast.LENGTH_SHORT).show();
                }
                else if((tvGioNote.getText().toString().equals("Chọn giờ")))
                {
                    Toast.makeText(AddNoteActivity.this,"Chọn giờ ghi chú",Toast.LENGTH_SHORT).show();
                }
                else if((tvNgayNote.getText().toString().equals("Chọn ngày")))
                {
                    Toast.makeText(AddNoteActivity.this,"Chọn ngày ghi chú",Toast.LENGTH_SHORT).show();
                }
                else if((edtKmNote.getText().toString().equals("")))
                {
                    Toast.makeText(AddNoteActivity.this,"km hiện tại là:"+kmHienTai,Toast.LENGTH_SHORT).show();
                }
                else if( Integer.parseInt(edtKmNote.getText().toString())<kmHienTai)
                {

                    Toast.makeText(AddNoteActivity.this,"km hiện tại phải >= "+kmHienTai,Toast.LENGTH_SHORT).show();
                }
                else if((edtDiaChiNote.getText().toString().equals("")))
                {
                    Toast.makeText(AddNoteActivity.this,"Nhập địa chỉ",Toast.LENGTH_SHORT).show();
                }


                else if((edtGhiChuNote.getText().toString().equals("")))
                {

                    Toast.makeText(AddNoteActivity.this,"Nhập ghi chú",Toast.LENGTH_SHORT).show();
                }

                else if((edtSoTienNote.getText().toString().equals("")))
                {
                    Toast.makeText(AddNoteActivity.this,"Nhập số tiền",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    myNote mynote = new myNote(loaiNote,xeNote,tvGioNote.getText().toString(),tvNgayNote.getText().toString(),edtKmNote.getText().toString(),edtDiaChiNote.getText().toString(),edtDungTichNote.getText().toString(),edtGhiChuNote.getText().toString(),edtSoTienNote.getText().toString(),maxid+1);
                    myRef.child(method.nameGmail(email)).child("ListNote").child("list").child(String.valueOf(maxid+1)).setValue(mynote);
                    myRef.child(method.nameGmail(email)).child("ListNote").child(("id")).setValue(maxid+1);
                    Toast.makeText(AddNoteActivity.this,"Thêm Note thành công",Toast.LENGTH_SHORT).show();
                    kmUpdate(Integer.parseInt(mynote.getKmNote()),mynote.getXeNote());
                    finish();
                }




            }
        });
        btnXoaNote.setOnClickListener(new View.OnClickListener() {
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


    private void getIdMax() {
        myRef.child(method.nameGmail(email)).child("ListNote").child(("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.getValue(int.class)==null)
                {
                    myRef.child(method.nameGmail(email)).child("ListNote").child(("id")).setValue(0);
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
                DatePickerDialog dpd = new DatePickerDialog(AddNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                mTimePicker = new TimePickerDialog(AddNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                    arrayXeNote.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                       myVehicles myxe= dataSnapshot.getValue(myVehicles.class);
                        arrayXeNote.add(myxe.TenXe);


                    }
                    ArrayAdapter arrayAdapterXeNote= new ArrayAdapter(AddNoteActivity.this, android.R.layout.simple_spinner_item,arrayXeNote);
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
               KmNow();

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
        snXeNote.setSelection(0);
        snLoaiNote.setSelection(0);
        tvGioNote.setText("Chọn giờ");
        tvNgayNote.setText("Chọn ngày");
        edtKmNote.setText("");
        edtDiaChiNote.setText("");
        edtDungTichNote.setText("");
        edtSoTienNote.setText("");


    }


    void kmUpdate(int km,String xe)
    {

        myRef= database.getReference();
        myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        myVehicles myVehicles= dataSnapshot.getValue(myVehicles.class);
                        int i=0;
                        if (myVehicles.getTenXe().equals(xe))
                        {
                            if(i==0)
                            {
                                myXeUpDate= myVehicles;
                                myXeUpDate.setKmHienTai(km);

                                DatabaseReference myKmUpdate = database.getReference();
                                myKmUpdate.child(method.nameGmail(email)).child("ListVehicles").child("list").child(String.valueOf(myXeUpDate.getStt())).setValue(myXeUpDate);
                                i++;
                            }

                            break;

                        }

                    }
                }

            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });

    }
    int KmNow()
    {
        myRef= database.getReference();
        myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        myVehicles myVehicles= dataSnapshot.getValue(myVehicles.class);
                        if (myVehicles.getTenXe().equals(xeNote))
                        {
                             kmHienTai=  myVehicles.getKmHienTai();

                        }

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return kmHienTai;
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





    }
}