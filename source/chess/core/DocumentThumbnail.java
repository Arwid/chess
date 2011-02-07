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
 * Created on Mar 27, 2005
 *
 */

package chess.core;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import chess.gui.board.*;
import chess.gui.panels.ChessComponent;
import chess.properties.State;

/**
 * DocumentThumbnail displays a thumbnail visualization of a chess game
 * DocumentThumbnail's are used by ChessDocumentView
 * 
 * @author Arvydas Bancewicz
 */
public class DocumentThumbnail extends JPanel {
    
  private ChessGame chessGame; // the chess game
  private ChessBoard board;    // ChessBoardSmallView
    
  private JPanel center;
  private JPanel header; // thumbnail header
  private JLabel label;  // header label
  private JButton exit;  // exit button
  
  private Dimension maxSize; // maximized thumbnail size
  private Dimension minSize; // minimized thumbnail size
  private boolean minimized = false; // is minimized ?
  private boolean selected = true;   // is selected ?
  
  private int gIndex; // game index
  
  private InfoLabel infoLabel; // game info
  
  /**
   * Constructor
   * @param game - the chess game to represent
   * @param index - the chess game's index
   */
  public DocumentThumbnail(ChessGame game, int index) {
    
    setLayout(new BorderLayout());
    setBorder(new LineBorder(Color.WHITE));
    
    // Save parameters game and index
    chessGame = game;
    setIndex(index);
    
    // Create the chess board
    board = new ChessBoardSmallView(game);
    board.setPreferredDimension(new Dimension(128,128));
    
    //------------------ Header ---------------------------------------------
    
    header = new JPanel(new BorderLayout());
    header.setBorder(new EtchedBorder());
    label = new JLabel(game.getTitle());
    header.add(label,BorderLayout.WEST);
    
    // Exit button for Header
    exit = new JButton();
    Icon icon = new ImageIcon(getClass().getResource("exit.png"));
    exit.setIcon(icon);
    exit.setPreferredSize(
        new Dimension(icon.getIconWidth(), icon.getIconHeight()));
    exit.setContentAreaFilled(false);
    header.add(exit,BorderLayout.EAST);
    
    //------------------ Center ---------------------------------------------
    center = new JPanel(new FlowLayout(FlowLayout.LEFT));
    center.setBorder(new EtchedBorder());
    center.setBackground(Color.WHITE);
    
    // Board and info for Center
    JPanel display = new JPanel(new BorderLayout());
    display.add(board,BorderLayout.CENTER);
    center.add(display);
    infoLabel = new InfoLabel(chessGame);
    center.add(infoLabel);
    
    //------------------ DocumentThumbnail ----------------------------------
    setBorder(new LineBorder(Color.WHITE));
    add(header,BorderLayout.NORTH);
    add(center,BorderLayout.CENTER);
    
    // Set Maximized and Minimized view dimensions
    int vgap = 4; // extra gap left when in minimized view
    minSize = new Dimension(400,header.getPreferredSize().height+vgap);
    maxSize = new Dimension(400, getPreferredSize().height);
    setMinimized(false); // set maximized
      
    //------------------ Listeners ------------------------------------------
    // Remove game when exit is clicked
    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ChessDocuments.getInstance().removeAt(gIndex);
        ChessComponent.getInstance().view.removeAt(gIndex);
      }});
      
    // Set this thumbnail selected when the center is pressed
    center.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent arg0) {
        ChessComponent.getInstance().view.setSelected(gIndex);
      }
      public void mouseEntered(MouseEvent e) {
        setBorder(new LineBorder(new Color(150,150,250)));
      } 
      public void mouseExited(MouseEvent e) {
        setBorder(new LineBorder(Color.WHITE));
      }
    });
    
    // Maximize or minimize thumbnail when the header is pressed
    header.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(minimized)
          setMinimized(false);
        else
          setMinimized(true);
      }
      public void mouseEntered(MouseEvent e) {
        setBorder(new LineBorder(new Color(150,150,250)));
      }
      public void mouseExited(MouseEvent e) {
        setBorder(new LineBorder(Color.WHITE));
      }  
    });
    
  }
  
  /**
   * Set the thumbnail index
   * @param index
   */
  public void setIndex(int index) {
    gIndex = index;
  }
  
  /**
   * Update the thumbnail info
   *
   */
  public void updateInfo() {
    label.setText(chessGame.getTitle());
    header.repaint();
    infoLabel.repaint();
  }
    
  /**
   * Set boolean if this thumbnail is maximized or minimized.
   * Maximize or minimize the thumbnail.
   * @param minimized - minimize ?
   */
  public void setMinimized(boolean minimized) {
    if (minimized) {
      setMaximumSize(minSize);
      setPreferredSize(new Dimension(0, minSize.height));
      this.minimized = true;
    } else {
      setMaximumSize(maxSize);
      setPreferredSize(new Dimension(0, maxSize.height));
      this.minimized = false;
    }
    repaint();
    revalidate();
  }
    
  /**
   * Is the thumbnail minimized ?
   * @return minimized
   */
  public boolean isMinimized() {
    return minimized;
  }
    
  /**
   * Set boolean if this thumbnail game is selected or not
   * Indicate the thumbnail being selected, or not.
   * @param bool
   */
  public void select(boolean bool) {
    selected = bool;
    infoLabel.setSelected(bool);
    
    if(bool) { // Selected
      Color selection = (Color)UIManager.get("List.selectionBackground");
      center.setBackground(selection);
      chessGame.resume(); // resume game
      
    } else {  // Not selected
      center.setBackground(Color.WHITE);
      chessGame.pause(); // pause game
      State a;
      if (chessGame.state.equals(State.WAITING))
        center.setBackground(Color.RED);
    }
    revalidate();
  }
    
  /**
   * Get this thumbnail's game
   * @return chessGame
   */
  public ChessGame getGame() {
    return chessGame;
  }
}
