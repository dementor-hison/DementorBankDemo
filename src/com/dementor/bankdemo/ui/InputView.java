package com.dementor.bankdemo.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.dementor.bankdemo.R;

public class InputView extends FrameLayout {  
	
	private ArrayList<Bitmap> mImageList = new ArrayList<Bitmap>();
	private GridView mGridView;
	
	private InputView.OnInputListener mInputListener;
	
	private int mColumnCount = 5;
	private int mIconSize = 0;
	private int mIconLeftPadding = 0;
	private int mMoveIndex;
	private InputViewAdapter mAdapter;
	
	private ImageView mMoveIconView;
	
	private int mIconBackgroundColor;
	
	public InputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.dementor_input_view, this);
		
		mGridView = (GridView)findViewById(R.id.gridView);
		mAdapter = new InputViewAdapter();
		
		int dp = 10;
		float scale = getResources().getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
	
		int width = getScreenWidth(getContext());
		if(width == 720){
			mGridView.setPadding(px, px, px, px);
			mGridView.setHorizontalSpacing(px);
			mGridView.setVerticalSpacing(px);	
		}else{
			mGridView.setPadding(px, px / 2, px, px / 2);
			mGridView.setHorizontalSpacing(px / 2);
			mGridView.setVerticalSpacing(px / 2);
		}
		mGridView.setAdapter(mAdapter);
		
		mMoveIconView = (ImageView)findViewById(R.id.moveIcon);
		mMoveIconView.setScaleType(ScaleType.FIT_XY);
	}
	
	public void setNumberColumn(int column) {
		Log.d("DEMENTOR - ", "column - " + column);
		mColumnCount = column;
		mGridView.setNumColumns(column);
		
		requestLayout();
	}
	
	public int getScreenWidth(Context ctx) {
		DisplayMetrics display = ctx.getResources().getDisplayMetrics();
		return display.widthPixels;
	}
	
	public int getIconBackgroundColor(){
		return mIconBackgroundColor;
	}
	
	public void setIconBackgroundColor(int color) {
		mIconBackgroundColor = color;
	}
		
	public void setInputListener(InputView.OnInputListener listener) {
		mInputListener = listener;
	}
	
	public Bitmap getIconBitmap(int index){
		return mImageList.get(index);
	}
	
	public int getIconSize(){
		return mIconSize;
	}
	
	public int[] getLocationAtPosition(int position) {
		int x = mIconSize * (position % mColumnCount);
		int y = mIconSize * (position / mColumnCount);
		
		int[] location = {x+mIconLeftPadding, y};
		
		return location;
	}
	
	public void setImageList(ArrayList<Bitmap> imageList) {
		
		for(Bitmap bitmap : mImageList) {
			bitmap.recycle();
			bitmap = null;
		}
		
		mImageList.clear();
		mImageList = imageList;
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mImageList == null)
			return false;
		
		float x = event.getX();
		float y = event.getY();
		int position = -1;
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			position = hitTest(x, y);
			if (position < 0 || position >= mImageList.size())
				return false;
			mMoveIndex = position;
			
			mMoveIconView.setImageBitmap(mImageList.get(position));
			mMoveIconView.setVisibility(View.VISIBLE);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mIconSize, mIconSize);
			params.gravity = Gravity.TOP;
			params.setMargins((int)x-mIconSize/2, (int)y-mIconSize/2, 0, 0);
			mMoveIconView.setLayoutParams(params);
			break;
		case MotionEvent.ACTION_MOVE:
			FrameLayout.LayoutParams moveParams = (FrameLayout.LayoutParams)mMoveIconView.getLayoutParams();
			moveParams.gravity = Gravity.TOP;
			moveParams.setMargins((int)x-mIconSize/2, (int)y-mIconSize/2, 0, 0);
			mMoveIconView.setLayoutParams(moveParams);
			break;
		case MotionEvent.ACTION_UP:
			mMoveIconView.setImageBitmap(null);
			mMoveIconView.setVisibility(View.INVISIBLE);
			
			position = hitTest(x, y);
			if (position < 0 || position >= mImageList.size())
				return false;
			
			if (mInputListener != null && mMoveIndex != position) {
				mInputListener.onInput(mMoveIndex, position);
			}
			break;
		}
		
		return super.dispatchTouchEvent(event);
	}
	
	private int hitTest(float x, float y) {
		if (x < 0 || y < 0)
			return -1;
		
		int xIndex = (int) x/mIconSize;
		int yIndex = (int) y/mIconSize;
		
		return xIndex + (mColumnCount * yIndex);
	}
		
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int size = MeasureSpec.getSize(widthMeasureSpec);
		mIconSize = size / mColumnCount;
		
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private class InputViewAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			
			if (mImageList == null){
				return 0;				
			}
			return mImageList.size();
		}

		@Override
		public Object getItem(int position) {
			return mImageList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			IconImageView iv = (IconImageView)convertView;
			
			if (iv == null) {
				iv = new IconImageView(getContext());
				iv.setIconBackgroundColor(mIconBackgroundColor);
						
				iv.setTag(Integer.valueOf(position));
				iv.setScaleType(ImageView.ScaleType.FIT_XY);
			}
			iv.setImageBitmap(mImageList.get(position));
			
			return iv;
		}
	}
	
	public void onDestroy() {
		
		if (mImageList != null) {
			for (Bitmap image : mImageList) {
				if (image != null) {
					image.recycle();					
					image = null;
				}
			}
			mImageList.clear();
		}
		
		if(mMoveIconView != null)
			mMoveIconView.setImageBitmap(null);
	}
	
	// 인터페이스
	public static interface OnInputListener {
		public void onInput(int moveIndex, int hitIndex);
	}
}
