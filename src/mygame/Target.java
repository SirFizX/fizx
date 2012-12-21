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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


/**
 *  This class loads an intended "target" model from the appropriate
 *  folder and assigns it a collision shape, rigid body control, and
 *  target control.
 * 
 * @author FizX
 */
public class Target extends Node{
    
   public Spatial t;
    
    public Target(String targetName, AssetManager assetManager,Vector3f location){
        Spatial target = assetManager.loadModel("Models/Targets/"+targetName+"/"+targetName+".j3o");
        target.setLocalScale(FizXBaseApp.GLOBAL_SCALE);
        target.move(location);
        target.setUserData("startPosition", location);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) target);
        RigidBodyControl targetPhys = new RigidBodyControl(sceneShape, 0);
        targetPhys.setEnabled(true);
        target.addControl(targetPhys);
        this.t = target;
    }
    
}
