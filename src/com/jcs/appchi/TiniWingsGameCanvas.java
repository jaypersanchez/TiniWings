
package com.jcs.appchi;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.game.*;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author jaypersanchez
 */
public class TiniWingsGameCanvas extends GameCanvas implements Runnable, CommandListener {
    
    Display display = null;
    Command exit = null;
    Command restart = null;
    Command cont = null;
    TiniWingsSplashScreen splash = null;
    Graphics g = null;
    boolean exitPressed = false;
    boolean sliding;
    byte time_constant = 10;
    int speed = 3;
    int pre_speed = 3;
    int[] rgb = null;
    Image tinywings = null;
    Image blank = null;
    Image black = null;
    Image sea = null;
    Image banner = null;
    Image hills_behind = null;
    Sprite bannerSprite = null;
    Sprite tinywingsSprite = null;
    Sprite blankSprite = null; 
    Sprite seaSprite = null;
    Sprite hillsBehindSprite = null; 
    Sprite blackSprite = null;
    short tinywings_pos_x = 45;
    short tinywings_pos_y = 156;
    byte tinywingsFrame = 0;
    int increment = 0;
    int screen_left_moved_amount = 0;
    short screen_width = 0;
    short screen_height = 0;
    byte highestAchievement = 0;
    static boolean paused = false;
    boolean ate_red_worm = false;
    boolean fell_into_sea = false;
    boolean reached_nest = false;
    short timeRemaining = 610;
    boolean timeOver = false;
    
    Image hillsImage = null;
    Sprite currentHillSprite = null;
    Sprite nextHillSprite = null;
    int current_hill_pos_x = 0;
    int next_hill_pos_x = 80;
    short hill_pos_y = 120;
    byte current_hill = 0;
    short gap_between_hills = 300;
    short counter = 0;
    int next_margin = 6150;
    int next_end = 6000;
    int next_start = 6250;
    boolean aboveIsland = false;

    Image sun = null;
    Sprite sunSprite = null;
    Image clouds = null;
    Image nest = null;
    Sprite cloudSprite = null;
    Sprite finalNestSprite = null;
    Sprite startingNestSprite = null;
    short sun_pos_x = 0;
    short sun_pos_y = 0;
    int final_nest_pos_x = 62450;  //changes with gap_between_hills
    short final_nest_pos_y = 186;
    short starting_nest_pos_x = 45;
    short starting_nest_pos_y = 186;
    boolean hits_the_clouds;
    boolean eating;
    short cloud_pos_y = -30;
    boolean touchSky;
    Sprite backgroundsprite = null;

    short change_of_increment = 0;
    short screen_up = 0;
    short change_in_direction = 0;
    boolean collided;
    boolean collided_previously;
    boolean hits_the_hill;
    boolean started;
    short previous_increment = 0;
    short before_collision = 0;
    short after_collision = 0;
    int pointer_x = 0;
    int pointer_y = 0;
           
    //Constant values to indicate each particle type
    static final byte FLOWER = 0;
    static final byte GREEN_WORM = 1;
    static final byte REGULAR_WORM = 2;
    static final byte RED_WORM = 3;
    short[] particle_count = new short[4]; //Stores the number of particles eaten, of each type
    int particle_number = 0;  //used to identify each particle
    Image moon = null;
    Sprite moonsprite = null;
    Image[] particleImages = new Image[4];

    int[] particle_position_x = {234,394,582,1056,1225,1321,1657,2099,2284,2545,2857,3144,3566,4102,4415,4650,4980,5250,5440,5600,
                                6542,6694,6882,7356,7570,7721,7967,8099,8584,8945,9157,9644,9866,10402,10715,10850,11480,11650,11780,11900,
                                12744,12994,13482,13656,13765,13921,14157,14399,14884,15145,15457,15744,16066,16502,16915,17250,17530,17750,17820,18100,
                                19124,19294,19482,19556,21125,21221,21457,21699,22184,22255,23057,23244,23466,23602,23795,23950,24080,24190,24340,24710,
                                25337,25494,25682,26056,26425,26521,26897,27299,27484,27845,28257,28444,28766,29302,29615,29850,30080,30450,30600,30800,
                                31701,31894,32082,32456,32685,32921,33357,33599,33784,34145,34357,34644,34966,35202,35515,35750,36080,36250,36640,37000,
                                37934,38194,38482,38856,39025,39321,39457,39899,40084,40425,40657,40944,41349,42902,42215,42450,42780,43050,43240,43400,
                                44653,44494,44682,45156,45325,45421,45757,46199,46384,46685,46957,47244,47666,48202,48515,48750,49080,49350,49540,49700,
                                50834,50994,51182,51456,51625,51821,52057,52199,52539,52980,53057,53444,53966,54102,54815,55050,55280,55650,55840,56000,
                                57094,57282,57756,58025,58221,58357,58387,58799,58984,59345,59557,59744,60166,60602,60915,61350,61600,61950,62140,62300,80000};

    int[] particle_position_y = {208,132,176,170,122,152,180,172,150,167,172,182,158,112,114,158,96,104,182,214,
                                202,128,176,166,110,156,174,146,148,110,170,114,154,108,112,190,158,154,194,228,
                                168,134,180,166,174,150,132,136,160,172,172,194,106,160,160,148,126,174,132,186,
                                202,152,200,140,128,148,162,184,168,113,134,140,198,118,153,120,160,114,188,160,
                                170,226,166,192,124,152,152,168,160,112,116,188,160,110,112,156,164,98,172,230,
                                218,130,178,124,157,158,192,178,150,112,172,180,104,146,138,174,198,124,172,202,
                                210,152,124,156,124,100,178,166,176,152,188,158,164,158,112,156,94,96,176,212,
                                158,108,142,130,200,134,218,110,160,138,172,194,172,102,112,148,94,213,240,245,
                                144,134,182,132,202,98,220,240,138,148,110,124,174,144,114,150,200,150,240,240,
                                154,204,158,156,102,178,158,168,178,140,190,100,114,168,174,158,151,200,196,200,1000};
    byte[] particle_type = {0,2,0,2,0,0,2,0,0,1,0,2,0,0,3,2,2,0,1,2,
                            2,0,0,2,0,1,2,0,3,0,1,2,0,0,0,2,2,0,0,2,
                            0,2,0,3,0,2,2,0,0,2,0,2,0,0,2,2,1,0,1,0,
                            2,0,0,2,0,0,2,0,2,1,0,2,3,0,1,2,2,0,0,0,
                            2,2,0,0,0,0,1,0,0,2,0,2,3,0,0,2,2,0,1,2,
                            0,2,0,2,1,0,2,0,0,2,0,2,0,0,3,2,2,0,0,1,
                            0,2,0,3,0,0,2,0,0,1,0,2,0,0,2,2,2,0,1,2,
                            1,2,0,2,0,0,2,0,2,1,0,2,0,0,3,2,2,0,0,0,
                            2,2,0,2,0,0,2,3,0,1,0,0,0,0,0,1,2,0,2,2,
                            0,0,2,0,0,2,1,1,0,2,0,2,0,2,3,2,0,0,2,0,0};
    boolean[] is_eaten = new boolean[201]; //indicates whether a particle is eaten by tiny wings or not
    short left_most_particle = 0;
    short right_most_particle = 0;
    short next_eat_particle = 0;
    byte particle_width = 20;

    int gamePoints = 0;
    RecordStore records;
    

   //detect keys pressed
   protected void keyPressed(int keyCode)  {
       try {
           int action = getGameAction( keyCode );
           switch( action ){
                case DOWN:                
                        if((!started)||paused) return;
                        increment += 2;
                        break;
                case UP:
                        if((!started)||paused) return;
                        if(!blankSprite.collidesWith(currentHillSprite, true)) return;
                        if(speed>5) speed /= 2;
                        tinywings_pos_y -= 10;
                        increment = -2*speed;                                 
                        break;
                case RIGHT:                     
                        if((!started)||paused) return;
                        pre_speed = speed;
                        if(speed<9) speed += 2;
                        else if(speed<10) speed = 10;
                        increment = (increment*speed)/pre_speed;
                        break;
                case FIRE:
                        if(!started) return;
                        if(timeOver){
                            System.gc();
                            this.splash.playing = false;
                            display.setCurrent(this.splash);
                            return;
                        }
                        if(!paused) {
                            paused = true;
                            this.addCommand(cont);
                            this.addCommand(restart);
                            this.addCommand(exit);
                            this.setFullScreenMode(false);
                        }
                        else {
                            paused = false;
                            this.removeCommand(cont);
                            this.removeCommand(restart);
                            this.removeCommand(exit);
                            this.setFullScreenMode(true);
                        }
                        break;
                }
       }
       catch(Exception e) {
           System.out.println(e.getMessage());
       }
   }

   protected void pointerPressed(int x,int y){
       pointer_x = x;
       pointer_y = y;
   }
   
   protected void pointerDragged(int x,int y){
       if((!started)||paused) return;
       if(x-20>pointer_x){ //moved right            
            pre_speed = speed;
            if(speed<10) speed++;
            increment = (increment*speed)/pre_speed;
       }
       else if(y+20<pointer_y){  //moved up
            if(!blankSprite.collidesWith(currentHillSprite, true)) return;
            if(speed>5) speed /= 2;
            tinywings_pos_y -= 10;
            increment = -speed;
       }
       else if(y-20>pointer_y){ //moved down
            increment++;
       }
   }
   
   void startMoving() {
        try{
        createBackground();
        currentHillSprite.setPosition(current_hill_pos_x,hill_pos_y);
        startingNestSprite.setPosition(starting_nest_pos_x, starting_nest_pos_y);
        tinywingsSprite.setRefPixelPosition(this.tinywings_pos_x,this.tinywings_pos_y);
        cloudSprite.setRefPixelPosition(0, this.cloud_pos_y);
        hillsBehindSprite.setPosition(0, 140);

        cloudSprite.paint(g);
        hillsBehindSprite.paint(g);
        sunSprite.paint(g);
        currentHillSprite.paint(g);
        startingNestSprite.paint(g);
        tinywingsSprite.paint(g);
        
        flushGraphics();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void restart() {
    try{
        gamePoints = 0;
        speed = 3;
        pre_speed = 3;
        tinywings_pos_y = 156;
        tinywingsFrame = 0;
        screen_left_moved_amount = 0;
        timeRemaining = 600;
        started = false;
        ate_red_worm = false;
        fell_into_sea = false;
        reached_nest = false;
        current_hill = 0;
        current_hill_pos_x = 0;
        highestAchievement = 0;
        hillsImage = Image.createImage("/hill1.png");
        currentHillSprite = new Sprite(this.hillsImage);
        currentHillSprite.setPosition(current_hill_pos_x, hill_pos_y);

        hillsImage = Image.createImage("/hill2.png");
        nextHillSprite = new Sprite(this.hillsImage);
        next_hill_pos_x = (short)(6000 + gap_between_hills);
        nextHillSprite.setPosition(next_hill_pos_x, hill_pos_y);
        
        sun_pos_x = (short)(screen_width - 40);
        sun_pos_y = 0;
        hits_the_clouds = false;
        eating = false;
        touchSky = false;
        exitPressed = false;
        pointer_x = 0;
        pointer_y = 0;
    
        change_of_increment = 0;
        screen_up = 0;
        change_in_direction = 0;
        collided = false;
        collided_previously = false;
        hits_the_hill = false;
        previous_increment = 0;
        before_collision = 0;
        after_collision = 0;
        next_margin = 6150;
        next_end = 6000;
        next_start = 6250;
        aboveIsland = false;

        particle_count = new short[4];
        is_eaten = new boolean[310];
        left_most_particle = 0;
        right_most_particle = 0;
        next_eat_particle = 0;
        timeOver = false;
        System.gc();
        this.setFullScreenMode(true);
        
    }catch(Exception e){
        System.out.println(e.getMessage());
    }
    }


    void updateScreen() {
        try{
        
        counter++;

        //Move the current hill
        this.current_hill_pos_x -= speed;
        screen_left_moved_amount += speed; //keeping track of the horizontal movement

        //Move the next hill
        if(current_hill<9){
            this.next_hill_pos_x -= speed;
            nextHillSprite.setPosition(next_hill_pos_x,hill_pos_y);
        }

        //Move tiny wings
        this.tinywings_pos_y += increment;
        tinywingsSprite.setRefPixelPosition(this.tinywings_pos_x,this.tinywings_pos_y);

        //Selecting the particles which are currently on the screen
        while(particle_position_x[left_most_particle] + particle_width <screen_left_moved_amount){
            left_most_particle++;
        }
        while(particle_position_x[right_most_particle]<screen_left_moved_amount + screen_width){
            right_most_particle++;
        }
        while(particle_position_x[next_eat_particle]<screen_left_moved_amount + tinywings_pos_x) next_eat_particle++;

        this.gamePoints++;

        if(screen_left_moved_amount>next_margin){
            if(current_hill>8){
                next_margin += 10000;
            }
            else{
                next_margin += 6300;
                current_hill++;  //Moved to the next hill
                currentHillSprite = nextHillSprite;
                current_hill_pos_x = next_hill_pos_x;

                new Thread(new NewHillAction()).start();
            }
        }             

        change_of_increment = this.tinywings_pos_y;      

        currentHillSprite.setPosition(current_hill_pos_x,hill_pos_y);
        //First, move tiny wings forward, and if it collapses with a hill, lift her up until it doesn't collapse 
        while(this.tinywingsSprite.collidesWith(currentHillSprite, true)){
            this.tinywings_pos_y -= 2;
            tinywingsSprite.setRefPixelPosition(this.tinywings_pos_x,this.tinywings_pos_y);
        }               
                
        change_of_increment -= this.tinywings_pos_y;
        increment -= change_of_increment;
        
        //bouncing
        if(change_of_increment>speed){           
            if(increment<-3) speed /= 2;
            if(speed<3) speed = 3;
            increment /= 2;
        }
        
        if(counter%15 == 0){            
        if(speed>3) speed--; //slowing   
        }
        
        if(counter%4 == 0){            
            if((change_of_increment==0)&&(increment<15))increment++; //gravity 
        }
        
        if(counter%10 == 0){ 
        timeOver = ((timeRemaining--)<=0);//timer
        if(counter==1000)counter = 0;
        }
        
        //Slapping wings
        if(counter%5 == 0){ 
        if(tinywingsFrame==2) tinywingsFrame -= 2;
        if(tinywingsFrame<2) tinywingsFrame++;
        }
               
        
        //opening mouth    
        if(eating){
           tinywingsFrame = 3;
        }
        else{
            if(tinywingsFrame==3) tinywingsFrame -= 3;
        }

        tinywingsSprite.setFrame(tinywingsFrame);
        
        
        //Lifting the screen to show clouds
        //When tiny wings went above the position 10, move the whole scene up accordingly
        screen_up = (short) (10 - tinywings_pos_y); //calculate the amount of lifting
        if(screen_up < 0) screen_up = 0; //we lift it only when screen_up is positive
        tinywingsSprite.setRefPixelPosition(this.tinywings_pos_x,this.tinywings_pos_y+screen_up);//move tiny wings up
        blankSprite.setRefPixelPosition(this.tinywings_pos_x+19,this.tinywings_pos_y+screen_up);
        currentHillSprite.setPosition(current_hill_pos_x,hill_pos_y+screen_up); //move the current hill up
        if(current_hill<9) nextHillSprite.setPosition(next_hill_pos_x,hill_pos_y+screen_up); //move the next hill up

        this.cloudSprite.setRefPixelPosition(0, this.cloud_pos_y+screen_up/2);

        if(tinywings_pos_y<0) {
            if(!touchSky){
                this.gamePoints += 200;
                touchSky = true;
            }
        }
        else touchSky = false;
        
        //Checking for the accomplishment of the goal
        if(screen_left_moved_amount+58>final_nest_pos_x){
            highestAchievement = 3;

            boolean reg = (particle_count[REGULAR_WORM]==70);
            boolean gre = (particle_count[GREEN_WORM]==20);
            boolean flo = (particle_count[FLOWER]==100);

            if(reg) highestAchievement = 4;
            if(gre){
                if(reg) highestAchievement = 5;
                if(flo){
                    highestAchievement = 6;
                    if(flo) highestAchievement = 7;
                }
            }

            reached_nest = true;
            timeOver = true;
        }               
        
        //eating process of flowers and worms
        while((screen_left_moved_amount+80>particle_position_x[next_eat_particle])&&(tinywings_pos_y+30>particle_position_y[next_eat_particle])){
            if(particle_type[next_eat_particle]==RED_WORM){
                ate_red_worm = true;
                timeOver = true;
                next_eat_particle++;
            }
            else{
                if(particle_type[next_eat_particle]==GREEN_WORM){
                    increment = (increment*13)/speed;
                    speed = 13;
                }
                particle_count[particle_type[next_eat_particle]]++;
                is_eaten[next_eat_particle] = true;
                next_eat_particle++;
                gamePoints++;               
            }
        }

        //move sun and moon according to timer       
        this.sunSprite.setPosition(sun_pos_x, 300-timeRemaining/2);
        this.moonsprite.setPosition(sun_pos_x, -timeRemaining);

        //Opening or closing the mouth
        eating = ((screen_left_moved_amount+110>particle_position_x[next_eat_particle])&&(tinywings_pos_y+60>particle_position_y[next_eat_particle]));
        
        //determine if tiny wings fell off between hills
        if(tinywings_pos_y > 260) {
            fell_into_sea = true;
            timeOver = true;
        }
        
        //Displaying the background
        createBackground();
        
        //Displaying clouds
        cloudSprite.paint(g);
        
        //Diaplaying hills behind
        hillsBehindSprite.setPosition(-screen_left_moved_amount/16, 140+screen_up);
        hillsBehindSprite.paint(g);

        //Displaying Tiny Wings
        tinywingsSprite.paint(g);
        
        //Diaplaying sea
        seaSprite.setPosition(0, 280+screen_up);
        seaSprite.paint(g);
        
        //Displaying sun or moon
        if(timeRemaining<100) moonsprite.paint(g);
        sunSprite.paint(g); 

        //Displaying hills
        currentHillSprite.paint(g);//current hill
        if((current_hill<9)&&(current_hill_pos_x < -5800)) nextHillSprite.paint(g);//next hill
        
        //Display uneaten flowers/worms
        for(int i=left_most_particle;i<right_most_particle;i++) if(!is_eaten[i]) g.drawImage(particleImages[particle_type[i]], particle_position_x[i]-screen_left_moved_amount, particle_position_y[i]+screen_up, Graphics.LEFT|Graphics.TOP);
        
        //Displaying the nests
        if(current_hill==0){
            startingNestSprite.setRefPixelPosition(starting_nest_pos_x - screen_left_moved_amount, starting_nest_pos_y + screen_up);
            startingNestSprite.paint(g);
        }
        if(current_hill==9){
            finalNestSprite.setRefPixelPosition(final_nest_pos_x - screen_left_moved_amount, final_nest_pos_y + screen_up);
            finalNestSprite.paint(g);
        }       
        if(!timeOver) g.drawString("Points:" + this.gamePoints, 0, screen_height-20, Graphics.LEFT | Graphics.BOTTOM);
        
        flushGraphics();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void updateGameStat() {
        try {      
            //Making an array to store in the RMS
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            String datesString = (calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.YEAR);
            String recordString = datesString+"^"+gamePoints+"^"+highestAchievement;
            byte[] data = recordString.getBytes();

            //Storing data in the record store
            records = RecordStore.openRecordStore("tinywings_records", true);
            records.addRecord(data, 0, data.length);
            records.closeRecordStore();
        } catch (RecordStoreException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //game engine
    public void run() {
        try {
            startMoving();
            Thread.sleep(1000);
            increment = -5;
            started = true;
            while (!timeOver) {
                if(!paused) updateScreen();
                Thread.sleep(time_constant);
            }
            
            if(exitPressed) return;

            this.removeCommand(restart);
            this.removeCommand(exit);

            blackSprite.paint(g);

            if(reached_nest) this.banner = Image.createImage("/welldone.png");
            else this.banner = Image.createImage("/gameover.png");
            this.bannerSprite = new Sprite(this.banner);
            this.banner = null;

            bannerSprite.paint(g);

            updateGameStat(); //Add records to RMS

            g.setColor(255,0,0);
            if(reached_nest){
                g.drawString("Tiny Wings reached", 120, 80, Graphics.TOP | Graphics.HCENTER);
                g.drawString("her goal!!!", 120, 100, Graphics.TOP | Graphics.HCENTER);
            }
            else if(ate_red_worm){
                g.drawString("Tiny Wings ate the", 120, 80, Graphics.TOP | Graphics.HCENTER);
                g.drawString("poisonous red worm", 120, 100, Graphics.TOP | Graphics.HCENTER);
            }
            else if(fell_into_sea){
                g.drawString("Tiny Wings fell", 120, 80, Graphics.TOP | Graphics.HCENTER);
                g.drawString("into the sea", 120, 100, Graphics.TOP | Graphics.HCENTER);
            }
            else{
                g.drawString("Time is up!", 120, 90, Graphics.TOP | Graphics.HCENTER);
            }
            
            g.setColor(0,0,255);
            g.drawString("Islands reached :", 10, 140, Graphics.TOP | Graphics.LEFT);
            g.drawString("Flowers picked  :", 10, 160, Graphics.TOP | Graphics.LEFT);
            g.drawString("Regular worms   :", 10, 180, Graphics.TOP | Graphics.LEFT);
            g.drawString("Green worms     :", 10, 200, Graphics.TOP | Graphics.LEFT);
            g.drawString("Bonus Time      :", 10, 220, Graphics.TOP | Graphics.LEFT);
            g.drawString("Points scored   :", 10, 240, Graphics.TOP | Graphics.LEFT);
            g.drawString("Nest achieved   :", 10, 260, Graphics.TOP | Graphics.LEFT);

            g.setColor(0,255,0);
            g.drawString(Integer.toString(current_hill+1), 230, 140, Graphics.TOP | Graphics.RIGHT);
            g.drawString(Integer.toString(particle_count[FLOWER]), 230, 160, Graphics.TOP | Graphics.RIGHT);
            g.drawString(Integer.toString(particle_count[REGULAR_WORM]), 230, 180, Graphics.TOP | Graphics.RIGHT);
            g.drawString(Integer.toString(particle_count[GREEN_WORM]), 230, 200, Graphics.TOP | Graphics.RIGHT);
            g.drawString(Integer.toString(timeRemaining/10), 230, 220, Graphics.TOP | Graphics.RIGHT);
            g.drawString(Integer.toString(gamePoints), 230, 240, Graphics.TOP | Graphics.RIGHT);
            g.drawString(Integer.toString(highestAchievement), 230, 260, Graphics.TOP | Graphics.RIGHT);
            
            g.setColor(255, 150, 150);
            g.drawString("press 'FIRE' to return", 120, 290, Graphics.TOP | Graphics.HCENTER);

            flushGraphics();                 

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
       
    void createBackground() {
        try {
            int color = 250;
            if(timeRemaining<100) color = timeRemaining + 50;
            else if(timeRemaining<300) color = 100 + timeRemaining/2;
            g.setColor(0,color,color);
            g.fillRect(0,0,screen_width,screen_height);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void start() {
        try {

            loadResources();
            
            blankSprite.setPosition(this.tinywings_pos_x+28,this.tinywings_pos_y);
 
            sun_pos_x = (short) (getWidth() - 40);
            sunSprite.setPosition(sun_pos_x, sun_pos_y);                        

            cloudSprite.setPosition(0,cloud_pos_y);
            
            g.fillRoundRect(62, 172, 72, 12, 12, 12);
            flushGraphics();           

            particleImages[0] = Image.createImage("/flower.png");
            particleImages[1] = Image.createImage("/green_worm.png");
            particleImages[2] = Image.createImage("/regular_worm.png");
            particleImages[3] = Image.createImage("/red_worm.png");

            g.fillRoundRect(62, 172, 90, 12, 12, 12);
            flushGraphics();

            this.startingNestSprite = new Sprite(this.nest);       
            this.finalNestSprite = new Sprite(this.nest);

            //Setting the first hill
            this.hillsImage = Image.createImage("/hill1.png");
            this.currentHillSprite = new Sprite(this.hillsImage);
            this.currentHillSprite.setPosition(current_hill_pos_x, hill_pos_y);

            //Setting the second hill
            this.hillsImage = Image.createImage("/hill2.png");
            this.nextHillSprite = new Sprite(this.hillsImage);
            next_hill_pos_x = (short) (6000 + gap_between_hills);
            this.nextHillSprite.setPosition(next_hill_pos_x, hill_pos_y);
            
            g.fillRoundRect(62, 172, 120, 12, 12, 12);
            flushGraphics();
            g.setColor(200,255,255);
            
            Thread runner = new Thread(this);
            runner.start();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    void loadResources() {
            
        try {
            screen_width = (short) (this.getWidth());
            screen_height = (short) (this.getHeight());
               
            g.setColor(0, 0, 0);  
            g.fillRect(0, 0, screen_width, screen_height);
            g.setColor(255, 0, 0);
            g.drawString("Loading....", 80, 140, Graphics.TOP|Graphics.LEFT);
            g.drawRoundRect(62, 172, 120, 12, 12, 12);
            flushGraphics();                                  
            
            this.tinywings = Image.createImage("/tini.png");
            this.tinywingsSprite = new Sprite(this.tinywings,tinywings.getWidth()/4,tinywings.getHeight());
            this.tinywings = null;
            
            g.fillRoundRect(62, 172, 12, 12, 12, 12);
            flushGraphics();
            
            this.sun = Image.createImage("/sun1.png");
            this.sunSprite = new Sprite(sun);
            this.sun = null;
        
            this.clouds = Image.createImage("/clouds_3.png");
            this.cloudSprite = new Sprite(this.clouds);
            this.clouds = null;
            
            g.fillRoundRect(62, 172, 24, 12, 12, 12);
            flushGraphics();
            
            this.blank = Image.createImage("/blank.png");
            this.blankSprite = new Sprite(this.blank);
            this.blank = null;           

            this.moon = Image.createImage("/moon1.png");
            moonsprite = new Sprite(moon);
            this.moon = null;
            
            this.sea = Image.createImage("/sea.png");
            seaSprite = new Sprite(sea);
            this.sea = null;
            
            g.fillRoundRect(62, 172, 42, 12, 12, 12);
            flushGraphics();
            
            //Semi transparent black layer
            rgb = new int[screen_height*screen_width];
            for(int i=0;i<rgb.length;i++) rgb[i] = 0xAF000000;
            black = Image.createRGBImage(rgb, screen_width, screen_height, true);
            blackSprite = new Sprite(black);
            
            this.hills_behind = Image.createImage("/hills_behind.png");
            hillsBehindSprite = new Sprite(hills_behind);
            this.hills_behind = null;

            this.nest = Image.createImage("/nest5.png");
            
            g.fillRoundRect(62, 172, 60, 12, 12, 12);
            flushGraphics();
            
        }
        catch(Exception e) {            
            System.out.println(e.getMessage());
        }
    }
    
    public void commandAction(Command c, Displayable d) {
        String label = c.getLabel();
        
            if(label.equals("Exit")) {
                exitPressed = true;
                timeOver = true;
                this.splash.playing = false;
                display.setCurrent(this.splash);
            }

            if(label.equals("Restart")) {
                paused = true;
                restart();
                startMoving();
            try {         
                Thread.sleep(1000);
                paused = false;
                started = true;
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
                increment = -5;                
            }      
            
            if(label.equals("Continue")) {
                if(!started) return;
                paused = false;
                this.removeCommand(cont);
                this.removeCommand(restart);
                this.removeCommand(exit);
                this.setFullScreenMode(true);
            }
    }
   
    public TiniWingsGameCanvas(Display _display, TiniWingsSplashScreen _splash) {
        super(false);
        try {
            splash = _splash;
            exit = new Command("Exit",Command.EXIT,1);
            restart = new Command("Restart",Command.EXIT,4);
            this.cont = new Command("Continue", Command.OK, 5);
            this.setCommandListener(this);
            g = getGraphics();
            display = _display;
            //shows true if device has touch screen capabilities
            System.out.println(this.hasPointerEvents());
            System.out.println(this.hasPointerMotionEvents());
            System.out.println(this.hasRepeatEvents());
            System.out.println("width: "+getWidth() + " " + "height: " + getHeight());      
            this.setFullScreenMode(true);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());           
        }
    }
    
    class NewHillAction implements Runnable{
        public void run() {

            //adds 10 seconds to the clock
            timeRemaining += 100;

            //Achievements
            if(current_hill == 5) highestAchievement = 1;
            if(current_hill == 7) highestAchievement = 2;
            if(current_hill<9){
                try {
                    hillsImage = Image.createImage("/hill" + Integer.toString(current_hill + 2) + ".png");
                    nextHillSprite = new Sprite(hillsImage, 6000, 240);
                    next_hill_pos_x = (short) (current_hill_pos_x + 6000 + gap_between_hills);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            hillsImage = null;
            System.gc();
        }
    }
}
