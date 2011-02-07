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
 * Created on Mar. 2 2005
 *
 */

package chess.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import chess.core.ChessDocuments;
import chess.core.ChessFile;
import chess.core.ChessFileFilter;
import chess.core.ChessGame;
import chess.gui.panels.ChessComponent;
import chess.gui.window.*;

/**
 * Create a window, which has a content pane where all components should be placed
 * @author Arvydas Bancewicz
 */
public final class Chess extends JFrame implements ActionListener {
  
  private String version = "1.0.0";
  
  public boolean fullMode = true;
  
  private final static Chess chess;
  
  // MenuBar
  private JMenuBar menuBar;
  // Menu - File
  private JMenu file;
  private JMenuItem newGame;
  private JMenuItem open;
  private JMenuItem save;
  private JMenuItem saveAs;
  private JMenuItem exit;
  // Menu - Settings
  private JMenu settings;
  private JMenuItem preferences;
  private JMenuItem gameDetails;
  // Menu - Board
  private JMenu board;
  private JMenuItem flipBoard;
  private JMenuItem chessSet;
  // Menu - Help
  private JMenu help;
  private JMenuItem manual;
  private JMenuItem about;
  
  SplashScreen sd;
  
  static {
    chess = new Chess();
  }
  
  public static Chess getInstance() {
    return chess;
  }
  
  public void setMode(boolean fullMode) {
    this.fullMode = fullMode;
    this.setVisible(fullMode);
  }

  /* Constructor builds GUI */
  public Chess() {
    
    setSize(1000,900);
    setTitle("Chess v"+version);
    
    
    // Setup the UI
    setupUI();
    
    try {
      
      // Build the Window
      init();
      
      setLocationRelativeTo(null); // Position window to center of screen
      setExtendedState(JFrame.MAXIMIZED_BOTH); 
      setVisible(true);
      
    } catch (Exception e) {
      new ErrorMessage(null,e.toString());
    }
    
  }
  
  /* Add components to the content pane */
  private void init() {
    
    // Instance of ChessComponent
    ChessComponent cc = ChessComponent.getInstance();
    
    // Add ChessComponent to the window
    Container cp = getContentPane();
    cp.add(ChessComponent.getInstance());
    
    // Set up the Window.
    createGUI();
    
    // Make sure the flipBoard button corresponds with the game
    flipBoard.setModel(cc.flipBoard);
    gameDetails.setModel(cc.gameDetails);
    
    // Close on Window exit
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Detect if the window is closing
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent arg0) {
        //System.out.println("Preferences Saved.");
        //ChessPreferences.saveBoard(cc.chessGame.board);
        //ChessPreferences.saveTurn(cc.chessGame.turn);
        //ChessPreferences.saveWhiteKing(cc.chessGame.whiteKing);
        //ChessPreferences.saveBlackKing(cc.chessGame.blackKing);
      }
    });
    
  }
  
  public static void main(String args[]) {
    // Set up the Look And Feel
    try {
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  	  //UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
      //UIManager.setLookAndFeel("com.shfarr.ui.plaf.fh.FhLookAndFeel");
      //UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel")
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // Setup the UI
    setupUI();
    
    // Start Chess
    //new Chess();
    
    getInstance();
  }
  
  /* Setup the UI. */
  private static void setupUI() {
    
    Color panelBackground = new Color(235,233,237);
    Color selectionBackground = new Color(53,121,207);
    
    UIManager.put("List.selectionBackground", selectionBackground);
    UIManager.put("Table.selectionBackground", selectionBackground);
    
    UIManager.put("MenuItem.selectionBackground", selectionBackground);//new Color(200,200,180));
    
    UIManager.put("Menu.selectionBackground", selectionBackground);//new Color(180,180,180));
    UIManager.put("CheckBoxMenuItem.selectionBackground", selectionBackground);//new Color(200,200,180));
    UIManager.put("Panel.background", panelBackground);//new Color(252,241,203));//new Color(220,220,220));

    UIManager.put("MenuBar.background", panelBackground);//new Color(210,210,210));
    UIManager.put("SplitPane.background", panelBackground);//new Color(220,220,220));
    
    UIManager.put("SplitPaneDivider.border", new EmptyBorder(0,0,0,0));
    UIManager.put("SplitPane.border", new EmptyBorder(0,0,0,0));
  }
  
  /* Build the menu bar */
  public void createGUI() {
    
    // Set menuBar as the menu bar for the Window
    setJMenuBar(menuBar = new JMenuBar());
    
    menuBar.setBorder(new EtchedBorder());
    menuBar.setPreferredSize(new Dimension(0,30));
    //MenuBarUI ui = new BasicMenuBarUI();
    //menuBar.setUI(ui);
    
    // Menu - File
    menuBar.add(file = new JMenu("File"));
    
    file.add(newGame = new JMenuItem("New Game"));
    //newGame.setIcon(BasicIconFactory.);
    newGame.addActionListener(this);
    
    file.addSeparator();
    file.add(open    = new JMenuItem("Open"));
    open.addActionListener(this);
    
    file.add(save    = new JMenuItem("Save"));
    save.addActionListener(this);
    save.setEnabled(false);
    
    file.add(saveAs  = new JMenuItem("Save As.."));
    saveAs.addActionListener(this);
    
    file.addSeparator();
    file.add(exit    = new JMenuItem("Exit"));
    exit.addActionListener(this);
    
    // Menu - Settings
    menuBar.add(settings     = new JMenu("Settings"));
    
    settings.add(preferences = new JMenuItem("Preferences"));
    preferences.addActionListener(this);
    
    settings.addSeparator();
    
    settings.add(gameDetails = new JMenuItem("Game Details"));
    gameDetails.addActionListener(this);
    
    // Menu - Board
    menuBar.add(board   = new JMenu("Board"));
    
    board.add(flipBoard = new JCheckBoxMenuItem("Flip Board"));
    flipBoard.addActionListener(this);
    board.addSeparator();
    
    board.add(chessSet  = new JMenuItem("Change Chess Set"));
    chessSet.addActionListener(this);
    
    // Menu - Help
    menuBar.add(help = new JMenu("Help"));
    
    help.add(manual  = new JMenuItem("Manual"));
    manual.addActionListener(this);
    manual.setEnabled(false);
    
    help.addSeparator();
    help.add(about  = new JMenuItem("About"));
    about.addActionListener(this);
    
  }
  
  public void createNewGame() {
    ChessGame game = new ChessGame();
    ChessDocuments.getInstance().add(game);
    ChessComponent.getInstance().view.addToList();
    ChessComponent.getInstance().chessSideBar.piecesPanel.refreshPieceCount(
        game.board.pieceCounts);
    //int index = ChessDocuments.getInstance().getSize()-1;
  }
  
  public void actionPerformed(ActionEvent e) {
    
    // MenuItem - New Game
    if (e.getSource()==newGame) {
      createNewGame();
      
    // MenuItem - Open
    } else if (e.getSource()==open) {
      JFileChooser fc = new JFileChooser();
      fc.setFileFilter(new ChessFileFilter());
      //fc.setFileHidingEnabled(true);
      fc.setBackground(Color.RED);
      fc.setAcceptAllFileFilterUsed(false); // Only chess files (*.pgn)
      int returnVal = fc.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        if (fc.accept(fc.getSelectedFile())) {
          File file = fc.getSelectedFile();
          //This is where a real application would open the file.
          System.out.println("Opening: " + file.getName() + "."+returnVal);
          
          ChessFile.getInstance().openFile(file.getPath());
        }
          
      } else {
          //log.append("Open command cancelled by user." + newline);
      }
      //log.setCaretPosition(log.getDocument().getLength());
      
    // MenuItem - Save
    } else if(e.getSource()==save) {
      
    // MenuItem - Save As
    } else if(e.getSource()==saveAs) {
      
      JFileChooser fc = new JFileChooser();
      
      fc.setFileFilter(new ChessFileFilter());
      
      fc.setDragEnabled(true);
      fc.setAcceptAllFileFilterUsed(false); // Only chess files (*.pgn)
      int returnVal = fc.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        
          File file = fc.getSelectedFile();
          
          if(!fc.accept(file))
           file = new File(file.getParent()+"\\"+file.getName()+".ch");
          ChessComponent.getInstance().chessGame.setFlipBoard(false);
          flipBoard.setSelected(false);
          ChessComponent.getInstance().updateForBoardFlip();
          ChessFile.getInstance().saveFile(file.getPath());
          //saveFile(file);
          
      } else {
          //log.append("Save command cancelled by user." + newline);
      }
      //log.setCaretPosition(log.getDocument().getLength());
      
    // MenuItem - Exit
    } else if(e.getSource()==exit) {
      ExitDialog dialog = new ExitDialog(this);
      dialog.setVisible(true);
      
    // MenuItem - Preferences
    } else if(e.getSource()==preferences) {
      PreferencesDialog m = new PreferencesDialog(this);
      m.setVisible(true);
      
    } else if(e.getSource()==gameDetails) {
      GameDetailsDialog d = new GameDetailsDialog(this);
      d.setVisible(true);
      ChessComponent.getInstance().chessSideBar.refresh(ChessComponent.getInstance().chessGame);
      
    // MenuItem - Flip Board
    } else if(e.getSource()==flipBoard) {
      ChessComponent.getInstance().updateForBoardFlip();
      //ChessComponent.getInstance().chessBoard.setFlipBoard(flipBoard.isSelected());
      //ChessComponent.getInstance().view.refreshDocuments();
      
    // MenuItem - Chess Set
    } else if(e.getSource()==chessSet) {
      //ChessSetupDialog dialog = new ChessSetupDialog(this);
      //dialog.setVisible(true);
      ChessSetDialog dialog = new ChessSetDialog(this);
      dialog.setVisible(true);
      
    // MenuItem - About
    } else if(e.getSource()==about) {
      AboutDialog dialog = new AboutDialog(this);
      dialog.setVisible(true);
    }
  }

}