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
 * Created on Feb. 22 2005
 *
 */

package chess.gui.panels;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.MetalIconFactory;

import chess.core.*;
import chess.gui.Chess;
import chess.gui.board.*;

/**
 * ChessComponent is the main class for putting all the different components
 * together. This is where everything comes together within the game layout.
 * 
 * @author Arvydas Bancewicz
 *
 */
public final class ChessComponent extends JPanel {

  // ChessComponent static instance
  private final static ChessComponent cc;
  
  // Board
  public ChessBoard chessBoard;
  public PlayerLabel whiteBoardLabel;
  public PlayerLabel blackBoardLabel;
  
  public ChessGame chessGame; // Current chess game
  public ChessDocuments docu; // List of chess games
  
  // Panels
  public ChessDocumentView view; // Thumbnail views for chess games
  public ChessChat chat;
  public ChessSideBar chessSideBar;
  
  public ButtonModel flipBoard; // Track's the current game's board flip
  public ButtonModel gameDetails;
  
  private JPanel centerBoardPanel; // Contains chessBoard and labels
  private JPanel center;    // Center Panel containing centerBoardPanel
  private JSplitPane gamePane; // Contains the center and side bar
  private JPanel gamePanel; // Contains the game pane
  private JPanel mainPanel; // Contains main pane and bottom panel
  
  // Popup menus
  private JPopupMenu boardPopup; // CenterBoardPanel popup
  private JPopupMenu docuPopup;  // DocumentView popup
  
  // boardPopup menu items
  public JMenuItem modeSwitch;
  public JMenuItem flip;
  public JMenu zoom;
  
  private boolean fullMode = true;
  
  
  private int div; // Horizontal divider location
  
  /**
   * Instantiate the ChessComponent instance
   */
  static {
    cc = new ChessComponent();
  }
  
  /**
   * Get the static instance of ChessComponent
   * @return ChessComponent - this
   */
  public static ChessComponent getInstance() {
    return cc;
  }
  
  /** 
   * Constructor. Build the window
   *
   */
  private ChessComponent() {
    
    super(new BorderLayout());
    chessGame = new ChessGame();
    chessBoard = new ChessBoardMain();
    ;
    //chessBoard.setPreferredDimension(new Dimension(500,500));
    
    gameDetails = new DefaultButtonModel();
    gameDetails.setEnabled(false);
    // flipBoard track's the current game's board flip
    flipBoard = new JToggleButton.ToggleButtonModel();
    flipBoard.setEnabled(false);
    
	//-------------------------------------------------------------------------
	//	Game Panel
	//-------------------------------------------------------------------------
    
    createPopupMenus();
    setUpChessBoard();
    
    // Right side bar
    chessSideBar = new ChessSideBar(chessGame);
    JPanel centerRSide = new JPanel(new BorderLayout());
    centerRSide.add(chessSideBar,BorderLayout.CENTER);
    centerRSide.setBorder(new EtchedBorder());
    
	// Board labels
    whiteBoardLabel = new PlayerLabel(true);
    blackBoardLabel = new PlayerLabel(false);
    centerBoardPanel.add(whiteBoardLabel,BorderLayout.SOUTH);
    centerBoardPanel.add(blackBoardLabel,BorderLayout.NORTH);
    
    // Game pane
    gamePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,center,centerRSide);
    gamePane.setContinuousLayout(true);
    gamePane.setResizeWeight(1);
    
    // Game panel
    gamePanel = new JPanel(new BorderLayout());
    JPanel rOption = new JPanel();
    rOption.setPreferredSize(new Dimension(10,0));
    gamePanel.add(gamePane,BorderLayout.CENTER);
    gamePanel.add(rOption,BorderLayout.EAST);
    
	//-------------------------------------------------------------------------
	//	Bottom Panel
	//-------------------------------------------------------------------------

    final JPanel bottom = new JPanel(new BorderLayout());
    Header bottomHeader = new Header("Chat");
    bottom.add(bottomHeader,BorderLayout.NORTH);
    bottom.setBorder(new EtchedBorder());
    
    chat = new ChessChat();
    chat.setPreferredSize(new Dimension(200,200));
    
    final CardLayout optionSwitch = new CardLayout();
    final JPanel bottomPanel = new JPanel(optionSwitch);
    bottomPanel.add(chat,"chat");
    JPanel fipsPanel = new JPanel();
    bottomPanel.add(fipsPanel,"fips");
    optionSwitch.show(bottomPanel,"chat");
    
    bottom.add(bottomPanel,BorderLayout.CENTER);

    final JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,gamePanel,bottom);
    mainPane.setContinuousLayout(true);
    mainPane.setResizeWeight(1);
    
    FlowLayout layout = new FlowLayout();
    final JToggleButton chatBT = new JToggleButton("Chat");
    chatBT.setBackground(Color.WHITE);
    chatBT.setPreferredSize(new Dimension(70,20));
    chatBT.setSelected(true);
    chatBT.setFocusable(false);
    final JToggleButton fipsBT = new JToggleButton("FIPS");
    fipsBT.setBackground(new Color(228,228,228));
    fipsBT.setPreferredSize(new Dimension(70,20));
    fipsBT.setSelected(false);
    fipsBT.setFocusable(false);
    
    JToolBar bOption = new JToolBar();
    bOption.setFloatable(false);
    bOption.add(chatBT);
    bOption.add(fipsBT);
    
    // Main panel
    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(mainPane,BorderLayout.CENTER);
    mainPanel.add(bOption,BorderLayout.SOUTH);
    
    bottomHeader.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2) {
          if(gamePanel.isVisible()) {
            gamePanel.setVisible(false);
            div=mainPane.getDividerLocation();
            mainPane.setDividerSize(0);
            mainPane.validate();
          } else {
            gamePanel.setVisible(true);
            mainPane.setDividerSize(UIManager.getInt("SplitPane.dividerSize"));
            mainPane.setDividerLocation(div);
            mainPane.validate();
          }
        }
      }
    });
    
    chatBT.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==JToggleButton.ToggleButtonModel.ARMED) { 
          //chatBT.setBorderPainted(true);
          //fipsBT.setBorderPainted(false);
          fipsBT.setSelected(false);
          chatBT.setBackground(Color.WHITE);
          fipsBT.setBackground(new Color(228,228,228));
          
          optionSwitch.show(bottomPanel,"chat");
          
          bottom.setVisible(true);
          //mainPane.setDividerSize(dividerSize);
          mainPane.setDividerSize(UIManager.getInt("SplitPane.dividerSize"));
          mainPane.setDividerLocation(div);
          mainPane.validate();
        } else {
          
          if(!gamePanel.isVisible()) {
            gamePanel.setVisible(true);
          	gamePanel.validate();
          } else {
            div=mainPane.getDividerLocation();
          }
          chatBT.setBackground(new Color(228,228,228));
          
          //chatBT.setBorderPainted(false);
          bottom.setVisible(false);
          mainPane.validate();
          mainPane.setDividerSize(0);
        }
        
      }});
    
    fipsBT.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange()==JToggleButton.ToggleButtonModel.ARMED) { 
          //fipsBT.setBorderPainted(true);
          //chatBT.setBorderPainted(false);
          chatBT.setSelected(false);
          fipsBT.setBackground(Color.WHITE);
          chatBT.setBackground(new Color(228,228,228));
          
          optionSwitch.show(bottomPanel,"fips");
          bottom.setVisible(true);
          mainPane.setDividerSize(UIManager.getInt("SplitPane.dividerSize"));
          mainPane.setDividerLocation(div);
          mainPane.validate();
        } else {
          if(!gamePanel.isVisible()) {
            gamePanel.setVisible(true);
          	gamePanel.validate();
          } else {
            div=mainPane.getDividerLocation();
          }
          fipsBT.setBackground(new Color(228,228,228));
          
          //fipsBT.setBorderPainted(false);
          bottom.setVisible(false);
          mainPane.validate();
          mainPane.setDividerSize(0);
          System.out.println("DeArmedFIPS");
        }
        
      }});
    
	//-------------------------------------------------------------------------
	//	Left
	//-------------------------------------------------------------------------
    
    JPanel lSide = new JPanel(new BorderLayout());
    lSide.setBorder(new EtchedBorder());
    lSide.setMinimumSize(new Dimension(200,0));
    view = new ChessDocumentView();
    
    lSide.add(view,BorderLayout.CENTER);
    lSide.addMouseListener(new DocuPopupListener());
    Header header = new Header("Games");
    
    docuPopup = new JPopupMenu("filter");
    JMenuItem minimizeAll = new JMenuItem("Minimize All");
    JMenuItem maximizeAll = new JMenuItem("Maximize All");
    docuPopup.add(minimizeAll);
    docuPopup.add(maximizeAll);
    
    minimizeAll.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent arg0) {
        ChessComponent.getInstance().view.minimizeAll(true);
        
      }});
    
    maximizeAll.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent arg0) {
        ChessComponent.getInstance().view.minimizeAll(false);
        
      }});
    
    header.addMouseListener(new DocuPopupListener());

    lSide.add(header,BorderLayout.NORTH);
    
    JSplitPane wholePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,lSide,mainPanel);
    wholePane.setContinuousLayout(true);
    wholePane.setResizeWeight(0);
    
    /*
    JPanel lOption = new JPanel(new BorderLayout());
    
    JToolBar o = new JToolBar(JToolBar.VERTICAL);
    FlatButton vb = new FlatButton("Documents");
    
    FlatButton vb2 = new FlatButton("OpenGL");
    vb2.setPreferredSize(new Dimension(200,300));

    o.setFloatable(false);
    o.setSize(200,80);
    o.setPreferredSize(new Dimension(30,400));
    o.add(vb);
    o.add(vb2);
    lOption.add(vb,BorderLayout.NORTH);
    
    lOption.addMouseListener(new DocuPopupListener());
    */
    JPanel wholePanel = new JPanel(new BorderLayout());
    wholePanel.add(wholePane,BorderLayout.CENTER);
    wholePanel.add(new JPanel(),BorderLayout.WEST);
    
    JPanel lOption = new JPanel();
    lOption.setPreferredSize(new Dimension(10,0));
    wholePanel.add(lOption,BorderLayout.WEST);
    
	//-------------------------------------------------------------------------
	//	Vertical Spacing
	//-------------------------------------------------------------------------
    
    JToolBar bar = new JToolBar(JToolBar.HORIZONTAL);
    JButton bu = new JButton();
    bu.setIcon(new ImageIcon(getClass().getResource("game.png")));
    bu.setText("New Game ");
    bar.add(bu);
    
    JPanel topSpace = new JPanel(new BorderLayout());
    topSpace.setPreferredSize(new Dimension(0,20));
    JPanel bottomSpace = new JPanel();
    bottomSpace.setPreferredSize(new Dimension(0,20));
    bottomSpace.setBorder(new EtchedBorder());
    
    add(wholePanel,BorderLayout.CENTER);
    //add(topSpace,BorderLayout.NORTH);
    //add(bottomSpace,BorderLayout.SOUTH);
    
    /////////////////////////////////////////////////////////////////
    JToolBar bak = new JToolBar(JToolBar.HORIZONTAL);
    bak.setFloatable(false);
    JButton gameBT = new JButton(" Normal ");
    gameBT.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        Chess.getInstance().createNewGame();
      }});
    gameBT.setIcon(new ImageIcon(getClass().getResource("normal2.png")));
    JButton lanBT = new JButton(" Lan ");
    lanBT.setIcon(new ImageIcon(getClass().getResource("lan2.png")));
    JButton worldBT = new JButton(" Online ");
    worldBT.setIcon(new ImageIcon(getClass().getResource("world2.png")));
    bak.add(gameBT);
    bak.add(lanBT);
    bak.add(worldBT);
    gameBT.setEnabled(true);
    lanBT.setEnabled(true);
    worldBT.setEnabled(true);
    add(bak,BorderLayout.NORTH);
    
    //gameBT.setEnabled(false);
    lanBT.setEnabled(false);
    worldBT.setEnabled(false);
    /////////////////////////////////////////////////////////////////
    
    validate();
  }
  
  /**
   * Create Popup menus boardPopup and docuPopup
   *
   */
  private void createPopupMenus() {
    
    // CenterBoardPanel popup menu
    //    _  _ _ _ _ _
    //   | Flip Board |
    //   | ---------- |
    //   | Mode Switch|
    //   |    Zoom    |
    //   |_ _ _ _ _ _ |
    
    boardPopup = new JPopupMenu("board options");
    
    // Flip Board
    boardPopup.add(flip = new JCheckBoxMenuItem("Flip Board"));
    flip.setModel(flipBoard); // Set it's model to flipBoard
    flip.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        updateForBoardFlip(); // Flip the board
      }});
    
    // Separator
    boardPopup.addSeparator();
    
    // Mode Switch
    boardPopup.add(modeSwitch = new JMenuItem("Mode Switch"));
    modeSwitch.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        fullMode = !fullMode;
        setMode(fullMode); // Switch the mode to full view
      }});
    
    // Zoom
    boardPopup.add(zoom = new JMenu("Zoom"));
    for (int i=75;i<=125;i+=25) { // Zoom 75%, 100%, 125%
      final double mul = i/100.0; // 0.75, 1.00, 1.25
      // Create the menu item
      JMenuItem zoomItem = new JMenuItem(i+"%");
      zoom.add(zoomItem);
      zoomItem.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
          // Change the chessBoard dimension
          Dimension def = chessBoard.getDefaultSize();
          Dimension preferred = new Dimension((int)(mul*def.width),(int)(mul*def.height));
          chessBoard.setPreferredDimension(preferred); 
          centerBoardPanel.revalidate();
        }});
    }
  }
  
  /**
   * Create the center board panel containing the chess board
   *
   */
  void setUpChessBoard() {
    
    flip.setEnabled(true);
    modeSwitch.setEnabled(true);
    modeSwitch.setText("Switch to Small Mode");
    Icon icon = MetalIconFactory.getFileChooserUpFolderIcon();
    modeSwitch.setIcon(icon);
    
    zoom.setEnabled(true);
    
    centerBoardPanel = new JPanel(new BorderLayout());
    centerBoardPanel.add(chessBoard,BorderLayout.CENTER);
    //JButton b = new JButton("Chess Board");
    //b.setEnabled(false);
    //centerBoardPanel.add(b,BorderLayout.NORTH);
    
    // Center Panel
    center = new JPanel();
    center.setBorder(new EtchedBorder());
    center.add(centerBoardPanel);
    
    chessBoard.addMouseListener(new BoardPopupListener());
    
    if (gamePane!=null) {
      centerBoardPanel.add(whiteBoardLabel,BorderLayout.SOUTH);
      centerBoardPanel.add(blackBoardLabel,BorderLayout.NORTH);
      gamePane.setLeftComponent(center);
      
    }
    
  }
  
  /**
   * Set the window mode to fullMode or small mode
   * @param fullMode
   */
  private void setMode(boolean fullMode) {
    this.fullMode = fullMode;
    Chess.getInstance().setMode(fullMode);
    if (!fullMode)
      createSmallMode();
    else
      setUpChessBoard();
  }

  private ChessSmallMode smallView;
  /**
   * Create the small mode window
   *
   */
  private void createSmallMode() {
    
    smallView = new ChessSmallMode();
  }
  
  /**
   * Change the current game on the chess board
   * @param game the game to change to
   */
  public void changeGame(ChessGame game) {
    
    chessBoard.changeGame(game);
    
    this.chessGame = game;
    
    if(!flipBoard.isEnabled())
      flipBoard.setEnabled(true);
    
    if(!gameDetails.isEnabled())
      gameDetails.setEnabled(true);
    
    if(game==null) {
      flipBoard.setEnabled(false);
      gameDetails.setEnabled(false);
      whiteBoardLabel.updateGame(null);
      blackBoardLabel.updateGame(null);
      System.err.println("YSSS");
    } else {
      flipBoard.setSelected(game.isBoardFlipped());
      whiteBoardLabel.updateGame(game);
      blackBoardLabel.updateGame(game);
      chessSideBar.refresh(game);
    }
    
    updateForBoardFlip();
    chessSideBar.piecesPanel.refreshPieceCount(
        chessGame.board.pieceCounts);
  }
  
  /**
   *  Set up the chess board if the board is flipped 
   * 
   */
  public void updateForBoardFlip() {
    
    boolean flip = flipBoard.isSelected();
    chessBoard.setFlipBoard(flip);
    view.refreshDocuments();
    
    if (fullMode) {
      // Reposition(flip) the player labels
      centerBoardPanel.remove(whiteBoardLabel);
      centerBoardPanel.remove(blackBoardLabel);
      if (flip) {
        centerBoardPanel.add(whiteBoardLabel, BorderLayout.NORTH);
        centerBoardPanel.add(blackBoardLabel, BorderLayout.SOUTH);
      } else {
        centerBoardPanel.add(whiteBoardLabel, BorderLayout.SOUTH);
        centerBoardPanel.add(blackBoardLabel, BorderLayout.NORTH);
      }
      centerBoardPanel.revalidate();
    } else if (smallView != null) {
      smallView.updateForBoardFlip();
    }
  }
  
  /**
   * Header is a simple panel containing a label
   * @author Arvydas Bancewicz
   *
   */
  class Header extends JPanel {
    
    public Header(String label) {
      
      setLayout(new BorderLayout());
      //setBackground(Color.LIGHT_GRAY);
      setBackground(new Color(150,150,150));
      setPreferredSize(new Dimension(0,20));
      setBorder(new LineBorder(Color.GRAY));
      JLabel title = new JLabel("  "+label);
      title.setForeground(Color.WHITE);
      add(title,BorderLayout.CENTER);
      
      /*
      JButton exit = new JButton();
      Icon icon;
      icon = new ImageIcon(getClass().getResource("expand.png"));
      exit.setIcon(icon);
      exit.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
      exit.setContentAreaFilled(false);
      add(exit,BorderLayout.EAST);
      */
      /*
      JButton minimize = new JButton();
      //minimize.setIcon(MetalIconFactory.getInternalFrameMinimizeIcon(0));
      Icon icon;
      icon = new ImageIcon(getClass().getResource("left.png"));
      //icon = MetalIconFactory.getInternalFrameDefaultMenuIcon();
      //icon = MetalIconFactory.getFileChooserUpFolderIcon();
      minimize.setIcon(icon);
      minimize.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
      minimize.setContentAreaFilled(false);
      minimize.setBackground(Color.WHITE);
      
      add(minimize,BorderLayout.EAST);
      */

      /*
      Icon image = new ImageIcon(getClass().getResource("docu.png"));
      JButton e = new JButton();
      e.setIcon(image);
      e.setContentAreaFilled(false);
      add(e,BorderLayout.EAST);
      */
    }
  }
  
  /**
   * Board popup listener
   * @author Arvydas Bancewicz
   *
   */
  class BoardPopupListener extends MouseAdapter {
    
    public void mousePressed(MouseEvent e) {
      maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
          boardPopup.show(e.getComponent(),
                     e.getX(), e.getY());
      }
    }
  }
  
  /**
   * Document popup listener
   * @author Arvydas Bancewicz
   *
   */
  class DocuPopupListener extends MouseAdapter {
    
    public void mousePressed(MouseEvent e) {
      maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
          docuPopup.show(e.getComponent(),
                     e.getX(), e.getY());
      }
    }
  }
  
}
