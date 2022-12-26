package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.DatePicker;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
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
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddVehiclesActivity extends AppCompatActivity {
    RecyclerView rvXe;
    VehiclesAdapter vehiclesAdapter;
    ListVehicles list= new ListVehicles();
    FirebaseDatabase database;
    DatabaseReference myRef;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicles);
        init();
        control();
        getEmail();

     //   rvXe.setLayoutManager(new LinearLayoutManager(AddVehiclesActivity.this));
       rvXe.setLayoutManager(new GridLayoutManager(AddVehiclesActivity.this,1));
        vehiclesAdapter= new VehiclesAdapter(AddVehiclesActivity.this,list);
        rvXe.setAdapter(vehiclesAdapter);




        initArrayList();

    }
    void getEmail()
    {
        Intent i= getIntent();
        email = i.getStringExtra("email");

    }

    private void initArrayList() {
        DatabaseReference mylistXe = database.getReference().child(method.nameGmail(email)).child("ListVehicles").child("list");
        mylistXe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(list!=null||!list.listDL.isEmpty())
                {
                    int i=0;
                    list.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        myVehicles myxe= dataSnapshot.getValue(myVehicles.class);
                        list.add(myxe);
                    }
                    vehiclesAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addvehicles, menu);
    return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case  R.id.iconThem:

                Intent i = new Intent(AddVehiclesActivity.this,AddInfoVehicleActivity.class);
                i.putExtra("email",email);
                startActivityForResult(i,123);

        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 123:
                myVehicles myxe = list.get(item.getGroupId());
                Intent i = new Intent(AddVehiclesActivity.this,EditInfoVehicleActivity.class);
                i.putExtra("email",email);
                i.putExtra("xe",myxe);
                startActivityForResult(i,123);
                break;
            case 456:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddVehiclesActivity.this);
                alertDialogBuilder.setMessage("Bán có muốn xóa sản phẩm này!");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.child(method.nameGmail(email)).child("ListVehicles").child("list").child(String.valueOf(list.get(item.getGroupId()).getStt())).removeValue();

                    }
                });
                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //không làm gì
                    }
                });
                alertDialogBuilder.show();
                break;
            case 789:
                final Dialog dialog= new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.thaynhotdialog);
                Window window=  dialog.getWindow();
                if(window==null)
                {

                }
                else {
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams windowAttibutes = window.getAttributes();
                    windowAttibutes.gravity = Gravity.CENTER;
                    window.setAttributes((windowAttibutes));
                    TextView mess = dialog.findViewById(R.id.txtTitleThayNhot);
                    TextView kmConLai = dialog.findViewById(R.id.tvKmConLai);
                    TextView ngaydukien= dialog.findViewById(R.id.tvNgayDuKien);
                    myRef.child(method.nameGmail(email)).child("ListNote").child("list").addValueEventListener(new ValueEventListener() {
                        ListNote listNote= new ListNote();
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(listNote!=null||!listNote.listDL.isEmpty())
                            {
                                listNote.clear();
                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    myNote mynote= dataSnapshot.getValue(myNote.class);
                                    listNote.add(mynote);


                                }
                                /// km hẹn
                                int kmThayNhotGanNhat= listNote.kmThayNhoGanNhat(list.get(item.getGroupId()).getTenXe());
                                int lichnhacthaynhot = list.GetKmThayNhobyXe(list.get(item.getGroupId()).getTenXe());
                                int kmhientai= list.get(item.getGroupId()).getKmHienTai();
                                kmConLai.setText(""+(kmThayNhotGanNhat+lichnhacthaynhot-kmhientai)+" km");
                                /// ngày hẹn
                                int thanghen =  list.getNgayHenThayNhotbyXe(list.get(item.getGroupId()).getTenXe());
                                String ngayThayNhotGanNhat = listNote.NgayThayNhoGanNhat(list.get(item.getGroupId()).getTenXe());
                                //
                                //

                                if( ngayThayNhotGanNhat.equals(""))
                                {
                                    ngayThayNhotGanNhat= list.get(item.getGroupId()).ngaySoHuu;
                                }
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar currentTime  = Calendar.getInstance();


                                try{
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // here set the pattern as you date in string was containing like date/month/year
                                    Date d = sdf.parse(ngayThayNhotGanNhat);
                                    d.setMonth(d.getMonth()+thanghen);

                                    Toast.makeText(AddVehiclesActivity.this,"."+d ,Toast.LENGTH_LONG);
                                    ngaydukien.setText(""+d.getDay()+"-"+"-"+d.getMonth());
                                }catch(ParseException ex){
                                    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
                                }
                                currentTime.add(currentTime.MONTH,thanghen);




                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });





                    mess.setText("Lịch thay nhớt dự kiến của "+ list.get(item.getGroupId()).getTenXe().toString());
                    kmConLai.setText(""+ "");

                    




                }
                dialog.show();
        }




        return true;
    }

    void init()
    {
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();

        rvXe= findViewById(R.id.rvXe);
    }
    void control() {
        // ánh xạ menubottomNAV
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        // mặc định chọn
        bottomNavigationView.setSelectedItemId(R.id.itemVehicles);
        // sự kiện chọn item nav
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemUtilities:
                        Intent i = new Intent(getApplicationContext(), UtilitiesActivity.class);
                        i.putExtra("email",email);
                        startActivity(i);

                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemMain:
                        Intent j = new Intent(getApplicationContext(), MainActivity.class);
                        j.putExtra("email",email);
                        startActivity(j);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itemVehicles:

                        return true;
                }
                return false;
            }
        });

    }
}