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
 * RandomGen, a subclass of MoveAlgorithm, generates a random move.
 * It chooses a random move from a valid list of moves.
 * @author Arvydas Bancewicz
 *
 */
public class RandomGen extends MoveAlgorithm implements Serializable{
  
  /**
   * Create an RandomGen algorithm object.
   */
  public RandomGen() {
    super();
  }
  
  /**
   * Create an RandomGen algorithm object with a game to use.
   * @param game - The chess game to work with
   */
  public RandomGen(ChessGame game) {
    super(game);
  }
  
  /**
   * Create an RandomGen algorithm object with a board to use.
   * @param board - The chess board to work with
   */
  public RandomGen(Board board) {
    super(board);
  }
  
  /**
   * Get a reply in the form of a move. NOTE: the move is stored in mm
   * @param white - White player ?
   * @return the move
   */
  public Move getReply(boolean white) {
    System.out.println("\n"+(white?"White":"Black") + " replies with "+toString());
    randomGen(white);
    return mm;
  }
  
  /**
   * Get a random, but valid move
   * @param white - White player ?
   */
  public void randomGen(boolean white){
  	Vector v = successors(white);
  	mm = (Move) v.elementAt(rnd.nextInt(v.size()));
  }
  
  /**
   * Get the name of this algorithm
   */
  public String toString() {
    return "RandomGen";
  }
}
