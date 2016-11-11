package com.youtube.sorcjc.tabdeportes.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.youtube.sorcjc.tabdeportes.ui.activity.PanelActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, PanelActivity.class);
        startActivity(intent);
        finish();
    }
}
