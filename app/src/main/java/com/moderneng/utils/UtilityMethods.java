package com.moderneng.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.moderneng.pojo.FloatPoint;

import java.io.File;
import java.util.Random;
import java.util.Vector;

public class UtilityMethods {

	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */

	public static float convertDpToPixel(float dp, Context context) {
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
		return px;
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into dp
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}

	/**
	 * Returns a Point which x and y values are in dp
	 * 
	 * @param x
	 *            x value in px
	 * @param y
	 *            y value in px
	 * @return Point which x and y Values are in dp
	 */
	public static Point getPointPXToDP(int x, int y, Context context) {
		return new Point((int) UtilityMethods.convertPixelsToDp(x, context), (int) UtilityMethods.convertPixelsToDp(y, context));
	}

	/**
	 * Validates if a Point falls under the boundary of passed ValidationRegion
	 * 
	 * @param validationRegion
	 * @param machingPoint
	 * @return true if the point falls under the provided ValidationRegion
	 */

	public static boolean isMachingRegion(Rect validationRegion, Point machingPoint) {
		return validationRegion.contains(machingPoint.x, machingPoint.y);
	}

	public static FloatPoint[] getPoints(Path path, int count) {
		FloatPoint[] pointArray = new FloatPoint[count];
		PathMeasure pm = new PathMeasure(path, false);
		float length = pm.getLength();
		float distance = 0f;
		float speed = length / count;
		int counter = 0;
		float[] aCoordinates = new float[2];

		while ((distance < length) && (counter < count)) {
			// get point from the path
			pm.getPosTan(distance, aCoordinates, null);
			pointArray[counter] = new FloatPoint(aCoordinates[0], aCoordinates[1]);
			counter++;
			distance = distance + speed;
		}

		return pointArray;
	}
	
	
	
	public static double getScreenSizeInInches(Context context)
	{
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = windowManager.getDefaultDisplay();
	    DisplayMetrics displayMetrics = new DisplayMetrics();
	    display.getMetrics(displayMetrics);


	    // since SDK_INT = 1;
	    int mWidthPixels = displayMetrics.widthPixels;
	    int mHeightPixels = displayMetrics.heightPixels;

	    // includes window decorations (statusbar bar/menu bar)
	    if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
	    {
	        try
	        {
	            mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
	            mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
	        }
	        catch (Exception ignored)
	        {
	        }
	    }

	    // includes window decorations (statusbar bar/menu bar)
	    if (Build.VERSION.SDK_INT >= 17)
	    {
	        try
	        {
	            Point realSize = new Point();
	            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
	            mWidthPixels = realSize.x;
	            mHeightPixels = realSize.y;
	        }
	        catch (Exception ignored)
	        {
	        }
	    }
	    
	    double x = Math.pow(mWidthPixels/displayMetrics.xdpi,2);
	    double y = Math.pow(mHeightPixels/displayMetrics.ydpi,2);
	    double screenInches = Math.sqrt(x+y);
	    Log.d("debug","Screen inches : " + screenInches);
	    
	    return  screenInches;
	}
	
	public static int getScreenWidth(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		//int width = size.x;
		//int height = size.y;
		return size.x;
	}
	
	public static int getScreenHeight(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		//int width = size.x;
		//int height = size.y;
		return size.y;
	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return int Random number in a rang of min and mix inclusive
	 */
	public static int getRandomInt(int min, int max){
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}


	private final static String EXP_PATH = "/Android/obb/";
	static String[] getAPKExpansionFiles(Context ctx, int mainVersion,  int patchVersion) {
		String packageName = ctx.getPackageName();
		Vector<String> ret = new Vector<String>();
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// Build the full path to the app's expansion files
			File root = Environment.getExternalStorageDirectory();
			File expPath = new File(root.toString() + EXP_PATH + packageName);

			// Check that expansion file path exists
			if (expPath.exists()) {
				if ( mainVersion > 0 ) {
					String strMainPath = expPath + File.separator + "main." + mainVersion + "." + packageName + ".obb";
					//String strMainPath = expPath + File.separator + "main." + mainVersion + "." + packageName+ ".zip";
					File main = new File(strMainPath);
					if ( main.isFile() ) {
						ret.add(strMainPath);
					}
				}
				if ( patchVersion > 0 ) {
					String strPatchPath = expPath + File.separator + "patch." + mainVersion + "." + packageName + ".obb";
					File main = new File(strPatchPath);
					if ( main.isFile() ) {
						ret.add(strPatchPath);
					}
				}
			}
		}
		String[] retArray = new String[ret.size()];
		ret.toArray(retArray);
		return retArray;
	}
	
}
