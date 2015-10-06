package com.moderneng.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;

public class PuzzleMenuActivity extends Activity implements OnClickListener {
    private ImageView mImageViewMenu1;
    private ImageView mImageViewMenu2, mImageViewMenu3, mImageViewMenu4, mImageViewMenu5;
    private GameMusic mp;
    private GameMusic mp4;
    private Bitmap mBitmapBg;
    private Bitmap mBitmapTitle;

    private Bitmap mBitmapPuzzleMenu1;
    private Bitmap mBitmapPuzzleMenu2;
    private Bitmap mBitmapPuzzleMenu3;
    private Bitmap mBitmapPuzzleMenu4;
    private Bitmap mBitmapPuzzleMenu5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.puzzlemenun);

        mImageViewMenu1 = (ImageView) findViewById(R.id.puzzle1);
        mImageViewMenu1.setOnClickListener(this);
        mImageViewMenu2 = (ImageView) findViewById(R.id.puzzle2);
        mImageViewMenu2.setOnClickListener(this);
        mImageViewMenu3 = (ImageView) findViewById(R.id.puzzle3);
        mImageViewMenu3.setOnClickListener(this);
        mImageViewMenu4 = (ImageView) findViewById(R.id.puzzle4);
        mImageViewMenu5 = (ImageView) findViewById(R.id.puzzle5);
        mImageViewMenu5.setOnClickListener(this);
        mImageViewMenu4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.puzzle1:
                Intent ip1 = new Intent(getApplicationContext(), Puzzle1Activity.class);
                ip1.putExtra("intVariableName", 0);
                ip1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ip1);
                break;
            case R.id.puzzle2:
                Intent ip2 = new Intent(getApplicationContext(), Puzzle2Activity.class);
                ip2.putExtra("intVariableName", 1);
                ip2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ip2);
                break;
            case R.id.puzzle3:
                Intent ip3 = new Intent(getApplicationContext(), Puzzle3Activity.class);
                ip3.putExtra("intVariableName", 2);
                ip3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ip3);
                break;
            case R.id.puzzle4:
                Intent ip4 = new Intent(getApplicationContext(), Puzzle4Activity.class);
                ip4.putExtra("intVariableName", 3);
                ip4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ip4);
                break;
            case R.id.puzzle5:
                Intent ip5 = new Intent(getApplicationContext(), Puzzle5Activity.class);
                ip5.putExtra("intVariableName", 4);
                ip5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ip5);
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mp4 != null) {
            mp4.release();
        }

        if (mp != null) {
            mp.release();
        }

        mBitmapBg.recycle();
        mBitmapBg = null;

        mBitmapTitle.recycle();
        mBitmapTitle = null;

        if (mBitmapPuzzleMenu1 != null) {
            mBitmapPuzzleMenu1.recycle();
            mBitmapPuzzleMenu1 = null;
        }
        if (mBitmapPuzzleMenu2 != null) {
            mBitmapPuzzleMenu2.recycle();
            mBitmapPuzzleMenu2 = null;
        }
        if (mBitmapPuzzleMenu3 != null) {
            mBitmapPuzzleMenu3.recycle();
            mBitmapPuzzleMenu3 = null;
        }
        if (mBitmapPuzzleMenu4 != null) {
            mBitmapPuzzleMenu4.recycle();
            mBitmapPuzzleMenu4 = null;
        }
        if (mBitmapPuzzleMenu5 != null) {
            mBitmapPuzzleMenu5.recycle();
            mBitmapPuzzleMenu5 = null;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        mBitmapPuzzleMenu1 = WeShineApp.getBitmapFromObb("puzzlen1.png");
        mBitmapPuzzleMenu2 = WeShineApp.getBitmapFromObb("puzzlen2.png");
        mBitmapPuzzleMenu3 = WeShineApp.getBitmapFromObb("puzzlen3.png");
        mBitmapPuzzleMenu4 = WeShineApp.getBitmapFromObb("puzzlen4.png");
        mBitmapPuzzleMenu5 = WeShineApp.getBitmapFromObb("puzzlen5.png");

        mImageViewMenu1.setImageBitmap(mBitmapPuzzleMenu1);
        mImageViewMenu2.setImageBitmap(mBitmapPuzzleMenu2);
        mImageViewMenu3.setImageBitmap(mBitmapPuzzleMenu3);
        mImageViewMenu4.setImageBitmap(mBitmapPuzzleMenu4);
        mImageViewMenu5.setImageBitmap(mBitmapPuzzleMenu5);

        //mBitmapBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdPuzzleMenuBg);
        mBitmapBg = WeShineApp.getBitmapFromObb("puzzle_menu_bg.png");
        findViewById(R.id.linear_layout_container).setBackgroundDrawable(new BitmapDrawable(mBitmapBg));

        mBitmapTitle = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdPuzzle);
        ((ImageView) findViewById(R.id.imageview_title)).setImageBitmap(mBitmapTitle);
        AnimationUtil.performAnimation(findViewById(R.id.imageview_title), AnimType.ZOOM_IN, null);


        mp4 = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdPuzzle);
        mp4.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mp = new GameMusic(getApplicationContext(), "homesound");
                mp.start();
            }
        }, 500);
    }


}
