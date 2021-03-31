package com.cst2335.li000713;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tbar = findViewById(R.id.mytoolbar);
       setSupportActionBar(tbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bnv = findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.chat_item:
                message = "You clicked chatitem";
                startActivity(new Intent(this, ChatRoomActivity.class));
                break;
            case R.id.weather_item:
                message = "You clicked weatheritem";
                startActivity(new Intent(this, WeatherForecast.class));
                break;
            case R.id.back_item:
                message = "You clicked loginitem";
                Intent intent = new Intent();
                TestToolbar.this.setResult(500, intent);
                TestToolbar.this.finish();
                break;
            case R.id.item4:
                message = getResources().getString(R.string.overflow);
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId())
        {
            case R.id.chat_item:
                message = "You clicked chatitem";
                startActivity(new Intent(this, ChatRoomActivity.class));
                break;
            case R.id.weather_item:
                message = "You clicked weatheritem";
                startActivity(new Intent(this, WeatherForecast.class));
                break;
            case R.id.back_item:
                message = "You clicked loginitem";
                Intent intent = new Intent();
                TestToolbar.this.setResult(500, intent);
                TestToolbar.this.finish();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        Toast.makeText(this,"NavigationDrawer"+ message, Toast.LENGTH_LONG).show();
        return false;
    }
}