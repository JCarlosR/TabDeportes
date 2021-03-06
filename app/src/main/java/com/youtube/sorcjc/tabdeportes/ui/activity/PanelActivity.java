package com.youtube.sorcjc.tabdeportes.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.youtube.sorcjc.tabdeportes.Global;
import com.youtube.sorcjc.tabdeportes.R;
import com.youtube.sorcjc.tabdeportes.ui.fragment.PostDialogFragment;
import com.youtube.sorcjc.tabdeportes.ui.fragment.SportFragment;
import com.youtube.sorcjc.tabdeportes.ui.fragment.HomeFragment;

import static android.R.id.toggle;

public class PanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Hide the title
            actionBar.setDisplayShowTitleEnabled(false);
        }

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);

        // Custom icon for the drawer toggle
        drawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_settings_24dp, getTheme());
        drawerToggle.setHomeAsUpIndicator(drawable);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // And also a custom action !!!
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Default / Initial fragment
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.content_panel, new HomeFragment())
            .commit();

        // My custom image will work as a drawer toggle icon
        ImageView toolbarIcon = (ImageView) findViewById(R.id.toolbarIcon);
        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // These actions are performed by default in a drawer toggle
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // It comes from a notification?
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean from_notification = bundle.getBoolean("from_notification");
            if (from_notification) {
                String post_categories = bundle.getString("post_categories");
                String post_title = bundle.getString("post_title");
                String post_date = bundle.getString("post_date");
                String post_content = bundle.getString("post_content");
                String post_image = bundle.getString("post_image");
                startPostDialogFromNotification(post_categories, post_title, post_date, post_content, post_image);
            }
        }

        setupInterstitial();
    }

    private void setupInterstitial() {
        // [instantiate_interstitial_ad]
        // Create an InterstitialAd object. This same object can be re-used whenever you want to
        // show an interstitial.
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        // [create_interstitial_ad_listener]
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
    }

    public void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            ((Global) getApplicationContext()).resetPostsViewed();
        }
    }
    /**
     * Load a new interstitial ad asynchronously.
     */
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();

        if (! mInterstitialAd.isLoaded())
            requestNewInterstitial();
    }

    private void startPostDialogFromNotification(String post_categories, String post_title, String post_date, String post_content, String post_image) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PostDialogFragment newFragment = PostDialogFragment.newInstance(
                post_categories, post_title, post_date, post_content, post_image
        );

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
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

        if (id == R.id.action_radio) {
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

            case R.id.item_double_A:
                fragment = SportFragment.newInstance("DobleA", 29);
                break;
            case R.id.item_MLB:
                fragment = SportFragment.newInstance("MLB", 28);
                break;
            case R.id.item_baseball_others:
                fragment = SportFragment.newInstance("Otras ligas", 30);
                break;

            case R.id.item_BSN:
                fragment = SportFragment.newInstance("BSN", 25);
                break;
            case R.id.item_NBA:
                fragment = SportFragment.newInstance("NBA", 24);
                break;
            case R.id.item_basketball_others:
                fragment = SportFragment.newInstance("Otras ligas", 26);
                break;

            case R.id.item_MMA:
                fragment = SportFragment.newInstance("MMA", 38);
                break;
            case R.id.item_NFL:
                fragment = SportFragment.newInstance("NFL", 35);
                break;
            case R.id.item_wheels:
                fragment = SportFragment.newInstance("Wheels & Lipstick", 241);
                break;
            case R.id.item_volleyball:
                fragment = SportFragment.newInstance("Voleibol", 41);
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
