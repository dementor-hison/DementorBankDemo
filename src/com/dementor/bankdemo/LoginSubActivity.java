package com.dementor.bankdemo;

import android.view.View;

public class LoginSubActivity extends AbstractLoginActivity {
	
	private View mLoginSubView;
	
	@Override
	protected void initLayout() {
		
		int position = getIntent().getIntExtra("position", 0);
		mLoginSubView = findViewById(R.id.rl_login_view);
		mLoginSubView.setBackgroundResource(Const.BANK_SUB[position]);
		
		findViewById(R.id.v_login_click_area).setVisibility(View.GONE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(mLoginSubView != null){
			mLoginSubView.setBackgroundResource(0);
			mLoginSubView = null;
		}
		System.gc();
	}
}
