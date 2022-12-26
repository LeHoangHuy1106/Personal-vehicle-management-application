package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {
    RecyclerView rvST;
    StatisticAdapter Adapter;
    ListNote list = new ListNote();
    FirebaseDatabase database;
    DatabaseReference myRef;
    private  Spinner snXeNote;
    CheckBox cbThayNhot, cbDoXang, cbSuaXe;
    public static String email;
    TextView tvTongTien;
    ImageButton ibtnExport;
    private ArrayList<String> arrayXeNote  = new ArrayList<>();
    String txtThayNhot="", txtDoXang="", txtSuaXe ="";
    private  String xeNote= new String();
    String stringFile = Environment.getExternalStorageDirectory().getPath()+File.separator+"Statistic.xls";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        control();
        init();
        getEmail();
        createSpinner();
        changeCheckbox();
        initArrayList();




        rvST.setLayoutManager(new LinearLayoutManager(StatisticActivity.this));
        Adapter=  new StatisticAdapter(StatisticActivity.this,list);
        rvST.setAdapter(Adapter);

    }
    void getChoiceCheckBox()
    {
            if(cbSuaXe.isChecked())
            {
                txtSuaXe="sửa chửa";

            }
            else
            {
                txtSuaXe="";
            }
            if(cbDoXang.isChecked())
            {
                txtDoXang= "đổ xăng";
            }
            else
            {
                txtDoXang="";
            }
            if(cbThayNhot.isChecked())
            {
                txtThayNhot="thay nhớt";
            }
            else
            {
                txtThayNhot="";
            }
        initArrayList();

    }
    void changeCheckbox()
    {
        cbThayNhot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getChoiceCheckBox();
            }
        });
        cbSuaXe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getChoiceCheckBox();
            }
        });
        cbDoXang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getChoiceCheckBox();
            }
        });

    }
    private void initArrayList() {


        myRef.child(method.nameGmail(email)).child("ListNote").child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(list!=null||!list.listDL.isEmpty())
                {


                    list.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        myNote mynote= dataSnapshot.getValue(myNote.class);
                        if(xeNote.equals("Tất cả"))
                        {
                            if(mynote.getLoaiNote().equals(txtDoXang)||mynote.getLoaiNote().equals(txtSuaXe)||mynote.getLoaiNote().equals(txtThayNhot)) {
                                list.add(mynote);
                            }
                        }
                        else
                        {
                            if(mynote.getXeNote().equals(xeNote))
                            {
                                if(mynote.getLoaiNote().equals(txtDoXang)||mynote.getLoaiNote().equals(txtSuaXe)||mynote.getLoaiNote().equals(txtThayNhot)) {
                                    list.add(mynote);
                                }
                            }

                        }

                    }
                    xuatfile(list);
                    Adapter.notifyDataSetChanged();
                    tvTongTien.setText(list.SumMoney()+"");

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void createSpinner() {
        //......Spinner loại xe......//

        arrayXeNote = new ArrayList<>();

        myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (arrayXeNote != null || !arrayXeNote.isEmpty()) {
                    int i = 0;
                    arrayXeNote.clear();
                    arrayXeNote.add("Tất cả");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        myVehicles myxe = dataSnapshot.getValue(myVehicles.class);
                        arrayXeNote.add(myxe.TenXe);
                        i++;

                    }

                    ArrayAdapter arrayAdapterXeNote = new ArrayAdapter(StatisticActivity.this, android.R.layout.simple_spinner_item, arrayXeNote);
                    arrayAdapterXeNote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    snXeNote.setAdapter(arrayAdapterXeNote);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        snXeNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xeNote= arrayXeNote.get(position);
                getChoiceCheckBox();
                initArrayList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

        void init()
    {
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        rvST= findViewById(R.id.rvStatistic);
        tvTongTien =findViewById(R.id.tvSTTongTien);
        cbDoXang= findViewById(R.id.cbDoXang);
        cbSuaXe= findViewById(R.id.cbSuaXe);
        cbThayNhot= findViewById(R.id.cbThayNhot);
        snXeNote = findViewById(R.id.spST_XeNote);
        ibtnExport= findViewById(R.id.iconExport);
        snXeNote.setSelection(0);
        cbDoXang.setChecked(true);
        cbSuaXe.setChecked(true);
        cbThayNhot.setChecked(true);
    }
    void getEmail()
    {
        Intent i= getIntent();
        email = i.getStringExtra("email");
    }
    void control() {
        // ánh xạ menubottomNAV
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        // mặc định chọn
        bottomNavigationView.setSelectedItemId(R.id.itemUtilities);
        // sự kiện chọn item nav
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemUtilities:
                        Intent k = new Intent(getApplicationContext(), UtilitiesActivity.class);
                        k.putExtra("email",email);
                        startActivity(k);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemMain:
                        Intent j = new Intent(getApplicationContext(), MainActivity.class);
                        j.putExtra("email",email);
                        startActivity(j);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemVehicles:
                        Intent z = new Intent(getApplicationContext(), AddVehiclesActivity.class);
                        z.putExtra("email",email);
                        startActivity(z);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

    void xuatfile( ListNote list ){
        ibtnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportExcel(list);

            }
        });
    }


    void ExportExcel( ListNote list)
    {
        ActivityCompat.requestPermissions(StatisticActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        // set tiêu đề
        HSSFRow hssfRow = hssfSheet.createRow(0); // hàng thứ mấy
        HSSFCell hssfCell=hssfRow.createCell(0); // ô thứ mấy
        hssfCell.setCellValue("stt");
        hssfCell=hssfRow.createCell(1); // ô thứ mấy
        hssfCell.setCellValue("Tên xe");
        hssfCell=hssfRow.createCell(2); // ô thứ mấy
        hssfCell.setCellValue("Loại");
        hssfCell=hssfRow.createCell(3); // ô thứ mấy
        hssfCell.setCellValue("Ngày");
        hssfCell=hssfRow.createCell(4); // ô thứ mấy
        hssfCell.setCellValue("Km");
        hssfCell=hssfRow.createCell(5); // ô thứ mấy
        hssfCell.setCellValue("Tiền");
        // set Up date

        for ( int i=0 ; i < list.size();i++)
        {
            hssfRow = hssfSheet.createRow(i+1);

            hssfCell=hssfRow.createCell(0); // ô thứ mấy
            hssfCell.setCellValue(list.get(i).getStt());
            hssfCell=hssfRow.createCell(1); // ô thứ mấy
            hssfCell.setCellValue(list.get(i).getLoaiNote());
            hssfCell=hssfRow.createCell(2); // ô thứ mấy
            hssfCell.setCellValue(list.get(i).getXeNote());
            hssfCell=hssfRow.createCell(3); // ô thứ mấy
            hssfCell.setCellValue(list.get(i).getNgayNote());
            hssfCell=hssfRow.createCell(4); // ô thứ mấy
            hssfCell.setCellValue(list.get(i).getKmNote());
            hssfCell=hssfRow.createCell(5); // ô thứ mấy
            hssfCell.setCellValue(list.get(i).getSoTienNote());
        }
        hssfRow = hssfSheet.createRow(list.size()+2);
        hssfCell=hssfRow.createCell(4); // ô thứ mấy
        hssfCell.setCellValue("Tổng tiền");
        hssfCell=hssfRow.createCell(5); // ô thứ mấy
        hssfCell.setCellValue(list.SumMoney());

        //





        // đặt tiêu đề
        File filepath= new File(stringFile);
        try {
            if (!filepath.exists()) {

                filepath.createNewFile();

            }
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            hssfWorkbook.write(fileOutputStream);


            if (fileOutputStream != null) {
                Toast.makeText(StatisticActivity.this, "Xuất file thành công",Toast.LENGTH_LONG).show();
                Sendfile();
                fileOutputStream.flush();
                fileOutputStream.close();



            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    void Sendfile()
    {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("application/xls");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+stringFile));

        startActivity(Intent.createChooser(share,"CHIA SẺ FILE VỚI:..."));
    }


}
