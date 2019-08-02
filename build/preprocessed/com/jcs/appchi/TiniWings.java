/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcs.appchi;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

/**
 * @author jaypersanchez
 */
public class TiniWings extends MIDlet implements CommandListener {

    TiniWingsGameCanvas tinywingsvsworms = null;
    public Display display = null;
    TiniWingsSplashScreen splash = null;
    
    public TiniWings() {
        try {
            display = Display.getDisplay(this);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void commandAction(Command c, Displayable d) {
        String label = c.getLabel();
        if(label.equals("Play")) {
            System.out.println("play command");
        }
    }
    
    public void startApp() {
       
        try {
            //audio player eats a lot of memory
            new Thread(new GameMusicPlayer()).start();
            splash = new TiniWingsSplashScreen(display, this);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void pauseApp() {
        
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
