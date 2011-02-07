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
 * Created on May 3, 2005
 *
 */

package chess.core;

import java.io.*;

import chess.gui.panels.ChessComponent;

/**
 * ChessFile can open an existing ChessGame or save a ChessGame
 * 
 * @author Arvydas Bancewicz
 */

public final class ChessFile {
  
  private final static ChessFile cf;
  
  static {
    cf = new ChessFile();
  }
  
  public static ChessFile getInstance() {
    return cf;
  }
  
  /**
   * 
   * @param file
   */
  public void openFile(String file) {
    FileInputStream f_in;
    try {
      f_in = new FileInputStream(file);
      
      //     Read object using ObjectInputStream
      ObjectInputStream obj_in = 
      	new ObjectInputStream (f_in);

      //     Read an object
      Object obj = obj_in.readObject();

      if (obj instanceof ChessGame)
      {
      	// Cast object to a Vector
      	ChessGame game = (ChessGame) obj;
      	//game.refreshBoard();

      	ChessDocuments.getInstance().add(game);
      	//ChessComponent.getInstance().view.refreshList();
      	ChessComponent.getInstance().view.addToList();
      	
      	int index = ChessDocuments.getInstance().getSize()-1;
        ChessComponent.getInstance().view.setSelected(index);
        ChessComponent.getInstance().chessSideBar.refresh(game);

        System.out.println("********************** DONE *********************");
      	// Do something with vector....
      }

    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }
  
  /**
   * 
   * @param file
   */
  public void saveFile(String file) {

    //  Write to disk with FileOutputStream
    FileOutputStream f_out;
    try {
      f_out = new 
      	FileOutputStream(file);
      
      //     Write object with ObjectOutputStream
      ObjectOutputStream obj_out = new
      	ObjectOutputStream (f_out);

      //     Write object out to disk
      ChessGame t = ChessComponent.getInstance().chessGame;
      
      obj_out.writeObject ( t );
      
     
    } catch (Exception e1) {
      e1.printStackTrace();
    }

  }
}
