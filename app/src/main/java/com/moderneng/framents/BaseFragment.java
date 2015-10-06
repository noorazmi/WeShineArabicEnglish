package com.moderneng.framents;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moderneng.WeShineApp;

import java.io.IOException;

/**
 * Base Fragment for all fragment in the app.
 *
 * @author noor.alam
 */
public abstract class BaseFragment extends Fragment implements OnCompletionListener {

	private View mFragmentView;
    private String mAudioFileName ;

    //protected Bitmap mTopBitmap;
    //protected Bitmap mMiddleBitmap;
    //protected Bitmap mBottomImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(getFragmentLayoutId(), container, false);
        return mFragmentView;
    }

    /**
     * Returns the view attached to the fragment
     *
     * @return View
     */
    protected View getFragmentView() {
        return mFragmentView;
    }

    protected void startAudioSound(int audioFileId) {

//		String uriPath = AppConstant.BASE_RESOURCE_PATH + audioFileId;
        int mAudioFileId = audioFileId;
//		Uri uri = Uri.parse(uriPath);
//		MediaPlayer mediaPlayer = MediaPlayer.create(WeShineApp.getInstance(), uri);
//		if(mediaPlayer != null){
//			mediaPlayer.setOnCompletionListener(this);
//			mediaPlayer.start();
//		}

       // MediaPlayer mMediaPlayer = new MediaPlayer();

        MediaPlayer mMediaPlayer = MediaPlayer.create(getActivity(), audioFileId);
        if(mMediaPlayer != null){
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.start();
		}


//		 SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//		 int soundID = soundPool.load(getActivity(), R.raw.maze1, 1);
//		 AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//         float actualVolume = (float) audioManager
//                 .getStreamVolume(AudioManager.STREAM_MUSIC);
//         float maxVolume = (float) audioManager
//                 .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//         float volume = actualVolume / maxVolume;
//		 soundPool.play(soundID, volume, volume, 1, 0, 1f);


    }


    protected void startAudioSound(String audioFileName) {
        MediaPlayer mediaPlayer = null;
        this.mAudioFileName = audioFileName;
        try {
            AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor(audioFileName + ".mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();
            //			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
//				@Override
//				public void onCompletion(MediaPlayer mp) {
//					mp.release();
//				}
//			});
//			if(mediaPlayer != null){
//				mediaPlayer.start();
//			}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mp != null) {
            mp.release();
        }
        onAudioComplete(mAudioFileName);
    }


//	protected void recycleBackgroundImages(){
//		if(mTopBitmap != null){
//			mTopBitmap.recycle();
//			mTopBitmap = null;
//		}
//		if(mMiddleBitmap != null){
//			mMiddleBitmap.recycle();
//			mMiddleBitmap = null;
//		}
//		if(mBottomImage != null){
//			mBottomImage.recycle();
//			mBottomImage = null;
//		}
//	}

    // Returns the layout id which will be attached to the fragment as view
    protected abstract int getFragmentLayoutId();

    protected abstract void onAudioComplete(String audioFileName);

}
