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
 * Created on Apr 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package chess.gui.lookAndFeel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class GrayTheme extends DefaultMetalTheme {

  private final ColorUIResource pr1 = new ColorUIResource(new Color(220,220,220));
  private final ColorUIResource pr2 = new ColorUIResource(213, 208, 198);
  private final ColorUIResource pr3 = new ColorUIResource(new Color(210,210,210));
  private final ColorUIResource sc1 = new ColorUIResource(133, 124, 93);
  private final ColorUIResource sc2 = new ColorUIResource(171, 171, 159);
  private final ColorUIResource sc3 = new ColorUIResource(new Color(220,220,220));
  private final ColorUIResource sc4 = new ColorUIResource(190, 190, 170);
  private final Color gradientReflection = new Color(255, 255, 255, 86);
  private final Color gradientShadow = new Color(188, 188, 180, 100);
  private final Color gradientTranslucentReflection =
    new Color(gradientReflection.getRGB() & 0x00FFFFFF, true);
  private final Color gradientTranslucentShadow =
    new Color(gradientShadow.getRGB() & 0x00FFFFFF, true);
  private FontUIResource plainFont = 
    new FontUIResource("SansSerif", Font.PLAIN, 12);
  private FontUIResource boldFont =
    new FontUIResource("SansSerif", Font.PLAIN, 12);
  public Color getGradientReflection(){
    return gradientReflection;
  }
  public Color getGradientShadow(){
    return gradientShadow;
  }
  public Color getGradientTranslucentReflection(){
    return gradientTranslucentReflection;
  }
  public Color getGradientTranslucentShadow(){
    return gradientTranslucentShadow;
  }
  public FontUIResource getControlTextFont(){
    return plainFont;
  }
  public FontUIResource getMenuTextFont(){
    return plainFont;
  }
  public FontUIResource getSystemTextFont(){
    return plainFont;
  }
  public FontUIResource getUserTextFont(){
    return plainFont;
  }
  public FontUIResource getWindowTitleFont(){
    return boldFont;
  }
  public ColorUIResource getMenuSelectedBackground(){
    return new ColorUIResource(231, 231, 219);
  }

  public ColorUIResource getSeparatorForeground(){
    return getPrimary2();
  }
  public String getName(){
    return "Gray Theme";
  }
  protected ColorUIResource getPrimary1(){
    return pr1;
  }
  protected ColorUIResource getPrimary2(){
    return pr2;
  }
  protected ColorUIResource getPrimary3(){
    return pr3;
  }
  protected ColorUIResource getSecondary1(){
    return sc1;
  }
  protected ColorUIResource getSecondary2(){
    return sc2;
  }
  protected ColorUIResource getSecondary3(){
    return sc3;
  }
  public ColorUIResource getPressedBackground(){
    return sc4;
  }
}