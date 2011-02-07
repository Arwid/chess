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
 * Created on Mar 05, 2005
 * 
 */

package chess.properties;

import java.util.prefs.*;
import java.awt.Point;

import chess.core.Coord;
import chess.core.Piece;
import chess.gui.Chess;

public class ChessPreferences {
  private static Preferences prefs = Preferences.userNodeForPackage(Chess.class); 
  
  public static void saveBoard(Piece[][] board) {    
    byte[][] nboard = new byte[8][8];
    for(int c=0;c<8;c++)
      for(int r=0;r<8;r++)
        if(board[c][r]!=null)
          nboard[c][r] = (byte)(board[c][r].type+1+(board[c][r].white?10:0));
        else
          nboard[c][r] = 0;
    for(int i=0;i<8;i++)
      prefs.putByteArray("boardR"+i,nboard[i]);
  }
  
  public static void saveTurn(boolean turn) {
    prefs.putBoolean("turn",turn);
  }
  
  public static void saveWhiteKing(Coord king) {
    prefs.putInt("whiteKingX",king.x);
    prefs.putInt("whiteKingY",king.y);
  }
  
  public static void saveBlackKing(Coord king) {
    prefs.putInt("blackKingX",king.x);
    prefs.putInt("blackKingY",king.y);
  }
  
  public static Point getPosition() {
    return new Point(prefs.getInt("xpos",0),prefs.getInt("ypos",0));
  }
  
  public static byte[][] getBoard() {
    byte[][] nboard = new byte[8][8];
    for(int i=0;i<8;i++)
      nboard[i] = prefs.getByteArray("boardR"+i,new byte[8]);
    return nboard;
  }
  
  public static Coord getWhiteKing() {
    return new Coord(prefs.getInt("whiteKingX",0),prefs.getInt("whiteKingY",0));
  }
  
  public static Coord getBlackKing() {
    return new Coord(prefs.getInt("blackKingX",0),prefs.getInt("blackKingY",0));
  }
  
  public static boolean getTurn() {
    return prefs.getBoolean("turn",true);
  }
}
