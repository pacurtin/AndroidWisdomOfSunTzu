package com.curtin.padraig.wisdomofsuntzu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    BroadcastReceiver mReceiver;
    ImageCreator imageCreator;
    String[] quotes;
    Bitmap newWallPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageCreator = new ImageCreator(getApplicationContext());
        quotes = getResources().getStringArray(R.array.sunTzuQuotes);

        FloatingActionButton refreshButton = (FloatingActionButton) findViewById(R.id.refreshButton);
        final ImageView image = (ImageView) findViewById(R.id.content_main_image);

        newWallPaper = refreshWallpaper(imageCreator, quotes);
        image.setImageBitmap(newWallPaper);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newWallPaper = refreshWallpaper(imageCreator, quotes);
                image.setImageBitmap(newWallPaper);
            }
        });

        RegisterAlarmBroadcast();
        //long firstAlarmTime = 1000*60*60*5;//5AM in milliseconds
        //long alarmInterval = 1000*60*60*24;//24Hrs in ms
        long alarmInterval = 1000*60;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmInterval , pendingIntent);
    }

    private void RegisterAlarmBroadcast() {
        mReceiver = new BroadcastReceiver() {
            // private static final String TAG = "Alarm Example Receiver";
            @Override
            public void onReceive(Context context, Intent intent) {
                refreshWallpaper(imageCreator, quotes);
            }
        };

        registerReceiver(mReceiver, new IntentFilter("sample"));
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("sample"), 0);
        alarmManager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
    }

    private void UnregisterAlarmBroadcast() {
        alarmManager.cancel(pendingIntent);
        getBaseContext().unregisterReceiver(mReceiver);
    }

    Bitmap refreshWallpaper(ImageCreator imageCreator, String[] quotes){
        // Overlays a random string from a string array over a black bitmap and sets it as the phones wallpaper. Returns the bitmap too.
        Random random = new Random();
        int randomQuoteNum = random.nextInt(quotes.length);
        String quote = quotes[randomQuoteNum];
        Bitmap wallpaper = imageCreator.addTextToImageBitmap(quote);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try{
            wallpaperManager.setBitmap(wallpaper);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return wallpaper;
    }

}
