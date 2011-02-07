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
 * Created on Mar 20, 2005
 *
 */

package chess.gui.board;

import java.awt.*;
import java.awt.event.*;

import chess.algorithms.MoveAlgorithm;
import chess.core.*;
import chess.properties.BoardParameters;
import chess.properties.State;

public class ChessBoardVirtual implements Constants,MouseListener, 
	MouseMotionListener {
  
  private double draggingPieceX = 0;
  private double draggingPieceY = 0;
  private int dragFromX = 0; 
  private int dragFromY = 0;
  private boolean pieceDrag = false;
  private boolean pieceClicked = false;
  
  private double slidePieceLength;
  private double slidePieceAngle;
  private double slidePieceSpeed = 4;
  private boolean pieceSliding;
  
  private double slidingPieceX = 0;
  private double slidingPieceY = 0;
  
  private Coord mouseOn = new Coord(-1,-1);
  
  private Piece pieceSelected = new Piece(TYPE_KING,true);
  private Coord pieceSelectedCoord = new Coord(-1,-1);
  
  private BoardParameters parameters;
  
  //Dimensions
  private Dimension boardDim = new Dimension();
  private int min_leftMargin = 80;
  private int min_topMargin = 100;
  private int xCell;
  private int leftMargin;
  private int yCell;
  private int topMargin;
  
  protected Board board;
  protected ChessGame game;
  
  private boolean mouseOverPieceBlock;
  
  public void setParameters(BoardParameters para) {
    parameters = para;
  }
  
  public ChessBoardVirtual() {
    updateDimensions();
    setGame(null);
  }
  public ChessBoardVirtual(ChessGame game) {
    updateDimensions();
    setGame(game);
  }
  
  public void setGame(ChessGame game) {
    System.out.println("SET GAME VIRTUAL BOARD)");
    pieceSliding = false;
    if (game instanceof ChessGame) {
      this.game = game;
      board = game.board;
    } else {
      board = null;
    }
    
    //pieceClicked = false;
    //pieceDrag = false;
  }
  
  public void updateDimensions(){
    //boardDim = getSize();
    boardDim = new Dimension(500,500);
    int dim = Math.min((boardDim.width-min_leftMargin),(boardDim.height-min_topMargin));
    
    xCell = dim / 8;
    yCell = dim / 8;
    
    leftMargin = (int)((boardDim.width-(8*xCell))/2);
    topMargin = (int)((boardDim.height-(8*yCell))/2);
  }
  
  public void setDimension(ChessBoardMain board) {
    boardDim = board.boardDim;
    int dim = Math.min((boardDim.width-min_leftMargin),(boardDim.height-min_topMargin));
    if (!(parameters==null || parameters.showLegend())) {
      dim = Math.min(boardDim.width,boardDim.height)-2*board.borderThickness;
    }
    
    xCell = dim / 8;
    yCell = dim / 8;
    
    leftMargin = (int)((boardDim.width-(8*xCell))/2);
    topMargin = (int)((boardDim.height-(8*yCell))/2);
      
  }
  
  //-------------------------------------------------------------------------------
  //	Get
  //-------------------------------------------------------------------------------
  
  public boolean isMouseOverPieceBlock() {
    return mouseOverPieceBlock;
  }
  
  public boolean isPieceDrag() {
    return pieceDrag;
  }
  public boolean isPieceClicked() {
    return pieceClicked;
  }
  public int getDraggingPieceX() {
  	return (int)draggingPieceX;
  }
  public int getDraggingPieceY() {
    return (int)draggingPieceY;
  }
  public int getSlidingPieceX() {
    return (int)slidingPieceX;
  }
  public int getSlidingPieceY() {
    return (int)slidingPieceY;
  }
  public Coord getMouseOn() {
    return mouseOn;
  }
  public Piece getPieceSelected() {
    return pieceSelected;
  }
  public Coord getPieceSelectedCoord() {
    return pieceSelectedCoord;
  }
  
  public boolean isBoardFlipped() {
    if (game!=null)
      return game.isBoardFlipped();
    else
      return false;
  }
  public boolean isPieceSliding() {
    return pieceSliding;
  }
  public Move getLastMove() {
    return game.getLastMove();
  }

  //-------------------------------------------------------------------------------
  //	Mouse Listeners
  //-------------------------------------------------------------------------------
  
  public void mouseClicked(MouseEvent arg0) {}
  
  /**
   * This method is called by the mouseListener when a mouse button is pressed.
   * This method finds out which piece, if any, the mouse cursor is over when
   * the mouse button is pressed. If the piece is valid then the piece is
   * selected and prepared for any furthur action.
   * @param e the mouse event which contains the cursor location as a point
   */
  public void mousePressed(MouseEvent e) {
    System.out.println("SDF");
    Point p = e.getPoint();
    
    // Search for the board block the point (mouse cursor location) is on and
    // check whether or not the board block contains the players piece. If the
    // piece is the current player's then have the piece selected.
    //if (computer == null || !computer.isAlive())
    if (game!=null && board!=null)
      if (!game.state.equals(State.WAITING))
    search:
      for (int c=0;c<8;c++)
        for (int r=0;r<8;r++)
          if (leftMargin+c*xCell<=p.x&&p.x<leftMargin+c*xCell+xCell&&
              topMargin+r*yCell<=p.y&&p.y<topMargin+r*yCell+yCell)
            if (board.b[game.isBoardFlipped()?7-c:c][game.isBoardFlipped()?r:7-r]!=null)
              if ((game.getTurn() && 
                  board.b[game.isBoardFlipped()?7-c:c][game.isBoardFlipped()?r:7-r].white)||
                  (!game.getTurn() && 
                      !board.b[game.isBoardFlipped()?7-c:c][game.isBoardFlipped()?r:7-r].white)){
                System.out.println("CLICK");
                // The search was successful in finding and varifying the piece
                // the player clicked on. The piece is now clicked on and 
                // selected. Consequently, change these values for the selected
                // piece.
                pieceClicked = true;
                pieceSelected = board.b[c][7-r];
                pieceSelectedCoord.setCoord(c,7-r);
                
                // Now update the active moves for the selected piece 
                // (The valid moves)
                updateActiveMoves();
                
                // If the board is flipped, we must invert the selected piece
                // coordinates
                if(game.isBoardFlipped()) {
                  pieceSelected = board.b[7-c][r];
                  pieceSelectedCoord.setCoord(7-c,r);
                }
                
                // Prepare the piece drag. This will prepare a piece to be 
                // dragged about the panel region.
                preparePieceDrag(e,new Coord(c,r));
                
                // Play a sound clip to indicate a mouse click on a board block
                // containing a piece
                ////pieceClick.play();
                
                // Now we have all we need, so stop searching
                break search; 
                // "break" is a branching statement that terminates the 
                // statement labeled search redirecting execution out of the 
                // search branch
              }
  }
  
  /**
   * This method is called by the MouseListener when the mouse button clicked
   * is released. This method whether or not a user move is valid.
   * @param e the mouse event which contains the cursor location as a point
   */
  public void mouseReleased(MouseEvent e) {
    Point p = e.getPoint();
    System.out.println("mouseReleased(MouseEvent e) ");
    // If a piece is appropriately moved by dragging or clicking then search 
    // for the board block coordinates that correspond with the point
    // location. Also, make sure the move is valid.
    if((pieceDrag || pieceClicked))
      search:
        for (int c=0;c<8;c++)
          for (int r=0;r<8;r++)
            if (leftMargin+c*xCell<=p.x && p.x<leftMargin+c*xCell+xCell &&
                topMargin+r*yCell<=p.y && p.y<topMargin+r*yCell+yCell && 
                p.x!=topMargin+7*xCell+xCell) {
              System.out.println("mouseReleased(MouseEvent e) can move");
              // Determine if the move is valid by searching through the active
              // moves
              if (game.canMove())
              searchMoves:
                if (game.getActiveMoves() != null) {
                  System.out.println("mouseReleased(MouseEvent e) active size:"+game.getActiveMoves().size());
                  for (int i = 0; i < game.getActiveMoves().size(); i++) {
                    System.out.println("mouseReleased(MouseEvent e) searchMoves");
                    Move m = (Move) game.getActiveMoves().get(i);
                    if ((game.isBoardFlipped()?7-m.x2:m.x2)==c && 
                        (game.isBoardFlipped()?7-m.y2:m.y2)==7-r) {
                      
                      // Call the movePiece method in the game class.
                      // This will perform the move on the game board.
                      game.movePiece(m);
                      
                      System.out.println(
                          "Player1Count:"+game.getWhitePieceCount()+
                          "Player2Count:"+game.getBlackPieceCount());
                      //if(NetworkEnd.server)
                      //	ChessServer.getInstance().getPlay().move
                      //	(m.x1, m.y1, m.x2, m.y2);
                      //else
                      //	ChessClient.getInstance().getPlay().move
                      //	(m.x1, m.y1, m.x2, m.y2);
                      
                      // A new move has been performed so update the last move.
                      // (the last move will be equal to this new move)
                      //updateLastMove();
                      
                      configureSlide();
                      
                      // Now if the piece should slide over to its new location
                      // then configure the piece to slide.
                      //if (settings.pieceSlide) 
                      //  configureSlide();
                      
                      // Now this move is performed, so stop searching
                      break searchMoves; 
                      // "break" is a branching statement that terminates the 
                      // statement labeled searchMove redirecting execution out
                      // of the searchMove branch
                    }
                  }
                }
              // Now if the piece is clicked on another piece block then set
              // pieceClicked to false (piece will no longer be selected)
              if (c!=(game.isBoardFlipped()?7-pieceSelectedCoord.x:pieceSelectedCoord.x) ||
                  7-r!=(game.isBoardFlipped()?7-pieceSelectedCoord.y:pieceSelectedCoord.y))
                pieceClicked = false;
              
              // Adversely, if the piece is clicked on the same piece block
              // then set pieceClicked to true (piece will still be selected)
              if (c==(game.isBoardFlipped()?7-pieceSelectedCoord.x:pieceSelectedCoord.x) &&
                  7-r==(game.isBoardFlipped()?7-pieceSelectedCoord.y:pieceSelectedCoord.y))
                pieceClicked = true;
              
              //The piece is no longer being dragged. Setting pieceDrag to 
              // false will allow the piece to return to its original location
              // or proceed to its new location (if a valid move has performed)
              pieceDrag = false;
              
              // Now we have all we need, so stop searching
              break search; 
              // "break" is a branching statement that terminates the statement
              // labeled search redirecting execution out of the search branch
            }
     
    pieceDrag = false;
  }
  
  public void continueSlide() {
    if (pieceSliding){
      slidingPieceX=(slidingPieceX+slidePieceSpeed*Math.sin(Math.toRadians(slidePieceAngle)));
      slidingPieceY=(slidingPieceY-slidePieceSpeed*Math.cos(Math.toRadians(slidePieceAngle)));
      slidePieceLength-=slidePieceSpeed;
      if (slidePieceLength-slidePieceSpeed<=0){
        pieceSliding = false;
      }
    }
  }
  
  public void configureSlide() {
    
    if (!(!pieceDrag && (parameters==null||parameters.willPieceSlide())))
      return;
      
    //if (pieceClicked) {
      System.out.println("ConfigureSlide");
      slidingPieceX = (game.isBoardFlipped()?7-game.getLastMove().x1:game.getLastMove().x1)*xCell;
      slidingPieceY = (game.isBoardFlipped()?game.getLastMove().y1:7-game.getLastMove().y1)*yCell;
    //}
	int endX = (game.isBoardFlipped()?7-game.getLastMove().x2:game.getLastMove().x2)*xCell;
	int endY = (game.isBoardFlipped()?game.getLastMove().y2:7-game.getLastMove().y2)*yCell;
	slidePieceLength = Math.sqrt(Math.pow(Math.abs(slidingPieceX-endX),2)+Math.pow(Math.abs(slidingPieceY-endY),2));
	slidePieceAngle  = -Math.toDegrees(Math.atan((slidingPieceX-endX)/(slidingPieceY-endY)));
	if (endY > slidingPieceY) slidePieceAngle+=180;
	//System.out.println("x"+movingmovingPieceX+" y"+movingmovingPieceY+" endX " + endX + " endY " + endY + " len"+slidePieceLength+" angle"+slidePieceAngle);
	pieceSliding = true;
  }
  
  public void mouseEntered(MouseEvent arg0) {}
  public void mouseExited(MouseEvent arg0) {}
  
  /** 
   * This method is called by the MouseMotionListener when either one of
   * the mouse buttons is clicked and the mouse cursor is moved; the mouse 
   * cursor is dragged. This method sets the necessary moving piece values
   * for the piece being dragged about the panel.
   * @param e the mouse event which contains the cursor location as a point
   */
  public void mouseDragged(MouseEvent e) {
    // When a piece is in drag mode "pieceDrag" will already be set to true.
    if (pieceDrag){
      // The piece is now in motion so it is no longer clicked on
      pieceClicked = false;
      // Now set the moving piece values according to the mouse cursor location
      
      if (parameters==null || (parameters.willPieceDragCenter())) {
        dragFromX = xCell/2; //how far from left
        dragFromY = yCell/2; //how far from top 
      }
      draggingPieceX = e.getX() - dragFromX; //set x coord for drag
      draggingPieceY = e.getY() - dragFromY; //set y coord for drag

    }
    // Now call the mouseOnBlock method to set the mouseOn coordinate to match
    // the board block the mouse cursor is on. Send it the mouse event 
    // containing the mouse cursor point.
    mouseOnBlock(e);
  }
  
  /** 
   * This method is called by the MouseMotionListener when the mouse cursor
   * is moved about the panel region. This method determines whether or not
   * a the mouse cursor location is on a board block containing a piece. 
   * The mouse cursor icon is set or changed accordingly.
   * @param e the mouse event which contains the cursor location as a point
   */
  public void mouseMoved(MouseEvent e) {
    Point p = e.getPoint();
    boolean onPieceBlock = false; // Wether or not a cursor is on a board 
                                  // block containing a piece
    // Search for the board block coordinates that correspond with the point
    // location
    if (game!=null && board!=null)
    search:
      for (int c = 0; c < 8; c++)
        for (int r = 0; r < 8; r++)
          if (leftMargin+c*xCell <= p.x && p.x < leftMargin+c*xCell+xCell && 
              topMargin+r*yCell <= p.y && p.y < topMargin+r*yCell+yCell){
            if (board.b[game.isBoardFlipped()?7-c:c][game.isBoardFlipped()?r:7-r]!=null)
              onPieceBlock = true;
            // Now we have all we need, so stop searching
            break search; 
            // "break" is a branching statement that terminates the statement
            // labeled search redirecting execution out of the search branch
          }
    // Whether the point is found to be on a board block containing a piece or
    // not, set the mouse cursor to a hand cursor or the default cursor 
    // respectively
    if (onPieceBlock)
      mouseOverPieceBlock = true;
    //  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    else
      mouseOverPieceBlock = false;
    //  setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    
    // Now call the mouseOnBlock method to set the mouseOn coordinate to match
    // the board block the mouse cursor is on. Send it the mouse event 
    // containing the mouse cursor point.
    mouseOnBlock(e);
    
  }
  
  /** 
   * This method searches for the board block the point is on, if any, and sets
   * the mouseOn coordinate to match the block coordinate. This method is
   * constructed alone so that it is accessible by each of the mouseDragged, 
   * mouseMoved, and mouseDragged methods.
   * @param e the mouse event which contains a point
   */
  private void mouseOnBlock(MouseEvent e) {
    Point p = e.getPoint();
    
    if (parameters!=null && !parameters.showMouseOver())
      return;
    
    // Search for the board block coordinates that correspond with the point 
    // location
    for (int c = 0; c < 8; c++)
      for (int r = 0; r < 8; r++)
        if (leftMargin+c*xCell <= p.x && p.x < leftMargin+c*xCell+xCell && 
            topMargin+r*yCell <= p.y && p.y < topMargin+r*yCell+yCell) {
          mouseOn.setCoord(c,r);
          // Now we have all we need, so stop searching
          return; 
          // "return" is a branching statement that redirects execution out of 
          // the current method and back to the calling method
        }
    // This statement is reached if the search was not successful.
    // Set the mouseOn coordinates to match an uncertain case.
    mouseOn.setCoord(-1,-1);
  }
  
  /**
   * This method is called by the mousePressed method. This method sets up the
   * moving piece values according to the mouse cursor's location in the panel
   * region and relative to it's exact location on the piece.
   * @param e the mouse event which contains the cursor location as a point
   * @param boardBlock the coordinates of the board block a piece is situated
   */
  private void preparePieceDrag(MouseEvent e, Coord boardBlock) {
    Point p = e.getPoint();
    
    if (parameters!=null && !parameters.allowPieceDrag())
      return;
      
    // Since this method is called by the mousePressed method, we know that 
    // mouse button is currently being pressed. The piece is now draggable.
    pieceDrag = true;
    
    // Now we set the moving piece values, so that they are relative to the
    // mouse coordinates (Point p) on the panel region. Also, set the values
    // according to the points location away from the dragging piece.
    if (parameters!=null)
      if (!parameters.willPieceDragCenter()) {
        draggingPieceX = leftMargin+boardBlock.x*xCell; //set x coord for drag
        draggingPieceY = topMargin+boardBlock.y*yCell;  //set y coord for drag
	    dragFromX = p.x - (int)draggingPieceX;  // how far from left
	    dragFromY = p.y - (int)draggingPieceY;  // how far from top 
        return;
      }
      
    dragFromX = xCell/2; //how far from left
    dragFromY = yCell/2; //how far from top 
    draggingPieceX = p.x-dragFromX; //set x coord for drag
    draggingPieceY = p.y-dragFromY; //set y coord for drag

  }
  
  public void updateActiveMoves() {
    MoveAlgorithm ma = new MoveAlgorithm(board);
    // Build a vector list of all possible legal and valid moves for the
    // current selected piece
    game.setActiveMoves(ma.getRealAll(
        new Coord(game.isBoardFlipped()?7-pieceSelectedCoord.x:pieceSelectedCoord.x,
            game.isBoardFlipped()?7-pieceSelectedCoord.y:pieceSelectedCoord.y)));
    // "active" is a vector which now contains a list of moves. These moves
    // can now be displayed in the panel. They will also be used to validify 
    // user piece moves.
    
    //System.out.println("updateActiveMoves() {"+game.getActiveMoves().size());
  }
  
  
  public void updateBoardViews() {
    //Vector list = ChessDocuments.getInstance().getList();
    //for(int i=0)
  }
  
}
