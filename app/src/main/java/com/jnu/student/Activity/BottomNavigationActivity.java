package com.jnu.student.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.R;

public class BottomNavigationActivity extends AppCompatActivity {
    private BottomNavigationView btmNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_tasks);

    }
}