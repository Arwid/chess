/*
 * Created on May 10, 2005
 *
 */
package chess.gui.board;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import chess.core.Constants;
import chess.core.Coord;
import chess.core.Move;
import chess.core.Piece;
import chess.media.BoardMedia;
import chess.properties.BoardParameters;
import chess.properties.ChessSettings;

/**
 * @author Arvydas Bancewicz
 *
 */
public class ChessBoardSample extends JPanel implements Constants {

  private Piece[][] board = new Piece[5][5];
  
  private BoardMedia images;
  
  public BoardParameters parameters;
  
  private Move lastMove;
  
  private Coord mouseOn;
  
  int leftMargin = 0;
  int topMargin = 0;
  int xCell = 40;
  int yCell = 40;
  
  /**
   * @param mouse
   */
  public ChessBoardSample() {
    
    mouseOn = new Coord(-1,-1);
    images = new BoardMedia(BoardMedia.set_1);
    parameters = new BoardParameters();
    for (byte i=TYPE_ROOK; i<=TYPE_KING;i++) {
      board[i-TYPE_ROOK][0] = new Piece(i,false);
      board[i-TYPE_ROOK][1] = new Piece(TYPE_PAWN,false);
      board[i-TYPE_ROOK][4] = new Piece(i,true);
      board[i-TYPE_ROOK][3] = new Piece(TYPE_PAWN,true);
    }
    lastMove = new Move(2,2,1,1);
    
    this.addMouseMotionListener(new MouseMotionListener(){

      public void mouseDragged(MouseEvent e) {
        mouseOverPiece(e);
        mouseOnBlock(e);
        repaint();
      }

      public void mouseMoved(MouseEvent e) {
        mouseOverPiece(e);
        mouseOnBlock(e);
        repaint();
      }});
    
    this.addMouseListener(new MouseAdapter(){
      public void mouseExited(MouseEvent arg0) {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        mouseOn.setCoord(-1,-1);
        repaint();
      }
    });
  }
  
  private boolean mouseOverPieceBlock = false;
  
  public void mouseOverPiece(MouseEvent e) {
    Point p = e.getPoint();
    boolean onPieceBlock = false; // Wether or not a cursor is on a board 
                                  // block containing a piece
    // Search for the board block coordinates that correspond with the point
    // location
    if (board!=null)
    search:
      for (int c = 0; c < board.length; c++)
        for (int r = 0; r < board[c].length; r++)
          if (leftMargin+c*xCell <= p.x && p.x < leftMargin+c*xCell+xCell && 
              topMargin+r*yCell <= p.y && p.y < topMargin+r*yCell+yCell){
            if (board[c][r]!=null)
              onPieceBlock = true;
            // Now we have all we need, so stop searching
            break search; 
            // "break" is a branching statement that terminates the statement
            // labeled search redirecting execution out of the search branch
          }
    if (onPieceBlock) {
      mouseOverPieceBlock = true;
      if (parameters==null || parameters.allowCursorChange())
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      System.out.println("allow :" +parameters.allowCursorChange());
    } else {
      mouseOverPieceBlock = false;
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      System.out.println("allow2 :" +parameters.allowCursorChange());
    }
  }
  
  private void mouseOnBlock(MouseEvent e) {
    Point p = e.getPoint();
    
    if (parameters!=null && !parameters.showMouseOver())
      return;
    
    // Search for the board block coordinates that correspond with the point 
    // location
    for (int c = 0; c < board.length; c++)
      for (int r = 0; r < board[c].length; r++)
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
  
  public void paint(Graphics g) {
    paintBoard(g);
  }
  
  private void drawArrow(Graphics2D g,Point p1,Point p2,int width,Color color){
    
    Object antialising;
    
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
  
  private void paintActiveSpots(Graphics g) {
    Object antialising;
    
    try   { antialising = ChessSettings.getBoardAntialiasing(); } 
    catch (ExceptionInInitializerError e) { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; } 
    catch (NoClassDefFoundError e)        { antialising = RenderingHints.VALUE_ANTIALIAS_DEFAULT; }
    
  }
  
  private void paintArrows(Graphics g) {
    
    // Arrow
    if (parameters==null || parameters.showArrows()) {
        Move m = lastMove;
        Color color = lastMove.p != null ? Color.RED : Color.BLUE;
        drawArrow(((Graphics2D)g), 
            new Point(
                xCell/2+leftMargin+m.x1*xCell,
                yCell/2+topMargin+m.y1*yCell), 
            new Point(
                xCell/2+leftMargin+m.x2*xCell,
                yCell/2+topMargin+m.y2*yCell), 
                (int)(xCell*0.15), 
                color);
    }
  }
  
  
  public void paintBoard(Graphics g) {
    
    int height = Math.min(getHeight(),getWidth());
    int width = height;
    
    leftMargin = 0;
    topMargin = 0;
    yCell = height/board[0].length; // row
    xCell = yCell;
    
    // Background
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0,0,getSize().width,getSize().height);
   
    //paintBorder(g);
    for (byte c = 0; c < board.length; c++)
      for (byte r = 0; r < board[0].length; r++)
        paintCell(g,c,r);
    
    paintArrows(g);
    paintActiveSpots(g);
    
    //Pieces
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    if(images!=null)
    if(board!=null)
    for (byte c = 0; c < board.length; c++)
      for (byte r = 0; r < board[c].length; r++)
        if (board[c][r]!=null) {
          Image image = images.getImage(board[c][r].white,board[c][r].type);
          g.drawImage(image,leftMargin+c*xCell,topMargin+r*yCell,xCell,yCell,null);
        }
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
    
  }

	private boolean mouseOver = false;
  
  public void paintCell(Graphics g, byte c, byte r) {
    // Using the graphic "g", paint a rectangle in column "c" and row "r" onto
    // the graphic using a predefined color scheme: Use alternating colors for
    // the board blocks.
    if((c+r)%2 == 1) g.setColor(parameters==null?Color.GRAY:parameters.getPrimaryCell());
    else             g.setColor(parameters==null?Color.LIGHT_GRAY:parameters.getAlternateCell());
    g.fillRect(leftMargin+c*xCell, topMargin+r*yCell, xCell, yCell);
    
    // Now, we have the coordinates of the mouse cursor on the board in 
    // "mouseOn". Paint this rectangle where the mouse coordinates lie on 
    // the graphic "g" a different color using the two alternating colors 
    // below, when applicable (just once).
    if(mouseOn!=null)
    if (mouseOn.x == c && mouseOn.y == r){
      if ((c+r)%2!=0) g.setColor(new Color(140,162,181));
      else            g.setColor(new Color(206,214,239)); 
      g.fillRect(leftMargin+c*xCell,topMargin+r*yCell,xCell,yCell);
    }
  }



}
