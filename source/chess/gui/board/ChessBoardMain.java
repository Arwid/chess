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
import java.awt.geom.AffineTransform;

import chess.algorithms.*;
import chess.core.*;
import chess.media.BoardMedia;
import chess.properties.*;

/**
 * ChessBoardMain is the main and large ChessBoard. This ChessBoard is 
 * what the ChessBoardVirtual is based on.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class ChessBoardMain extends ChessBoard {
  
  static final Dimension defaultSize = new Dimension(500,500);
  private Dimension preferredSize;
  
  // Board parameters (colors, etc)
  private BoardParameters parameters;
  
  // Board images (pieces)
  private BoardMedia images;
  int borderThickness = 4; // pixel(s)
  private boolean mouseOver;
  
  /**
   * Constructor. Make a default ChessBoard with no game.
   *
   */
  public ChessBoardMain() {
    super();
    
    x = new ChessBoardVirtual();
    setPreferredDimension(defaultSize);
    parameters = new BoardParameters();
    x.setParameters(parameters);
    
    images = parameters.boardImages;
    addMouseListeners();
    updateDimensions();
    refreshBoard();
  }
  
  /**
   * Constructor. Make an empty ChessBoard
   * @param addListeners - add mouse listeners ?
   */
  public ChessBoardMain(boolean addListeners) {
    super();
    
    setPreferredDimension(defaultSize);
    parameters = new BoardParameters();
    images = parameters.boardImages;
    
    if (addListeners)
      addMouseListeners();
    
    refreshBoard();
  }
  
  /**
   * Constructor. Make a ChessBoard using a virtual board.
   * @param x - the ChessBoardVirtual
   * @param addListeners - add mouse listeners ?
   */
  public ChessBoardMain(ChessBoardVirtual x, boolean addListeners) {
    super(x);
    
    setPreferredDimension(defaultSize);
    parameters = new BoardParameters();
    images = parameters.boardImages;
    refreshBoard();
    
    if (addListeners)
      addMouseListeners();
  }
  
  /**
   * Contructor. Make a ChessBoard using a game
   * @param game - the ChessGame
   */
  public ChessBoardMain(ChessGame game) {
    super(game);
    
    setPreferredDimension(defaultSize);
    parameters = new BoardParameters();
    images = parameters.boardImages;
    refreshBoard();
  }
  
  /**
   * Refresh/Scale the piece images for the board.
   */
  protected void refreshBoard() {
    System.out.println("REFRESH BOARD");
    if (images!=null)
      images.scaleImages(xCell,yCell);
    // IMAGES SCALED
    repaint();
  }
  
  // ------------- Paint ------------------------------------------------------
  
  /**
   * The paint method is called when ever the panel area needs to be repainted
   */
  public void paint(Graphics g) {
    paintBoard(g);
    if (game != null) {
      //System.out.println("can move: "+game.canMove());
      MoveAlgorithm ma = new AlfaBeta(game);
      if (ma.checkMate()) {
        System.out.println("CHECKMATE");
      }
    }
  }
  
  /**
   * Paint the chess board. PaintBoard calls all the necessary paint methods.
   */
  public void paintBoard(Graphics g) {
    
    preferredSize = getSize();
    updateDimensions();
    
    // Background
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0,0,getSize().width,getSize().height);
   
    // Paint border and board cells
    paintBorder(g);
    for (byte c = 0; c < 8; c++)
      for (byte r = 0; r < 8; r++)
      paintCell(g,c,r);
    
    if (parameters==null || parameters.showActiveSpots())
    	// Paint legal move cells
    	paintActiveSpots(g);
    
    if (parameters==null || parameters.showArrows())
      // Paint arrows
      paintArrows(g);
    
    // Paint inanimate and animate pieces
    paintPieces(g);
    
    if (parameters==null || parameters.allowCursorChange())
    if(x!=null)
      if(mouseOver != x.isMouseOverPieceBlock())
        if(x.isMouseOverPieceBlock()) {
          setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
          mouseOver = true;
        } else {
          setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          mouseOver = false;
        }
  }
  
  /**
   * This method is called by the paintBoard method; it is called exactly (8*8)
   * 64 times during one repaint to paint all the board cells
   * @param g the graphic on which to paint on
   * @param c the cell column #
   * @param r the cell row #
   */
  public void paintCell(Graphics g, byte c, byte r) {
    // Using the graphic "g", paint a rectangle in column "c" and row "r" onto
    // the graphic using a predefined color scheme: Use alternating colors for
    // the board blocks.
    if((c+r)%2 == 1) g.setColor(parameters==null?Color.GRAY:parameters.getPrimaryCell());
    else             g.setColor(parameters==null?Color.LIGHT_GRAY:parameters.getAlternateCell());
    g.fillRect(leftMargin+c*xCell, topMargin+(7-r)*yCell, xCell, yCell);
    
    if (images!=null) {
      //Image image = images.getImage(true,TYPE_KING);
      //BufferedImage img =
      ///  new BufferedImage(image.getWidth(this), image.getHeight(this),
      //      BufferedImage.TYPE_INT_RGB);
      //Rectangle2D anchor = new Rectangle(235, 70, image.getWidth(this), image.getHeight(this));
      //TexturePaint paint = new TexturePaint(img,anchor);
      //((Graphics2D)g).setPaint(paint);
    }
    
    if (images!=null) {
      
      if((c+r)%2==1) {
        //Surface a = Surface.newGradient(Color.WHITE,Color.BLUE);
        //((Graphics2D)g).setPaint(a.getPaint(0,0,500,500));
        //g.fillRect(leftMargin+c*xCell, topMargin+(7-r)*yCell,xCell, yCell);
        //g.drawImage((Image) a.getPaint(),leftMargin+c*xCell, topMargin+(7-r)*yCell, xCell, yCell, null);
      } else {
        //Surface a = Surface.newGradient(Color.RED,Color.PINK);
        //((Graphics2D)g).setPaint(a.getPaint(0,0,500,500));
        //g.fillRect(leftMargin+c*xCell, topMargin+(7-r)*yCell,xCell, yCell);
        //g.drawImage(images.cell2,leftMargin+c*xCell, topMargin+(7-r)*yCell, xCell, yCell, null);
      }
    }
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
  
  /**
   * This method is called by the paintBoard method to paint the border and the
   * board legend.
   */
  public void paintBorder(Graphics g) {
    Object antialising;
    
    // Get antialising value from the ChessSettings
    try   { antialising = ChessSettings.getBoardAntialiasing(); } 
    catch (ExceptionInInitializerError e) { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; } 
    catch (NoClassDefFoundError e)        { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; }
    
    //border
    g.setColor(Color.BLACK);
    g.fillRect(leftMargin-borderThickness,topMargin-borderThickness,borderThickness,8*yCell+2*borderThickness); //left
    g.fillRect(leftMargin-borderThickness,topMargin-borderThickness,8*xCell+2*borderThickness,borderThickness); //top
    g.fillRect(leftMargin-borderThickness,topMargin+8*yCell,8*xCell+2*borderThickness,borderThickness); //bottom
    g.fillRect(leftMargin+8*xCell,topMargin-borderThickness,borderThickness,8*yCell+2*borderThickness); //right
    
    //draw legend
    if (parameters==null || parameters.showLegend()) {
      String[] coord_X = {"A","B","C","D","E","F","G","H"};
      int fieldSize = 8*xCell;
      g.setFont(new Font("sanserif", Font.BOLD, 14));
      g.setColor(Color.WHITE);
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialising);
      if(game!=null)
        for(byte i=0;i<8;i++) {
          g.drawString(coord_X[(game.isBoardFlipped()?7-i:i)], leftMargin+i*xCell+xCell/2-4, topMargin+fieldSize+20);
          g.drawString(""+(game.isBoardFlipped()?7-i+1:i+1), leftMargin-20, topMargin+fieldSize-i*yCell-yCell/2+5);
        }
      ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
  }
  /**
   * This method is called by the paintBoard method to paint both 
   * animate (still) and inatimate (sliding or dragging) pieces in the 
   * appropriate board cells on the graphic
   */
  private void paintPieces(Graphics g) {
    
    //  extra vertical piece space
    int extraY = 0;
    if (images!=null)
      extraY = (int)(yCell*images.getPieceYScale()-yCell);
      
    if(images!=null && (x!=null && x.board!=null) && board!=null)
      
      // Go through all the board cells and draw the pieces there, if any
      for (byte r=(byte)(x.isBoardFlipped()?0:7);x.isBoardFlipped()?r<8:r>=0;
      r+=x.isBoardFlipped()?1:-1) // r = cell row #
      for (byte c=(byte)(x.isBoardFlipped()?0:7);x.isBoardFlipped()?c<8:c>=0; 
                c+=x.isBoardFlipped()?1:-1) // c = cell column #
          
          // There is a piece in this cell
          if (board.b[c][r]!=null) {
            
            // Get an image for the piece
            Image image = images.getImage(board.b[c][r].white, board.b[c][r].type);
            
            // Do not draw a piece in the dragging piece's orginal cell
            if ((!x.isPieceDrag() || (x.getPieceSelectedCoord().y) != r || 
                x.getPieceSelectedCoord().x != c))
            if (!x.isPieceSliding()) { 
              
              // There is no sliding piece and this is not the selected piece. 
              // Draw this piece
              g.drawImage(image,
                  leftMargin+(x.isBoardFlipped()?7-c:c)*xCell,
                  topMargin+(x.isBoardFlipped()?r:7-r)*yCell -extraY,
                  null);
              
                
            } else if (c != x.getLastMove().x2 || r != (x.getLastMove().y2)) {
              
              // This cell is not the sliding piece's destination. Draw this 
              // piece. NOTE: We want to leave the sliding piece's destination 
              // empty for now
              g.drawImage(image,
                  leftMargin+(x.isBoardFlipped()?7-c:c)*xCell,
                  topMargin+(x.isBoardFlipped()?r:7-r)*yCell-extraY,
                  null);
                
            } else if (x.getLastMove().p!=null){
              
              // This cell is the sliding piece's destination and there was
              // previously a piece here.
              // Draw this piece which is going to be captured by the sliding
              // piece
              Image capImage = images.getImage(
                  x.getLastMove().p.white, x.getLastMove().p.type);
              g.drawImage(capImage,
                  leftMargin+(x.isBoardFlipped()?
                      7-x.getLastMove().x2:x.getLastMove().x2)*xCell,
                  topMargin+(x.isBoardFlipped()?
                      x.getLastMove().y2:7-x.getLastMove().y2)*yCell-extraY,
                  null);
              }
              
          // There is no piece in this cell
          } else {
            
            // Draw the sliding piece if there is one and if the sliding piece
            // was originally in this cell
            if (x.isPieceSliding()
                &&game.getLastMove().x1==c && game.getLastMove().y1==r) {
              
              Image image = images.getImage(
                  board.b[game.getLastMove().x2][game.getLastMove().y2].white,
                  board.b[game.getLastMove().x2][game.getLastMove().y2].type);
              g.drawImage(image,
                  leftMargin+x.getSlidingPieceX(),topMargin+x.getSlidingPieceY()-extraY,null);
              
              // Continue the sliding progress (set up the next position)
              x.continueSlide();
              
              // Continue repainting this sliding piece, now in new position
              repaint();
            }
          }
    
    // Draw the piece being dragged, if there is one
    if (x!=null && images!=null)
    if (x.isPieceDrag()) {
      Image image = images.getImage(x.getPieceSelected().white,x.getPieceSelected().type);
      g.drawImage(image,(int)x.getDraggingPieceX(), (int)x.getDraggingPieceY()-extraY,null);
    }
  }
  
  /**
   * This method is called by the paintBoard method to paint arrows to indicate
   * the last game move.
   */
  private void paintArrows(Graphics g) {
    
    // Arrow
    if (x!=null && x.board!=null && board!=null && !x.isPieceSliding()) {
      if (x.getLastMove()!=null) {
        Move m = x.getLastMove();
        Color color = x.getLastMove().p != null ? Color.RED : Color.BLUE;
        drawArrow(((Graphics2D)g), 
            new Point(
                xCell/2+leftMargin+(x.isBoardFlipped()?7-m.x1:m.x1)*xCell,
                yCell/2+topMargin+(x.isBoardFlipped()?m.y1:7-m.y1)*yCell), 
            new Point(
                xCell/2+leftMargin+(x.isBoardFlipped()?7-m.x2:m.x2)*xCell,
                yCell/2+topMargin+(x.isBoardFlipped()?m.y2:7-m.y2)*yCell), 
                (int)(xCell*0.15), 
                color);
      }
    }
  }
  
  /**
   * This method is called by the paintArrows method to construct and draw an 
   * arrow.
   * @param g - the graphic to draw on
   * @param p1 - from this point
   * @param p2 - to this point
   * @param width - the arrow width
   * @param color - the arrow color
   */
  private void drawArrow(Graphics2D g,Point p1,Point p2,int width,Color color){
    
    Object antialising;
    
    // Get the antialising value from the ChessSettings
    try   { antialising = ChessSettings.getBoardAntialiasing(); } 
    catch (ExceptionInInitializerError e) { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; } 
    catch (NoClassDefFoundError e)        { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; }
    
    // set up a a polygon of normal width
    int length = (int)Math.round(p1.distance(p2))-xCell*2/5;
    int tip = 2*width;
    
    int[] x={0,         0,length-tip,length-tip,length,length-tip,length-tip,};
    int[] y={-width,width,     width,       tip,     0,      -tip,    -width,};
    
    // Rotate into place
    AffineTransform oldTransform = g.getTransform();
    AffineTransform tf = (AffineTransform)oldTransform.clone();
    tf.translate(p1.x,p1.y);
    double angle = Math.atan2(p2.y-p1.y, p2.x-p1.x);
    tf.rotate(angle);
    g.setTransform(tf);
    
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialising);
    // Fill in the arrow
    g.setColor(color);
    g.fillPolygon(x,y, x.length);
    
    // Draw in the arrow's edge
    g.setColor(Color.black);
    g.drawPolygon(x,y, x.length);
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    
    g.setTransform(oldTransform);
  }
  
  /**
   * This method is called by the paintBoard method to draw visual indications
   * of legal move cells (such as circles).
   */
  private void paintActiveSpots(Graphics g) {
    Object antialising;
    
    // Get the antialising value from ChessSettings
    try   { antialising = ChessSettings.getBoardAntialiasing(); } 
    catch (ExceptionInInitializerError e) { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; } 
    catch (NoClassDefFoundError e)        { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; }
    
    
    // Active spots 
    if(x!=null && game!=null)
    if (x.isPieceClicked() || x.isPieceDrag()){
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
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialising);
            g.fillOval(leftMargin+(x.isBoardFlipped()?7-a.x2:a.x2)*xCell+5, topMargin+(x.isBoardFlipped()?a.y2:7-a.y2)*yCell+5, xCell-11, yCell-11);
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
      }
    }
  }
  
  // ------------- Set values -------------------------------------------------
  
  private void addMouseListeners() {
    
    // Add the mouse listeners from the virtual chess board
    addMouseListener(x); 
    addMouseMotionListener(x);
    
    // Repaint the board on mouse events
    
    addMouseListener(new MouseAdapter(){
      public void mouseClicked (MouseEvent e) { repaint(); }
      public void mousePressed (MouseEvent e) { repaint(); }
      public void mouseReleased(MouseEvent e) { repaint(); }
      public void mouseDragged (MouseEvent e) { repaint(); }
      public void mouseMoved   (MouseEvent e) { repaint(); }  
    });
    
    addMouseMotionListener(new MouseMotionListener(){
      public void mouseDragged(MouseEvent arg0) { repaint();}
      public void mouseMoved  (MouseEvent arg0) { repaint(); }      
    }); 
  }
  
  public void setBoardParameters(BoardParameters parameters) {
    this.parameters = parameters;
    images = parameters.boardImages;
    refreshBoard();
  }
  
  public void setPreferredDimension(Dimension preferred) {
    this.preferredSize = preferred;
    setPreferredSize(preferredSize);
    updateDimensions();
    refreshBoard();
    updateDimensions();
    repaint();
  }
  
  public void updateDimensions(){
    
    boolean resize = false;
    if ((boardDim.getHeight() != preferredSize.getHeight()) ||
        (boardDim.getWidth() != preferredSize.getWidth())) {
      boardDim = preferredSize;
      resize = true;
    }
    if(x!=null)
    x.setDimension(this);
    
    int dim = Math.min((boardDim.width-min_leftMargin),(boardDim.height-min_topMargin));
    if (!(parameters==null || parameters.showLegend())) {
      dim = Math.min(boardDim.width,boardDim.height)-2*borderThickness;
    }
    xCell = dim / 8;
    yCell = dim / 8;
    
    leftMargin = (int)((boardDim.width-(8*xCell))/2);
    topMargin = (int)((boardDim.height-(8*yCell))/2);
    
    if (resize)
      refreshBoard();
  }
  
  // ------------- Get values -------------------------------------------------
  
  public BoardParameters getBoardParameters() {
    return parameters;
  }
  
  public Dimension getDefaultSize() {
    return defaultSize;
  }
  
  /** This method is called by the configureSlide method to prevent the user
   * from selecting a piece while a piece is sliding. This method removes the 
   * mouseListener, but not the mouseMotionListener.
   */
  public void removeMouse() {
    //removeMouseListener(this);
  }
  
  /**
   * This method is called by the paintSlide method to give the user the
   * ability to once again to select a piece. This method adds the 
   * mouseListener.
   */
  public void addMouse() {
    //addMouseListener(this);
  }
  
} // end of class


  // IGNORE THE SYNTAX BELOW. The syntax would be implemented if a runnable 
  // interface was used. Implementing a runnable interface is not necessary 
  // since we do not want to have the board continually refreshing or 
  // repainting. Rather, the board only needs to be repainted when needed; 
  // when mouse listeners are called, after piece moves, or for animating a 
  // sliding piece.
  
  /*
    
  // This method is called by paint to paint thread info onto the graphic.
  // @param g graphic on which to paint on
  //
  public void displayFPS(Graphics g){
    g.setFont(new Font("Monospaced",Font.PLAIN,12));
    g.setColor(Color.GREEN);
    g.drawString((int)fps+" fps  Delay: "+delay+"s",30,30);
    //g.drawString("Thread priority: "+runner.getPriority()+
    //    " isInterrupted: "+runner.isInterrupted(),30,45);
  }
  
  protected double fps;
  protected double delay;
  
  public void interruptThread() {
    //if (!activeThread && (computer == null || !computer.isAlive())) runner.suspend();
    //if (!activeThread) { 
    //  runner.interrupt();
    //  repaint();
    //}
  }
  
  public void resumeThread() {}

  Thread   runner;
  public void start() {    
    if(runner==null){
      runner=new Thread(this); 
      runner.start();
      runner.setPriority(1);
    } 
  }
  
  // This method is implemented by the Runnable interface. This method is
  // called by the start method; otherwise, this method does nothing.
   
  public void run() {
    setBackground(Color.WHITE);
    repaint();
    //ChessTools c = new ChessTools(cc);
    
    delay = 1; //seconds
    int frame = 0;
    Date stamp1 = new Date();
    
    while(true){
      //if(c.checkMate()) {
      //  System.out.println("CheckMate!!");
      //} else {
        try {
        Thread.sleep(1);
        }catch(InterruptedException e){}
        repaint();
        
        Date stamp2 = new Date();
        double milliDiff = stamp2.getTime() - stamp1.getTime();
        double seconds = milliDiff/1000;
      //}
        frame++;
        if (seconds >= delay) {
          stamp1 = new Date();
          fps = frame/seconds;
          //System.out.println((int)fps+"fps");
          frame = 0;
        } 
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
}
*/
