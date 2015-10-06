package com.moderneng.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.moderneng.WeShineApp;
import com.moderneng.listeners.OnMazeMenuItemClickListener;

public class ImageViewPagerAdapter extends PagerAdapter implements OnClickListener {
	private Context context;
	private OnMazeMenuItemClickListener mMazeMenuItemClickListener;
	//private int[] menuImages = new int[] { R.drawable.maze1_menu_img, R.drawable.maze2_menu_img, R.drawable.maze3_menu_img, R.drawable.maze4_menu_img, R.drawable.maze5_menu_img };
	private String[] menuImages = { "maze1_menu_img.png" , "maze2_menu_img.png", "maze3_menu_img.png", "maze4_menu_img.png", "maze5_menu_img.png" };

	public ImageViewPagerAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return menuImages.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		ImageView imageView = new ImageView(context);
		//imageView.setImageResource(menuImages[position]);
		imageView.setImageBitmap(WeShineApp.getBitmapFromObb(menuImages[position]));
		LayoutParams imageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(imageParams);

		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(layoutParams);
		imageView.setTag(String.valueOf(position));
		layout.addView(imageView);
		imageView.setOnClickListener(this);
		container.addView(layout);
		return layout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((LinearLayout) object);
	}

	public void setOnMazeMenuItemClickListener(OnMazeMenuItemClickListener mazeMenuItemClickListener) {
		mMazeMenuItemClickListener = mazeMenuItemClickListener;
	}

	@Override
	public void onClick(View v) {
		if (mMazeMenuItemClickListener != null) {
			mMazeMenuItemClickListener.onGameMenuItemClick(Integer.parseInt((String) v.getTag()));
		}
	}
}