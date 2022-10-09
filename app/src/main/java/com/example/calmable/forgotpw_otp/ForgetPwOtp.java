package com.example.calmable.forgotpw_otp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calmable.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPwOtp extends AppCompatActivity {

    private String verificationId, password, phoneNo, whatToDo;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editTextOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw_otp);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
        editTextOTP = findViewById(R.id.editTextCode);

        // get all the data form intent
        password = getIntent().getStringExtra("password");
        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDo = getIntent().getStringExtra("whatToDo");


        String phonenumber = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode(phonenumber);


        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editTextOTP.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editTextOTP.setError("Not Valid");
                    editTextOTP.requestFocus();
                    return;
                }
                verifyCode(code);
                Intent intent = new Intent(ForgetPwOtp.this, EnterNewPwActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                //startActivity(intent);
            }
        });

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            //startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                            //Intent intent = new Intent(VerifyPhoneActivity.this,MainActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            //startActivity(intent);

                            if (whatToDo.equals("updateData")) {
//                                Intent intent = new Intent(getApplicationContext(), EnterNewPwActivity.class);
//                                intent.putExtra("phoneNo", phoneNo);
                                updateOldUserData();
                            }

                        } else {
                            Toast.makeText(ForgetPwOtp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void updateOldUserData() {

        Intent intent = new Intent(getApplicationContext(), EnterNewPwActivity.class);
        intent.putExtra("phoneNumber", phoneNo);
        startActivity(intent);
        finish();
    }

    private void sendVerificationCode(String number) {

        // visible progress bar
        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        //verifying the code
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        //this method called when the verification is completed
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                editTextOTP.setText(code);
                verifyCode(code);
            }

        }

        //if the verification fails
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ForgetPwOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


//    // [START resend_verification]
//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
//                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//    // [END resend_verification]
}



