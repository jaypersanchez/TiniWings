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
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/**
 *
 * @author jaypersanchez
 */
public class StatScreen extends Canvas {
    
    Display dp = null;
    TiniWingsSplashScreen splash = null;
    RecordStore records;
    RecordEnumeration enumeration = null;
    
    public StatScreen(Display d, TiniWingsSplashScreen t) {
        this.dp = d;
        splash = t;
        this.setFullScreenMode(true);
    }
    
    protected void keyPressed(int keyCode)  {  
       if(getGameAction(keyCode)==FIRE){
           this.dp.setCurrent(this.splash);
       }
    }
    
    public void paint(Graphics g) {
        try {
            new Sprite(Image.createImage("/splash_dark.png")).paint(g);

            //Displaying topic
            g.setColor(0,255,0);
            g.drawString("HIGH SCORES", 120, 10, Graphics.TOP | Graphics.HCENTER);

            g.setColor(0,255,255);
            int linePosition = 60;
            //Displaying the names of fields
            g.drawString("Date", 50, 30, Graphics.TOP | Graphics.HCENTER);
            g.drawString("Score", 140, 30, Graphics.TOP | Graphics.HCENTER);
            g.drawString("Nest", 210, 30, Graphics.TOP | Graphics.HCENTER);

            g.setColor(255,255,0);
            //Opening RMS
            records = RecordStore.openRecordStore("tinywings_records", true);
            enumeration = records.enumerateRecords(null, null, false);
            //Getting records to an arrey
            int num = records.getNextRecordID();
            String[] dates = new String[num];
            int[] scores = new int[num];
            String[] nests = new String[num];
            int index = 0;
            //Displaying records stored in RMS
            while(enumeration.hasNextElement()){
                //Taking the record into a string
                byte[] data = enumeration.nextRecord();
                char[] chars = new char[data.length];
                for(int i=0;i<data.length;i++) chars[i] = (char)data[i];
                String recordString = String.valueOf(chars);

                //Seperating the fields and storing
                int strLength = recordString.length();
                int dateEnd = recordString.indexOf('^');
                dates[index] = recordString.substring(0, dateEnd);
                scores[index] = Integer.parseInt(recordString.substring(dateEnd+1, strLength-2));
                nests[index] = recordString.substring(strLength-1);
                index++;
                
            }
            
            //Releasing the resources
            enumeration.destroy();
            records.closeRecordStore();  
            
            int lines = (index>10)? 10 : index; //number of lines to display
            int max, maxpos;
            for(int i=0;i<lines;i++){
                max=0;maxpos=0;
                for(int j=0;j<index;j++){ //sorting the scores
                    if(scores[j]>max){
                        max = scores[j];
                        maxpos = j;
                    }
                }
                //Displayind values in a line
                g.drawString(dates[maxpos], 50, linePosition, Graphics.TOP | Graphics.HCENTER);
                g.drawString(Integer.toString(scores[maxpos]), 140, linePosition, Graphics.TOP | Graphics.HCENTER);
                g.drawString(nests[maxpos], 210, linePosition, Graphics.TOP | Graphics.HCENTER);
                linePosition += 20;
                scores[maxpos] = -1;
            }

            g.setColor(255, 0, 0);
            g.drawString("press 'FIRE' to return", 120, 290, Graphics.TOP | Graphics.HCENTER);        
                                        
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
