package com.example.calmable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.calmable.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WalletFragment extends Fragment {

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
               binding.currentCoins.setText(String.valueOf(user.getCoins()));

               long musicCoinTest = user.getCoins();

                Intent ii=new Intent(getContext(), Home.class);
                ii.putExtra("name", musicCoinTest);

                // to save music coins
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.calmable", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("allMusicCoin", musicCoinTest);
                editor.commit();

                Log.d("TAG", "Total coins : " + musicCoinTest);

                //binding.currentCoins.setText(user.getCoins() + "");

            }
        });

        return binding.getRoot();
    }
}
