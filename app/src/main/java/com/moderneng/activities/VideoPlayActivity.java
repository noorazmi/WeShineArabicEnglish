package com.moderneng.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.providers.CustomAPEZProvider;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.ImageAndMediaResources;

public class VideoPlayActivity extends Activity {
	private VideoView vv;
	private Intent imatch;
	private  String mVideoFileName;
	private int mVideoFileId;
	private Bitmap mBitmapThankyou;
	private String mVideoType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		System.gc();
		Intent mIntent = getIntent();
		//mVideoFileName = mIntent.getIntExtra("vid", 0);
		mVideoFileName = mIntent.getStringExtra(AppConstant.EXTRA_VIDEO_NAME);
		mVideoFileId = mIntent.getIntExtra(AppConstant.EXTRA_VIDEO_ID, 0);
		//mVideoType = mIntent.getStringExtra(AppConstant.VIDEO_TYPE_STORY);
		mVideoType = mIntent.getStringExtra(AppConstant.EXTRA_VIDEO_TYPE);
		vv = (VideoView) this.findViewById(R.id.videoview_game_end);
		Uri uri = null;
		if(mIntent.getStringExtra(AppConstant.EXTRA_VIDEO_LOCATION).equals(AppConstant.EXTRA_VIDEO_LOCATION_APK)){
			uri = Uri.parse("android.resource://" + getPackageName() + "/" + mVideoFileId);
		}else {//Fetch file from .obb
		  uri= CustomAPEZProvider.buildUri(WeShineApp.MEDIA_FILE_BASE_PATH + mVideoFileName+".mp4");
		}
		vv.setVideoURI(uri);
		vv.start();

		int videoDuration = mIntent.getIntExtra(AppConstant.BUNDLE_EXTRA_VIDEO_DURATION, 0);

		new CountDownTimer(videoDuration, 500) {
			@Override
			public void onTick(long millisUntilFinished) {
				if(millisUntilFinished <= 1000){
					startNextLevel();
				}
			}
			@Override
			public void onFinish() {

			}
		}.start();



		vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				 mp.reset();
			}
		});
	}

	private void startNextLevel(){

		{

			switch (mVideoFileName) {
				case "matching1_video":
					finish();
					imatch = new Intent(VideoPlayActivity.this, Match2Activity.class);
					break;
				case "matching2_video":
					finish();
					imatch = new Intent(VideoPlayActivity.this, Match3Activity.class);
					break;
				case "matching3_video":
					finish();
					imatch = new Intent(VideoPlayActivity.this, Match4Activity.class);
					break;
				case "matching4_video":
					finish();
					imatch = new Intent(VideoPlayActivity.this, Match5Activity.class);
					break;
				case "matching5_video":
					finish();
					break;
				case "story":
					showStoryEndImage();
					break;
				case "story_arb":
					showStoryEndImage();
					break;
			}

			if(mVideoType!= null && mVideoType.equals(AppConstant.VIDEO_TYPE_STORY)){
				showStoryEndImage();
			}

			if(imatch!=null){
				startActivity(imatch);
			}
		}

	}

	private void showStoryEndImage(){

		//mBitmapThankyou = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdStoryEndThankYouImage);
		mBitmapThankyou = WeShineApp.getBitmapFromObb(ImageAndMediaResources.sImageIdStoryEndThankYouImage);
		((ImageView) findViewById(R.id.imageview_thankyou)).setImageBitmap(mBitmapThankyou);
		findViewById(R.id.imageview_thankyou).setVisibility(View.VISIBLE);
		findViewById(R.id.videoview_game_end).setVisibility(View.GONE);

	}


	@Override
	protected void onStop() {
		super.onStop();
		if(mBitmapThankyou != null){
			mBitmapThankyou.recycle();
		}
	}
}
