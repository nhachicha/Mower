/*
 * Copyright 2013 Nabil HACHICHA

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package dev.nhachicha.demo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import dev.nhachicha.demo.R;

/**
 * Adapter for our GridView
 * @author Nabil HACHICHA
 *
 */
public class GridAdapter extends BaseAdapter {
	private int mN, mM;
	private Cell[] mMatrix;
	private LayoutInflater mInflater;
	
	public GridAdapter(Context context, int n, int m) {
		mN = n;
		mM = m;
		mMatrix = new Cell[n * m];
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// init grid, this is O(N) time complexity
		clearGrid();
	}
	
	public void clearGrid () {
		for (int i = 0; i < mN * mM; i++) {
			mMatrix[i] = new Cell(false, null);
		}
	}
	
	@Override
	public int getCount() {
		return mN * mM;
	}

	@Override
	public Cell getItem(int i) {
		return mMatrix[i];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.cell, parent, false);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.imgCell);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		Cell cell = getItem(position);
		if (cell.isMowed()) {
			// holder.img.setImageResource(R.drawable.mower_n);
			holder.img.setBackgroundResource(R.drawable.grass_unselected);
			if (null != cell.getOrientation()) {
				switch (cell.getOrientation()) {
				case NORTH: {//
					holder.img.setImageResource(R.drawable.mower_n);
					break;
				}
				case EAST: {//
					holder.img.setImageResource(R.drawable.mower_e);
					break;
				}
				case SOUTH: {//
					holder.img.setImageResource(R.drawable.mower_s);
					break;
				}
				case WEST: {// ?
					holder.img.setImageResource(R.drawable.mower_w);
					break;
				}
				}
			}
		} else {
			holder.img.setBackgroundResource(R.drawable.grass_selected);
			holder.img.setImageResource(android.R.color.transparent);
		}
		return convertView;
	}

	class ViewHolder {
		public ImageView img;
	}
}
