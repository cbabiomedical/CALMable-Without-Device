package com.example.calmable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.List;

public class EnterPhoneActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private CountryCodePicker ccp;

    FirebaseUser mUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone);

        DatabaseReference rootDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();


        // initialize
        //spinner = findViewById(R.id.spinnerCountries);
        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));


        editText = findViewById(R.id.editTextPhone);
        ccp = findViewById(R.id.txtCcp);

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String code = ccp.getSelectedCountryCode();
                String country = ccp.getSelectedCountryEnglishName();
                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 9) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }


                //String phoneNo = "+" + code + number;
                String phoneNo = "+" + code + number;

                Log.d("TAG", "onClick: " + phoneNo);

                HashMap<String, Object> hashMap = new HashMap<>();


                hashMap.put("phoneNumber", phoneNo);
                rootDatabaseRef.child(userKey).updateChildren(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                Toast.makeText(EnterPhoneActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                //Intent intent=new Intent(UserPreferences.this,ProfileActivity.class);
                                //startActivity(intent);
                            }
                        });
                Toast.makeText(EnterPhoneActivity.this, "Successful !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EnterPhoneActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phoneNo", phoneNo);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }


    //    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Intent intent = new Intent(this, ProfileActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//            startActivity(intent);
//        }
//    }


}