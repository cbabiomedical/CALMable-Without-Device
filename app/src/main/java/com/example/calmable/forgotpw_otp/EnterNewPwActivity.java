package com.example.calmable.forgotpw_otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calmable.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterNewPwActivity extends AppCompatActivity {

    private EditText edNewPw1, edNewPw2;
    private Button changePwBtn;


    FirebaseAuth firebaseAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_new_pw);

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference rootdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        mUser = FirebaseAuth.getInstance().getCurrentUser();

        edNewPw1 =(EditText) findViewById(R.id.newPw1);
        edNewPw2 =(EditText) findViewById(R.id.newPw2);

        changePwBtn =(Button) findViewById(R.id.set_new_password_btn);

        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                if (edNewPw1.getText().toString().isEmpty()) {
//                    edNewPw1.setText("Required Field");
//                    return;
//                }
//
//                if (edNewPw2.getText().toString().isEmpty()) {
//                    edNewPw2.setError("Required Field");
//                    return;
//                }
//
//                if(!edNewPw1.getText().toString().equals(edNewPw2.getText().toString())){
//                    edNewPw1.setError("Password not match");
//                }


                // change password in profile
                FirebaseUser users = null;
                users.updatePassword(edNewPw1.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EnterNewPwActivity.this, "Password Updated, \nLogin Again", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EnterNewPwActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




                // Forgot password OTP

//                String phonenumber = getIntent().getStringExtra("phoneNo");
//                String newPassword = edNewPw2.getText().toString().trim();

//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//                reference.child("Users//password").setValue(newPassword);


//                startActivity(new Intent(getApplicationContext(), LoginUserActivity.class));
//                finish();
            }
        });

    }

    public void setNewPasswordBtn(View view) {

        if (edNewPw1.getText().toString().isEmpty()) {
            edNewPw1.setText("Required Field");
            return;
        }

        if (edNewPw2.getText().toString().isEmpty()) {
            edNewPw2.setError("Required Field");
            return;
        }

        if(!edNewPw1.getText().toString().equals(edNewPw2.getText().toString())){
            edNewPw1.setError("Password not match");
        }

        String phonenumber = getIntent().getStringExtra("phoneNumber");
        String newPassword = edNewPw2.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();


        user.updatePassword(edNewPw2.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(EnterNewPwActivity.this, "Password Updated, \nLogin Again", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EnterNewPwActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.child("Users").child(userKey).child("phoneNumber").setValue(newPassword);
//
//        startActivity(new Intent(getApplicationContext(), LoginUserActivity.class));
//        finish();
    }

}