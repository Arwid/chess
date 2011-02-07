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
package chess.gui.panels;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import chess.core.ChessGame;
import chess.core.ComponentList;
import chess.core.Constants;
import chess.core.ImageManipulator;
import chess.core.PieceCounts;
import chess.media.BoardMedia;

public class PiecesPanel extends JPanel implements Constants, ActionListener{
  
  private JPanel topPanel;
  private JLabel whiteLabel;
  private JLabel blackLabel;
  
  private JPanel piecePanel;
  private JPanel whitePieces;
  private JPanel blackPieces;

  private JPanel radioButtonPanel;
  private ButtonGroup radioButtonGroup;
  private JRadioButton captured;
  private JRadioButton onBoard;
  
  private boolean displayOnBoard;
  
  private BoardMedia boardMedia;
  
  public PiecesPanel() {
    super(new BorderLayout());
    
    boardMedia = new BoardMedia(BoardMedia.set_4);
    
    // Build the top panel
    whiteLabel = new JLabel("Player1");
    blackLabel = new JLabel("Player2");
    topPanel = new JPanel();
    topPanel.add(whiteLabel);
    topPanel.add(blackLabel);
    add(topPanel,BorderLayout.NORTH);
    
    // Build the piece panel
    buildPiecePanel();
    add(piecePanel,BorderLayout.CENTER);
    
    Vector vector = new Vector();
    ComponentList list = new ComponentList();
    list.setListData(vector);
    ComponentList list2 = new ComponentList();
    list2.setListData(vector);
    vector.add(new PieceLabel(TYPE_PAWN,true));
    vector.add(new PieceLabel(TYPE_PAWN,false));
    
    JPanel c = new JPanel();
    c.add(list);
    c.add(list2);
    
    //add(c,BorderLayout.CENTER);

    // Build the radio button panel
    captured   = new JRadioButton("captured");
    onBoard    = new JRadioButton("on board");
    captured.addActionListener(this);
    onBoard.addActionListener(this);
    radioButtonGroup = new ButtonGroup();
    radioButtonGroup.add(captured);
    radioButtonGroup.add(onBoard);
    captured.setSelected(true);
    refreshPieceView();
    radioButtonPanel = new JPanel();
    radioButtonPanel.add(captured);
    radioButtonPanel.add(onBoard);
    add(radioButtonPanel,BorderLayout.SOUTH);

  }
  
  public void refreshPieceCount(PieceCounts counts) {
    whiteLabel.setText(""+counts.whiteCount);
    blackLabel.setText(""+counts.blackCount);
    
    int whitePawns   = counts.getCount(TYPE_PAWN,true);
    int blackPawns   = counts.getCount(TYPE_PAWN,false);
    int whiteKnights = counts.getCount(TYPE_KNIGHT,true);
    int blackKnights = counts.getCount(TYPE_KNIGHT,false);
    int whiteBishops = counts.getCount(TYPE_BISHOP,true);
    int blackBishops = counts.getCount(TYPE_BISHOP,false);
    int whiteRooks   = counts.getCount(TYPE_ROOK,true);
    int blackRooks   = counts.getCount(TYPE_ROOK,false);
    int whiteQueens  = counts.getCount(TYPE_QUEEN,true);
    int blackQueens  = counts.getCount(TYPE_QUEEN,false);
    int whiteKings   = counts.getCount(TYPE_KING,true);
    int blackKings   = counts.getCount(TYPE_KING,false);
    
    boolean dob = displayOnBoard;
    
    int siz = whitePieces.getComponentCount();
    for (int i=0;i<siz;i++) {
      PieceLabel label = ((PieceLabel)whitePieces.getComponent(i));
      if (label.white) {
        switch(label.type) {
          case TYPE_PAWN:
            if (whitePawns-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_KNIGHT:
            if (whiteKnights-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_BISHOP:
            if (whiteBishops-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_ROOK:
            if (whiteRooks-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_QUEEN:
            if (whiteQueens-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_KING:
            if (whiteKings-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
        }
      }
    }
    //System.out.println("black pawn:"+blackPawns);
    siz = blackPieces.getComponentCount();
    for (int i=0;i<siz;i++) {
      PieceLabel label = ((PieceLabel)blackPieces.getComponent(i));
      if (!label.white) {
        switch(label.type) {
          case TYPE_PAWN:
            if (blackPawns-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_KNIGHT:
            if (blackKnights-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_BISHOP:
            if (blackBishops-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_ROOK:
            if (blackRooks-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_QUEEN:
            if (blackQueens-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
          case TYPE_KING:
            if (blackKings-->0)
              label.setActive(true);
            else
              label.setActive(false);
            break;
        }
      }
    }
    //siz = blackPieces.getComponentCount();
    //int blackPanws
    refreshPieceView();
  }
  
  private void refreshPieceView() {
    int siz = whitePieces.getComponentCount();
    for (int i=0;i<siz;i++) {
      PieceLabel label = ((PieceLabel)whitePieces.getComponent(i));
      label.setActive(label.isActive());
    }
    siz = blackPieces.getComponentCount();
    for (int i=0;i<siz;i++) {
      PieceLabel label = ((PieceLabel)blackPieces.getComponent(i));
      label.setActive(label.isActive());
    }
  }
  
  private void buildPiecePanel() {
    
    GridLayout layout = new GridLayout(0,2);
    whitePieces = new JPanel(layout);
    whitePieces.setBorder(new EtchedBorder());
    blackPieces = new JPanel(layout);
    blackPieces.setBorder(new EtchedBorder());

    // White piece panel
    for(int i=0;i<8;i++)
      whitePieces.add(new PieceLabel(TYPE_PAWN,true));
    whitePieces.add(new PieceLabel(TYPE_KNIGHT,true));
    whitePieces.add(new PieceLabel(TYPE_KNIGHT,true));
    whitePieces.add(new PieceLabel(TYPE_BISHOP,true));
    whitePieces.add(new PieceLabel(TYPE_BISHOP,true));
    whitePieces.add(new PieceLabel(TYPE_ROOK,true));
    whitePieces.add(new PieceLabel(TYPE_ROOK,true));
    whitePieces.add(new PieceLabel(TYPE_QUEEN,true));
    whitePieces.add(new PieceLabel(TYPE_KING,true));
    
    // Black piece panel
    for(int i=0;i<8;i++)
      blackPieces.add(new PieceLabel(TYPE_PAWN,false));
    blackPieces.add(new PieceLabel(TYPE_KNIGHT,false));
    blackPieces.add(new PieceLabel(TYPE_KNIGHT,false));
    blackPieces.add(new PieceLabel(TYPE_BISHOP,false));
    blackPieces.add(new PieceLabel(TYPE_BISHOP,false));
    blackPieces.add(new PieceLabel(TYPE_ROOK,false));
    blackPieces.add(new PieceLabel(TYPE_ROOK,false));
    blackPieces.add(new PieceLabel(TYPE_QUEEN,false));
    blackPieces.add(new PieceLabel(TYPE_KING,false));
    
    piecePanel = new JPanel();
    piecePanel.add(whitePieces);
    piecePanel.add(blackPieces);
  }
  
  /** Create a label with a piece image icon */
  class PieceLabel extends JLabel {
    
    private Image image;
    public byte type;
    public boolean white;
    private boolean active;
    
    public PieceLabel(byte type, boolean white) {
      this.type = type;
      this.white = white;
      image = boardMedia.largePieces.getImage(type,white);
      setPreferredSize(new Dimension(image.getWidth(null),image.getHeight(null)));
      if(type == TYPE_KING)
       // setIcon(image);
      //else
      //  setIcon(new ImageIcon(Images.getInstance().enabledPieces.getImage(type,white)));
      setBorder(new LineBorder(Color.LIGHT_GRAY));
    }
    
    public void setActive(boolean active) {
      this.active = active;
      if (displayOnBoard?active:!active) {
        //setBorder(new LineBorder(Color.BLUE));
        setIcon(new ImageIcon(image));
      } else {
        //setBorder(new LineBorder(Color.YELLOW));
        setIcon(null);
      }
    }

    public boolean isActive() {
      return active;
    }
  }
  
  /** Listen to the check boxes */
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == captured) {
      displayOnBoard = false;
    } else if(e.getSource() == onBoard) {
      displayOnBoard = true;
    }
    refreshPieceView();
  }
  
  public String toString() {
    return "Pieces";
  }
}
