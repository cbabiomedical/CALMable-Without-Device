package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginUserActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button signIn , home;

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        //set onclick listener for register button
        //register = (Button) findViewById(R.id.register);
        //register.setOnClickListener(this);

        //set onclick listener for signin button
        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar =(ProgressBar) findViewById(R.id.progressBar);

        //firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        //set onclick listener for forgot password button
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        home = (Button) findViewById(R.id.homebtn);
        home.setOnClickListener(this);

    }

    // main btns actions
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //go to sign in page
            case R.id.signIn:
                userLogin();
                break;
            //go to forget password page
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.homebtn:
                startActivity(new Intent(this, Home.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                break;
        }
    }

//    private void test() {
//
//        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("7nUpkp29epQ0rQxM20fYWVSfzuB2");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String name = snapshot.child("fullName").getValue().toString();
//                String email = snapshot.child("email").getValue().toString();
//                String age = snapshot.child("age").getValue().toString();
//                String phone = snapshot.child("phoneNo").getValue().toString();
//
//                Log.d(TAG, "onDataChange: check-------------" + name);
//                Log.d(TAG, "onDataChange: check-------------" + email);
//                Log.d(TAG, "onDataChange: check-------------" + age);
//                Log.d(TAG, "onDataChange: check-------------" + phone);
//
//                System.out.println(name);
//                System.out.println(email);
//                System.out.println(age);
//                System.out.println(phone);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    //    //get & display current user's profile
    @Override
    protected void onStart() {
        super.onStart();

        //signin authentication
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }




    //get user credentials & convert it back to string
    private void userLogin() {
        //get user email and password
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //check email is entered
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        //check whether email is valid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        //check password is entered
        if(password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }
        //check password length is more than 6 characters
        if(password.length() < 6){
            editTextPassword.setError("Minimum Password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.GONE);
        //signin with email and password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(LoginUserActivity.this, RoadMapVideoActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                     /*if (user.isEmailVerified()) {
                        // redirect to user profile
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }
                    else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG)
                                .show();
                    }*/

                }else {
                    Toast.makeText(LoginUserActivity.this,"LogIn Failed! Please check your username or password again",Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }


    public void btnUserRegister (View view) {
        startActivity(new Intent(getApplicationContext(), RegisterUser.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}