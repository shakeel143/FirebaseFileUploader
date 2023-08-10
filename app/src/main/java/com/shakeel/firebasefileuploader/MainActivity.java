package com.shakeel.firebasefileuploader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shakeel.stoast.Toaster;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toaster.simpleToast(this, "Hello its my first library");
    }
}