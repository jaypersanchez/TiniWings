/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcs.appchi;

import javax.microedition.media.Manager;
import javax.microedition.media.Player;

/**
 *
 * @author jaypersanchez
 */
public class GameMusicPlayer implements Runnable {
    
    public void run() {
        try {
            Player player = Manager.createPlayer(getClass().getResourceAsStream("/night_1.mp3"),"audio/mpeg");
            player.realize();

            player.prefetch();
            player.setLoopCount(-1); //indefinate play
            player.start();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
