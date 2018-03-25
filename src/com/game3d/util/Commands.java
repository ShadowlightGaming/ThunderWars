package com.game3d.util;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Commands {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());
	
	static ArrayList<String> commands = new ArrayList<>();
	static ArrayList<String> commandsUse = new ArrayList<>();
	static ArrayList<String> commandsDesc = new ArrayList<>();
	
	//Add command name, use, and description to  arrays
	public static void add(String command, String commandUse, String commandDesc) {
		commands.add(command);
		commandsUse.add(commandUse);
		commandsDesc.add(commandDesc);
	}
	
	//Get ID via command name
	public static int getID(String command) {
		int spaces = 0;
		int s1 = command.length();
		//Go through string look for a space
		for(int j = 0; j < command.length(); j++) {
			//Log character
			LOG.finer("Chararcter " + j + " is " + command.charAt(j));

			if(command.charAt(j) == ' ') {
				//If a space is found, set s1 to the position of that space
				spaces++;
				if(spaces == 1) {
					//Set space 1 divider at point j
					s1 = j;
					LOG.fine("1st Space found at position " + j);
				}
			}
		}
		//Set the string to end at the first space
		command = command.substring(0, s1);
		
		
		//return ID of command
		if(command.equals(commands.get(0))) {
			LOG.config("ID for command " + command + " is 0");
			return 0;
		} else if(command.equals(commands.get(1))) {
			LOG.config("ID for command " + command + " is 1");
			return 1;
		} else {
			LOG.config("ID for command " + command + " is 2");
			return 2;
		}
	}
	
	//Run Command
	public static void run(String command) {
		LOG.config("Attempting to run command \"" + command + "\"");
		//Get ID of command
		int num = getID(command);
		
		int spaces = 0;
		int s1 = command.length();

		//Go through string look for a space
		for(int j = 0; j < command.length(); j++) {
			//Log character
			LOG.finer("Chararcter " + j + " is " + command.charAt(j));

			if(command.charAt(j) == ' ') {
				//Record that a space was found
				spaces++;
				if(spaces == 1) {
					//Set space 1 divider at point j
					s1 = j;
					LOG.fine("1st Space found at position " + j);
				}
			}
		}
		
		//Get sub command (string of text after first space)
		String sub = command.substring(s1).trim();
		LOG.fine("Subcommand is " + sub);
		
		//Execute command
		switch(num) {
		//help
		case 0:
			//Print all commands names, uses, and descriptions
			for(int i = 0; i < commands.size(); i++) {
				LOG.info(commandsUse.get(i) + ": " + commandsDesc.get(i));
			}
			
			LOG.info("\"Help\" command executed");
			break;
		case 1:
			break;
		default:
			LOG.warning("Unkown Command \"" + command + "\"; for a list of commands run \"help\"");
			break;
		}
	}
}
