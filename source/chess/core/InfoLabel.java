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
 * Created on May 5, 2005
 *
 */

package chess.core;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;

/**
 * The info label is used by a DocumentThumbnail containing some player and 
 * game info, such as the player name, timer and the last move of the game.
 * 
 * @author Arvydas Bancewicz
 */
public class InfoLabel extends JLabel {
  
  Font font  = new Font("Serif", Font.BOLD,12);
  Font font2 = new Font("Serif", Font.PLAIN, 12);
  Color foreground = new Color(100,100,100);
  Color highlight  = new Color(230,230,230);
  String white;
  String black;
  
  private ChessGame game;
  private boolean selectedGame = true;
  
  public void setSelected(boolean selected) {
    selectedGame = selected;
  }
  
  public InfoLabel(ChessGame game) {
    setPreferredSize(new Dimension(80,50));
    this.game = game;
    try {
    white = game.getWhiteName();
    black = game.getBlackName();
    } catch (Exception e){
      white = black = "";
    }
    repaint();
  }
  
  public void paint(Graphics g) {
    FontMetrics metrics = g.getFontMetrics();
    Rectangle2D rect;
    int height;
    
    try {
    white = game.getWhiteName();
    black = game.getBlackName();
    } catch (Exception e) {
      white = "";
      black = "";
    }
    
    
    
    // Draw default label background
    super.paint(g);
    
    // Set graphic antialiasing on
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    
    //----------------- Draw names ----------------------------------------
    g.setFont(font);
    if (selectedGame)
      g.setColor(highlight);
    else
      g.setColor(foreground);
    
    rect = metrics.getStringBounds(white, g);
    height = (int)(rect.getHeight());
    
    g.drawString("W: "+white, 0, height);
    g.drawString("B: "+black, 0, height*2);
    
    //----------------- Draw last move ------------------------------------
    g.setFont(font2);
    try {
      int num = game.getMoveCount();
      String lm = num+") "+(game.getTurn()?"... ":" ")
                          + game.getLastMove().toString();
      rect = metrics.getStringBounds(lm , g);
      g.drawString(lm,0,height*3+3);
    } catch (Exception e){}
  }
}
