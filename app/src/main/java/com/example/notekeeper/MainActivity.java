package com.example.notekeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setSupportActionBar(mToolbar);

        mFab.setOnClickListener(View -> startActivity(new Intent(this, NoteActivity.class)));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_notes, R.id.nav_courses)
                .setOpenableLayout(mDrawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, navController);

        //Listener to handle drawer items that do not have fragment to navigate
        mNavigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.nav_share) {
                handleShare();
            }
            if(id == R.id.nav_send) {

            }
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController);
            //This is for closing the drawer after acting on it
            mDrawer.closeDrawer(GravityCompat.START);
            return true;
        });

//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            Log.d(TAG, "onCreate: " + destination.getLabel());
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavDrawerHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: " + id);
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
//        if(id == R.id.nav_share) {
//            handleShare();
//            return true;
//        }
//        if(id == R.id.nav_send) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initializeViews() {
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
    }

    private void handleShare() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString(getString(R.string.pref_user_favorite_social_key), "");
        Snackbar.make(findViewById(R.id.nav_host_fragment),  "Share to => "+ url, Snackbar.LENGTH_SHORT).show();
        Log.d(TAG, "handleShare: " + sharedPreferences.getString(getString(R.string.pref_user_favorite_social_title), ""));
    }

    ///////////////////////////////////////////////////////////////////////////
// clean logd, commit the pass to lesson sqlite
///////////////////////////////////////////////////////////////////////////
    private void updateNavDrawerHeader() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        View headerView = mNavigationView.getHeaderView(0);
        TextView textviewUserName = headerView.findViewById(R.id.textview_user_name);
        TextView textviewUserEmail = headerView.findViewById(R.id.textview_user_email);

        textviewUserName.setText(sharedPreferences.getString(getString(R.string.pref_display_name_key), ""));
        textviewUserEmail.setText(sharedPreferences.getString(getString(R.string.pref_user_email_key), ""));
    }
}