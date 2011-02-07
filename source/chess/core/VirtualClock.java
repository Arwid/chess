/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*                              Java Chess                                      *
*                 Copyright (C) 2005  Arvydas Bancewicz                        *
*                                                                              *
*    This program is free software; you can redistribute it and/or modify      *
*    it under the terms of the GNU General Public License as published by      *
*    the Free Software Foundation; either version 2 of the License, or         *
*    (at your option) any later version.                                       *
*                                                                              *
*    This program  is distributed in the hope that it will be useful,          *
*    but WITHOUT ANY WARRANTY; without even the implied warranty of            *
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
*    GNU General Public License for more details.                              *
*                                                                              *
*    You should have received a copy of the GNU General Public License         *
*    along with Java Chess; if not, write to the Free Software                 *
*    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA *
*                                                                              *                                                       *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*
 * Created on Apr 19, 2005
 *
 */
package chess.core;

import javax.swing.Timer;

import chess.gui.panels.ChessComponent;

import java.awt.event.*;
import java.io.Serializable;

/**
 * VirtualClock is a timer for the amount of time a player has left to move.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class VirtualClock implements Constants, Serializable {
  
  private boolean timeIsRunning = false;
  private boolean hasEnded = false;
  private long time = TEN_MINUTES;
  private long lastSysTime;
  private Timer clockTimer;
  
  transient ActionListener listener;
  
  public VirtualClock() {
    listener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          updateClock();
      }
    };
    clockTimer = new Timer(250, listener);
    
  }
  
  public void addActionListener(ActionListener ac) {
    clockTimer.addActionListener(ac);
  }
  
  private void updateClock() {
    long thisSysTime = System.currentTimeMillis();
    if (timeIsRunning) {
      time -= thisSysTime - lastSysTime;
      if (time <= 0) {
        time = 0;
        stopClock();
        hasEnded = true;
        ChessComponent.getInstance().chessGame.checkGameStatus();
      }
    }
    lastSysTime = System.currentTimeMillis();
  }
  
  public boolean hasEnded() {
    return hasEnded;
  }
  
  public void setEnabled(boolean enable) {
    if (!enable)
      stopClock();
  }
  
  public boolean isRunning() {
    return timeIsRunning;
  }
  
  public void startClock() {
    if (timeIsRunning)
      return;
    timeIsRunning = true;
    lastSysTime = System.currentTimeMillis();
    clockTimer.start();
  }
  
  public void stopClock() {
    timeIsRunning = false;
    clockTimer.stop();
    updateClock();
  }
  
  /**
   * Resets the clock to the initial time.
   */
  public void resetClock() {
    time = FIVE_MINUTES;
    //time = settings.whiteTime;
  }
  
  public void setTime(long time) {
    this.time = time;
  }
  
  public long getTime() {
    return time;
  }
  
  public String getTimeToString() {
    return getHours()+":"+getMinutes()+":"+getSeconds();
  }
  
  private String getHours() {
    int hours = (int)(time/1000/60/60);
    String sHrs = (""+hours).length()==2?""+hours:"0"+hours;
    
    return sHrs;
  }
  
  private String getMinutes() {
    int hours = Integer.parseInt(getHours());
    int minutes = (int)(60*(time/1000.0/60.0/60.0-hours));
    String sMin = (""+minutes).length()==2?""+minutes:"0"+minutes;
    
    return sMin;
  }
  
  private String getSeconds() {
    int hours = Integer.parseInt(getHours());
    int minutes = Integer.parseInt(getMinutes());
    int seconds = (int)(60*(time/1000.0/60.0-minutes-hours*60));
    String sSec = (""+seconds).length()==2?""+seconds:"0"+seconds;
    
    return sSec;
  }
  
}
