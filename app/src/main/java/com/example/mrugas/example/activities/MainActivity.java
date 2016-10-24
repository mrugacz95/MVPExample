package com.example.mrugas.example.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.mrugas.example.R;
import com.example.mrugas.example.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
    }
}
