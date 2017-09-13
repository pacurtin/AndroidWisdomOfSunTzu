package com.curtin.padraig.wisdomofsuntzu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device
 */
@RunWith(AndroidJUnit4.class)
public class ImageCreatorTest {
    static private Context appContext;
    static private ImageCreator imageCreator;   //ide says this is a memory leak but this is just a test so does it matter?

    @BeforeClass
    public static void constructClass() {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        imageCreator = new ImageCreator(appContext);
    }

    @Test
    public void imageCreatorConstructorTest() throws Exception {
        assertEquals(appContext, imageCreator.getMyContext());
    }

    @Test
    public void addTextToImageBitmapTest() throws Exception {
        Bitmap imageWithText = imageCreator.addTextToImageBitmap("Test text");
        assertNotNull(imageWithText);
    }

    @Test
    public void saveImageToInternalStorage() throws Exception {
        Bitmap imageWithText = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        String pathToImage = imageCreator.saveImageToInternalStorage(imageWithText);
        assertNotNull(pathToImage);
    }
}
