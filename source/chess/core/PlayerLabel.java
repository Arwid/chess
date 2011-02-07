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
 * Created on Apr 22, 2005
 *
 */
package chess.core;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * PlayerLabel is a label put above or below the main chess board to provide
 * player info, such as player name and timer.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class PlayerLabel extends JPanel {
  
  // Clock
  private VirtualClock vc;
  private ActionListener vcListener;
  
  private boolean white;   // player white ?
  private boolean active;  // player's turn to move ?
  
  private String time;  // player time
  private String name;  // player name
  
  private boolean user;
  
  /**
   * Constructor sets up the player label
   * @param white - is player white ?
   */
  public PlayerLabel (boolean white) {
    
    setPreferredSize(new Dimension(0,25));
    
    this.white = white;
    
    time = new String();
    name = new String();
    setNull();
    
    // Repaint the label when the time changes
    vcListener = new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        try {
          time = vc.getTimeToString();
        } catch (Exception ex){}
        repaint();
    }};
  }
  
  /**
   * Change the game
   * @param game - the chess game
   */
  public void updateGame(ChessGame game) {
    
    if (game instanceof ChessGame) {
      
      vc = game.getClock(white);
      vc.addActionListener(vcListener);
      
      time = vc.getTimeToString();
      name = white? game.getWhiteName() : game.getBlackName();
      setActive(white? game.getTurn() : !game.getTurn());
      
      user = white? game.whiteParameters.isUser() : game.blackParameters.isUser();
      
    } else 
      setNull();
    
    repaint();
  }
  
  /**
   * Is it this player's turn to move ?
   * @param active - player's turn to move ?
   */
  public void setActive(boolean active) {
    this.active = active;
    repaint();
  }
  
  /**
   * There is no current game
   *
   */
  private void setNull() {
    vc = null;
    time = "";
    name = "";
  }
  
  // Painting info
  private Color border     = new Color(160,160,160);
  private Color foreground = new Color(10,10,10);    // text color
  private Color highlight  = new Color(180,180,180); // active
  private Color inactive   = new Color(140,140,140); // inactive
  
  private Font font = new Font("Serif", Font.CENTER_BASELINE, 12); // text font
  
  // Border stroke
  private Stroke dottedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
      BasicStroke.JOIN_ROUND, 10,
      new float[] { 1, 3 }, 0);
  
  /**
   * Paint the label
   */
  public void paint (Graphics g) {
    
    paintComponent(g);
    Graphics2D g2D = (Graphics2D)g;
    
    // Draw Border
    g.setColor(border);
    Stroke temp = g2D.getStroke();
    g2D.setStroke(dottedStroke);
    g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    g2D.setStroke(temp);
    
    if(vc!=null) {
    
      ((Graphics2D)g).setRenderingHint(
          RenderingHints.KEY_ANTIALIASING, 
          RenderingHints.VALUE_ANTIALIAS_ON);
      
      g.setFont(font);
      
      // Text metrics
      FontMetrics metrics = g.getFontMetrics();
      Rectangle2D rect;
      
      //----------------- Draw name field -------------------------------------
      
      rect = metrics.getStringBounds(name, g);
      int width = (int)(rect.getWidth());
      int height = (int)(rect.getHeight());
      int hs = 10;
      if (!user) {
        g.setColor(Color.WHITE);
        g.fillOval(getHeight()/2,hs,8,8);
        g.setColor(highlight);
        g.drawOval(getHeight()/2,hs,8,8);
        g.setColor(inactive);
        g.drawString("C",hs,getHeight()/2+height/2-1);
        g.drawString("C",hs,getHeight()/2+height/2);
        g.drawString("C",hs,getHeight()/2+height/2+1);
        hs = 25;
      }
      g.setColor(foreground);
      
      g.drawString(name, hs, getHeight()/2+height/2);
      
      //----------------- Draw time field -------------------------------------
      
      rect = metrics.getStringBounds(time,g);
      width = (int)(rect.getWidth());
      height = (int)(rect.getHeight());
    
      int rightHSp = 15; // horizontal spacing from the right
    
      // Draw heighlight rectangle for time field
      if (active) {
        g.setColor(highlight);
        int sp = 2; // extra outer space
        g2D.fillRoundRect(
            getWidth()-width-sp*2-rightHSp,getHeight()/2-height/2-sp,
            width+4*sp, height+2*sp, 4, 4);
      }
      
      String cs; // time substring
      
      // Hours
      cs = time.substring(0,3);
      if (cs.equals("00:"))
        g.setColor(inactive);
      else
        g.setColor(foreground);
      g.drawString(
          cs, getWidth()-(width*3/3)-rightHSp+0, getHeight()/2+height/2);
      
      // Minutes
      cs = time.substring(3,6);
      if (cs.equals("00:"))
        g.setColor(inactive);
      else
        g.setColor(foreground);
      g.drawString(
          cs,getWidth()-(width*2/3)-rightHSp+1,getHeight()/2+height/2);
      
      // Seconds
      cs = time.substring(6,8);
      if (!cs.equals("00"))
        g.setColor(foreground);
      g.drawString(
          cs,getWidth()-(width*1/3)-rightHSp+2,getHeight()/2+height/2);
      
    }
  }
  
}
