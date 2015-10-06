package com.moderneng.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.moderneng.framents.BalloonAnimationActivityFragment;
import com.moderneng.utils.AppConstant;
import com.moderneng.R;

public class BalloonAnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_baloon_animation);

        Fragment fragment = new BalloonAnimationActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.EXTRA_GREETING_IMAGE_RESOURCE_ID,getIntent().getIntExtra(AppConstant.EXTRA_GREETING_IMAGE_RESOURCE_ID, 0));
        bundle.putInt(AppConstant.EXTRA_GREETING_SOUND_ID,getIntent().getIntExtra(AppConstant.EXTRA_GREETING_SOUND_ID, 0));
        //bundle.putInt(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_ID,getIntent().getIntExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_ID, 0));
        bundle.putInt(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_DELAY,getIntent().getIntExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_DELAY, 0));
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }
}
