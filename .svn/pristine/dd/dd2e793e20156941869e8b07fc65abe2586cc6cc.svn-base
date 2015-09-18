package com.dementor.bankdemo.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;

public class CommonUtil {
	
	/**
	 * 라이브러리 리소스에서 id값을 찾는다.
	 * @param name
	 * @param type
	 * @param context
	 * @return
	 */
	public static int findLayoutByName(String name, String type, Context context) {
		return context.getResources().getIdentifier(name, type, context.getPackageName());
	}
	
	/**
	 * 이름으로 뷰 찾기
	 * @param name
	 * @param obj
	 * @return
	 */
	public static View findViewByName(String name, Object obj) {
		String id = "id";
		
		View resultView = null;
		if (obj instanceof Activity) {
			Activity act = (Activity) obj;
			resultView = act.findViewById(CommonUtil.findLayoutByName(name, id, act));
		}

		if (obj instanceof View) {
			View view = (View) obj;
			resultView = view.findViewById(CommonUtil.findLayoutByName(name, id, view.getContext()));
		}

		if (obj instanceof Dialog) {
			Dialog dialog = (Dialog) obj;
			resultView = dialog.findViewById(CommonUtil.findLayoutByName(name, id, dialog.getContext()));
		}

		return resultView;
	}
	
	/**
	 * 상태바 높이 값
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	/**
	 * dp -> px 변환
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dpToPixel(Context context, int dp) {
		int dpi = context.getResources().getDisplayMetrics().densityDpi;
		int px = (int)(dp * (dpi / 160f));
		return px;
	}
	
	/**
	 * px -> dp 변환
	 * @param context
	 * @param px
	 * @return
	 */
	public static int pixelToDp(Context context, int px) {
		int dpi = context.getResources().getDisplayMetrics().densityDpi;
		int dp = (int)(px / (dpi / 160f));
		return dp;
	}
	
//	public static int getPixelsFromDP(Context ctx, float dp) {
//		final float scale = ctx.getResources().getDisplayMetrics().density;
//		int px = (int) (dp * scale + 0.5f);
//		return px;
//	}
	
	/**
	 * 디바이스 진동
	 * @param ctx
	 * @param milliseconds
	 */
	public static void vibrate(Context ctx, long milliseconds) {
		Vibrator vib = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}
	
	/**
	 * 스크린 가로
	 * @param ctx
	 * @return
	 */
	public static int getScreenWidth(Context ctx) {
		DisplayMetrics display = ctx.getResources().getDisplayMetrics();
		return display.widthPixels;
	}
	
	/**
	 * 스크린 세로
	 * @param ctx
	 * @return
	 */
	public static int getScreenHeight(Context ctx) {
		DisplayMetrics display = ctx.getResources().getDisplayMetrics();
		return display.heightPixels;
	}
	
	/**
	 * 이미지 알파
	 * @param v
	 * @param alpha
	 */
	@SuppressWarnings("deprecation")
	public static void setImageAlpha(ImageView v, int alpha){
		if(v == null)
			return;
		
		if (Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			v.setAlpha(alpha * 0.01f);
			return;
		}
		v.setAlpha(180);
	}
	
	/**
	 * 이미지 백그라운드
	 * @param v
	 * @param d
	 */
	@SuppressWarnings("deprecation")
	public static void setBackground(View v, Drawable d){
		if(v == null){
			return;
		}
		
		if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
			v.setBackground(d);
			return;
		}
		v.setBackgroundDrawable(d);
	}
	
	/**
	 * 뷰 좌표 getter
	 * @param v
	 * @return
	 */
	public static Point getViewLocation(View v){
		
		if (Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			return new Point((int)v.getX(), (int)v.getY());
		} 
		MarginLayoutParams marginParams = (MarginLayoutParams)v.getLayoutParams();
		return new Point(marginParams.leftMargin, marginParams.topMargin);
	}
	
	/**
	 * 뷰 좌표 setter
	 * @param v
	 * @param x
	 * @param y
	 */
	public static void setViewLocation(View v, float x, float y){
		
		if (Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			v.setX(x);
			v.setY(y);
			return;
		}
		MarginLayoutParams params = (MarginLayoutParams)v.getLayoutParams();
		params.leftMargin = (int)x;
		params.topMargin = (int)y;
		v.setLayoutParams(params);
	}
	
	/**
	 * 비트맵 원형으로 드로잉
	 * @param reSizeBitmap
	 * @return
	 */
	public static Bitmap getCircleFromBitmap(Bitmap reSizeBitmap){
		
		int width = reSizeBitmap.getWidth();
		int height = reSizeBitmap.getHeight();
		
		Bitmap icon = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(icon);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
//        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, width / 3, width / 3, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(reSizeBitmap, rect, rect, paint);
        
        return icon;
	}
}
