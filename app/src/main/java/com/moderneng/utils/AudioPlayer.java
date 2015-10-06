package com.moderneng.utils;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.moderneng.WeShineApp;

import java.io.IOException;

public class AudioPlayer {
	private MediaPlayer mp;
	private Context mContext;

	public AudioPlayer(Context ctx, int id) {
		this.mContext = ctx;
		mp = new MediaPlayer();
		mp = MediaPlayer.create(mContext, id);
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mp.setLooping(false);
	}

	public AudioPlayer(Context ctx, String fileName) {
		this.mContext = ctx;
        try {
			AssetFileDescriptor fd = WeShineApp.getAssetFileDescriptor(fileName+".mp3");
			mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
			mp.prepare();
		} catch (IOException e) {
			e.printStackTrace();
        }
	}

	public void start() {
		if(mp != null){
			mp.start();
		}
	}

	public void stop() {
		if (mp != null && mp.isPlaying() == true) {
			mp.stop();
		}
	}

	public void pause() {
		if (mp != null && mp.isPlaying() == true) {
			mp.pause();
		}
	}

	public void release() {
		if (mp != null) {
			mp.release();
		}

	}

	public void setLooping(boolean lopping) {
		mp.setLooping(lopping);
	}
}
