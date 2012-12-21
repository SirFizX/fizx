package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.font.BitmapFont;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
/**
 * FizX Simulation Base Application
 * @author Eric Eisaman
 */
public class FizXBaseApp extends SimpleApplication implements ActionListener{
    
    private BldgManager bldgManager;
    private BillboardManager billboardManager;
    private TerrainManager terrainManager;
    private LightManager lightManager;
    public GUIManager guiManager;
    private WeaponsManager weaponsManager;
    private TargetManager targetManager;
    public CharacterControl player;
    private Vector3f playerStartPosition = new Vector3f(0f,5,0f);
    private float jumpSpeed = 20f;
    public float camMoveSpeed = 100f;
    private Vector3f walkDirection = new Vector3f();
    public boolean left = false, right = false, up = false, down = false;
    public boolean begin = false;
    public int score = 0;
    //public constants to be assigned to visual 3d elements to describe
    //their destructability
    public static final int DESTROY_WITH_ALL_WEAPONS = 1;
    public static final int DESTROY_WITH_HEAVY_WEAPONS = 2;
    public static final int DESTROY_ONLY_WITH_BOMBS = 3;
    public static final int INDESTRUCTABLE = 4;
    
    public static final int DISTRIBUTION_HORIZ_GRID = 1;
    public static final int DISTRIBUTION_VERT_GRID = 2;
    public static final int DISTRIBUTION_HORIZ_SPIRAL = 3;
    public static final int DISTRIBUTION_VERT_SPIRAL = 4;
    
    public static final int ANIM_ROTATE_Y = 1;
    public static final int ANIM_SINSCALE = 2;
    public static final int ANIM_YOYO_X = 3;
    public static final int ANIM_DROP = 4;
    
    public static float GLOBAL_SCALE = 4.0f;
    /**
     * 
     */
    public static ColorRGBA Color_DUSK = new ColorRGBA(0.2f,0.18f,0.23f,1);
    /**
     * 
     */
    public static ColorRGBA Color_BLUE_SKY = new ColorRGBA(0.6f,0.8f,3.6f,1);
    
     public static ColorRGBA Color_WHITE = new ColorRGBA(1f,1f,1f,1);
    /**
     * 
     */
    public BulletAppState bulletAppState;

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        FizXBaseApp app = new FizXBaseApp();
        app.start();
    }

    /**
     * 
     */
    @Override
    public void simpleInitApp() {
        //don't show stats
        setDisplayFps(false);
        setDisplayStatView(false);
        
        this.bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
   
        this.lightManager = new LightManager(this);
        this.lightManager.addAmbientLight("ambientLight", ColorRGBA.White);
        
        this.guiManager = new GUIManager(this);
        stateManager.attach(guiManager);
        
        this.weaponsManager = new WeaponsManager(this);
        
        this.targetManager = new TargetManager(this);
         
        // We re-use the flyby camera for rotation, while positioning is handled by physics
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        flyCam.setMoveSpeed(camMoveSpeed);
        flyCam.setRotationSpeed(5);
        flyCam.setEnabled(false);
        this.cam.setFrustumFar(2000f);
        setUpKeys();
        setUpPlayer();
        addStartScreen();
        pauseGame();
    }

    /**
     * 
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
        camDir.y*=0.3f;
        camDir.normalize();
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        if (left)  { walkDirection.addLocal(camLeft); }
        if (right) { walkDirection.addLocal(camLeft.negate()); }
        if (up)    { walkDirection.addLocal(camDir); }
        if (down)  { walkDirection.addLocal(camDir.negate()); }
        player.setWalkDirection(walkDirection);
        
        cam.setLocation(player.getPhysicsLocation());  
        
        //for 3d audio
        listener.setLocation(cam.getLocation());
        listener.setRotation(cam.getRotation());
        //hand out passes to the update parade to our
        //hardworking manager classes
        targetManager.getControl(TargetManagerControl.class).controlUpdate(tpf);
        
        for(Spatial s:targetManager.targets){
            if(s.getUserData("Animation")!=null){
                s.getControl(TargetControl.class).controlUpdate(tpf);
            }
        }
        
    }

    /**
     * 
     * @param rm
     */
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    /**
     * Make sure you have a .j3o building model in the Models/Buidings/(buildingName)/
     * folder.
     * @param buildingName
     * @param location
     */
    public void addBuiding(String buildingName, Vector3f location){
       if(this.bldgManager==null){
          this.bldgManager = new BldgManager(this); 
       } 
       
       this.bldgManager.addBldg(buildingName, location);  
    }
    
     public void addBuiding(String buildingName, Vector3f location, int DESTRUCTIBILITY){
       if(this.bldgManager==null){
          this.bldgManager = new BldgManager(this); 
       } 
       
       this.bldgManager.addBldg(buildingName, location, DESTRUCTIBILITY);  
    }
     
     public void addBillboard(String name, Vector3f position, String text){
          if(this.billboardManager==null){
          this.billboardManager = new BillboardManager(this); 
       } 
         this.billboardManager.addBillboard(name, position,text);
     }
     
      public void addBillboard(String name, Vector3f position, String text, int DESTRUCTIBILITY, int POINTS){
          if(this.billboardManager==null){
          this.billboardManager = new BillboardManager(this); 
       } 
         this.billboardManager.addBillboard(name, position,text, DESTRUCTIBILITY, POINTS);
     }
     
     public void addTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addTarget(targetName, location, DESTRUCTIBILITY, POINT_VALUE);
     }
     
      public void addTarget(String targetName, Vector3f location, int DESTRUCTIBILITY, int POINT_VALUE, int ANIMATION){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addTarget(targetName, location, DESTRUCTIBILITY, POINT_VALUE, ANIMATION);
     }
      
      public void addDropTarget(String targetName, Vector3f location){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addDropTarget(targetName, location);
     }
     
     public void addMultipleTargets(String targetName, Vector3f firstPosition, int DISTRIBUTION, int distributionSize, int DESTRUCTIBILITY, int POINT_VALUE){
         if(this.targetManager==null){
             this.targetManager = new TargetManager(this);
         }
         
         this.targetManager.addMultipleTargets(targetName, firstPosition, DISTRIBUTION, distributionSize, DESTRUCTIBILITY,POINT_VALUE);
     }
     
      public void addFireEffect(Vector3f location){
       FireEffect fe = new FireEffect(this,location);
     }
    
     
    
    /**
     * This method adds a premade terrain to your scene. The terrain .j3o
     * file should be located in the Models/Terrains/ folder.
     * @param terrainName
     * @param location
     */
    public void addTerrain(String terrainName, Vector3f location){
       if(this.terrainManager==null){
          this.terrainManager = new TerrainManager(this); 
       } 
       
       this.terrainManager.addTerrain(terrainName, location);  
    }
    
    /**
     * 
     * @param lightName
     * @param location
     * @param radius
     * @param color
     */
    public void addPointLight(String lightName, Vector3f location, float radius, ColorRGBA color){
       if(this.lightManager==null){
          this.lightManager = new LightManager(this); 
       }
       this.lightManager.addPointLight(lightName, location, radius, color); 
    }
    
     public void addLampPost(String lampPostName, Vector3f location, float radius, ColorRGBA color){
       if(this.lightManager==null){
          this.lightManager = new LightManager(this); 
       }
       this.lightManager.addLampPost(lampPostName, location, radius, color); 
    }
    
    /**
     * 
     */
    public void removeAmbientLight(){
        this.lightManager.removeAmbientLight();
    }
    
    /**
     * 
     * @param intensity
     */
    public void setAmbientLightIntensity(float intensity){
        this.lightManager.setAmbientLightIntensity(intensity);
    }
    
    /**
     * 
     * @param color
     */
    public void setBackgroundColor(ColorRGBA color){
        this.viewPort.setBackgroundColor(color);
    }
    private void addStartScreen(){
        if(!this.guiManager.hasStartScreen){
            this.guiManager.addStartScreen();
        }
    }
    public void enableShooting(){
        if(!this.weaponsManager.shootingEnabled){
            this.guiManager.initCrossHairs();
            this.weaponsManager.enableShooting();
        }
    }
    public void disableShooting(){
        if(this.weaponsManager.shootingEnabled){
            this.weaponsManager.disableShooting();
        }
    }
    public boolean isShooting(){
        return this.weaponsManager.shootingEnabled;
    }
    public void stopFlyCam(){
        this.flyCam.setRotationSpeed(0);
    }
    public void goFlyCam(){
        this.flyCam.setRotationSpeed(5f);
    }
    public void setGUIFont(BitmapFont bitmapFont){
        this.guiFont = bitmapFont;
    }
    public BitmapFont getGUIFont(){
        return this.guiFont;
    }
    public int getWindowWidth(){
        return this.settings.getWidth();
    }
    public int getWindowHeight(){
        return this.settings.getHeight();
    }
    
    public void pauseGame(){
         //this.getStateManager().detach(this.bulletAppState);
         this.bulletAppState.setEnabled(false);
         this.flyCam.setEnabled(false);
         this.begin = false;
         this.getInputManager().setCursorVisible(true);
         
    }
    
     public void playGame(){
        this.getInputManager().setCursorVisible(false);
        //this.getStateManager().attach(this.bulletAppState);
        this.bulletAppState.setEnabled(true);
        this.guiManager.nifty.gotoScreen("gameplay");
        if(this.isShooting()){
            
            this.guiManager.initCrossHairs();
        } else {
            //this.guiManager.nifty.gotoScreen("gameplay");
        }
        this.begin = true;
        this.flyCam.setEnabled(true);
        
    }
     
    public void addToScore(int points){
        this.score+=points;
        this.guiManager.addToScore(points);
    }
    
    public void setMuzzleVelocity(int vel){
        this.weaponsManager.setMuzzleVelocity(vel);
    }
    
    public void showCamUpAngle(){
        this.guiManager.showCamUpAngle();
    }
    
    public int getCamUp(){
        float[] a = cam.getRotation().toAngles(null);
        //System.out.println(a[1]);
        int x = (int)(a[0]*180/3.14159);
        int z = (int)(a[2]*180/3.14159);
        return -(x+z);
        //return -((int)this.cam.getUp().multLocal(90f).y-90);
        //return (int)this.cam.getDirection().y;
    }
     
    public PhysicsSpace getPhysicsSpace(){
        return this.bulletAppState.getPhysicsSpace();
    }
    
    public WeaponsManager getWeaponsManager(){
        return this.weaponsManager;
    }
    
    public BillboardManager getBillBoardManager(){
        return this.billboardManager;
    }
    
    public TargetManager getTargetManager(){
        return this.targetManager;
    }
    
    public void addSky(String skyname) {
  
        Texture west = assetManager.loadTexture("Textures/Sky/"+skyname+"/west.jpg");
        Texture east = assetManager.loadTexture("Textures/Sky/"+skyname+"/east.jpg");
        Texture north = assetManager.loadTexture("Textures/Sky/"+skyname+"/north.jpg");
        Texture south = assetManager.loadTexture("Textures/Sky/"+skyname+"/south.jpg");
        Texture up = assetManager.loadTexture("Textures/Sky/"+skyname+"/up.jpg");
        Texture down = assetManager.loadTexture("Textures/Sky/"+skyname+"/down.jpg");

        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        rootNode.attachChild(sky);
  
  }
    
    /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  private void setUpKeys() {
        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        inputManager.addMapping("Left", new KeyTrigger(keyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(keyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(keyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(keyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(keyInput.KEY_SPACE));
        inputManager.addMapping("Menu", new KeyTrigger(keyInput.KEY_ESCAPE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
        inputManager.addListener(this, "Menu");
        //inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        //inputManager.addListener(this, "shoot");
       // inputManager.addMapping("gc", new KeyTrigger(KeyInput.KEY_X));
       //wwwwwwwww inputManager.addListener(this, "gc");
  }
  
   /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed.
   * @param binding
   * @param value  
   */
  public void onAction(String binding, boolean value, float tpf) {
    if (binding.equals("Left")) {
      if (value) { left = true; } else { left = false; }
    } else if (binding.equals("Right")) {
      if (value) { right = true; } else { right = false; }
    } else if (binding.equals("Up")) {
      if (value) { up = true; } else { up = false; }
    } else if (binding.equals("Down")) {
      if (value) { down = true; } else { down = false; }
    } else if (binding.equals("Jump")) {
      player.jump();
    } else if (binding.equals("Menu")){
      this.guiManager.nifty.gotoScreen("screenTwo");
      this.pauseGame();
    }
//    if (binding.equals("shoot") && !value && begin) {
//                Geometry bulletg = new Geometry("bullet", bullet);
//                bulletg.setMaterial(mat2);
//                bulletg.setShadowMode(ShadowMode.CastAndReceive);
//                bulletg.setLocalTranslation(cam.getLocation().add(cam.getDirection().mult(new Vector3f(3.5f,3.5f,3.5f))));
//                
//                SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(0.4f);
//                RigidBodyControl bulletNode = new BombControl(assetManager, bulletCollisionShape, 25);
////                RigidBodyControl bulletNode = new RigidBodyControl(bulletCollisionShape, 1);
//                bulletNode.setLinearVelocity(cam.getDirection().mult(55));
//                bulletg.addControl(bulletNode);
//                rootNode.attachChild(bulletg);
//                getPhysicsSpace().add(bulletNode);
//                audio_shoot.playInstance();
//            }
//            if (binding.equals("gc") && !value) {
//                System.gc();
//            }
  }
    
    private void setUpPlayer(){
        // We set up collision detection for the player by creating
        // a capsule collision shape and a CharacterControl.
        // The CharacterControl offers extra settings for
        // size, stepheight, jumping, falling, and gravity.
        // We also put the player in its starting position.
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.3f, 5f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(jumpSpeed);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setPhysicsLocation(playerStartPosition);
        bulletAppState.getPhysicsSpace().add(player);
        //bulletAppState.startPhysics();
    }
    
}
