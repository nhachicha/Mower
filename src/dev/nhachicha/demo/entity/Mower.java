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

package dev.nhachicha.demo.entity;

import java.io.Serializable;

import android.graphics.Point;
import dev.nhachicha.demo.engine.Orientation;

/**
 * Define a mower, with it's properties  
 * @author Nabil HACHICHA
 *
 */
public class Mower implements Serializable {// TODO make it Parcelable ?
	private static final long serialVersionUID = 1L;
	public Point position;//No getter&setter to optimize access 
	public String movement;
	public Orientation orientation;

	public Mower() {
	}
	
	public Mower(int intialPositionX, int intialPositionY, String bearing, String movement) {
		this.position = new Point(intialPositionX, intialPositionY);
		this.movement = movement;

		if (bearing.equals("N")) {
			orientation = Orientation.NORTH;

		} else if (bearing.equals("E")) {
			orientation = Orientation.EAST;

		} else if (bearing.equals("W")) {
			orientation = Orientation.WEST;

		} else if (bearing.equals("S")) {
			orientation = Orientation.SOUTH;
		}
	}
	
	@Override
	public String toString() {
		return "Mower [position=" + position + ", orientation=" + orientation
				+ "]";
	}
}
