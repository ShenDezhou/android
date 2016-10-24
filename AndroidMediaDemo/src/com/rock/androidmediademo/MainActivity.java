package com.rock.androidmediademo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

	private VideoView myVideo;
	
	private MediaController mediaController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		myVideo = (VideoView) this.findViewById(R.id.myvideo);
		mediaController = new MediaController(this);
		myVideo.setMediaController(mediaController);
		mediaController.setMediaPlayer(myVideo);
		
		myVideo.setVideoPath("http://7rflo2.com2.z0.glb.qiniucdn.com/568e1da0af4b6.mp4");
		myVideo.requestFocus();
		myVideo.start();
	}
}
