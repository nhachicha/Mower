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

package dev.nhachicha.demo.engine;

import dev.nhachicha.demo.entity.Mower;
import dev.nhachicha.demo.ui.Cell;
import dev.nhachicha.demo.ui.GridAdapter;

/**
 * Implement an Engine for Graphic mode
 * @author Nabil HACHICHA
 *
 */
public class GraphicEngineImpl implements EngineInterface<Void>{

	private int n, m;
	Mower mower;
	
	public final static int _NORTH = 0;
	public final static int _EAST_1 = 1;
	public final static int _EAST_2 = -3;

	public final static int _SOUTH_1 = 2;
	public final static int _SOUTH_2 = -2;

	public final static int _WEST_1 = 3;
	public final static int _WEST_2 = -1;

	private GridAdapter mAdapter;
	
	public GraphicEngineImpl(int _n, int _m,  GridAdapter adapter) {
		n = _n+1;
		m = _m+1;
		mAdapter = adapter;
	}
	
	@Override
	public Void run(Mower _mower) {
		mower = _mower;
		//initial position
		mAdapter.getItem((n-1-mower.position.y)*n + mower.position.x).setMowed(true);
		char[] movement = mower.movement.toCharArray();
		char currentChar;
		for (int i=0; i<movement.length; i++) {
			currentChar = movement[i];
			
			if (currentChar == 'D') {
				turnRight();
				
			} else if (currentChar == 'G') {
				turnLeft();
				
			} else if (currentChar == 'A') {
				moveForward();
				
			} else {
				throw new IllegalArgumentException("No such movement. " + currentChar);
			}
		}
		
		//last position
		Cell cell = mAdapter.getItem((n-1-mower.position.y)*n + mower.position.x);
		cell.setMowed(true);
		cell.setOrientation(mower.orientation);
		return null;
	}
	
	void turnRight() {
		switch (mower.orientation) {
		case NORTH: {
			mower.orientation = (Orientation.EAST);
			break;
		}
		case EAST: {
			mower.orientation = Orientation.SOUTH;
			break;
		}
		case SOUTH: {
			mower.orientation = Orientation.WEST;
			break;
		}
		case WEST: {
			mower.orientation = Orientation.NORTH;
			break;
		}
		}
	}
	
	void turnLeft() {
		switch (mower.orientation) {
		case NORTH: {
			mower.orientation = Orientation.WEST;
			break;
		}
		case EAST: {
			mower.orientation = Orientation.NORTH;
			break;
		}
		case SOUTH: {
			mower.orientation = Orientation.EAST;
			break;
		}
		case WEST: {
			mower.orientation = Orientation.SOUTH;
			break;
		}
		}
	}

	void moveForward() {
		switch (mower.orientation) {
		case NORTH: {
			if ((mower.position.y+1)<m) {
				mower.position.y++;
			}
			break;
		}
		case EAST: {
			if ((mower.position.x+1)<n) {
				mower.position.x++;
			}
			break;
		}
		case SOUTH: {
			if ((mower.position.y-1)>=0) {
				mower.position.y--;
			}
			break;
		}
		case WEST: {
			if ((mower.position.x-1)>=0) {
				mower.position.x--;
			}
			break;
		}
		}
		mAdapter.getItem((n-1-mower.position.y)*n + mower.position.x).setMowed(true);
	}
}
