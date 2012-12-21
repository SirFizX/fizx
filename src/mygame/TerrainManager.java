/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


/**
 * <code>BldgManager</code> extends the {@link com.jme3.scene.Geometry}
 * class to provide an easy means to organize and control interactions with
 * a single building asset. The next version will extend the {@link com.jme3.scene.Node}
 * class to provide means by which multiple building assets could be
 * managed with a single class object. 
 */
public class TerrainManager extends Node {
  
    private FizXBaseApp app;
    private AssetManager assetManager;
    private RigidBodyControl terrainPhys;
    private TerrainControl terrainControl;
    private Spatial terrain;
    private float scale = 2f;
    
    /**
     * Simple constructor used when you want an early handle on the building
     * manager yet don't wish to add any buildings at that time.
     * @param app
     */
    public TerrainManager(FizXBaseApp app){
        
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.terrainControl = new TerrainControl(this);
        this.terrainControl.setEnabled(true);
        this.addControl(this.terrainControl);
    }
    
    /**
     * The buildingName parameter is the name of the .j3o file found
      * in the Models/Buildings/buildingName/ directory. The location parameter
     * is the desired world space translation you want for the building.
     * @param app
     * @param buildingName
      * @param location
     */
    public TerrainManager(FizXBaseApp app, String terrainName, Vector3f location){
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.terrainControl = new TerrainControl(this);
        this.terrainControl.setEnabled(true);
        this.addControl(this.terrainControl); 
        Spatial terr = this.assetManager.loadModel("Models/Terrains/"+terrainName+".j3o");
        terr.setName(terrainName);
        terr.move(location);
        addTerrain(terr);
    }
    
     private void addTerrain(Spatial b) {
        
        this.terrain = b;
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) b);
        terrainPhys = new RigidBodyControl(sceneShape, 0);
        b.addControl(terrainPhys);


        this.app.getRootNode().attachChild(b);
        this.app.bulletAppState.getPhysicsSpace().add(terrainPhys);

  }
    
    public void addTerrain(String terrainName, Vector3f location) {
        Spatial terr = this.assetManager.loadModel("Models/Terrains/"+terrainName+".j3o");
        terr.setName(terrainName);
        terr.move(location);
        terr.setLocalScale(scale);
        this.terrain = terr;
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) terr);
        terrainPhys = new RigidBodyControl(sceneShape, 0);
        terrainPhys.setEnabled(true);
        terr.addControl(terrainPhys);
        
        this.app.getRootNode().attachChild(terr);
        this.app.bulletAppState.getPhysicsSpace().add(terr);

  }
    
    
}
