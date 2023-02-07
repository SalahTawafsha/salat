package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
    }

    public void salat(View view) {

    }

    public void khatma(View view) throws ClassNotFoundException {
        Intent intent = new Intent(this, Class.forName("com.example.test.MainActivity"));
        startActivity(intent);
    }
}