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
 * Created on Mar 23, 2005
 *
 */
package chess.gui.window;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import chess.media.BoardMedia;

/**
 * 
 * @author Arvydas Bancewicz
 *
 */
public class SplashScreen extends JDialog {
  
  private boolean loading = true;
  private JLabel label;
  
  private Image splash;
  
  public SplashScreen() {
    setTitle("Startup");
    
    Container cpane = getContentPane();
    splash = BoardMedia.getLargeLogo();
    
    setSize(splash.getWidth(null),splash.getHeight(null));
    
    getContentPane().setLayout(new BorderLayout());
    
    label = new JLabel("  Initializing ... ");//,JLabel.CENTER);
    //label.setBorder(new LineBorder(Color.GRAY));
    cpane.add(label,BorderLayout.SOUTH);
    
    setUndecorated(true);
    //pack();
    setLocationRelativeTo(null); // Position window to center of screen
    
    //setBackground(Color.WHITE);
    setVisible(true);
    
    //setAlwaysOnTop(true);
  }
  
  public void paint(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0,0,getWidth(),getHeight());
    
    g.drawImage(splash, 0, 0, this);
    
    //g.setColor(Color.BLACK);
    //g.setFont(new Font("SansSerif",Font.PLAIN,12));
	//g.drawString("version 6.0", 250,224);
	//g.drawString("Copyright ", 250,248);
	
    FontMetrics metrics = g.getFontMetrics();
    
    if (!loading) {
      String prompt = "Click to enter";
      Rectangle2D rect = metrics.getStringBounds(prompt, g);
      int height = (int) rect.getHeight();
      int width = (int) rect.getWidth();
      g.setColor(getBackground());
      g.fillRect(0,getHeight()-height,getWidth(),height);
      g.setColor(Color.BLACK);
      g.drawString(prompt,(getWidth()-width)/2,getHeight()-4);
    }
	
    // Draw Line Border
    g.setColor(Color.GRAY);
    //((Graphics2D)g).setStroke(pen);
    g.drawRect(0,0,getWidth()-1,getHeight()-1);
  }
  
  public void setReady() {
    loading = false;
    repaint();
    //label.setText(label.getText()+"Ready.   Click to enter");
  }
  
  public boolean isReady() {
    return !loading;
  }
}
