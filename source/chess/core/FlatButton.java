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
 * Created on Mar 25, 2005
 *
 */

package chess.core;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.JPanel;

/**
 * FlatButton is a custom button in vertical left-top orientation
 * 
 * @author Arvydas Bancewicz
 *
 */
public class FlatButton extends JPanel {
  
  private boolean mouseOver;
  private boolean mouseClicked;
  private AffineTransform at;
  private int clickCount;
  private String label;
  private Font font;
  private Component content;
  
  private boolean armed = true;
  
  private Point strPos;
  
  public FlatButton(String name) {
    setPreferredSize(new Dimension(20,78));
    setMinimumSize(new Dimension(20,78));
    label = name;
    
    add("Documents ",new JPanel());
    
    clickCount=-1;
    at = new AffineTransform();
    
    strPos = new Point(30,30);
    at.setToRotation(-Math.PI/2.0,strPos.x,strPos.y);
    font = new Font("",Font.TRUETYPE_FONT,12);
    
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        mouseClicked = true;
        repaint();
      }
      public void mouseReleased(MouseEvent e) {
        if (armed)
          armed = false;
        else
          armed = true;
        mouseClicked = false;
        repaint();
      }
      public void mouseEntered(MouseEvent e) {
        mouseOver = true;
        repaint();
      }
      public void mouseExited(MouseEvent e) {
        mouseOver = false;
        repaint();
      }
    });

  }
  
  public void paint (Graphics g) {
    //super.paintComponent(g);
    
    if(armed)
      g.setColor(Color.LIGHT_GRAY);
    else
      g.setColor(Color.WHITE);
    
    g.fillRect(0,0,getSize().width,getSize().height);

    Graphics2D g2d = (Graphics2D)g;
    g2d.setFont(font);
    
    FontMetrics metrics = g.getFontMetrics();
    Rectangle2D rect;
    
    //----------------- Draw name field -------------------------------------
    
    g.setColor(Color.BLUE);
    rect = metrics.getStringBounds(label, g);
    int width = (int)(rect.getWidth());
    int height = (int)(rect.getHeight());
    
    AffineTransform origXform = g2d.getTransform();
    AffineTransform newXform = (AffineTransform)(origXform.clone());
    //center of rotation is center of the panel
    
    int x = getWidth()/2+height/2;
    int y = getHeight()/2+width/2;
    
    newXform.rotate(Math.toRadians(-90),x,y);
    g2d.setTransform(newXform);
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //draw image centered in panel
    g2d.drawString(label,x,y);
    
    
    if(clickCount<0) {
      //repaint();
      clickCount++;
    }
    //g2d.setTransform(origXform);
    
  }
  
}