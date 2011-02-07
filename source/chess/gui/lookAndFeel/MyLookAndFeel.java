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
 * Created on Mar 27, 2005
 *
 */

package chess.gui.lookAndFeel;

import java.awt.*;

import javax.swing.UIDefaults;
import javax.swing.border.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

public class MyLookAndFeel extends MetalLookAndFeel { //WindowsLookAndFeel {
  
  protected static DefaultTheme theme;
  private static boolean themeHasBeenSet = false;
  
  
  public MyLookAndFeel ()
  {
    System.out.println ("MyLookAndFeel");
  }

  public String getName() 
  {
    System.out.println ("getName");
    return "MyLookAndFeel";
  }
  
  public String getDescription() 
  {
    System.out.println ("getDescription");
    return "The My Look and Feel";
  }
  
  public boolean isNativeLookAndFeel() 
  {
    System.out.println ("isNative");
    return false;
  }
  
  public boolean isSupportedLookAndFeel() 
  {
    System.out.println ("isSupported");
    return true;
  }

  public String getID ()
  {
    System.out.println ("getID");
    return "MyLookAndFeel";
  }
  
  protected void createDefaultTheme()
  {
    if (!themeHasBeenSet)
    {
      theme = new DefaultTheme();
      setCurrentTheme(theme);
    }
  }
  
  public static void setCurrentTheme(MetalTheme theme)
  {
    MetalLookAndFeel.setCurrentTheme(theme);
    themeHasBeenSet = true;
  }
  
  protected void initSystemColorDefaults(UIDefaults table) {
    super.initSystemColorDefaults(table);
    
    ColorUIResource pr1 = new ColorUIResource(new Color(220,220,220));
    ColorUIResource pr2 = new ColorUIResource(Color.GRAY);
    ColorUIResource pr3 = new ColorUIResource(new Color(210,210,210));
    ColorUIResource pr4 = new ColorUIResource(Color.LIGHT_GRAY);
    ColorUIResource blk = new ColorUIResource(Color.BLACK);
    ColorUIResource wht = new ColorUIResource(Color.WHITE);
    ColorUIResource gry = new ColorUIResource(Color.GRAY);
    
    Object[] colors = {

        "desktop"              , pr1, /* Color of the desktop background */
        "activeCaption"        , pr3, /* Color for captions (title bars) when they are active. */
        "activeCaptionText"    , wht, /* Text color for text in captions (title bars). */
        "activeCaptionBorder"  , gry, /* Border color for caption (title bar) window borders. */
        "inactiveCaption"      , pr1, /* Color for captions (title bars) when not active. */
        "inactiveCaptionText"  , gry, /* Text color for text in inactive captions (title bars). */
        "inactiveCaptionBorder", gry, /* Border color for inactive caption (title bar) window borders. */
        "window"               , wht, /* Default color for the interior of windows */
        "windowBorder"         , gry, /* Color of the window border */
        "windowText"           , blk, /* Color of the window text */
        "menu"                 , pr1, /* Background color for menus */
        "menuText"             , blk, /* Text color for menus  */
        "text"                 , pr1, /* Text background color */
        "textText"             , blk, /* Text foreground color */
        "textHighlight"        , pr4, /* Text background color when selected */
        "textHighlightText"    , wht, /* Text color when selected */
        "textInactiveText"     , gry, /* Text color when disabled */
        "control"              , pr1, /* Default color for controls (buttons, sliders, etc) */
        "controlText"          , blk, /* Default color for text in controls (buttons, sliders, etc) */
        "controlHighlight"     , pr4, /* Highlight color for controls */
        "controlLtHighlight"   , wht, /* Lighter highlight color for controls */
        "controlShadow"        , pr2, /* Shadow color for controls */
        "controlDkShadow"      , gry, /* Dark shadow color for controls */
        "scrollbar"            , pr3, /* Scrollbar background (usually the "track") */
        "info"                 , wht, /* Background color for informational text */
        "infoText"             , blk  /* Color for informational text */
        
    };
    
    //table.putDefaults(colors);
  }
  
  protected void initClassDefaults (UIDefaults table) 
  {
    System.out.println ("initClassDef");
    super.initClassDefaults (table);
    String pkg = "chess.gui.";
    Object[] classes = {
        //"ToggleButtonUI",pkg+"ToggleButtonUI",
        "TabbedPaneUI",pkg+"TabbedPaneUI",
        "ToolBarUI",pkg+"ToolBarUI",
        "MenuBarUI",pkg+"MenuBarUI",
        "MenuUI",pkg+"MenuUI",
        //"ButtonUI",pkg+"ButtonUI",
        //"ScrollBarUI",pkg+"ScrollBarUI",
        //"ToggleButtonUI",pkg+"ToggleButtonUI",

      };

      table.putDefaults(classes);
    
  }
  
  protected void initComponentDefaults(UIDefaults table) {

    super.initComponentDefaults(table);

    Object[] components = {

      //"CheckBox.icon"    , new Object(),
      //"Button.background", Color.BLUE,
      //"Button.foreground", Color.GRAY,
      //"Button.font"      , new Font("Times",Font.BOLD|Font.ITALIC,10),
      "SplitPane.border" , new EmptyBorder(0,0,0,0),
      //"SplitPaneDivider.border" , new EmptyBorder(0,0,0,0),
      //"Menu.borderPainted" , new Boolean(false),
      ///"Menu.border"      , new EtchedBorder(),
      //"Menu.selectionBackground", new Color(100,100,100),
      //"MenuItem.selectionBackground",new Color(100,100,100),
      //"MenuItem.background", Color.WHITE,
      //"MenuItem.border", new EtchedBorder(),
      //"RadioButton.focus",new Color(163,184,204),
      //"ScrollBar.thumb",new Color(163,184,204),
      //"ProgressBar.background",new Color(249,249,249),
      //"Component.disabledBackground" ,new Color(226,226,226),
      //"SysButton.font",new Font("Dialog",Font.PLAIN,12),
      //"Component.inactiveForeground",new Color(182,182,182),
      //"Button.textIconGap",new Integer(4),
      //"ScrollBar.thumbShadow",new Color(99,130,191),
      //"TabbedPane.selectedBackground",new Color(226,226,226),
      //"RadioButton.icon",MetalIconFactory.getRadioButtonIcon(),
      
      //"MenuItem.selectionBackground",new Color(200,200,180),
      //"Menu.selectionBackground",new Color(180,180,180),
      //"CheckBoxMenuItem.selectionBackground",new Color(200,200,180),
      //"Panel.background",new Color(220,220,220),
      //"MenuBar.background",new Color(210,210,210),
      //"Menu.background",new Color(210,210,210),
      //"SplitPane.background",new Color(220,220,220),
      
    };

    table.putDefaults(components);
    
    // Replace the Metal borders:
    Border border;
    //table.put("Button.border", new CompoundBorder(new EtchedBorder(),new EmptyBorder(4,8,4,8)));
    //table.put("ToggleButton.border", MetouiaBorderUtilities.getToggleButtonBorder());
    //table.put("TextField.border", MetouiaBorderUtilities.getTextFieldBorder());
    //table.put("ToolBar.border", new MetouiaToolBarBorder());
    ////table.put("MenuBar.border", new MetouiaMenuBarBorder());
    //table.put("ScrollPane.border", new MetouiaScrollPaneBorder());
    //table.put("InternalFrame.border", new MetouiaInternalFrameBorder());
    //table.put("InternalFrame.paletteBorder", new MetouiaPaletteBorder());
    //table.put("InternalFrame.optionDialogBorder", new MetouiaOptionDialogBorder());
    //border = new MetouiaMenuItemBorder();
    //table.put("Menu.border", border);
    //table.put("MenuItem.border", border);
    //table.put("CheckBoxMenuItem.border", border);
    //table.put("RadioButtonMenuItem.border", border);
    //table.put("PopupMenu.border", new MetouiaPopupMenuBorder());
    //table.put("TableHeader.cellBorder", new MetouiaTableHeaderBorder());

    // Tweak some subtle values:
    table.put("SplitPane.dividerSize", new Integer(6));
    table.put("InternalFrame.paletteTitleHeight", new Integer(13));
    table.put("InternalFrame.frameTitleHeight", new Integer(21));
    table.put("TabbedPane.contentBorderInsets", new Insets(4, 4, 3,3));
//    "TabbedPane.contentBorderInsets", tabbedPaneContentBorderInsets,
    table.put("Button.select", theme.getPressedBackground());
    table.put("RadioButton.select", theme.getPressedBackground());
    table.put("ToggleButton.select", theme.getPressedBackground());
    table.put("Checkbox.select", theme.getPressedBackground());

  }
}
