package com.dementor.bankdemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
//import android.view.View.MeasureSpec;

public class IconImageView extends ImageView {
	// 페이나우
	int mIconBackgourndColor = Color.rgb(191, 225, 208);
//	int mIconBackgourndColor = Color.rgb(141, 154, 255);

	public IconImageView(Context context) {
		super(context);
	}
	
	public IconImageView(Context context, AttributeSet attr) {
		super(context, attr);
	}
	
	public void setIconBackgroundColor(int color) {
		mIconBackgourndColor = color;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    int width = MeasureSpec.getSize(widthMeasureSpec);
	    super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
	    );
	    
	    if (this.getBackground() == null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
				this.setBackgroundDrawable(new IconBackgroundDrawable(getMeasuredWidth(), this.getPaddingLeft()));
			else
				this.setBackground(new IconBackgroundDrawable(getMeasuredWidth(), this.getPaddingLeft()));
	    }
	}
	
	private class IconBackgroundDrawable extends Drawable {
		
		int mWidth = 0;
		int mPadding = 0;
		
		public IconBackgroundDrawable(final int width, final int padding) {
			mWidth = width;
			mPadding = padding;
		}

		@Override
		public void draw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setColor( mIconBackgourndColor );
			paint.setAntiAlias(true);
//			canvas.drawCircle(mWidth/2, mWidth/2, mWidth/2 - mPadding*2, paint);
			RectF rectF = new RectF(0, 0, mWidth, mWidth);
			canvas.drawRoundRect(rectF, mWidth/3, mWidth/3 - mPadding*2, paint);
			
			paint.setColor(Color.TRANSPARENT);
			canvas.drawRoundRect(rectF, mWidth/4, mWidth/4 - mPadding*2, paint);
		}

		@Override
		public int getOpacity() {
			return PixelFormat.RGBX_8888;
		}

		@Override
		public void setAlpha(int alpha) {
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
		}
	}
}
