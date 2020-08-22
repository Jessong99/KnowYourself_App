package com.example.knowyourself;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //set up drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,
                R.string.navigation_bar_open, R.string.navigation_bar_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
