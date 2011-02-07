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
 * Created on Apr 2, 2005
 *
 */

package chess.algorithms;

import java.io.Serializable;
import java.util.Vector;

import chess.core.*;

/**
 * MiniMax, a subclass of MoveAlgorithm, is a MiniMax search algorithm.
 * The minimax strategy serves as the foundation of chess programs.
 * It is a strategy that minimizes the maximum risk for a player, going through
 * all of the branches in the move tree and evaluating the board position.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class MiniMax extends MoveAlgorithm implements Serializable{
  
  private boolean white; // White player ?
  
  /**
   * Create an minimax algorithm object.
   * @param game - The chess game to work with
   */
  public MiniMax() {
    super();
  }
  
  /**
   * Create an minimax algorithm object with a game to use.
   * @param game - The chess game to work with
   */
  public MiniMax(ChessGame game) {
    super(game);
  }
  
  /**
   * Create an minimax algorithm object with a board to use.
   * @param board - The board to work with
   */
  public MiniMax(Board board) {
    super(board);
  }
  
  /**
   * Get a reply in the form of a move. NOTE: the move is stored in mm
   * @param white - White player ?
   * @return the move
   */  
  public Move getReply(boolean white) {
    System.out.println("\n"+(white?"White":"Black") + " replies with "+toString());
    minimax(white, dd);
    return mm;
  }
  
  /**
   * Start the search algorithm with the right player
   * @param white - White player ?
   * @param depth - The current number of levels in the tree to be searched.
   */
  public void minimax(boolean white, int depth){
    this.white = white;
  	int val = 0;
  	if(this.white)
  	  val = Max(depth); // white moves first
  	else
  	  val = Min(depth); // black moves first
  }
  
  /**
   * The search algorithm for max (White player)
   * @param depth - The current number of levels in the tree to be searched.
   * @return the best evaluation
   */
  private int Max(int depth) {
  	if (depth == 0)
  		return estimate();
  	int best = -INFINITY;
  	Vector v = successors(true);
  	if (v!=null) { 
  	  int siz = v.size();
  	  while(v.size()>0) {
  	    Move mv = (Move)v.remove(0);
  	    mv.perform(board);
  	    int val = -Min(depth-1);
  	    if (val>best) {
  	      best = val;
  	      if (this.white) {
  	        mm = mv; // Current choice of move
  	      }
  	    }
  	    mv.undo(board);
  	  }
  	}
  	return best;
  }
  
  /**
   * The search algorithm for min (Black player)
   * @param depth - The current depth
   * @return the best evaluation
   */
  private int Min(int depth) {
    if (depth == 0)
      return estimate();
    int best = -INFINITY;
    Vector v = successors(false);
  	if (v!=null) { 
  	  int siz = v.size();
  	  while(v.size()>0) {
  	    Move mv = (Move)v.remove(0);
  	    mv.perform(board);
  	    int val = -Max(depth-1);
  	    if (val>best) {
  	      best = val;
  	      if (!this.white) {
  	        mm = mv; // Current choice of move
  	      }
  	    }
  	    mv.undo(board);
  	  }
  	}
  	return best;
  }
  
  /**
   * Get the name of this algorithm
   */
  public String toString() {
    return "Minimax";
  }
  
}
