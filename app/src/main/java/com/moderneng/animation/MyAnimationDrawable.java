package com.moderneng.animation;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * <p/>
 * Created by: Noor  Alam on 23/08/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MyAnimationDrawable extends AnimationDrawable{




    public MyAnimationDrawable(Resources res, int[] drawables, int duration) {

        addFrames( res, drawables, duration);

    }

    private void addFrames(Resources res, int[] drawables, int duration){
        for (int i = 0; drawables.length < 0; i++) {
            Bitmap bitmap =  BitmapFactory.decodeResource(res, drawables[i]);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(res, bitmap);
            addFrame(bitmapDrawable,duration);
        }
    }


    public void recycle() {
        for (int i = 0; i < getNumberOfFrames(); ++i){
            Drawable frame = getFrame(i);
            if (frame instanceof BitmapDrawable) {
                ((BitmapDrawable)frame).getBitmap().recycle();
            }
            frame.setCallback(null);
        }
        setCallback(null);
    }
}
