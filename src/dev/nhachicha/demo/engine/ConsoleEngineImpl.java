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

/**
 * Implement an Engine for Console mode
 * @author Nabil HACHICHA
 *
 */
public class ConsoleEngineImpl implements EngineInterface<String>{

	private int n, m;
	Mower mower;
	
	public final static int _NORTH = 0;
	public final static int _EAST_1 = 1;
	public final static int _EAST_2 = -3;

	public final static int _SOUTH_1 = 2;
	public final static int _SOUTH_2 = -2;

	public final static int _WEST_1 = 3;
	public final static int _WEST_2 = -1;

	
	public ConsoleEngineImpl(int _n, int _m) {//init Matrix
		n = _n+1;
		m = _m+1;
	}
	
	@Override
	public String run(Mower _mower) {
		mower = _mower;
		char[] movement = mower.movement.toCharArray();
		
		for (int i=0; i<movement.length; i++) {
			if (movement[i] == 'D') {
				turnRight();
				
			} else if (movement[i] == 'G') {
				turnLeft();
				
			} else if (movement[i] == 'A') {
				moveForward();
				
			} else {
				System.err.println("No such movement.");
			}
		}
		return getPosition();
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
	}

	String getPosition() {
		return new StringBuilder("X: ").append(mower.position.x).append(" Y: ")
				.append(mower.position.y).append(" ").append(mower.orientation)
				.toString();
	}
	
	

}
