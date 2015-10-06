package com.moderneng.animation;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.moderneng.R;
import com.moderneng.WeShineApp;

public class AnimationUtil {

	/**
	 * Performs the requested animation
	 * 
	 * @param view
	 *            View on which the animation will be performed
	 * @param animType
	 *            AnimType The type of the animation which user want to perform
	 * @param animationListener
	 *            AnimationListener
	 */
	public static void performAnimation(View view, AnimType animType, AnimationListener animationListener) {
		if (animType == AnimType.ZOOM_IN) {
			playZoomInAnimation(view, animationListener);
		}else if(animType == AnimType.ZOOM_OUT){
			playZoomOutAnimation(view, animationListener);
		}

	}

	/**
	 * Performs the animation using image frames set as the background of the
	 * image view
	 * 
	 * @param imageView
	 *            ImageView
	 * @param imageFramesdrawable
	 *            image frames drawable id which will be put as background of
	 *            the imageview.
	 */
	public static void performFrameAnimation(ImageView imageView, int imageFramesdrawable) {
		imageView.setBackgroundResource(imageFramesdrawable);
		AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
		frameAnimation.start();
	}

	// Performs zoom in/scale up animation
	private static void playZoomInAnimation(View view, AnimationListener animationListener) {
		// load the animation
		Animation animation = AnimationUtils.loadAnimation(WeShineApp.getInstance(), R.anim.zoom_in);
		animation.setAnimationListener(animationListener);
		view.startAnimation(animation);
	}
	
	// Performs zoom out/scale down animation
	private static void playZoomOutAnimation(View view, AnimationListener animationListener){
		Animation animation = AnimationUtils.loadAnimation(WeShineApp.getInstance(), R.anim.zoom_out);
		animation.setAnimationListener(animationListener);
		view.startAnimation(animation);
	}




}
