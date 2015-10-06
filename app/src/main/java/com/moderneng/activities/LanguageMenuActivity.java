package com.moderneng.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.moderneng.utils.AppConstant;
import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.ColorTool;
import com.moderneng.R;
import com.moderneng.WeShineApp;

public class LanguageMenuActivity extends Activity implements View.OnTouchListener {

    private Bitmap mBitmapTop;
    private Bitmap mBitmapBottom;
    private ImageView mImageViewFront;
    private ImageView mImageViewHotspot;
    private AudioPlayer mAudioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.gc();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_language_menu);
    }





    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean openMenu = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int touchColor = getHotspotColor(R.id.imageview_hotspot, x, y);
                int tolerance = 100;
                if (ColorTool.closeMatch(Color.RED, touchColor, tolerance)) {
                    WeShineApp.setLanguage(AppConstant.LANGUAGE_ENGLISH);
                    openMenu = true;
                } else if (ColorTool.closeMatch(Color.BLUE, touchColor, tolerance)) {
                    WeShineApp.setLanguage(AppConstant.LANGUAGE_ARABIC);
                    openMenu = true;
                }

                if(openMenu){
                    mAudioPlayer.release();
                    Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(i);
                }
                break;
        }


        return true;
    }

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", " Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }


    @Override
    protected void onResume() {
       // mAudioPlayer = new AudioPlayer(this, R.raw.homesound);
        //mAudioPlayer = new AudioPlayer(this, R.raw.homesound);
        mAudioPlayer = new AudioPlayer(this, "homesound");
        mAudioPlayer.start();


        mBitmapTop = BitmapFactory.decodeResource(getResources(), R.drawable.arabic_english);
        mImageViewFront = ((ImageView) findViewById(R.id.imageview_arabic_english));
        mImageViewFront.setImageBitmap(mBitmapTop);
        mImageViewFront.setOnTouchListener(this);

        mBitmapBottom = BitmapFactory.decodeResource(getResources(), R.drawable.arabic_english_hotspot);
        mImageViewHotspot = ((ImageView) findViewById(R.id.imageview_hotspot));
        mImageViewHotspot.setImageBitmap(mBitmapBottom);


        super.onResume();
    }

    @Override
    protected void onStop() {
        mAudioPlayer.release();
        mAudioPlayer = null;
        releaseResources();
        super.onStop();
    }



    private void releaseResources(){
        mBitmapTop.recycle();
        mBitmapTop = null;


        mBitmapBottom.recycle();
        mBitmapBottom = null;

    }


}
