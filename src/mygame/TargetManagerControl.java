/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
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
public class TargetManagerControl extends AbstractControl implements Savable, Cloneable {
    
    private TargetManager targetManager;
    
    /**
     * 
     */
    public TargetManagerControl(){}
    
    /**
     * 
     * @param bm
     */
    public TargetManagerControl(TargetManager tm){
        this.targetManager = tm;
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
        if(spatial != null && targetManager != null) {
            // Implement your custom control here ...
            // Change scene graph, access and modify userdata in the spatial, etc
            
        }
        
        
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp){
        // optional rendering manipulation (for advanced users)
    }
    @Override
    public Control cloneForSpatial(Spatial spatial){
        final TargetManagerControl control = new TargetManagerControl();
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
} 
