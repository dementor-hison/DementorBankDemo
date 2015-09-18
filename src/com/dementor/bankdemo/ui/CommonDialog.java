package com.dementor.bankdemo.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.dementor.bankdemo.R;

public class CommonDialog extends Dialog implements View.OnClickListener {
	private TextView mTitle;
	private TextView mComment01;
	// private TextView mComment02;

	private TextView mCheckButton;

	CommonDialog.onCloseListener mCloseListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.2f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.dementor_common_dialog);

		mCheckButton = (TextView) findViewById(R.id.dialogBtn);
		mCheckButton.setOnClickListener(this);

		mTitle = (TextView) findViewById(R.id.commonDialogTitle);
		mComment01 = (TextView) findViewById(R.id.commonDialogComent);
	}

	public CommonDialog(Context context) {
		// Dialog 배경을 투명 처리 해준다.
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}

	public void setCloseListener(CommonDialog.onCloseListener listener) {
		mCloseListener = listener;
	}

	public void setTitle(String str) {
		mTitle.setText(str);
	}

	public void setComment(String str) {
		mComment01.setText(str);
	}

	@Override
	public void onClick(View arg0) {
		this.cancel();

		if (mCloseListener != null)
			mCloseListener.onClose();
	}

	static public interface onCloseListener {
		public void onClose();
	}
}
