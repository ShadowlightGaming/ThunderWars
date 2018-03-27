package com.game3d;

import java.util.logging.Logger;

import com.game3d.util.Config;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
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

    Spatial sceneModel;
    BulletAppState bas;
    RigidBodyControl landscape;
    CharacterControl player;
    
    Vector3f walkDirection = new Vector3f();
    Vector3f camDir = new Vector3f();
    Vector3f camLeft = new Vector3f();
    
	@Override
	public void simpleInitApp() {
		bas = new BulletAppState();
		stateManager.attach(bas);
		
		//Set world settings
		viewPort.setBackgroundColor(new ColorRGBA(0.7f,0.8f,1f,1f));
		flyCam.setMoveSpeed(20);
		
		//Load map
		app.getAssetManager().registerLocator("assets/models/map1.zip", ZipLocator.class);
		sceneModel = app.getAssetManager().loadModel("map1.scene");
		sceneModel.setLocalScale(2.5f);
		
		//Set world collision paramators
		CollisionShape collisionShape = CollisionShapeFactory.createMeshShape(sceneModel);
		landscape = new RigidBodyControl(collisionShape, 0);
		sceneModel.addControl(landscape);
		
		//Set Lighting
		PointLight light = new PointLight();
		light.setColor(ColorRGBA.White);
		light.setRadius(2500f);
		light.setPosition(new Vector3f(0f,50f,0f));
		rootNode.addLight(light);
		
		//Load Player
		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
		player = new CharacterControl(capsuleShape, 0.05f);
		player.setJumpSpeed(20);
		player.setFallSpeed(30);
		player.setGravity(30f);
		player.setPhysicsLocation(new Vector3f(0, 10, 0));
		player.setEnabled(true);
		
		//Add map
		rootNode.attachChild(sceneModel);
		bas.getPhysicsSpace().add(landscape);
		bas.getPhysicsSpace().add(player);
		
		//Setup lights and keybinds
		Controls controls = new Controls();
		controls.init();
		
		LOG.info("Game initialized");
	}

	@Override
	public void simpleUpdate(float tpf) {
		if(Controls.sprint == true) camDir.set(cam.getDirection().multLocal(0.8f));
		if(Controls.sprint == false) camDir.set(cam.getDirection().multLocal(0.6f));
		camLeft.set(cam.getLeft().multLocal(0.4f));
		walkDirection.set(0, 0, 0);
		
		if(Controls.up) walkDirection.addLocal(camDir.getX(), 0, camDir.getZ());
		if(Controls.down) walkDirection.addLocal(camDir.negate().getX(), 0, camDir.negate().getZ());
		if(Controls.left) walkDirection.addLocal(camLeft);
		if(Controls.right) walkDirection.addLocal(camLeft.negate());
		
		if(Controls.jump == true && player.onGround() == true) {
			Controls.jump = false;
			player.jump();
		}
		
		player.setWalkDirection(walkDirection);
		cam.setLocation(player.getPhysicsLocation());
	}
}
