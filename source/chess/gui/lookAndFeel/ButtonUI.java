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
 * Created on Mar 30, 2005
 *
 */

package chess.gui.lookAndFeel;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class ButtonUI extends BasicButtonUI {

  //The singleton istance of BlueButtonUI

  static ButtonUI buttonUI = new ButtonUI();

  //Default background and foreground

  Color background;

  Color foreground;

  //There will be only one font for this these buttons

  Font font;

  public ButtonUI() {
    super();
  }
  
  /**
   * Installs some default values for the given button.
   * The button border is replaced by a metouia border.
   *
   * @param button The reference of the button to install its default values.
   */
  public void installDefaults(AbstractButton button)
  {
    super.installDefaults(button);
    //button.setBorder();
  }
 
 //The factory method returns the singleton

 public static ComponentUI createUI(JComponent c) {
      return buttonUI;
  }
 
/*
  public void installUI(JComponent c) {

    //Since we know this is a JButton it is safe to cast as an AbstractButton

    AbstractButton b = (AbstractButton)c;

    //Setting the default values from the UIDefaults table

    background = UIManager.getColor("Button.background");

    foreground = UIManager.getColor("Button.foreground");

    font       = UIManager.getFont("Button.font");

    //Checking for user set values for foreground and background before setting them

    //Note that the font compnonent is not checked therefore the value from the UIDefaults table will

    //override the user’s values (This is not recommended) further not all the defaults are set

    if(c.getBackground()==null || (c.getBackground() instanceof UIResource))

      c.setBackground(background);

    if(c.getForeground()==null || (c.getForeground() instanceof UIResource))

      c.setForeground(foreground);
    
    //Using BasicButtonUI installListeners method to install listeners

    super.installListeners(b);

   

    /*Note that there are no keyboard registations, therefore hit any of the keys will not invoke an event

  }
*/

 

public void paint(Graphics g, JComponent c) {

  super.paint(g, c);
  AbstractButton button = (AbstractButton)c;
  ButtonModel model = button.getModel();

  // Don't paint gradients on pressed buttons!
  if (!model.isPressed())
  {
    // If the button is on a toolbar and the mouse is over, then paint the
    // gradients.
    if (button.isContentAreaFilled() || button.getModel().isRollover())
    {
      //g.drawRect(0,0,button.getWidth(),button.getHeight());
    }
    
  } else {
    //g.fillRect(0,0,button.getWidth(),button.getHeight());
  }
  
  /*
   //Once again it is safe to cast as an AbstractButton because we know it is a JButton

    AbstractButton b = (AbstractButton)c;

   //The ButtonModel holds a lot of the functional state of the button

    ButtonModel model = b.getModel();

   //Casting to a Graphics2D for convenience, this is safew because we know that the g object is really a Graphics2D object

    Graphics2D g2 = (Graphics2D)g;

 

    //Sets the arcs widths and heights

    int arc_w = (int)c.getHeight()/2;

    int arc_h = arc_w;

 

    Insets i = c.getInsets();

    //Gets the area for the text and icon to be painted in with respects to the insets

    Rectangle viewRect = new Rectangle(i.left,i.top,b.getWidth()-(i.right+i.left),b.getHeight() - (i.bottom + i.top));

    //the area that the text will be drawn in that will be defined

    //by SwingUtilities.layoutCompoundLabel

    Rectangle textRect = new Rectangle(0,0,0,0);

    //the area that the icon will be drawn in that will be defined

    //by SwingUtilities.layoutCompoundLabel

    Rectangle iconRect = new Rectangle(0,0,0,0);

 

    //I have opted to set the base font size on the size of the button this will cause the font size to skrink or grow with respect to the button size

    int fontSize = (int)c.getHeight()/3;

    if(fontSize<8)

      fontSize = 8;

    //g2.setFont(new Font(font.getName(),font.getStyle(),fontSize));

    //modify text for display, will add ... if clipped and

    //determine the text area and icon area

    String text = SwingUtilities.layoutCompoundLabel(

            c, g2.getFontMetrics(), b.getText(), b.getIcon(),

            b.getVerticalAlignment(), b.getHorizontalAlignment(),

            b.getVerticalTextPosition(), b.getHorizontalTextPosition(),

            viewRect, iconRect, textRect,

           b.getText() == null ? 0 : b.getIconTextGap());

 

    //Starting with a BufferedImage because the graphics object from a BufferedImage respects composite overlay directives

    //NOTE the Graphics object passed in to this method does not respect these directives

    BufferedImage buffImg = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);

    Graphics2D gbi = buffImg.createGraphics();

    //Retrieving the state of the colors from the component which were set in the installUI method

    Color back = Color.BLACK;

    Color fore = Color.WHITE;//c.getForeground();

 

    //creating a semi-transparent background for the button

    Color bg = new Color(back.getRed(),back.getGreen(),back.getBlue(),127);

    //Defining the color of my borders

    Color wh = Color.WHITE;

    Color gr = Color.WHITE;

    //if button is pressed change the background to dark and switch the border colors (this makes it appear that the button is pressed in)

    if (model.isArmed() && model.isPressed()) {

      Color d = back.darker().darker().darker();

      bg = new Color(d.getRed(),d.getGreen(),d.getBlue(),127);

      wh = Color.GRAY;

      gr = Color.LIGHT_GRAY;

    }
    
    if(model.isSelected()) {
      gr = Color.BLUE;
    }

 

    //set background color

    gbi.setColor(bg);

    gbi.fillRoundRect(0,0,c.getWidth(),c.getHeight(),arc_w,arc_h);

    //lay in the strips

    gbi.setColor(Color.BLACK);

    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,1.0f));

    gbi.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);



 

    //paint button image

    g2.drawImage(buffImg,0,0,c);

    //Draw borders (NOTE a better implementation would have created a borders object)

    g2.setColor(wh);

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setStroke(new BasicStroke(2.0f));

    Arc2D.Double  ar1;

    ar1 = new Arc2D.Double(0,0,arc_w,arc_h,90,90,Arc2D.OPEN);

    g2.draw(ar1);

    ar1 = new Arc2D.Double(c.getWidth()-arc_w,1,arc_w,arc_h,0,90,Arc2D.OPEN);

    g2.draw(ar1);

    g2.fillRect(arc_w/2-2,0,c.getWidth()-arc_w+2,2);

    g2.fillRect(0,arc_h/2-2,2,c.getHeight()-arc_h+2);

 

    g2.setColor(gr);

    ar1 = new Arc2D.Double(c.getWidth()-arc_w,c.getHeight()-arc_h,arc_w,arc_h,270,90,Arc2D.OPEN);

    g2.draw(ar1);

    ar1 = new Arc2D.Double(0,c.getHeight()-arc_h,arc_w,arc_h,180,90,Arc2D.OPEN);

    g2.draw(ar1);

    g2.fillRect(c.getWidth()-1,arc_h/2-2,1,c.getHeight()-arc_h+8);

    g2.fillRect(arc_w/2-8,c.getHeight()-2,c.getWidth()-arc_w+16,2);

 
    g2.fillRect(0,0,c.getWidth(),c.getHeight());

    //painting text

    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    g2.setColor(fore);

    //draw the text at the x of the textRect and the y textRect plus the font ascent.

    //"The font ascent is the distance from the font's baseline to the top of most

    //alphanumeric characters."(From Java API Doc on java.awt.FontMetrics.getAscent())
    
    g2.setColor(Color.BLACK);

    g2.drawString(text,(int)textRect.getX(),(int)textRect.getY()+g2.getFontMetrics().getAscent());

    //If there is an icon paint it at the x and y of the iconRect

    if(b.getIcon()!=null)

      b.getIcon().paintIcon(c,g,(int)iconRect.getX(),(int)iconRect.getY());
    
    */
  }
  
}