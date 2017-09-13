package com.curtin.padraig.wisdomofsuntzu;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageCreator imageCreator = new ImageCreator(getApplicationContext());

        FloatingActionButton refreshButton = (FloatingActionButton) findViewById(R.id.refreshButton);
        final ImageView image = (ImageView) findViewById(R.id.content_main_image);
        refreshWallpaper(image,imageCreator);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshWallpaper(image,imageCreator);
            }
        });
    }

    void refreshWallpaper(ImageView image, ImageCreator imageCreator){
        Bitmap wallpaper = imageCreator.addTextToImageBitmap(getString(R.string.quote1));
        image.setImageBitmap(wallpaper);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try{
            wallpaperManager.setBitmap(wallpaper);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
