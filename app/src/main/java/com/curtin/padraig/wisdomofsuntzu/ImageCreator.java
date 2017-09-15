package com.curtin.padraig.wisdomofsuntzu;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by padraig on 13/09/2017.
 * Class to add text to an image and save it in the file system
 */

class ImageCreator {

    private Context myContext;

    ImageCreator(Context context)
    {
        myContext = context;    //Use getApplicationContext()
    }

    Context getMyContext(){
        return myContext;
    }

    public Bitmap addTextToImageBitmap(String quote){
        Bitmap rectangleBitmap = Bitmap.createBitmap(getScreenWidth(), getScreenHeight(), Bitmap.Config.RGB_565);    //Want a plain black background the size of the screen
        Canvas canvas = new Canvas(rectangleBitmap);
        float scale = myContext.getResources().getDisplayMetrics().density;
        TextPaint paint = new TextPaint();
        paint.setColor(Color.rgb(255, 255, 255)); // Text Color
        //paint.setStrokeWidth(12); // Text Size
        paint.setTextSize((int)20*scale);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern
        // set text width to canvas width minus 72dp padding. Needed to avoid text running off right of screen in wallpaper due to android stretching wallpaper over three screens.
        int textWidth = canvas.getWidth() - (int) (72 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout( quote, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (rectangleBitmap.getWidth() - textWidth)/2;
        float y = (rectangleBitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return rectangleBitmap;
    }


    //Might not need this.
    String saveImageToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(myContext);                       //Use getApplicationContext()
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);          // path to /data/data/yourapp/app_data/imageDir
        File file=new File(directory,"WisdomOfSunTzuWallpaper.jpg");         // Create imageDir

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
