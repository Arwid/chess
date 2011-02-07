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

import chess.algorithms.*;
import chess.gui.panels.ChessComponent;

/**
 * Computer is a thread that undergoes the 'moving a piece' process.
 * 
 * @author Arvydas Bancewicz
 * 
 */

public class Computer extends Thread {
  
  private MoveAlgorithm algorithm;
  private ChessGame game;
  
  /**
   * Constructor
   * @param game - the chess game to perform the resulting move on
   */
  Computer(ChessGame game) {
    setPriority(Thread.MIN_PRIORITY);
    setDaemon(true);
    this.game = game;
  }
  Computer(ChessGame game, MoveAlgorithm algorithm) {
    setPriority(Thread.MIN_PRIORITY);
    setDaemon(true);
    this.game = game;
    this.algorithm = algorithm;
    this.algorithm.setGame(game);
  }
  
  public void setAlgorithm(MoveAlgorithm algorithm) {
    this.algorithm = algorithm;
  }
  
  public void run() {
    if (algorithm==null)
      algorithm = new AlfaBeta(game); // Default algorithm
    else
      algorithm.setGame(game);
    game.movePiece(algorithm.reply(game.board.turn));
    ChessComponent.getInstance().chessBoard.getVirtualBoard().configureSlide();
    ChessComponent.getInstance().chessBoard.repaint();
    this.stop();
  }
}