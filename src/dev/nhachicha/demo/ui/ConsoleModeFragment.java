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



import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import dev.nhachicha.demo.MowerApplication;
import dev.nhachicha.demo.R;
import dev.nhachicha.demo.engine.ConsoleEngineImpl;
import dev.nhachicha.demo.entity.Mower;
import dev.nhachicha.demo.utils.Constants;

/**
 * Fragment responsible for rendering the result as a ListView 
 * @author Nabil HACHICHA
 *
 */
public class ConsoleModeFragment extends Fragment {
	public static final String KEY_NB_COLONNE = "dev.nhachicha.demo.ui.SimulationFragment.KEY_NB_COLONNE";
	public static final String KEY_NB_ROW = "ddev.nhachicha.demo.ui.SimulationFragment.KEY_NB_ROW";
	private ProgressBar mProgressBar;
	private int mNbColonne;
	private int mNbRow;
	private ConsoleEngineImpl mEngine;
	private ListView mList;
	private List<String> mResultData = new ArrayList<String>();
	private ArrayAdapter<String> mAdapter;
	
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		
		Bundle args = getArguments();
		if(null != args && args.containsKey(KEY_NB_COLONNE) && args.containsKey(KEY_NB_ROW)) {
			mNbColonne = args.getInt(KEY_NB_COLONNE);
			mNbRow = args.getInt(KEY_NB_ROW);
		}
		return inflater.inflate(R.layout.console_fragment_layout, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		mResultData.clear();
		mList = (ListView) view.findViewById(android.R.id.list);
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mResultData);
		mList.setAdapter(mAdapter);
		mEngine = new ConsoleEngineImpl(mNbColonne, mNbRow);//TODO inject this from a Factory

		mProgressBar.setVisibility(View.VISIBLE);
		asyncTask.execute();
		
	}
	
	AsyncTask<Void, String, Void> asyncTask = new AsyncTask<Void, String, Void>(){

		@Override
		protected Void doInBackground(Void... params) {
			for (Mower mower : MowerApplication.MOWERS) {
				String result = mEngine.run(mower);
				publishProgress(result);
				try {
					Thread.sleep(Constants.MODE_CONSOLE_SIMULATION_DELAY);//simulate speed
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String... progress) {
	         mAdapter.add(progress[0]);
	         mAdapter.notifyDataSetChanged();
	     }

		@Override
	     protected void onPostExecute(Void result) {
	         // hide loading progress
			mProgressBar.setVisibility(View.INVISIBLE);
	     }
		
		
	};
}
