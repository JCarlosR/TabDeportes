package com.youtube.sorcjc.tabdeportes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.youtube.sorcjc.tabdeportes.R;
import com.youtube.sorcjc.tabdeportes.ui.fragment.SportFragment;
import com.youtube.sorcjc.tabdeportes.ui.fragment.HomeFragment;

public class PanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Default / Initial fragment
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.content_panel, new HomeFragment())
            .commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_radio) {
            showSoonMessage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSoonMessage() {
        new AlertDialog.Builder(this)
                .setTitle("TAB Deportes Radio")
                .setMessage("Pronto")
                .setPositiveButton(android.R.string.yes, null)
                .show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (id) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_baseball:
                fragment = SportFragment.newInstance("Baseball", 27);
                break;

            case R.id.nav_basketball:
                fragment = SportFragment.newInstance("Basketball", 23);
                break;

            case R.id.nav_football:
                fragment = SportFragment.newInstance("Futbol", 32);
                break;

            case R.id.nav_boxing:
                fragment = SportFragment.newInstance("Boxeo", 31);
                break;

            case R.id.nav_others:
                fragment = SportFragment.newInstance("Otros deportes", 33);
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction()
                .replace(R.id.content_panel, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}