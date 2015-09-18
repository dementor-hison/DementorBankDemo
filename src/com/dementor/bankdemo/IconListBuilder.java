package com.dementor.bankdemo;

import java.util.ArrayList;
import java.util.Collections;

public class IconListBuilder<E> extends ArrayList<E>{

	private int mColumnCount = 0;

	private int mRowCount = 0;
	
	public IconListBuilder(){}
	
	public IconListBuilder(ArrayList<E> list, int column, int row)
	{
		addAll(list);
		
		mColumnCount = column;
		mRowCount = row;
	}
	
	public int getColumnCount() {
		return mColumnCount;
	}

	public void setColumnCount(int columnCount) {
		this.mColumnCount = columnCount;
	}

	public int getRowCount() {
		
		if(mRowCount <= 0)
		{
			if(mColumnCount != 0 && this.size() > 0)
			{
				mRowCount = ((this.size() - 1) / mColumnCount) + 1;
			}
			else
			{
				return 0;
			}
		}
		
		return mRowCount;
	}

	public void setRowCount(int rowCount) {
		this.mRowCount = rowCount;
	}

		
	public ArrayList<E> moveColumn(int position)
	{
		//Log.e("TAG", "position :" + position);
		
		ArrayList<E> oldData = new ArrayList<E>();
		
		oldData.addAll(this);
		
		for (int i = 0; i < this.size(); i++) 
		{
			int addIndex = i + position;
			
			int currentRow = (i / mColumnCount) + 1;
			
			int rangeMin = mColumnCount * (currentRow - 1);
			
			int rangeMax = (mColumnCount * currentRow) - 1;
			
			addIndex =  addIndex < rangeMin ? addIndex + mColumnCount : addIndex > rangeMax ? addIndex - mColumnCount : addIndex;

			//Log.e("TAG","rangeMin : " + rangeMin + " ,rangeMax : " + rangeMax + ", addIndex : " + addIndex + " , i =" + i + " , size : " + this.size());
			
			oldData.set(addIndex, get(i));
		}

		clear();
		
		addAll(oldData);
		
		oldData.clear();
		
		oldData = null;
		
		return this;
	}
	
	public ArrayList<E> moveRow(int position)
	{
		int moveCount = position * mRowCount;
		
		Collections.rotate(this, moveCount);
		
		return this;
	}
	
	
}
