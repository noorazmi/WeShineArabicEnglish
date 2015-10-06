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
import android.view.Window;
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

public class MemoryGame2Activity extends Activity implements OnClickListener, AnimationListener {
    private ImageView ucard1, ucard2, ucard3, ucard4, ucard5, mcard1, mcard2, mcard3, mcard4, mcard5, clocka, textv;
    private Animation anim1, anim2;
    private View v1, v2;
    private int count = 0, clickcount = 0;
    private MediaPlayer mMediaPlayerClock;
    private GameMusic findsame;
    private AnimationDrawable clockanim;
    private TextView tv;
    private RelativeLayout textlay;
    private CountDownTimer mCountDownTimer;
    private ScaleAnimation gameover, scale1;
    private Boolean gamefinish = false;
    int nomatch = 0;
    private boolean isGameWon = false;
    private Bitmap mBitmapBg;
    private Bitmap mBitmapTitle;
    private boolean mIsStoppedCalled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_memory_games_level2);
        gameover = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
        gameover.setDuration(1000);
        gameover.setFillAfter(true);
        scale1 = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, (float) 0.5, Animation.RELATIVE_TO_SELF, (float) 0.5);
        scale1.setDuration(1000);
        scale1.setFillAfter(true);
        scale1.setAnimationListener(this);
        tv = (TextView) findViewById(R.id.time2);
        clocka = (ImageView) findViewById(R.id.clock);
        clocka.setBackgroundResource(R.drawable.clock);
        clockanim = (AnimationDrawable) clocka.getBackground();
        clockanim.start();
        anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tomid);
        anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.toend);
        anim1.setAnimationListener(this);
        anim2.setAnimationListener(this);
        ucard1 = (ImageView) findViewById(R.id.cardu1);
        ucard2 = (ImageView) findViewById(R.id.cardu2);
        ucard3 = (ImageView) findViewById(R.id.cardu3);
        ucard4 = (ImageView) findViewById(R.id.cardu4);
        ucard5 = (ImageView) findViewById(R.id.cardu5);
        mcard1 = (ImageView) findViewById(R.id.cardb1);
        mcard2 = (ImageView) findViewById(R.id.cardb2);
        mcard3 = (ImageView) findViewById(R.id.cardb3);
        mcard4 = (ImageView) findViewById(R.id.cardb4);
        mcard5 = (ImageView) findViewById(R.id.cardb5);

        findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdFindTheSimilarCards);
        findsame.start();
        textlay = (RelativeLayout) findViewById(R.id.textlay);
        textv = (ImageView) findViewById(R.id.textimg);
        mCountDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished / 1000);
                tv.setText("" + time);
            }

            @Override
            public void onFinish() {
                tv.setText("0");
                if(mMediaPlayerClock != null){
                    mMediaPlayerClock.release();
                    mMediaPlayerClock = null;
                }
                clockanim.stop();
                findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGameOverTingTing);
                findsame.setOnCompleteListener(new GameMusic.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        finish();
                    }
                });
                if (!isGameWon) {
                    if(!mIsStoppedCalled){
                        findsame.start();
                    }else{
                        finish();
                    }

                    ucard1.setOnClickListener(null);
                    mcard1.setOnClickListener(null);
                    ucard2.setOnClickListener(null);
                    mcard2.setOnClickListener(null);
                    ucard3.setOnClickListener(null);
                    mcard3.setOnClickListener(null);
                    ucard4.setOnClickListener(null);
                    mcard4.setOnClickListener(null);
                    ucard5.setOnClickListener(null);
                    mcard5.setOnClickListener(null);
                    textlay.setVisibility(View.VISIBLE);
                    textv.setAnimation(gameover);
                    gameover.start();
                }

            }
        };
        mCountDownTimer.start();

        ucard1.startAnimation(anim1);
        mcard1.startAnimation(anim1);
        ucard2.startAnimation(anim1);
        mcard2.startAnimation(anim1);
        ucard3.startAnimation(anim1);
        mcard3.startAnimation(anim1);
        ucard4.startAnimation(anim1);
        mcard5.startAnimation(anim1);
        ucard5.startAnimation(anim1);
        mcard5.startAnimation(anim1);

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


        //mBitmapBg = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMemoryGamesLevel2);
        mBitmapBg = WeShineApp.getBitmapFromObb("memory_games_bg_level3.png");
        findViewById(R.id.relative_layout_parent).setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmapBg));

        mBitmapTitle = BitmapFactory.decodeResource(getResources(), ImageAndMediaResources.sImageIdMemoryGame);
        ((ImageView) findViewById(R.id.imageview_title)).setImageBitmap(mBitmapTitle);
        AnimationUtil.performAnimation(findViewById(R.id.imageview_title), AnimType.ZOOM_IN, null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        clickcount++;
        if (clickcount == 1) {
            v1 = v;
            v1.startAnimation(anim1);
        } else if (clickcount == 2) {
            v2 = v;
            v2.startAnimation(anim1);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == scale1) {
            mCountDownTimer.cancel();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MemoryGame2Activity.this, MemoryGame3Activity.class);
                    startActivity(i);
                    finish();
                }
            }, 2500);
        }
        if (count == 0) {
            if (animation == anim1) {
                ucard1.setImageResource(R.drawable.cgolf);
                mcard1.setImageResource(R.drawable.csplate);
                ucard2.setImageResource(R.drawable.cblue);
                mcard2.setImageResource(R.drawable.plate);
                ucard3.setImageResource(R.drawable.csun);
                mcard3.setImageResource(R.drawable.cgolf);
                ucard4.setImageResource(R.drawable.csplate);
                mcard4.setImageResource(R.drawable.cblue);
                ucard5.setImageResource(R.drawable.plate);
                mcard5.setImageResource(R.drawable.csun);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ucard1.startAnimation(anim2);
                        mcard1.startAnimation(anim2);
                        ucard2.startAnimation(anim2);
                        mcard2.startAnimation(anim2);
                        ucard3.startAnimation(anim2);
                        mcard3.startAnimation(anim2);
                        ucard4.startAnimation(anim2);
                        mcard4.startAnimation(anim2);
                        ucard5.startAnimation(anim2);
                        mcard5.startAnimation(anim2);
                    }
                }, 2700);
            }
            if (animation == anim2) {
                ucard1.setImageResource(R.drawable.front);
                mcard1.setImageResource(R.drawable.front);
                ucard2.setImageResource(R.drawable.front);
                mcard2.setImageResource(R.drawable.front);
                ucard3.setImageResource(R.drawable.front);
                mcard3.setImageResource(R.drawable.front);
                ucard4.setImageResource(R.drawable.front);
                mcard4.setImageResource(R.drawable.front);
                ucard5.setImageResource(R.drawable.front);
                mcard5.setImageResource(R.drawable.front);
                count++;
                ucard1.setOnClickListener(this);
                mcard1.setOnClickListener(this);
                ucard2.setOnClickListener(this);
                mcard2.setOnClickListener(this);
                ucard3.setOnClickListener(this);
                mcard3.setOnClickListener(this);
                ucard4.setOnClickListener(this);
                mcard4.setOnClickListener(this);
                ucard5.setOnClickListener(this);
                mcard5.setOnClickListener(this);
            }
        }
        if (clickcount == 1) {
            if (animation == anim1) {
                if (v1.getId() == R.id.cardu1) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGolfCart);
                    findsame.start();
                    ucard1.setImageResource(R.drawable.cgolf);
                } else if (v1.getId() == R.id.cardb1) {
                    mcard1.setImageResource(R.drawable.csplate);
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarStation);
                    findsame.start();
                } else if (v1.getId() == R.id.cardu2) {
                    ucard2.setImageResource(R.drawable.cblue);
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGreenBilli);
                    findsame.start();
                } else if (v1.getId() == R.id.cardb2) {
                    mcard2.setImageResource(R.drawable.plate);
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarLight);
                    findsame.start();
                } else if (v1.getId() == R.id.cardu3) {
                    ucard3.setImageResource(R.drawable.csun);
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdLovelySun);
                    findsame.start();
                } else if (v1.getId() == R.id.cardb3) {
                    mcard3.setImageResource(R.drawable.cgolf);
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGolfCart);
                    findsame.start();
                } else if (v1.getId() == R.id.cardu4) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarStation);
                    findsame.start();
                    ucard4.setImageResource(R.drawable.csplate);
                } else if (v1.getId() == R.id.cardb4) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGreenBilli);
                    findsame.start();
                    mcard4.setImageResource(R.drawable.cblue);
                } else if (v1.getId() == R.id.cardu5) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarLight);
                    findsame.start();
                    ucard5.setImageResource(R.drawable.plate);
                } else if (v1.getId() == R.id.cardb5) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdLovelySun);
                    findsame.start();
                    mcard5.setImageResource(R.drawable.csun);
                }
            }
        } else if (clickcount == 2) {
            if (animation == anim1) {
                if (v2.getId() == R.id.cardu1) {
                    ucard1.setImageResource(R.drawable.cgolf);
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGolfCart);
                    findsame.start();
                } else if (v2.getId() == R.id.cardb1) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarStation);
                    findsame.start();
                    mcard1.setImageResource(R.drawable.csplate);
                } else if (v2.getId() == R.id.cardu2) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGreenBilli);
                    findsame.start();
                    ucard2.setImageResource(R.drawable.cblue);
                } else if (v2.getId() == R.id.cardb2) {
                    mcard2.setImageResource(R.drawable.plate);
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarLight);
                    findsame.start();
                } else if (v2.getId() == R.id.cardu3) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdLovelySun);
                    findsame.start();
                    ucard3.setImageResource(R.drawable.csun);
                } else if (v2.getId() == R.id.cardb3) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGolfCart);
                    findsame.start();
                    mcard3.setImageResource(R.drawable.cgolf);
                } else if (v2.getId() == R.id.cardu4) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarStation);
                    findsame.start();
                    ucard4.setImageResource(R.drawable.csplate);
                } else if (v2.getId() == R.id.cardb4) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdGreenBilli);
                    findsame.start();
                    mcard4.setImageResource(R.drawable.cblue);
                } else if (v2.getId() == R.id.cardu5) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdSolarLight);
                    findsame.start();
                    ucard5.setImageResource(R.drawable.plate);
                } else if (v2.getId() == R.id.cardb5) {
                    findsame = new GameMusic(getApplicationContext(), ImageAndMediaResources.sSoundIdLovelySun);
                    findsame.start();
                    mcard5.setImageResource(R.drawable.csun);
                }
            } else if (clickcount == 2) {
                if (animation == anim2) {
                    ImageView img1 = (ImageView) v1;
                    ImageView img2 = (ImageView) v2;
                    img1.setImageResource(R.drawable.front);
                    img2.setImageResource(R.drawable.front);
                    clickcount = 0;
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((v1.getId() == R.id.cardu1 && v2.getId() == R.id.cardb3) || v2.getId() == R.id.cardu1 && v1.getId() == R.id.cardb3) {
                        findsame = new GameMusic(getApplicationContext(), "p2drop");
                        findsame.start();
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        clickcount = 0;
                        gamefinish = true;
                        nomatch++;

                    } else if ((v1.getId() == R.id.cardu2 && v2.getId() == R.id.cardb4) || v2.getId() == R.id.cardu2 && v1.getId() == R.id.cardb4) {
                        findsame = new GameMusic(getApplicationContext(), "p2drop");
                        findsame.start();
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        gamefinish = true;
                        nomatch++;
                        clickcount = 0;
                    } else if ((v1.getId() == R.id.cardu3 && v2.getId() == R.id.cardb5) || v2.getId() == R.id.cardu3 && v1.getId() == R.id.cardb5) {
                        findsame = new GameMusic(getApplicationContext(), "p2drop");
                        findsame.start();
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        gamefinish = true;
                        nomatch++;
                        clickcount = 0;
                    } else if ((v1.getId() == R.id.cardu4 && v2.getId() == R.id.cardb1) || v2.getId() == R.id.cardu4 && v1.getId() == R.id.cardb1) {
                        findsame = new GameMusic(getApplicationContext(), "p2drop");
                        findsame.start();
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        gamefinish = true;
                        nomatch++;
                        clickcount = 0;
                    } else if ((v1.getId() == R.id.cardu5 && v2.getId() == R.id.cardb2) || v2.getId() == R.id.cardu5 && v1.getId() == R.id.cardb2) {
                        findsame = new GameMusic(getApplicationContext(), "p2drop");
                        findsame.start();
                        v1.setVisibility(View.INVISIBLE);
                        v2.setVisibility(View.INVISIBLE);
                        gamefinish = true;
                        nomatch++;
                        clickcount = 0;
                    } else {
                        ImageView img1 = (ImageView) v1;
                        ImageView img2 = (ImageView) v2;
                        findsame = new GameMusic(getApplicationContext(), "wrongs");
                        findsame.start();
                        img1.setImageResource(R.drawable.front);
                        img2.setImageResource(R.drawable.front);
                        clickcount = 0;
                        nomatch = nomatch;
                        gamefinish = false;
                    }
                }
            }, 900);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (nomatch == 5) {
                        if (gamefinish == true) {
                            isGameWon = true;
                            if(mMediaPlayerClock != null){
                                mMediaPlayerClock.release();
                                mMediaPlayerClock = null;
                            }
                            mCountDownTimer.cancel();
                            clockanim.stop();
                            Intent intent = new Intent(MemoryGame2Activity.this, BalloonAnimationActivity.class);
                            intent.putExtra(AppConstant.EXTRA_GREETING_IMAGE_RESOURCE_ID, ImageAndMediaResources.sImageIdCongratulations);
                            intent.putExtra(AppConstant.EXTRA_GREETING_SOUND_ID, ImageAndMediaResources.sSoundIdCongratulationsShort);
                            //intent.putExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_ID, R.raw.ballon_playing);
                            intent.putExtra(AppConstant.EXTRA_BALLOON_ANIMATION_SOUND_DELAY, AppConstant.BALLOON_ANIMATION_SOUND_DELAY);
                            startActivityForResult(intent, 100);
                        }
                    }
                }
            }, 2000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(MemoryGame2Activity.this, MemoryGame3Activity.class);
        startActivity(i);
        finish();

    }


    @Override
    public void onAnimationRepeat(Animation arg0) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (count == 0) {
            if (animation == anim1) {
                ucard1.setImageResource(R.drawable.front);
                mcard1.setImageResource(R.drawable.front);
                ucard2.setImageResource(R.drawable.front);
                mcard2.setImageResource(R.drawable.front);
                ucard3.setImageResource(R.drawable.front);
                mcard3.setImageResource(R.drawable.front);
                ucard4.setImageResource(R.drawable.front);
                mcard4.setImageResource(R.drawable.front);
                ucard5.setImageResource(R.drawable.front);
                mcard5.setImageResource(R.drawable.front);
            }
            if (animation == anim2) {
                ucard1.setImageResource(R.drawable.front);
                mcard1.setImageResource(R.drawable.front);
                ucard2.setImageResource(R.drawable.front);
                mcard2.setImageResource(R.drawable.front);
                ucard3.setImageResource(R.drawable.front);
                mcard3.setImageResource(R.drawable.front);
                ucard4.setImageResource(R.drawable.front);
                mcard4.setImageResource(R.drawable.front);
                ucard5.setImageResource(R.drawable.front);
                mcard5.setImageResource(R.drawable.front);
            }
        }
        if (clickcount == 1) {
            if (animation == anim1) {
                ImageView img = (ImageView) v1;
                img.setImageResource(R.drawable.front);
            }
        } else if (clickcount == 2) {
            if (animation == anim1) {
                ImageView img = (ImageView) v2;
                img.setImageResource(R.drawable.front);
            } else if (animation == anim2) {
                ImageView img = (ImageView) v1;
                img.setImageResource(R.drawable.front);
                ImageView img1 = (ImageView) v2;
                img1.setImageResource(R.drawable.front);
            }
        } else if (clickcount > 2) {
            if (animation == anim2) {
                ImageView v = (ImageView) v1;
                v.setImageResource(R.drawable.front);
            }
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
