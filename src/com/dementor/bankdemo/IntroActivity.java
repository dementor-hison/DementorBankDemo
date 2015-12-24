package com.dementor.bankdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class IntroActivity extends Activity {

	private Bitmap mBgBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("DEMENTOR - ", "IntroActivity - onCreate()");
		
		final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.putExtra("position", 13);
		int position = 13;
		
		mBgBitmap = BitmapFactory.decodeResource(getResources(), Const.BANK_INTRO[position]);
		
		ImageView view = new ImageView(this);
		view.setScaleType(ScaleType.FIT_XY);
		view.setImageBitmap(mBgBitmap);
		
		setContentView(view);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				
				startActivity(intent);
				Log.e("Dementor", "StartActivity");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				}, 1000);
			}
		}, 2000);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("DEMENTOR - ", "IntroActivity - onDestroy()");
		
		if(mBgBitmap != null) {
			mBgBitmap.recycle();
			mBgBitmap = null;
		}
		System.gc();
	}
	
	@Override
	public void onBackPressed() {
		Process.killProcess(Process.myPid());
	}
}
