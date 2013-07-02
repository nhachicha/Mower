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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import dev.nhachicha.demo.entity.Mower;
import dev.nhachicha.demo.utils.Constants;

/**
 * This is a singleton, hence all the init should be here
 * @author Nabil HACHICHA
 *
 */
public class MowerApplication extends Application {
	public static int MATRIX_WIDTH = -1;
	public static int MATRIX_HEIGHT = -1;
	public static ArrayList<Mower> MOWERS = new ArrayList<Mower>();
	
	@Override
	public void onCreate() {
		super.onCreate();
		//loadConfig();
	}
	
	private void loadConfig () {//TODO maybe move this in an AsynTask or a service
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(Constants.GAME_CONF_FILE)));
			
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
				MATRIX_WIDTH = Integer.parseInt(tmp[0]);
				MATRIX_HEIGHT = Integer.parseInt(tmp[1]);
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
						
						MOWERS.add(mower);
						
					} else {
						throw new IllegalArgumentException("Malformed configuration file. mouvements are wrong");
					}
					
				} else {
					throw new IllegalArgumentException("Malformed configuration file. initial position are wrong");
				}
			}
			
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "Fatal Error Can't load configuration file from assets", Toast.LENGTH_LONG).show();
			exit();
			
		} catch (NumberFormatException e) {
			Toast.makeText(getApplicationContext(), "Fatal Error Can't load configuration file from assets", Toast.LENGTH_LONG).show();
			exit();
			
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(), "Fatal Error Can't load configuration file from assets", Toast.LENGTH_LONG).show();
			exit();
		    
		} 
	}

	private void exit() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
