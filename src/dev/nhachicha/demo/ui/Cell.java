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

import dev.nhachicha.demo.engine.Orientation;


/**
 * Represent a cell within the GridfView
 * @author Nabil HACHICHA
 *
 */
public class Cell {
	private boolean isMowed = false;
	private Orientation orientation = null;//null if the mower isn't in this cell
	
	public Cell(boolean _isSelected, Orientation _orientation) {
		isMowed = _isSelected;
		orientation = _orientation;
	}

	//Getter/Setters
	
	public boolean isMowed() {
		return isMowed;
	}

	public void setMowed(boolean isMowed) {
		this.isMowed = isMowed;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
}
