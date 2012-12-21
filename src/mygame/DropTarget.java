/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;


/**
 *  This class loads an intended "target" model from the appropriate
 *  folder and assigns it a collision shape, rigid body control, and
 *  target control.
 * 
 * @author FizX
 */
public class DropTarget extends Node{
    
   public Spatial t;
    
    public DropTarget(String targetName,FizXBaseApp app,Vector3f location){
        Spatial target = app.getAssetManager().loadModel("Models/Targets/"+targetName+"/"+targetName+".j3o").clone();
        
        target.setLocalScale(FizXBaseApp.GLOBAL_SCALE);
        target.center().move(location);
        target.setUserData("startPosition", location);
        float xBound = ((BoundingBox)target.getWorldBound()).getXExtent();
        float yBound = ((BoundingBox)target.getWorldBound()).getYExtent();
        float zBound = ((BoundingBox)target.getWorldBound()).getZExtent();
        BoxCollisionShape bcs = new BoxCollisionShape(new Vector3f(xBound,yBound,zBound));
        DropTargetControl targetPhys = new DropTargetControl(app,bcs, 30f);
        targetPhys.setEnabled(true);
        targetPhys.setKinematic(true);
        target.addControl(targetPhys);
        this.t = target;
    }
    
}
