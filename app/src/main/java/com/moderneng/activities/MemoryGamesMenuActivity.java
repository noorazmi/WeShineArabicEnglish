package com.moderneng.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.ColorTool;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;

public class MemoryGamesMenuActivity extends Activity implements View.OnTouchListener {
    private ImageView frontimg, backimg, bird, bird2, beev, bird3;
    private GameMusic mp;
    private AudioPlayer mp4;
    private Bitmap mBitmapFront;
    private Bitmap mBitmapBack;
    private Bitmap mBitmapTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_memory_games_menu);

        bird = (ImageView) findViewById(R.id.sunim);
        bird2 = (ImageView) findViewById(R.id.bird2);
        beev = (ImageView) findViewById(R.id.bee);
        beev.setBackgroundResource(R.drawable.bee);
        bird.setBackgroundResource(R.drawable.word1);
        bird2.setBackgroundResource(R.drawable.word2);
        bird3 = (ImageView) findViewById(R.id.bird3);
        bird3.setBackgroundResource(R.drawable.bird3);
        AnimationDrawable bird3anim = (AnimationDrawable) bird3.getBackground();
        bird3anim.start();
        AnimationDrawable bird2anim = (AnimationDrawable) bird2.getBackground();
        bird2anim.start();
        AnimationDrawable beeanim = (AnimationDrawable) beev.getBackground();
        beeanim.start();
        AnimationDrawable gyroAnimation = (AnimationDrawable) bird.getBackground();
        gyroAnimation.start();
        frontimg = (ImageView) findViewById(R.id.frontimg);
        backimg = (ImageView) findViewById(R.id.backimg);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getAction();
        final int evx = (int) event.getX();
        final int evy = (int) event.getY();
        ImageView front = (ImageView) v.findViewById(R.id.frontimg);
        if (front == null)
            return false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                int touchColor = getHotspotColor(R.id.backimg, evx, evy);
                ColorTool ct = new ColorTool();
                int tolerance = 25;
                if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
                    Intent i = new Intent(MemoryGamesMenuActivity.this, MemoryGame1Activity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (ct.closeMatch(Color.GREEN, touchColor, tolerance)) {
                    Intent i3 = new Intent(MemoryGamesMenuActivity.this, MemoryGame3Activity.class);
                    i3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i3);
                }else if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
                    Intent i2 = new Intent(MemoryGamesMenuActivity.this, MemoryGame2Activity.class);
                    i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i2);
                }
                break;
        }
        return false;
    }

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", "hot spot image not found");
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
    protected void onStop() {
        super.onStop();
        if (mBitmapFront != null) {
            mBitmapFront.recycle();
            mBitmapFront = null;
        }

        if (mBitmapBack != null) {
            mBitmapBack.recycle();
            mBitmapBack = null;
        }
        if (mBitmapTitle != null) {
            mBitmapTitle.recycle();
            mBitmapTitle = null;
        }
    }

    @Override
    protected void onPause() {
        if (mp4 != null) {
            mp4.release();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mp = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdMemoryGames);
        mp.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mp4 = new AudioPlayer(getApplicationContext(), "homesound");
                mp4.start();
            }
        }, 1100);



        //mBitmapFront = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMemoryBg);
        //mBitmapBack = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMemoryBgHotspot);

        mBitmapFront = WeShineApp.getBitmapFromObb("memory_games_bg.png");
        mBitmapBack = WeShineApp.getBitmapFromObb("memory_games_bg_hotspot.png");


        frontimg.setImageBitmap(mBitmapFront);
        backimg.setImageBitmap(mBitmapBack);
        frontimg.setOnTouchListener(this);

        mBitmapTitle = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMemoryGames);
        ((ImageView) findViewById(R.id.imageview_title)).setImageBitmap(mBitmapTitle);
        AnimationUtil.performAnimation(findViewById(R.id.imageview_title), AnimType.ZOOM_IN, null);
    }
}
