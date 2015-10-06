package com.moderneng.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.ImageAndMediaResources;
import com.moderneng.utils.Logger;
import com.moderneng.utils.UtilityMethods;

import java.io.IOException;

public class SunCatcherActivity extends Activity implements OnTouchListener {

	private static final String TAG = SunCatcherActivity.class.getName();
	// private ImageView golfCarImageView;
	private boolean dragging = false;
	private Rect hitRect = new Rect();
	private AbsoluteLayout mainLayout;
	private int yPositionGolfCar;

	private ImageView letterView;
	private ImageView birdImageView;
	private ImageView sunImageView;
	private int mAudioFileId ;
	private String mAudioFileName ;
	private MediaPlayer mediaPlayer;
	private double screenSize;
	private int birdMarginTop;
	private TextView timerText;
	private ImageView betteryView;
	private ImageView raysImageView;
	private int xFromPosSunRay;
	private int yFromPosSunRay;
	private int yToPosSunRay;
	private int xToPosSunRay;
	private int xPositionGolfCar;
	private int rayHitCount = 0;
	private final int BATTERY_25_HIT_COUNT = 9; //10;
	private final int BATTERY_50_HIT_COUNT = 18; //20;
	private final int BATTERY_75_HIT_COUNT = 27; //30;
	private final int BATTERY_100_HIT_COUNT = 36; //40;
	private final int RAY_ONE = 1;
	private final int RAY_TWO = 2;
	private final int RAY_THREE = 3;
	private final int RAY_FOUR = 4;
	private final int RAY_FIVE = 5;
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private ImageView starImageView;
	private int currentRayNumber;
	private int golfCarHalfWidth;
	private int golfCarWith;
	private MediaPlayer backgroundMusicMediaPlayer;
	private int sunWidth;
	private Bitmap mBitmapBg;
	boolean mIsStoppedCalled = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_sun_catcher);

		mBitmapBg = WeShineApp.getBitmapFromObb("sun_catcher_bg.png");
		findViewById(R.id.frame_layout_container).setBackgroundDrawable(new BitmapDrawable(mBitmapBg));

		screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		SCREEN_WIDTH = UtilityMethods.getScreenWidth(this);
		SCREEN_HEIGHT = UtilityMethods.getScreenHeight(this);
		letterView = (ImageView) findViewById(R.id.sunCatcher_golfCarImageView);
		letterView.setOnTouchListener(this);
		mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
		mainLayout.setOnTouchListener(this);
		birdImageView = (ImageView) findViewById(R.id.sunCatcher_birdImageView);
		setBatteryImageView();
		setCircleTextView();
		setGolfCarImageView();
		setLakeImageView();
		setBirdImageView();
		// startAudioSoundBackground(R.raw.sun_cather_music);
		startBackgroundMusic();
		timerText = (TextView) findViewById(R.id.sunCatcher_circleTextView);
		betteryView = (ImageView) findViewById(R.id.sunCatcher_batteryImageView);
		raysImageView = (ImageView) findViewById(R.id.sunCatcher_raysImageView);
		//setSunRaysImageWidthAndHeight();
		sunImageView = (ImageView) findViewById(R.id.sunCatcher_sunImageView);
		new CountDownTimer(60000, 1000) {

			public void onTick(long millisUntilFinished) {
				int remainingMilliSecs = (int) millisUntilFinished / 1000;
				if (remainingMilliSecs == 58) {
					startAudioSound(ImageAndMediaResources.sSoundIdCatchUpTheSun);
				}
				if (remainingMilliSecs == 1) {
					raysImageView.setVisibility(View.INVISIBLE);
				}
				timerText.setText("" + remainingMilliSecs);
			}

			public void onFinish() {
				timerText.setText("" + 0);
				if (rayHitCount >= BATTERY_100_HIT_COUNT) {
					betteryView.setImageResource(R.drawable.battery100);

					(findViewById(R.id.sunCatcher_batteryFullLinearLayout)).setVisibility(View.VISIBLE);
					//Bitmap bm = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdBatteryIsFullThanYou)
					((ImageView)findViewById(R.id.sunCatcher_batteryFullImageView)).setImageBitmap(BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdBatteryIsFullThanYou));
					startAudioSound(ImageAndMediaResources.sSoundIdBatteryFullThankYou);
				} else {
					(findViewById(R.id.sunCatcher_gameOverLinearLayout)).setVisibility(View.VISIBLE);
					((ImageView)findViewById(R.id.imageview_gameover)).setImageBitmap(BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdGameOver));
					if(!mIsStoppedCalled){
						startAudioSound(ImageAndMediaResources.sSoundIdGameOver);
					}
				}

			}

		}.start();

		new CountDownTimer(60000, 1000) {

			public void onTick(long millisUntilFinished) {
				detectSunrayAndCarCollision();
				raysAnimation(UtilityMethods.getRandomInt(1, 5));
			}

			public void onFinish() {

			}
		}.start();
	}

	private void setSunRaysImageWidthAndHeight(){
		AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) raysImageView.getLayoutParams();
		if (screenSize >= 9.4) {
			sunWidth = (int) UtilityMethods.convertDpToPixel(150, WeShineApp.getInstance());
			params.width = sunWidth;
			int sunHeight = (int) UtilityMethods.convertDpToPixel(146, WeShineApp.getInstance());
			params.height = sunHeight;
			//params.x = SCREEN_WIDTH / 2 - sunWidth / 2;
			//params.y = (int) UtilityMethods.convertDpToPixel(115, WeShineApp.getInstance());
		}
//		else if (screenSize >= 6.9) {
//			sunWidth = (int) UtilityMethods.convertDpToPixel(140, WeShineApp.getInstance());
//			params.width = sunWidth;
//			int sunHeight = (int) UtilityMethods.convertDpToPixel(146, WeShineApp.getInstance());
//			params.height = sunHeight;
//			params.x = SCREEN_WIDTH / 2 - sunWidth / 2;
//			params.y = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
//		} else {
//			sunWidth = (int) UtilityMethods.convertDpToPixel(85, WeShineApp.getInstance());
//			params.width = sunWidth;
//			int sunHeight = (int) UtilityMethods.convertDpToPixel(89, WeShineApp.getInstance());
//			params.height = sunHeight;
//			params.x = SCREEN_WIDTH / 2 - sunWidth / 2;
//			params.y = (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
//		}
//		Log.d(TAG, "WIDTH****:" + SCREEN_WIDTH);
//		// existing height is ok as is, no need to edit it
		raysImageView.setLayoutParams(params);
	}

	private void setAnimatedSunView() {

		Logger.error(TAG, "size%%%%%%%%%%@@@@@@@@************::" + screenSize);
		AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) sunImageView.getLayoutParams();
		if (screenSize >= 9.4) {
			sunWidth = (int) UtilityMethods.convertDpToPixel(153, WeShineApp.getInstance());
			params.width = sunWidth;
			int sunHeight = (int) UtilityMethods.convertDpToPixel(159, WeShineApp.getInstance());
			params.height = sunHeight;
			params.x = SCREEN_WIDTH / 2 - sunWidth / 2;
			params.y = (int) UtilityMethods.convertDpToPixel(115, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			sunWidth = (int) UtilityMethods.convertDpToPixel(140, WeShineApp.getInstance());
			params.width = sunWidth;
			int sunHeight = (int) UtilityMethods.convertDpToPixel(146, WeShineApp.getInstance());
			params.height = sunHeight;
			params.x = SCREEN_WIDTH / 2 - sunWidth / 2;
			params.y = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
		} else {
			sunWidth = (int) UtilityMethods.convertDpToPixel(85, WeShineApp.getInstance());
			params.width = sunWidth;
			int sunHeight = (int) UtilityMethods.convertDpToPixel(89, WeShineApp.getInstance());
			params.height = sunHeight;
			params.x = SCREEN_WIDTH / 2 - sunWidth / 2;
			params.y = (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
		}
		Log.d(TAG, "WIDTH****:" + SCREEN_WIDTH);
		// existing height is ok as is, no need to edit it
		sunImageView.setLayoutParams(params);
		AnimationUtil.performFrameAnimation(sunImageView, R.drawable.maze2_sun_animation);
	}

	private void setGolfCarImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.sunCatcher_golfCarImageView);
		AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) imageView.getLayoutParams();
		// int carWidth;
		if (screenSize >= 9.4) {
			golfCarWith = (int) UtilityMethods.convertDpToPixel(270, WeShineApp.getInstance());
			params.width = golfCarWith;
			params.height = (int) UtilityMethods.convertDpToPixel(270, WeShineApp.getInstance());
			xPositionGolfCar = SCREEN_WIDTH / 3;
			params.x = xPositionGolfCar;
			yPositionGolfCar = (int) (SCREEN_HEIGHT - UtilityMethods.convertDpToPixel(250, WeShineApp.getInstance()));
			params.y = yPositionGolfCar;
			yToPosSunRay = yPositionGolfCar - 30;
		} else if (screenSize >= 6.9) {
			golfCarWith = (int) UtilityMethods.convertDpToPixel(170, WeShineApp.getInstance());
			params.width = golfCarWith;
			params.height = (int) UtilityMethods.convertDpToPixel(170, WeShineApp.getInstance());
			xPositionGolfCar = SCREEN_WIDTH / 3;
			params.x = xPositionGolfCar;
			yPositionGolfCar = (int) (SCREEN_HEIGHT - UtilityMethods.convertDpToPixel(195, WeShineApp.getInstance()));
			params.y = yPositionGolfCar;
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
		} else {
			golfCarWith = (int) UtilityMethods.convertDpToPixel(105, WeShineApp.getInstance());
			params.width = golfCarWith;
			params.height = (int) UtilityMethods.convertDpToPixel(105, WeShineApp.getInstance());
			xPositionGolfCar = SCREEN_WIDTH / 3;
			params.x = xPositionGolfCar;
			yPositionGolfCar = (int) (SCREEN_HEIGHT - UtilityMethods.convertDpToPixel(130, WeShineApp.getInstance()));
			params.y = yPositionGolfCar;
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
		}

		golfCarHalfWidth = golfCarWith / 2;
	}

	private void raysAnimation(int rayNumber) {
		int oneSixScreen = SCREEN_WIDTH / 6;
		currentRayNumber = rayNumber;
		if (screenSize >= 9.4) {
			yFromPosSunRay = (int) UtilityMethods.convertDpToPixel(245, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			yFromPosSunRay = (int) UtilityMethods.convertDpToPixel(155, WeShineApp.getInstance());
		} else {
			yFromPosSunRay = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
		}
		switch (currentRayNumber) {
			case RAY_ONE:
				if (screenSize >= 9.4) {
					raysImageView.setImageResource(R.drawable.rays1);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(45, WeShineApp.getInstance());
					xToPosSunRay = oneSixScreen;
				} else if (screenSize >= 6.9) {
					raysImageView.setImageResource(R.drawable.rays1_medium);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(48, WeShineApp.getInstance());
					xToPosSunRay = oneSixScreen;
				} else {
					raysImageView.setImageResource(R.drawable.rays1_small);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(87, WeShineApp.getInstance());
					xToPosSunRay = oneSixScreen - (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
				}
				break;
			case RAY_TWO:
				if (screenSize >= 9.4) {
					raysImageView.setImageResource(R.drawable.rays2);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(15, WeShineApp.getInstance());
					xToPosSunRay = 2 * oneSixScreen;
				} else if (screenSize >= 6.9) {
					raysImageView.setImageResource(R.drawable.rays2_medium);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(22, WeShineApp.getInstance());
					xToPosSunRay = 2 * oneSixScreen;
				} else {
					raysImageView.setImageResource(R.drawable.rays2_small);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
					xToPosSunRay = 2 * oneSixScreen - (int) UtilityMethods.convertDpToPixel(47, WeShineApp.getInstance());
				}

				break;
			case RAY_THREE:
				if (screenSize >= 9.4) {
					raysImageView.setImageResource(R.drawable.rays3);
					xToPosSunRay = 3 * oneSixScreen - sunWidth / 2 + 20;
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(5, WeShineApp.getInstance());
				} else if (screenSize >= 6.9) {
					raysImageView.setImageResource(R.drawable.rays3_medium);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(2, WeShineApp.getInstance());
					xToPosSunRay = 3 * oneSixScreen - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(2, WeShineApp.getInstance());
				} else {
					raysImageView.setImageResource(R.drawable.rays3_small);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
					xToPosSunRay = 3 * oneSixScreen - sunWidth / 2 - (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
				}

				break;
			case RAY_FOUR:
				if (screenSize >= 9.4) {
					raysImageView.setImageResource(R.drawable.rays4);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
					xToPosSunRay = 4 * oneSixScreen;
				} else if (screenSize >= 6.9) {
					raysImageView.setImageResource(R.drawable.rays4_medium);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
					xToPosSunRay = 4 * oneSixScreen;
				} else {
					raysImageView.setImageResource(R.drawable.rays4_small);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(2, WeShineApp.getInstance());
					xToPosSunRay = 4 * oneSixScreen- (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
				}
				break;
			case RAY_FIVE:
				if (screenSize >= 9.4) {
					raysImageView.setImageResource(R.drawable.rays5);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(45, WeShineApp.getInstance());
					xToPosSunRay = 5 * oneSixScreen;
				} else if (screenSize >= 6.9) {
					raysImageView.setImageResource(R.drawable.rays5_medium);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(45, WeShineApp.getInstance());
					xToPosSunRay = 5 * oneSixScreen - (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
				} else {
					raysImageView.setImageResource(R.drawable.rays5_small);
					xFromPosSunRay = SCREEN_WIDTH / 2 - sunWidth / 2 + (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
					xToPosSunRay = 5 * oneSixScreen - (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
				}
				break;

			default:
				break;
		}

		AnimationSet replaceAnimation = new AnimationSet(false);
		TranslateAnimation trans = new TranslateAnimation(0, xFromPosSunRay, TranslateAnimation.ABSOLUTE, xToPosSunRay, 0, yFromPosSunRay, TranslateAnimation.ABSOLUTE, yToPosSunRay);
		trans.setDuration(1000);
		replaceAnimation.addAnimation(trans);
		replaceAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// detectSunrayAndCarCollision();
			}
		});

		// start our animation
		raysImageView.startAnimation(replaceAnimation);

	}

	private void detectSunrayAndCarCollision() {
		boolean isCollision = false;
		int marginLeft = 0;
		int margingRight = 0;
		if (currentRayNumber == RAY_ONE) {
			if (screenSize >= 9.4) {
			} else if (screenSize >= 6.9) {
			} else {
				marginLeft = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
			}
		} else if (currentRayNumber == RAY_TWO) {
			if (screenSize >= 9.4) {
			} else if (screenSize >= 6.9) {
			} else {
				marginLeft = (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
			}

		} else if (currentRayNumber == RAY_THREE) {
			if (screenSize >= 9.4) {
				marginLeft = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			} else if (screenSize >= 6.9) {
				marginLeft = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			} else {
				marginLeft = (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			}
		} else if (currentRayNumber == RAY_FOUR) {
			if (screenSize >= 9.4) {
				marginLeft = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			} else if (screenSize >= 6.9) {
				marginLeft = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			} else {
				marginLeft = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			}

		} else if (currentRayNumber == RAY_FIVE) {
			if (screenSize >= 9.4) {
				marginLeft = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			} else if (screenSize >= 6.9) {
				marginLeft = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			} else {
				marginLeft = (int) UtilityMethods.convertDpToPixel(105, WeShineApp.getInstance());
				margingRight = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			}

		}
		if ((xToPosSunRay >= (xPositionGolfCar - golfCarHalfWidth - marginLeft) && xToPosSunRay <= (xPositionGolfCar + golfCarHalfWidth - margingRight))) {
			isCollision = true;
		}
		Log.d(TAG, "ray:" + currentRayNumber + " CarHalfWidth:" + golfCarHalfWidth + " xToPosRay: " + xToPosSunRay + " xGolfCar:" + xPositionGolfCar + " minX:" + (xPositionGolfCar - golfCarHalfWidth) + " maxX" + (xPositionGolfCar + golfCarHalfWidth) + " colli:" + isCollision);
		if (isCollision) {
			performStarsAnimation();
			Log.d(TAG, "detected collision*******:" + rayHitCount);
			++rayHitCount;
			if (rayHitCount == BATTERY_25_HIT_COUNT) {
				betteryView.setImageResource(R.drawable.battery25);
				startAudioSound(ImageAndMediaResources.sSoundIdWellDone);
			} else if (rayHitCount == BATTERY_50_HIT_COUNT) {
				betteryView.setImageResource(R.drawable.battery50);
				startAudioSound(ImageAndMediaResources.sSoundIdPerfect);
			} else if (rayHitCount == BATTERY_75_HIT_COUNT) {
				betteryView.setImageResource(R.drawable.battery75);
				startAudioSound(ImageAndMediaResources.sSoundIdSuper);
			}

		}
	}

	@Override
	/**
	 * NOTE:  Had significant problems when I tried to react to ACTION_MOVE on letterView.   Kept getting alternating (X,Y) 
	 * locations of the motion events, which caused the letter to flicker and move back and forth.  The only solution I could 
	 * find was to determine when the user had touched down on the letter, then process moves in the ACTION_MOVE 
	 * associated with the mainLayout.
	 */
	public boolean onTouch(View v, MotionEvent event) {
		boolean eventConsumed = true;
		int x = (int) event.getX();
		int y = (int) event.getY();
		// Log.d(TAG, " xToPosSunRay: " + xToPosSunRay+" xPositionGolfCar:" +
		// xPositionGolfCar );

		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			if (v == letterView) {
				dragging = true;
				eventConsumed = false;
			}
		} else if (action == MotionEvent.ACTION_UP) {
			dragging = false;
			eventConsumed = false;

		} else if (action == MotionEvent.ACTION_MOVE) {
			if (v != letterView) {
				if (dragging) {
					xPositionGolfCar = x;
					setAbsoluteLocationCentered(letterView, x, yPositionGolfCar);
				}
			}
		}

		return eventConsumed;

	}

	private void setAbsoluteLocationCentered(View v, int x, int y) {
		setAbsoluteLocation(v, x - v.getWidth() / 2, y);

	}

	private void setAbsoluteLocation(View v, int x, int y) {
		AbsoluteLayout.LayoutParams alp = (AbsoluteLayout.LayoutParams) v.getLayoutParams();
		alp.x = x;
		alp.y = y;
		v.setLayoutParams(alp);
	}

	/**
	 * Will be invoked when the sun ray hit the car.
	 */
	private void performStarsAnimation() {
		starImageView = (ImageView) findViewById(R.id.sunCatcher_starsImageView);
		starImageView.setVisibility(View.VISIBLE);
		AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) starImageView.getLayoutParams();
		params.x = xToPosSunRay;
		params.y = yToPosSunRay + 30;
		starImageView.setLayoutParams(params);

		final AnimationDrawable myAnimationDrawable = (AnimationDrawable) starImageView.getDrawable();
		myAnimationDrawable.setOneShot(true);
		myAnimationDrawable.setVisible(true, true);
		if (!myAnimationDrawable.isRunning()) {
			myAnimationDrawable.start();
		}
	}

	private void setBatteryImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.sunCatcher_batteryImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.width = (int) UtilityMethods.convertDpToPixel(250, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(133, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(180, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(96, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(125, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(67, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(15, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(2, WeShineApp.getInstance());
		}
	}

	private void setCircleTextView() {
		TextView imageView = (TextView) findViewById(R.id.sunCatcher_circleTextView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.width = (int) UtilityMethods.convertDpToPixel(125, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(123, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(103, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(102, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
			imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(5, WeShineApp.getInstance());
			imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		}

	}

	private void startBackgroundMusic() {

		//String uriPath = AppConstant.BASE_RESOURCE_PATH + R.raw.sun_cather_music;
		// mAudioFileId = R.raw.sun_cather_music;
		//Uri uri = Uri.parse(uriPath);
		//backgroundMusicMediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);



		AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor("sun_cather_music" + ".mp3");
		backgroundMusicMediaPlayer = new MediaPlayer();
		backgroundMusicMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		backgroundMusicMediaPlayer.setLooping(false);
		try {
			backgroundMusicMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
			backgroundMusicMediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		backgroundMusicMediaPlayer.setOnCompletionListener(new MediaListener());
		backgroundMusicMediaPlayer.start();
	}

	protected void startAudioSound(int audioFileId) {

		String uriPath = AppConstant.BASE_RESOURCE_PATH + audioFileId;
		mAudioFileId = audioFileId;
		Uri uri = Uri.parse(uriPath);
		mediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);
		//if(audioFileId == R.raw.battery_full_thankyou || audioFileId == R.raw.game_over){
		if(audioFileId == ImageAndMediaResources.sSoundIdBatteryFullThankYou || audioFileId == ImageAndMediaResources.sSoundIdGameOver){
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					if (mp != null) {
						mp.release();
						finish();
					}
				}
			});
		}else{
			mediaPlayer.setOnCompletionListener(new MediaListener());
		}

		mediaPlayer.start();

	}


	protected void startAudioSound(String audioFileName) {
		//String uriPath = AppConstant.BASE_RESOURCE_PATH + audioFileId;
		mAudioFileName = audioFileName;
		//Uri uri = Uri.parse(uriPath);
		//mediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);
		//if(audioFileId == R.raw.battery_full_thankyou || audioFileId == R.raw.game_over){

		AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor(audioFileName+".mp3");
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
			mediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(mAudioFileName.equals(ImageAndMediaResources.sSoundIdBatteryFullThankYou) || mAudioFileName.equals(ImageAndMediaResources.sSoundIdGameOver)){
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					if (mp != null) {
						mp.release();
						finish();
					}
				}
			});
		}else{
			mediaPlayer.setOnCompletionListener(new MediaListener());
		}

		mediaPlayer.start();
	}

	class MediaListener implements OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer mp) {
			if (mp != null) {
				mp.release();

			}
		}

	}

	private void setLakeImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.sunCatcher_lakeImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.width = (int) UtilityMethods.convertDpToPixel(510, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(379, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(120, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(300, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(223, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(90, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(200, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(149, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
		}

		AnimationUtil.performFrameAnimation((ImageView) findViewById(R.id.sunCatcher_lakeImageView), R.drawable.lake_animation);

	}

	private void setBirdImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.sunCatcher_birdImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.width = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(91, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(365, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(68, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(230, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(57, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(160, WeShineApp.getInstance());
		}

		AnimationUtil.performFrameAnimation((ImageView) findViewById(R.id.sunCatcher_birdImageView), R.drawable.sun_catcher_bird_animation);
		int toX = UtilityMethods.getScreenWidth(this);
		if (screenSize >= 9.4) {
			toX = toX - (int) UtilityMethods.convertDpToPixel(98, WeShineApp.getInstance());
			birdMarginTop = (int) UtilityMethods.convertDpToPixel(179, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			toX = toX - (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
			birdMarginTop = (int) UtilityMethods.convertDpToPixel(120, WeShineApp.getInstance());
		} else {
			toX = toX - (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
			birdMarginTop = (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
		}
		birdFlyAnimation(toX, birdMarginTop);

	}

	private void birdFlyAnimation(int xToPosSunRay, int yToPosSunRay) {

		ObjectAnimator birdAnimX = ObjectAnimator.ofFloat(birdImageView, "translationX", 0, xToPosSunRay);
		birdAnimX.setDuration(10000);
		ObjectAnimator birdAnimY = ObjectAnimator.ofFloat(birdImageView, "translationY", 0, yToPosSunRay);
		birdAnimY.setDuration(10000);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				birdImageView.setBackgroundResource(R.drawable.birds_first17);
				super.onAnimationEnd(animation);
			}
		});
		animatorSet.play(birdAnimX).with(birdAnimY);
		animatorSet.start();

	}

	@Override
	protected void onResume() {
		setAnimatedSunView();
		backgroundMusicMediaPlayer.start();
		mIsStoppedCalled = false;
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStop() {
		if (backgroundMusicMediaPlayer != null) {
			try {
				backgroundMusicMediaPlayer.pause();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		mIsStoppedCalled = true;
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (backgroundMusicMediaPlayer != null) {
			if (backgroundMusicMediaPlayer != null) {
				try {
					backgroundMusicMediaPlayer.release();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}
		}

		if(mBitmapBg != null){
			mBitmapBg.recycle();
			mBitmapBg = null;
		}

		super.onDestroy();
	}

}
