package com.game3d;

import java.util.logging.Logger;

import com.bulletphysics.dynamics.RigidBody;
import com.game3d.util.Config;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

public class Game extends SimpleApplication {
	private static final Logger LOG = Logger.getLogger(com.game3d.util.Log.class.getName());
	
    static Game app = new Game();
    
    public static void run() {
    	AppSettings settings = new AppSettings(true);
    	//Set settings
    	settings.setFrameRate(Integer.parseInt(Config.get(Config.displayFile, "framerate")));
		settings.setFullscreen(Boolean.parseBoolean(Config.get(Config.displayFile, "fullscreen")));
		settings.setWidth(Integer.parseInt(Config.get(Config.displayFile, "screenWidth")));
		settings.setHeight(Integer.parseInt(Config.get(Config.displayFile, "screenHeight")));
		settings.setSamples(Integer.parseInt(Config.get(Config.displayFile, "antianliasing")));
		settings.setVSync(Boolean.parseBoolean(Config.get(Config.displayFile, "vsync")));
		settings.setTitle("Game 3D [WIP]");
		//settings.setIcons();
		
		//Log settings
		LOG.fine("Framerate set to " + Integer.parseInt(Config.get(Config.displayFile, "framerate")));
		LOG.fine("Fullscreen set to " + Boolean.parseBoolean(Config.get(Config.displayFile, "fullscreen")));
		LOG.fine("Screen Width set to " + Integer.parseInt(Config.get(Config.displayFile, "screenWidth")));
		LOG.fine("Screen Height set to " + Integer.parseInt(Config.get(Config.displayFile, "screenHeight")));
		LOG.fine("Antianliasing set to " + Integer.parseInt(Config.get(Config.displayFile, "antianliasing")));
		LOG.fine("VSynce set to " + Boolean.parseBoolean(Config.get(Config.displayFile, "vsync")));
		LOG.fine("Title set to \"Game 3D [WIP]\"");
		LOG.fine("Icon set");
		
		app.setSettings(settings);
		app.setShowSettings(false);
		
        app.start();
        LOG.info("Starting game");
    }

    private Spatial sceneModel;
    private BulletAppState bas;
    private RigidBody landscape;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;
    
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    
	@Override
	public void simpleInitApp() {
		viewPort.getName();
		Init init = new Init();
		
		init.world();
		init.entities();
		init.lighting();
		init.keys();
		
	}

	@Override
	public void simpleUpdate(float tpf) {
		
	}
}
