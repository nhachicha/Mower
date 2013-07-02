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

import java.util.regex.Pattern;
/**
 * Constants goes here
 * @author Nabil HACHICHA
 *
 */
public class Constants {
	public final static String GAME_CONF_FILE = "game.txt";
	public final static String REGEXP_WORDS = "\\S+";//word separated with space
	public final static Pattern REGEXP_INIT_POSITION = Pattern.compile("^(\\d\\ \\d\\ [NEWS]){1}$");
	public final static Pattern REGEXP_MVTS = Pattern.compile("^([GDA]){1,}$");
	public final static int MODE_CONSOLE_SIMULATION_DELAY = 500;//ms
	public final static int MODE_GRAPHIC_SIMULATION_DELAY = 2000;//ms
	
	
}
