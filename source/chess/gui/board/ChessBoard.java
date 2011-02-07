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
 * Created on Dec 2, 2005
 *
 */

package chess.gui.board;

import java.awt.*;

import javax.swing.JPanel;

import chess.core.*;
import chess.properties.BoardParameters;

/**
 * ChessBoard is the super class of ChessBoardMain and ChessBoardSmallView.
 * It takes care of storing board and game values.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class ChessBoard extends JPanel implements Constants {
  
  // Board Dimensions. These variables are used by the subclasses.
  protected Dimension boardDim = new Dimension();
  protected int xCell;
  protected int leftMargin;
  protected int yCell;
  protected int topMargin;
  
  protected int min_leftMargin = 80;
  protected int min_topMargin = 100;
  
  protected ChessGame game;
  protected Board board; // An instance of the game's board
  protected ChessBoardVirtual x;
  
  /**
   * Constructor. Make an empty ChessBoard
   *
   */
  public ChessBoard() {
    this.setMinimumSize(new Dimension(300,300));
    this.game = null;
    board = null;
    
  }
  
  /**
   * Constructor. Make a ChessBoard using a virtual board
   * @param x - the ChessBoardVirtual
   */
  public ChessBoard(ChessBoardVirtual x) {
    this.x = x;
    this.setMinimumSize(new Dimension(300,300));

    game = x.game;
    board = game.board;
    
  }
  
  /**
   * Constructor. Make a ChessBoard using a game
   * @param game - the ChessGame
   */
  public ChessBoard(ChessGame game) {
    this.game = game;
    board = this.game.board;
    
    x = new ChessBoardVirtual(game);
  }
  
  /**
   * Subclasses may implement this method, otherwise try to paint their board.
   */
  public void paint(Graphics g) {
    paintBoard(g);
  }
  
  // ------------- Set values -------------------------------------------------
  
  public void changeGame(ChessGame game) {
    if(game instanceof ChessGame) {
      if (x!=null)
        x.setGame(game);
      else
        x = new ChessBoardVirtual(game);
      this.game = x.game;
      board = game.board;
    } else {
      this.game = null;
      board = null;
      x.setGame(null);
    }
    refreshBoard();
    repaint();
  }
  
  public void setFlipBoard(boolean flip) {
    game.setFlipBoard(flip);
    repaint();
  }
  
  public void setMinLeftMargin(int value) {
    leftMargin = value;
    min_leftMargin = value;
  }
  
  public void setMinTopMargin(int value) {
    topMargin = value;
    min_topMargin = value;
  }
  
  // ------------- Get values -------------------------------------------------
  
  public ChessBoardVirtual getVirtualBoard() {
    return x;
  }
  
  public boolean isBoardFlipped() {
    return game!=null?game.isBoardFlipped():false;
  }
  
  public boolean isPieceDrag() {
    return x!=null?x.isPieceClicked():false;
  }
  
  public boolean isPieceSliding() {
    return x!=null?x.isPieceSliding():false;
  }
      
  public Coord getPieceSelectedCoord() {
    return x!=null?x.getPieceSelectedCoord():new Coord(-1,-1);
  }
  
  // Sub classes may implement these methods
  public Dimension getDefaultSize(){ return null; }
  protected void refreshBoard(){};
  public void setBoardParameters(BoardParameters parameter) {}
  public BoardParameters getBoardParameters() {return null;}
  public void setPreferredDimension(Dimension preferredSize) {}
  public void updateDimensions(){}
  public void paintBoard(Graphics g){}
  public void paintCell(Graphics g, byte c, byte r){}
  
  /* IGNORE since these are only necessary for a runnable interface
  public void resumeThread() {}
  public void start(){}
  public void stop(){}
  public void run() {}
  public void update(Graphics g){}
  */
  
}

