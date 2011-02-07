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
 * Created on May 4, 2005
 *
 */
package chess;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import chess.gui.Chess;
import chess.gui.window.ErrorMessage;
import chess.gui.window.GameDetailsDialog;
import chess.gui.window.SplashScreen;

/**
 * Make sure the user's system is compatible and start the program if so.
 * @author Arvydas Bancewicz
 *
 */
public class Main {
  
  public static void main(String[] args) {
    
    boolean showSplash = true;
    boolean waitForSplash = true;
    
    SplashScreen splashScreen = null;
    
    try {
      //System.getProperties().list(System.out);
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      if (!checkSystemRequirements())
        System.exit(-1);
      
      splashScreen = new SplashScreen();
      
      ///int s = Integer.parseInt("23s");
      
      Chess.main(args);
      
    } catch (Exception e) {
      /*
      JOptionPane.showMessageDialog( null,
          e.toString()+"\n"+e.toString()+e.toString(),
          "Error",
          JOptionPane.ERROR_MESSAGE );
          */
      new ErrorMessage(null, e.toString());
    } finally {
      splashScreen.dispose();
    }
  }
  
  public static boolean checkSystemRequirements() {
    
    String javaVersion = System.getProperty("java.runtime.version");
    
    if (javaVersion==null)
      javaVersion = System.getProperty("java.version");
    
    System.out.println("java.version:"+javaVersion);
    if (javaVersion==null || javaVersion.compareTo("1.4")<0) {
		
      if (javaVersion==null)
        javaVersion = "unknown !?";
      
      new ErrorMessage(null,
          "Java Runtime Environment 1.4 or later required !\n"+
          "Your current version is "+javaVersion+"\n"+
          "     http://java.sun.com/j2se/downloads.html");
      
      return false;
    }
    return true;
  }
}
