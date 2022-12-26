package com.example.projectfinaltest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvNote;
    NotesAdapter notesAdapter;
    ListNote list = new ListNote();
    FirebaseDatabase database;
    DatabaseReference myRef;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        init();
        control();
        getEmail();
        initArrayList();
        getInfo();
        rvNote.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        notesAdapter= new NotesAdapter(MainActivity.this,list);
        rvNote.setAdapter(notesAdapter);


    //




    }
    private void getInfo() {

        myRef.child(method.nameGmail(email)).child("PersonInfo").addValueEventListener(new ValueEventListener() {

                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if( snapshot.getValue(person.class)==null)
                    {
                        Intent i= new Intent(MainActivity.this,personalnformationActivity.class);
                        i.putExtra("email",email);
                        startActivity(i);
                    }



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
                    int i=0;
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                      myNote mynote= dataSnapshot.getValue(myNote.class);
                        list.add(mynote);
                        i++;
                    }
                    notesAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    void init()
    {
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        rvNote= findViewById(R.id.rvNote);
    }
    void getEmail()
    {
        Intent i= getIntent();
        email = i.getStringExtra("email");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addvehicles, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 123:
                myNote mynote = list.get(item.getGroupId());
                Intent i = new Intent(MainActivity.this,EditNoteActivity.class);
                i.putExtra("email",email);
                i.putExtra("note",mynote);
                startActivityForResult(i,123);
                break;
            case 456:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Bạn có muốn xóa Note này!");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.child(method.nameGmail(email)).child("ListNote").child("list").child(String.valueOf(list.get(item.getGroupId()).getStt())).removeValue();
                        Toast.makeText(MainActivity.this,"Xóa thành công",Toast.LENGTH_LONG).show();

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
        }




        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case  R.id.iconThem:

                Intent i = new Intent(MainActivity.this,AddNoteActivity.class);
                i.putExtra("email",email);
                startActivityForResult(i,123);

        }
        return true;
    }
    void control()
    {
        // ánh xạ menubottomNAV
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNav);
        // mặc định chọn
        bottomNavigationView.setSelectedItemId(R.id.itemMain);
        // sự kiện chọn item nav
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.itemUtilities:
                        Intent i= new Intent(getApplicationContext(),UtilitiesActivity.class);
                        i.putExtra("email",email);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.itemMain:
                        return true;
                    case R.id.itemVehicles:
                        Intent z = new Intent(getApplicationContext(),AddVehiclesActivity.class);
                        z.putExtra("email",email);
                        startActivity(z);
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });

    }




}