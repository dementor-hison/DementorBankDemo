package com.dementor.bankdemo;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends AbstractLoginActivity {
	
	private View mLoginView;
	
	@Override
	protected void initLayout() {
		
		final Intent intent = getIntent(); 
		int position = intent.getIntExtra("position", 0);
		
		mLoginView = findViewById(R.id.rl_login_view);
		mLoginView.setBackgroundResource(Const.BANK_MAIN[position]);
		
		if(Const.BANK_SUB[position] == 0)
			return;
		
		View v = findViewById(R.id.v_login_click_area);
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				intent.setClass(LoginActivity.this, LoginSubActivity.class);
				startActivity(intent);
			}
		});		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(mLoginView != null){
			mLoginView.setBackgroundResource(0);
			mLoginView = null;
		}
		System.gc();
	}
}
