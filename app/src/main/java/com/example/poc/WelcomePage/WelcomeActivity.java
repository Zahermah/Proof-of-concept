package com.example.poc.WelcomePage;

import android.content.Intent;
import android.os.Bundle;

import com.example.poc.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(getApplication(), MainActivity.class));
        finish();
    }
}
