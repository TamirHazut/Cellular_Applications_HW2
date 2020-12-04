package com.example.war.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.war.fragment.Fragment_Main;

public class Activity_Base extends AppCompatActivity {
    protected boolean isDoubleBackPressToClose = true;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            backToMain();
        } else {
            playerExitValidate();
        }
    }

    private void playerExitValidate() {
        if (this.isDoubleBackPressToClose) {
            if (this.mBackPressed + this.TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            }
            else {
                Toast.makeText(this, "Tap back button again to exit", Toast.LENGTH_SHORT).show();
            }
            this.mBackPressed = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    public void backToMain() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(Fragment_Main.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
