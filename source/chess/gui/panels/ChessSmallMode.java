/*
 * Created on May 15, 2005
 *
 */
package chess.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.plaf.metal.MetalIconFactory;

import chess.core.InfoLabel;
import chess.gui.Chess;

/**
 * @author Arvydas Bancewicz
 *
 */
public class ChessSmallMode {
  
  ChessComponent cc;
  
  JPanel centerboard;
  
  public ChessSmallMode() {
    
    cc = ChessComponent.getInstance();
    cc.flip.setEnabled(true);
    cc.modeSwitch.setEnabled(true);
    cc.modeSwitch.setText("Switch to Full Mode");
    
    // Create the new frame
    final JFrame frame = new JFrame();
    frame.setTitle(Chess.getInstance().getTitle());
    
    cc.modeSwitch.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        cc.view.returnControlToolBar();
        frame.dispose();
      }});
    
    Icon icon = MetalIconFactory.getFileChooserUpFolderIcon();
    cc.modeSwitch.setIcon(icon);
    cc.zoom.setEnabled(false);
    
    
    centerboard = new JPanel(new BorderLayout());
    JPanel center = new JPanel(new BorderLayout());
    center.add(cc.chessBoard,BorderLayout.CENTER);
    
    InfoLabel info = new InfoLabel(cc.chessGame);
    info.setPreferredSize(new Dimension(50,5));
    info.setBackground(Color.BLUE);
    center.add(info,BorderLayout.EAST);
    
    centerboard.add(cc.whiteBoardLabel,BorderLayout.SOUTH);
    centerboard.add(cc.blackBoardLabel,BorderLayout.NORTH);
    centerboard.add(center,BorderLayout.CENTER);
    
    JPanel whole = new JPanel(new BorderLayout());
    whole.add(centerboard,BorderLayout.CENTER);
    
    JToolBar control = cc.view.getControlToolBar();
    
    JButton gameBT = new JButton();
    gameBT.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        Chess.getInstance().createNewGame();
      }});
    gameBT.setIcon(new ImageIcon(getClass().getResource("normal2.png")));
    
    JToolBar bottom = new JToolBar();
    bottom.setRollover(true);
    bottom.setFloatable(false);
    
    bottom.add(gameBT);
    bottom.add(control);
    
    whole.add(bottom, BorderLayout.SOUTH);
    frame.getContentPane().add(whole);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    
    // Detect if the window is closing
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent arg0) {
        cc.view.returnControlToolBar();
        Chess.getInstance().setMode(true);
        cc.setUpChessBoard();
      }
    });
  }
  
  public void updateForBoardFlip() {
    
    boolean flip = cc.flipBoard.isSelected();
    
    // Reposition(flip) the player labels
    centerboard.remove(cc.whiteBoardLabel);
    centerboard.remove(cc.blackBoardLabel);
    if (flip) {
      centerboard.add(cc.whiteBoardLabel, BorderLayout.NORTH);
      centerboard.add(cc.blackBoardLabel, BorderLayout.SOUTH);
    } else {
      centerboard.add(cc.whiteBoardLabel, BorderLayout.SOUTH);
      centerboard.add(cc.blackBoardLabel, BorderLayout.NORTH);
    }
    centerboard.revalidate();
  }
}
