package com.dementor.bankdemo;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.dementor.bankdemo.DementorAuthController.OnInputResultListener;
import com.dementor.bankdemo.ui.CommonDialog;
import com.dementor.bankdemo.ui.CommonUtil;

public class MainActivity extends Activity implements OnInputResultListener
{
	public static Activity sActivityMain = null;
	private View mMainView;
	private DementorAuthController mController;

	private View mDementorView;
	private int mPosition = 0;

	private int mColumnCount = 5;
	private int mIconCount = 25;
	private boolean mIsUserImage = false;
	private final int RESULT_MOVIE_END = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i("DEMENTOR - ", "MainActivity - onCreate()");
		
		sActivityMain = this;
		
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
		setContentView(R.layout.main);

//		mPosition = getIntent().getIntExtra("position", 0);
		
		mPosition = 13;
		getIntent().putExtra("position", mPosition); 
		mMainView = findViewById(R.id.mainView);
		mMainView.setBackgroundResource(Const.BANK_AUTH[mPosition]);

		int id = getResources().getIdentifier("dementor_y_" + mPosition, "dimen", getPackageName());
		int topMargin = (int) getResources().getDimension(id);

		mDementorView = findViewById(R.id.ll_dementor_layout);
		mDementorView.setWillNotDraw(false);

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mDementorView.getLayoutParams();
		params.topMargin = topMargin;
		mDementorView.setLayoutParams(params);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus)
		{

			if (mController != null) return;

			start();
		}
	}

	private void start()
	{
		Log.d("DEMENTOR - ", "start()");

		ArrayList<Bitmap> randomImages = new ArrayList<Bitmap>();
		ArrayList<Integer> randomList = getIconList();
		// Random icon setting
		for (int i : randomList)
		{
			Log.d("DEMENTOR - ", "i - " + i);
			Bitmap bitmap = CommonUtil.getCircleFromBitmap(BitmapFactory.decodeResource(getResources(),
					getResources().getIdentifier("icon_" + i, "drawable", getPackageName())));
			Log.d("DEMENTOR - ", "bitmap - " + bitmap);
			randomImages.add(bitmap);
		}

		int backgroundColor = Color.rgb(191, 225, 208);
		int highlightColor = Const.COLORS_HIGHLIGHT[mPosition];
		int disableColor = Color.rgb(150, 149, 149);

		if (mController != null)
		{
			Log.d("DEMENTOR - ", "Controller.onDestroy()");
			mController.onDestroy();
		}

		mController = new DementorAuthController(this, mMainView, backgroundColor, highlightColor, disableColor, mColumnCount, this);
		if (mPosition == Const.BANK_INDEX_CHINA)
		{
			mController.setStateText("请输入密码");
		}
		else
		{
			mController.setStateText("비밀번호를 입력하여 주세요");
		}
		mController.setIconList(randomImages);
	}

	private ArrayList<Integer> getPrivacyImage()
	{

		int startIndex = 10000;
		if (mPosition == Const.BANK_INDEX_PAYNOW) startIndex += 25;

		ArrayList<Integer> privacyImgList = new ArrayList<Integer>();
		for (int i = startIndex; i < startIndex + mIconCount; i++)
		{
			privacyImgList.add(i);
		}
		return privacyImgList;
	}

	private ArrayList<Integer> getIconListWithBankIcon(int position)
	{

		ArrayList<Integer> randomIconNumbers = new ArrayList<Integer>();

		int startIndex = (position + 9) * 100;
		int count = Const.CI_ICON_COUNT[position];
		if (count > mIconCount) count = mIconCount;

		for (int i = startIndex; i < startIndex + count; i++)
		{
			randomIconNumbers.add(i);
		}
		return randomIconNumbers;
	}

	private ArrayList<Integer> getIconList()
	{
		Log.d("DEMENTOR - ", "getIconList()");

		ArrayList<Integer> iconList;
		if (mIsUserImage)
		{
			iconList = getPrivacyImage();
		}
		else
		{
			iconList = getIconListWithBankIcon(mPosition);
		}

		// Getting Random key
		while (true)
		{

			if (iconList.size() == mIconCount) break;

			int index = (int) (Math.random() * 155);
			if (iconList.contains(index)) continue;

			iconList.add(index);
		}
		Collections.shuffle(iconList);
		return iconList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("Change");
		menu.add("3x3");
		menu.add("4x4");
		menu.add("5x5");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if (item.getTitle().equals("Change"))
		{
			mIsUserImage = !mIsUserImage;
		}
		else if (item.getTitle().equals("3x3"))
		{
			mColumnCount = 3;
			mIconCount = 9;
		}
		else if (item.getTitle().equals("4x4"))
		{
			mColumnCount = 4;
			mIconCount = 16;
		}
		else if (item.getTitle().equals("5x5"))
		{
			mColumnCount = 5;
			mIconCount = 25;
		}
		start();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onInputResult(boolean isResult)
	{
		String comment = "";
		Intent intent = null;
		if (isResult)
		{
			if (mPosition == Const.BANK_INDEX_DK)
			{
				intent = new Intent(this, MovieActivity.class);
				intent.putExtra("position", mPosition);
				
				startActivityForResult(intent, RESULT_MOVIE_END );
			}
			else
			{
				intent = getIntent().setClass(this, LoginActivity.class);
				
				startActivity(intent);

				this.finish();
			}

			return;
		}
		else
		{
			comment = "비밀번호가 틀립니다.";
		}

		CommonDialog dialog = new CommonDialog(this);
		dialog.show();
		dialog.setTitle("실패");
		dialog.setComment(comment);
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//Log.e("TAG", "onActivityResult - requestCode : " + requestCode + " , resultCode : " + resultCode);
		if(requestCode == RESULT_MOVIE_END)
		{
			if(resultCode == RESULT_OK)
			{
				Intent intent = getIntent().setClass(this, LoginActivity.class);
				
				startActivity(intent);

				MainActivity.this.finish();
			}
				
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Log.i("DEMENTOR - ", "MainActivity - onDestroy()");

		if (mController != null)
		{
			mController.onDestroy();
			mController = null;
		}

		if (mMainView != null)
		{
			mMainView.setBackgroundResource(0);
			mMainView = null;
		}
		mDementorView = null;

		System.gc();
	}
}
