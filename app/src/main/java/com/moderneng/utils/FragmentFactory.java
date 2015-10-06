package com.moderneng.utils;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Bundle;

import com.moderneng.framents.GameEndVideoFragment;
import com.moderneng.framents.MazeGame1Fragment;
import com.moderneng.framents.MazeGame2Fragment;
import com.moderneng.framents.MazeGame3Fragment;
import com.moderneng.framents.MazeGame4Fragment;
import com.moderneng.framents.MazeGame5Fragment;
import com.moderneng.activities.MazeMenuActivity;

public class FragmentFactory {

   public static void attachGameLevelOneFragment(Activity activity, Bundle bundle){
	   System.gc();
	   FragmentHelper.addFragmentOnBackStack(new WeakReference<MazeMenuActivity>((MazeMenuActivity) activity), new MazeGame1Fragment(), null, FragmentTag.FragmentMazeGame1.getTag());
   }
   
   public static void attachGameLevelTwoFragment(Activity activity, Bundle bundle){
	   System.gc();
	   FragmentHelper.replaceFragment(new WeakReference<MazeMenuActivity>((MazeMenuActivity) activity), new MazeGame2Fragment(), null, FragmentTag.FragmentMazeGame2.getTag());
   }
   
   public static void attachGameLevelThreeFragment(Activity activity, Bundle bundle){
	   System.gc();
	   FragmentHelper.replaceFragment(new WeakReference<MazeMenuActivity>((MazeMenuActivity) activity), new MazeGame3Fragment(), null, FragmentTag.FragmentMazeGame3.getTag());
   }
   
   public static void attachGameLevelFourFragment(Activity activity, Bundle bundle){
	   System.gc();
	   FragmentHelper.replaceFragment(new WeakReference<MazeMenuActivity>((MazeMenuActivity) activity), new MazeGame4Fragment(), null, FragmentTag.FragmentMazeGame4.getTag());
   }
   
   public static void attachGameLevelFiveFragment(Activity activity, Bundle bundle){
	   System.gc();
	   FragmentHelper.replaceFragment(new WeakReference<MazeMenuActivity>((MazeMenuActivity) activity), new MazeGame5Fragment(), null, FragmentTag.FragmentMazeGame5.getTag());
   }
	
	public static void attachGameEndVideoFragment(Activity activity, Bundle bundle) {
		System.gc();
		//FragmentHelper.replaceFragment(new WeakReference<MainActivity>((MainActivity) activity), new GameEndVideoFragment(), null, FragmentTag.FragmentGameEndVideo.getTag());
		FragmentHelper.replaceFragment(new WeakReference<MazeMenuActivity>((MazeMenuActivity) activity), new GameEndVideoFragment(), bundle, FragmentTag.FragmentGameEndVideo.getTag());
	}
	
	public static void popTopFragment(Activity activity){
		System.gc();
		FragmentHelper.popTopFragment(new WeakReference<MazeMenuActivity>((MazeMenuActivity) activity));
	}

}
