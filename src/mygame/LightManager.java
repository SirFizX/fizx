/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.List;
import com.jme3.scene.LightNode;
import com.jme3.scene.Spatial;
import java.util.ArrayList;


/**
 * <code>BldgManager</code> extends the {@link com.jme3.scene.Geometry}
 * class to provide an easy means to organize and control interactions with
 * a single building asset. The next version will extend the {@link com.jme3.scene.Node}
 * class to provide means by which multiple building assets could be
 * managed with a single class object. 
 */
public class LightManager extends Node {
  
    private FizXBaseApp app;
    private FizXLightControl lightControl;
    private List<PointLight> pointLights = new ArrayList<PointLight>();
    private List<SpotLight> lampLights = new ArrayList<SpotLight>();
    private List<Spatial> lamps = new ArrayList<Spatial>();
    private AmbientLight ambientLight;
    private RigidBodyControl lampPhys;
    
    /**
     * Simple constructor used when you want an early handle on the building
     * manager yet don't wish to add any buildings at that time.
     * @param app
     */
    public LightManager(FizXBaseApp app){
        
        this.app = app;
        this.lightControl = new FizXLightControl(this);
        this.lightControl.setEnabled(true);
        this.addControl(this.lightControl);
    }
    
   
    
    public void addPointLight(String lightName, Vector3f location, float radius, ColorRGBA color ){
        PointLight l = new PointLight();
        l.setPosition(location);
        l.setRadius(radius);
        l.setColor(color);
        this.pointLights.add(l);
        this.app.getRootNode().addLight(l);
        System.out.println("point light added");
    }
    
    public void addAmbientLight(String lightName, ColorRGBA color ){
        AmbientLight l = new AmbientLight();
        l.setColor(color);
        this.ambientLight = l;
        this.app.getRootNode().addLight(l);
    }
    
    public void setAmbientLightIntensity(float intensity){
        this.ambientLight.setColor(new ColorRGBA(intensity,intensity,intensity,1));
    }
    
    public void removeAmbientLight(){
        this.app.getRootNode().removeLight(ambientLight);
    }
    
    public void addLampPost(String lampPostName, Vector3f location, float radius, ColorRGBA color){
        Spatial lampPost = this.app.getAssetManager().loadModel("Models/Lights/lampPost/lampPost.j3o");
        lampPost.setName(lampPostName);
        lampPost.setUserData("Destructibility", 4);
        lampPost.move(location);
        addMeshLight(lampPost);
        
        SpotLight l = new SpotLight();
        l.setPosition(new Vector3f(location.x+2,location.y+9,location.z));
        l.setSpotInnerAngle(.01f);
        l.setSpotOuterAngle(4f);
        l.setDirection(new Vector3f(0f,-1f,0f));
        l.setSpotRange(100);
        l.setColor(color);
        this.lampLights.add(l);
        this.app.getRootNode().addLight(l);
        
    }
    
    private void addMeshLight(Spatial m) {
        
        this.lamps.add(m);
        com.jme3.bullet.collision.shapes.CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) m);
        lampPhys = new RigidBodyControl(sceneShape, 0);
        m.addControl(lampPhys);


        this.app.getRootNode().attachChild(m);
        this.app.bulletAppState.getPhysicsSpace().add(m);

  }
    
    
}
