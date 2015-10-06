package com.moderneng;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.vending.expansion.zipfile.APKExpansionSupport;
import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.moderneng.utils.ImageAndMediaResources;

import java.io.IOException;
import java.io.InputStream;

public class WeShineApp extends Application {
    private static WeShineApp singleton;
    private static String sLanguage = null;
    private static ZipResourceFile mExpansionFile = null;
    public static final String MEDIA_FILE_BASE_PATH = "main/raw/";
    public static final String IMAGE_FILE_BASE_PATH = "main/drawable/";
    private int MAIN_EXPANSION_FILE_VERSION = 2;

    public static WeShineApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        try {
            mExpansionFile = APKExpansionSupport.getAPKExpansionZipFile(getApplicationContext(), MAIN_EXPANSION_FILE_VERSION, -1/* patch file is not being used hence using patch version number less than 1*/);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WeShineApp getSingleton() {
        return singleton;
    }

    public static void setSingleton(WeShineApp singleton) {
        WeShineApp.singleton = singleton;
    }

    public static String getLanguage() {
        return sLanguage;
    }

    public static void setLanguage(String sLanguage) {
        WeShineApp.sLanguage = sLanguage;
        ImageAndMediaResources.assignSoundAndVideosIds();
        ImageAndMediaResources.assignDrawableIds();
        ImageAndMediaResources.assignVideoDurations();
    }

    public static AssetFileDescriptor getAssetFileDescriptor(String fileName) {
        return mExpansionFile.getAssetFileDescriptor(MEDIA_FILE_BASE_PATH + fileName);
    }

    public static ZipResourceFile getExpansionFile() {
        return mExpansionFile;
    }

    public static Bitmap getBitmapFromObb(String drawableNameWithExtension){

        InputStream inputStream = null;
        try {
            inputStream = mExpansionFile.getInputStream("main/drawable/"+drawableNameWithExtension);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOptions);
        return bitmap;
    }
}
