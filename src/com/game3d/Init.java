package com.game3d;

import java.util.logging.Logger;

import com.game3d.handlers.InputHandler;
import com.game3d.util.Config;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Init extends Game {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());
	
	InputHandler listener = new InputHandler();

	public void world() {
		//Set world settings
		app.getViewPort().setBackgroundColor(new ColorRGBA(0.7f,0.8f,1f,1f));
		app.getFlyByCamera().setMoveSpeed(100);
		
		//Log world settings
		LOG.fine("Background color set");
		LOG.info("World initialized");
	}
	
	public void entities() {
		//Log entitites
		LOG.info("Entities initialized");		
	}

	public void lighting() {
		//Set ambient light
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1.3f));
		rootNode.addLight(al);
		//Log AL creation
		LOG.fine("Ambient light created");
		
		//Set directional light
		DirectionalLight dl = new DirectionalLight();
		dl.setColor(ColorRGBA.White);
		dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
		rootNode.addLight(dl);
		//Log DL light
		LOG.fine("Directional light created");
		
		//Log lighting
		LOG.info("Lighting initialized");
	}

	public void keys() {
		//Set input
		app.getInputManager().addMapping("Up", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Up"))));
		app.getInputManager().addMapping("Down", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Down"))));
		app.getInputManager().addMapping("Left", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Left"))));
		app.getInputManager().addMapping("Right", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Right"))));
		app.getInputManager().addMapping("Jump", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Jump"))));
		app.getInputManager().addMapping("Sprint", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Sprint"))));
		app.getInputManager().addMapping("Crouch", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Crouch"))));
		app.getInputManager().addMapping("Shoot", new KeyTrigger(Integer.parseInt(Config.get(Config.keysFile, "Shoot"))));
		
		//Set and log listener
		app.getInputManager().addListener(listener, new String[]{"Left", "Right", "Up", "Down", "Jump"});
		LOG.info("Key listener created");
		
		//Log intialization
		LOG.info("Keys initialized");
	}
}
