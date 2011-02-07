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
import java.util.*;

import chess.core.*;

/**
 * 
 * MoveAlgorithm is a super class for search algorithms. It consists of all the
 * required utilities defining a playable chess game, such as: move definitions,
 * composing moves from move definitions, applying board position circumstances
 * to the move definition (eg. a piece cannot move to put it's king in check), 
 * composing lists of possible moves, and board evaluations based on piece and 
 * position costs.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class MoveAlgorithm implements Constants,Serializable{
  
  // The chess board to be worked o
  protected Board board;
  
  protected final int INFINITY = 100000;
  private boolean stopped = false; 
  protected Random rnd = new Random();
  
  // The number of levels in the tree to be searched.
  protected int dd = 3;       // Depth
  
  protected int stepcounter;  // Total node count
  protected Move mm;          // Current move
  protected int val = 0;      // Current estimation value
  
  private int estnow;         // base estimation
  private int est_cost;       // Cost estimation value
  private int est_attackCost; // Attack cost estimation value
  
  // Speed test
  private Date stamp1;       // Time stamp start
  private Date stamp2;       // Time stamp end
  
  /** 
   * Create a new MoveAlgorithm object.
   */
  public MoveAlgorithm() {
    setBoard(new Board());
  }
  
  /**
   * Create a new MoveAlgorithm object with a game to use.
   * @param game - the game to work with
   */
  public MoveAlgorithm (ChessGame game){
    setGame(game);
  }
  
  /**
   * Create a new MoveAlgorithm object with a board to use
   * @param board - the board to work with
   */
  public MoveAlgorithm (Board board) {
    setBoard(board);
  }
  
  // Set the default values for the variables
  private void setDefaults() {
    dd = 3;       // Depth
    
    stepcounter = 0;  // Total node count
    mm = null;          // Current move
    val = 0;      // Current estimation value
    
    estnow = 0;         // base estimation
    est_cost = 0;       // Cost estimation value
    est_attackCost = 0; // Attack cost estimation value
  }
  
  /**
   * Get the board being used by this MoveAlgorithm.
   * @return the Board
   */
  public Board getBoard() {
    return board;
  }
  
  /**
   * Set the depth to use by this MoveAlgorithm.
   * @param depth
   */
  public void setDepth(int depth) {
    if (depth > 0)
      dd = depth;
  }
  
  /**
   * Get the depth used by this MoveAlgorithm.
   * @return
   */
  public int getDepth() {
    return dd;
  }
  
  /**
   * Set the game for this MoveAlgorithm.
   * @param game
   */
  public void setGame(ChessGame game) {
    this.board = copyBoard(game.board);
  }
  
  /**
   * Set the board for this MoveAlgorithm.
   * @param board
   */
  public void setBoard(Board board) {
    this.board = copyBoard(board);
  }
  
  // Has the searching stopped
  protected boolean isStopped() {
    return stopped;
  }
  
  /**
   * Stop the move searching if so
   */
  public void stop() {
    stopped = true;
  }
  
  /**
   * Get a reply 
   * @param white - White player ?
   * @return the move
   */
  public Move reply(boolean white) {
    //setDefaults();
    stopped = false;
    estnow = estimateBase();
    
    testSpeed(); // Start time stamp
    stepcounter = 0;
    // Get the reply from the subclass
    Move move = getReply(white);
    
    if (move == null) {
      System.out.println("ALLLLL");
      Vector v = successors(board.turn);
      if(v!=null) {
        System.out.println("SOME");
        int siz = v.size();
        move = (Move)v.remove(0);
        mm = move;
      }
    }
      
    testSpeed(); // End time stamp
    
    board.movePiece(move);
    
    return move;
  }
  
  /** Subclasses must implement this method */
  protected Move getReply(boolean white) { return mm; }
  
  private Vector getPawnMoves(Coord c){
    Vector v = new Vector();
    if(board.b[c.x][c.y].white){
      if(board.b[c.x][c.y+1]==null){ //empty block
        v.add(new Move(c.x,c.y,c.x,c.y+1));
        if (c.y==1) //beginning jump
          v.add(new Move(c.x,c.y,c.x,c.y+2));
      }
    }else{
      if(board.b[c.x][c.y-1]==null){
        v.add(new Move(c.x,c.y,c.x,c.y-1));
        if (c.y==6)
          v.add(new Move(c.x,c.y,c.x,c.y-2));
      }
    }
    return (v.size()==0)?null:v;
  }
  
  private Vector getPawnAttacks(Coord c) {
    Vector v = new Vector();
    if(board.b[c.x][c.y].white) {
      if(c.x+1<=7)
        v.add(new Move(c.x,c.y,c.x+1,c.y+1));
      if(c.x-1>=0)
        v.add(new Move(c.x,c.y,c.x-1,c.y+1));
    } else {
      if(c.x+1<=7)
        v.add(new Move(c.x,c.y,c.x+1,c.y-1));
      if(c.x-1>=0)
        v.add(new Move(c.x,c.y,c.x-1,c.y-1));
    }
    return (v.size()==0)?null:v;
  }
  
  private Vector getKnightMoves(Coord c) {
    return getKnightAttacks(c);
  }
  
  private Vector getKnightAttacks(Coord c){
    Vector v = new Vector();
    if(c.x+1<=7) {
      if(c.y+2<=7)
        v.add(new Move(c.x,c.y,c.x+1,c.y+2));
      if(c.y-2>=0)
        v.add(new Move(c.x,c.y,c.x+1,c.y-2));
      if(c.x+2<=7) {
        if(c.y+1<=7)
          v.add(new Move(c.x,c.y,c.x+2,c.y+1));
        if(c.y-1>=0)
          v.add(new Move(c.x,c.y,c.x+2,c.y-1));
      }                                                    
    }
    if(c.x-1>=0) {
      if(c.y+2<=7)
        v.add(new Move(c.x,c.y,c.x-1,c.y+2));
      if(c.y-2>=0)
        v.add(new Move(c.x,c.y,c.x-1,c.y-2));
      if(c.x-2>=0) {
        if(c.y+1<=7)
          v.add(new Move(c.x,c.y,c.x-2,c.y+1));
        if(c.y-1>=0)
          v.add(new Move(c.x,c.y,c.x-2,c.y-1));
      }                                                    
    }
    return (v.size()==0)?null:v;
  }
  
  
  private Vector getBishopMoves(Coord c) {
    return getBishopAttacks(c);
  }
  
  private Vector getBishopAttacks(Coord c) {
    Vector v = new Vector();
    byte i; // Increment
    
    // Top Right
    i = 1;
    while(c.x+i<=7 && c.y+i<=7) {
      v.add(new Move(c.x,c.y,c.x+i,c.y+i));
      if(board.b[c.x+i][c.y+i]!=null)
        break;
      i++;
    }
    
    // Bottom Right
    i = 1;
    while(c.x+i<=7 && c.y-i>=0) {
      v.add(new Move(c.x,c.y,c.x+i,c.y-i));
      if(board.b[c.x+i][c.y-i]!=null)
        break;
      i++;
    }
    // Bottom Left
    i = 1;
    while(c.x-i>=0 && c.y-i>=0) {
      v.add(new Move(c.x,c.y,c.x-i,c.y-i));
      if(board.b[c.x-i][c.y-i]!=null)
        break;
      i++;
    }
    
    // Top Left
    i = 1;
    while(c.x-i>=0 && c.y+i<=7) {
      v.add(new Move(c.x,c.y,c.x-i,c.y+i));
      if(board.b[c.x-i][c.y+i]!=null)
        break;
      i++;
    }
    return (v.size()==0)?null:v;
  }
  
  private Vector getRookMoves(Coord c) {
    return getRookAttacks(c);
  }
  
  private Vector getRookAttacks(Coord c) {
    Vector v = new Vector();
    byte i;
    
    // Top
    i = 1;
    while(c.y+i<=7) {
      v.add(new Move(c.x,c.y,c.x,c.y+i));
      if(board.b[c.x][c.y+i]!=null)
        break;
      i++;
    }
    
    // Left
    i = 1;
    while(c.x-i>=0) {
      v.add(new Move(c.x,c.y,c.x-i,c.y));
      if(board.b[c.x-i][c.y]!=null)
        break;
      i++;
    }
    
    // Bottom
    i = 1;
    while(c.y-i>=0) {
      v.add(new Move(c.x,c.y,c.x,c.y-i));
      if(board.b[c.x][c.y-i]!=null)
        break;
      i++;
    }
    
    // Right
    i = 1;
    while(c.x+i<=7) {
      v.add(new Move(c.x,c.y,c.x+i,c.y));
      if(board.b[c.x+i][c.y]!=null)
        break;
      i++;
    }
    return (v.size()==0)?null:v;
  }
  
  private Vector getQueenMoves(Coord c) {
    // Queen acts as both bishop and rook
    return sumVectors(getBishopMoves(c), getRookMoves(c));
  }
  
  private Vector getQueenAttacks(Coord c) {
    // All Queen moves are attacks
    return getQueenMoves(c);
  }
  
  private Vector getBasicKingMoves(Coord c) {
    Vector v = new Vector();
    if(c.x+1<=7) {
      v.add(new Move(c.x,c.y,c.x+1,c.y));
      if(c.y+1<=7)
        v.add(new Move(c.x,c.y,c.x+1,c.y+1));
      if(c.y-1>=0)
        v.add(new Move(c.x,c.y,c.x+1,c.y-1));
    }
    if(c.x-1>=0) {
      v.add(new Move(c.x,c.y,c.x-1,c.y));
      if(c.y+1<=7)
        v.add(new Move(c.x,c.y,c.x-1,c.y+1));
      if(c.y-1>=0)
        v.add(new Move(c.x,c.y,c.x-1,c.y-1));
    }
    if(c.y+1<=7)
      v.add(new Move(c.x,c.y,c.x,c.y+1));
    if(c.y-1>=0)
      v.add(new Move(c.x,c.y,c.x,c.y-1));
    return (v.size()==0)?null:v;
  }
  
  private Vector getKingMoves(Coord c) {
    Vector v = getBasicKingMoves(c);
    if (v==null)
      v = new Vector();
    // Castling
    if (board.b[c.x][c.y].white) {
      if (c.x==4 && c.y==0 && !isAttacked(new Coord(4,0),false)) {
        // Short
        if (board.b[5][0]==null && !isAttacked(new Coord(5,0),false) &&
            board.b[6][0]==null && !isAttacked(new Coord(6,0),false) &&
            board.b[7][0]!=null && board.b[7][0].type==TYPE_ROOK && board.b[7][0].white)
          v.add(new Move(4,0,6,0));
        // Long
        if (board.b[3][0]==null && !isAttacked(new Coord(3,0),false) &&
            board.b[2][0]==null && !isAttacked(new Coord(2,0),false) &&
            board.b[1][0]==null &&
            board.b[0][0]!=null && board.b[0][0].type==TYPE_ROOK && board.b[0][0].white)
          v.add(new Move(4,0,2,0));
      }
    } else {
      if (c.x==4 && c.y==7 && !isAttacked(new Coord(4,7),true)) {
        // Short
        if (board.b[5][7]==null && !isAttacked(new Coord(5,7),true) &&
            board.b[6][7]==null && !isAttacked(new Coord(6,7),true) &&
            board.b[7][7]!=null && board.b[7][7].type==TYPE_ROOK && !board.b[7][7].white)
          v.add(new Move(4,7,6,7));
        // Long
        if (board.b[3][7]==null && !isAttacked(new Coord(3,7),true) &&
            board.b[2][7]==null && !isAttacked(new Coord(2,7),true) &&
            board.b[1][7]==null &&
            board.b[0][7]!=null && board.b[0][7].type==TYPE_ROOK && !board.b[0][7].white)
          v.add(new Move(4,7,2,7));
      }
    }
    return (v.size()==0)?null:v;
  }
  
  private Vector getKingAttacks(Coord c) {
    return getBasicKingMoves(c);
  }
  
  private Vector sumVectors(Vector a, Vector b) {
    if(a==null && b==null)
      return null;
    Vector v=new Vector();
    if(a!=null)
      for(int i=0;i<a.size();i++)
      v.add(a.elementAt(i));
    if(b!=null)
      for(int i=0;i<b.size();i++)
      v.add(b.elementAt(i));
    return v;
  }
  
  private Vector getMoves(Coord c) {
    Piece p = board.b[c.x][c.y];
    if(p==null)
      return null;
    switch(p.type) {
      case TYPE_PAWN:
        return getPawnMoves(c);
      case TYPE_KNIGHT:
        return getKnightMoves(c);
      case TYPE_BISHOP:
        return getBishopMoves(c);
      case TYPE_ROOK:
        return getRookMoves(c);
      case TYPE_QUEEN:
        return getQueenMoves(c);
      case TYPE_KING:
        return getKingMoves(c);
    }
    return null;
  }
  
  public Vector getAttacks(Coord c) {
    Piece p = board.b[c.x][c.y];
    if(p==null)
      return null;
    switch(p.type) {
      case TYPE_PAWN:
        return getPawnAttacks(c);
      case TYPE_KNIGHT:
      	return getKnightAttacks(c);
      case TYPE_BISHOP:
        return getBishopAttacks(c);
      case TYPE_ROOK:
        return getRookAttacks(c);
      case TYPE_QUEEN:
        return getQueenAttacks(c);
      case TYPE_KING:
        return getKingAttacks(c);
    }
    return null;
  }
  
  private Vector getRealMoves(Coord c){
    Piece p = board.b[c.x][c.y];
    Vector v = getMoves(c);
    if(v!=null) {
      for(int k=0;k<v.size();k++) {
        Move m = (Move)v.elementAt(k);
        if(board.b[m.x2][m.y2]!=null) {
       	  v.remove(k);
          k--;
        } else {
          m.perform(board);
          //m.perform(board, game);
          if(isAttacked(p.white?board.whiteKing:board.blackKing,!p.white)) {
            v.remove(k);
          	k--;
          }
          m.undo(board);
          //m.undo(board);
        }
      }
      if (v.size()==0)
        v = null;
    }
    if (v==null)return null;
    return v.size()==0?null:v;
  }
  
  private Vector getRealAttacks(Coord c){
    Piece p = board.b[c.x][c.y];
    Vector v = getAttacks(c);
    if(v!=null) {
      for(int k=0;k<v.size();k++) {
        Move m = (Move)v.elementAt(k);
        if(board.b[m.x2][m.y2]==null || board.b[m.x2][m.y2].white==p.white) {
          v.remove(k);
          k--;
        } else {
          m.perform(board);
          //m.perform(board, game);
          if (isAttacked(p.white?board.whiteKing:board.blackKing,!p.white)) {
            v.remove(k);
            k--;
          }
          m.undo(board);
          //m.undo(board);
        }
      }
      if (v.size()==0)
        v = null;
    }
    
    return v;
  }
 
  /**
   * Get a vector containing all the legal moves for the piece in the 
   * coordinate location
   * @param c - the coordinate location
   * @return the vector with legal moves
   */
  public Vector getRealAll(Coord c) {
    Piece p = board.b[c.x][c.y];
    if (p==null) 
      return null;
    Vector v = null;
    switch(p.type) {
      case TYPE_PAWN:
        return sumVectors(getRealMoves(c),getRealAttacks(c));
      case TYPE_KNIGHT:
        v = getKnightMoves(c);
        break;
      case TYPE_BISHOP:
        v = getBishopAttacks(c);
        break;
      case TYPE_ROOK:
        v = getRookAttacks(c);
        break;
      case TYPE_QUEEN:
        v = sumVectors(getBishopAttacks(c),getRookAttacks(c));
        break;
      case TYPE_KING:
        v = getKingMoves(c);
        break;
    }
    if(v!=null) {
      for(int k=0;k<v.size();k++) {
        Move mmm = (Move)v.elementAt(k);
        // Make sure piece can not take one of it's own
        if(board.b[mmm.x2][mmm.y2]!=null && board.b[mmm.x2][mmm.y2].white==p.white) {
          v.remove(k);
          k--;
        } else {
          mmm.perform(board);
          //mmm.perform(board, game);
          if (isAttacked(p.white?board.whiteKing:board.blackKing,!p.white)) {
            v.remove(k);
            k--;
          }
          mmm.undo(board);
          //mmm.undo(board);
        }
      }
      if(v.size()==0)
        v = null;
    }
    return v;
  }
  
  /**
   * Is this coordinate attacked by !white
   * @param c - the coordinate
   * @param white - is the piece white ?
   * @return coordinate attacked ?
   */
  public boolean isAttacked(Coord c, boolean white) {
    for (byte a=0;a<8;a++)
      for (byte r=0;r<8;r++)
        if(board.b[a][r]!=null && board.b[a][r].white==white) {
          Vector v = getAttacks(new Coord(a,r));
          if (v!=null) {
            for (int i=0;i<v.size();i++) {
              Move m = (Move)v.elementAt(i);
              if (m.x2==c.x && m.y2==c.y)
                return true;
            }
          }
        }
    return false;
  }
  
  protected Vector successors(boolean white) {
    Vector v = null;
    for(byte i=0;i<8;i++)
      for(byte j=0;j<8;j++)
        if(board.b[i][j]!=null && board.b[i][j].white==white) 
          v = sumVectors(v, getRealAll(new Coord(i,j)));
    return v;
  }
  
  private int getCost() {
    int out = 0;
    for(int c=0;c<8;c++)
      for(int r=0;r<8;r++)
        if(board.b[c][r]!=null)
          out+=board.b[c][r].getCost();
    return out;
  }
  
  private int getAttackCost() {
    int out = 0;
    for(byte c=0;c<8;c++)
      for(byte r=0;r<8;r++)
        if(board.b[c][r]!=null) {
          Vector v = getRealAttacks(new Coord(c,r));
          if(v!=null)
            for(int k=0;k<v.size();k++) {
              Move m = (Move)v.elementAt(k);
              out += board.b[m.x2][m.y2].getCost();
      }
    }
    return -out;
  }
  
  /**
   * Is their are checkmate for either player
   * @return checkmate ?
   */
  public boolean checkMate() {
    return isAttacked(board.blackKing,true) && !replyForMate(false) ||
           isAttacked(board.whiteKing,false) && !replyForMate(true);
  }
  
  /**
   * Is the move legal for this board
   * @param m - the move
   * @return legal ?
   */
  public boolean canMove(Move m) {
    if(board.b[m.x1][m.y1]==null)
      return false;
    Vector v = getRealAll(new Coord(m.x1,m.y1));
    if(v!=null) {
      for(int k=0;k<v.size();k++) {
        Move mm = (Move)v.elementAt(k);
        if(m.x2==mm.x2 && m.y2==mm.y2)
          return true;
      }
    }
    return false; 
  }
  
  /**
   * Can the white ? player apply for mate
   * @param white - white ?
   * @return can it reply ?
   */
  public boolean replyForMate(boolean white) {
    for(byte c=0;c<8;c++)
      for(byte r=0;r<8;r++)
        if(board.b[c][r]!=null && board.b[c][r].white==white)
          if(getRealAll(new Coord(c,r))!=null)
            return true;
    return false;
  }
  
  protected int estimate() {
    stepcounter++;
    int dc = getCost()-est_cost;
    int dac = getAttackCost()-est_attackCost;
    //System.out.println("getCost => " + getCost());
    //System.out.println("getAttackCost => " + getAttackCost());
    return dc*10+dac;
  }
  
  private int estimateBase() {
    //cc.progress.setValue(0);
    mm = null;
    est_cost = 0;
    est_attackCost = 0;
    int out = estimate();
    est_cost = getCost();
    est_attackCost = getAttackCost();
    return out;
  }
  
  protected Vector randomize(Piece[][] board, Vector v) {
    int s=v.size();
    int b=0;
    for(int i=0;i<s;i++) {
      Move m = (Move)v.elementAt(i);
      if(board[m.x2][m.y2]!=null) {
        v.remove(i);
        v.insertElementAt(m, 0);
        b++;
      }
    }
    for(int i=b;i<s-1;i++) {
      Object o = v.elementAt(i);
      int j = rnd.nextInt(s-i-1)+i+1;
      v.setElementAt(v.elementAt(j), i);
      v.setElementAt(o, j);
    }
    return v;
  }
  
  private Piece[][] copyBoard (Piece[][] g){
    Piece[][] newGrid = new Piece[8][8];
    for(byte c = 0; c < 8; c++)
      for(byte r = 0; r < 8; r++)
        newGrid[c][r] = g[c][r];
    return newGrid;
  }
  
  private Board copyBoard(Board board) {
    Board newBoard = new Board();
    newBoard.b = new Piece[8][8];
    for(byte c = 0; c < 8; c++)
      for(byte r = 0; r < 8; r++)
        newBoard.b[c][r] = board.b[c][r];
    newBoard.blackKing = board.blackKing;
    newBoard.whiteKing = board.whiteKing;
    
    return newBoard;
  }
  
  private void testSpeed() {
    
    if (stamp1==null)
      stamp1 = new Date(); // Start time
    else {
      stamp2 = new Date(); // End time
      
      // Get seconds and milliseconds
      long milliDiff = stamp2.getTime() - stamp1.getTime();
      long seconds = (long)(milliDiff/1000);
      long millisec = (long)(milliDiff-(seconds*1000));
      stamp1 = stamp2 = null;
      
      // Output the move and the time for it
      System.out.println("reply => " + mm.toString());
      System.out.println("time  =  seconds: " + seconds +
                                 " milliseconds: " + millisec);
      
      // Draw a table with the obtained information
      String col1 = "depth";
      String col2 = "# of nodes";
      String col3 = "evaluation";
      
      // Table specifications
      int spacing = 5;
      int col1Width = col1.length()+spacing;
      int col2Width = col2.length()+spacing;
      int col3Width = col3.length()+spacing;
      
      // Empty string to fill in any gaps
      String empty = "                                                       ";
      
      // Table header (right justified)
      System.out.println(
          empty.substring(0,col1Width-col1.length())+col1+
          empty.substring(0,col2Width-col2.length())+col2+
          empty.substring(0,col3Width-col3.length())+col3
          );
      
      // Table values (right justified)
      System.out.println(
          empty.substring(0,col1Width-(dd+"").length())         +dd+
          empty.substring(0,col2Width-(stepcounter+"").length())+stepcounter+
          empty.substring(0,col3Width-(val+"").length())        +val
          );
    }
  }
}
