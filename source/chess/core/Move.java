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
 * Created on Dec 11, 2004
 *
 */

package chess.core;

import java.io.Serializable;

/**
 * Move contains the two coordinates for piece from and to location.
 * Move also can be performed on a board or game.
 * @author Arvydas Bancewicz
 *
 */
public class Move implements Constants, Serializable{
  
  private String eat = "";
  private boolean pawnc = false;
  private Move rook;
  public Piece p = null; // piece taken
  public int type;
  
  //private boolean enpassant = false;
  
  public byte x1;
  public byte y1;
  public byte x2;
  public byte y2;
  
  /* Constructor sets up values for a move */
  public Move(int c, int r, int c2, int r2) {
    this((byte)c, (byte)r, (byte)c2, (byte)r2);
  }
  
  /* Save move values */
  public Move(byte a, byte b, byte c, byte d) {
    x1 = a;
    y1 = b;
    x2 = c;
    y2 = d;
    rook = null;
  }
  
  public void perform(Piece[][] board, ChessGame game) {
    board[x2][y2] = board[x1][y1];
    board[x1][y1] = null;
  }
  
  /* Perform the move in specific grid */
  public void perform(Board bOrd){
    Piece[][] board = bOrd.b;
    type = board[x1][y1].type;
    
    p = bOrd.b[x2][y2];
    if(p!=null) {
      if(p.white) {
        //game.changePieceCount(true,false,p);
      } else
        //game.changePieceCount(false,false,p);
      eat = "x";
    } else
      eat = "";
    board[x2][y2] = board[x1][y1];
    board[x1][y1] = null;
    
    //switch(board[x2][y2].type) {
    //  case(TYPE_PAWN): return;
    //}
    
    /*
    // Castling validity
    if(grid[x2][y2].type==TYPE_KING) {
      if(grid[x2][y2].white) {
        g.whiteCastleLong = false;
        g.whiteCastleShort = false; 
      } else {
        g.blackCastleLong = false;
        g.blackCastleShort = false;
      }
    }
    if(grid[x2][y2].type==TYPE_ROOK) {
      if(grid[x2][y2].white) {
        if(x1==7)
          g.whiteCastleShort = false;
        else if (x1==0)
          g.whiteCastleLong = false;
      } else {
        if(x1==7)
          g.blackCastleShort = false;
        else if (x1==0)
          g.blackCastleLong = false;
      }
    }
    */
    
    
    // Pawn Promotion
    
    if(board[x2][y2].type==TYPE_PAWN && (y2==0 || y2==7)) {
      board[x2][y2].setType(TYPE_QUEEN);
      //board[x2][y2].setImage();
      pawnc = true;
    }
    if(board[x2][y2].type==TYPE_KING) {
      // Change king coordinates
      
      if(board[x2][y2].white)
        bOrd.whiteKing.setCoord(x2,y2);
      else
        bOrd.blackKing.setCoord(x2,y2);
      
      //System.out.println("King coordinates changed: white "+game.whiteKing.toString()+" black "+ game.blackKing.toString());
      // Move rook when castling
      if(y1==0 && x1==4 && x2==6)      //white short castle
        rook = new Move(7,0,5,0);
      else if(y1==0 && x1==4 && x2==2) //white long castle
        rook = new Move(0,0,3,0);
      else if(y1==7 && x1==4 && x2==6) //black short castle
        rook = new Move(7,7,5,7);
      else if(y1==7 && x1==4 && x2==2) //black long castle
        rook = new Move(0,7,3,7);
      if(rook!=null)
        rook.perform(bOrd); 
    }
    
  }
  
  public void undo(Piece[][] board) {
    board[x1][y1] = board[x2][y2];
    board[x2][y2] = p;
    p = null;
  }
  
  /* Undo the move */
  public void undo(Board game){
    Piece[][] board = game.b;
    
    if(board[x2][y2].type==TYPE_KING) {
      if(board[x2][y2].white)
        game.whiteKing.setCoord(x1,y1);
      else
        game.blackKing.setCoord(x1,y1);
      if(rook!=null) {
        rook.undo(game);
        rook = null;
      }
    }
    
    board[x1][y1] = board[x2][y2];
    board[x2][y2] = p;
      
    if(pawnc) {
      board[x1][y1].setType(TYPE_PAWN);
      pawnc = false;
    }
    p = null;
  }
  
  /* Move notation */
  public String toString() {
    return TYPE[type]+COORD_X[x1]+(y1+1)+eat+COORD_X[x2]+(y2+1);
  }
  
  public boolean opposite(Move m) {
    if(m == null)
      return false;
    return m.x1==x2&&m.x2==x1&&m.y1==y2&&m.y2==y1;
  }
}