/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Computer
 */
public class TargetControl extends AbstractControl implements Savable, Cloneable {
    
    private Spatial target;
    private double tt;
    private float displacement = 60f;
    private float speed = 10f;
    
    /**
     * 
     */
    public TargetControl(){}
    
    /**
     * 
     * @param bm
     */
    public TargetControl(Spatial t){
        this.target = t;
        
        // can't access spatial here
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
    super.setSpatial(spatial);
        // optional init method
        // you can get and set user data in the spatial here
    }
    @Override
    protected void controlUpdate(float tpf){
        if(spatial != null && target != null) {
            // Implement your custom control here ...
            // Change scene graph, access and modify userdata in the spatial, etc
            if(target.getUserData("Animation")!=null){
                switch((Integer)target.getUserData("Animation")){
                    case FizXBaseApp.ANIM_ROTATE_Y :
                        rotateY(tpf);
                        return;
                    case FizXBaseApp.ANIM_SINSCALE :
                        sinScale(tpf);
                        return;
                    case FizXBaseApp.ANIM_YOYO_X :
                        yoyoX(tpf);
                        return;
                   
                }
            }
            
        }
      
        
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp){
        // optional rendering manipulation (for advanced users)
    }
    @Override
    public Control cloneForSpatial(Spatial spatial){
        final TargetControl control = new TargetControl();
        control.setSpatial(spatial);
        return control;
    }
    /**
     * 
     * @param im
     * @throws IOException
     */
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        // im.getCapsule(this).read(...);
    }
    
    /**
     * 
     * @param ex
     * @throws IOException
     */
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        // ex.getCapsule(this).write(...);
    }
    
    private void rotateY(float tpf) {
        target.rotate(0f,tpf,0f);
    }
    
    private void sinScale(float tpf){
        tt+=tpf;
        float s = (float)Math.sin(tt)*5+5f;
        target.setLocalScale(s);
    }
    
    private void yoyoX(float tpf){
        Vector3f sv = (Vector3f)target.getUserData("startPosition");
        float sx = sv.x;
        float cx = target.getWorldTranslation().x;
        if(cx>sx+displacement||cx<sx)speed=-speed;
        target.move(speed*tpf,0f, 0f);
    }
   
} 
