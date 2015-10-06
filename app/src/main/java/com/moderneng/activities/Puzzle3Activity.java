package com.moderneng.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.moderneng.R;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;
import com.moderneng.views.ImageDragShadowBuilder;


public class Puzzle3Activity extends Activity {
    private ImageView p3imgv1, p3imgv2, p3imgv3, p3imgv4, p3imgv5, p3imgv6, p3imgv7, p3dragv;
    private RelativeLayout mRelativeLayoutParent;
    private int count = 1;
    private GameMusic mp3;
    private AudioPlayer mp;
    private int x, y;
    private Bitmap mBitmapBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        super.onCreate(savedInstanceState);
        if (MainMenuActivity.height <= 480) {
            setContentView(R.layout.puzzle1small);
        } else {
            setContentView(R.layout.puzzle2);
        }
        mRelativeLayoutParent = (RelativeLayout) findViewById(R.id.relative_layout_parent);
        mp = new AudioPlayer(getApplicationContext(), "puzzle3");
        if(mp != null){
            mp.start();
        }

        p3imgv1 = (ImageView) findViewById(R.id.ltop);
        p3imgv2 = (ImageView) findViewById(R.id.lmiddle);
        p3imgv3 = (ImageView) findViewById(R.id.lbottom);
        p3imgv4 = (ImageView) findViewById(R.id.mtop);
        p3imgv5 = (ImageView) findViewById(R.id.mmiddle);
        p3imgv6 = (ImageView) findViewById(R.id.mbottom);
        p3imgv7 = (ImageView) findViewById(R.id.rmost);
        p3imgv4.bringToFront();
        p3imgv1.setOnDragListener(new Mydraglistner());
        p3imgv2.setOnDragListener(new Mydraglistner());
        p3imgv3.setOnDragListener(new Mydraglistner());
        p3imgv4.setOnDragListener(new Mydraglistner());
        p3imgv5.setOnDragListener(new Mydraglistner());
        p3imgv6.setOnDragListener(new Mydraglistner());
        p3imgv7.setOnDragListener(new Mydraglistner());

        p3dragv = (ImageView) findViewById(R.id.pdragv);

        p3dragv.setImageResource(R.drawable.p2img7);

        p3dragv.setOnTouchListener(new Mytouchlistener());
    }

    private class Mytouchlistener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mp.stop();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                DragShadowBuilder p3dragb = null;
                ClipData data = ClipData.newPlainText("", "");
                ImageView img = (ImageView) v;
                //mp3 = new GameMusic(getApplicationContext(), R.raw.drag);
                //mp3.start();

                mp3 = new GameMusic(getApplicationContext(), "drag");
                if(mp3 != null){
                    mp3.start();
                }

                if (count == 1) {
                    p3dragb = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p2img7);
                    v.startDrag(data, p3dragb, img, 0);

                } else if (count == 2) {
                    p3dragb = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p2img3);
                    v.startDrag(data, p3dragb, img, 0);

                } else if (count == 3) {
                    p3dragb = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p2img1);
                    v.startDrag(data, p3dragb, img, 0);

                } else if (count == 4) {
                    p3dragb = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p2img6);
                    v.startDrag(data, p3dragb, img, 0);

                } else if (count == 5) {
                    p3dragb = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p2img2);
                    v.startDrag(data, p3dragb, img, 0);

                } else if (count == 6) {
                    p3dragb = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p2img4);
                    v.startDrag(data, p3dragb, img, 0);

                } else if (count == 7) {
                    p3dragb = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p2img5);
                    v.startDrag(data, p3dragb, img, 0);

                }
            }
            return false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mBitmapBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdPuzzle3Bg);
        findViewById(R.id.relative_layout_parent).setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapBg));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBitmapBg != null){
            mBitmapBg.recycle();
            mBitmapBg = null;
        }
    }

    private class Mydraglistner implements OnDragListener {

        @Override
        public boolean onDrag(View p3v, DragEvent dragvent) {
            switch (dragvent.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                    p3dragv.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    if (count == 1 && p3v.getId() == R.id.rmost) {
                        p3imgv7.setImageResource(R.drawable.p2img7);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), R.raw.p3drop);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "p3drop");
                        if(mp3 != null){
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p3imgv7.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p3imgv7.getWidth() / 2);
                        y = imageCordinates[1] + (p3imgv7.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle3Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 2 && p3v.getId() == R.id.lbottom) {
                        p3imgv3.setImageResource(R.drawable.p2img3);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdPerfect);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdPerfect);
                        if(mp3 != null){
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p3imgv3.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p3imgv3.getWidth() / 2);
                        y = imageCordinates[1] + (p3imgv3.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle3Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 3 && p3v.getId() == R.id.ltop) {
                        p3imgv1.setImageResource(R.drawable.p2img1);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), R.raw.p3drop);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "p3drop");
                        if(mp3 != null){
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p3imgv1.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p3imgv1.getWidth() / 2);
                        y = imageCordinates[1] + (p3imgv1.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle3Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 4 && p3v.getId() == R.id.mbottom) {
                        p3imgv6.setImageResource(R.drawable.p2img6);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), R.raw.p3drop);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "p3drop");
                        if(mp3 != null){
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p3imgv6.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p3imgv6.getWidth() / 2);
                        y = imageCordinates[1] + (p3imgv6.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle3Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 5 && p3v.getId() == R.id.lmiddle) {
                        p3imgv2.setImageResource(R.drawable.p2img2);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), R.raw.p3drop);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "p3drop");
                        if(mp3 != null){
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p3imgv2.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p3imgv2.getWidth() / 2);
                        y = imageCordinates[1] + (p3imgv2.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle3Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 6 && p3v.getId() == R.id.mtop) {
                        p3imgv4.setImageResource(R.drawable.p2img4);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), R.raw.p3drop);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "p3drop");
                        if(mp3 != null){
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p3imgv4.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p3imgv4.getWidth() / 2);
                        y = imageCordinates[1] + (p3imgv4.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle3Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 7 && p3v.getId() == R.id.mmiddle) {
                        p3imgv5.setImageResource(R.drawable.p2img5);
                        count++;
                        int[] imageCordinates = new int[2];
                        p3imgv5.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p3imgv5.getWidth() / 2);
                        y = imageCordinates[1] + (p3imgv5.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle3Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else {
                        count = count;
                       // mp3 = new GameMusic(getApplicationContext(), R.raw.wrongs);
                       // mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "wrongs");
                        if(mp3 != null){
                            mp3.start();
                        }

                        return false;
                    }
                case DragEvent.ACTION_DRAG_ENDED:
                    if (count == 1) {
                        p3dragv.setImageResource(R.drawable.p2img7);
                        p3dragv.setVisibility(View.VISIBLE);
                    } else if (count == 2) {
                        p3dragv.setImageResource(R.drawable.p2img3);
                        p3dragv.setVisibility(View.VISIBLE);
                    } else if (count == 3) {
                        p3dragv.setImageResource(R.drawable.p2img1);
                        p3dragv.setVisibility(View.VISIBLE);
                    } else if (count == 4) {
                        p3dragv.setImageResource(R.drawable.p2img6);
                        p3dragv.setVisibility(View.VISIBLE);
                    } else if (count == 5) {
                        p3dragv.setImageResource(R.drawable.p2img2);
                        p3dragv.setVisibility(View.VISIBLE);
                    } else if (count == 6) {
                        p3dragv.setImageResource(R.drawable.p2img4);
                        p3dragv.setVisibility(View.VISIBLE);
                    } else if (count == 7) {
                        p3dragv.setImageResource(R.drawable.p2img5);
                        p3dragv.setVisibility(View.VISIBLE);
                    } else if (count == 8) {

                        count++;

                        Intent intent = new Intent(Puzzle3Activity.this, BalloonAnimationActivity.class);
                        intent.putExtra(AppConstant.EXTRA_GREETING_IMAGE_RESOURCE_ID, ImageAndMediaResources.sImageIdGreat);
                        intent.putExtra(AppConstant.EXTRA_GREETING_SOUND_ID, ImageAndMediaResources.sSoundIdGreat);
                        //intent.putExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_ID, R.raw.hey);
                        intent.putExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_DELAY, AppConstant.BALLOON_ANIMATION_SOUND_DELAY);
                        startActivityForResult(intent, 100);

                    }
                    break;
                default:

                    break;
            }
            return true;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(this, Puzzle4Activity.class);
        startActivity(intent);
        finish();

    }



    private class smallanim extends View {
        Context ctx1;
        int smcount = 0;
        Bitmap smstar1, smstar2, smstar3, smstar4, smstar5, smstar6, smstar7, smstar8, smstar9;

        public smallanim(Context context) {
            super(context);
            ctx1 = context;
            initBitmaps();
        }


        private void initBitmaps() {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            smstar1 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star1, opts);
            smstar2 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star2, opts);
            smstar3 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star3, opts);
            smstar4 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star4, opts);
            smstar5 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star5, opts);
            smstar6 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star6, opts);
            smstar7 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star7, opts);
            smstar8 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star8, opts);
            smstar9 = BitmapFactory.decodeResource(getResources(), R.drawable.p1_star9, opts);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (smcount <= 15) {
                canvas.drawBitmap(smstar1, x - smstar1.getWidth() / 2, y - smstar1.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 2 && smcount < 4) {
                canvas.drawBitmap(smstar2, x - smstar2.getWidth() / 2, y - smstar2.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 4 && smcount < 6) {
                canvas.drawBitmap(smstar3, x - smstar3.getWidth() / 2, y - smstar3.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 6 && smcount < 8) {
                canvas.drawBitmap(smstar4, x - smstar4.getWidth() / 2, y - smstar4.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 8 && smcount < 10) {
                canvas.drawBitmap(smstar5, x - smstar5.getWidth() / 2, y - smstar5.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 10 && smcount < 12) {
                canvas.drawBitmap(smstar6, x - smstar6.getWidth() / 2, y - smstar6.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 12 && smcount < 14) {
                canvas.drawBitmap(smstar7, x - smstar7.getWidth() / 2, y - smstar7.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 14 && smcount < 16) {
                canvas.drawBitmap(smstar8, x - smstar8.getWidth() / 2, y - smstar8.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
            if (smcount >= 16 && smcount < 18) {
                canvas.drawBitmap(smstar9, x - smstar9.getWidth() / 2, y - smstar9.getHeight() / 2, null);
                smcount++;
                invalidate();
            }
        }
    }
}
