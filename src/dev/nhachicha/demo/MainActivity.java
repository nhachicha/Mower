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


package dev.nhachicha.demo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import dev.nhachicha.demo.ui.ConsoleModeFragment;
import dev.nhachicha.demo.ui.GraphicModeFragment;
import dev.nhachicha.demo.ui.ModeDialogFragment;
import dev.nhachicha.demo.utils.Misc;

/**
 * Activity to handle result rendering (two modes available console & graphic)
 * @author Nabil HACHICHA
 *
 */ 
public class MainActivity extends FragmentActivity implements ModeDialogFragment.DialogCallback {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ModeDialogFragment dialog = new ModeDialogFragment ();
		dialog.show(getSupportFragmentManager(), "dialog");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_exit:
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onResume() {
		//Load the list of Mower from the assets
		//Destroy the loaded list, we don't want to reuse it in an inconsistency state
		super.onResume();//
		Misc.loadConfig(this, getWindow().getDecorView().getHandler());//use activity default handler
	}

	@Override
	public void startConsole() {
		Bundle args = new Bundle ();
		args.putInt(ConsoleModeFragment.KEY_NB_COLONNE, MowerApplication.MATRIX_WIDTH);
		args.putInt(ConsoleModeFragment.KEY_NB_ROW, MowerApplication.MATRIX_HEIGHT);
		
		Fragment fragment = new ConsoleModeFragment();
		fragment.setArguments(args);
		
		getSupportFragmentManager().beginTransaction().replace(R.id.fragGame, fragment).commit();
	}


	@Override
	public void startGraphic() {
		Bundle args = new Bundle ();
		args.putInt(GraphicModeFragment.KEY_NB_COLONNE, MowerApplication.MATRIX_WIDTH);
		args.putInt(GraphicModeFragment.KEY_NB_ROW, MowerApplication.MATRIX_HEIGHT);
		
		Fragment fragment = new GraphicModeFragment();
		fragment.setArguments(args);
		
		getSupportFragmentManager().beginTransaction().replace(R.id.fragGame, fragment).commit();
		
	}
	
}
