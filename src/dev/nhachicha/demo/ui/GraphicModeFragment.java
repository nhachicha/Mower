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

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import dev.nhachicha.demo.MowerApplication;
import dev.nhachicha.demo.engine.GraphicEngineImpl;
import dev.nhachicha.demo.utils.Constants;

/**
 * Fragment responsible for rendering the result as a GridView 
 * @author Nabil HACHICHA
 *
 */
public class GraphicModeFragment extends Fragment {
	public static final String KEY_NB_COLONNE = "dev.nhachicha.demo.ui.DemoGrigFragment.KEY_NB_COLONNE";
	public static final String KEY_NB_ROW = "dev.nhachicha.demo.ui.DemoGrigFragment.KEY_NB_ROW";

	private GridView mGrid;
	private int mNbColonne;
	private int mNbRow;
	private GridAdapter mAdapter;
	GraphicEngineImpl mEngine;
	volatile int currentMower = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstance) {
		Bundle args = getArguments();
		if (null != args && args.containsKey(KEY_NB_COLONNE)
				&& args.containsKey(KEY_NB_ROW)) {
			mNbColonne  = args.getInt(KEY_NB_COLONNE);
			mNbRow  = args.getInt(KEY_NB_ROW);
			
			mGrid = new GridView(getActivity());

			LayoutParams params = new ViewGroup.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			mGrid.setLayoutParams(params);
			mGrid.setNumColumns(mNbColonne + 1);
			mGrid.setColumnWidth((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 48, getActivity()
							.getResources().getDisplayMetrics()));
			mGrid.setVerticalSpacing(0);
			mGrid.setHorizontalSpacing(0);
			mGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			mGrid.setGravity(Gravity.CENTER);
			mGrid.setPadding(0, 0, 0, 0);
			mGrid.setBackgroundColor(getResources().getColor(
					android.R.color.white));

		} else {
			// Hmm. we shouldn't be here, are you in a parallel universe ?
		}
		
		mAdapter = new GridAdapter(getActivity(), mNbColonne+1, mNbRow+1);
		mGrid.setAdapter(mAdapter);
		return mGrid;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mEngine =  new GraphicEngineImpl(mNbColonne, mNbRow, mAdapter);
		Handler handler = new Handler();
		
		for (int i=0; i<MowerApplication.MOWERS.size(); i++) {
			
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					mAdapter.clearGrid();
					mEngine.run(MowerApplication.MOWERS.get(currentMower++));
					mAdapter.notifyDataSetChanged();
				}
			}, i*Constants.MODE_GRAPHIC_SIMULATION_DELAY);
		}
	}
	
	//TODO Display compas to knwo where is north

}
