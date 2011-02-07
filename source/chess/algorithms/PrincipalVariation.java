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
 * Created on Mar 16, 2005
 */

package chess.algorithms;

import java.io.Serializable;
import java.util.Vector;

import chess.core.Board;
import chess.core.ChessGame;
import chess.core.Move;

/**
 * PrincipalVariation, a subclass of MoveAlgorithm, is a principalvariation 
 * search algorithm. Principal variation search is a variation of alpha-beta 
 * search where all nodes outside the principal variation are searched with
 * a minimal window beta = alpha + 1. The idea is that with perfect move
 * ordering all moves outside the principal variation will be worse than 
 * the principal variation.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class PrincipalVariation extends MoveAlgorithm implements Serializable{
  
  /**
   * Create an PrincipalVariation algorithm object.
   */
  public PrincipalVariation() {
    super();
  }
  
  /**
   * Create an PrincipalVariation algorithm object with a game to use.
   * @param game - The chess game to work with
   */
  public PrincipalVariation(ChessGame game) {
    super(game);
  }
  
  /**
   * Create an PrincipalVariation algorithm object with a board to use.
   * @param board - The board to work with
   */
  public PrincipalVariation(Board board) {
    super(board);
  }
  
  /**
   * Get a reply in the form of a move. NOTE: the move is stored in mm
   * @param white - White player ?
   * @return the move
   */
  public Move getReply(boolean white) {
    System.out.println("\n"+(white?"White":"Black") + " replies with "+toString());
    principalVariation(white, -INFINITY, INFINITY, dd);
    return mm;
  }
  
  /**
   * The search algorithm, using methods found in the super class MoveAlgorithm,
   * produces the best move result in mm
   * @param white - White player ?
   * @param alpha - The best score
   * @param beta  - The worst-case scenario for the opponent. 
   * @param depth - The number of levels in the tree to be searched.
   * @return the estimated position value
   */
  public int principalVariation(boolean white, int alpha, int beta, int depth) {
    if(isStopped())
      return 0;
    if(depth==0)
      return estimate();
    Vector v = successors(white);
    int best = -INFINITY;
    if(v!=null) {
      int siz = v.size();
      if(depth==dd)
        super.board.progress.setMaximum(v.size());
      v = randomize(board.b, v);
      if(v.size()>0) {
        Move m = (Move)v.remove(0);
        m.perform(board);
        best = -principalVariation(!white, -beta, -alpha, depth-1);
        if(depth==dd) {
          mm = m;
        }
        m.undo(board);
      }
      while(v.size()>0 && best<beta) {
        Move m = (Move)v.remove(0);
        m.perform(board);
        if(best>alpha)
          alpha = best;
        int est = -principalVariation(!white, -alpha-1, -alpha, depth-1);
        if(est>alpha && est<beta) {
          best = -principalVariation(!white, -beta, -est, depth-1);
          if(depth==dd)
            mm = m;
        } else if(est>best) {
          best = est;
          if(depth==dd)
            mm = m;
        }
        m.undo(board);
        if (depth==dd) {
          super.board.think.setText((mm==null?"":mm+" - ")+v.toString()); 
          super.board.progress.setValue(siz-v.size());
        }
      }
    }
    return best;
  }
  
  /**
   * Get the name of this algorithm
   */
  public String toString() {
    return "PrincipalVariation";
  }
  
}
