package com.example.calmable.data;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.calmable.fitbit.AuthStateManager;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationService;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;

/**
 * Helper class to perform API requests
 * */
public class APIService {

    private OkHttpClient client;
    private AuthStateManager mStateManager;
    private  AuthorizationService authService;

    public APIService(Context context){
        client= new OkHttpClient();

        mStateManager = AuthStateManager.getInstance(context);
        authService = new AuthorizationService(context.getApplicationContext());
    }
    /**
     * Method to perform a get request
     * */
    public void get(String url , Callback callback) {
        mStateManager.getCurrent().performActionWithFreshTokens(authService, new AuthState.AuthStateAction() {
            @Override
            public void execute(@Nullable String accessToken, @Nullable String idToken,
                                @Nullable AuthorizationException ex) {
                Request request = new Builder()
                        .url(url)
                        .addHeader("Authorization", String.format("Bearer %s", accessToken))
                        .build();

                Call call = APIService.this.client.newCall(request);
                call.enqueue(callback);
            }
        });
    }
}