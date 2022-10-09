package com.example.calmable.data;

import android.view.View;
import androidx.core.content.ContextCompat;

import com.example.calmable.R;
import com.google.android.material.snackbar.Snackbar;
/**
 * Helper class to display alert
 * */
public class AlertService {

    public static void displaySnackBar(View view, CharSequence text, Boolean isSuccess) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        View snackBarView  = snackbar.getView();
        if (isSuccess) {
            snackBarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.success));
        } else {
            snackBarView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.error));
        }
        snackbar.show();
    }
}
