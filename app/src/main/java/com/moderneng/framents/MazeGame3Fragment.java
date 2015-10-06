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
import com.moderneng.utils.UtilityMethods;
import com.moderneng.views.DrawingSurface;
import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.activities.MazeMenuActivity;

public class MazeGame3Fragment extends BaseFragment implements OnGameEndListener, AnimationListener {

	// Drawing surface to draw the path on
	private DrawingSurface mDrawingSurface;
	private AnimationDrawable mSunAnimationDrawable;
	private  AnimationDrawable mCarAnimationDrawable;

	private Bitmap mTopBitmap;
	private Bitmap mMiddleBitmap;
	private Bitmap mBottomBitmap;
	private Bitmap mBitmapWellDone;

	private GameMusic mGameMusic1;
	private GameMusic mGameMusic2;
	private GameMusic mGameMusicEnd;



	@Override
	protected int getFragmentLayoutId() {
		return R.layout.maze_game3;
	}

	@Override
	public void onResume() {
		super.onResume();

		System.gc();
		mDrawingSurface = (DrawingSurface) getFragmentView().findViewById(R.id.mazeGame3_middleImageView);
		mDrawingSurface.setOnGameEndListener(this);
		mDrawingSurface.setHotSpotImageView((ImageView) getFragmentView().findViewById(R.id.mazeGame3_bottomImageView));

		setBackgroundImages();
		setAnimatedSunView();
		setAnimatinCarView();
		//startAudioSound(R.raw.maze1_ondraw);
		mGameMusic1 = new GameMusic(getActivity(), "maze1_ondraw");
		mGameMusic1.start();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mGameMusic2 = new GameMusic(getActivity(), ImageAndMediaResources.sSoundIdMaze3);
				mGameMusic2.start();
			}
		}, 500);
	}


	private void setBackgroundImages() {

		//mTopBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.maze3_top_img);
		mTopBitmap =  WeShineApp.getBitmapFromObb("maze3_top_img.png");
		((ImageView)getFragmentView().findViewById(R.id.mazeGame3_topImageView)).setImageBitmap(mTopBitmap);

		//mMiddleBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.maze3_middle_img);
		mMiddleBitmap =  WeShineApp.getBitmapFromObb("maze3_middle_img.png");
		mDrawingSurface.setImageBitmap(mMiddleBitmap);

		//mBottomBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.maze3_bottom_img);
		mBottomBitmap =  WeShineApp.getBitmapFromObb("maze3_bottom_img.png");
		((ImageView)getFragmentView().findViewById(R.id.mazeGame3_bottomImageView)).setImageBitmap(mBottomBitmap);


		mBitmapWellDone = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdWellDone);
		((ImageView)getFragmentView().findViewById(R.id.mazeGame3_wellDoneImageView)).setImageBitmap(mBitmapWellDone);


	}

	private void setAnimatedSunView() {
		
		double screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		ImageView imageView = (ImageView) getFragmentView().findViewById(R.id.mazeGame3_sunImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if(screenSize >= 10){
			params.width = (int) UtilityMethods.convertDpToPixel(198, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(198, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
		}else if(screenSize >= 6.9){
			params.width = (int) UtilityMethods.convertDpToPixel(150, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(155, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(31, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(70, WeShineApp.getInstance());
		}else {
			params.width = (int) UtilityMethods.convertDpToPixel(82, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(82, WeShineApp.getInstance());
			params.topMargin = (int) UtilityMethods.convertDpToPixel(20, WeShineApp.getInstance());
			params.leftMargin = (int) UtilityMethods.convertDpToPixel(40, WeShineApp.getInstance());
		}


		//int[] animationImages = {R.drawable.maze3sun0, R.drawable.maze3sun1,R.drawable.maze3sun2,R.drawable.maze3sun3,R.drawable.maze3sun4,R.drawable.maze3sun5,R.drawable.maze3sun6,R.drawable.maze3sun7,R.drawable.maze3sun8,R.drawable.maze3sun9,R.drawable.maze3sun10,R.drawable.maze3sun11,R.drawable.maze3sun12,R.drawable.maze3sun13,R.drawable.maze3sun14,R.drawable.maze3sun15,R.drawable.maze3sun16,R.drawable.maze3sun17,R.drawable.maze3sun18,R.drawable.maze3sun19,R.drawable.maze3sun20};
		//mSunAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.maze1sun_animation_frame_duration));
		//mSunAnimationDrawable.setOneShot(false);
		//((ImageView) getFragmentView().findViewById(R.id.mazeGame3_sunImageView)).setImageDrawable(mSunAnimationDrawable);
		//mSunAnimationDrawable.start();

		//AnimationUtil.performFrameAnimation((ImageView) getFragmentView().findViewById(R.id.mazeGame3_sunImageView), R.drawable.maze3_sun_animation);
	}

	private void setAnimatinCarView(){
		
		double screenSize = UtilityMethods.getScreenSizeInInches(WeShineApp.getInstance());
		ImageView imageView = (ImageView) getFragmentView().findViewById(R.id.mazeGame3_carImageView);
		LayoutParams params = (LayoutParams) imageView.getLayoutParams();
		if(screenSize >= 10){
			params.width = (int) UtilityMethods.convertDpToPixel(405, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(314, WeShineApp.getInstance());
		}else if(screenSize >= 6.9){
			params.width = (int) UtilityMethods.convertDpToPixel(325, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(252, WeShineApp.getInstance());
			params.rightMargin = (int) UtilityMethods.convertDpToPixel(50, WeShineApp.getInstance());
			params.bottomMargin = (int) UtilityMethods.convertDpToPixel(-10, WeShineApp.getInstance());
		}else {
			params.width = (int) UtilityMethods.convertDpToPixel(155, WeShineApp.getInstance());
			params.height = (int) UtilityMethods.convertDpToPixel(120, WeShineApp.getInstance());
		}

		//int[] animationImages = {R.drawable.maze3gfcar1, R.drawable.maze3gfcar1,R.drawable.maze3gfcar2,R.drawable.maze3gfcar3,R.drawable.maze3gfcar4,R.drawable.maze3gfcar5,R.drawable.maze3gfcar6,R.drawable.maze3gfcar7,R.drawable.maze3gfcar8,R.drawable.maze3gfcar9,R.drawable.maze3gfcar10,R.drawable.maze3gfcar11,R.drawable.maze3gfcar12,R.drawable.maze3gfcar13,R.drawable.maze3gfcar14,R.drawable.maze3gfcar15,R.drawable.maze3gfcar16,R.drawable.maze3gfcar17,R.drawable.maze3gfcar18,R.drawable.maze3gfcar19,R.drawable.maze3gfcar20,R.drawable.maze3gfcar21,R.drawable.maze3gfcar22,R.drawable.maze3gfcar23,R.drawable.maze3gfcar24,R.drawable.maze3gfcar25,R.drawable.maze3gfcar26};
		//int[] animationImages = {R.drawable.maze3gfcar1,R.drawable.maze3gfcar3,R.drawable.maze3gfcar5,R.drawable.maze3gfcar7,R.drawable.maze3gfcar9,R.drawable.maze3gfcar11,R.drawable.maze3gfcar13,R.drawable.maze3gfcar15,R.drawable.maze3gfcar17,R.drawable.maze3gfcar19,R.drawable.maze3gfcar21,R.drawable.maze3gfcar23,R.drawable.maze3gfcar25};
		//mCarAnimationDrawable = getAnimationDrawable(animationImages, getResources().getInteger(R.integer.maze1sun_animation_frame_duration));
		//mCarAnimationDrawable.setOneShot(false);
		//((ImageView) getFragmentView().findViewById(R.id.mazeGame3_carImageView)).setImageDrawable(mCarAnimationDrawable);
		//mCarAnimationDrawable.start();
		//AnimationUtil.performFrameAnimation((ImageView) getFragmentView().findViewById(R.id.mazeGame3_carImageView), R.drawable.maze3_car_animation);
	}
	@Override
	public void onGameEnd(boolean isSuccessful) {
		if (isSuccessful) {
			AnimationUtil.performAnimation(getFragmentView().findViewById(R.id.mazeGame3_wellDoneImageView), AnimType.ZOOM_IN, this);
		} else {
			// Reset the view and let the user try to draw right path again.
			resetDrawingSurface();
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		//startAudioSound(ImageAndMediaResources.sSoundIdWellDone);
		mGameMusicEnd = new GameMusic(getActivity(), ImageAndMediaResources.sSoundIdWellDone);
		mGameMusicEnd.start();
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		//((MazeActivity) getActivity()).popTopFragment();
		Bundle bundle = getArguments();
		if (bundle == null) {
			bundle = new Bundle();
		}
		//bundle.putInt(AppConstant.VIDEO_FILE_NAME, R.raw.maze3_end_video);
		bundle.putString(AppConstant.VIDEO_FILE_NAME, "maze3_end_video");
		bundle.putInt(AppConstant.BUNDLE_EXTRA_VIDEO_DURATION, AppConstant.MAZE_THREE_VIDEO_DURATION);
		((MazeMenuActivity) getActivity()).attachGameEndVideoFragment(bundle);
		AnimationUtil.performAnimation(getFragmentView().findViewById(R.id.mazeGame3_wellDoneImageView), AnimType.ZOOM_OUT, null);
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
			startAudioSound(ImageAndMediaResources.sSoundIdMaze3);
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
		//recycle(mCarAnimationDrawable);
		mCarAnimationDrawable = null;

		//recycle(mSunAnimationDrawable);
		mSunAnimationDrawable = null;




		mTopBitmap.recycle();
		mTopBitmap = null;

		mMiddleBitmap.recycle();
		mMiddleBitmap = null;

		mBottomBitmap.recycle();
		mBottomBitmap = null;

		mBitmapWellDone.recycle();
		mBitmapWellDone = null;

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
