package com.dementor.bankdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class MovieActivity extends Activity
{
	private SurfaceView surface = null;
	private SurfaceHolder surfaceHolder = null;
	private MediaPlayer player = null;
//	private ArrayList<Integer> playList = new ArrayList<Integer>(Arrays.asList(R.raw.dk1, R.raw.dk2, R.raw.dk3, R.raw.dk4));
	private int contentsWidth = 0;
	private int contentsHeight = 0;
	private int surfaceWidth = 0;
	private int surfaceHeight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

		setContentView(R.layout.dementor_movie_view);
		
		findViewById(R.id.btMovieSkip).setOnClickListener(onClickListener);

		surface = (SurfaceView) findViewById(R.id.surface);
		surface.setOnClickListener(onClickListener);
		surfaceHolder = surface.getHolder();
		surfaceHolder.setKeepScreenOn(true);
		surfaceHolder.addCallback(onSurfaceChangeListener);

	}

	@Override
	protected void onResume()
	{
		super.onResume();

		if (player == null) return;

		player.start();

		// Toast.makeText(getApplicationContext(), "영상을 클릭하면 Skip됩니다.",
		// Toast.LENGTH_SHORT).show();;
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		if (player == null) return;

		player.pause();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		if (player == null) return;
		player.stop();
		player.release();
		player = null;
		surface.invalidate();
	}

	OnClickListener onClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Log.d("TAG", "onClickListener ");

			switch (v.getId())
			{
			case R.id.surface:
				if (player == null) return;

				if (player.isPlaying())
				{
					player.pause();
				}
				else
				{
					player.start();
				}
				break;

			case R.id.btMovieSkip:
				finishMovie();
			default:
				break;
			}

		}
	};

	SurfaceHolder.Callback onSurfaceChangeListener = new Callback()
	{

		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
			if (player == null) return;

			player.stop();
			player.release();
			player = null;
			surface.invalidate();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
			initPlayer();

			surfaceWidth = surface.getWidth();
			surfaceHeight = surface.getHeight();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
			Log.i("TAG", "surfaceChanged width : " + width + " , height : " + height + " , format : " + format);
		}
	};

	private void initPlayer()
	{
		if (player == null)
		{
			Random random = new Random();
			int index = random.nextInt(4);
			Log.d("TAG", "Random index :" + index);
//			player = MediaPlayer.create(getApplicationContext(), playList.get(index));
		}

		if (surfaceHolder == null)
		{
			Log.e("TAG", "surfaceHolder is null");
		}

		player.setDisplay(surfaceHolder);
		player.setOnErrorListener(onErrorListener);
		player.setOnPreparedListener(onPreparedListener);
		player.setOnBufferingUpdateListener(onBufferingUpdateListener);
		player.setOnInfoListener(onInfoListener);
		player.setOnCompletionListener(onCompletionListener);
		player.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
	}

	OnErrorListener onErrorListener = new OnErrorListener()
	{
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra)
		{
			String msg = String.format("onError what : %s , extra : %s", what, extra);
			Log.e("TAG", msg);

			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
			;
			return false;
		}
	};

	OnInfoListener onInfoListener = new OnInfoListener()
	{
		@Override
		public boolean onInfo(MediaPlayer mp, int what, int extra)
		{
			Log.i("TAG", "onInfo - what : " + what + " , extra : " + extra);
			return false;
		}
	};

	OnBufferingUpdateListener onBufferingUpdateListener = new OnBufferingUpdateListener()
	{
		@Override
		public void onBufferingUpdate(MediaPlayer mp, int percent)
		{
			Log.d("TAG", "onBufferingUpdate : " + percent);
		}
	};

	OnPreparedListener onPreparedListener = new OnPreparedListener()
	{
		@Override
		public void onPrepared(MediaPlayer mp)
		{
			Log.d("Tag", "onPrepared");
			if (player == null) return;

			contentsWidth = player.getVideoWidth();
			contentsHeight = player.getVideoHeight();
			Log.d("TAG", String.format("contentsWidth : %s ,  contentsHeight : %s", contentsWidth, contentsHeight));
			adjustSurfaceViewSize();

			player.start();
		}
	};

	OnCompletionListener onCompletionListener = new OnCompletionListener()
	{
		@Override
		public void onCompletion(MediaPlayer mp)
		{
			finishMovie();
		}
	};

	OnVideoSizeChangedListener onVideoSizeChangedListener = new OnVideoSizeChangedListener()
	{
		@Override
		public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
		{
			// contentsWidth = width;
			// contentsHeight = height;
			// Log.d("TAG",
			// String.format("contentsWidth : %s ,  contentsHeight : %s",
			// contentsWidth, contentsHeight));
			// adjustSurfaceViewSize();
		}
	};

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		Log.d("TAG", "onConfigurationChanged");
		super.onConfigurationChanged(newConfig);
	}

	private void adjustSurfaceViewSize()
	{
		if (surface == null) return;

		if (contentsWidth <= 0 || contentsHeight <= 0) return;

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		Log.d("TAG", String.format("surfaceWidth : %s ,  surfaceHeight : %s", surfaceWidth, surfaceHeight));
		int resizeWidth = 0;
		int resizeHeight = 0;

		float fWidthRatio = (float) surfaceWidth / (float) contentsWidth;
		float fHeightRatio = (float) surfaceHeight / (float) contentsHeight;
		float fScreenRatio = (float) surfaceWidth / (float) surfaceHeight;
		float fMediaRatio = (float) contentsWidth / (float) contentsHeight;

		if (fMediaRatio > fScreenRatio)
		{
			resizeHeight = (int) ((float) contentsHeight * fWidthRatio);
			resizeWidth = surfaceWidth;
		}
		else
		{
			resizeWidth = (int) ((float) contentsWidth * fHeightRatio);
			resizeHeight = surfaceHeight;
		}

		LayoutParams params = surface.getLayoutParams();
		params.width = resizeWidth;
		params.height = resizeHeight;
		surface.setLayoutParams(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			finishMovie();
		}

		return super.onKeyDown(keyCode, event);
	}

	private void finishMovie()
	{
		if (player != null)
		{
			player.stop();
			player.release();
			player = null;
			surface.invalidate();
		}

		Log.e("TAG", "Send result RESULT OK");
		setResult(RESULT_OK);
		finish();
		

	}

}
