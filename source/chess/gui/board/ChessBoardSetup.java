/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*                              Java Chess                                      *
*         Copyright (C) 2005  Arvydas Bancewicz and Ihor Lesko                 *
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
 * Created on Mar 27, 2005
 *
 */

package chess.gui.board;

import java.awt.*;

import chess.core.ChessGame;
import chess.core.Move;
import chess.properties.BoardParameters;

public class ChessBoardSetup extends ChessBoard {

  private Dimension preferredSize;
  private BoardParameters parameters;

  public ChessBoardSetup(ChessBoardVirtual x) {
    super(x, false);
    super.init();
    start();
    preferredSize = new Dimension(200,200);
    setPreferredSize(preferredSize);
    
    parameters = new BoardParameters();
  }
  
  public ChessBoardSetup(ChessGame g) {
    super(g);
    super.init();
    start();
    
    preferredSize = new Dimension(200,200);
    setPreferredSize(preferredSize);
    updateDimensions();
  }

  public void setBoardParameters(BoardParameters parameters) {
    this.parameters = parameters;
  }
  
  public void setPreferredDimension(Dimension preferred) {
    this.preferredSize = preferred;
    setPreferredSize(preferredSize);
    updateDimensions();
  }
  
  public void paintPlayerInfo(Graphics g) {}
  
  public void updateDimensions(){
    //boardDim = getSize();
    boardDim = preferredSize;
    if(x!=null)
    x.setDimension(boardDim);
    
    //boardDim = new Dimension(500,500);
    int dim = Math.min((boardDim.width-min_leftMargin),(boardDim.height-min_topMargin));
    
    xCell = dim / 8;
    yCell = dim / 8;
    
    leftMargin = (int)((boardDim.width-(8*xCell))/2);
    topMargin = (int)((boardDim.height-(8*yCell))/2);
  }
  
  public void paintBoard(Graphics g){
    updateDimensions();
    
    //background
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0,0,getSize().width,getSize().height);
   
    paintBorder(g);
    for (byte c = 0; c < 8; c++)
      for (byte r = 0; r < 8; r++)
      paintCell(g,c,r);

    /* Active spots */
    if(x!=null)
    if (x.isPieceClicked() || x.isPieceDrag()){
      //System.out.println("show");
      if (game.getActiveMoves() != null)
        if (game.getActiveMoves().size() != 0){
          Move a = (Move) game.getActiveMoves().get(0);
          //g.setColor(Color.BLUE);
          //g.fillRect(leftMargin+(a.x1)*xCell,topMargin+(7-a.y1)*yCell,xCell,yCell);
          for (int i = 0; i < game.getActiveMoves().size(); i++){
            g.setColor(Color.GREEN);
            a = (Move) game.getActiveMoves().get(i);
            if((a.x2+a.y2)%2 == 1)
              g.setColor(new Color(100,100,150));
            else                             
              g.setColor(new Color(100,100,250));  
            //System.out.println(i+". "+a.toString());
            //g.fillRect(leftMargin+(a.x2)*xCell,topMargin+(7-a.y2)*yCell,xCell,yCell);
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.fillOval(leftMargin+(x.isBoardFlipped()?7-a.x2:a.x2)*xCell+5, topMargin+(x.isBoardFlipped()?a.y2:7-a.y2)*yCell+5, xCell-11, yCell-11);
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
      }
    }
    
    //Pieces  
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    if(x!=null || board!=null)
    for (byte c = 0; c < 8; c++)
      for (byte r = 0; r < 8; r++)
        if (board[c][r]!=null)
          if(x!=null) {
            if (!x.isPieceDrag() || (x.getPieceSelectedCoord().y) != r || x.getPieceSelectedCoord().x != c)
              if (!x.isPieceSliding())
                g.drawImage(board[c][r].image,leftMargin+(x.isBoardFlipped()?7-c:c)*xCell,topMargin+(x.isBoardFlipped()?r:7-r)*yCell,null);
              else if (c != x.getLastMove().x2 || r != (x.getLastMove().y2))
                g.drawImage(board[c][r].image,leftMargin+(x.isBoardFlipped()?7-c:c)*xCell,topMargin+(x.isBoardFlipped()?r:7-r)*yCell,null);
          } else if(board!=null) {
            g.drawImage(board[c][r].image,leftMargin+(isBoardFlipped()?7-c:c)*xCell,topMargin+(isBoardFlipped()?r:7-r)*yCell,null);
          }
    
              
    if(x!=null)
    if (x.isPieceSliding() && x.getLastMove().p != null)
      g.drawImage(x.getLastMove().p.image,leftMargin+(x.isBoardFlipped()?7-x.getLastMove().x2:x.getLastMove().x2)*xCell,topMargin+(x.isBoardFlipped()?x.getLastMove().y2:7-x.getLastMove().y2)*yCell,null);
    
    //Dragging piece
    if(x!=null)
    if (x.isPieceDrag())
      g.drawImage(x.getPieceSelected().image,(int)x.getDraggingPieceX(), (int)x.getDraggingPieceY(),null);

    // Sliding piece
    if(x!=null)
    if (x.isPieceSliding()) {
      x.paintSlide(g,leftMargin,topMargin);
    }
    
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
    
    if(x!=null)
      if(mouseOver != x.isMouseOverPieceBlock())
        if(x.isMouseOverPieceBlock()) {
          setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
          mouseOver = true;
        } else {
          setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          mouseOver = false;
        }
    
    //Graphics2D gd2 = (Graphics2D)g;
    //AffineTransform at = new AffineTransform();
    //at.setToRotation(Math.PI/2.0);
    //gd2.setColor(Color.RED);
    //gd2.setTransform(at);
    //gd2.drawString("vertical text",100,100);
    
    //at.setToRotation(0);   
    
    //  paintSlide(g);
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
  }
  
  private boolean mouseOver;
  
  /**
   * This method is called by the paintBoard method; it is called exactly (8*8)
   * 64 times during one frame(refresh) to paint all the board blocks.
   * @param g the graphic on which to paint on
   * @param c the column of the board block
   * @param r the row of the board block
   */
  public void paintCell(Graphics g, byte c, byte r) {
    // Using the graphic "g", paint a rectangle in column "c" and row "r" onto
    // the graphic using a predefined color scheme: Use alternating colors for
    // the board blocks.
    if((c+r)%2 == 1) g.setColor(parameters.getPrimaryCell());//x.settings.tablet.current.primaryCell);
    else             g.setColor(parameters.getAlternateCell());//x.settings.tablet.current.alternateCell);
    g.fillRect(leftMargin+c*xCell, topMargin+(7-r)*yCell, xCell, yCell);
    
    // Now, we have the coordinates of the mouse cursor on the board in 
    // "mouseOn". Paint this rectangle where the mouse coordinates lie on 
    // the graphic "g" a different color using the two alternating colors 
    // below, when applicable (just once).
    if(x!=null)
    if (x.getMouseOn().x == c && x.getMouseOn().y == 7-r){
      if ((c+r)%2!=0) g.setColor(new Color(140,162,181));
      else            g.setColor(new Color(206,214,239)); 
      g.fillRect(leftMargin+c*xCell,topMargin+(7-r)*yCell,xCell,yCell);
    }
  }
}
