package com.moderneng.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.moderneng.listeners.OnGameEndListener;
import com.moderneng.pojo.FloatPoint;
import com.moderneng.utils.ColorTool;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.Logger;
import com.moderneng.utils.UtilityMethods;
import com.moderneng.WeShineApp;

public abstract class DrawingSurface extends ImageView implements OnTouchListener {

	private final String TAG = getClass().getName();
	private Canvas mCanvas;
	private Path mPath;
	private Paint mPaint;
	// GameEndListener instance to invoke the onGameEnd method
	protected OnGameEndListener mGameEndListener;

	// For a right path, isTouchedBlue = true, isTouchedRed = true and isTouchedGreen = true when the user removes the finger from the screen.
	private boolean isTouchedBlue = false;
	private boolean isTouchedGreen = false;
	private boolean isTouchedRed = false;
	private boolean isTouchedWhite = false;

	private final int TOLERANCE = 25;

	private ImageView bottomImageView;
	private Bitmap hotSpotBitmap;
	
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;

	public DrawingSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setOnTouchListener(this);
		init();
	}

	/**
	 * Initialize the view drawing objects
	 */
	private void init() {

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(UtilityMethods.convertDpToPixel(AppConstant.STROKE_WIDTH, WeShineApp.getInstance()));
		mCanvas = new Canvas();
		mPath = new Path();

		// Set path tracking boolean
		isTouchedBlue = false;
		isTouchedGreen = false;
		isTouchedRed = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(mPath, mPaint);
	}

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
		// commit the path to our off screen.
		mCanvas.drawPath(mPath, mPaint);
		onTouchEndEvent(checkRightPath());
		if (!isTouchedBlue || !isTouchedGreen || !isTouchedRed || !isTouchedWhite) {
			// Reset, so we don't double draw.
			reset();
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;

	}

	private void initHotSpotBitmap() {

		if (hotSpotBitmap != null) {
			return;
		}

		if (bottomImageView == null) {
			Logger.debug(TAG, "Please set hot spot imageView first");
			return;
		}
		bottomImageView.setDrawingCacheEnabled(true);
		hotSpotBitmap = Bitmap.createBitmap(bottomImageView.getDrawingCache());
		if (hotSpotBitmap == null) {
			Logger.debug(TAG, "Hot spot bitmap was not created.");
			return;
		} else {
			bottomImageView.setDrawingCacheEnabled(false);
		}

	}

	/**
	 * Check if the user drawn the path on the right way.
	 * @return
	 */
	private boolean checkRightPath() {
		initHotSpotBitmap();
		if (hotSpotBitmap == null) {
			Logger.debug(TAG, "******************Hot Spot image is null. Please set hot spot image using method setHotSpotImageView()");
			return false;
		}
		FloatPoint[] floatPoints = UtilityMethods.getPoints(mPath, AppConstant.POINTS_COUNT);

		for (FloatPoint floatPoint : floatPoints) {
			int hotSpotColorPixel = -1;
			try {
				hotSpotColorPixel = hotSpotBitmap.getPixel((int) floatPoint.getX(), (int) floatPoint.getY());
				if (isTouchedGreen && isTouchedBlue && isTouchedRed && isTouchedWhite) {
					Logger.debug(TAG, "********************ALL COLORS TOUCHED");
					return true;

				}
				if (!isTouchedGreen && ColorTool.closeMatch(Color.GREEN, hotSpotColorPixel, TOLERANCE)) {
					isTouchedGreen = true;
					Logger.debug(TAG, "********************touchColor:GREEN");
					continue;
				}
				if (!isTouchedBlue && ColorTool.closeMatch(Color.BLUE, hotSpotColorPixel, TOLERANCE)) {
					isTouchedBlue = true;
					Logger.debug(TAG, "********************touchColor:BLUE");
					continue;
				}
				if (!isTouchedRed && ColorTool.closeMatch(Color.RED, hotSpotColorPixel, TOLERANCE)) {
					isTouchedRed = true;
					Logger.debug(TAG, "********************touchColor:RED");
					continue;
				}
				if (!isTouchedWhite && ColorTool.closeMatch(Color.WHITE, hotSpotColorPixel, TOLERANCE)) {
					isTouchedWhite = true;
					Logger.debug(TAG, "********************touchColor:WHITE");
					continue;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		return false;

	}

	public void setOnGameEndListener(OnGameEndListener gameEndListener) {
		this.mGameEndListener = gameEndListener;
	}

	/**
	 * This hot spot image will be used to track the path user draws on the screen.
	 * @param hotSpotImageView
	 */
	public void setHotSpotImageView(ImageView hotSpotImageView) {
		this.bottomImageView = hotSpotImageView;
	}

	abstract protected void onTouchEndEvent(boolean isSuccess);

	/**
	 * Resets the view in fresh state.
	 */
	public void reset() {
		init();
		invalidate();
	}

	/**
	 * Three colors Green, Blue and Black will be on every maze level. White
	 * color will be present on Maze level 2, 3, 4 and 5. So in case of
	 * MazeGame1Fragment, we have to set it true because the right path will
	 * check all colors eg Red, green, black and white.
	 */
	public void setIsTouchedWhite(boolean status) {
		isTouchedWhite = status;
	}

}
