package com.game3d;

import java.util.logging.Logger;

import com.game3d.util.Config;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

public class Controls extends Game implements AnalogListener, ActionListener {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());
	Game game = new Game();
	
	public static boolean left; 
	public static boolean right;
	public static boolean up;
	public static boolean down;
	public static boolean jump;
	public static boolean sprint;
	public static boolean crouch;

	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		switch(binding) {
		case "Left":
			left = isPressed;
			break;
		case "Right":
			right = isPressed;
			break;
		case "Up":
			up = isPressed;
			break;
		case "Down":
			down = isPressed;
			break;
		case "Jump":
			jump = isPressed;
			break;
		case "Sprint":
			sprint = isPressed;
			break;
		case "Crouch":
			crouch = isPressed;
			break;
		case "Shoot":
			left = isPressed;
			break;
		}
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
		switch(name) {
		}
	}
	
	public void init() {
		//Set input
		Game.app.getInputManager().addMapping("Up", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Up"))));
		Game.app.getInputManager().addMapping("Down", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Down"))));
		Game.app.getInputManager().addMapping("Left", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Left"))));
		Game.app.getInputManager().addMapping("Right", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Right"))));
		Game.app.getInputManager().addMapping("Jump", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Jump"))));
		Game.app.getInputManager().addMapping("Sprint", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Sprint"))));
		Game.app.getInputManager().addMapping("Crouch", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Crouch"))));
		Game.app.getInputManager().addMapping("Shoot", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Shoot"))));
		
		//Set and log listener
		Game.app.getInputManager().addListener(this, new String[]{"Left", "Right", "Up", "Down", "Jump", "Sprint", "Crouch","Shoot"});
		LOG.info("Key listener created");
		
		//Log intialization
		LOG.info("Keys initialized");
	}
}
