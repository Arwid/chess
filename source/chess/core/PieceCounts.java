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
 * Created on May 13, 2005
 *
 */
package chess.core;

import java.io.Serializable;

/**
 * PieceCounts is used by ChessGame.
 * PieceCounts keeps track of the number of pieces: for each player and for 
 * each type of piece.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class PieceCounts implements Constants, Serializable {
  
  // Player Piece count
  public int whiteCount;
  public int blackCount;
  
  // Type Piece count
  public int whitePawns;
  public int blackPawns;
  public int whiteKnights;
  public int blackKnights;
  public int whiteBishops;
  public int blackBishops;
  public int whiteRooks;
  public int blackRooks;
  public int whiteQueens;
  public int blackQueens;
  public int whiteKings;
  public int blackKings;
  
  // The chess game with the pieces
  private Board board;
  
  /**
   * Constructor gets initial piece counts for the chess game
   * @param game - the chess game
   */
  public PieceCounts(ChessGame game) {
    this.board = game.board;
    refreshPieceCount();
  }
  
  public PieceCounts(Board board) {
    this.board = board; 
    refreshPieceCount();
  }
  
  /**
   * Refresh / update the piece counts
   *
   */
  public void refreshPieceCount() {
    defaultValues();
    for (int row=0;row<8;row++) {
      for (int col=0;col<8;col++) {
        if (board.b[row][col] != null) {
          inc(board.b[row][col].type,board.b[row][col].white);
        }
      }
    }
  }
  
  /**
   * Set up default piece count values;
   *
   */
  private void defaultValues() {
    whiteCount = blackCount = 0;
    
    whitePawns = blackPawns = 0;
    whiteKnights = blackKnights = 0;
    whiteBishops = blackBishops = 0;
    whiteRooks = blackRooks = 0;
    whiteQueens = blackQueens = 0;
    whiteKings = blackKings = 0;
  }
  
  /**
   * Increase the count for a piece type and white
   * @param type - the type of piece
   * @param white - is the piece white ?
   */
  private void inc(byte type, boolean white) {
    int inc = 1;
    if(white) {
      whiteCount += inc;
      switch(type) {
        case(TYPE_PAWN): 
          whitePawns   += inc;
          break;
        case(TYPE_KNIGHT):
          whiteKnights += inc;
          break;
        case(TYPE_BISHOP):
          whiteBishops += inc;
          break;
        case(TYPE_ROOK):
          whiteRooks   += inc;
          break;
        case(TYPE_QUEEN):
          whiteQueens  += inc;
          break;
        case(TYPE_KING):
          whiteKings   += inc;
          break;
      }
    } else {
      blackCount += inc;
      switch(type) {
        case(TYPE_PAWN): 
          blackPawns   += inc;
          break;
        case(TYPE_KNIGHT):
          blackKnights += inc;
          break;
        case(TYPE_BISHOP):
          blackBishops += inc;
          break;
        case(TYPE_ROOK):
          blackRooks   += inc;
          break;
        case(TYPE_QUEEN):
          blackQueens  += inc;
          break;
        case(TYPE_KING):
          blackKings   += inc;
          break;
      }
    }
  }
  
  /**
   * Get the count for a piece type and white
   * @param type
   * @param white
   * @return
   */
  public int getCount(byte type, boolean white) {
    if(white) {
      switch(type) {
        case(TYPE_PAWN): 
          return whitePawns;
        case(TYPE_KNIGHT):
          return whiteKnights;
        case(TYPE_BISHOP):
          return whiteBishops;
        case(TYPE_ROOK):
          return whiteRooks;
        case(TYPE_QUEEN):
          return whiteQueens;
        case(TYPE_KING):
          return whiteKings;
      }
    } else {
      switch(type) {
        case(TYPE_PAWN): 
          return blackPawns;
        case(TYPE_KNIGHT):
          return blackKnights;
        case(TYPE_BISHOP):
          return blackBishops;
        case(TYPE_ROOK):
          return blackRooks;
        case(TYPE_QUEEN):
          return blackQueens;
        case(TYPE_KING):
          return blackKings;
      }
    }
    return 0;
  }
  
}