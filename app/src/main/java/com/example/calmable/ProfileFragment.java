package com.example.calmable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    StorageReference storageReference;
    ImageView profileImage;
    private View view;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button settings = (Button) view.findViewById(R.id.settings);
        Button signOutBtn = (Button) view.findViewById(R.id.signOut);
        Button myDownloadsBtn = (Button) view.findViewById(R.id.myDownloadsBtn);
        Button myFavouritesBtn = (Button) view.findViewById(R.id.myFavouritesBtn);
        Button remindersBtn = (Button) view.findViewById(R.id.remindersBtn);
        Button calenderBtn = (Button) view.findViewById(R.id.calenderBtn);
        Button premiumBtn = (Button) view.findViewById(R.id.premiumBtn);
        Button rateUsBtn = (Button) view.findViewById(R.id.rateUsBtn);
        Button aboutAppBtn = (Button) view.findViewById(R.id.aboutAppBtn);

        //TextView displaynametextview = (TextView) view.findViewById(R.id.userName);

        //load profile picture to the imageview
        ImageView profileImage = (ImageView) view.findViewById(R.id.profileImage);

        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        StorageReference profileRef = storageReference.child("users/" + userid + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        getData();

        //go to edit profile page
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(in);
            }

        });

        myDownloadsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MyDownloadsActivity.class);
                startActivity(in);
            }

        });

        myFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MyFavouritesActivity.class);
                startActivity(in);
            }

        });

        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), LocationActivity.class);
                startActivity(in);
            }

        });

        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), CalenderActivity.class);
                startActivity(in);
            }

        });

        premiumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), PremiumActivity.class);
                startActivity(in);
            }

        });

        rateUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), RateUsActivity.class);
                startActivity(in);
            }

        });

        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AboutAppActivity.class);
                startActivity(in);
            }

        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), LoginUserActivity.class);
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(in);

            }

        });
        return view;
    }

    private void getData() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("fullName").getValue(String.class);

                Log.d("TAG", "edit profile: name-------------" + name);

                View view = getView();
                TextView textView = (TextView) view.findViewById(R.id.userName);
                textView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}