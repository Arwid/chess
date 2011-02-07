/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*                              Java Chess                                      *
*         Copyright (C) 2005  Arvydas Bancewicz and Ihor Lesko                 *
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
 * Created on Apr 1, 2005
 *
 */
package chess.gui.lookAndFeel;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToggleButtonUI;

/**
 * This class represents the UI delegate for the JToggleButton component.
 *
 * @author Taoufik Romdhane
 */
public class ToggleButtonUI extends MetalToggleButtonUI
{

  /**
   * The Cached UI delegate.
   */
  private static final ToggleButtonUI buttonUI =
    new ToggleButtonUI();

  /**
   * Creates the UI delegate for the given component.
   *
   * @param c The component to create its UI delegate.
   * @return The UI delegate for the given component.
   */
  public static ComponentUI createUI(JComponent c)
  {
    return buttonUI;
  }

  /**
   * Paints the given component.
   *
   * @param g The graphics context to use.
   * @param c The component to paint.
   */
  public void paint(Graphics g, JComponent c)
  {
    super.paint(g, c);
    AbstractButton button = (AbstractButton)c;
    ButtonModel model = button.getModel();

    // Don't paint gradients on pressed buttons!
    if (!model.isPressed() && !model.isSelected())
    {
      // If the button is on a toolbar and the mouse is over, then paint the
      // gradients.
      if (button.isContentAreaFilled() || button.getModel().isRollover())
      {
        // Paint the horizontal highlight gradient:
        //MetouiaGradients.drawHorizontalHighlight(g, c);

        // Paint the horizontal shadow gradient:
        //MetouiaGradients.drawHorizontalShadow(g, c);
        
        //drawHighlight(g,new Rectangle(0, 0, c.getWidth(), c.getHeight()));
        g.setColor(selectColor);
        g.fillRect(0,0,button.getWidth(),button.getHeight());
        
        super.paint(g,c);
        return;
      }
    }
  }
  
  

  /**
   * Paints the background of given component whenit is pressed.
   *
   * @param g The graphics context to use.
   * @param button The button to paint its background.
   */
  protected void paintButtonPressed(Graphics g, AbstractButton button)
  {
    if (button.isContentAreaFilled() || button.getModel().isRollover())
    {
      if (button.isSelected()) {
        //g.setColor(selectColor);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, button.getWidth(), button.getHeight());
      } else {
        g.setColor(selectColor);
        g.fillRect(0, 0, button.getWidth(), button.getHeight());
        return;
      }
    } else {
      //g.setColor(Color.RED);
      //g.fillRect(0,0,button.getWidth(),button.getHeight());
    }
    int thickness = 4;
    g.setColor(Color.BLUE);
    g.fillRect(0,button.getHeight()-thickness,button.getWidth(),button.getHeight()-thickness);
  }
  
  public static final void drawHighlight(Graphics graphics, Rectangle rectangle)
    {
      Graphics2D graphics2D = (Graphics2D)graphics;
      GradientPaint g = new GradientPaint(rectangle.x,rectangle.y,Color.WHITE,rectangle.width,rectangle.height,Color.GRAY,false);
     
      graphics2D.setPaint(g);
      graphics2D.fill(rectangle);
    }

}