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

import chess.core.*;

/**
 * NegaScout, a subclass of MoveAlgorithm, is a negascout search algorithm.
 * It is a variation of the "alfa-beta pruning" technique.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class NegaScout extends MoveAlgorithm implements Serializable{
  
  /**
   * Create an negaScout algorithm object.
   */
  public NegaScout() {
    super();
  }
  
  
  /**
   * Create an negaScout algorithm object with a game to use.
   * @param game - The chess game to work with
   */
  public NegaScout(ChessGame game) {
    super(game);
  }
  
  /**
   * Create an negaScout algorithm object with a board to use.
   * @param board - The board to work with
   */
  public NegaScout(Board board) {
    super(board);
  }
  
  /**
   * Get a reply in the form of a move. NOTE: the move is stored in mm
   * @param white - White player ?
   * @return the move
   */
  public Move getReply(boolean white) {
    System.out.println("\n"+(white?"White":"Black") + " replies with "+toString());
    negaScout(white, -INFINITY, INFINITY, dd);
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
  public int negaScout(boolean white, int alpha, int beta, int depth) {
    if(isStopped())
      return 0;
    if(depth==0)
      return estimate();
    int best = -INFINITY;
    int n = beta;
    Vector v = successors(white);
    if(v!=null) {
      int siz = v.size();
      if(depth==dd)
        super.board.progress.setMaximum(v.size());
      v = randomize(board.b, v);
      if(v.size()>0 && depth==dd)
        mm = (Move)v.elementAt(0);
      while(v.size()>0 && best<beta) {
        Move m = (Move)v.remove(0);
        m.perform(board);
        int est = -negaScout(!white, -n, -Math.max(alpha, best), depth-1);
        if(est>best) {
          if(n==beta || depth<=2) {
            best = est;
            if(depth==dd)
              mm = m;
          } else {
            best = -negaScout(!white, -beta, -est, depth-1);
            if(depth==dd)
              mm = m;
          }
        }
        m.undo(board);
        n = Math.max(alpha, best)+1;
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
    return "NegaScout";
  }
  
}
