package com.example.rotomugear;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_setup:
                Toast.makeText(this, "Function hasn't been finished.", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(drawer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Toolbar toolbar;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private NavigationView drawer;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_menu);
        }
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer = (NavigationView)findViewById(R.id.drawer);
        exitButton = (Button)findViewById(R.id.exit);
        drawer.setCheckedItem(R.id.mine);
        drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(drawer);
                return true;
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawer);
                finish();
            }
        });
    }

    public void IDCheckActivityStart(View view){
        Intent intent = new Intent(this, IDCheckActivity.class);
        startActivity(intent);
    }

    public void CryptionActivityStart(View view){
        Intent intent = new Intent(this, CryptionActivity.class);
        startActivity(intent);
    }

    public void StopwatchActivityStart(View view){
        Intent intent = new Intent(this, StopwatchActivity.class);
        startActivity(intent);
    }

    public void PlayerActivityStart(View view){
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }

    public void BaiduLBSActivityStart(View view){
        Intent intent = new Intent(this, BaiduLBSActivity.class);
        startActivity(intent);
    }
}
