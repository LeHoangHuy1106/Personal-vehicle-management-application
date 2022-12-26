package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class UtilitiesActivity extends AppCompatActivity {
    ImageButton btnCaNhan,btnThongKe, btnLienLac, btnViTri;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities);
        control();
        getEmail();
        init();
        selection();
    }
   void selection(){
        btnCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personDialog(Gravity.CENTER);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UtilitiesActivity.this,StatisticActivity.class);
                i.putExtra("email",email);
                startActivityForResult(i,123);
            }
        });
        btnViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UtilitiesActivity.this,MapActivity.class);
                i.putExtra("email",email);
                startActivityForResult(i,123);
            }
        });


    }
    public void personDialog( int gravity) {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.persondialog);
        Window window=  dialog.getWindow();
        if(window==null)
        {
            return;
        }
        else
        {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttibutes = window.getAttributes();
            windowAttibutes.gravity = gravity;
            window.setAttributes((windowAttibutes));

            if (Gravity.CENTER== gravity)
            {
                dialog.setCancelable(true);
            }
            else
            {
                dialog.setCancelable(false);
            }

            ImageButton ibtnCaNhan =dialog.findViewById(R.id.ibtnCaNhan);
            ImageButton ibtnTaiKhoan = dialog.findViewById(R.id.ibtnTaiKhoan);
            ibtnCaNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(UtilitiesActivity.this, EditPersonaInfomationActivity.class);
                    i.putExtra("email",email);
                    startActivityForResult(i,123);
                }
            });
            ibtnTaiKhoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(UtilitiesActivity.this, AccountActivity.class);
                    i.putExtra("email",email);
                    startActivityForResult(i,123);
                }
            });
            dialog.show();

        }
    }









    void init(){
        btnCaNhan = findViewById(R.id.btnCaNhan);
        btnLienLac = findViewById(R.id.btnLienLac);
        btnThongKe= findViewById(R.id.btnThongKe);
        btnViTri=findViewById(R.id.btnViTri);
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
}