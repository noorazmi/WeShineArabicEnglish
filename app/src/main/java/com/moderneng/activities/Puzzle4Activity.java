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

import com.moderneng.utils.AppConstant;
import com.moderneng.R;
import com.moderneng.utils.AudioPlayer;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;
import com.moderneng.views.ImageDragShadowBuilder;

public class Puzzle4Activity extends Activity {
    private RelativeLayout mRelativeLayoutParent;
    private ImageView p4imgv1, p4imgv2, p4imgv3, p4imgv4, p4imgv5, p4imgv6, p4imgv7, p4dragv;
    private int count = 1;
    private AudioPlayer mp;
    private GameMusic mp3;
    private int x, y;
    private Bitmap mBitmapBg;
    Boolean play = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (MainMenuActivity.height <= 480) {
            setContentView(R.layout.puzzle1small);
        } else {
            setContentView(R.layout.puzzle2);
        }
        mRelativeLayoutParent = (RelativeLayout) findViewById(R.id.relative_layout_parent);
        p4dragv = (ImageView) findViewById(R.id.pdragv);
        p4dragv.setImageResource(R.drawable.p4m5);
        mp = new AudioPlayer(getApplicationContext(), "puzzle4");
        if (mp != null) {
            mp.start();
        }

        p4imgv1 = (ImageView) findViewById(R.id.ltop);
        p4imgv2 = (ImageView) findViewById(R.id.lmiddle);
        p4imgv3 = (ImageView) findViewById(R.id.lbottom);
        p4imgv4 = (ImageView) findViewById(R.id.mtop);
        p4imgv5 = (ImageView) findViewById(R.id.mmiddle);
        p4imgv6 = (ImageView) findViewById(R.id.mbottom);
        p4imgv7 = (ImageView) findViewById(R.id.rmost);
        p4imgv4.bringToFront();
        p4dragv.setOnTouchListener(new Mytouchlistener());
        p4imgv1.setOnDragListener(new Mydraglistener());
        p4imgv2.setOnDragListener(new Mydraglistener());
        p4imgv3.setOnDragListener(new Mydraglistener());
        p4imgv4.setOnDragListener(new Mydraglistener());
        p4imgv5.setOnDragListener(new Mydraglistener());
        p4imgv6.setOnDragListener(new Mydraglistener());
        p4imgv7.setOnDragListener(new Mydraglistener());

    }

    private class Mytouchlistener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mp.stop();
                mp3 = new GameMusic(getApplicationContext(), "drag");
                if (mp3 != null) {
                    mp3.start();
                }


                DragShadowBuilder p4img = null;
                ImageView img = (ImageView) v;
                ClipData data = ClipData.newPlainText("", "");
                if (count == 1) {
                    p4img = ImageDragShadowBuilder.fromResource(getApplicationContext(), R.drawable.p4m5);
                    v.startDrag(data, p4img, img, 0);

                } else if (count == 2) {
                    p4img = ImageDragShadowBuilder.fromResource(getApplicationContext(), R.drawable.p4m4);
                    v.startDrag(data, p4img, img, 0);

                } else if (count == 3) {
                    p4img = ImageDragShadowBuilder.fromResource(getApplicationContext(), R.drawable.p4m3);
                    v.startDrag(data, p4img, img, 0);

                } else if (count == 4) {
                    p4img = ImageDragShadowBuilder.fromResource(getApplicationContext(), R.drawable.p4m1);
                    v.startDrag(data, p4img, img, 0);

                } else if (count == 5) {
                    p4img = ImageDragShadowBuilder.fromResource(getApplicationContext(), R.drawable.p4m6);
                    v.startDrag(data, p4img, img, 0);

                } else if (count == 6) {
                    p4img = ImageDragShadowBuilder.fromResource(getApplicationContext(), R.drawable.p4m2);
                    v.startDrag(data, p4img, img, 0);

                } else if (count == 7) {
                    p4img = ImageDragShadowBuilder.fromResource(getApplicationContext(), R.drawable.p4m7);
                    v.startDrag(data, p4img, img, 0);

                }
                return true;
            }
            return false;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mBitmapBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdPuzzle4Bg);
        findViewById(R.id.relative_layout_parent).setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapBg));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBitmapBg != null) {
            mBitmapBg.recycle();
            mBitmapBg = null;
        }
    }

    private class Mydraglistener implements OnDragListener {

        @Override
        public boolean onDrag(View dragv, DragEvent dragevent) {
            switch (dragevent.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                    p4dragv.setVisibility(View.INVISIBLE);
                    //mp3 = new GameMusic(getApplicationContext(), R.raw.drag);
//                    mp3 = new GameMusic(getApplicationContext(), "drag");
//                    if (mp3 != null) {
//                        mp3.start();
//                    }

                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    if (count == 1 && dragv.getId() == R.id.mmiddle) {
                        p4imgv5.setImageResource(R.drawable.p4m5);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), R.raw.p2drop);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "p2drop");
                        if (mp3 != null) {
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p4imgv5.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p4imgv5.getWidth() / 2);
                        y = imageCordinates[1] + (p4imgv5.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle4Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 2 && dragv.getId() == R.id.mtop) {
                        p4imgv4.setImageResource(R.drawable.p4m4);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSuper);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSuper);
                        if (mp3 != null) {
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p4imgv4.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p4imgv4.getWidth() / 2);
                        y = imageCordinates[1] + (p4imgv4.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle4Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 3 && dragv.getId() == R.id.lbottom) {
                        p4imgv3.setImageResource(R.drawable.p4m3);
                        count++;
                        //mp3 = new GameMusic(getApplicationContext(), R.raw.p2drop);
                        //mp3.start();

                        mp3 = new GameMusic(getApplicationContext(), "p2drop");
                        if (mp3 != null) {
                            mp3.start();
                        }

                        int[] imageCordinates = new int[2];
                        p4imgv3.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p4imgv3.getWidth() / 2);
                        y = imageCordinates[1] + (p4imgv3.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle4Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 4 && dragv.getId() == R.id.ltop) {
                        p4imgv1.setImageResource(R.drawable.p4m1);
                        count++;
                        mp3 = new GameMusic(getApplicationContext(), "p2drop");
                        mp3.start();
                        int[] imageCordinates = new int[2];
                        p4imgv1.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p4imgv1.getWidth() / 2);
                        y = imageCordinates[1] + (p4imgv1.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle4Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 5 && dragv.getId() == R.id.mbottom) {
                        p4imgv6.setImageResource(R.drawable.p4m6);
                        count++;
                        mp3 = new GameMusic(getApplicationContext(), "p2drop");
                        mp3.start();
                        int[] imageCordinates = new int[2];
                        p4imgv6.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p4imgv6.getWidth() / 2);
                        y = imageCordinates[1] + (p4imgv6.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle4Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 6 && dragv.getId() == R.id.lmiddle) {
                        p4imgv2.setImageResource(R.drawable.p4m2);
                        count++;
                        mp3 = new GameMusic(getApplicationContext(), "p2drop");
                        mp3.start();
                        int[] imageCordinates = new int[2];
                        p4imgv2.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p4imgv2.getWidth() / 2);
                        y = imageCordinates[1] + (p4imgv2.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle4Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else if (count == 7 && dragv.getId() == R.id.rmost) {
                        p4imgv7.setImageResource(R.drawable.p4m7);
                        count++;
                 /*   mp3=new Gamemusic(getApplicationContext(), R.raw.goodjob);
				    mp3.start();*/
                        int[] imageCordinates = new int[2];
                        p4imgv7.getLocationOnScreen(imageCordinates);

                        x = imageCordinates[0] + (p4imgv7.getWidth() / 2);
                        y = imageCordinates[1] + (p4imgv7.getHeight() / 2);
                        smallanim mv = new smallanim(Puzzle4Activity.this);
                        mRelativeLayoutParent.addView(mv);
                        return true;
                    } else {
                        count = count;
                        mp3 = new GameMusic(getApplicationContext(), "wrongs");
                        mp3.start();

                        return true;

                    }

                case DragEvent.ACTION_DRAG_ENDED:
                    if (count == 1) {
                        p4dragv.setImageResource(R.drawable.p4m5);
                        p4dragv.setVisibility(View.VISIBLE);
                    } else if (count == 2) {
                        p4dragv.setImageResource(R.drawable.p4m4);
                        p4dragv.setVisibility(View.VISIBLE);
                    } else if (count == 3) {
                        p4dragv.setImageResource(R.drawable.p4m3);
                        p4dragv.setVisibility(View.VISIBLE);
                    } else if (count == 4) {
                        p4dragv.setImageResource(R.drawable.p4m1);
                        p4dragv.setVisibility(View.VISIBLE);
                    } else if (count == 5) {
                        p4dragv.setImageResource(R.drawable.p4m6);
                        p4dragv.setVisibility(View.VISIBLE);
                    } else if (count == 6) {
                        p4dragv.setImageResource(R.drawable.p4m2);
                        p4dragv.setVisibility(View.VISIBLE);
                    } else if (count == 7) {
                        p4dragv.setImageResource(R.drawable.p4m7);
                        p4dragv.setVisibility(View.VISIBLE);
                    } else if (count == 8) {
                        count++;
//					   Intent ipuzzle4 = new Intent(puzzle4.this,
//								Videoplay.class);
//						int id=R.raw.puzzle4a;
//						ipuzzle4.putExtra("vid", id);
//						ipuzzle4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						puzzle4.this.finish();
//						startActivity(ipuzzle4);

                        Intent intent = new Intent(Puzzle4Activity.this, BalloonAnimationActivity.class);
                        intent.putExtra(AppConstant.EXTRA_GREETING_IMAGE_RESOURCE_ID, ImageAndMediaResources.sImageIdGoogJob);
                        intent.putExtra(AppConstant.EXTRA_GREETING_SOUND_ID, ImageAndMediaResources.sSoundIdGoodjob);
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
        Intent intent = new Intent(this, Puzzle5Activity.class);
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
