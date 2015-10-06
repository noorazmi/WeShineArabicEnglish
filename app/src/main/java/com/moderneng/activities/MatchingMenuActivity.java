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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.moderneng.R;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;

public class MatchingMenuActivity extends Activity implements OnClickListener {
	private ImageButton game1, game2, game3, game4, game5;
	private GameMusic mp4, mp;
    private Bitmap mBitmapBg;
	private Bitmap mBitmapTitle;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.matchmenun);
		game1 = (ImageButton) findViewById(R.id.match1);
		game2 = (ImageButton) findViewById(R.id.match2);
		game3 = (ImageButton) findViewById(R.id.match3);
		game4 = (ImageButton) findViewById(R.id.match4);
		game5 = (ImageButton) findViewById(R.id.match5);
		game1.setOnClickListener(this);
		game2.setOnClickListener(this);
		game3.setOnClickListener(this);
		game4.setOnClickListener(this);
		game5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.match1:
			Intent match1 = new Intent(getApplicationContext(), Match1Activity.class);
			match1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(match1);
			break;
		case R.id.match2:
			Intent match2 = new Intent(getApplicationContext(), Match2Activity.class);
			match2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(match2);
			break;
		case R.id.match3:
			Intent match3 = new Intent(getApplicationContext(), Match3Activity.class);
			match3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(match3);
			break;
		case R.id.match4:
			Intent match4 = new Intent(getApplicationContext(), Match4Activity.class);
			match4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(match4);
			break;
		case R.id.match5:
			Intent match5 = new Intent(getApplicationContext(), Match5Activity.class);
			match5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(match5);
			break;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
        if(mp4 !=null){
            mp4.release();
        }

		if(mp !=null){
			mp.release();
		}

        mBitmapBg.recycle();
        mBitmapBg = null;

		mBitmapTitle.recycle();
		mBitmapTitle = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
        mBitmapBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMatchingMenuBg);
        findViewById(R.id.linear_layout_container).setBackgroundDrawable(new BitmapDrawable(mBitmapBg));

		mBitmapTitle =  BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMatching);
        ((ImageView)findViewById(R.id.imageview_title)).setImageBitmap(mBitmapTitle);
		AnimationUtil.performAnimation(findViewById(R.id.imageview_title), AnimType.ZOOM_IN, null);


        mp = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdMatching);
		mp.start();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				mp4 = new GameMusic(getApplicationContext(), "homesound");
				mp4.start();
			}
		}, 500);
	}
}
