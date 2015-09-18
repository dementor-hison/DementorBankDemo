package com.dementor.bankdemo.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.dementor.bankdemo.IconListBuilder;
import com.dementor.bankdemo.R;

public class InputView extends FrameLayout {  
	
	private static final int SECURITY_LEVEL_1 = 1;
	private static final int SECURITY_LEVEL_2 = 2;
	private static final int SECURITY_LEVEL_3 = 3;
	
	private ArrayList<Bitmap> mImageList = new ArrayList<Bitmap>();
	private IconListBuilder<Bitmap> mImageListMini= new IconListBuilder<Bitmap>();
	private GridView mGridView, mGridViewMini;
	
	private InputView.OnInputListener mInputListener;
	
	private int mColumnCount = 5;
	private int mIconSize = 0;
	private int mIconLeftPadding = 0;
	private int mMoveIndex;
	private int mLevel = SECURITY_LEVEL_1;
	private InputViewAdapter mAdapter;
	private MiniViewAdapter mMiniAdapter;
	
	private ImageView mMoveIconView;
	
	private int mIconBackgroundColor;
	private LinearLayout m_llGridController;
	
	
	public InputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.dementor_input_view, this);
		
		m_llGridController = (LinearLayout)findViewById(R.id.llGridController);
		
		findViewById(R.id.ibArrowUp).setOnClickListener(mClickListener);
		findViewById(R.id.ibArrowLeft).setOnClickListener(mClickListener);
		findViewById(R.id.ibArrowOK).setOnClickListener(mClickListener);
		findViewById(R.id.ibArrowRight).setOnClickListener(mClickListener);
		findViewById(R.id.ibArrowDown).setOnClickListener(mClickListener);
		
		//GridView Start
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
		//GridView End

		mMoveIconView = (ImageView)findViewById(R.id.moveIcon);
		mMoveIconView.setScaleType(ScaleType.FIT_XY);
		
		//GridView Start
		mGridViewMini = (GridView)findViewById(R.id.gridViewMini);
		mMiniAdapter = new MiniViewAdapter();
		
		if(width == 720){
			mGridViewMini.setPadding(px, px, px, px);
			mGridViewMini.setHorizontalSpacing(px);
			mGridViewMini.setVerticalSpacing(px);	
		}else{
			mGridViewMini.setPadding(px, px / 2, px, px / 2);
			mGridViewMini.setHorizontalSpacing(px / 2);
			mGridViewMini.setVerticalSpacing(px / 2);
		}
		mGridViewMini.setAdapter(mMiniAdapter);
		//GridView End
		
		//hison - 2, 3단계 선택창 추가 
		showSecurityLevelPopup();

	}
	
	public void setNumberColumn(int column) {
		Log.d("DEMENTOR - ", "column - " + column);
		mColumnCount = column;
		mGridView.setNumColumns(column);
		mGridViewMini.setNumColumns(column);
		requestLayout();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
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
		
		mImageListMini.clear();
		mImageListMini.addAll(imageList);
		mImageListMini.setColumnCount(mColumnCount);
		mImageListMini.setRowCount( ( (imageList.size() - 1) / mColumnCount ) + 1);
		
		if(mLevel > SECURITY_LEVEL_1)
			mMiniAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mImageList == null)
			return false;
		
		//hison - 보안레벨이 2이상이면 터치로 이동하지 않겠다. 
		if(mLevel > SECURITY_LEVEL_1)
		{
			m_llGridController.setVisibility(GridLayout.VISIBLE);
		}
		else
		{
			m_llGridController.setVisibility(GridLayout.GONE);

			if( moveTouchIcon(event) == false)
			{
				return false;
			}
		}
		
		return super.dispatchTouchEvent(event);
	}
	
	private boolean moveTouchIcon(MotionEvent event) {
		// TODO Auto-generated method stub
		boolean result = true;
		
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
		return result;
	}

	private int hitTest(float x, float y) {
		if (x < 0 || y < 0)
			return -1;
		
		int xIndex = (int) x/mIconSize;
		int yIndex = (int) y/mIconSize;
		
		return xIndex + (mColumnCount * yIndex);
	}
	
	
	private void showSecurityLevelPopup() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle("보안레벨")        
			.setPositiveButton("1단계", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mLevel = SECURITY_LEVEL_1;					
					m_llGridController.setVisibility(GridLayout.GONE);
					mGridViewMini.setVisibility(GONE);
				}
			})
			.setNeutralButton("2단계", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mLevel = SECURITY_LEVEL_2;
					mGridViewMini.setVisibility(VISIBLE);
					m_llGridController.setVisibility(GridLayout.VISIBLE);
				}
			})
			.setNegativeButton("3단계", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mLevel = SECURITY_LEVEL_3;
					m_llGridController.setVisibility(GridLayout.VISIBLE);
					mGridViewMini.setVisibility(GONE);
				}
			});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private boolean isMoved = false;
	View.OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ibArrowOK:
				mImageListMini.clear();
				mImageListMini.addAll(mImageList);
				mMiniAdapter.notifyDataSetChanged();
				
				if(isMoved == false)
				{
					mInputListener.onInput(1, 1000);	
					break;
				}
				
				isMoved = false;
				mInputListener.onInput(1, 1);
				break;
			case R.id.ibArrowLeft:
				isMoved = true;
				mImageListMini.moveColumn(-1);
				break;
			case R.id.ibArrowRight:
				isMoved = true;
				mImageListMini.moveColumn(1);
				break;
			case R.id.ibArrowUp:
				isMoved = true;
				mImageListMini.moveRow(-1);
				break;
			case R.id.ibArrowDown:
				isMoved = true;
				mImageListMini.moveRow(1);
				break;
			default:
				break;
			}
			
			mMiniAdapter.notifyDataSetChanged();
		}
	};
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int size = MeasureSpec.getSize(widthMeasureSpec);
		mIconSize = size / mColumnCount;
		
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	private class MiniViewAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			
			if (mImageListMini == null){
				return 0;				
			}
			return mImageListMini.size();
		}

		@Override
		public Object getItem(int position) {
			return mImageListMini.get(position);
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
				iv.setScaleType(ImageView.ScaleType.FIT_XY);
				iv.setPadding(100, 100, 0, 0);

				iv.setTag(Integer.valueOf(position));
			}
			
			iv.setImageBitmap(mImageListMini.get(position));
			
			return iv;
		}
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
				iv.setScaleType(ImageView.ScaleType.FIT_XY);

				iv.setTag(Integer.valueOf(position));
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
		
		if (mImageListMini != null) {
			for (Bitmap image : mImageListMini) {
				if (image != null) {
					image.recycle();					
					image = null;
				}
			}
			mImageListMini.clear();
		}
		
		if(mMoveIconView != null)
			mMoveIconView.setImageBitmap(null);
	}
	
	// 인터페이스
	public static interface OnInputListener {
		public void onInput(int moveIndex, int hitIndex);
	}
}
