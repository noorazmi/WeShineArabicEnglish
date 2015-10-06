package com.moderneng.utils;

import com.moderneng.framents.GameEndVideoFragment;
import com.moderneng.framents.MazeGame1Fragment;
import com.moderneng.framents.MazeGame2Fragment;
import com.moderneng.framents.MazeGame3Fragment;
import com.moderneng.framents.MazeGame4Fragment;
import com.moderneng.framents.MazeGame5Fragment;
import com.moderneng.framents.MazeGameMenuFragment;

public enum FragmentTag {

	FragmentMazeGameMenu(MazeGameMenuFragment.class.getSimpleName()),
	FragmentGameEndVideo(GameEndVideoFragment.class.getSimpleName()),
	FragmentMazeGame1(MazeGame1Fragment.class.getSimpleName()),
	FragmentMazeGame2(MazeGame2Fragment.class.getSimpleName()),
	FragmentMazeGame3(MazeGame3Fragment.class.getSimpleName()),
	FragmentMazeGame4(MazeGame4Fragment.class.getSimpleName()),
	FragmentMazeGame5(MazeGame5Fragment.class.getSimpleName());
	
    private String fragmentTag;
	FragmentTag(String fragmentTag) {
		this.fragmentTag = fragmentTag;
	}
	
	/**
	 * Returns the tag defined for this fragment.
	 * @return String tag of this fragment.
	 */
	public String getTag(){
		return fragmentTag;
	}
	
	
}
