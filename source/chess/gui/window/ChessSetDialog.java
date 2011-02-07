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
package chess.gui.window;

//import ChessBoard;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import chess.core.ChessGame;
import chess.gui.board.*;
import chess.gui.panels.ChessComponent;
import chess.media.BoardMedia;
import chess.properties.BoardParameters;
import chess.properties.Utilities;

public class ChessSetDialog extends JDialog implements ActionListener {
  
  private JButton ok;
  private JButton apply;
  private JButton cancel;
  
  private ChessBoard smallBoard;
  private BoardParameters boardParameters;
  private JList colorList;
  private JList setList;
  
  private JTabbedPane tabbedPane;
  
  private JPanel rightPanel;
  
  Vector params;
  
  public ChessSetDialog(JFrame frame) {
    super(frame,"Chess Set",true);
    //smallBoard = new ChessBoard();
    
    params = BoardParameters.params;
    
    // Set up the components in the window
    createGUI();
    
    //setSize(new Dimension(500,500));
    
    // Resize the window to the minimum size to satisfy the preferred size of
    // each of the components in the layout.
    pack();
    
    // Compute the layout. Validate effectively redoes the layout if necessary
    // deciding on new sizes and locations of all the components in the conainer.
    validate();
    
    // Position the window to the center of the screen
    setLocationRelativeTo(null);
    
    // Prevent the window from resizing
    //setResizable(false);
  }
  
  
  private void createColorTab() {
    
    JLabel setLabel = new JLabel(" Choose a Colour Scheme ");
    String[] list = new String[params.size()];
    for(int i=0;i<params.size();i++) {
      list[i] = ((BoardParameters)params.elementAt(i)).toString();
    }
    colorList = new JList(list);
    
    colorList.setSelectedIndex(1);
    for(int i=0;i<params.size();i++) {
      if (boardParameters.toString().equals(list[i].toString())) {
        colorList.setSelectedIndex(i);
      }
    }
    
    JPanel right = new JPanel(new BorderLayout());
    right.setBorder(new EtchedBorder());
    right.add(setLabel,BorderLayout.NORTH);
    right.add(colorList,BorderLayout.CENTER);
    
    colorList.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e) {
        //BoardParameters b = ((BoardParameters)params.get(colorList.getSelectedIndex()));
        BoardParameters b = smallBoard.getBoardParameters();
        BoardParameters in = ((BoardParameters)params.get(colorList.getSelectedIndex()));
        b.setPrimaryCell(in.getPrimaryCell());
        b.setAlternateCell(in.getAlternateCell());
        smallBoard.setBoardParameters(b);
        //smallBoard.repaint();
        
      }
    });
    
    rightPanel.add(right, BorderLayout.SOUTH);
    //tabbedPane.add(right,"Colors");
    
  }
  
  private void createSetTab() {
    
    JLabel setLabel = new JLabel(" Choose a piece set ");
    final Vector sets = BoardMedia.imageSets;
    
    String[] list = new String[sets.size()];
    
    for(int i=0;i<params.size();i++) {
      list[i] = ((BoardMedia.PieceImageSet)sets.elementAt(i)).getName();
    }
    setList = new JList(list);
    
    JPanel right = new JPanel(new BorderLayout());
    right.setBorder(new EtchedBorder());
    right.add(setLabel,BorderLayout.NORTH);
    right.add(setList,BorderLayout.CENTER);
    
    JPanel center = new JPanel(new BorderLayout());
    center.setBorder(new EtchedBorder());
    center.setPreferredSize(new Dimension(400,300));
    center.add(smallBoard, BorderLayout.CENTER);
    //center.add(right, BorderLayout.EAST);
    
    rightPanel.add(right, BorderLayout.CENTER);
    
    center.add(rightPanel,BorderLayout.EAST);
    tabbedPane.add(center,"Chess Set");
    
    setList.setSelectedIndex(4);
    setList.addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e) {
        int selected = setList.getSelectedIndex();
        BoardParameters bc = smallBoard.getBoardParameters();
        int set = (int)(((BoardMedia.PieceImageSet)sets.elementAt(selected)).getSet());
        bc.boardImages.setPieceSet(set);
        smallBoard.setBoardParameters(bc);
        smallBoard.repaint();
        
      }
    });
  }
  
  public void createGUI() {
    Container cpane = getContentPane();
    //ChessGame game = ChessComponent.getInstance().chessGame;
    ChessBoardVirtual b = new ChessBoardVirtual(new ChessGame());
    smallBoard = new ChessBoardMain(b, false);
    //smallBoard.init();
    smallBoard.setPreferredDimension(new Dimension(500,500));
    //smallBoard.start();
    
    smallBoard.setMinLeftMargin(0);
    smallBoard.setMinTopMargin(0);
    
	//-------------------------------------------------------------------------
	//	Bottom
	//-------------------------------------------------------------------------
    
    ok = new JButton("Ok");
    ok.addActionListener(this);
    apply = new JButton("Apply");
    apply.addActionListener(this);
    cancel = new JButton("Cancel");
    cancel.addActionListener(this);
    
    JPanel bottom = new JPanel();
    bottom.add(ok);
    bottom.add(apply);
    bottom.add(cancel);
    
    //ok.setEnabled(false);
    //apply.setEnabled(false);
    //cancel.setEnabled(false);
    
    tabbedPane = new JTabbedPane();
    rightPanel = new JPanel(new BorderLayout());
    
    boardParameters  = ChessComponent.getInstance().chessBoard.getBoardParameters();
    
    createSetTab();
    createColorTab();
    
    boardParameters = 
      Utilities.copyParam(
          ChessComponent.getInstance().chessBoard.getBoardParameters()
          );
    smallBoard.setBoardParameters(boardParameters);
    
    cpane.setLayout(new BorderLayout());
    cpane.add(tabbedPane, BorderLayout.CENTER);
    //cpane.add(rightPanel, BorderLayout.EAST);
    cpane.add(bottom,BorderLayout.SOUTH);
    
  }

  public void actionPerformed(ActionEvent e) {
    ChessComponent cc = ChessComponent.getInstance();
    if(e.getSource()==ok) {
      //BoardParameters b = ((BoardParameters)params.get(colorList.getSelectedIndex()));
      BoardParameters b = Utilities.copyParam(smallBoard.getBoardParameters());
      cc.chessBoard.setBoardParameters(b);
      cc.chessBoard.repaint();
      dispose();
    } else if(e.getSource()==apply) {
      //BoardParameters b = ((BoardParameters)params.get(colorList.getSelectedIndex()));
      BoardParameters b = Utilities.copyParam(smallBoard.getBoardParameters());
      cc.chessBoard.setBoardParameters(b);
      cc.chessBoard.repaint();
    } else if(e.getSource()==cancel) {
      dispose();
    }
  }
}