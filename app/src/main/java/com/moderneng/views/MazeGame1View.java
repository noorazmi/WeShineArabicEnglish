package com.moderneng.views;

import android.content.Context;
import android.util.AttributeSet;

public class MazeGame1View extends DrawingSurface {

	public MazeGame1View(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onTouchEndEvent(boolean isSuccess) {
		if (mGameEndListener != null) {
			mGameEndListener.onGameEnd(isSuccess);
		}
	}

}
