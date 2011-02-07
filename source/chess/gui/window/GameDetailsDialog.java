
/*
 * Created on May 15, 2005
 *
 */

package chess.gui.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import chess.algorithms.AlfaBeta;
import chess.algorithms.MiniMax;
import chess.algorithms.MoveAlgorithm;
import chess.algorithms.NegaScout;
import chess.algorithms.PrincipalVariation;
import chess.algorithms.RandomGen;
import chess.core.ChessGame;
import chess.gui.board.ChessBoard;
import chess.gui.panels.ChessComponent;
import chess.properties.BoardParameters;

/**
 * 
 * @author Arvydas Bancewicz
 *
 */
public class GameDetailsDialog extends JDialog {
  
  private JButton ok;
  private JButton apply;
  private JButton cancel;
  
  private ChessBoard smallBoard;
  private BoardParameters boardParameters;
  private JList colorList;
  private JList setList;
  
  private JTabbedPane tabbedPane;
  
  private ChessGame game;
  
  private JTextField whiteName;
  private JTextField blackName;
  
  private JTextField title;
  
  private JPanel computerTab;
  
  Vector params;
  
  public GameDetailsDialog(JFrame frame) {
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
  JComboBox blackChoice;
  JComboBox whiteChoice;
  
  Object[] algorithmList = {
      new MiniMax(), 
      new AlfaBeta(),
      new NegaScout(), 
      new PrincipalVariation(), 
      new RandomGen()};;
      
  JComboBox algorithm;
  
  JComboBox depthChoice;
  
  private void apply() {
    // Set player names
    game.setWhiteName(whiteName.getText());
    game.setBlackName(blackName.getText());
    
    // Set title
    game.setTitle(title.getText());
    
    // Set user
    if (blackChoice.getSelectedItem().equals("User"))
      game.blackParameters.setUser(true);
    else
      game.blackParameters.setUser(false);
    
    // Set move algorithm
    MoveAlgorithm ma = (MoveAlgorithm) algorithmList[algorithm.getSelectedIndex()];
    ma.setDepth(Integer.parseInt(depthChoice.getSelectedItem()+""));
    game.setAlgorithm(ma);
    
    
    // Update components
    ChessComponent.getInstance().whiteBoardLabel.updateGame(game);
    ChessComponent.getInstance().blackBoardLabel.updateGame(game);
    ChessComponent.getInstance().view.updateThumbnails();
  }

  
  private void createGUI() {
    Container cpane = getContentPane();
    
    cpane.setLayout(new BorderLayout());
    
    game = ChessComponent.getInstance().chessGame;
    
    whiteChoice = new JComboBox();
    whiteChoice.addItem("User");
    whiteChoice.addItem("Computer");
    whiteChoice.setEnabled(false);
    
    blackChoice = new JComboBox();
    blackChoice.addItem("User");
    blackChoice.addItem("Computer");
    
    blackChoice.addItemListener(new ItemListener(){

      public void itemStateChanged(ItemEvent arg0) {
        if (blackChoice.getSelectedIndex()==1) {
          blackName.setEnabled(false);
          blackName.setName(blackName.getText());
          blackName.setText("Computer");
          tabbedPane.setEnabledAt(1,true);
        } else {
          blackName.setEnabled(true);
          blackName.setText(blackName.getName());
          tabbedPane.setEnabledAt(1,false);
        }
        
      }});
    
    // Game
    JPanel gameDet = new JPanel(new SpringLayout());
    gameDet.setBorder(new TitledBorder("Game"));
    
    title = new JTextField(game.getTitle(),10);
    
    // Row 1
    gameDet.add(new JLabel("Title: ", JLabel.TRAILING));
    gameDet.add(title);
    gameDet.add(new JLabel("Date Created: ", JLabel.TRAILING));
    gameDet.add(new JLabel(game.getDateCreated(),10));
    
    // Lay out the panel.
    SpringUtilities.makeCompactGrid(gameDet,
                                    2, 2, //rows, cols
                                    6, 6,        //initX, initY
                                    6, 6);       //xPad, yPad
    
    /// Players 
    
    String[] labels = {"","Type: ","Name: "};
    int numPairs = labels.length;
    
    //Create and populate the panel.
    JPanel players = new JPanel(new SpringLayout());
    players.setBorder(new TitledBorder("Players"));
    
    // Row 1
    players.add(new JLabel(labels[0], JLabel.TRAILING));
    players.add(new JLabel("White"));
    players.add(new JLabel("Black"));
    
    // Row 2
    players.add(new JLabel(labels[1], JLabel.TRAILING));
    players.add(whiteChoice);
    players.add(blackChoice);
    
    // Row 3
    players.add(new JLabel(labels[2], JLabel.TRAILING));
    
    whiteName = new JTextField(game.getWhiteName(),10);
    blackName = new JTextField(game.getBlackName(),10);
    blackName.setName(game.getBlackName());
    players.add(whiteName);
    players.add(blackName);
    
    //Lay out the panel.
    SpringUtilities.makeCompactGrid(players,
                                    numPairs, 3, //rows, cols
                                    6, 6,        //initX, initY
                                    6, 6);       //xPad, yPad
    
    
    JPanel gameDetails = new JPanel();
    gameDetails.setLayout(new BoxLayout(gameDetails, BoxLayout.Y_AXIS));
    gameDetails.add(gameDet);
    gameDetails.add(players);
    
    JPanel gameDetailsTab = new JPanel(new BorderLayout());
    gameDetailsTab.setName("Details");
    gameDetailsTab.add(gameDetails, BorderLayout.NORTH);
    
    //playerTab.add();
    
    // Computer
    computerTab = new JPanel(new BorderLayout());
    computerTab.setName("Computer");
    
    
    depthChoice = new JComboBox();
    for (int i=1;i<=5;i+=2) {
      depthChoice.addItem(i+"");
      if (game.algorithm.getDepth() == i) {
        depthChoice.setSelectedIndex(depthChoice.getItemCount()-1);
      }
    }
    
    algorithm = new JComboBox();
    for (int i=0;i<algorithmList.length;i++) {
      algorithm.addItem(algorithmList[i].toString());
      if (game.algorithm.toString().equals(((MoveAlgorithm)algorithmList[i]).toString())) {
        algorithm.setSelectedIndex(i);
      }
    }
    
    algorithm.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent arg0) {
        MoveAlgorithm ma = (MoveAlgorithm) algorithmList[algorithm.getSelectedIndex()];
        game.setAlgorithm(ma);
      }});
    
    JPanel b = new JPanel(new SpringLayout());
    
    // Row 1
    b.add(new JLabel("Move Algorithm: ", JLabel.TRAILING));
    b.add(algorithm);
    b.add(new JLabel("Depth: ", JLabel.TRAILING));
    b.add(depthChoice);
    
    //Lay out the panel.
    SpringUtilities.makeCompactGrid(b,
                                    2, 2, //rows, cols
                                    6, 6,        //initX, initY
                                    6, 6);       //xPad, yPad
    
    JPanel computerPanel = new JPanel();
    computerPanel.setLayout(new BorderLayout());
    computerPanel.add(b, BorderLayout.NORTH);
    
    JLabel logo = new JLabel();
    logo.setIcon(new ImageIcon(getClass().getResource("Minimax-Search.png")));
    computerPanel.add(logo, BorderLayout.CENTER);
    
    
    computerTab.add(computerPanel, BorderLayout.NORTH);
    
    tabbedPane = new JTabbedPane();
    tabbedPane.add(gameDetailsTab,gameDetailsTab.getName());
    tabbedPane.add(computerTab,computerTab.getName());
    
    
    JPanel bottom = new JPanel();
    JButton ok = new JButton("Ok");
    JButton apply = new JButton("Apply");
    JButton cancel = new JButton("Cancel");
    
    bottom.add(ok);
    bottom.add(apply);
    bottom.add(cancel);
    
    ok.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        apply();
        dispose();
      }});
    
    apply.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        apply();
      }});
    
    cancel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        dispose();
      }
    });
    
    if (game.blackParameters.isUser()) {
      blackChoice.setSelectedIndex(0);
      blackName.setEnabled(true);
      blackName.setText(blackName.getName());
      tabbedPane.setEnabledAt(1,false);
    } else {
      blackChoice.setSelectedIndex(1);
      blackName.setEnabled(false);
      blackName.setName(blackName.getText());
      blackName.setText("Computer");
      tabbedPane.setEnabledAt(1,true);
    }
    
    cpane.add(tabbedPane, BorderLayout.CENTER);
    cpane.add(bottom, BorderLayout.SOUTH);
    
  }

}
