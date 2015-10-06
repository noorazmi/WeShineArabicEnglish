package com.moderneng.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.ImageAndMediaResources;
import com.moderneng.views.HorizontalPage;

public class Horizontalpager extends Activity {
    private int count;
    private AudioPlayer mp;
    private String[] backgoundmusic;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Create the view switcher
        HorizontalPage realViewSwitcher = new HorizontalPage(getApplicationContext());
        int[] backgroundDrawables = null;

        backgroundDrawables = new int[]{ImageAndMediaResources.sImageIdEducation1, ImageAndMediaResources.sImageIdEducation2, ImageAndMediaResources.sImageIdEducation3, ImageAndMediaResources.sImageIdEducation4, ImageAndMediaResources.sImageIdEducation5};
        //backgoundmusic = new int[]{ImageAndMediaResources.sSoundIdEducation1, ImageAndMediaResources.sSoundIdEducation2, ImageAndMediaResources.sSoundIdEducation3, ImageAndMediaResources.sSoundIdEducation4, ImageAndMediaResources.sSoundIdEducation5};
        backgoundmusic = new String[]{ImageAndMediaResources.sSoundIdEducation1, ImageAndMediaResources.sSoundIdEducation2, ImageAndMediaResources.sSoundIdEducation3, ImageAndMediaResources.sSoundIdEducation4, ImageAndMediaResources.sSoundIdEducation5};


        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("intVariableName", 0);
        count = intValue;
        for (int i = intValue; i < 5; i++) {
            ImageView textView = new ImageView(getApplicationContext());
            textView.setScaleType(ScaleType.FIT_XY);
            textView.setImageResource(backgroundDrawables[i]);
            realViewSwitcher.addView(textView);
        }
        mp = new AudioPlayer(this, backgoundmusic[count]);

        setContentView(realViewSwitcher);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mp.start();
        realViewSwitcher.setOnScreenSwitchListener(onScreenSwitchListener);

    }

    private final HorizontalPage.OnScreenSwitchListener onScreenSwitchListener = new HorizontalPage.OnScreenSwitchListener() {
        @Override
        public void onScreenSwitched(final int screen) {
            mp.pause();
            int id = 0;
            System.gc();
            if (screen == 0) {
                id = count;
            } else if (screen == 1) {
                id = count + 1;
            } else if (screen == 2) {
                id = count + 2;
            } else if (screen == 3) {
                id = count + 3;
            } else if (screen == 4) {
                id = count + 4;
            }
            mp = new AudioPlayer(getApplicationContext(), backgoundmusic[id]);
            mp.start();
        }
    };

    public void onBackPressed() {
        mp.pause();
        super.onBackPressed();
    }
}
