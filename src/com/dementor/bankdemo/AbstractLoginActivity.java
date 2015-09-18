package com.dementor.bankdemo;

import android.app.Activity;
import android.os.Bundle;

public abstract class AbstractLoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		
		setContentView(R.layout.login);
		initLayout(); 
	}
	
	protected abstract void initLayout();
}
