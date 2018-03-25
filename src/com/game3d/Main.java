package com.game3d;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.game3d.util.Commands;
import com.game3d.util.Config;
import com.game3d.util.Log;

public class Main {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());
	//static LogRecord lr = new LogRecord(Level.ALL, "Log Record");
	
	public static void main(String[] args) {
		//Save old log
		Log.saveLog();
		//Make files
		makeFiles();
		//Add config values if none exist
		Config.add();
		//Store old logs
		Log.saveLog();
		//Set logging format
		Log.configLog(Level.parse(Config.get(Config.logFile, "level")), 
		   Log.format(Boolean.parseBoolean(Config.get(Config.logFile, "showTime")), 
				      Boolean.parseBoolean(Config.get(Config.logFile, "showClass")), 
				      Boolean.parseBoolean(Config.get(Config.logFile, "showSource")), 
				      Boolean.parseBoolean(Config.get(Config.logFile, "showLevel"))));
		//Add commands used while in app
		addCommands();
		//Startup game
		Game.run();
	}
	
	private static void addCommands() {
		//Add help command to list all commands
		Commands.add("help", "help", "Displays all commands");
		LOG.fine("Help command added");
		
		LOG.info("Commands loaded");
	}
	
	private static void makeFiles() {
		try {
			//Create folders, should they not exist
	        Config.folder.mkdirs();
	        Log.folder.mkdirs();
	        
	        //Create config files, should they not exist
			Config.displayFile.createNewFile();
			Config.logFile.createNewFile();
			Config.keysFile.createNewFile();
			
			//Create log file, should it not exist
			Log.file.createNewFile();
		} catch (IOException e) {
			LOG.severe("I/O Exception encountered. Full stack trace:\n" + e.toString() + "\n\tat " +
					Arrays.asList(e.getStackTrace()).stream().map(Objects::toString).collect(Collectors.joining("\n\tat ")));
		}
	}
}
