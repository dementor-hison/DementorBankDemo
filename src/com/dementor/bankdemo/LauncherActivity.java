package com.dementor.bankdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class LauncherActivity extends Activity implements OnItemClickListener {
	
	private GridView mGridView;
	
	private String mIconNames[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		
		Log.d("DEMENTOR - ", "width " + getResources().getDisplayMetrics().widthPixels + ", height - " + 
				getResources().getDisplayMetrics().heightPixels + ", " + getResources().getDisplayMetrics().densityDpi);
		
		mIconNames = getResources().getStringArray(R.array.names);
		
		mGridView = (GridView)findViewById(R.id.gv_bank_view);
		mGridView.setNumColumns(4);
		mGridView.setAdapter(new GridAdapter());
		mGridView.setOnItemClickListener(this);
	}
	
	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Const.BANK_ICON.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null){
				convertView = LayoutInflater.from(LauncherActivity.this).inflate(R.layout.launcher_item, null);
			}
			
			ImageView icon = (ImageView)convertView.findViewById(R.id.iv_bank_demo_icon);
			icon.setImageResource(Const.BANK_ICON[position]);
			TextView name = (TextView)convertView.findViewById(R.id.tv_bank_demo_name);
			name.setText(mIconNames[position]);
			return convertView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Intent shortcutIntent = new Intent("noAction");
	//	shortcutIntent.setData();
		shortcutIntent.putExtra("position", position);
		shortcutIntent.setClassName(LauncherActivity.this, IntroActivity.class.getName());
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		Intent addintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		addintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		addintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, mIconNames[position] + "\n그래픽인증");
		addintent.putExtra("duplicate", false);
		Parcelable iconResource = Intent.ShortcutIconResource.fromContext(LauncherActivity.this, Const.BANK_ICON[position]);
		addintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
		sendBroadcast(addintent);
		finish();
	}
}
