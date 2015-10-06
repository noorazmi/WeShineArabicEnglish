package com.moderneng.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moderneng.R;
import com.moderneng.WeShineApp;
import com.moderneng.animation.AnimType;
import com.moderneng.animation.AnimationUtil;
import com.moderneng.utils.AppConstant;
import com.moderneng.utils.GameMusic;
import com.moderneng.utils.ImageAndMediaResources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame3Activity extends Activity implements OnClickListener, AnimationListener {
    private ImageView mcard, tc1, tc2, tc3, bc1, bc2, bc3, bc4, bc5, clockv, textimage;
    private Animation anim1, anim2;
    private int count = 1, clickcount = 1;
    private View v1;
    private int mcount = 0;
    private int ncount;
    private RandomNumberGenerator g;
    private AnimationDrawable clockanim;
    private CountDownTimer mCountDownTimer;
    private TextView tv;
    private GameMusic findsame;
    private MediaPlayer mMediaPlayerClock;
    private RelativeLayout textl;
    private ScaleAnimation gameover, scale1;
    private boolean isGameWon = false;
    private Bitmap mBitmapBg;
    private Bitmap mBitmapTitle;
    private boolean mIsStoppedCalled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_memory_games_level3);
        tv = (TextView) findViewById(R.id.time2);
        clockv = (ImageView) findViewById(R.id.clock);
        clockv.setBackgroundResource(R.drawable.clock);
        clockanim = (AnimationDrawable) clockv.getBackground();
        clockanim.start();
        textimage = (ImageView) findViewById(R.id.textimg);
        textl = (RelativeLayout) findViewById(R.id.textlay);
        gameover = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
        gameover.setDuration(1000);
        gameover.setFillAfter(true);
        scale1 = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
        scale1.setDuration(1000);
        scale1.setFillAfter(true);
        scale1.setAnimationListener(this);
        findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdFindTheSimilarCard);
        //findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdFindTheSimilarCard);
        findsame.start();

        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished / 1000);
                tv.setText("" + time);
            }

            @Override
            public void onFinish() {
                tv.setText("0");
                clockanim.stop();
                if (!isGameWon) {



                    findsame = new GameMusic(getApplicationContext(), R.raw.game_over_ting_ting);
                    findsame.setOnCompleteListener(new GameMusic.OnCompleteListener() {
                        @Override
                        public void onComplete() {
                            finish();
                        }
                    });

                    textl.setVisibility(View.VISIBLE);
                    textimage.setAnimation(gameover);

                    if(!mIsStoppedCalled){
                        findsame.start();
                    }else{
                        finish();
                    }
                    gameover.start();
                    tc1.setOnClickListener(null);
                    tc2.setOnClickListener(null);
                    tc3.setOnClickListener(null);
                    bc1.setOnClickListener(null);
                    bc2.setOnClickListener(null);
                    bc3.setOnClickListener(null);
                    bc4.setOnClickListener(null);
                    bc5.setOnClickListener(null);
                }

            }
        };
        mCountDownTimer.start();
        g = new RandomNumberGenerator(8);
        ncount = g.generateNewRandom(mcount);
        anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim3);
        anim1.setAnimationListener(this);
        anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.toend);
        anim2.setAnimationListener(this);
        mcard = (ImageView) findViewById(R.id.cmain);
        tc1 = (ImageView) findViewById(R.id.tcard1);
        tc2 = (ImageView) findViewById(R.id.tcard2);
        tc3 = (ImageView) findViewById(R.id.tcard3);
        bc1 = (ImageView) findViewById(R.id.lcard1);
        bc2 = (ImageView) findViewById(R.id.lcard2);
        bc3 = (ImageView) findViewById(R.id.lcard3);
        bc4 = (ImageView) findViewById(R.id.lcard4);
        bc5 = (ImageView) findViewById(R.id.lcard5);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mcard.startAnimation(anim1);
                tc1.startAnimation(anim1);
                tc2.startAnimation(anim1);
                tc3.startAnimation(anim1);
                bc1.startAnimation(anim1);
                bc2.startAnimation(anim1);
                bc3.startAnimation(anim1);
                bc4.startAnimation(anim1);
                bc5.startAnimation(anim1);
            }
        }, 2500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsStoppedCalled = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor("clocksound.mp3");
                    mMediaPlayerClock = new MediaPlayer();
                    mMediaPlayerClock.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayerClock.setLooping(false);
                    mMediaPlayerClock.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                    mMediaPlayerClock.prepare();
                    if (mMediaPlayerClock != null) {
                        mMediaPlayerClock.setVolume(0.25f, 0.25f);
                        mMediaPlayerClock.start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 800);

        mBitmapBg = WeShineApp.getBitmapFromObb("memory_games_bg_level2.png");
        findViewById(R.id.relative_layout_parent).setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapBg));

        mBitmapTitle = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMemoryGame);
        ((ImageView) findViewById(R.id.imageview_title)).setImageBitmap(mBitmapTitle);
        AnimationUtil.performAnimation(findViewById(R.id.imageview_title), AnimType.ZOOM_IN, null);

    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        v1 = v;
        if (id == R.id.tcard1) {
            tc1.setImageResource(R.drawable.light);
        } else if (id == R.id.tcard2) {
            tc2.setImageResource(R.drawable.credb);
        } else if (id == R.id.tcard3) {
            tc3.setImageResource(R.drawable.ctrafic);
        } else if (id == R.id.lcard1) {
            bc1.setImageResource(R.drawable.cgolf);
        } else if (id == R.id.lcard2) {
            bc2.setImageResource(R.drawable.cblue);
        } else if (id == R.id.lcard3) {
            bc3.setImageResource(R.drawable.csun);
        } else if (id == R.id.lcard4) {
            bc4.setImageResource(R.drawable.csplate);
        } else if (id == R.id.lcard5) {
            bc5.setImageResource(R.drawable.plate);
        }
        if (ncount == 1) {
            if (id == R.id.tcard1) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarSignal);
                findsame.start();
            }
        } else if (ncount == 2) {
            if (id == R.id.tcard2) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdRedBilli);
                findsame.start();
            }
        } else if (ncount == 3) {
            if (id == R.id.tcard3) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarCamera);
                findsame.start();
            }
        } else if (ncount == 4) {
            if (id == R.id.lcard1) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGolfCart);
                findsame.start();
            }
        } else if (ncount == 5) {
            if (id == R.id.lcard2) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGreenBilli);
                findsame.start();
            }
        } else if (ncount == 6) {
            if (id == R.id.lcard3) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdLovelySun);
                findsame.start();
            }
        } else if (ncount == 7) {
            if (id == R.id.lcard4) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarStation);
                findsame.start();
            }
        } else if (ncount == 8) {
            if (id == R.id.lcard5) {
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarLight);
                findsame.start();
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ncount == 1) {
                    if (id == R.id.tcard1) {

                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }
                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }
                } else if (ncount == 2) {
                    if (id == R.id.tcard2) {
                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }

                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }

                } else if (ncount == 3) {
                    if (id == R.id.tcard3) {

                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }
                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }

                } else if (ncount == 4) {
                    if (id == R.id.lcard1) {

                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }
                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }

                } else if (ncount == 5) {
                    if (id == R.id.lcard2) {

                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }
                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }

                } else if (ncount == 6) {
                    if (id == R.id.lcard3) {
                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }

                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }

                } else if (ncount == 7) {
                    if (id == R.id.lcard4) {
                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }

                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }

                } else if (ncount == 8) {
                    if (id == R.id.lcard5) {
                        v1.setVisibility(View.INVISIBLE);
                        if (mcount < 7) {
                            mcount = mcount + 1;
                            ncount = g.generateNewRandom(mcount);
                            setmaincard(ncount);
                        } else {
                            mcard.setVisibility(View.INVISIBLE);
                            gameover();
                        }
                    } else {
                        v1.startAnimation(anim1);
                        ncount = ncount;
                        mcount = mcount;
                    }

                }
            }

            private void gameover() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isGameWon = true;
                        if(mMediaPlayerClock != null){
                            mMediaPlayerClock.release();
                            mMediaPlayerClock = null;
                        }
                        mCountDownTimer.cancel();
                        clockanim.stop();
                        mCountDownTimer.cancel();
                        clockanim.stop();
                        Intent intent = new Intent(MemoryGame3Activity.this, BalloonAnimationActivity.class);
                        intent.putExtra(AppConstant.EXTRA_GREETING_IMAGE_RESOURCE_ID, ImageAndMediaResources.sImageIdYouAreSmart);
                        intent.putExtra(AppConstant.EXTRA_GREETING_SOUND_ID, ImageAndMediaResources.sSoundIdYouAreSmart);
                        intent.putExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_DELAY, AppConstant.BALLOON_ANIMATION_SOUND_DELAY);
                        startActivityForResult(intent, 100);
                    }
                }, 1500);
            }

            private void setmaincard(int ncount) {
                if (ncount == 1) {
                    mcard.setImageResource(R.drawable.light);
                } else if (ncount == 2) {
                    mcard.setImageResource(R.drawable.credb);
                } else if (ncount == 3) {
                    mcard.setImageResource(R.drawable.ctrafic);
                } else if (ncount == 4) {
                    mcard.setImageResource(R.drawable.cgolf);
                } else if (ncount == 5) {
                    mcard.setImageResource(R.drawable.cblue);
                } else if (ncount == 6) {
                    mcard.setImageResource(R.drawable.csun);
                } else if (ncount == 7) {
                    mcard.setImageResource(R.drawable.csplate);
                } else if (ncount == 8) {
                    mcard.setImageResource(R.drawable.plate);
                }
            }
        }, 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Intent i = new Intent(Mlevel3.this, Mlevel3.class);
        //startActivity(i);
        finish();

    }


    @Override
    public void onAnimationEnd(Animation animation) {
        if (clickcount == 1) {
            if (animation == anim1) {
                // mcard.setImageResource(R.drawable.light);
                tc1.setImageResource(R.drawable.front);
                tc2.setImageResource(R.drawable.front);
                tc3.setImageResource(R.drawable.front);
                bc1.setImageResource(R.drawable.front);
                bc2.setImageResource(R.drawable.front);
                bc3.setImageResource(R.drawable.front);
                bc4.setImageResource(R.drawable.front);
                bc5.setImageResource(R.drawable.front);
                if (ncount == 1) {
                    mcard.setImageResource(R.drawable.light);
                } else if (ncount == 2) {
                    mcard.setImageResource(R.drawable.credb);
                } else if (ncount == 3) {
                    mcard.setImageResource(R.drawable.ctrafic);
                } else if (ncount == 4) {
                    mcard.setImageResource(R.drawable.cgolf);
                } else if (ncount == 5) {
                    mcard.setImageResource(R.drawable.cblue);
                } else if (ncount == 6) {
                    mcard.setImageResource(R.drawable.csun);
                } else if (ncount == 7) {
                    mcard.setImageResource(R.drawable.csplate);
                } else if (ncount == 8) {
                    mcard.setImageResource(R.drawable.plate);
                }
                tc1.setOnClickListener(this);
                tc2.setOnClickListener(this);
                tc3.setOnClickListener(this);
                bc1.setOnClickListener(this);
                bc2.setOnClickListener(this);
                bc3.setOnClickListener(this);
                bc4.setOnClickListener(this);
                bc5.setOnClickListener(this);
                clickcount++;
                // count=uniquenumber();
            }
        }
        if (clickcount > 1) {
            if (animation == anim1) {
                tc1.setImageResource(R.drawable.front);
                tc2.setImageResource(R.drawable.front);
                tc3.setImageResource(R.drawable.front);
                bc1.setImageResource(R.drawable.front);
                bc2.setImageResource(R.drawable.front);
                bc3.setImageResource(R.drawable.front);
                bc4.setImageResource(R.drawable.front);
                bc5.setImageResource(R.drawable.front);
            }
        }
        if (animation == scale1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCountDownTimer.cancel();
                    if(mMediaPlayerClock != null){
                        mMediaPlayerClock.release();
                        mMediaPlayerClock = null;
                    }
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (clickcount > 1) {
            if (animation == anim1) {
                findsame = new GameMusic(getApplicationContext(), "wrongs");
                findsame.start();
            }
        }
    }

    public class RandomNumberGenerator {
        ArrayList numbersList = new ArrayList();

        public RandomNumberGenerator(int length) {
            for (int x = 1; x <= length; x++)
                numbersList.add(x);
            Collections.shuffle(numbersList);
        }

        public int generateNewRandom(int n) {
            return (Integer) numbersList.get(n);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCountDownTimer.cancel();
        if(mMediaPlayerClock != null){
            mMediaPlayerClock.release();
            mMediaPlayerClock = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsStoppedCalled = true;
        if(mMediaPlayerClock != null){
            mMediaPlayerClock.release();
            mMediaPlayerClock = null;
        }

        if (mBitmapBg != null) {
            mBitmapBg.recycle();
            mBitmapBg = null;
        }
        if (mBitmapTitle != null) {
            mBitmapTitle.recycle();
            mBitmapTitle = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayerClock != null){
            mMediaPlayerClock.release();
            mMediaPlayerClock = null;
        }
        mCountDownTimer.cancel();
    }
}
