package com.moderneng.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.ImageAndMediaResources;
import com.moderneng.utils.Logger;
import com.moderneng.utils.UtilityMethods;

import java.io.IOException;

public class BalloonActivity extends Activity {

	private static final String TAG = BalloonActivity.class.getName();
	private static final int POLE_PANEL_1 = 10;
	private static final int POLE_PANEL_2 = 11;
	private AbsoluteLayout mainLayout;
	private int yPositionGolfCar;
	private ImageView poleBatteryImageView1;
	private ImageView poleBatteryImageView2;
	private ImageView sunrayRightImageView;
	private ImageView sunrayDownImageView;
	private ImageView sunImageView;
	private double screenSize;
	private TextView timerText;
	private int yToPosSunRay;
	private int xPositionGolfCar;
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private int golfCarHalfWidth;
	private int golfCarWith;
	private MediaPlayer backgroundMusicMediaPlayer;
	private int sunWidth;
	private ImageView polePanelImageView1;
	private ImageView polePanelImageView2;

	private int xPosPole;
	private int yPosPole;

	private int xPosBatteryBox;
	private int yPosBatteryBox;

	private int xStartPosPanel;
	private int yStartPosPanel;
	private int polePanel1Height;
	private int currentPanel = POLE_PANEL_1;
	private int scoreCounter = 0;
	private int balloonPopCount = 0;
	private TextView scoreTextView;
	private View balloon1, balloon2, balloon3, balloon4, balloon5, balloon6, balloon7, balloon8, balloon9, balloon10, balloon11, balloon12, balloon13;
	private static final int CAR_PANEL_1 = 10;
	private static final int CAR_PANEL_2 = 11;
	private static final int CAR_PANEL_3 = 12;
	private static final int CAR_PANEL_4 = 13;
	private static final int BATTERY_PANEL_1 = 14;
	private static final int BATTERY_PANEL_2 = 15;
	private ImageView carPanelImageView1;
	private ImageView carPanelImageView2;
	private ImageView carPanelImageView3;
	private ImageView carPanelImageView4;
	private int currentCarPanel = CAR_PANEL_1;
	private ImageView batteryBoxImageView;
	private int currentBatteryPanel = BATTERY_PANEL_1;
	private int sunX;
	private int sunY;
	private ImageView golfcarImageView;
	private View baloonFrame;
	private AnimatorSet baloonPanelAnimatorSet;
	private MediaPlayer welldonePlayer;
	private MediaPlayer perfectPlayer;
	private MediaPlayer goodJobPlayer;
	private MediaPlayer superPlayer;
	private MediaPlayer fabulousPlayer;
	private MediaPlayer greatPlayer;
	private MediaPlayer popBalloonPlayer;
	private MediaPlayer excellentPlayer;
	private AudioPlayer popPlayer1;
	private AudioPlayer popPlayer2;
	private AudioPlayer popPlayer3;
	private AudioPlayer popPlayer4;
	private AudioPlayer popPlayer5;
	private int tickCount = 60;
	private CountDownTimer tickTimer;
	private boolean isGameFinish = false;
	private static final int BALOON_POP_PLAYER1 = 101;
	private static final int BALOON_POP_PLAYER2 = 102;
	private static final int BALOON_POP_PLAYER3 = 103;
	private static final int BALOON_POP_PLAYER4 = 104;
	private static final int BALOON_POP_PLAYER5 = 105;
	private int currentPopPlayer = BALOON_POP_PLAYER1;
	private int balloonRepeatTime = 4000;
	private CountDownTimer balloonTimer;
	private int WINING_SCORE = 330;

	private Bitmap mBitmapBg;
	private boolean mIsStoppedCalled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_baloon);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mBitmapBg = WeShineApp.getBitmapFromObb("baloon_game_bg.png");
		findViewById(R.id.frame_layout_container).setBackgroundDrawable(new BitmapDrawable(mBitmapBg));

		screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		SCREEN_WIDTH = UtilityMethods.getScreenWidth(this);
		SCREEN_HEIGHT = UtilityMethods.getScreenHeight(this);
		mainLayout = (AbsoluteLayout) findViewById(R.id.mainLayout);
		if (screenSize >= 9.4) {
			balloonRepeatTime = 2600;
			xStartPosPanel = SCREEN_WIDTH - (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
			yStartPosPanel = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			balloonRepeatTime = 4000;
			xStartPosPanel = SCREEN_WIDTH - (int) UtilityMethods.convertDpToPixel(90, WeShineApp.getInstance());
			yStartPosPanel = (int) UtilityMethods.convertDpToPixel(5, WeShineApp.getInstance());
		} else {
			balloonRepeatTime = 4000;
			xStartPosPanel = SCREEN_WIDTH - (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
			yStartPosPanel = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
		}

		setScoreTextView();
		setCircleTextView();
		setGolfCarImageView();
		setLakeImageView();
		setPoleImageView();
		startBackgroundMusic();
		timerText = (TextView) findViewById(R.id.sunCatcher_circleTextView);
		setSunView();
		tickTimer = new CountDownTimer(65000, 1050) {

			public void onTick(long millisUntilFinished) {
				tickCount--;
				if (!isGameFinish) {

					timerText.setText("" + tickCount);
				}
				if (tickCount == 59) {
					playPopBalloon();
				} else if (tickCount == 5 && scoreCounter >= WINING_SCORE) {
					playExcellentSound();
					currentBatteryPanel = BATTERY_PANEL_2;
					setPoleBatteryImageView(R.drawable.pole_battery);
					tickTimer.cancel();
					isGameFinish = true;
				} else if (tickCount <= 0) {
					timerText.setText("" + 0);
					if (scoreCounter < WINING_SCORE && isGameFinish == false) {
						setBalloonsVisiblility(View.INVISIBLE);
						balloonTimer.cancel();
						balloonTimer = null;
						timerText.setText("" + 0);
						baloonPanelAnimatorSet.end();
						(findViewById(R.id.gameOverImageView)).setBackgroundResource(ImageAndMediaResources.sImageIdGameOver);
						(findViewById(R.id.gameOverImageView)).setVisibility(View.VISIBLE);
						if(!mIsStoppedCalled){
							startAudioSound(ImageAndMediaResources.sSoundIdGameOver);
						}else{
							finish();
						}

						isGameFinish = true;
						tickTimer.cancel();
					}
				}
			}

			public void onFinish() {
				if (scoreCounter >= WINING_SCORE && isGameFinish == false) {
					timerText.setText("" + 0);
					playExcellentSound();
					currentBatteryPanel = BATTERY_PANEL_2;
					setPoleBatteryImageView(R.drawable.pole_battery);
					tickTimer.cancel();
					isGameFinish = true;
				}

			}

		}.start();

		initBalloonPanelView();
		balloonTimer = new BalloonTimer();
		balloonTimer.start();
		Logger.error(TAG, "sizeDiagonal%%%%%%%%%************::" + screenSize);
	}



	class BalloonTimer extends CountDownTimer {

		public BalloonTimer() {
			super(70000, balloonRepeatTime);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			Log.d(TAG, "OnTick Remaining:" + millisUntilFinished);
			startBaloonFrameAnimation();
			if (balloonPopCount == 0) {
				resetBalloonPanel();
				setBalloonsVisiblility(View.VISIBLE);
			}
		}

		@Override
		public void onFinish() {
		}

	}

	private void setSunView() {

		sunImageView = new ImageView(this);
		sunImageView.setImageResource(R.drawable.baloon_sun);
		mainLayout.addView(sunImageView);
		AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) sunImageView.getLayoutParams();
		if (screenSize >= 9.4) {
			sunWidth = (int) UtilityMethods.convertDpToPixel(153, WeShineApp.getInstance());
			params.width = sunWidth;
			int sunHeight = (int) UtilityMethods.convertDpToPixel(159, WeShineApp.getInstance());
			params.height = sunHeight;
			sunX = SCREEN_WIDTH / 2 - sunWidth / 2;
			params.x = sunX;
			sunY = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			params.y = sunY;
		} else if (screenSize >= 6.9) {
			sunWidth = (int) UtilityMethods.convertDpToPixel(140, WeShineApp.getInstance());
			params.width = sunWidth;
			int sunHeight = (int) UtilityMethods.convertDpToPixel(146, WeShineApp.getInstance());
			params.height = sunHeight;
			sunX = SCREEN_WIDTH / 2 - sunWidth / 2;
			params.x = sunX;
			sunY = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
			params.y = sunY;
		} else {
			sunWidth = (int) UtilityMethods.convertDpToPixel(85, WeShineApp.getInstance());
			params.width = sunWidth;
			int sunHeight = (int) UtilityMethods.convertDpToPixel(89, WeShineApp.getInstance());
			params.height = sunHeight;
			sunX = SCREEN_WIDTH / 2 - sunWidth / 2;
			params.x = sunX;
			sunY = (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
			params.y = sunY;
		}
		Log.d(TAG, "SCREEN_WIDTH****:" + SCREEN_WIDTH);
		// existing height is ok as is, no need to edit it
		sunImageView.setLayoutParams(params);
		// mainLayout.addView(sunImageView);
		// AnimationUtil.performFrameAnimation(sunImageView,
		// R.drawable.maze2_sun_animation);
	}

	private void setGolfCarImageView() {
		golfcarImageView = (ImageView) findViewById(R.id.baloon_golfCarImageView);
		AbsoluteLayout.LayoutParams params = (AbsoluteLayout.LayoutParams) golfcarImageView.getLayoutParams();
		// int carWidth;
		if (screenSize >= 9.4) {
			golfCarWith = (int) UtilityMethods.convertDpToPixel(300, WeShineApp.getInstance());
			params.width = golfCarWith;
			params.height = (int) UtilityMethods.convertDpToPixel(248, WeShineApp.getInstance());
			xPositionGolfCar = SCREEN_WIDTH / 3;
			params.x = xPositionGolfCar;
			yPositionGolfCar = (int) (SCREEN_HEIGHT - UtilityMethods.convertDpToPixel(285, WeShineApp.getInstance()));
			params.y = yPositionGolfCar;
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			golfCarWith = (int) UtilityMethods.convertDpToPixel(260, WeShineApp.getInstance());
			params.width = golfCarWith;
			params.height = (int) UtilityMethods.convertDpToPixel(215, WeShineApp.getInstance());
			xPositionGolfCar = SCREEN_WIDTH / 3;
			params.x = xPositionGolfCar;
			yPositionGolfCar = (int) (SCREEN_HEIGHT - UtilityMethods.convertDpToPixel(240, WeShineApp.getInstance()));
			params.y = yPositionGolfCar;
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
		} else {

			golfCarWith = (int) UtilityMethods.convertDpToPixel(140, WeShineApp.getInstance());
			params.width = golfCarWith;
			params.height = (int) UtilityMethods.convertDpToPixel(116, WeShineApp.getInstance());
			xPositionGolfCar = SCREEN_WIDTH / 3;
			params.x = xPositionGolfCar;
			yPositionGolfCar = (int) (SCREEN_HEIGHT - UtilityMethods.convertDpToPixel(145, WeShineApp.getInstance()));
			params.y = yPositionGolfCar;
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
		}

		golfCarHalfWidth = golfCarWith / 2;
	}

	private void startGolfCarAnimation() {
		golfcarImageView.setImageResource(R.drawable.golfcar_with_panel);
		try {
			carPanelImageView1.setVisibility(View.INVISIBLE);
			carPanelImageView2.setVisibility(View.INVISIBLE);
			carPanelImageView3.setVisibility(View.INVISIBLE);
			carPanelImageView4.setVisibility(View.INVISIBLE);
		} catch (Exception e) {

		}

		int toX = 500;
		int toY = 500;
		if (screenSize >= 9.4) {
			toX = -(int) UtilityMethods.convertDpToPixel(800, WeShineApp.getInstance());
			toY = (int) UtilityMethods.convertDpToPixel(250, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			toX = -(int) UtilityMethods.convertDpToPixel(500, WeShineApp.getInstance());
			toY = (int) UtilityMethods.convertDpToPixel(300, WeShineApp.getInstance());

		} else {
			toX = -(int) UtilityMethods.convertDpToPixel(500, WeShineApp.getInstance());
			toY = (int) UtilityMethods.convertDpToPixel(200, WeShineApp.getInstance());
		}

		ObjectAnimator birdAnimX = ObjectAnimator.ofFloat(golfcarImageView, "translationX", 0, toX);
		birdAnimX.setDuration(3500);
		ObjectAnimator birdAnimY = ObjectAnimator.ofFloat(golfcarImageView, "translationY", 0, toY);
		birdAnimY.setDuration(3500);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				(findViewById(R.id.gameOverImageView)).setBackgroundResource(ImageAndMediaResources.sImageIdCongratulations);
				(findViewById(R.id.gameOverImageView)).setVisibility(View.VISIBLE);
				AnimationUtil.performAnimation(findViewById(R.id.gameOverImageView), AnimType.ZOOM_IN, null);
				startAudioSound(ImageAndMediaResources.sSoundIdCongratulationsAndSound);
			}
		});
		animatorSet.play(birdAnimX).with(birdAnimY);
		animatorSet.start();

	}

	private void setPoleImageView() {

		int poleWidth;
		int poleHeight;
		int batteryBoxWidth;
		int batteryBoxHeight;
		ImageView poleImageView = (ImageView) findViewById(R.id.baloon_poleImageView);
		poleImageView.setBackgroundResource(R.drawable.baloon_pole);
		AbsoluteLayout.LayoutParams poleImageParams = (AbsoluteLayout.LayoutParams) poleImageView.getLayoutParams();

		batteryBoxImageView = (ImageView) findViewById(R.id.baloon_batteryBoxImageView);
		AbsoluteLayout.LayoutParams batteryBoxImageParams = (AbsoluteLayout.LayoutParams) batteryBoxImageView.getLayoutParams();

		if (screenSize >= 9.4) {
			// increase xPosPole to shift pole to left
			xPosPole = SCREEN_WIDTH - (int) UtilityMethods.convertDpToPixel(530, WeShineApp.getInstance());
			poleImageParams.x = xPosPole;
			// increase yPosPole to shift pole to down
			yPosPole = (int) UtilityMethods.convertDpToPixel(300, WeShineApp.getInstance());
			poleImageParams.y = yPosPole;
			poleWidth = (int) UtilityMethods.convertDpToPixel(280, WeShineApp.getInstance());
			poleHeight = (int) UtilityMethods.convertDpToPixel(344, WeShineApp.getInstance());
			poleImageParams.width = poleWidth;
			poleImageParams.height = poleHeight;
			/* Setting battery box image view on the pole */
			// increase to shift pole to right
			xPosBatteryBox = xPosPole + (int) UtilityMethods.convertDpToPixel(160, WeShineApp.getInstance());
			// increase to shift pole to up
			yPosBatteryBox = yPosPole + poleWidth - (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
			batteryBoxWidth = (int) UtilityMethods.convertDpToPixel(89, WeShineApp.getInstance());
			batteryBoxHeight = (int) UtilityMethods.convertDpToPixel(38, WeShineApp.getInstance());
			batteryBoxImageParams.x = xPosBatteryBox;
			batteryBoxImageParams.y = yPosBatteryBox;
			batteryBoxImageParams.width = batteryBoxWidth;
			batteryBoxImageParams.height = batteryBoxHeight;

		} else if (screenSize >= 6.9) {
			// increase xPosPole to shift pole to left
			xPosPole = SCREEN_WIDTH - (int) UtilityMethods.convertDpToPixel(430, WeShineApp.getInstance());
			poleImageParams.x = xPosPole;
			// increase yPosPole to shift pole to down
			yPosPole = (int) UtilityMethods.convertDpToPixel(175, WeShineApp.getInstance());
			poleImageParams.y = yPosPole;
			poleWidth = (int) UtilityMethods.convertDpToPixel(224, WeShineApp.getInstance());
			poleHeight = (int) UtilityMethods.convertDpToPixel(275, WeShineApp.getInstance());
			poleImageParams.width = poleWidth;
			poleImageParams.height = poleHeight;
			/* Setting battery box image view on the pole */
			// increase to shift box to right
			xPosBatteryBox = xPosPole + (int) UtilityMethods.convertDpToPixel(135, WeShineApp.getInstance());
			// increase to shift box to up
			yPosBatteryBox = yPosPole + poleWidth - (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
			batteryBoxWidth = (int) UtilityMethods.convertDpToPixel(65, WeShineApp.getInstance());
			batteryBoxHeight = (int) UtilityMethods.convertDpToPixel(28, WeShineApp.getInstance());
			batteryBoxImageParams.x = xPosBatteryBox;
			batteryBoxImageParams.y = yPosBatteryBox;
			batteryBoxImageParams.width = batteryBoxWidth;
			batteryBoxImageParams.height = batteryBoxHeight;

		} else {
			// increase xPosPole to shift pole to left
			xPosPole = SCREEN_WIDTH - (int) UtilityMethods.convertDpToPixel(230, WeShineApp.getInstance());
			// increase yPosPole to shift pole to down
			yPosPole = (int) UtilityMethods.convertDpToPixel(95, WeShineApp.getInstance());
			poleImageParams.x = xPosPole;
			poleImageParams.y = yPosPole;

			poleWidth = (int) UtilityMethods.convertDpToPixel(125, WeShineApp.getInstance());
			poleHeight = (int) UtilityMethods.convertDpToPixel(153, WeShineApp.getInstance());
			poleImageParams.width = poleWidth;
			poleImageParams.height = poleHeight;

			/* Setting battery box image view on the pole */
			// increase to shift box to right
			xPosBatteryBox = xPosPole + (int) UtilityMethods.convertDpToPixel(75, WeShineApp.getInstance());
			// increase to shift box to up
			yPosBatteryBox = yPosPole + poleWidth - (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
			batteryBoxWidth = (int) UtilityMethods.convertDpToPixel(38, WeShineApp.getInstance());
			batteryBoxHeight = (int) UtilityMethods.convertDpToPixel(16, WeShineApp.getInstance());
			batteryBoxImageParams.x = xPosBatteryBox;
			batteryBoxImageParams.y = yPosBatteryBox;
			batteryBoxImageParams.width = batteryBoxWidth;
			batteryBoxImageParams.height = batteryBoxHeight;
		}

		poleImageView.setLayoutParams(poleImageParams);
		batteryBoxImageView.setLayoutParams(batteryBoxImageParams);
		Log.d(TAG, "xPosPole*****" + xPosPole + " yPosPole******:" + yPosPole);

	}

	private void setPolePanelImageView(int imageDrawable) {
		int width;
		int height;
		ImageView panelImageView = null;
		if (screenSize >= 9.4) {
			width = (int) UtilityMethods.convertDpToPixel(105, WeShineApp.getInstance());
			polePanel1Height = (int) UtilityMethods.convertDpToPixel(63, WeShineApp.getInstance());
			height = polePanel1Height;
			panelImageView = getNewPanel(imageDrawable, width, height);

		} else if (screenSize >= 6.9) {
			width = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
			polePanel1Height = (int) UtilityMethods.convertDpToPixel(48, WeShineApp.getInstance());
			height = polePanel1Height;
			panelImageView = getNewPanel(imageDrawable, width, height);

		} else {

			width = (int) UtilityMethods.convertDpToPixel(45, WeShineApp.getInstance());
			polePanel1Height = (int) UtilityMethods.convertDpToPixel(27, WeShineApp.getInstance());
			height = polePanel1Height;
			panelImageView = getNewPanel(imageDrawable, width, height);
		}
		if (currentPanel == POLE_PANEL_1) {
			polePanelImageView1 = panelImageView;
		} else if (currentPanel == POLE_PANEL_2) {
			polePanelImageView2 = panelImageView;
		}
		mainLayout.addView(panelImageView);
		startPolePanelAnimation(panelImageView);
	}

	private void startPolePanelAnimation(ImageView panelImageView) {
		int toX = 0;
		int toY = 0;
		int marginX = 0;
		int marginY = 0;
		if (screenSize >= 9.4) {

			if (currentPanel == POLE_PANEL_1) {
				// increase marginX to move polePanel to right
				marginX = (int) UtilityMethods.convertDpToPixel(152, WeShineApp.getInstance());
				// increase marginY to move polePanel to down
				marginY = -(int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosPole - marginY;
			} else if (currentPanel == POLE_PANEL_2) {
				// increase marginX to move polePanel to right
				marginX = (int) UtilityMethods.convertDpToPixel(171, WeShineApp.getInstance());
				// increase marginY to move polePanel to up
				marginY = (int) UtilityMethods.convertDpToPixel(16, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosPole - marginY;
			}

		} else if (screenSize >= 6.9) {
			if (currentPanel == POLE_PANEL_1) {
				// increase marginX to move polePanel to right
				marginX = (int) UtilityMethods.convertDpToPixel(124, WeShineApp.getInstance());
				// increase marginY to move polePanel to up
				marginY = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosPole + marginY;
			} else if (currentPanel == POLE_PANEL_2) {
				// increase marginX to move polePanel to right
				marginX = (int) UtilityMethods.convertDpToPixel(141, WeShineApp.getInstance());
				// increase marginY to move polePanel to down
				marginY = (int) UtilityMethods.convertDpToPixel(22, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosPole - polePanel1Height / 2 + marginY;
			}

		} else {

			if (currentPanel == POLE_PANEL_1) {
				// increase marginX to move polePanel to right
				marginX = (int) UtilityMethods.convertDpToPixel(69, WeShineApp.getInstance());
				// increase marginY to move polePanel to up
				marginY = (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosPole - marginY;
			} else if (currentPanel == POLE_PANEL_2) {
				// increase marginX to move polePanel to right
				marginX = (int) UtilityMethods.convertDpToPixel(77, WeShineApp.getInstance());
				// increase marginY to move the panel up
				marginY = (int) UtilityMethods.convertDpToPixel(33, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosPole - polePanel1Height / 2 - marginY;
			}
		}

		startPanelAnimation(toX, toY, panelImageView);
		Log.d(TAG, "AnimatinToX*****" + toX + " AnimatinToY******:" + toY);

	}

	private void setPoleBatteryImageView(int imageDrawable) {
		int width;
		int height;
		ImageView poleBatteryImageView = null;
		if (screenSize >= 9.4) {

			width = (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(37, WeShineApp.getInstance());
			poleBatteryImageView = getNewPanel(imageDrawable, width, height);

		} else if (screenSize >= 6.9) {
			width = (int) UtilityMethods.convertDpToPixel(19, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(23, WeShineApp.getInstance());
			poleBatteryImageView = getNewPanel(imageDrawable, width, height);

		} else {

			width = (int) UtilityMethods.convertDpToPixel(11, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(13, WeShineApp.getInstance());
			poleBatteryImageView = getNewPanel(imageDrawable, width, height);
		}
		mainLayout.addView(poleBatteryImageView);
		if (currentBatteryPanel == BATTERY_PANEL_1) {
			poleBatteryImageView1 = poleBatteryImageView;
		} else if (currentBatteryPanel == BATTERY_PANEL_2) {
			poleBatteryImageView2 = poleBatteryImageView;
		}
		startBatteryPanelAnimation(poleBatteryImageView);
	}

	private void startBatteryPanelAnimation(ImageView poleBatteryImageView) {
		int toX = 0;
		int toY = 0;
		int marginX = 0;
		int marginY = 0;
		if (screenSize >= 9.4) {

			if (currentBatteryPanel == BATTERY_PANEL_1) {
				// increase to shift battery right
				marginX = (int) UtilityMethods.convertDpToPixel(192, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				// increase marginY to move tht panel up
				marginY = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
				toY = yPosBatteryBox - marginY;
				startPanelAnimation(toX, toY, poleBatteryImageView);
			}

			else if (currentBatteryPanel == BATTERY_PANEL_2) {
				marginX = (int) UtilityMethods.convertDpToPixel(217, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				// increase marginY to move tht panel up
				marginY = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
				toY = yPosBatteryBox - marginY;
			}

		} else if (screenSize >= 6.9) {
			if (currentBatteryPanel == BATTERY_PANEL_1) {
				// increase to shift battery right
				marginX = (int) UtilityMethods.convertDpToPixel(161, WeShineApp.getInstance());
				// increase marginY to move tht panel up
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosBatteryBox - (int) UtilityMethods.convertDpToPixel(5, WeShineApp.getInstance());
				startPanelAnimation(toX, toY, poleBatteryImageView);
			}

			else if (currentBatteryPanel == BATTERY_PANEL_2) {
				marginX = (int) UtilityMethods.convertDpToPixel(177, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosBatteryBox - (int) UtilityMethods.convertDpToPixel(5, WeShineApp.getInstance());
			}

		} else {

			if (currentBatteryPanel == BATTERY_PANEL_1) {
				// Increase to shift right
				marginX = (int) UtilityMethods.convertDpToPixel(90, WeShineApp.getInstance());
				// Increase to shift up
				marginY = (int) UtilityMethods.convertDpToPixel(49, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosBatteryBox - marginY;
				startPanelAnimation(toX, toY, poleBatteryImageView);
			} else if (currentBatteryPanel == BATTERY_PANEL_2) {
				marginX = (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
				marginY = (int) UtilityMethods.convertDpToPixel(49, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPosPole - marginX);
				toY = yPosBatteryBox - marginY;
			}
		}
		if (currentBatteryPanel == BATTERY_PANEL_2) {

			ObjectAnimator birdAnimX = ObjectAnimator.ofFloat(poleBatteryImageView, "translationX", 0, toX);
			birdAnimX.setDuration(5000);
			ObjectAnimator birdAnimY = ObjectAnimator.ofFloat(poleBatteryImageView, "translationY", 0, toY);
			birdAnimY.setDuration(5000);
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					stopBackgroundMusic();
					startBellSound();
					setSunRayImageView();
				}
			});
			animatorSet.play(birdAnimX).with(birdAnimY);
			animatorSet.start();
			baloonPanelAnimatorSet.end();
		}
	}

	private void setSunRayImageView() {
		int widthSunrayRight;
		int heightSunrayRight;
		int widthSunrayDown;
		int heightSunrayDown;
		if (screenSize >= 9.4) {

			widthSunrayRight = (int) UtilityMethods.convertDpToPixel(114, WeShineApp.getInstance());
			heightSunrayRight = (int) UtilityMethods.convertDpToPixel(45, WeShineApp.getInstance());

			widthSunrayDown = (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
			heightSunrayDown = (int) UtilityMethods.convertDpToPixel(176, WeShineApp.getInstance());

			sunrayRightImageView = getSunRays(R.drawable.baloon_sunrays_right, widthSunrayRight, heightSunrayRight);
			sunrayDownImageView = getSunRays(R.drawable.baloon_sun_rays_down, widthSunrayDown, heightSunrayDown);

		} else if (screenSize >= 6.9) {
			widthSunrayRight = (int) UtilityMethods.convertDpToPixel(114, WeShineApp.getInstance());
			heightSunrayRight = (int) UtilityMethods.convertDpToPixel(45, WeShineApp.getInstance());

			widthSunrayDown = (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
			heightSunrayDown = (int) UtilityMethods.convertDpToPixel(176, WeShineApp.getInstance());

			sunrayRightImageView = getSunRays(R.drawable.baloon_sunrays_right, widthSunrayRight, heightSunrayRight);
			sunrayDownImageView = getSunRays(R.drawable.baloon_sun_rays_down, widthSunrayDown, heightSunrayDown);

		} else {

			widthSunrayRight = (int) UtilityMethods.convertDpToPixel(80, WeShineApp.getInstance());
			heightSunrayRight = (int) UtilityMethods.convertDpToPixel(31, WeShineApp.getInstance());

			widthSunrayDown = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
			heightSunrayDown = (int) UtilityMethods.convertDpToPixel(117, WeShineApp.getInstance());

			sunrayRightImageView = getSunRays(R.drawable.baloon_sunrays_right, widthSunrayRight, heightSunrayRight);

			sunrayDownImageView = getSunRays(R.drawable.baloon_sun_rays_down_small, widthSunrayDown, heightSunrayDown);
		}
		mainLayout.addView(sunrayRightImageView);
		mainLayout.addView(sunrayDownImageView);
		setSunView();
		startDownSunRaysAnimation();
		startRighrSunRaysAnimation();
	}

	private void startDownSunRaysAnimation() {

		int yToPosSunRay = 0;
		if (screenSize >= 9.4) {
			// increase to shift up
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(260, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(230, WeShineApp.getInstance());

		} else {
			// increase to shift up
			yToPosSunRay = yPositionGolfCar - (int) UtilityMethods.convertDpToPixel(133, WeShineApp.getInstance());
		}
		ObjectAnimator birdAnimY = ObjectAnimator.ofFloat(sunrayDownImageView, "translationY", 0, yToPosSunRay);
		birdAnimY.setDuration(2000);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				sunrayDownImageView.setVisibility(View.INVISIBLE);
				super.onAnimationEnd(animation);
			}
		});
		animatorSet.play(birdAnimY);
		animatorSet.start();

	}

	private void startRighrSunRaysAnimation() {

		int toX = 0;
		int toY = 0;
		int margin = 0;
		if (screenSize >= 9.4) {
			// increase to shift left
			margin = (int) UtilityMethods.convertDpToPixel(200, WeShineApp.getInstance());
			toX = (xStartPosPanel - xPosPole - margin);
			// increase to shift up
			toY = yPosPole - (int) UtilityMethods.convertDpToPixel(120, WeShineApp.getInstance());

		} else if (screenSize >= 6.9) {
			// increase to shift left
			margin = (int) UtilityMethods.convertDpToPixel(220, WeShineApp.getInstance());
			toX = (xStartPosPanel - xPosPole - margin);
			toY = yPosPole - (int) UtilityMethods.convertDpToPixel(105, WeShineApp.getInstance());

		} else {
			// increase to shift left
			margin = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			toX = (xStartPosPanel - xPosPole - margin);
			// increase to shift up
			toY = yPosPole - (int) UtilityMethods.convertDpToPixel(75, WeShineApp.getInstance());
		}

		ObjectAnimator birdAnimX = ObjectAnimator.ofFloat(sunrayRightImageView, "translationX", 0, toX);
		birdAnimX.setDuration(2000);
		ObjectAnimator birdAnimY = ObjectAnimator.ofFloat(sunrayRightImageView, "translationY", 0, toY);
		birdAnimY.setDuration(2000);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {

				poleBatteryImageView1.setVisibility(View.INVISIBLE);
				poleBatteryImageView2.setVisibility(View.INVISIBLE);
				batteryBoxImageView.setVisibility(View.INVISIBLE);
				polePanelImageView1.setVisibility(View.INVISIBLE);
				polePanelImageView2.setVisibility(View.INVISIBLE);

				ImageView imgView = (ImageView) findViewById(R.id.baloon_poleImageView);
				imgView.setBackgroundResource(R.drawable.baloon_pole_with_light);
				sunrayRightImageView.setVisibility(View.INVISIBLE);
				startGolfCarAnimation();
				super.onAnimationEnd(animation);
			}
		});
		animatorSet.play(birdAnimX).with(birdAnimY);
		animatorSet.start();

	}

	private ImageView getSunRays(int panelDrawable, int width, int height) {
		ImageView panelImg = new ImageView(this);
		panelImg.setImageResource(panelDrawable);
		int fromX = SCREEN_WIDTH / 2;
		int fromY = sunY + (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
		// int fromX = SCREEN_WIDTH / 2 - sunWidth / 2;
		AbsoluteLayout.LayoutParams alp = new AbsoluteLayout.LayoutParams(width, height, fromX, fromY);
		panelImg.setLayoutParams(alp);
		return panelImg;
	}

	private void setCarPanelImageView(int panelNumber) {
		int width;
		int height;
		if (screenSize >= 9.4) {

			if (panelNumber == CAR_PANEL_1) {
				width = (int) UtilityMethods.convertDpToPixel(130, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(38, WeShineApp.getInstance());
				carPanelImageView1 = getNewPanel(R.drawable.gf1, width, height);
				mainLayout.addView(carPanelImageView1);
			} else if (panelNumber == CAR_PANEL_2) {
				width = (int) UtilityMethods.convertDpToPixel(130, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(41, WeShineApp.getInstance());
				carPanelImageView2 = getNewPanel(R.drawable.gf2, width, height);
				mainLayout.addView(carPanelImageView2);

			} else if (panelNumber == CAR_PANEL_3) {

				width = (int) UtilityMethods.convertDpToPixel(130, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(36, WeShineApp.getInstance());
				carPanelImageView3 = getNewPanel(R.drawable.gf3, width, height);
				mainLayout.addView(carPanelImageView3);

			} else if (panelNumber == CAR_PANEL_4) {
				width = (int) UtilityMethods.convertDpToPixel(130, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
				carPanelImageView4 = getNewPanel(R.drawable.gf4, width, height);
				mainLayout.addView(carPanelImageView4);
			}

		} else if (screenSize >= 6.9) {
			if (panelNumber == CAR_PANEL_1) {
				width = (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(29, WeShineApp.getInstance());
				carPanelImageView1 = getNewPanel(R.drawable.gf1, width, height);
				mainLayout.addView(carPanelImageView1);
			} else if (panelNumber == CAR_PANEL_2) {
				width = (int) UtilityMethods.convertDpToPixel(105, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(33, WeShineApp.getInstance());
				carPanelImageView2 = getNewPanel(R.drawable.gf2, width, height);
				mainLayout.addView(carPanelImageView2);

			} else if (panelNumber == CAR_PANEL_3) {

				width = (int) UtilityMethods.convertDpToPixel(104, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(29, WeShineApp.getInstance());
				carPanelImageView3 = getNewPanel(R.drawable.gf3, width, height);
				mainLayout.addView(carPanelImageView3);

			} else if (panelNumber == CAR_PANEL_4) {
				width = (int) UtilityMethods.convertDpToPixel(103, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(32, WeShineApp.getInstance());
				carPanelImageView4 = getNewPanel(R.drawable.gf4, width, height);
				mainLayout.addView(carPanelImageView4);
			}

		} else {
			if (panelNumber == CAR_PANEL_1) {
				width = (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(17, WeShineApp.getInstance());
				carPanelImageView1 = getNewPanel(R.drawable.gf1, width, height);
				mainLayout.addView(carPanelImageView1);
			} else if (panelNumber == CAR_PANEL_2) {
				width = (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(19, WeShineApp.getInstance());
				carPanelImageView2 = getNewPanel(R.drawable.gf2, width, height);
				mainLayout.addView(carPanelImageView2);

			} else if (panelNumber == CAR_PANEL_3) {

				width = (int) UtilityMethods.convertDpToPixel(57, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(16, WeShineApp.getInstance());
				carPanelImageView3 = getNewPanel(R.drawable.gf3, width, height);
				mainLayout.addView(carPanelImageView3);

			} else if (panelNumber == CAR_PANEL_4) {
				width = (int) UtilityMethods.convertDpToPixel(58, WeShineApp.getInstance());
				height = (int) UtilityMethods.convertDpToPixel(18, WeShineApp.getInstance());
				carPanelImageView4 = getNewPanel(R.drawable.gf4, width, height);
				mainLayout.addView(carPanelImageView4);
			}
		}
		startCarPanelAnimation(panelNumber);
	}

	private ImageView getNewPanel(int panelDrawable, int width, int height) {
		ImageView panelImg = new ImageView(this);
		panelImg.setImageResource(panelDrawable);
		AbsoluteLayout.LayoutParams alp = new AbsoluteLayout.LayoutParams(width, height, xStartPosPanel, yStartPosPanel);
		panelImg.setLayoutParams(alp);
		return panelImg;
	}

	private void startBellSound() {
		MediaPlayer mediaPlayer = null;
		try {
			AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor("bell_sound.mp3");
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
			mediaPlayer.prepare();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			if(mediaPlayer != null){
				mediaPlayer.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

//		String uriPath = AppConstant.BASE_RESOURCE_PATH + R.raw.bell_sound;
//		Uri uri = Uri.parse(uriPath);
//		MediaPlayer mediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);
//		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
//
//			@Override
//			public void onCompletion(MediaPlayer mp) {
//				mp.release();
//			}
//		});
//		mediaPlayer.start();
	}

	private void startCarPanelAnimation(int panelNumber) {
		int toX = 0;
		int toY = 0;
		int marginX = 0;
		int marginY = 0;
		if (screenSize >= 9.4) {
			if (panelNumber == CAR_PANEL_1) {
				// increase to shift right
				marginX = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
				// increase to shift down
				marginY = (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView1);
			} else if (panelNumber == CAR_PANEL_2) {
				marginX = (int) UtilityMethods.convertDpToPixel(91, WeShineApp.getInstance());
				marginY = (int) UtilityMethods.convertDpToPixel(23, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView2);
			} else if (panelNumber == CAR_PANEL_3) {
				// increase to shift right
				marginX = (int) UtilityMethods.convertDpToPixel(131, WeShineApp.getInstance());
				// increase to shift up
				marginY = -(int) UtilityMethods.convertDpToPixel(8, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView3);
			} else if (panelNumber == CAR_PANEL_4) {
				// increase to shift right
				marginX = (int) UtilityMethods.convertDpToPixel(177, WeShineApp.getInstance());
				// increase to shift down
				marginY = (int) UtilityMethods.convertDpToPixel(2, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView4);
			}

		} else if (screenSize >= 6.9) {
			if (panelNumber == CAR_PANEL_1) {
				marginX = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
				marginY = (int) UtilityMethods.convertDpToPixel(17, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView1);
			} else if (panelNumber == CAR_PANEL_2) {
				marginX = (int) UtilityMethods.convertDpToPixel(81, WeShineApp.getInstance());
				marginY = (int) UtilityMethods.convertDpToPixel(27, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView2);
			} else if (panelNumber == CAR_PANEL_3) {
				marginX = (int) UtilityMethods.convertDpToPixel(115, WeShineApp.getInstance());
				marginY = (int) UtilityMethods.convertDpToPixel(3, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView3);
			} else if (panelNumber == CAR_PANEL_4) {
				marginX = (int) UtilityMethods.convertDpToPixel(149, WeShineApp.getInstance());
				marginY = (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView4);
			}

		} else {

			if (panelNumber == CAR_PANEL_1) {
				marginX = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
				marginY = -(int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView1);
			} else if (panelNumber == CAR_PANEL_2) {
				marginX = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
				marginY = (int) UtilityMethods.convertDpToPixel(29, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar - marginY;
				startPanelAnimation(toX, toY, carPanelImageView2);
			} else if (panelNumber == CAR_PANEL_3) {
				// increase to shift right
				marginX = (int) UtilityMethods.convertDpToPixel(57, WeShineApp.getInstance());
				// increase to shift up
				marginY = -(int) UtilityMethods.convertDpToPixel(42, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView3);
			} else if (panelNumber == CAR_PANEL_4) {
				// //increase to shift right
				marginX = (int) UtilityMethods.convertDpToPixel(75, WeShineApp.getInstance());
				// increase to shift up
				marginY = -(int) UtilityMethods.convertDpToPixel(39, WeShineApp.getInstance());
				toX = -(xStartPosPanel - xPositionGolfCar - marginX);
				toY = yPositionGolfCar + marginY;
				startPanelAnimation(toX, toY, carPanelImageView4);
			}
		}

		Log.d(TAG, "AnimatinToX*****" + toX + " AnimatinToY******:" + toY);
	}

	private void startPanelAnimation(int xToPosSunRay, int yToPosSunRay, ImageView imageView) {

		ObjectAnimator birdAnimX = ObjectAnimator.ofFloat(imageView, "translationX", 0, xToPosSunRay);
		birdAnimX.setDuration(5000);
		ObjectAnimator birdAnimY = ObjectAnimator.ofFloat(imageView, "translationY", 0, yToPosSunRay);
		birdAnimY.setDuration(5000);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
			}
		});
		animatorSet.play(birdAnimX).with(birdAnimY);
		animatorSet.start();

	}

	private void setScoreTextView() {
		scoreTextView = (TextView) findViewById(R.id.baloon_scoreTextView);
		LayoutParams params = (LayoutParams) scoreTextView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.topMargin = (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
			scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
		} else if (screenSize >= 6.9) {
			params.topMargin = (int) UtilityMethods.convertDpToPixel(75, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(10, WeShineApp.getInstance());
			scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		} else {
			params.topMargin = (int) UtilityMethods.convertDpToPixel(30, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(8, WeShineApp.getInstance());
			scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		}

	}

	private void setCircleTextView() {
		TextView imageView = (TextView) findViewById(R.id.sunCatcher_circleTextView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.width = (int) UtilityMethods.convertDpToPixel(125, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(125, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(135, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
			imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(103, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(102, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(110, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(75, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(75, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(-5, WeShineApp.getInstance());
			imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

		}

	}

	private void startBackgroundMusic() {

//		String uriPath = AppConstant.BASE_RESOURCE_PATH + R.raw.sun_cather_music;
//		Uri uri = Uri.parse(uriPath);
//		backgroundMusicMediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);
//		backgroundMusicMediaPlayer.setOnCompletionListener(
//				new OnCompletionListener() {
//			@Override
//			public void onCompletion(MediaPlayer mp) {
//				if (mp != null) {
//					mp.release();
//				}
//			}
//		});
//		backgroundMusicMediaPlayer.start();

		try {
			AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor("sun_cather_music.mp3");
			backgroundMusicMediaPlayer = new MediaPlayer();
			backgroundMusicMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			backgroundMusicMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
			backgroundMusicMediaPlayer.prepare();
			backgroundMusicMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			if(backgroundMusicMediaPlayer != null){
				backgroundMusicMediaPlayer.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}




	}

	protected void startAudioSound(int audioFileId) {
		String uriPath = AppConstant.BASE_RESOURCE_PATH + audioFileId;
		Uri uri = Uri.parse(uriPath);
		MediaPlayer mediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				if (mp != null) {
					mp.release();
					mp = null;
				}
				//TODO place to end the activity after game end
				finish();
			}
		});
		mediaPlayer.start();
	}

	protected void startAudioSound(String audioFileName) {
		MediaPlayer mediaPlayer = null;
		try {
			AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor(audioFileName+".mp3");
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
			mediaPlayer.prepare();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mediaPlayer.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setLakeImageView() {
		ImageView imageView = (ImageView) findViewById(R.id.baloon_lakeImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.width = (int) UtilityMethods.convertDpToPixel(475, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(144, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(300, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(91, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(200, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(61, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
		}

		AnimationUtil.performFrameAnimation((ImageView) findViewById(R.id.baloon_lakeImageView), R.drawable.baloon_water_animation);

	}

	public void onBaloonClick(View v) {
		v.setVisibility(View.INVISIBLE);
		balloonPopCount++;
		if (balloonPopCount == 13) {
			balloonPopCount = 0;
			balloonTimer.cancel();
			balloonTimer = null;
			balloonTimer = new BalloonTimer();
			balloonTimer.start();
		}
		int clickXPos = (int) v.getX();
		int clickYPos = (int) v.getY();
		int left = v.getLeft();
		int top = v.getTop();
		Log.d(TAG, "clickXPos^^^^^^^^^^^^:" + clickXPos + " clickYPos:" + clickYPos + " clickLeftX:" + left + " clickLeftY:" + top);
		String tag = (String) v.getTag();
		playBalloonSound();
		if (tag.equals(getResources().getString(R.string.play_five))) {
			startDropAnimation(getFiveImageView(left, top));
			scoreCounter = scoreCounter + 5;
			scoreTextView.setText("Score: " + scoreCounter);
//			 if (scoreCounter == 5) {
//			 setSunRayImageView();
//			 setCarPanelImageView(CAR_PANEL_1);
//			 setCarPanelImageView(CAR_PANEL_2);
//			 setCarPanelImageView(CAR_PANEL_3);
//			 setCarPanelImageView(CAR_PANEL_4);
//
//			 currentPanel = POLE_PANEL_1;
//			 setPolePanelImageView(R.drawable.pole_panel1);
//			 currentBatteryPanel = BATTERY_PANEL_1;
//			 setPoleBatteryImageView(R.drawable.pole_battery);
//			 }
//			 if (scoreCounter == 10) {
//			 currentPanel = POLE_PANEL_2;
//			 setPolePanelImageView(R.drawable.pole_panel2);
//			 //setCarPanelImageView(CAR_PANEL_2);
//
//			 currentBatteryPanel = BATTERY_PANEL_2;
//			 setPoleBatteryImageView(R.drawable.pole_battery);
//			 }
//			 if (scoreCounter == 15) {
//
//			  //setCarPanelImageView(CAR_PANEL_3);
//			 }
//			 if (scoreCounter == 20) {
//
//			  //setCarPanelImageView(CAR_PANEL_4);
//			 }
			if (scoreCounter == 35) {
				playWelldoneSound();
				currentCarPanel = CAR_PANEL_1;
				setCarPanelImageView(currentCarPanel);
			} else if (scoreCounter == 70) {
				playPerfectSound();
				currentCarPanel = CAR_PANEL_2;
				setCarPanelImageView(currentCarPanel);
			} else if (scoreCounter == 105) {
				playGoodJobSound();
				// startAudioSound(R.raw.goodjob);
				currentCarPanel = CAR_PANEL_3;
				setCarPanelImageView(currentCarPanel);
			} else if (scoreCounter == 140) {
				// startAudioSound(R.raw.super_sound);
				playSuperSound();
				currentCarPanel = CAR_PANEL_4;
				setCarPanelImageView(currentCarPanel);
			} else if (scoreCounter == 175) {
				playFabulousSound();
				// startAudioSound(R.raw.fabulous);
				currentPanel = POLE_PANEL_1;
				setPolePanelImageView(R.drawable.pole_panel1);
			} else if (scoreCounter == 210) {
				// startAudioSound(R.raw.great);
				playGreatSound();
				currentPanel = POLE_PANEL_2;
				setPolePanelImageView(R.drawable.pole_panel2);
			} else if (scoreCounter == 245) {
				// setSunRayImageView();
				// startAudioSound(R.raw.well_done);
				currentBatteryPanel = BATTERY_PANEL_1;

				setPoleBatteryImageView(R.drawable.pole_battery);
			} else if (scoreCounter == WINING_SCORE) {
				playExcellentSound();
				currentBatteryPanel = BATTERY_PANEL_2;
				setPoleBatteryImageView(R.drawable.pole_battery);
				tickTimer.cancel();
				isGameFinish = true;
				setBalloonsVisiblility(View.INVISIBLE);
				balloonTimer.cancel();
				balloonTimer = null;
			}

		} else if (tag.equals(getResources().getString(R.string.play_flower))) {
			startDropAnimation(getFlowerImageView(left, top));
		}

	}

	private void playPerfectSound() {
		//String uriPath = AppConstant.BASE_RESOURCE_PATH + R.raw.perfect;
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdPerfect;
		Uri uri = Uri.parse(uriPath);
		perfectPlayer = MediaPlayer.create(this, uri);
		perfectPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				perfectPlayer.release();
				perfectPlayer = null;
			}
		});
		perfectPlayer.start();

	}

	private void playGoodJobSound() {
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdGoodjob;
		Uri uri = Uri.parse(uriPath);
		goodJobPlayer = MediaPlayer.create(this, uri);
		goodJobPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				goodJobPlayer.release();
				goodJobPlayer = null;
			}
		});
		goodJobPlayer.start();

	}

	private void playPopBalloon() {
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdPopTheBalloons;
		Uri uri = Uri.parse(uriPath);
		popBalloonPlayer = MediaPlayer.create(this, uri);
		popBalloonPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				popBalloonPlayer.release();
				popBalloonPlayer = null;
			}
		});
		popBalloonPlayer.start();

	}

	private void playSuperSound() {
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdSuper;
		Uri uri = Uri.parse(uriPath);
		superPlayer = MediaPlayer.create(this, uri);
		superPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				superPlayer.release();
				superPlayer = null;
			}
		});
		superPlayer.start();

	}

	private void playFabulousSound() {
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdFabulous;
		Uri uri = Uri.parse(uriPath);
		fabulousPlayer = MediaPlayer.create(this, uri);
		fabulousPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				fabulousPlayer.release();
				fabulousPlayer = null;
			}
		});
		fabulousPlayer.start();

	}

	private void playGreatSound() {
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdGreat;
		Uri uri = Uri.parse(uriPath);
		greatPlayer = MediaPlayer.create(this, uri);
		greatPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				greatPlayer.release();
				greatPlayer = null;
			}
		});
		greatPlayer.start();

	}

	private void playExcellentSound() {
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdExcellent;
		Uri uri = Uri.parse(uriPath);
		excellentPlayer = MediaPlayer.create(this, uri);
		excellentPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				excellentPlayer.release();
				excellentPlayer = null;
			}
		});
		excellentPlayer.start();

	}

	private void playWelldoneSound() {
		//String uriPath = AppConstant.BASE_RESOURCE_PATH + R.raw.well_done;
		String uriPath = AppConstant.BASE_RESOURCE_PATH + ImageAndMediaResources.sSoundIdWellDone;
		Uri uri = Uri.parse(uriPath);
		welldonePlayer = MediaPlayer.create(this, uri);
		welldonePlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				welldonePlayer.release();
				welldonePlayer = null;
			}
		});
		welldonePlayer.start();

	}

	private void startDropAnimation(final ImageView imageView) {
		ObjectAnimator birdAnimY = ObjectAnimator.ofFloat(imageView, "translationY", 0, 100);
		birdAnimY.setDuration(400);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				imageView.setVisibility(View.GONE);
				super.onAnimationEnd(animation);
			}
		});
		animatorSet.play(birdAnimY);
		animatorSet.start();
	}

	private ImageView getFlowerImageView(int left, int top) {
		ImageView flowerImg = new ImageView(this);
		flowerImg.setImageResource(R.drawable.show_star);
		int width = 0;
		int height = 0;
		if (screenSize >= 9.4) {

			width = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			left = left + 3 * width;
			top = top + 6 * height;

		} else if (screenSize >= 6.9) {
			width = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			left = left + 3 * width;
			top = top + 4 * height;
		} else {

			width = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			left = left + 3 * width;
			top = top + 4 * height;
		}
		AbsoluteLayout.LayoutParams alp = new AbsoluteLayout.LayoutParams(width, height, left, top);
		flowerImg.setLayoutParams(alp);
		mainLayout.addView(flowerImg);
		return flowerImg;
	}

	private ImageView getFiveImageView(int left, int top) {
		ImageView fiveImg = new ImageView(this);
		fiveImg.setImageResource(R.drawable.five);
		int width = 0;
		int height = 0;
		if (screenSize >= 9.4) {

			width = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			left = left + 3 * width;
			top = top + 4 * height;

		} else if (screenSize >= 6.9) {
			width = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
			left = left + 3 * width;
			top = top + 3 * height;
		} else {
			width = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			height = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			left = left + 3 * width;
			top = top + 3 * height;
		}
		AbsoluteLayout.LayoutParams alp = new AbsoluteLayout.LayoutParams(width, height, left, top);
		fiveImg.setLayoutParams(alp);
		mainLayout.addView(fiveImg);
		return fiveImg;
	}

	private void playBalloonSound() {

		//String uriPath = AppConstant.BASE_RESOURCE_PATH + R.raw.balloon_sound;
		//Uri uri = Uri.parse(uriPath);
		if (currentPopPlayer == BALOON_POP_PLAYER1) {
			popPlayer2 = new AudioPlayer(this, "balloon_sound");
			popPlayer2.start();
			currentPopPlayer = BALOON_POP_PLAYER2;
			if (popPlayer5 != null) {
				popPlayer5.release();
				popPlayer5 = null;
			}
		} else if (currentPopPlayer == BALOON_POP_PLAYER2) {
			popPlayer3 = new AudioPlayer(this, "balloon_sound");
			popPlayer3.start();
			currentPopPlayer = BALOON_POP_PLAYER3;
			if (popPlayer1 != null) {
				popPlayer1.release();
				popPlayer1 = null;
			}
		} else if (currentPopPlayer == BALOON_POP_PLAYER3) {
			popPlayer4 = new AudioPlayer(this, "balloon_sound");
			popPlayer4.start();
			currentPopPlayer = BALOON_POP_PLAYER4;
			if (popPlayer2 != null) {
				popPlayer2.release();
				popPlayer2 = null;
			}
		} else if (currentPopPlayer == BALOON_POP_PLAYER4) {
			popPlayer5 = new AudioPlayer(this, "balloon_sound");
			popPlayer5.start();
			currentPopPlayer = BALOON_POP_PLAYER5;
			if (popPlayer3 != null) {
				popPlayer3.release();
				popPlayer3 = null;
			}
		} else if (currentPopPlayer == BALOON_POP_PLAYER5) {
			popPlayer1 = new AudioPlayer(this, "balloon_sound");
			popPlayer1.start();
			currentPopPlayer = BALOON_POP_PLAYER1;
			if (popPlayer4 != null) {
				popPlayer4.release();
				popPlayer4 = null;
			}
		}
	}

	private void initBalloonPanelView() {

		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AbsoluteLayout.LayoutParams alp;
		if (screenSize >= 9.4) {
			baloonFrame = layoutInflater.inflate(R.layout.baloon_frame_large, null);
			mainLayout.addView(baloonFrame);
			alp = (AbsoluteLayout.LayoutParams) baloonFrame.getLayoutParams();
			alp.y = SCREEN_HEIGHT - (int) UtilityMethods.convertDpToPixel(150, WeShineApp.getInstance());
			alp.x = (int) UtilityMethods.convertDpToPixel(200, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {

			baloonFrame = layoutInflater.inflate(R.layout.baloon_frame_medium, null);
			mainLayout.addView(baloonFrame);
			alp = (AbsoluteLayout.LayoutParams) baloonFrame.getLayoutParams();
			alp.y = SCREEN_HEIGHT - (int) UtilityMethods.convertDpToPixel(150, WeShineApp.getInstance());
			alp.x = (int) UtilityMethods.convertDpToPixel(140, WeShineApp.getInstance());
		} else {
			baloonFrame = layoutInflater.inflate(R.layout.baloon_frame_small, null);
			mainLayout.addView(baloonFrame);
			alp = (AbsoluteLayout.LayoutParams) baloonFrame.getLayoutParams();
			alp.y = SCREEN_HEIGHT - (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
			alp.x = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
		}
		balloon1 = baloonFrame.findViewById(R.id.imageView1);
		balloon2 = baloonFrame.findViewById(R.id.imageView2);
		balloon3 = baloonFrame.findViewById(R.id.imageView3);
		balloon4 = baloonFrame.findViewById(R.id.imageView4);
		balloon5 = baloonFrame.findViewById(R.id.imageView5);
		balloon6 = baloonFrame.findViewById(R.id.imageView6);
		balloon7 = baloonFrame.findViewById(R.id.imageView7);
		balloon8 = baloonFrame.findViewById(R.id.imageView8);
		balloon9 = baloonFrame.findViewById(R.id.imageView9);
		balloon10 = baloonFrame.findViewById(R.id.imageView10);
		balloon11 = baloonFrame.findViewById(R.id.imageView11);
		balloon12 = baloonFrame.findViewById(R.id.imageView12);
		balloon13 = baloonFrame.findViewById(R.id.imageView13);
		baloonFrame.setLayoutParams(alp);
	}

	private void setBalloonsVisiblility(int visiblility) {
		balloon1.setVisibility(visiblility);
		balloon2.setVisibility(visiblility);
		balloon3.setVisibility(visiblility);
		balloon4.setVisibility(visiblility);
		balloon5.setVisibility(visiblility);
		balloon6.setVisibility(visiblility);
		balloon7.setVisibility(visiblility);
		balloon8.setVisibility(visiblility);
		balloon9.setVisibility(visiblility);
		balloon10.setVisibility(visiblility);
		balloon11.setVisibility(visiblility);
		balloon12.setVisibility(visiblility);
		balloon13.setVisibility(visiblility);
	}

	private void resetBalloonPanel() {
		mainLayout.removeView(baloonFrame);
		mainLayout.addView(baloonFrame);
	}

	private void startBaloonFrameAnimation() {
		ObjectAnimator balloonPanelAnimY = ObjectAnimator.ofFloat(baloonFrame, "translationY", 0, (int) (-1.7 * SCREEN_HEIGHT));
		if (screenSize >= 9.4) {
			balloonPanelAnimY.setDuration(4000);
		} else {
			balloonPanelAnimY.setDuration(5000);
		}
		baloonPanelAnimatorSet = new AnimatorSet();
		baloonPanelAnimatorSet.play(balloonPanelAnimY);
		baloonPanelAnimatorSet.start();
	}

	private void stopBackgroundMusic() {
		if (backgroundMusicMediaPlayer != null) {
			try {
				backgroundMusicMediaPlayer.pause();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onResume() {
		mIsStoppedCalled = false;
		if (backgroundMusicMediaPlayer != null && !isGameFinish) {
			backgroundMusicMediaPlayer.start();
		}
		super.onResume();
		System.gc();
	}

	@Override
	protected void onStop() {
		mIsStoppedCalled = true;
		stopBackgroundMusic();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		tickTimer.cancel();
		tickTimer = null;
		System.gc();
		if(mBitmapBg != null){
			mBitmapBg.recycle();
			mBitmapBg = null;
		}
	}

}
