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
 * Created on May 10, 2005
 *
 */

package chess.core;

import java.io.Serializable;

import javax.swing.*;

import chess.properties.ChessPreferences;

/**
 * Board is simply a chess board layout containing all piece positions
 * 
 * @author Arvydas Bancewicz
 *
 */
public class Board implements Constants, Serializable {

  public Piece[][] b;     // the chess board is an 8 by 8 array of Pieces
  public Coord whiteKing; // coordinates of white king
  public Coord blackKing; // coordinates of black king
  
  public boolean turn = true; // white ?
  
  
  // Piece counters
  public PieceCounts pieceCounts;
  
  //transient public Coord enPassant; // null-no current enpassant move
  
  transient public BoundedRangeModel progress;
  transient public JTextArea think;
  
  /**
   * Constructor
   */
  public Board() {
    initDefaultBoard();
    
    pieceCounts = new PieceCounts(this);
    progress = new DefaultBoundedRangeModel();
    think = new JTextArea();
  }
  
  /**
   * Perform a move on the board
   * @param m
   */
  public void movePiece(Move m) {
    m.perform(this);
    pieceCounts.refreshPieceCount();
  }
  
  /**
   * Initialize a default chess board with the default setup of pieces
   */
  public void initDefaultBoard(){
    b = new Piece[8][8];
    
    // Pawns
    for (int c=0;c<8;c++) {
      b[c][1] = new Piece(TYPE_PAWN,true);  // White
      b[c][6] = new Piece(TYPE_PAWN,false); // Black
    }
    // Other White pieces
    b[2][0] = b[5][0] = new Piece(TYPE_BISHOP,true);
    b[1][0] = b[6][0] = new Piece(TYPE_KNIGHT,true);
    b[0][0] = b[7][0] = new Piece(TYPE_ROOK,true);
    b[3][0] = new Piece(TYPE_QUEEN,true);
    b[4][0] = new Piece(TYPE_KING,true);
    // Other Black pieces
    b[2][7] = b[5][7] = new Piece(TYPE_BISHOP,false);
    b[1][7] = b[6][7] = new Piece(TYPE_KNIGHT,false);
    b[0][7] = b[7][7] = new Piece(TYPE_ROOK,false);
    b[3][7] = new Piece(TYPE_QUEEN,false);
    b[4][7] = new Piece(TYPE_KING,false);
    
    // Keep track of king coordinates
    whiteKing = new Coord(4,0);
    blackKing = new Coord(4,7);
    
    turn = true; // White's turn
    
    
  }
  
  /**
   * Initialize a saved chess board
   */
  public void initSavedBoard(){
    byte[][] nboard = ChessPreferences.getBoard();
    b = new Piece[8][8];
    for(int r=0;r<8;r++)
      for(int c=0;c<8;c++)
        if(nboard[c][r]>0)
          b[c][r] = new Piece((byte)((nboard[c][r]-10>0)?(nboard[c][r]-10-1):
            (nboard[c][r]-1)),(nboard[c][r]-10>0)?true:false);
    
    whiteKing = ChessPreferences.getWhiteKing();
    blackKing = ChessPreferences.getBlackKing();
    
    turn = ChessPreferences.getTurn();
  }
  
}
