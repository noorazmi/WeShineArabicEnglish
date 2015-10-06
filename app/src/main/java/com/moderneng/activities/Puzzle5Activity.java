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


public class Puzzle5Activity extends Activity {
    private RelativeLayout mRelativeLayoutParent;
    private ImageView p5imgv1, p5imgv2;
    private ImageView p5imgv3, p5imgv4, p5imgv5, p5imgv6, p5imgv7;
    private ImageView p5dragv;
    private int count = 1;
    private AudioPlayer mp3;
    private GameMusic mp;
    private int x, y;
    private Bitmap mBitmapBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (MainMenuActivity.height <= 480) {
            setContentView(R.layout.puzzle1small);
        } else {
            setContentView(R.layout.puzzle2);
        }
        mRelativeLayoutParent = (RelativeLayout) findViewById(R.id.relative_layout_parent);
        p5dragv = (ImageView) findViewById(R.id.pdragv);
        p5dragv.setImageResource(R.drawable.p5m4);
        mp3 = new AudioPlayer(getApplicationContext(), "puzzle5");
        if(mp3 != null){
            mp3.start();
        }

        p5imgv1 = (ImageView) findViewById(R.id.ltop);
        p5imgv2 = (ImageView) findViewById(R.id.lmiddle);
        p5imgv3 = (ImageView) findViewById(R.id.lbottom);
        p5imgv4 = (ImageView) findViewById(R.id.mtop);
        p5imgv5 = (ImageView) findViewById(R.id.mmiddle);
        p5imgv6 = (ImageView) findViewById(R.id.mbottom);
        p5imgv7 = (ImageView) findViewById(R.id.rmost);
        p5imgv4.bringToFront();
        p5imgv1.setOnDragListener(new Mydraglistener());
        p5imgv2.setOnDragListener(new Mydraglistener());
        p5imgv3.setOnDragListener(new Mydraglistener());
        p5imgv4.setOnDragListener(new Mydraglistener());
        p5imgv5.setOnDragListener(new Mydraglistener());
        p5imgv6.setOnDragListener(new Mydraglistener());
        p5imgv7.setOnDragListener(new Mydraglistener());

        p5dragv.setOnTouchListener(new Mytouchlistener());

    }


    private class Mytouchlistener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mp3.stop();


            DragShadowBuilder p5img = null;
            ImageView img = (ImageView) v;
            ClipData data = ClipData.newPlainText("", "");
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mp = new GameMusic(getApplicationContext(), "drag");
                mp.start();
                if (count == 1) {
                    p5img = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p5m4);
                    v.startDrag(data, p5img, img, 0);

                } else if (count == 2) {
                    p5img = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p5m6);
                    v.startDrag(data, p5img, img, 0);

                } else if (count == 3) {
                    p5img = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p5m1);
                    v.startDrag(data, p5img, img, 0);

                } else if (count == 4) {
                    p5img = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p5m3);
                    v.startDrag(data, p5img, img, 0);

                } else if (count == 5) {
                    p5img = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p5m5);
                    v.startDrag(data, p5img, img, 0);

                } else if (count == 6) {
                    p5img = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p5m7);
                    v.startDrag(data, p5img, img, 0);

                } else if (count == 7) {
                    p5img = ImageDragShadowBuilder.fromResource(
                            getApplicationContext(), R.drawable.p5m2);
                    v.startDrag(data, p5img, img, 0);

                }
                return true;
            }
            return false;
        }

    }

    private class Mydraglistener implements OnDragListener {

        @Override
        public boolean onDrag(View dragv, DragEvent dragevent) {
            // TODO Auto-generated method stub
            switch (dragevent.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                    p5dragv.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    if (count == 1 && dragv.getId() == R.id.mtop) {
                        p5imgv4.setImageResource(R.drawable.p5m4);
                        mp = new GameMusic(getApplicationContext(), "psun");
                        if(mp != null){
                            mp.start();
                        }

                        count++;
                        int[] imageCordinates = new int[2];
                        p5imgv4.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p5imgv4.getWidth() / 2);
                        y = imageCordinates[1] + (p5imgv4.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle5Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 2 && dragv.getId() == R.id.mbottom) {
                        p5imgv6.setImageResource(R.drawable.p5m6);
                        mp = new GameMusic(getApplicationContext(), "psun");
                        if(mp != null){
                            mp.start();
                        }
                        count++;
                        int[] imageCordinates = new int[2];
                        p5imgv6.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p5imgv6.getWidth() / 2);
                        y = imageCordinates[1] + (p5imgv6.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle5Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 3 && dragv.getId() == R.id.ltop) {
                        p5imgv1.setImageResource(R.drawable.p5m1);
                        mp = new GameMusic(getApplicationContext(),  ImageAndMediaResources.sSoundIdAwsome);
                        if(mp != null){
                            mp.start();
                        }
                        count++;
                        int[] imageCordinates = new int[2];
                        p5imgv1.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p5imgv1.getWidth() / 2);
                        y = imageCordinates[1] + (p5imgv1.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle5Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 4 && dragv.getId() == R.id.lbottom) {
                        p5imgv3.setImageResource(R.drawable.p5m3);
                        mp = new GameMusic(getApplicationContext(), "psun");
                        if(mp != null){
                            mp.start();
                        }
                        count++;
                        int[] imageCordinates = new int[2];
                        p5imgv3.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p5imgv3.getWidth() / 2);
                        y = imageCordinates[1] + (p5imgv3.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle5Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;

                    } else if (count == 5 && dragv.getId() == R.id.mmiddle) {
                        p5imgv5.setImageResource(R.drawable.p5m5);
                        mp = new GameMusic(getApplicationContext(), "psun");
                        if(mp != null){
                            mp.start();
                        }
                        count++;
                        int[] imageCordinates = new int[2];
                        p5imgv5.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p5imgv5.getWidth() / 2);
                        y = imageCordinates[1] + (p5imgv5.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle5Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 6 && dragv.getId() == R.id.rmost) {
                        p5imgv7.setImageResource(R.drawable.p5m7);
                        mp = new GameMusic(getApplicationContext(), "psun");
                        if(mp != null){
                            mp.start();
                        }
                        count++;
                        int[] imageCordinates = new int[2];
                        p5imgv7.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p5imgv7.getWidth() / 2);
                        y = imageCordinates[1] + (p5imgv7.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle5Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;

                    } else if (count == 7 && dragv.getId() == R.id.lmiddle) {
                        p5imgv2.setImageResource(R.drawable.p5m2);
                    /*mp=new Gamemusic(getApplicationContext(), R.raw.yoursmart);
					mp.start();*/
                        count++;
                        int[] imageCordinates = new int[2];
                        p5imgv2.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p5imgv2.getWidth() / 2);
                        y = imageCordinates[1] + (p5imgv2.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle5Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else {
                        count = count;
                        mp = new GameMusic(getApplicationContext(), "wrongs");
                        if(mp != null){
                            mp.start();
                        }
                        return false;
                    }

                case DragEvent.ACTION_DRAG_ENDED:
                    if (count == 1) {
                        p5dragv.setImageResource(R.drawable.p5m4);
                        p5dragv.setVisibility(View.VISIBLE);
                    } else if (count == 2) {
                        p5dragv.setImageResource(R.drawable.p5m6);
                        p5dragv.setVisibility(View.VISIBLE);
                    } else if (count == 3) {
                        p5dragv.setImageResource(R.drawable.p5m1);
                        p5dragv.setVisibility(View.VISIBLE);
                    } else if (count == 4) {
                        p5dragv.setImageResource(R.drawable.p5m3);
                        p5dragv.setVisibility(View.VISIBLE);
                    } else if (count == 5) {
                        p5dragv.setImageResource(R.drawable.p5m5);
                        p5dragv.setVisibility(View.VISIBLE);

                    } else if (count == 6) {
                        p5dragv.setImageResource(R.drawable.p5m7);
                        p5dragv.setVisibility(View.VISIBLE);
                    } else if (count == 7) {
                        p5dragv.setImageResource(R.drawable.p5m2);
                        p5dragv.setVisibility(View.VISIBLE);
                    } else if (count == 8) {

                        count++;
                        Intent intent = new Intent(Puzzle5Activity.this, BalloonAnimationActivity.class);
                        intent.putExtra(AppConstant.EXTRA_GREETING_IMAGE_RESOURCE_ID, ImageAndMediaResources.sImageIdYouAreSmart);
                        intent.putExtra(AppConstant.EXTRA_GREETING_SOUND_ID,  ImageAndMediaResources.sSoundIdYouAreSmart);
                        //intent.putExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_ID, R.raw.hey);
                        intent.putExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_DELAY, AppConstant.BALLOON_ANIMATION_SOUND_DELAY);
                        startActivityForResult(intent, 100);
                    }
            }
            return true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();

    }



    @Override
    protected void onResume() {
        super.onResume();


        mBitmapBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdPuzzle5Bg);
        findViewById(R.id.relative_layout_parent).setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapBg));

        mp3.start();    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBitmapBg != null){
            mBitmapBg.recycle();
            mBitmapBg = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp3.stop();
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
