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
import java.util.ArrayList;
import java.util.List;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.renderer.queue.RenderQueue.Bucket;


/**
 * <code>BldgManager</code> extends the {@link com.jme3.scene.Geometry}
 * class to provide an easy means to organize and control interactions with
 * a single building asset. The next version will extend the {@link com.jme3.scene.Node}
 * class to provide means by which multiple building assets could be
 * managed with a single class object. 
 */
public class BillboardManager extends Node {
  
    private FizXBaseApp app;
    private AssetManager assetManager;
    private RigidBodyControl bbPhys;
    private BillboardControl billboardControl;
    public List<Spatial> billboards = new ArrayList<Spatial>();
    public List<BitmapText> boardtextlist = new ArrayList<BitmapText>();
    private float scale = 4f;
    private String billboardType = "plainWood";
    
    /**
     * Simple constructor used when you want an early handle on the building
     * manager yet don't wish to add any buildings at that time.
     * @param app
     */
    public BillboardManager(FizXBaseApp app){
        
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.billboardControl = new BillboardControl(this);
        this.billboardControl.setEnabled(true);
        this.addControl(this.billboardControl);
    }
    
    /**
     * The buildingName parameter is the name of the .j3o file found
      * in the Models/Buildings/buildingName/ directory. The location parameter
     * is the desired world space translation you want for the building.
     * @param app
     * @param buildingName
      * @param location
     */
    public BillboardManager(FizXBaseApp app, String billboardName, Vector3f location){
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.billboardControl = new BillboardControl(this);
        this.billboardControl.setEnabled(true);
        this.addControl(this.billboardControl); 
        Spatial bb = this.assetManager.loadModel("Models/Billboards/"+billboardName+"/"+billboardName+".j3o");
        bb.setName(billboardName);
        bb.setUserData("Destructibility", 4);
        bb.move(location);
        addBillboard(bb);
    }
    
     private void addBillboard(Spatial b) {
        
        this.billboards.add(b);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) b);
        bbPhys = new RigidBodyControl(sceneShape, 0);
        b.addControl(bbPhys);


        this.app.getRootNode().attachChild(b);
        this.app.bulletAppState.getPhysicsSpace().add(b);

  }
    
    public void addBillboard(String billboardName, Vector3f location, String text) {
        Spatial bb = this.assetManager.loadModel("Models/Billboards/"+billboardType+"/"+billboardType+".j3o");
        bb.setName(billboardName);
        bb.setUserData("Destructibility", 4);
        bb.setUserData("IsBillboard", 1);
        bb.move(new Vector3f(location.x+4f,location.y-3.2f,location.z-.7f));
        bb.setLocalScale(scale);
        this.billboards.add(bb);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) bb);
        bbPhys = new RigidBodyControl(sceneShape, 0);
        bb.addControl(bbPhys);

        this.app.getRootNode().attachChild(bb);
        this.app.bulletAppState.getPhysicsSpace().add(bb);
        
        BitmapFont fnt = this.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText txt = new BitmapText(fnt, false);
        txt.setName(billboardName);
        this.boardtextlist.add(txt);
        txt.setBox(new Rectangle(0, 0, 8, 4));
        txt.setQueueBucket(Bucket.Transparent);
        txt.setSize( scale/4f );
        txt.setText(text);
        txt.setLocalTranslation(location);
        this.app.getRootNode().attachChild(txt);

  }
    
       public void addBillboard(String billboardName, Vector3f location, String text, int DESTRUCTIBILITY, int POINTS) {
        System.out.println(billboardName);
        Spatial bb = this.assetManager.loadModel("Models/Billboards/"+billboardType+"/"+billboardType+".j3o");
        bb.setName(billboardName);
        bb.setUserData("Points", POINTS);
        bb.setUserData("Destructibility", DESTRUCTIBILITY);
        bb.setUserData("IsBillboard", 1);
        bb.move(new Vector3f(location.x+4f,location.y-3.2f,location.z-0.7f));
        bb.setLocalScale(scale);
        this.billboards.add(bb);
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) bb);
        bbPhys = new RigidBodyControl(sceneShape, 0);
        bb.addControl(bbPhys);
         if(DESTRUCTIBILITY<4){
           this.app.getWeaponsManager().getDestructiblesManager().addDestructible(bb); 
        }
        this.app.getRootNode().attachChild(bb);
        this.app.bulletAppState.getPhysicsSpace().add(bb);
        
        BitmapFont fnt = this.app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText txt = new BitmapText(fnt, false);
        txt.setName(billboardName+"text");
        this.boardtextlist.add(txt);
        txt.setBox(new Rectangle(0, 0, 8, 4));
        txt.setQueueBucket(Bucket.Transparent);
        txt.setSize( scale/4f );
        txt.setText(text);
        txt.setLocalTranslation(location);
        this.app.getRootNode().attachChild(txt);

  }
    
    
    
}
