package com.moderneng.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.ImageAndMediaResources;

public class EducationMenuActivity extends Activity implements OnClickListener {
    private ImageButton slide1, slide2, slide3, slide4, slide5;
    private Bitmap mBitmapEduMenu1;
    private Bitmap mBitmapEduMenu2;
    private Bitmap mBitmapEduMenu3;
    private Bitmap mBitmapEduMenu4;
    private Bitmap mBitmapEduMenu5;

    private Bitmap mBitmapEduBg;
    private Bitmap mBitmapTitle;


    private int intValue;
    private AudioPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_education_menu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        slide1 = (ImageButton) findViewById(R.id.imagebutton_menu1);
        slide2 = (ImageButton) findViewById(R.id.imagebutton_menu2);
        slide3 = (ImageButton) findViewById(R.id.imagebutton_menu3);
        slide4 = (ImageButton) findViewById(R.id.imagebutton_menu4);
        slide5 = (ImageButton) findViewById(R.id.imagebutton_menu5);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imagebutton_menu1:
                intValue = 0;
                mp.pause();
                start();
                break;
            case R.id.imagebutton_menu2:
                intValue = 1;
                mp.pause();
                start();
                break;
            case R.id.imagebutton_menu3:
                intValue = 2;
                mp.pause();
                start();
                break;
            case R.id.imagebutton_menu4:
                intValue = 3;
                mp.pause();
                start();
                break;
            case R.id.imagebutton_menu5:
                intValue = 4;
                mp.pause();
                start();
        }
    }

    public void start() {
        Intent myIntent = new Intent(EducationMenuActivity.this, Horizontalpager.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myIntent.putExtra("intVariableName", intValue);
        startActivity(myIntent);

        slide1.setOnClickListener(null);
        slide2.setOnClickListener(null);
        slide3.setOnClickListener(null);
        slide4.setOnClickListener(null);
        slide5.setOnClickListener(null);
    }


    @Override
    protected void onResume() {
        super.onResume();

        mBitmapTitle = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdEducation);
        ((ImageView) findViewById(R.id.imageview_title)).setImageBitmap(mBitmapTitle);
        AnimationUtil.performAnimation(findViewById(R.id.imageview_title), AnimType.ZOOM_IN, null);

        mp = new AudioPlayer(getApplicationContext(), "homesound");
        mp.start();

        mBitmapEduMenu1 = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdEduMenuImg1);
        mBitmapEduMenu2 = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdEduMenuImg2);
        mBitmapEduMenu3 = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdEduMenuImg3);
        mBitmapEduMenu4 = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdEduMenuImg4);
        mBitmapEduMenu5 = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdEduMenuImg5);
//        mBitmapEduMenu1 = WeShineApp.getBitmapFromObb(ImageAndMediaResources.sImageIdEduMenuImg1);
//        mBitmapEduMenu2 = WeShineApp.getBitmapFromObb(ImageAndMediaResources.sImageIdEduMenuImg2);
//        mBitmapEduMenu3 = WeShineApp.getBitmapFromObb(ImageAndMediaResources.sImageIdEduMenuImg3);
//        mBitmapEduMenu4 = WeShineApp.getBitmapFromObb(ImageAndMediaResources.sImageIdEduMenuImg4);
//        mBitmapEduMenu5 = WeShineApp.getBitmapFromObb(ImageAndMediaResources.sImageIdEduMenuImg5);

        //mBitmapEduBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdEduBg);
        mBitmapEduBg = WeShineApp.getBitmapFromObb("edubg.png");

        slide1.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapEduMenu1));
        slide2.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapEduMenu2));
        slide3.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapEduMenu3));
        slide4.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapEduMenu4));
        slide5.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapEduMenu5));
        slide5.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapEduMenu5));
        findViewById(R.id.linear_layout_parent).setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapEduBg));


        slide1.setOnClickListener(this);
        slide2.setOnClickListener(this);
        slide3.setOnClickListener(this);
        slide4.setOnClickListener(this);
        slide5.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        mp.release();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBitmapEduMenu1.recycle();
        mBitmapEduMenu2.recycle();
        mBitmapEduMenu3.recycle();
        mBitmapEduMenu4.recycle();
        mBitmapEduMenu5.recycle();
        mBitmapEduBg.recycle();
        if (mBitmapTitle != null) {
            mBitmapTitle.recycle();
            mBitmapTitle = null;
        }
        mBitmapEduMenu1 = null;
        mBitmapEduMenu2 = null;
        mBitmapEduMenu3 = null;
        mBitmapEduMenu4 = null;
        mBitmapEduMenu5 = null;
        mBitmapEduBg = null;


    }

}
