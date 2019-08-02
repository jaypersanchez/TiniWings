/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcs.appchi;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;

/**
 * @author jaypersanchez
 */
public class TinyWings extends MIDlet {

    TinyWingsGameCanvas tinywingsvsworms = null;
    
    public TinyWings() {
        tinywingsvsworms = new TinyWingsGameCanvas();
    }
    
    public void startApp() {
        Display display = Display.getDisplay(this);
        tinywingsvsworms.start();
        display.setCurrent(tinywingsvsworms);
    }
    
    public void pauseApp() {
        
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
