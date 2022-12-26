package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class CreateAccountActivity extends AppCompatActivity {

    EditText myGmail,myPass,myPass2;
    Button btnCreteAcc;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        init();
        btnCreteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String gmail,pass1,pass2;
        gmail= myGmail.getText().toString();
        pass1= myPass.getText().toString();
        pass2= myPass2.getText().toString();
        if( gmail.equals(""))
        {
            Toast.makeText(CreateAccountActivity.this,"Vui lòng nhập Email của ban",Toast.LENGTH_LONG).show();
            return;
        }
        else if( pass1.equals(""))
        {
            Toast.makeText(CreateAccountActivity.this,"Vui lòng nhập mật khẩu của ban",Toast.LENGTH_LONG).show();
            return;
        }
        else if( pass2.equals(""))
        {
            Toast.makeText(CreateAccountActivity.this,"Vui lòng nhập lại mật khẩu của ban",Toast.LENGTH_LONG).show();
            return;
        }
        else if ( !pass2.equals(pass1))
        {
            Toast.makeText(CreateAccountActivity.this,"Mật khẩu nhập lại không chính xác",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(gmail,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"Tạo tài khoản thành công",Toast.LENGTH_LONG).show();
                        Intent i= new Intent(CreateAccountActivity.this,personalnformationActivity.class);
                        i.putExtra("email",gmail);
                        startActivity(i);

                    }
                    else
                    {
                        Toast.makeText(CreateAccountActivity.this, "Tạo tài khoản thất bại", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }

    void init()
    {
        mAuth=FirebaseAuth.getInstance();
        myGmail= findViewById(R.id.edtMyGmail);
        myPass= findViewById(R.id.edtMyPassword);
        myPass2=findViewById(R.id.edtMyPassword2);
        btnCreteAcc= findViewById(R.id.btnCreateAc);
    }
}