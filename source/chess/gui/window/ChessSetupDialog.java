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
package chess.gui.window;

import java.awt.event.*;

import javax.swing.*;

import chess.core.ChessGame;
import chess.gui.board.ChessBoard;
import chess.gui.board.ChessBoardSetup;




public class ChessSetupDialog extends JDialog implements ActionListener {
    
    public ChessSetupDialog(JFrame frame) {
      super(frame,"Chess Set",true);
      
      pack();
      
      // Compute the layout. Validate effectively redoes the layout if necessary
      // deciding on new sizes and locations of all the components in the conainer.
      validate();
      
      JPanel board = new JPanel();
      ChessBoard b = new ChessBoardSetup(new ChessGame());
      board.add(b);
      add(board);
      
      // Position the window to the center of the screen
      setLocationRelativeTo(null);
      
      // Prevent the window from resizing
      //setResizable(false);
    }
    
    public void actionPerformed(ActionEvent arg0) {}
}
