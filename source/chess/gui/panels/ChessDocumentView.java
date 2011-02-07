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
 * Created on Mar 21, 2005
 *
 */
package chess.gui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import chess.core.*;

/**
 * ChessDocumentView uses DocumentThumbnail's to display thumbnail 
 * visualizations of the chess games in ChessDocuments
 */
public class ChessDocumentView extends JPanel {
  
  private JPanel viewPanel; // panel containing ChessDocumentThumbnail's
  
  // Control buttons
  private JButton left;
  private JButton right;
  private JLabel selectedGameTitle;
  
  private int selectedIndex; // index of thumbnail/game selected
  
  private JToolBar control; // Control tool bar
  
  public JToolBar getControlToolBar() {
    return control;
  }
  public void returnControlToolBar() {
    add(control,BorderLayout.SOUTH);
  }
  
  public ChessDocumentView() {
    
    setLayout(new BorderLayout());
    
    viewPanel = new JPanel();
    viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.PAGE_AXIS));
    
    // Add the viewPanel to a scrollPane
    JScrollPane scrollPane = new JScrollPane(viewPanel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    add(scrollPane,BorderLayout.CENTER);
    
    // Control tool bar
    control = new JToolBar();
    control.setRollover(true);
    control.setFloatable(false);
    
    control.add(left  = new JButton());
    left.setIcon(new ImageIcon(getClass().getResource("left.png")));  
    
    control.add(right = new JButton());
    right.setIcon(new ImageIcon(getClass().getResource("right.png")));
    
    control.add(selectedGameTitle = new JLabel());
    
    
    add(control,BorderLayout.SOUTH);
    
    //------------------ Listeners --------------------------------------------
    
    left.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        setSelected(--selectedIndex);
      }});
    
    right.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        setSelected(++selectedIndex);
      }});
    
    
    addToList();
    refreshControls();
    refreshSelected();
  }
  
  /**
   * Enable and disable the control buttons when needed
   */
  private void refreshControls() {
    if (selectedIndex>0)
      left.setEnabled(true);
    else
      left.setEnabled(false);
    
    if (selectedIndex<viewPanel.getComponentCount()-1)
      right.setEnabled(true);
    else
      right.setEnabled(false);
    
    if (viewPanel.getComponentCount()>0) {
      String text = ((ChessGame)ChessDocuments.getInstance().getElementAt(selectedIndex)).getTitle();
      selectedGameTitle.setText((selectedIndex+1)+"/"+viewPanel.getComponentCount()+" "+text);
    } else {
      selectedGameTitle.setText("");
    }
  }
  
  /**
   * Remove a ChessDocumentThumbnail from the viewPanel
   * @param index the thumbnail's index #
   */
  public void removeAt(int index) {
    viewPanel.remove(index);
    if (selectedIndex!=0 && index <= selectedIndex)
      setSelected(--selectedIndex);
    int siz = viewPanel.getComponentCount();
    while(index < siz) {
      ((DocumentThumbnail)viewPanel.getComponent(index)).setIndex(index);
      index++;
    }
    refreshDocuments();
    refreshControls();
    refreshSelected();
  }
  
  /**
   * Add ChessDocumentThumbnail(s) to the viewPanel if chess games have been 
   * added to the ChessDocuments list
   */
  public void addToList() {
    int docuNum = viewPanel.getComponentCount();
    int siz = ChessDocuments.getInstance().getSize();
    for(int i=docuNum; i<siz; i++) {
      ChessGame game = (ChessGame)ChessDocuments.getInstance().getElementAt(i);
      viewPanel.add(new DocumentThumbnail(game,i));
    }
    if (docuNum < siz)
      setSelected(siz);
    refreshDocuments();
    refreshControls();
    refreshSelected();
  }
  
  /**
   * Minimize or maximize the chess game thumbnails
   * @param yes true for minimize, false for maximize
   */
  public void minimizeAll(boolean minimize) {
    int docuNum = viewPanel.getComponentCount();
    for(int i=0; i<docuNum; i++) {
      ((DocumentThumbnail)viewPanel.getComponent(i)).setMinimized(minimize);
    }
    refreshDocuments();
  }
  
  /**
   * Refresh the ChessDocumentView panel
   */
  public void refreshDocuments() {
    repaint();
    validate();
  }
  
  public void updateThumbnails() {
    int docuNum = viewPanel.getComponentCount();
    for(int i=0; i<docuNum; i++) {
        ((DocumentThumbnail)viewPanel.getComponent(i)).updateInfo();
    }
  }
  
  /**
   * Set the selected index corresponding to the selected chess game
   * @param index
   */
  public void setSelected(int index) {
    selectedIndex = index;
    refreshSelected();
    ChessGame game = ((DocumentThumbnail)viewPanel.getComponent(selectedIndex)).getGame();
    ChessComponent.getInstance().changeGame(game);
  }
  
  /**
   * Set the current game to no game - an empty board
   * @throws Exception
   */
  private void setNone() throws Exception {
    ChessComponent.getInstance().changeGame(null);
  }
  
  /**
   * Refresh the ChessDocumentThumbnails in respect to a thumbnail/game 
   * selection change
   */
  private void refreshSelected() {
    int docuNum = viewPanel.getComponentCount();
    for(int i=0; i<docuNum; i++) {
      if (i==selectedIndex)
        ((DocumentThumbnail)viewPanel.getComponent(i)).select(true);
      else
        ((DocumentThumbnail)viewPanel.getComponent(i)).select(false);
    }
    if (docuNum!=0 && docuNum-1<selectedIndex)
      setSelected(docuNum-1);
    else if (docuNum==0)
      try {
        setNone();
      } catch (Exception e) {}
    
    refreshControls();
  }
  
  /**
   * Get the selected thumbnail/game index
   * @return selectedIndex
   */
  public int getSelected() {
    return selectedIndex;
  }
  
}
