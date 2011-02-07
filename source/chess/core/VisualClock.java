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
 * 
 * Created on Apr 19, 2005
 */

package chess.core;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import javax.swing.*;

/**
 * VisualClock displays a visual form of a VirtualClock
 * 
 * @author Arvydas Bancewicz
 *
 */
public class VisualClock extends JApplet {

  BasicStroke widePen = new BasicStroke(3.0f, // Line width
      BasicStroke.CAP_ROUND, // End cap
      BasicStroke.JOIN_MITER); // Line join
  
  BasicStroke narrowPen = new BasicStroke(1.0f,
      BasicStroke.CAP_ROUND,
      BasicStroke.JOIN_MITER);
  
  private int seconds = 40;
  private int minutes = 30;
  private int hours = 10;
  
  final double TWO_PI = 2*Math.PI;
  
  VirtualClock clock;
  
  private Hands hands;
  private ClockFace clockFace;
  
  public VisualClock() {
    init();
  }
  
  public void init() {
    clock = new VirtualClock();
    ActionListener a = clock.listener;
    
    //Dimension size = getSize();
    int clockDiameter = 100;//Math.min(size.width, size.height)*9/10;
    ((JComponent) getContentPane()).setPreferredSize(new Dimension(clockDiameter,clockDiameter));
    
    
    clockFace = new ClockFace(clockDiameter);
    getContentPane().add(clockFace);
    //setGlassPane(clockFace);
    //clockFace.setVisible(true);
    
    hands = new Hands(clockDiameter);
    setGlassPane(hands);
    hands.setVisible(true);
    //hands.repaint();
  }
  
  class ClockFace extends JPanel
  {
    // Creates a clock face with the given diameter
    public ClockFace(int diameter)
    {
      this.diameter = diameter;
      face = new Ellipse2D.Double();  
      hourMark = new Line2D.Double(0, -diameter*0.38,
                                   0, -diameter*0.48); 
      setOpaque(false);                    // Set panel transparent     
    }

    public void paint(Graphics g)
    {
      Dimension size = getSize();
      face.setFrame((size.width-diameter)/2,  // Set the size
                    (size.height-diameter)/2, // of the face centered
                      diameter, diameter);    // and of the given diameter

      Graphics2D g2D = (Graphics2D)g;

      // Clear the panel
      //g2D.setPaint(CLEAR);                 // Transparent color
      //g2D.fillRect(0,0,size.width,size.height); // Fill the background

      g2D.setPaint(Color.lightGray);       // Face color
      g2D.fill(face);                      // Fill the clock face
      g2D.setPaint(Color.darkGray);        // Face outline color
      //g2D.setStroke(widePen);              // Use wide pen
      g2D.draw(face);                      // Draw face outline

      // Move origin to center of face
      g2D.translate(size.width/2, size.height/2);

      // Paint hour marks
      for(int i = 0 ; i<12 ; i++)
      {
        if(i%3 == 0)
          g2D.setStroke(widePen);     // Wide pen each quarter position
        else 
          g2D.setStroke(narrowPen);   // otherwise narrow pen

        g2D.draw(hourMark);           // Draw the hour mark
        g2D.rotate(TWO_PI/12.0);      // Rotate to next mark
      }
    }

    int diameter;                            // Face diameter
    Ellipse2D.Double face;                   // The face
    Line2D.Double hourMark;                  // Mark for hours  
  }
  
  //Class defining the hands on the clock
  class Hands extends JPanel
  {
    public Hands(int clockDiameter)
    {
      center = new Ellipse2D.Double(-3,-3,6,6);                    
      // Central boss
      hourHand = new Line2D.Double(0,6,0,-clockDiameter*0.25);
      minuteHand = new Line2D.Double(0,8,0,-clockDiameter*0.3);
      secondHand = new Line2D.Double(0,14,0,-clockDiameter*0.35);
      setOpaque(false);                                            
      // Set transparent
    }

    // Paint the hands
    public void paint(Graphics g)
    {
      // Get hand angles for the current time
      double secondAngle = seconds*TWO_PI/60;
      double minuteAngle = (secondAngle+minutes*TWO_PI)/60;
      double hourAngle   = (minuteAngle+hours*TWO_PI)/12;

      Dimension size = getSize();
      Graphics2D g2D = (Graphics2D)g;
      g2D.setPaint(CLEAR);                      // Transparent color
      g2D.fillRect(0,0,size.width,size.height); // Fill to erase


      g2D.setPaint(Color.darkGray);             // Hands color
      g2D.translate(size.width/2, size.height/2); // Origin to center
      AffineTransform transform = g2D.getTransform(); // Save this xform

      // Draw hour hand
      g2D.setStroke(widePen);            // Use wide pen
      g2D.rotate(hourAngle);             // Rotate to hour position
      g2D.draw(hourHand);                // and draw hand

      // Draw minute hand
      g2D.setTransform(transform);       // Reset transform
      g2D.rotate(minuteAngle);           // Rotate to minute position
      g2D.draw(minuteHand);              // and draw hand

      // Draw second hand
      g2D.setStroke(narrowPen);          // Use narrow pen
      g2D.setTransform(transform);       // Reset transform
      g2D.rotate(secondAngle);           // Rotate to second position
      g2D.draw(secondHand);              // and draw hand

      g2D.setPaint(Color.white);         // Center color
      g2D.draw(center);                  // Draw center
    }

    Line2D.Double hourHand;
    Line2D.Double minuteHand;
    Line2D.Double secondHand;
    Ellipse2D.Double center;
    final Color CLEAR = new Color(0,0,0,0);
  }
}
