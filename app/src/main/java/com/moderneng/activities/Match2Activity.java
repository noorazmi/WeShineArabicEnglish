package com.moderneng.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;
import com.moderneng.views.ImageDragShadowBuilder;

public class Match2Activity extends Activity {
	private ImageView stower, golf, sun, boat, drag, wtower;
	private int  count = 1;
	private GameMusic mp;
	private AudioPlayer mp3;
    private Bitmap mBitmapText;
    private MediaPlayer mMediaPlayer;
	private Bitmap mBitmapBg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.match2);
		mp3 = new AudioPlayer(getApplicationContext(), ImageAndMediaResources.sSoundIdMatching2);
		mp3.start();
		stower = (ImageView) findViewById(R.id.stowerblank);
		golf = (ImageView) findViewById(R.id.golf1blank);
		sun = (ImageView) findViewById(R.id.sun1blank);
		boat = (ImageView) findViewById(R.id.boatblank);
		drag = (ImageView) findViewById(R.id.draglayout);
		wtower = (ImageView) findViewById(R.id.towerblank);
		stower.setOnDragListener(new Mydraglisterner());
		golf.setOnDragListener(new Mydraglisterner());
		sun.setOnDragListener(new Mydraglisterner());
		boat.setOnDragListener(new Mydraglisterner());
		wtower.setOnDragListener(new Mydraglisterner());
		drag.setOnTouchListener(new Mytouchlistener());

        mBitmapText = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdFabulous);
        ((ImageView) findViewById(R.id.imageview_greeting)).setImageBitmap(mBitmapText);
	}

	private final class Mytouchlistener implements OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			mp3.stop();

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				DragShadowBuilder view2 = null;
				mp = new GameMusic(getApplicationContext(), "drag");
				mp.start();
				ImageView img = (ImageView) view;
				ClipData data = ClipData.newPlainText("", "");
				if (count == 1) {
					view2 = ImageDragShadowBuilder.fromResource(
							getApplicationContext(), R.drawable.sun2);
					view.startDrag(data, view2, img, 0);

				} else if (count == 2) {
					view2 = ImageDragShadowBuilder.fromResource(
							getApplicationContext(), R.drawable.bouy);
					view.startDrag(null, view2, null, 0);
				} else if (count == 3) {
					view2 = ImageDragShadowBuilder.fromResource(
							getApplicationContext(), R.drawable.vessal);
					view.startDrag(null, view2, null, 0);

				} else if (count == 4) {
					view2 = ImageDragShadowBuilder.fromResource(
							getApplicationContext(), R.drawable.golf2);
					view.startDrag(null, view2, null, 0);

				} else if (count == 5) {
					view2 = ImageDragShadowBuilder.fromResource(
							getApplicationContext(), R.drawable.pole);
					view.startDrag(null, view2, null, 0);

				}
				return true;
			} else {
				return false;
			}
		}

	}

	class Mydraglisterner implements OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent dragevent) {
			// TODO Auto-generated method stub
			switch (dragevent.getAction()) {
			case DragEvent.ACTION_DRAG_ENTERED:
				break;
			case DragEvent.ACTION_DRAG_STARTED:
				drag.setVisibility(View.INVISIBLE);
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				if (count == 1) {
					drag.setImageResource(R.drawable.sun2);
					drag.setVisibility(View.VISIBLE);
				} else if (count == 2) {
					drag.setImageResource(R.drawable.bouy);

					drag.setVisibility(View.VISIBLE);
				} else if (count == 3) {
					drag.setImageResource(R.drawable.vessal);
					drag.setVisibility(View.VISIBLE);
				} else if (count == 4) {
					drag.setImageResource(R.drawable.golf2);
					drag.setVisibility(View.VISIBLE);
				} else if (count == 5) {
					drag.setImageResource(R.drawable.pole);
					drag.setVisibility(View.VISIBLE);
				} else if (count == 6) {
					count++;
                    startAudioSound();
                    performGreetingTextAnimation();
				}
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				break;
			case DragEvent.ACTION_DROP:
				View v1 = (View) dragevent.getLocalState();
				if (count == 1 && v.getId() == R.id.sun1blank) {
					ViewGroup group = (ViewGroup) v.getParent();
					mp = new GameMusic(getApplicationContext(), "suns");
					mp.start();
					sun.setImageResource(R.drawable.sun2);
					int[] imagecordinates=new int[2];
					sun.getLocationOnScreen(imagecordinates);
					// sun.setOnClickListener(null);
					count++;
					return true;
				} else if (count == 2 && v.getId() == R.id.towerblank) {
					ViewGroup group = (ViewGroup) v.getParent();
					wtower.setImageResource(R.drawable.bouy);
					mp = new GameMusic(getApplicationContext(), "bouy");
					mp.start();
					count++;
					// wtower.setOnClickListener(null);
					return true;

				} else if (count == 3 && v.getId() == R.id.boatblank) {
					ViewGroup group = (ViewGroup) v.getParent();
					boat.setImageResource(R.drawable.vessal);
					count++;
					mp = new GameMusic(getApplicationContext(), "vessel");
					// boat.setOnClickListener(null);
					mp.start();
					return true;
				} else if (count == 4 && v.getId() == R.id.golf1blank) {
					ViewGroup group = (ViewGroup) v.getParent();
					golf.setImageResource(R.drawable.golf2);
					count++;
					int[] imagecordinates=new int[2];
					golf.getLocationOnScreen(imagecordinates);
					mp = new GameMusic(getApplicationContext(), "golfcart");
					mp.start();
					// golf.setOnClickListener(null);
					return true;
				} else if (count == 5 && v.getId() == R.id.stowerblank) {
					ViewGroup group = (ViewGroup) v.getParent();
					stower.setImageResource(R.drawable.pole);
					count++;

					return true;
				} else {
					mp = new GameMusic(getApplicationContext(), "wrongs");
					mp.start();
					count = count;
					return false;
				}
			}

			return true;
		}
	}


    protected void startAudioSound() {
        String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdFabulous;
        Uri uri = Uri.parse(uriPath);
        mMediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);
        mMediaPlayer.start();
    }

    private void performGreetingTextAnimation(){
        findViewById(R.id.imageview_greeting).setVisibility(View.VISIBLE);
        AnimationUtil.performAnimation(findViewById(R.id.imageview_greeting), AnimType.ZOOM_IN, new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				Intent intent = new Intent(Match2Activity.this, VideoPlayActivity.class);
				//intent.putExtra(AppConstant.EXTRA_VIDEO_ID, "matching2_video");
				intent.putExtra(AppConstant.EXTRA_VIDEO_NAME, "matching2_video");
				intent.putExtra(AppConstant.EXTRA_VIDEO_LOCATION, AppConstant.EXTRA_VIDEO_LOCATION_OBB);
				intent.putExtra(AppConstant.BUNDLE_EXTRA_VIDEO_DURATION, AppConstant.MACHING_TWO_VIDEO_DURATION);
				startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
    }


	@Override
	protected void onResume() {
		super.onResume();
		mBitmapBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMatch2Bg);
		findViewById(R.id.relative_layout_parent).setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapBg));
	}


    @Override
    protected void onStop() {
        super.onStop();

		if(mBitmapBg != null){
			mBitmapBg.recycle();
			mBitmapBg = null;
		}

        if(mBitmapText != null){
            mBitmapText.recycle();
            mBitmapText = null;
        }
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
