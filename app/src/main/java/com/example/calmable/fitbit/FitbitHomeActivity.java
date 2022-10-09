package com.example.calmable.fitbit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.calmable.Home;
import com.example.calmable.R;
import com.example.calmable.databinding.ActivityFitbitHomeBinding;
import com.google.android.material.navigation.NavigationView;



public class FitbitHomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFitbitHomeBinding binding;
    private AuthStateManager mStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFitbitHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.appBarHome.toolbar);

        mStateManager = AuthStateManager.getInstance(this);

        DrawerLayout drawerLayout  = binding.drawerLayout;
        NavigationView navView  = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.nav_home), drawerLayout)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(
                navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutbtn) {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOut(){
        mStateManager.clear();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}