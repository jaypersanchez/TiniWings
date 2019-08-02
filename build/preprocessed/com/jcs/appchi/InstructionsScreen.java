package com.jcs.appchi;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author jaypersanchez
 */
public class InstructionsScreen extends Canvas {
    
    Display d = null;
    TiniWingsSplashScreen splash = null;
     
    protected void keyPressed(int keyCode)  {  
       if(getGameAction(keyCode)==FIRE){
           this.d.setCurrent(this.splash);
       }
    }
    
    public InstructionsScreen(Display d, TiniWingsSplashScreen t) {
        this.d = d;
        splash = t;
        this.setFullScreenMode(true);
    }
    
    public void paint(Graphics g) {
        try {
            new Sprite(Image.createImage("/splash_dark.png")).paint(g);
            g.setColor(255,255,0);
            g.drawString("INSTRUCTIONS", 120, 10, Graphics.TOP | Graphics.HCENTER);
            g.setColor(0,255,0);
            g.drawString("Press the Right button", 120, 50, Graphics.TOP | Graphics.HCENTER);
            g.drawString("to increase the speed", 120, 70, Graphics.TOP | Graphics.HCENTER);
            g.drawString("Press the Up button", 120, 100, Graphics.TOP | Graphics.HCENTER);
            g.drawString("to jump over red worms", 120, 120, Graphics.TOP | Graphics.HCENTER);
            g.drawString("Press the Down button", 120, 150, Graphics.TOP | Graphics.HCENTER);
            g.drawString("to descend faster", 120, 170, Graphics.TOP | Graphics.HCENTER);
            g.drawString("Press the Middle button", 120, 200, Graphics.TOP | Graphics.HCENTER);
            g.drawString("for menu", 120, 220, Graphics.TOP | Graphics.HCENTER);
            g.setColor(255, 0, 0);
            g.drawString("press 'FIRE' to return", 120, 290, Graphics.TOP | Graphics.HCENTER);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
