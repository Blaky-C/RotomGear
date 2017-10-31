package com.example.rotomugear;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class BaiduLBSActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_son, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Toolbar toolbar;
    private ActionBar actionBar;

    private LocationClient client;
    private MyLocationListener listener;

    private TextView longitudeView;
    private TextView latitudeView;
    private TextView countryView;
    private TextView provinceView;
    private TextView cityView;
    private TextView districtView;
    private TextView streetView;
    private TextView locateMethodView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_lbs);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(getResources().getString(R.string.location).toString());

        client = new LocationClient(getApplicationContext());
        listener = new MyLocationListener();
        client.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        client.setLocOption(option);

        longitudeView = (TextView)findViewById(R.id.longitude);
        latitudeView = (TextView)findViewById(R.id.latitude);
        countryView = (TextView)findViewById(R.id.country);
        provinceView = (TextView)findViewById(R.id.province);
        cityView = (TextView)findViewById(R.id.city);
        districtView = (TextView)findViewById(R.id.district);
        streetView = (TextView)findViewById(R.id.street);
        locateMethodView = (TextView)findViewById(R.id.locate_method);

        //申请权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }

        fab = (FloatingActionButton)findViewById(R.id.locate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main", client.isStarted()+"");
                if (client.isStarted()){
                    //client.stop();
                    client.restart();
                }else {
                    client.start();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.stop();
    }

    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location) {
            longitudeView.setText(location.getLongitude()+"");
            latitudeView.setText(location.getLatitude()+"");
            countryView.setText(location.getCountry().toString());

            provinceView.setText(location.getProvince());
            cityView.setText(location.getCity());
            districtView.setText(location.getDistrict());
            streetView.setText(location.getStreet());

            if (location.getLocType()==BDLocation.TypeGpsLocation){
                locateMethodView.setText("GPS");
            }else if (location.getLocType()==BDLocation.TypeNetWorkLocation){
                locateMethodView.setText("网络");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                }
                break;
            default:
        }
    }


}
