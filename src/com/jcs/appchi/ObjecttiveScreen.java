/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcs.appchi;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author jaypersanchez
 */
public class ObjecttiveScreen extends Canvas {
    
    Display d = null;
    TiniWingsSplashScreen splash = null;

    public ObjecttiveScreen(Display d, TiniWingsSplashScreen t) {
        this.d = d;
        splash = t;
        this.setFullScreenMode(true);
    }
    
    protected void keyPressed(int keyCode)  {  
       if(getGameAction(keyCode)==FIRE){
           this.d.setCurrent(this.splash);
       }
    }
    
    public void paint(Graphics g) {
        try {
            new Sprite(Image.createImage("/splash_dark.png")).paint(g);
            g.setColor(255,255,0);
            g.drawString("OBJECTIVES", 120, 10, Graphics.TOP | Graphics.HCENTER);
            g.setColor(0,255,0);
            g.drawString("1: Complete 5 hills", 10, 40, Graphics.TOP | Graphics.LEFT);
            g.drawString("2: Complete 7 hills", 10, 65, Graphics.TOP | Graphics.LEFT);
            g.drawString("3: Reach the nest", 10, 90, Graphics.TOP | Graphics.LEFT);
            g.drawString("4: Collect all regular worms", 10, 115, Graphics.TOP | Graphics.LEFT);
            g.drawString("5: Collect all regular worms", 10, 140, Graphics.TOP | Graphics.LEFT);
            g.drawString("and green worms", 35, 155, Graphics.TOP | Graphics.LEFT);
            g.drawString("6: Collect all green worms", 10, 180, Graphics.TOP | Graphics.LEFT);
            g.drawString("and flowers", 35, 195, Graphics.TOP | Graphics.LEFT);
            g.drawString("7: Collect all green worms,", 10, 220, Graphics.TOP | Graphics.LEFT);
            g.drawString("regular worms and", 35, 235, Graphics.TOP | Graphics.LEFT);
            g.drawString("flowers", 35, 250, Graphics.TOP | Graphics.LEFT);
            g.setColor(255, 0, 0);
            g.drawString("press 'FIRE' to return", 120, 290, Graphics.TOP | Graphics.HCENTER);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
