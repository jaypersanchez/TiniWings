/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcs.appchi;

import javax.microedition.lcdui.game.*;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


/**
 *
 * @author jaypersanchez
 */
public class TinyWingsGameCanvas extends GameCanvas implements Runnable, iGameData {
    
    int sleepTime = 35;
    int sleepTimeDelta = 2;
    Image tinywings = null;
    Image hillsImage = null;
    Sprite tinywingssprite = null;
    Sprite hillsprite = null;
    int hillPosition = 0;
    int tinywings_pos_x = 0;
    int tinywings_pos_y = 10;
    final static int tinywings_pos_x_vel = 3;
    final static int tinywings_pos_y_vel = 1;
    int hill_pos_x = 0;
    final static int hill_pos_x_vel = 1;
    int tinywingsdirection = 1;
    int current_collision_pos_x = 0;
    int current_collision_pos_y = 0;
    int previous_collision_pos_x = 0;
    int previous_collision_pos_y = 0;
    
    //refreshes only when screen has been released
    protected void pointerPressed(int x, int y) {
        this.tinywings_pos_y += tinywings_pos_y_vel;
        //every time player taps on screen, movement goes faster.
        sleepTime -= sleepTimeDelta;
    }
    
   
    //called specifically by action via key buttons
    void moveTinyWings() {
        try {
            
            //gravitation push.  This is to push tiny wings down towards the hill
            //velocity variables controls the speed of the movement as the player presses on the touch screen.
            this.tinywings_pos_y += this.tinywings_pos_y_vel;
            
            //determine key pressed
            int keyState = getKeyStates();
            if( (keyState & UP_PRESSED) != 0) {
                
                this.tinywings_pos_y -= this.tinywings_pos_y_vel;
            }
            else if ( (keyState & DOWN_PRESSED) != 0 ) {
                this.tinywings_pos_y += this.tinywings_pos_y_vel;
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
       
    void updateScreen(Graphics g) {
        createBackground(g);
        moveTinyWings();
        //updates the position of the bird
        tinywingssprite.setRefPixelPosition(this.tinywings_pos_x, this.tinywings_pos_y);
        //this.hillsprite.setRefPixelPosition(this.hill_pos_x -= hill_pos_x_vel, this.hillPosition);
        
        
        //determine if there is collision and if collision is decending or ascending
        if( this.tinywingssprite.collidesWith(this.hillsprite, true) ) {
                //get current collision coordinates
                current_collision_pos_x = this.tinywings_pos_x;
                current_collision_pos_y = this.tinywings_pos_y;
                previous_collision_pos_x = current_collision_pos_x;
                previous_collision_pos_y = current_collision_pos_y;
                 
                this.tinywings_pos_x += 1;
                this.tinywings_pos_y -= 5;
                if( current_collision_pos_y < previous_collision_pos_y ) {
                    this.tinywings_pos_y -= 10;
                }
                else if( current_collision_pos_y > previous_collision_pos_y ) {
                    this.tinywings_pos_y += 5;
                }
                System.out.println(current_collision_pos_x + " " + tinywings_pos_x + "/" + current_collision_pos_y + " " + tinywings_pos_y);
        }
        
        //repaint all object position
        tinywingssprite.paint(g);
        hillsprite.paint(g);
        flushGraphics();
    }
    
    
    //game engine
    public void run() {
        while(true) {
            updateScreen(getGraphics());
            try {
                Thread.sleep(sleepTime);
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    void createHills(Graphics g) {
        try {
            
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    void createBackground(Graphics g) {
        try {
            g.setColor(0xffffff);
            g.fillRect(0,0,getWidth(),getHeight());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void start() {
        try {
            loadResources();
            this.tinywingssprite = new Sprite(this.tinywings,tinywings.getWidth(),tinywings.getHeight());
            tinywingssprite.setPosition(this.tinywings_pos_x, this.tinywings_pos_y);
            
            this.hillsprite = new Sprite(this.hillsImage,this.hillsImage.getWidth(),this.hillsImage.getHeight());
            
            
            System.out.println("width: " + hillsprite.getWidth() + " height: " + hillsprite.getHeight());
            //take 25% of the screen for the hill
            //first hill
            this.hillPosition = (int) ( hillsprite.getWidth() - (hillsprite.getWidth()*.25)); 
            hillsprite.setPosition(0, hillPosition);
            
            System.out.println("Hill position: " + hillPosition);
            Thread runner = new Thread(this);
            runner.start();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    void loadResources() {
        try {
            this.tinywings = Image.createImage("/tinywings.png");
            this.hillsImage = Image.createImage("/hill_png.png");
            
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public TinyWingsGameCanvas() {
        super(false);
        try {
            //shows true if device has touch screen capabilities
            System.out.println(this.hasPointerEvents());
            System.out.println(this.hasPointerMotionEvents());
            System.out.println(this.hasRepeatEvents());
            System.out.println("width: "+getWidth() + " " + "height: " + getHeight());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
