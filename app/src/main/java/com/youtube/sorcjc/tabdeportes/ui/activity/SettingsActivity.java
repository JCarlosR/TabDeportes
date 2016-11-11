package com.youtube.sorcjc.tabdeportes.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.onesignal.OneSignal;
import com.youtube.sorcjc.tabdeportes.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.action_settings);
        }

        Button btnNotifications = (Button) findViewById(R.id.btnNotifications);
        btnNotifications.setOnClickListener(this);
        Button btnContact = (Button) findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // handle close button click here
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNotifications:
                showNotificationsDialog();
                break;

            case R.id.btnContact:
                showContactDialog();
                break;
        }
    }

    private void showNotificationsDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Notificaciones");
        adb.setMessage("¿Desea recibir notificaciones de TAB Deportes?");
        adb.setPositiveButton("Activar notificaciones", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                OneSignal.setSubscription(true);
                Toast.makeText(SettingsActivity.this, "Se han activado las notificaciones", Toast.LENGTH_SHORT).show();
            } });

        adb.setNegativeButton("Desactivar notificaciones", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                OneSignal.setSubscription(false);
                Toast.makeText(SettingsActivity.this, "Se han desactivado las notificaciones", Toast.LENGTH_SHORT).show();
            } });
        adb.show();
    }

    private void showContactDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("E-mail de contacto");
        adb.setMessage("info@tabsportshow.com");
        adb.setPositiveButton("Aceptar", null);
        adb.show();
    }
}
