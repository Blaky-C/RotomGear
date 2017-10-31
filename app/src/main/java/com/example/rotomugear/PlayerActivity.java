package com.example.rotomugear;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rotomugear.adapter.MusicAdapter;
import com.example.rotomugear.bean.Music;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{

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

    private List<Music> musicList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MusicAdapter adapter;
    private static ImageView musicImage;
    private static TextView musicName;
    private static TextView musician;
    private static Button startButton;
    private static Button pauseButton;
    private static Button stopButton;
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private static String path;

    private void initMusicList(){
        File dir = new File(Environment.getExternalStorageDirectory(), "Music");
        File[] files = dir.listFiles();
        for(File f: files){
            musicList.add(new Music(f));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_player);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(getResources().getString(R.string.music_player).toString());

        //申请读写内存权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            initMusicList();
        }

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MusicAdapter(musicList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        musicImage = (ImageView)findViewById(R.id.music_image);
        musicName = (TextView)findViewById(R.id.music_name);
        musician = (TextView)findViewById(R.id.musician);
        startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(this);
        pauseButton = (Button)findViewById(R.id.pause);
        pauseButton.setOnClickListener(this);
        stopButton = (Button)findViewById(R.id.stop);
        stopButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initMusicList();
                }else{
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    try {
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
        }
    }

    public static void clickItem(Music m){
        //更新界面
        musicImage.setImageResource(R.drawable.default_music);
        musicName.setText(m.getName());
        musician.setText(m.getArtist());
        path = m.getAbsolutePath();

        //释放mediaPlayer
        //播放音乐
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
