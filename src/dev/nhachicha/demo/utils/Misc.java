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


package dev.nhachicha.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;
import dev.nhachicha.demo.MowerApplication;
import dev.nhachicha.demo.R;
import dev.nhachicha.demo.entity.Mower;

/**
 * Useful routines methods 
 * @author Nabil HACHICHA
 *
 */
public class Misc {
	
public static void loadConfig (final Context ctx, final Handler handler) {
		new AsyncTask<Void, Void, Void>() {
			ProgressDialog progress;
			@Override
			protected void onPreExecute() {
				progress = ProgressDialog.show(ctx,
						ctx.getText(R.string.dlg_loading_title),
						ctx.getText(R.string.dlg_loading_msg), true, false);
			}

			@Override
			protected Void doInBackground(Void... params) {
				try {//TODO maybe we could move this to an asynTask
					 
					
					MowerApplication.MOWERS =  new ArrayList<Mower>();
					BufferedReader reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(Constants.GAME_CONF_FILE)));
					
					String matrixDimension = reader.readLine();
					String line, initialPosition, movement = null;
					String[] tmp = null;
					int initPosX, initPosY;
					Mower mower = null;
					
					if (TextUtils.isEmpty(matrixDimension)) {
						//TODO throw a custom exception 
						throw new IllegalArgumentException("Malformed configuration file. (matrix dimensions should not be empty)");
						
					} else {
						tmp = matrixDimension.split(" ");
						if (2 != tmp.length) {
							throw new IllegalArgumentException("Malformed configuration file. (matrix should be 2 int)");
						}
						//FIXME must be an Int within a range 
						MowerApplication.MATRIX_WIDTH = Integer.parseInt(tmp[0]);
						MowerApplication.MATRIX_HEIGHT = Integer.parseInt(tmp[1]);
					}
					//First line, initial position
					//Second line, movements 
					while (null != (line=reader.readLine()) && !TextUtils.isEmpty(line.trim())) {
						if (Constants.REGEXP_INIT_POSITION.matcher(line).find()) {
							initialPosition = line;
							
							line=reader.readLine();
							if (Constants.REGEXP_MVTS.matcher(line).find()) {
								movement = line;
								
								// Parsing 
								tmp = initialPosition.split(" ");
								initPosX = Integer.parseInt(tmp[0]);
								initPosY = Integer.parseInt(tmp[1]);
								mower = new Mower(initPosX,initPosY, tmp[2], movement);
								
								MowerApplication.MOWERS.add(mower);
								
							} else {
								throw new IllegalArgumentException("Malformed configuration file. mouvements are wrong");
							}
							
						} else {
							throw new IllegalArgumentException("Malformed configuration file. initial position are wrong");
						}
					}
					
				} catch (IOException e) {
					Toast.makeText(ctx, "Fatal Error Can't load configuration file from assets", Toast.LENGTH_LONG).show();
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							exit(ctx);
						}
					}, 2000);//Delay the exit so we give the user a chance to read the error message
					
					
				} catch (NumberFormatException e) {
					Toast.makeText(ctx, "Fatal Error Can't load configuration file from assets", Toast.LENGTH_LONG).show();
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							exit(ctx);
						}
					}, 2000);
					
				} catch (IllegalArgumentException e) {
					Toast.makeText(ctx, "Fatal Error Can't load configuration file from assets", Toast.LENGTH_LONG).show();
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							exit(ctx);
						}
					}, 2000);
				    
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				progress.dismiss();
			}

			
			
		}.execute();
		

		  
	}

	public static void exit(Context ctx) {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
