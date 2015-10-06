package com.moderneng.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.moderneng.R;
import com.moderneng.framents.GameEndVideoFragment;
import com.moderneng.framents.MazeGame1Fragment;
import com.moderneng.framents.MazeGame2Fragment;
import com.moderneng.framents.MazeGame3Fragment;
import com.moderneng.framents.MazeGame4Fragment;
import com.moderneng.framents.MazeGame5Fragment;
import com.moderneng.framents.MazeGameMenuFragment;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.FragmentTag;

public class MazeMenuActivity extends Activity {

	private FragmentManager mFragmentManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maze);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mFragmentManager = getFragmentManager();
		attachMazeGameMenuFragment();
	}
	public void AttachGameFragment(int gameLevel){
		System.gc();
		switch (gameLevel) {
		case AppConstant.GAME_LEVEL_0:
			attachMaze1Fragment();
			break;
		case AppConstant.GAME_LEVEL_1:
			attachMaze2Fragment();
			break;
		case AppConstant.GAME_LEVEL_2:
			attachMaze3Fragment();
			break;
		case AppConstant.GAME_LEVEL_3:
			attachMaze4Fragment();
			break;
		case AppConstant.GAME_LEVEL_4:
			attachMaze5Fragment();
			break;

		default:
			break;
		}
	}

    public void gotToMazeGameMenu(){
        attachMazeGameMenuFragment();
    }

	private void attachMazeGameMenuFragment(){
		MazeGameMenuFragment mazeGameMenuFragment = new MazeGameMenuFragment();
        replaceFragment(mazeGameMenuFragment, FragmentTag.FragmentMazeGameMenu.getTag());
	}

	private void attachMaze1Fragment(){
		MazeGame1Fragment mazeGame1Fragment = new MazeGame1Fragment();
        replaceFragment(mazeGame1Fragment, FragmentTag.FragmentMazeGame1.getTag());
	}

	private void attachMaze2Fragment(){
		MazeGame2Fragment mazeGame2Fragment = new MazeGame2Fragment();
        replaceFragment(mazeGame2Fragment, FragmentTag.FragmentMazeGame2.getTag());
	}

	private void attachMaze3Fragment(){
		MazeGame3Fragment mazeGame3Fragment = new MazeGame3Fragment();
        replaceFragment(mazeGame3Fragment, FragmentTag.FragmentMazeGame3.getTag());
	}

	private void attachMaze4Fragment(){
		MazeGame4Fragment mazeGame4Fragment = new MazeGame4Fragment();
        replaceFragment(mazeGame4Fragment, FragmentTag.FragmentMazeGame4.getTag());
	}

	private void attachMaze5Fragment(){
		MazeGame5Fragment mazeGame5Fragment = new MazeGame5Fragment();
        replaceFragment(mazeGame5Fragment, FragmentTag.FragmentMazeGame5.getTag());
	}

	private void addFragmentToBackStack(Fragment fragment, String tag){
		mFragmentManager.beginTransaction().add(R.id.container, fragment, tag).addToBackStack(null).commit();
	}

    private void replaceFragment(Fragment fragment, String tag){
        mFragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit();
    }

	public void attachGameEndVideoFragment(Bundle bundle) {
        GameEndVideoFragment gameEndVideoFragment = new GameEndVideoFragment();
        gameEndVideoFragment.setArguments(bundle);
        mFragmentManager.beginTransaction().replace(R.id.container, gameEndVideoFragment, FragmentTag.FragmentGameEndVideo.getTag()).commit();
    }

	public void removeMazeFiveFragment(){
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment1 = fragmentManager.findFragmentByTag(FragmentTag.FragmentMazeGame5.getTag());
		if(fragment1 != null){
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.remove(fragment1);
			ft.commit();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
			System.gc();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}

    @Override
    public void onBackPressed() {
        Fragment fragment = mFragmentManager.findFragmentByTag(FragmentTag.FragmentMazeGameMenu.getTag());
        if(fragment == null){
            gotToMazeGameMenu();
            return;
        }
        super.onBackPressed();
    }
}
