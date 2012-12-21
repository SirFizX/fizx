/*
 * This template has been designed for beginners having little
 * to no previous knowledge of the java language. This template
 * can be used to develop a fairly robust first person perspective
 * application with minimal coding.
 */
package mygame;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

/**
 *
 * @author Eric Eisaman
 */
public class MyGame extends FizXBaseApp {
    
     /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        MyGame app = new MyGame();
        app.start();
    }
    
     /**
     * 
     */
    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        //Add your initialization code here. Use type "this." to see 
        //some convenient methods
        this.addBuiding("business_cool", new Vector3f(0f,0f,-400f));
        //this.addMultipleTargets("queen_palm", new Vector3f(0f,0f,0f), DISTRIBUTION_HORIZ_GRID, 5, DESTROY_WITH_ALL_WEAPONS, 100);
       // this.addBuiding("ghsmar13", new Vector3f(0f,0.3f,0f),INDESTRUCTABLE);
       // this.addBuiding("domeHouse", new Vector3f(-90f,0f,-70f));
       // this.addMultipleTargets("fpcon_cube",new Vector3f(-20f,70f,20f),DISTRIBUTION_VERT_SPIRAL,4, DESTROY_WITH_ALL_WEAPONS,100);
        //this.addTarget("fpcon_cube",new Vector3f(-66f,26f,10f), DESTROY_WITH_ALL_WEAPONS,500,ANIM_SINSCALE);
        //this.addTarget("fpcon_cube",new Vector3f(-86f,26f,50f), DESTROY_WITH_ALL_WEAPONS,500,ANIM_ROTATE_Y);
       // this.addTarget("fpcon_cube",new Vector3f(46f,16f,50f), DESTROY_WITH_ALL_WEAPONS,500,ANIM_YOYO_X);
        //this.addDropTarget("box2",new Vector3f(60f,50f,-50f));
        this.addBillboard("Gain", new Vector3f(-70,8,-10),"Hi there!\nWhat are you doing?", DESTROY_WITH_ALL_WEAPONS, 500);
         this.addBillboard("Lose", new Vector3f(114,8,-60),"Luther is a wonderful boy!\nCool huh?",DESTROY_WITH_ALL_WEAPONS, -500);
        this.addTerrain("arenaGrass", Vector3f.ZERO);
        //this.addLampPost("coolBusiness", new Vector3f(0f,0f,-320f), 30f, Color_WHITE);
        this.setAmbientLightIntensity(2.7f);
        this.setBackgroundColor(Color_BLUE_SKY);
        this.addDropTarget("barrel",new Vector3f(60f,50f,-50f));
        this.addDropTarget("barrel",new Vector3f(-60f,50f,-50f));
        //this.addPointLight("light1", new Vector3f(0f,60f,0f), 1000f , ColorRGBA.White);
        //this.addPointLight("light2", new Vector3f(-90f,10f,-70f), 100f , ColorRGBA.Red);
        //this.addPointLight("light3", new Vector3f(-15,15f,20f),1000f, ColorRGBA.White);
        this.enableShooting();
        this.setMuzzleVelocity(50);
        this.showCamUpAngle();
        this.addSky("Lagoon");
        //this.addFireEffect(new Vector3f(0,2,-90));
    }
    
     @Override
    public void simpleRender(RenderManager rm) {
         super.simpleRender(rm);
        //TODO: add render code
    }
    
    /**
     * 
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        //TODO: add update code
    }
    
}

 
