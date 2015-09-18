package com.dementor.bankdemo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dementor.bankdemo.R;

public class StateView extends LinearLayout {
	
	TextView mHelpText;
	ImageView[] mStateIcon = new ImageView[4];
	ImageView[] mStateDot = new ImageView[4];
	View mProgressBar;
	int mCurrentStep = 1;
	
	private int mHighlightColor, mDisableColor;
	
	private int mStateCount = 4;
	
	public StateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.dementor_state_view, this);
		
		mHelpText = (TextView)findViewById(R.id.help_text);
		
		mProgressBar = findViewById(R.id.progress_on);
		mProgressBar.setBackgroundColor(mHighlightColor);
		
		for (int i = 0; i < mStateIcon.length; i++) {
			
			int stateId = getResources().getIdentifier("state_place" + i, "id", getContext().getPackageName());
			mStateIcon[i] = (ImageView)findViewById(stateId);
			
			int dotId = getResources().getIdentifier("state_dot" + i, "id", getContext().getPackageName());
			mStateDot[i] = (ImageView)findViewById(dotId);
		}
	}
	
	public void setStateCount(int columnCount) {
		if(columnCount == 3){
			mStateCount = columnCount;
			findViewById(R.id.state_last_group).setVisibility(View.GONE);
			return;
		}
		mStateCount = 4;
		findViewById(R.id.state_last_group).setVisibility(View.VISIBLE);
	}
	
	public int getStateCount() {
		return mStateCount;
	}
	
	public void setColor(int iconBackgroundColor, int highlightColor, int disableColor) {
		mHighlightColor = highlightColor;
		mDisableColor = disableColor;
		
		View progressOff = findViewById(R.id.progress_off);
		progressOff.setBackgroundColor(mDisableColor);
		mProgressBar.setBackgroundColor(mHighlightColor);
		
		setStep(mCurrentStep);
	}
	
	public void setStep(int step) {
		Log.d("DEMENTOR - ", "setStep - " + step);
		mCurrentStep = step;
		
		for (int i = 0; i < 4; i++) {
			if (mCurrentStep > i){
				mStateIcon[i].setColorFilter(mHighlightColor);
				mStateDot[i].setColorFilter(mHighlightColor);
				continue;
			}
			mStateIcon[i].setColorFilter(mDisableColor);
			mStateDot[i].setColorFilter(mDisableColor);
		}
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		  @Override     
		  public void run() {
			  displayProgress();
		  }
		}, 100);
	}
	
	private void displayProgress() {
		int progressBarSize = 0;
		for (int i = 0; i < 4; i++) {
			ImageView stateIcon = mStateIcon[i];
			
			if (mCurrentStep > i) {
				int[] location = new int[2];
				stateIcon.getLocationOnScreen(location);
				progressBarSize = location[0] + (stateIcon.getWidth() / 2);
			}
		}
		
		int dp = 4;
		float scale = getResources().getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
		mProgressBar.setLayoutParams(new FrameLayout.LayoutParams(progressBarSize - 22, px));
	}
	
	public void setShowText(boolean show) {
		mHelpText.setVisibility(show ? View.VISIBLE : View.GONE);
	}
	
	public void setText(String text) {
		mHelpText.setText(text);
	}
}
