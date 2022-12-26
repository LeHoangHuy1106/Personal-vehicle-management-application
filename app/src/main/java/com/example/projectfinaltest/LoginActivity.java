package com.example.projectfinaltest;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {

    EditText edtGamail,edtPassword;
    Button btnLogin, btnCreate, btnForget;
    FirebaseAuth mAuth;
    String email;
    String pass;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseUser user;
    int RC_SIGN_IN= 123;
    private static final String TAG = MainActivity.class.getSimpleName();
    CallbackManager callbackManager;
    LoginButton fbLoginButton;

    @Override
    protected void onStart() {
        super.onStart();
        init();
        Toast.makeText(LoginActivity.this,user.toString(),Toast.LENGTH_LONG).show();
        if(user== null)
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
          //  intent.putExtra("email", "kc80943@gmail.com");

            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        //https://developers.facebook.com/docs/facebook-login/permissions#reference
        fbLoginButton.setReadPermissions("email");

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "======Facebook login success======");
                Log.d(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
                Toast.makeText(LoginActivity.this, "Login Facebook success.", Toast.LENGTH_SHORT).show();

                getFbInfo();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Facebook cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "======Facebook login error======");
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(LoginActivity.this, "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });


        createRequest();
        findViewById(R.id.google_signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });
    }



    private void getFbInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                Log.i("Login: ", me.optString("name"));
                                Log.i("ID: ", me.optString("id"));

                                Toast.makeText(LoginActivity.this, "Name: " + me.optString("name"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "ID: " + me.optString("id"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private void createRequest()
    {

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("782341718789-5nsltpf8v6m7pou3tgdlbol8q9j03t07.apps.googleusercontent.com")
            .requestEmail()
            .build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();


        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
            //    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                // Google Sign In failed, update UI appropriately
             //   Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                            GoogleSignInAccount t = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
                            Toast.makeText(LoginActivity.this,"gmail: "+  t.getEmail(),Toast.LENGTH_LONG).show();
                            intent.putExtra("email",t.getEmail() );
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this,"Đăng nhập thất bại",Toast.LENGTH_LONG).show();


                        }
                    }
                });
    }

    private void login()
    {

        email = edtGamail.getText().toString();
        pass = edtPassword.getText().toString();
        if(email.equals(""))
        {
            Toast.makeText(LoginActivity.this,"Vui lòng nhập Email của ban",Toast.LENGTH_LONG).show();
            return;
        }
        else if(pass.equals("")){
            Toast.makeText(LoginActivity.this,"Vui lòng nhập Mật khẩu của ban",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);

                        i.putExtra("email",email);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                    }


                }
            });

        }

    }

    private void create() {
        Intent i= new Intent(LoginActivity.this,CreateAccountActivity.class);
         startActivity(i);
        
    }

    void init()
    {
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        edtGamail= findViewById(R.id.edtMyGmail);
        edtPassword = findViewById(R.id.edtMyPassword);
        btnCreate= findViewById(R.id.btnCreate);
        btnLogin= findViewById(R.id.btnCreateAc);
        btnForget= findViewById(R.id.btnForget);
    }


}