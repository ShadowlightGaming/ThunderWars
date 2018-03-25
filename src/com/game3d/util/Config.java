package com.game3d.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;

public class Config {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());
	
	public static File folder = new File("configs");
	public static File displayFile = new File(folder + "/display.settings");
	public static File logFile = new File(folder + "/log.settings");
	public static File keysFile = new File(folder + "/keybinds.settings");
	
	/**
	 * To add a new config file you must:
	 * 1) Make a new File variable
	 * 2) Add .createNewFile to Main.makeFiles();
	 * 3) Load the file
	 * 4) Use .putIfAbsent to add values
	 * 5) Store the file
	 * 6) Add config.clear();
	 */
	
	public static Properties config = new Properties();
	
	//Add config properties
	public static void add() {
        try {
        	//Get Screen Size
        	Toolkit tk = Toolkit.getDefaultToolkit();
        	Dimension d = tk.getScreenSize();
        	
			/*******
			 * Display Config File
			 *******/
			config.load(new FileInputStream(displayFile));
			
			//Add configs
			config.putIfAbsent("framerate", "120");
			config.putIfAbsent("screenWidth", "" + d.width);
			config.putIfAbsent("screenHeight", "" + d.height);
			config.putIfAbsent("antianliasing", "16");
			config.putIfAbsent("vsync", "true");
			config.putIfAbsent("fullscreen", "true");
			config.putIfAbsent("showfps", "true");
			
			//Save them to file
			config.store(new FileOutputStream(displayFile), "This config controls the video settings. If you don't know what you are doing, DO NOT EDIT!");
			
			//Close out of the config
			config.clear();
			
			/*******
			 * Log Config File
			 *******/
			config.load(new FileInputStream(logFile));
			
			//Add configs
			config.putIfAbsent("keepLogs", "5");
			config.putIfAbsent("level", "ALL");
			config.putIfAbsent("showTime", "true");
			config.putIfAbsent("showClass", "true");
			config.putIfAbsent("showSource", "true");
			config.putIfAbsent("showLevel", "true");
			
			//Save them to file
			config.store(new FileOutputStream(logFile), "This config edits things such as log output appearance");
			
			//Close out of the config
			config.clear();
			
			/*******
			 * Key Bindings File
			 *******/
			config.load(new FileInputStream(keysFile));
			
			//Add configs
			config.putIfAbsent("Up", KeyInput.KEY_W + "");
			config.putIfAbsent("Down", KeyInput.KEY_S + "");
			config.putIfAbsent("Left", KeyInput.KEY_A + "");
			config.putIfAbsent("Right", KeyInput.KEY_D + "");
			config.putIfAbsent("Sprint", KeyInput.KEY_LSHIFT + "");
			config.putIfAbsent("Crouch", KeyInput.KEY_LCONTROL + "");
			config.putIfAbsent("Jump", KeyInput.KEY_SPACE + "");
			config.putIfAbsent("Shoot", MouseInput.BUTTON_LEFT + "");
			
			//Save them to file
			config.store(new FileOutputStream(keysFile), "This config dictates the keybinds");
			
			//Close out of the config
			config.clear();
        } catch(SecurityException e) {
        	LOG.severe("Security Exception encountered. Full stack trace:\n" + e.toString() + "\n\tat " +
					Arrays.asList(e.getStackTrace()).stream().map(Objects::toString).collect(Collectors.joining("\n\tat ")));
		} catch (IOException e) {
			LOG.severe("I/O Exception encountered. Full stack trace:\n" + e.toString() + "\n\tat " +
					Arrays.asList(e.getStackTrace()).stream().map(Objects::toString).collect(Collectors.joining("\n\tat ")));
		}
	}
	
	//Get config property
	public static String get(File file, String value) {
		String string = "";
		try {
			//Load config file
			config.load(new FileInputStream(file));
			
			//Test if config contains anything
			if(!config.isEmpty()) {
				//Test if config contains value to be returned
				if(!(config.contains(value))) {
					//Get property value
					string = config.getProperty(value);
					//Can't return a null string
					if(string == null) {
						string = "";
					}
				}
			}
		} catch(IOException e) {
			LOG.severe("I/O Exception encountered. Full stack trace:\n" + e.toString() + "\n\tat " +
					Arrays.asList(e.getStackTrace()).stream().map(Objects::toString).collect(Collectors.joining("\n\tat ")));
		}
		//Close out of the config
		config.clear();
		//Return string
		return string;
	}
}
