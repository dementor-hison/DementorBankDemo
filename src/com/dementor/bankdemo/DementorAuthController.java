package com.dementor.bankdemo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.dementor.bankdemo.ui.InputView;
import com.dementor.bankdemo.ui.StateView;

public class DementorAuthController implements InputView.OnInputListener {
	
	private Context mContext;
	private OnInputResultListener mListener;
	
	private StateView mStateView;
	private InputView mInputView;

	private int mCurrentStep = 1;
	private int mLockIndex = -1;
	
	public DementorAuthController(Context context, View mainView, int backgroundColor, int highlightColor, int disableColor,
			int columnCount, OnInputResultListener listener) {
		
		mContext = context;
		
		mInputView = (InputView)mainView.findViewById(R.id.inputView);
		mInputView.setNumberColumn(columnCount);
		mInputView.setIconBackgroundColor(backgroundColor);
		mInputView.setInputListener(this);
		
		mStateView = (StateView)mainView.findViewById(R.id.descriptionView);
		mStateView.setStateCount(columnCount);
		mStateView.setColor(backgroundColor, highlightColor, disableColor);
		mStateView.setStep(mCurrentStep);
		
		mListener = listener;
	}
	
	public void setStateText(String text) {
		mStateView.setText(text);		
	}
	
	public void setIconList(ArrayList<Bitmap> iconImageList) {
		Log.d("DEMENTOR - ", "setIconList() - " + iconImageList.size());
		
		mInputView.setImageList(iconImageList);
	}
	
	public void reset() {
		mLockIndex = -1;
		mCurrentStep = Math.abs(mLockIndex);
		
		mStateView.setStep(mCurrentStep);
	}
	
	public void onDestroy() {
		
		if(mInputView != null) {
			mInputView.onDestroy();	
		}
	}
	
	@Override
	public void onInput(int moveIndex, int hitIndex) {
		Log.d("DEMENTOR - ", "moveIndex - " + moveIndex + ", hitIndex - " + hitIndex + " , mLockIndex : " + mLockIndex);
		
		Vibrator vib = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		vib.vibrate(200);
		
		if(mLockIndex == -1)
			mLockIndex = hitIndex;

		// Failed
		if(hitIndex > 100){
			reset();
			mListener.onInputResult(false);
			return;
		}
		
		// Failed
		if(mLockIndex != hitIndex){
			reset();
			mListener.onInputResult(false);
			return;
		}
		
		// Success Step
		mStateView.setStep(++mCurrentStep);
		if(mCurrentStep == mStateView.getStateCount()){
			mListener.onInputResult(true);			
		}
	}
	
	public interface OnInputResultListener {
		public void onInputResult(boolean isResult);
	}
}
