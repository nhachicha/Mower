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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import dev.nhachicha.demo.R;

/**
 * Dialog to choose between Console or Graphic mode
 * @author Nabil HACHICHA
 *
 */
public class ModeDialogFragment extends DialogFragment{
	private DialogCallback mCallback;
	
	public static interface DialogCallback {
		void startConsole();
		void startGraphic();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_mode_title).setItems(
				R.array.mode_arrays, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index position
						// of the selected item
						switch (which) {
						case 0: {
							mCallback.startConsole();
							break;
						}
						case 1: {
							mCallback.startGraphic();
							break;
						}
						}
						dismiss();
					}
				});
		return builder.create();
	}

	
	  //Hold a reference to the parent Activity so we can report the choice
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    mCallback = (DialogCallback) activity;
	  }
	   
	  @Override
	  public void onDetach() {
	    super.onDetach();
	    mCallback = null;//avoid accidentally leak the Activity instance.
	  
	  }
}
