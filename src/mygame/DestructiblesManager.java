/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;


/**
 * <code>BldgManager</code> extends the {@link com.jme3.scene.Geometry}
 * class to provide an easy means to organize and control interactions with
 * a single building asset. The next version will extend the {@link com.jme3.scene.Node}
 * class to provide means by which multiple building assets could be
 * managed with a single class object. 
 */
public class DestructiblesManager extends Node {
  
    private FizXBaseApp app;
    private DestructiblesControl destroyControl;
    public List<Spatial> destructibles = new ArrayList<Spatial>();
    
    /**
     * Simple constructor used when you want an early handle on the building
     * manager yet don't wish to add any buildings at that time.
     * @param app
     */
    public DestructiblesManager(FizXBaseApp app){
        
        this.app = app;
        this.destroyControl = new DestructiblesControl(this);
        this.destroyControl.setEnabled(true);
        this.addControl(this.destroyControl);
    }
    
    /**
     * The buildingName parameter is the name of the .j3o file found
      * in the Models/Buildings/buildingName/ directory. The location parameter
     * is the desired world space translation you want for the building.
     * @param app
     * @param buildingName
      * @param location
     */
    public DestructiblesManager(FizXBaseApp app, String buildingName, Vector3f location){
        this.app = app;
        this.destroyControl = new DestructiblesControl(this);
        this.destroyControl.setEnabled(true);
        this.addControl(this.destroyControl); 
    }
    
     public void addDestructible(Spatial b) {
        
        this.destructibles.add(b);

  }
     
    
}
