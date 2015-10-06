package com.moderneng.framents;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.moderneng.listeners.OnGameEndListener;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;
import com.moderneng.utils.Logger;
import com.moderneng.utils.UtilityMethods;
import com.moderneng.views.DrawingSurface;
import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.activities.MazeMenuActivity;

public class MazeGame2Fragment extends BaseFragment implements OnGameEndListener, AnimationListener {

	// Drawing surface to draw the path on
	private DrawingSurface mDrawingSurface;
	private  AnimationDrawable mBirdsAnimationDrawable;
	//private  AnimationDrawable mSunAnimationDrawable;
	//private  AnimationDrawable mRedShipAnimationDrawable;
	//private  AnimationDrawable mGreenShipAnimationDrawable;
	//private  AnimationDrawable mSeaAnimationDrawable;


    private Bitmap mTopBitmap;
    private Bitmap mMiddleBitmap;
    private Bitmap mBottomBitmap;
    private Bitmap mBitmapFabulous;

	private GameMusic mGameMusic1;
	private GameMusic mGameMusic2;
	private GameMusic mGameMusicEnd;


	@Override
	protected int getFragmentLayoutId() {
		return R.layout.maze_game2;
	}

	@Override
	public void onResume() {
		super.onResume();
		System.gc();
		mDrawingSurface = (DrawingSurface) getFragmentView().findViewById(R.id.mazeGame2_middleImageView);
		mDrawingSurface.setOnGameEndListener(this);
		mDrawingSurface.setHotSpotImageView((ImageView) getFragmentView().findViewById(R.id.mazeGame2_bottomImageView));

        setBackgroundImages();
		setAnimatedBirdsView();
		setAnimatedSunView();
		setAnimationGreenShipView();
		setAnimationRedShipView();
		setYellowShipView();
		setSeaAnimation();
		///startAudioSound(R.raw.maze1_ondraw);
		mGameMusic1 = new GameMusic(getActivity(), "maze1_ondraw");
		mGameMusic1.start();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mGameMusic2 = new GameMusic(getActivity(), ImageAndMediaResources.sSoundIdMaze2);
				mGameMusic2.start();
			}
		}, 500);
	}

    private void setBackgroundImages() {

        //mTopBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.maze2_top_img);
        mTopBitmap =  WeShineApp.getBitmapFromObb("maze2_top_img.png");
        ((ImageView)getFragmentView().findViewById(R.id.mazeGame2_topImageView)).setImageBitmap(mTopBitmap);

        //mMiddleBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.maze2_middle_img);
        mMiddleBitmap =  WeShineApp.getBitmapFromObb("maze2_middle_img.png");
        mDrawingSurface.setImageBitmap(mMiddleBitmap);

        //mBottomBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.maze2_bottom_img);
        mBottomBitmap =  WeShineApp.getBitmapFromObb("maze2_bottom_img.png");
        ((ImageView)getFragmentView().findViewById(R.id.mazeGame2_bottomImageView)).setImageBitmap(mBottomBitmap);

		mBitmapFabulous = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdFabulous);
		((ImageView)getFragmentView().findViewById(R.id.mazeGame2_fabulusImageView)).setImageBitmap(mBitmapFabulous);


    }


    private void setAnimatedBirdsView() {
        //mBirdsAnimationDrawable = new AnimationDrawable();
        int[] animationImages = {R.drawable.maze2birds1, R.drawable.maze2birds2, R.drawable.maze2birds3, R.drawable.maze2birds4, R.drawable.maze2birds5, R.drawable.maze2birds6, R.drawable.maze2birds7, R.drawable.maze2birds8, R.drawable.maze2birds9, R.drawable.maze2birds10, R.drawable.maze2birds11, R.drawable.maze2birds12, R.drawable.maze2birds13, R.drawable.maze2birds14, R.drawable.maze2birds15, R.drawable.maze2birds16, R.drawable.maze2birds17, R.drawable.maze2birds18, R.drawable.maze2birds19, R.drawable.maze2birds20, R.drawable.maze2birds21, R.drawable.maze2birds22, R.drawable.maze2birds23, R.drawable.maze2birds24, R.drawable.maze2birds25, R.drawable.maze2birds26, R.drawable.maze2birds27, R.drawable.maze2birds28, R.drawable.maze2birds29, R.drawable.maze2birds30, R.drawable.maze2birds31, R.drawable.maze2birds32};

        //mBirdsAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.birds_animation_frame_duration));
        mBirdsAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.birds_animation_frame_duration));
        mBirdsAnimationDrawable.setOneShot(false);
        ((ImageView) getFragmentView().findViewById(R.id.mazeGame2_birdsImageView)).setImageDrawable(mBirdsAnimationDrawable);
        mBirdsAnimationDrawable.start();

		//AnimationUtil.performFrameAnimation((ImageView) getFragmentView().findViewById(R.id.mazeGame2_birdsImageView), R.drawable.maze2_birds_animation);
	}

	private void setAnimatedSunView() {

		double screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		Logger.error(getTag(), "size%%%%%%%%%%@@@@@@@@************::" + screenSize);
		ImageView imageView = (ImageView) getFragmentView().findViewById(R.id.mazeGame2_sunImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 10) {
			params.width = (int) UtilityMethods.convertDpToPixel(198, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(198, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(153, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(159, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(18, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(82, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(82, WeShineApp.getInstance());
		}

		// existing height is ok as is, no need to edit it

		//AnimationUtil.performFrameAnimation((ImageView) getFragmentView().findViewById(R.id.mazeGame2_sunImageView), R.drawable.maze2_sun_animation);

        //int[] animationImages = {R.drawable.maze2sun1,R.drawable.maze2sun2,R.drawable.maze2sun3,R.drawable.maze2sun4,R.drawable.maze2sun5,R.drawable.maze2sun6,R.drawable.maze2sun7,R.drawable.maze2sun8,R.drawable.maze2sun9,R.drawable.maze2sun10,R.drawable.maze2sun11,R.drawable.maze2sun12,R.drawable.maze2sun13,R.drawable.maze2sun14,R.drawable.maze2sun15,R.drawable.maze2sun16,R.drawable.maze2sun17};
        //int[] animationImages = {R.drawable.maze2sun1,R.drawable.maze2sun3,R.drawable.maze2sun5,R.drawable.maze2sun7,R.drawable.maze2sun9,R.drawable.maze2sun11,R.drawable.maze2sun13,R.drawable.maze2sun15,R.drawable.maze2sun17};
        //mSunAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.maze1sun_animation_frame_duration));
        //mSunAnimationDrawable.setOneShot(false);
        //((ImageView) getFragmentView().findViewById(R.id.mazeGame2_sunImageView)).setImageDrawable(mSunAnimationDrawable);
        //mSunAnimationDrawable.start();
	}

	private void setAnimationGreenShipView() {
		double screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		ImageView imageView = (ImageView) getFragmentView().findViewById(R.id.mazeGame2_greenShipImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 10) {
			params.width = (int) UtilityMethods.convertDpToPixel(227, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(390, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(5, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(154, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(265, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(8, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(60, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(82, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(140, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(8, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(35, WeShineApp.getInstance());
		}

        //int[] animationImages = {R.drawable.maze2_green_ship1, R.drawable.maze2_green_ship1,R.drawable.maze2_green_ship2,R.drawable.maze2_green_ship3,R.drawable.maze2_green_ship4,R.drawable.maze2_green_ship5,R.drawable.maze2_green_ship6,R.drawable.maze2_green_ship7,R.drawable.maze2_green_ship8,R.drawable.maze2_green_ship9,R.drawable.maze2_green_ship10,R.drawable.maze2_green_ship11,R.drawable.maze2_green_ship12,R.drawable.maze2_green_ship13,R.drawable.maze2_green_ship14,R.drawable.maze2_green_ship15,R.drawable.maze2_green_ship16,R.drawable.maze2_green_ship17, R.drawable.maze2_green_ship18, R.drawable.maze2_green_ship19, R.drawable.maze2_green_ship20, R.drawable.maze2_green_ship21, R.drawable.maze2_green_ship22, R.drawable.maze2_green_ship23, R.drawable.maze2_green_ship24, R.drawable.maze2_green_ship25, R.drawable.maze2_green_ship26, R.drawable.maze2_green_ship27, R.drawable.maze2_green_ship28, R.drawable.maze2_green_ship29, R.drawable.maze2_green_ship30, R.drawable.maze2_green_ship31, R.drawable.maze2_green_ship32, R.drawable.maze2_green_ship33, R.drawable.maze2_green_ship34, R.drawable.maze2_green_ship35, R.drawable.maze2_green_ship36};
        //int[] animationImages = {R.drawable.maze2_green_ship1,R.drawable.maze2_green_ship3,R.drawable.maze2_green_ship5,R.drawable.maze2_green_ship7,R.drawable.maze2_green_ship9,R.drawable.maze2_green_ship11,R.drawable.maze2_green_ship13,R.drawable.maze2_green_ship15,R.drawable.maze2_green_ship17, R.drawable.maze2_green_ship19, R.drawable.maze2_green_ship21,  R.drawable.maze2_green_ship23, R.drawable.maze2_green_ship25,  R.drawable.maze2_green_ship27, R.drawable.maze2_green_ship29, R.drawable.maze2_green_ship31, R.drawable.maze2_green_ship33, R.drawable.maze2_green_ship35};
        //mGreenShipAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.ship_green_animation_frame_duration));
        //mGreenShipAnimationDrawable.setOneShot(false);
        //((ImageView) getFragmentView().findViewById(R.id.mazeGame2_greenShipImageView)).setImageDrawable(mGreenShipAnimationDrawable);
        //mGreenShipAnimationDrawable.start();
		//AnimationUtil.performFrameAnimation((ImageView) getFragmentView().findViewById(R.id.mazeGame2_greenShipImageView), R.drawable.maze2_green_ship_animation);
	}

	private void setAnimationRedShipView() {
		double screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		ImageView imageView = (ImageView) getFragmentView().findViewById(R.id.mazeGame2_redShipImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 10) {
			params.width = (int) UtilityMethods.convertDpToPixel(129, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(300, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(110, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(88, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(205, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(85, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(116, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(0, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(47, WeShineApp.getInstance());
		}

        //int[] animationImages = {R.drawable.maze2_red_ship1, R.drawable.maze2_red_ship1,R.drawable.maze2_red_ship2,R.drawable.maze2_red_ship3,R.drawable.maze2_red_ship4,R.drawable.maze2_red_ship5,R.drawable.maze2_red_ship6,R.drawable.maze2_red_ship7,R.drawable.maze2_red_ship8,R.drawable.maze2_red_ship9,R.drawable.maze2_red_ship10,R.drawable.maze2_red_ship11,R.drawable.maze2_red_ship12,R.drawable.maze2_red_ship13,R.drawable.maze2_red_ship14,R.drawable.maze2_red_ship15,R.drawable.maze2_red_ship16,R.drawable.maze2_red_ship17, R.drawable.maze2_red_ship18, R.drawable.maze2_red_ship19, R.drawable.maze2_red_ship20, R.drawable.maze2_red_ship21, R.drawable.maze2_red_ship22, R.drawable.maze2_red_ship23, R.drawable.maze2_red_ship24, R.drawable.maze2_red_ship25, R.drawable.maze2_red_ship26, R.drawable.maze2_red_ship27, R.drawable.maze2_red_ship28, R.drawable.maze2_red_ship29, R.drawable.maze2_red_ship30, R.drawable.maze2_red_ship31, R.drawable.maze2_red_ship32, R.drawable.maze2_red_ship33, R.drawable.maze2_red_ship34, R.drawable.maze2_red_ship35, R.drawable.maze2_red_ship36};
        //int[] animationImages = {R.drawable.maze2_red_ship1,R.drawable.maze2_red_ship3,R.drawable.maze2_red_ship5,R.drawable.maze2_red_ship7,R.drawable.maze2_red_ship9,R.drawable.maze2_red_ship11,R.drawable.maze2_red_ship13,R.drawable.maze2_red_ship15,R.drawable.maze2_red_ship17, R.drawable.maze2_red_ship19, R.drawable.maze2_red_ship21, R.drawable.maze2_red_ship23, R.drawable.maze2_red_ship25, R.drawable.maze2_red_ship27, R.drawable.maze2_red_ship29, R.drawable.maze2_red_ship31,R.drawable.maze2_red_ship33,  R.drawable.maze2_red_ship35};
        //mRedShipAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.ship_red_animation_frame_duration));
        //mRedShipAnimationDrawable.setOneShot(false);
        //((ImageView) getFragmentView().findViewById(R.id.mazeGame2_redShipImageView)).setImageDrawable(mRedShipAnimationDrawable);
        //mRedShipAnimationDrawable.start();

		//AnimationUtil.performFrameAnimation((ImageView) getFragmentView().findViewById(R.id.mazeGame2_redShipImageView), R.drawable.maze2_red_ship_animation);
	}
	
	private void setYellowShipView() {
		double screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		ImageView imageView = (ImageView) getFragmentView().findViewById(R.id.mazeGame2_yellowShipImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if (screenSize >= 9.4) {
			params.width = (int) UtilityMethods.convertDpToPixel(99, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(97, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(210, WeShineApp.getInstance());
		} else if (screenSize >= 6.9) {
			params.width = (int) UtilityMethods.convertDpToPixel(99, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(97, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(67, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(180, WeShineApp.getInstance());
		} else {
			params.width = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(116, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(5, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(100, WeShineApp.getInstance());
		}
	}

	private void setSeaAnimation() {
		double screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		ImageView imageView = (ImageView) getFragmentView().findViewById(R.id.mazeGame2_seaImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if(screenSize >= 9.4){
			params.height = (int) UtilityMethods.convertDpToPixel(150, WeShineApp.getInstance());
		}else if(screenSize >= 6.9){
			params.height = (int) UtilityMethods.convertDpToPixel(110, WeShineApp.getInstance());
		}else {
			params.height = (int) UtilityMethods.convertDpToPixel(62, WeShineApp.getInstance());
		}


        //int[] animationImages = {R.drawable.sea1,R.drawable.sea2,R.drawable.sea3,R.drawable.sea4,R.drawable.sea5,R.drawable.sea6,R.drawable.sea7,R.drawable.sea8,R.drawable.sea9,R.drawable.sea10};
        //mSeaAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.sea_animation_frame_duration));
        //mSeaAnimationDrawable.setOneShot(false);
        //((ImageView) getFragmentView().findViewById(R.id.mazeGame2_seaImageView)).setImageDrawable(mSeaAnimationDrawable);
        //mSeaAnimationDrawable.start();
		AnimationUtil.performFrameAnimation((ImageView) getFragmentView().findViewById(R.id.mazeGame2_seaImageView), R.drawable.sea_animation);


    }

	@Override
	public void onGameEnd(boolean isSuccessful) {
		if (isSuccessful) {
			AnimationUtil.performAnimation(getFragmentView().findViewById(R.id.mazeGame2_fabulusImageView), AnimType.ZOOM_IN, this);
		} else {
			// Reset the view and let the user try to draw right path again.
			resetDrawingSurface();
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		//startAudioSound(ImageAndMediaResources.sSoundIdFabulous);
		mGameMusicEnd = new GameMusic(getActivity(), ImageAndMediaResources.sSoundIdFabulous);
		mGameMusicEnd.start();
    }

    @Override
	public void onAnimationEnd(Animation animation) {
		///((MazeActivity) getActivity()).popTopFragment();
		Bundle bundle = getArguments();
		if (bundle == null) {
			bundle = new Bundle();
		}
		//bundle.putInt(AppConstant.VIDEO_FILE_NAME, R.raw.maze2_end_video);
		bundle.putString(AppConstant.VIDEO_FILE_NAME, "maze2_end_video");
		bundle.putInt(AppConstant.BUNDLE_EXTRA_VIDEO_DURATION, AppConstant.MAZE_TWO_VIDEO_DURATION);
		((MazeMenuActivity) getActivity()).attachGameEndVideoFragment(bundle);
		AnimationUtil.performAnimation(getFragmentView().findViewById(R.id.mazeGame2_fabulusImageView), AnimType.ZOOM_OUT, null);
		resetDrawingSurface();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	/**
	 * Resets the drawing surface. Every the paths drawn on the surface will be
	 * erased.
	 */
	private void resetDrawingSurface() {
		mDrawingSurface.reset();
	}

	@Override
	protected void onAudioComplete(String audioFileId) {
		switch (audioFileId) {
		case "maze1_ondraw":
			startAudioSound(ImageAndMediaResources.sSoundIdMaze2);
			break;

		default:
			break;
		}

	}



	private AnimationDrawable getAnimationDrawable(int[] drawables, int duration){

		AnimationDrawable newAnim = new AnimationDrawable();
		for (int i = 0; i < drawables.length ; i++) {
			Bitmap bitmap =  BitmapFactory.decodeResource(getResources(), drawables[i]);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
			newAnim.addFrame(bitmapDrawable, duration);
		}
		return newAnim;
	}

	private void releaseResources(){
		//recycle(mBirdsAnimationDrawable);
		mBirdsAnimationDrawable = null;

        //recycle(mSunAnimationDrawable);
		//mSunAnimationDrawable = null;

        //recycle(mGreenShipAnimationDrawable);
        //mGreenShipAnimationDrawable = null;

        //recycle(mRedShipAnimationDrawable);
        //mRedShipAnimationDrawable = null;

        //recycle(mSeaAnimationDrawable);
        //mSeaAnimationDrawable = null;

        mTopBitmap.recycle();
        mTopBitmap = null;

        mMiddleBitmap.recycle();
        mMiddleBitmap = null;

        mBottomBitmap.recycle();
        mBottomBitmap = null;

		mBitmapFabulous.recycle();
		mBitmapFabulous = null;

		if(mGameMusic1 != null){
			mGameMusic1.release();
		}

		if(mGameMusic2 != null){
			mGameMusic2.release();
		}

		if(mGameMusicEnd != null){
			mGameMusicEnd.release();
			mGameMusicEnd = null;
		}
	}


	private void recycle(AnimationDrawable animationDrawable) {
		int noOfFrames = animationDrawable.getNumberOfFrames();
		for (int i = 0; i < noOfFrames; ++i){
			Drawable frame = animationDrawable.getFrame(i);
			if (frame instanceof BitmapDrawable) {
				((BitmapDrawable)frame).getBitmap().recycle();
			}
			frame.setCallback(null);
		}
		animationDrawable.setCallback(null);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		System.gc();
		releaseResources();
	}

}
