package com.game3d.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Log {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());
	public static File folder = new File("logs/");
	public static File file = new File(folder + "/latest.log");
	
	public static void configLog(Level level, Formatter format) {
		try {
			FileHandler fh = new FileHandler(file.getAbsolutePath());
			fh.setFormatter(format);
			
			ConsoleHandler ch = new ConsoleHandler();
			ch.setFormatter(format);
			
			fh.setLevel(level);
			ch.setLevel(level);
			LOG.setLevel(level);
			
			LOG.setUseParentHandlers(false);
			LOG.addHandler(fh);
			LOG.addHandler(ch);
			
			/**
			 * JME Log
			 **/
			Logger logger = Logger.getLogger(java.util.logging.Logger.class.getName());
			logger.setUseParentHandlers(false);
			logger.addHandler(ch);
			
		} catch(IOException e) {
			LOG.severe("I/O Exception encountered. Full stack trace:\n" + e.toString() + "\n\tat " +
					Arrays.asList(e.getStackTrace()).stream().map(Objects::toString).collect(Collectors.joining("\n\tat ")));
		}
	}
	
	public static Formatter format(boolean time, boolean showClass, boolean source, boolean level) {
		Formatter fm = new Formatter() {
			public String format(LogRecord record) {
				String string = "";
				
				Date date = new Date(record.getMillis());
				DateFormat timeStamp = new SimpleDateFormat("HH:mm:ss");
				
				if(time == true) string += "[" + timeStamp.format(date) + "]";
				if(showClass == true) {
					string += "[" + record.getSourceClassName().substring(11);
					if(source == true) string += ":" + record.getSourceMethodName();
					string += "] ";
				} else
				if(source == true) string += "[" + record.getSourceMethodName() + "] ";
				if(level == true) string += record.getLevel() + ": ";
				string += record.getMessage() + "\n";
				
				return string;
			}
		};
		
		return fm;
	}
	
	public static void saveLog() {
		//Get Current time
		LocalDateTime time = LocalDateTime.now();
		//Set path of file to copy to
		File oldLog = new File(folder + "/" + time.getDayOfMonth() + "." + time.getMonthValue() + "." + time.getYear() + "_" + 
							  					time.getHour() + "." + time.getMinute() + "." + time.getSecond() + ".log");
    	
		if(file.exists()) file.renameTo(oldLog);
		//Delete extra logs
		delLogs();
	}
	
	//This Method deletes log files over a certain number dictated by a config
	private static void delLogs() {
		//Get array of all files in folder
		String[] logs = folder.list();
		
		//Checks to make sure files exist
		if(folder.exists() && logs.length > 1 && Config.logFile.exists()) {
			//Tests if the number of files in folder is greater than the number of files to keep dictated by config
			for(;folder.list().length - 1 > (Integer.parseInt(Config.get(Config.logFile, "keepLogs")));) {
				//Get array of all files in folder
				logs = folder.list();
				//Set latest file to be deleted
				File del = new File(folder + "/" + logs[0]);
				//Delete file
				del.delete();
				//Log file deletion
				LOG.fine("Log file " + del.getAbsolutePath() + " deleted");
			}
		}
	}
}
