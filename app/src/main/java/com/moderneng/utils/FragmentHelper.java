package com.moderneng.utils;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.moderneng.utils.Logger;
import com.moderneng.R;

public class FragmentHelper {
	public static void replaceFragment(WeakReference<?> activity, Fragment fragment, Bundle datas, String fragTag) {
		if (activity.get() != null && !((Activity) activity.get()).isFinishing()) {
			FragmentTransaction transaction = ((Activity) activity.get()).getFragmentManager().beginTransaction();
			if (datas != null) {
				fragment.setArguments(datas);
			}
			//We don't need any animation for video view after game end
			//transaction.setCustomAnimations(R.animator.fragment_slide_right_enter, R.animator.fragment_slide_left_exit, 0, 0);
			Logger.debug("test", "goto : Total Fragment count : " + ((Activity) activity.get()).getFragmentManager().getBackStackEntryCount());
			transaction.replace(((Activity) activity.get()).findViewById(R.id.container).getId(), fragment, fragTag);
			transaction.show(fragment);
			transaction.commitAllowingStateLoss();
		}
	}
	
	public static void addFragmentOnBackStack(WeakReference<?> activity, Fragment fragment, Bundle datas, String fragTag) {
		if (activity.get() != null && !((Activity) activity.get()).isFinishing()) {
			FragmentTransaction transaction = ((Activity) activity.get()).getFragmentManager().beginTransaction();
			if (datas != null) {
				fragment.setArguments(datas);
			}
			//We don't need any animation for video view after game end
			//transaction.setCustomAnimations(R.animator.fragment_slide_right_enter, R.animator.fragment_slide_left_exit, 0, 0);
			Logger.debug("test", "goto : Total Fragment count : " + ((Activity) activity.get()).getFragmentManager().getBackStackEntryCount());
			transaction.add(((Activity) activity.get()).findViewById(R.id.container).getId(), fragment, fragTag);
			transaction.addToBackStack(fragTag);
			transaction.show(fragment);
			transaction.commitAllowingStateLoss();
		}
	}
	
	public static void popTopFragment(WeakReference<?> activity){
		((Activity) activity.get()).getFragmentManager().popBackStack();
	}
	
	
	
}
