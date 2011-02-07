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

import chess.core.ChessGame;
import chess.media.BoardMedia;

public class ChessBoardSmallView extends ChessBoard {
  
  // The preferred size of the panel
  private Dimension preferredSize;
  
  // Board images (pieces)
  private BoardMedia images;
  
  /**
   * Contructor. Make a ChessBoard using a game
   * @param game - the ChessGame
   */
  public ChessBoardSmallView(ChessGame g) {
    super(g);
    System.out.println("ChessBoardSmallView(ChessGame g)");
    //super.init();
    //start();
    images = new BoardMedia(BoardMedia.set_1);
    
    preferredSize = new Dimension(200,200);
    setPreferredSize(preferredSize);
    updateDimensions();
  }
  
  /**
   * This method is called by the paint method;
   */
  public void paintBoard(Graphics g){

    //background
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0,0,getSize().width,getSize().height);
   
    //paintBorder(g);
    for (byte c = 0; c < 8; c++)
      for (byte r = 0; r < 8; r++)
      paintCell(g,c,r);

    //Pieces
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    if(board!=null)
    for (byte c = 0; c < 8; c++)
      for (byte r = 0; r < 8; r++)
        if (board.b[c][r]!=null) {
          Image image = images.getImage(board.b[c][r].white,board.b[c][r].type);
          g.drawImage(image,leftMargin+(isBoardFlipped()?7-c:c)*xCell,topMargin+(isBoardFlipped()?r:7-r)*yCell,xCell,yCell,null);
        }
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
    
    // We do not wait to paint the sliding piece
    
    //Sliding piece
    //if (x.isPieceSliding())
    //  paintSlide(g);
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
  }
  
  /**
   * This method is called by the paintBoard method; it is called exactly (8*8)
   * 64 times during one repaint to paint all the board blocks.
   * @param g the graphic on which to paint on
   * @param c the column of the board block
   * @param r the row of the board block
   */
  public void paintCell(Graphics g, byte c, byte r) {
    // Using the graphic "g", paint a rectangle in column "c" and row "r" onto
    // the graphic using a predefined color scheme: Use alternating colors for
    // the board blocks.
    if((c+r)%2 == 1) g.setColor(Color.LIGHT_GRAY);
    else             g.setColor(Color.WHITE);
    g.fillRect(leftMargin+c*xCell, topMargin+(7-r)*yCell, xCell, yCell);
  }
  
  // ------------- Set values -------------------------------------------------
  
  public void setPreferredDimension(Dimension preferredSize) {
    this.preferredSize = preferredSize;
    setPreferredSize(preferredSize);
    updateDimensions();
  }
  
  public void updateDimensions(){
    
    boolean resize = false;
    if ((boardDim.getHeight() != preferredSize.getHeight()) ||
        (boardDim.getWidth() != preferredSize.getWidth())) {
      boardDim = preferredSize;
      resize = true;
    }
    
    int dim = Math.min((boardDim.width),(boardDim.height));
    
    xCell = dim / 8;
    yCell = dim / 8;
    
    leftMargin =0;
    topMargin =0;
    
    if (resize)
      images.scaleImages(xCell,yCell);
  }
  
} // end of class

  // IGNORE THE SYNTAX BELOW. The syntax would be implemented if a runnable 
  // interface was used. Implementing a runnable interface is not necessary 
  // since we do not want to have the board continually refreshing or 
  // repainting. Rather, the board only needs to be repainted when needed; 
  // when mouse listeners are called, after piece moves, or for animating a 
  // sliding piece.
  /*
  Thread runner;
  
  public void start() {    
    if(runner==null){
      runner=new Thread(this); 
      runner.start();
      runner.setPriority(1);
    } 
  }
  
  public void run() {
    setBackground(Color.WHITE);
    repaint();
    while(true){
      try {
        Thread.sleep(200);
      } catch(Exception e) {}
      //System.out.println("repaint");
      repaint();
    }
  }

  public void update(Graphics g){
    g.clipRect(0,0,getSize().width,getSize().height);
    repaint();
  }
  
  public void stop(){
    if(runner!=null){
      runner.interrupt();
      runner=null;
    }
  }
  */
