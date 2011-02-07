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
 * Created on Nov 15 2005
 *
 */

package chess.core;

import java.io.Serializable;
import java.util.Vector;

import chess.algorithms.*;
import chess.gui.panels.ChessComponent;
import chess.properties.*;

/**
 * ChessGame contains all the necessary information for a chess game, such as
 * the chess board, game and player parameters, timers, history, etc.
 * 
 * @author Arvydas Bancewicz
 */
public class ChessGame implements Constants, Serializable {
  
  public Board board;    // The chess board
  public Move lastMove;  // The last move made
  
  // Game parameters
  private GameParameters gameParameters;
  
  // Player parameters
  public PlayerParameters whiteParameters;
  public PlayerParameters blackParameters;
  
  // Player virtual clocks
  private VirtualClock whiteClock;
  private VirtualClock blackClock;
  
  // The State of the chess game (eg. Playing)
  public State state;
  
  /** Game history (Notation) */
  public ChessTableModel clm;
  
  // Active moves (the valid moves for the piece selected)
  private Vector activeMoves = new Vector();
  
  // Board flipped ?
  private boolean flipBoard = false;
  
  public MoveAlgorithm algorithm; // For Computer
  
  /**
   * Constructor
   *
   */
  public ChessGame () {
    setUpDefaults();
    //refreshBoard();
  }
  
  public void setAlgorithm(MoveAlgorithm algorithm) {
    this.algorithm = algorithm;
  }
  
  /**
   * Default values for variables
   *
   */
  private void setUpDefaults() {
    // Game state
    state = State.INITIALIZED;
    
    algorithm = new AlfaBeta();
    
    // Parameters
    gameParameters = new GameParameters();
    whiteParameters = new PlayerParameters("White",true);
    blackParameters = new PlayerParameters("Black",true);
    
    // Player clocks
    whiteClock = new VirtualClock();
    blackClock = new VirtualClock();
    
    clm = new ChessTableModel();
    
    board = new Board();
    
    lastMove = null;
  }
  
  /**
   * @return white's turn ?
   */
  public boolean getTurn() {
    return board.turn;
  }
  
  /**
   * Get the # of pieces
   * @param type  - The piece type
   * @param white - player white ?
   * @return # of pieces
   */
  public int getCount(byte type, boolean white) {
    return board.pieceCounts.getCount(type,white);
  }
  
  /**
   * Get the # of white pieces
   */
  public int getWhitePieceCount() {
    return board.pieceCounts.whiteCount;
  }
  
  /**
   * Get the # of black pieces
   */
  public int getBlackPieceCount() {
    return board.pieceCounts.blackCount;
  }
  
  /**
   * Pause the current game state
   */
  public void pause() {
    if (!state.equals(State.INITIALIZED) && !state.equals(State.WAITING)) {
      
      // The chess game is paused
      state = State.PAUSED;
      whiteClock.stopClock();
      blackClock.stopClock();
    }
  }
  
  /**
   * Resume the paused game state
   */
  public void resume() {
    if (state.equals(State.PAUSED)) {
      state = State.PLAYING;
      if (board.turn)
        whiteClock.startClock();
      else
        blackClock.startClock();
    }
  }
  
  /**
   * Get the player's VirtualClock
   * @param white true for white, false for black
   * @return VirtualClok
   */
  public VirtualClock getClock(boolean white) {
    if (white)
      return whiteClock;
    else
      return blackClock;
  }
  
  /**
   * Set the legal moves the player is allowed
   * @param a list of legal moves
   */
  public void setActiveMoves(Vector active) {
    activeMoves = active;
  }
  
  /**
   * Get the legal moves the player is allowed
   * @return a list of legal moves
   */
  public Vector getActiveMoves() {
    return activeMoves;
  }
  
  /**
   * True if the board is flipped, false if it is not flipped
   * @return true or false
   */
  public boolean isBoardFlipped() {
    return flipBoard;
  }
  
  /**
   * Set the board to be flipped or not flipped
   * @param flip true for flip, false not for flip
   */
  public void setFlipBoard(boolean flip) {
    flipBoard = flip;
  }
  
  /**
   * Get the last move performed in this game
   * @return the last move
   */
  public Move getLastMove() {
    return lastMove;
  }
  
  /**
   * Perform a piece move in this game
   * @param m the piece move
   */
  public void movePiece(Move m){
    
    // Perform the move
    m.perform(board);
    
    ChessComponent.getInstance().view.refreshDocuments(); // Refresh the document view
    clm.add(m); // Add the move to the notation list
    
    lastMove = m; // Save this move as the lastMove
    
    swTurn(); // Switch player turns
    
  }
  
  /**
   * Switch player turns
   */
  private void swTurn(){
    board.turn = board.turn ? false : true;
    
    board.pieceCounts.refreshPieceCount();
    try {
      ChessComponent.getInstance().chessSideBar.piecesPanel.refreshPieceCount(board.pieceCounts);
    } catch (Exception e) {}
    
    // Start or stop the appropriate clock relative to the turn
    if (!state.equals(State.GAMEOVER))
      if (board.turn) { // white
        whiteClock.startClock();
        blackClock.stopClock();
      } else {    // black
        whiteClock.stopClock();
        blackClock.startClock();
      }
    
    state = State.PLAYING;
    if (!board.turn) {
      //algorithm.setBoard(board);
      //state = State.WAITING;
      //new Computer(this, algorithm).start();
      //MoveAlgorithm ma = new AlfaBeta(this);
      //ma.reply(turn);
    }
    if ((!board.turn && !blackParameters.isUser()) ||
        (board.turn && !whiteParameters.isUser())) {
      state = State.WAITING;
      algorithm.setBoard(board);
      new Computer(this, algorithm).start();
    }
    
    ChessComponent.getInstance().whiteBoardLabel.setActive(board.turn);
    ChessComponent.getInstance().blackBoardLabel.setActive(!board.turn);
    
    checkGameStatus();
  }
  
  /**
   * Check the game status for any need to change the game state
   */
  public void checkGameStatus() {
    MoveAlgorithm ma = new AlfaBeta(this);
    if (ma.checkMate()) {
      System.out.println("GAME OVER");
      state = State.GAMEOVER;
      whiteClock.stopClock();
      blackClock.stopClock();
    }
    System.out.println("ended ? white:"+whiteClock.hasEnded()+" black:"+blackClock.hasEnded());
    System.out.println("running ? white:"+whiteClock.isRunning()+" black:"+blackClock.isRunning());
    if (whiteClock.hasEnded()||blackClock.hasEnded()) {
      state = State.GAMEOVER;
      ChessComponent.getInstance().chessBoard.repaint();
    }
    //System.out.println("CheckMate: "+ma.checkMate());
  }
  
  /**
   * Can the player move a piece
   * @return true if there is a move
   */
  public boolean canMove() {
    boolean playing = false;
    if (state == State.PLAYING || state == State.INITIALIZED)
      playing = true;
    return playing;
  }
  
  /**
   * Get the move number pertaining to whites move number
   * @return
   */
  public int getMoveCount() {
    return clm.getRowCount();
  }
  
  /**
   * Get the ChessGame creation date
   * @return date as string
   */
  public String getDateCreated() {
    if (gameParameters == null)
      return "??";
    return gameParameters.getDateCreated();
  }
  
  /**
   * Get the ChessGame title
   * @return title as string
   */
  public String getTitle() {
    if (gameParameters == null)
      return "??";
    return gameParameters.getTitle();
  }
  
  public void setTitle(String title) {
    gameParameters.setTitle(title);
  }
  
  /**
   * Get the white players name
   * @return white name
   */
  public String getWhiteName() {
    if (whiteParameters == null)
      return "?? White";
    return whiteParameters.getName();
  }
  
  /**
   * Get the black players name
   * @return black name
   */
  public String getBlackName() {
    if (blackParameters == null)
      return "?? Black";
    return blackParameters.getName();
  }
  
  public void setWhiteName(String name) {
    whiteParameters.setName(name);
  }
  
  public void setBlackName(String name) {
    blackParameters.setName(name);
  }
}
