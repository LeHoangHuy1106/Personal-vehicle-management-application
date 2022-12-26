package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class AccountActivity extends AppCompatActivity {

    Button btnDeleteData, btnChangePass, btnDeleteAccount;
    String email;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();
        getEmail();
        control();
        selection();
    }
    void getEmail()
    {
        Intent i= getIntent();
        email = i.getStringExtra("email");
    }
    void selection(){
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDataDialog(Gravity.CENTER);
            }
        });
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAccountDialog(Gravity.CENTER);
            }
        }
        );
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangepassDialog(Gravity.CENTER);
            }
        });

    }
    public void DeleteDataDialog( int gravity) {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_deletedata);
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


            TextView txtMess;
            EditText txtPass1, txtPass2;
            Button btnXacNhan;

            txtMess = dialog.findViewById(R.id.message);
            txtPass1= dialog.findViewById(R.id.edtPasswordDeleteData);
            txtPass2= dialog.findViewById(R.id.edtPasswordDeleteData2);
            btnXacNhan= dialog.findViewById(R.id.btnXacNhanDeleteDaTa);

            txtMess.setText("Xác thực tài khoản để xóa dữ liệu");
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtPass1.getText().toString().equals(""))
                    {
                        Toast.makeText(AccountActivity.this,"Bạn chưa nhập mật khẩu",Toast.LENGTH_LONG).show();
                    }
                    else if(txtPass2.getText().toString().equals(""))
                    {
                        Toast.makeText(AccountActivity.this,"Bạn chưa xác nhận mật khẩu",Toast.LENGTH_LONG).show();
                    }
                    else if(!txtPass1.getText().toString().equals(txtPass2.getText().toString()))
                    {
                        Toast.makeText(AccountActivity.this,"Mật khẩu không trùng khớp",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountActivity.this);
                        alertDialogBuilder.setMessage("Bạn có chắc chắn xóa dữ liệu này sẽ không thể lấy lại.");
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DeleteData(txtPass1.getText().toString());

                            }
                        });
                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        alertDialogBuilder.show();
                    }
                }
            });




            dialog.show();

        }
    }

    void DeleteData(String pass)
    {

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AccountActivity.this, "xóa dữ liệu thành công!", Toast.LENGTH_LONG).show();
                    myRef.child(method.nameGmail(email)).child("ListNote").removeValue();
                    myRef.child(method.nameGmail(email)).child("ListVehicles").removeValue();
                    finish();


                }
                else
                {
                    Toast.makeText(AccountActivity.this, "Mật khẩu không đúng", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
    public void DeleteAccountDialog( int gravity) {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_deletedata);
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


            TextView txtMess;
            EditText txtPass1, txtPass2;
            Button btnXacNhan;

            txtMess = dialog.findViewById(R.id.message);
            txtPass1= dialog.findViewById(R.id.edtPasswordDeleteData);
            txtPass2= dialog.findViewById(R.id.edtPasswordDeleteData2);
            btnXacNhan= dialog.findViewById(R.id.btnXacNhanDeleteDaTa);

            txtMess.setText("Xác thực tài khoản để xóa tài khoản");
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtPass1.getText().toString().equals(""))
                    {
                        Toast.makeText(AccountActivity.this,"Bạn chưa nhập mật khẩu",Toast.LENGTH_LONG).show();
                    }
                    else if(txtPass2.getText().toString().equals(""))
                    {
                        Toast.makeText(AccountActivity.this,"Bạn chưa xác nhận mật khẩu",Toast.LENGTH_LONG).show();
                    }
                    else if(!txtPass1.getText().toString().equals(txtPass2.getText().toString()))
                    {
                        Toast.makeText(AccountActivity.this,"Mật khẩu không trùng khớp",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountActivity.this);
                        alertDialogBuilder.setMessage("Bạn có chắc chắn xóa tài khoán này sẽ không thể lấy lại.");
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               deleteAccount(email,txtPass1.getText().toString());

                            }
                        });
                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        alertDialogBuilder.show();
                    }
                }
            });




            dialog.show();

        }
    }


    private void deleteAccount(String email, String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        if (user != null) {
            myRef.child(method.nameGmail(email)).removeValue();
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                                                Toast.makeText(AccountActivity.this, "Xóa tài khoản thành công,", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    });
        }
    }
    public void ChangepassDialog( int gravity) {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_deletedata);
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


            TextView txtMess, txtMTK;
            EditText txtPass1, txtPass2;
            Button btnXacNhan;

            txtMess = dialog.findViewById(R.id.message);
            txtMTK= dialog.findViewById(R.id.tvMKM);
            txtPass1= dialog.findViewById(R.id.edtPasswordDeleteData);
            txtPass2= dialog.findViewById(R.id.edtPasswordDeleteData2);
            btnXacNhan= dialog.findViewById(R.id.btnXacNhanDeleteDaTa);

            txtMess.setText("THAY ĐỔI MẬT KHẨU");
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtPass1.getText().toString().equals(""))
                    {
                        Toast.makeText(AccountActivity.this,"Bạn chưa nhập mật khẩu cũ",Toast.LENGTH_LONG).show();
                    }
                    else if(txtPass2.getText().toString().equals(""))
                    {
                        Toast.makeText(AccountActivity.this,"Bạn chưa xác nhận mật khẩu mới",Toast.LENGTH_LONG).show();
                    }
                    else if(txtPass2.getText().toString().length()<=6 )
                    {
                        Toast.makeText(AccountActivity.this,"Mật khẩu phải lớn hơn hoặc bằng 6 kí tự",Toast.LENGTH_LONG).show();
                    }
                    else if(txtPass1.getText().toString().equals(txtPass2.getText().toString()))
                    {
                        Toast.makeText(AccountActivity.this,"Mật khẩu trùng nhau",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountActivity.this);
                        alertDialogBuilder.setMessage("Bạn có chắc chắn việc thay đổi mật khẩu này!");
                        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ChangePassword(email,txtPass1.getText().toString(), txtPass2.getText().toString());

                            }
                        });
                        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        alertDialogBuilder.show();
                    }
                }
            });




            dialog.show();

        }
    }
    void  ChangePassword(String email, String pass, String newpass)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, pass);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AccountActivity.this,"Đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(AccountActivity.this,"Đổi mật khẩu không thành công",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                });
    }

    void init()
    {
        btnChangePass= findViewById(R.id.btnChangePassword);
        btnDeleteAccount= findViewById(R.id.btnDeleteAccount);
        btnDeleteData= findViewById(R.id.btnDeleteData);
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        mAuth=FirebaseAuth.getInstance();
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

}