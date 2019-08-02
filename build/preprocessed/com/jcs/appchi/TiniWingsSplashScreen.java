/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcs.appchi;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author jaypersanchez
 */
public class TiniWingsSplashScreen extends Canvas{
    
    Image splashImage = null;
    Image normalButtonImage = null;
    Image selectedButtonImage = null;
    Sprite normalButtonSprite = null;
    Sprite selectedButtonSprite = null;
    int selection = 0;
    Sprite _logo = null;
    Display display = null;
    TiniWingsGameCanvas tinywingsvsworms = null;
    Command play = null;
    Command settings = null;
    Command instructions = null;
    Command objectives = null;
    Command statistics = null;
    Command exit = null;
    boolean playing = false;
    boolean inactive = true;
    MIDlet tinymid = null;
    
    protected void keyPressed(int keyCode)  {
       if(inactive) return;
       try {
           int action = getGameAction( keyCode );
           switch( action ){
                case DOWN:                
                        selection++;
                        if(selection>4) selection = 0;
                        repaint();
                        break;
                case UP:
                        selection--;
                        if(selection<0) selection = 4;
                        repaint();
                        break;
                case FIRE:
                        switch( selection ){
                            case 0:
                                if(playing) return;
                                this.tinywingsvsworms = new TiniWingsGameCanvas(display, this);
                                display.setCurrent(this.tinywingsvsworms);
                                Thread playGame = new Thread(new PlayGame());
                                playGame.start();
                                playing = true;
                                System.gc();
                                break;
                            case 1:
                                this.display.setCurrent(new InstructionsScreen(this.display,this));
                                break;
                            case 2:
                                this.display.setCurrent(new ObjecttiveScreen(this.display,this));
                                break;
                            case 3:
                                this.display.setCurrent(new StatScreen(this.display,this));
                                break;
                            case 4:
                                this.tinymid.notifyDestroyed();
                                break;
                        }
                }
       }
       catch(Exception e) {
           System.out.println(e.getMessage());
       }
   }
    
    public TiniWingsSplashScreen(Display _display, MIDlet tiny) {
        try {
            splashImage = Image.createImage("/splash_image_portrait.png");
            normalButtonImage = Image.createImage("/button_normal.png");
            selectedButtonImage = Image.createImage("/button_selected.png");
            this.tinymid = tiny;
            
            this.display = _display;
            this.setFullScreenMode(true);
            display.setCurrent(this);
            Thread.sleep(3000);
            inactive = false;
            repaint();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void paint(Graphics g) {
        g.drawImage(splashImage, 0, 0, Graphics.LEFT | Graphics.TOP);
        if(inactive) return;
        g.drawImage(normalButtonImage, 120, 50, Graphics.HCENTER | Graphics.TOP);
        g.drawImage(normalButtonImage, 120, 90, Graphics.HCENTER | Graphics.TOP);
        g.drawImage(normalButtonImage, 120, 130, Graphics.HCENTER | Graphics.TOP);
        g.drawImage(normalButtonImage, 120, 170, Graphics.HCENTER | Graphics.TOP);
        g.drawImage(normalButtonImage, 120, 210, Graphics.HCENTER | Graphics.TOP);
        g.drawImage(selectedButtonImage, 120, 50+40*selection, Graphics.HCENTER | Graphics.TOP);
        g.setColor(0, 0, 0);
        g.drawString("PLAY", 120, 53, Graphics.HCENTER | Graphics.TOP);
        g.drawString("INSTRUCTIONS", 120, 93, Graphics.HCENTER | Graphics.TOP);
        g.drawString("OBJECTIVES", 120, 133, Graphics.HCENTER | Graphics.TOP);
        g.drawString("HIGH SCORES", 120, 173, Graphics.HCENTER | Graphics.TOP);
        g.drawString("EXIT", 120, 213, Graphics.HCENTER | Graphics.TOP);
    }

    class PlayGame implements Runnable{
        public void run() {
            tinywingsvsworms.start();    
        }       
    }
    
    
}

