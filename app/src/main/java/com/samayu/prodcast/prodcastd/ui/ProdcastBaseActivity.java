package com.samayu.prodcast.prodcastd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.samayu.prodcast.prodcastd.SessionInfo;

import java.io.File;

import prodcastd.prodcast.samayu.com.prodcastd.R;

public abstract class ProdcastBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setContentView(int layoutId){
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_prodcast_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutId, activityContainer, true);
        initializeDrawer(fullView);
       TextView distributorName = (TextView)fullView.findViewById(R.id.distributorName);
       TextView screenName = (TextView)fullView.findViewById(R.id.screenName);
        if( SessionInfo.instance().getEmployee()!=null) {
            distributorName.setText(SessionInfo.instance().getEmployee().getDistributorName().toUpperCase());

        }
        screenName.setText(getProdcastTitle().toUpperCase());
        super.setContentView(fullView);

    }

    protected void initializeDrawer(DrawerLayout layout){
        Toolbar toolbar = (Toolbar)layout.findViewById(R.id.proToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.logo);
        DrawerLayout drawer = layout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) layout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_prodcast_base, null);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        final Intent intent;
        final Bundle b;
        if (id == R.id.nav_home) {
            intent =new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_orderEntry) {
            intent =new Intent(this, OrderEntryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_customer) {
            intent =new Intent(this, CustomersActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_passwordReset) {
            intent =new Intent(this, CustomerPasswordActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_logOut){
            File dir = getFilesDir();
            File file = new File(dir, "prodcastLogin.txt");
            SessionInfo.instance().destroy();
            boolean deleted = file.delete();
            intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        return true;
    }
    public abstract String getProdcastTitle();

}
