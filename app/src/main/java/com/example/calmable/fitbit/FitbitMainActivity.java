package com.example.calmable.fitbit;


import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.calmable.BuildConfig;
import com.example.calmable.Home;
import com.example.calmable.R;
import com.example.calmable.data.AlertService;
import com.example.calmable.databinding.ActivityFitbitMainBinding;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import org.jetbrains.annotations.NotNull;


public class FitbitMainActivity extends AppCompatActivity {

    private ActivityFitbitMainBinding binding;
    private AuthorizationService authService;
    private AuthStateManager mStateManager;
    private final int RecordAudioRequestCode = 1;
    private final int RC_AUTH = 1;
    @NotNull

    public final int getRecordAudioRequestCode() {
        return this.RecordAudioRequestCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFitbitMainBinding binding = ActivityFitbitMainBinding.inflate(this.getLayoutInflater());

        setContentView(binding.getRoot());

        //Getting instance of state
        mStateManager = AuthStateManager.getInstance(this);

        //If user is already logged in taking the user to the home page
        if (mStateManager.getCurrent().isAuthorized()) {
            Intent intent = new Intent(this, FitbitHomeActivity.class);
            startActivity(intent);
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        Button loginbtn = binding.loginbtn;
        ProgressBar loading = binding.loading;

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);

                //Creating configuration for auth by providing the urls (Urls are specified in the build gradle file)
                AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                        Uri.parse(BuildConfig.AUTH_URL),  // authorization endpoint
                        Uri.parse(BuildConfig.TOKEN_URL) // token endpoint
                );


                AuthorizationRequest authRequest = new AuthorizationRequest.Builder(
                        serviceConfig,  // the authorization service configuration
                        BuildConfig.CLIENT_ID,  // the client ID, typically pre-registered and static
                        ResponseTypeValues.CODE,  // the response_type value: we want a code
                        Uri.parse(BuildConfig.REDIR_SCHEME)
                ).setScope("heartrate").build(); //Currently we only need access to get heart rate

                //val authRequest = authRequestBuilder.build()
                authService = new AuthorizationService(getApplicationContext());
                Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);
                startActivityForResult(authIntent, RC_AUTH);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_AUTH) {
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);

            Context context = this;
            if (resp != null) {
                mStateManager.updateAfterAuthorization(resp, ex);
                authService.performTokenRequest(
                        resp.createTokenExchangeRequest(),
                        new AuthorizationService.TokenResponseCallback() {
                            @Override public void onTokenRequestCompleted(
                                    TokenResponse resp, AuthorizationException ex) {
                                if (resp != null) {
                                    mStateManager.updateAfterTokenResponse(resp, ex);

                                    Intent intent = new Intent(context, FitbitHomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    AlertService.displaySnackBar(binding.view, "Authorization failed", false);
                                    binding.loading.setVisibility(View.INVISIBLE);
                                }
                            }
                        });

            } else {
                AlertService.displaySnackBar(binding.view, "Authorization failed", false);
                binding.loading.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.RECORD_AUDIO },
                    RecordAudioRequestCode
            );
        }
    }


    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}