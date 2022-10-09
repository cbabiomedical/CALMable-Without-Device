package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText new_password;
    EditText confirm_Password;
    Button btnResetPassword;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        new_password = (EditText) findViewById(R.id.newPw1);
        confirm_Password = (EditText) findViewById(R.id.newPw2);

        user = FirebaseAuth.getInstance().getCurrentUser();

        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);

        //set onclick listener for reset password button to reset the password
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newPw1 = new_password.getText().toString().trim();
                String newPw2 = confirm_Password.getText().toString().trim();

                //get new password
                if(newPw1.isEmpty()){
                    new_password.setError("Password is Required");
                    new_password.requestFocus();
                    return;
                }

                //confirm new password
                if(newPw2.isEmpty()){
                    confirm_Password.setError("Password is Required");
                    confirm_Password.requestFocus();
                    return;
                }

                //check whether password match
                if (!new_password.getText().toString().equals(confirm_Password.getText().toString())) {
                    confirm_Password.setError("Password is Required");
                    confirm_Password.requestFocus();
                }

                //check password length is more than 6 characters
                if(newPw1.length() < 6){
                    new_password.setError("Minimum Password length should be 6 characters!");
                    new_password.requestFocus();
                    return;
                }

                //update user password
                user.updatePassword(new_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //give toast on password update success
                        Toast.makeText(ChangePasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //give toast on password update fail
                        Toast.makeText(ChangePasswordActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}