package com.example.rotomugear;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity implements View.OnClickListener{

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

    private TextView timeView;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;

    private int second = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(getResources().getString(R.string.stop_watch).toString());

        timeView = (TextView)findViewById(R.id.time_view);
        startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        stopButton = (Button)findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        resetButton = (Button)findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);

        //秒表逻辑
        runTimer();
        //new RunTimeTask().execute();

        if (savedInstanceState!=null){
            isRunning = savedInstanceState.getBoolean("isRunning");
            second =savedInstanceState.getInt("second");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putInt("second",second);
    }

    public void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning){
                    second++;
                    refreshTimeView();
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void refreshTimeView(){
        int s = second%60;
        int m = (second/60)%60;
        int h = (second/3600);

        String time = String.format("%02d:%02d:%02d", h, m, s);
        timeView.setText(time);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_button:
                isRunning = true;
                break;
            case R.id.stop_button:
                isRunning = false;
                break;
            case R.id.reset_button:
                isRunning = false;
                second = 0;
                refreshTimeView();
                break;
            default:
                break;
        }
    }

    //使用AsyncTask执行秒表逻辑
    class RunTimeTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (isRunning){
                            second++;
                            publishProgress();
                        }
                    }
                }
            }).start();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            refreshTimeView();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
