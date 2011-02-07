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
 * Created on Undisclosed
 *
 */

package chess.gui.panels;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;

import chess.core.ChessGame;
import chess.core.CustomCellRenderer;

public class ChessSideBar extends JPanel {
  
  private Thread runner;
  public Button restart;
  
  private Info infoPanel;
  private Notation notationPanel;
  
  
  public PiecesPanel piecesPanel;
  
  private JButton menu;
  private JButton options;
  
  JTabbedPane tabbedPane;
  
  //Uncomment the following line to use scrolling tabs.
  //tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
  
  /* Initialize. */
  public void init(){}
  
  public ChessGame game;
  
  /* Constructor sets preferred size for this panel. */
  public ChessSideBar(ChessGame game) {
    super();
    
    this.game = game;

    //CardLayout l = new CardLayout();
    
    //infoPanel = new Info();
    notationPanel = new Notation();
    piecesPanel = new PiecesPanel();
    tabbedPane = new JTabbedPane();
    //tabbedPane.add(infoPanel.toString(),infoPanel);
    tabbedPane.add(notationPanel.toString(),notationPanel);
    tabbedPane.add(piecesPanel.toString(),piecesPanel);
    
    JPanel bottom = new JPanel(new GridLayout(1,3));
    bottom.setBorder(new LineBorder(Color.BLACK));
    options = new JButton("options");
    menu = new JButton("menu");
    menu.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        
      }
    });

    JPanel bottomBar = new JPanel();
    bottomBar.setPreferredSize(new Dimension(0,25));
    
    bottom.add(options);
    bottom.add(menu);
    bottom.add(bottomBar);
    
    //Container cp = getContentPane();
    BorderLayout bl = new BorderLayout();
    bl.setVgap(5);
    setLayout(bl);
    //setBackground(cc.colors.background);
    add(tabbedPane,BorderLayout.CENTER);
    //add(bottom,BorderLayout.SOUTH);
    
    //add(new ClockPanel(),BorderLayout.SOUTH);

  }
  
  public void refresh(ChessGame game) {
    //infoPanel.refreshGame(game);
    
    this.game = game;
    notationPanel.moves.setModel(game.clm);
    TableColumnModel colModel = notationPanel.moves.getColumnModel();
    colModel.getColumn(0).setMaxWidth(30);
    
    piecesPanel.refreshPieceCount(game.board.pieceCounts);
  }
  
  public void paint(Graphics g) {
    super.paint(g);
    notationPanel.repaint();
  }
  
  class Info extends JPanel {
    ComputerInfo player2Panel;
    private JProgressBar progressWhite;
    private JProgressBar progressBlack;
    private BoundedRangeModel progress;
    private JButton stop;
    
    public void refreshGame(ChessGame g) {
      System.out.println("REFERESH GAME !!!!!!!!");
      if (game == g) {
        System.out.println("aaaaaaaaaaaREFERESH GAME !!!!!!!!");
        progress = game.board.progress;
        progressBlack.setModel(progress);
        
        progress.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            System.out.println("aaaaaaaaaaaREFERESH GAME  CHANGE!!!!!!!!");
            progressBlack.setString((int)((double)progress.getValue()/progress.getMaximum()*100)+"%");
            if(progress.getValue()==0) {
              progressBlack.setIndeterminate(true);
              stop.setEnabled(false);
            } else {
              progressBlack.setIndeterminate(false);
              stop.setEnabled(true);
            }
            if(progress.getValue()==progress.getMaximum()) {
                progressWhite.setString("Waiting...");
                progressBlack.setString("Idle...");
                stop.setEnabled(false);
            }
          }
        });
      }
      /*
      progress = game.board.progress;
      progressBlack.setModel(progress);
      progress.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          System.out.println("State changed!");
          progressBlack.setString((int)((double)progress.getValue()/progress.getMaximum()*100)+"%");
          if(progress.getValue()==0) {
            progressBlack.setIndeterminate(true);
            stop.setEnabled(false);
          } else {
            progressBlack.setIndeterminate(false);
            stop.setEnabled(true);
          }
          if(progress.getValue()==progress.getMaximum()) {
              progressWhite.setString("Waiting...");
              progressBlack.setString("Idle...");
              stop.setEnabled(false);
          }
        }
      });
      */
    }
    
    public Info() {
      super(new BorderLayout());
      
      stop = new JButton("Stop");
      stop.setEnabled(false);
      
      progressWhite = new JProgressBar();
      progressBlack = new JProgressBar();
      progressWhite.setString("idle");
      progressBlack.setString("idle");
      progressWhite.setStringPainted(true);
      progressBlack.setStringPainted(true);
      
      progress = new DefaultBoundedRangeModel();
      progress.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          progressBlack.setString((int)((double)progress.getValue()/progress.getMaximum()*100)+"%");
          if(progress.getValue()==0) {
            progressBlack.setIndeterminate(true);
            stop.setEnabled(false);
          } else {
            progressBlack.setIndeterminate(false);
            stop.setEnabled(true);
          }
          if(progress.getValue()==progress.getMaximum()) {
              progressWhite.setString("Waiting...");
              progressBlack.setString("Idle...");
              stop.setEnabled(false);
          }
        }
      });
      /*
      progress = ChessComponent.getInstance().chessGame.board.progress;
      progressBlack.setModel(progress);
      progress.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          progressBlack.setString((int)((double)progress.getValue()/progress.getMaximum()*100)+"%");
          if(progress.getValue()==0) {
            progressBlack.setIndeterminate(true);
            stop.setEnabled(false);
          } else {
            progressBlack.setIndeterminate(false);
            stop.setEnabled(true);
          }
          if(progress.getValue()==progress.getMaximum()) {
              progressWhite.setString("Waiting...");
              progressBlack.setString("Idle...");
              stop.setEnabled(false);
          }
        }
      });
      */
      stop.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //ChessComponent.getInstance().moveAlgorithm.stopped = true;
        }
      });
      
      //UserInfo player1Panel = new UserInfo(true);
      player2Panel = new ComputerInfo(false);
      
      JPanel panel = new JPanel(new GridLayout(2,1));
      //panel.add(player1Panel);
      panel.add(player2Panel);

      add(panel);
    }
    
    class UserInfo extends JPanel {
      JLabel label;
      JTextField timer;
      JLabel box;
      JProgressBar progress;
      public UserInfo(boolean player) {
        super(new BorderLayout());
        if(player)
          progress = progressWhite;
        else
          progress = progressBlack;
        label = new JLabel("Player 1");
        timer = new JTextField("Timer: ");
        timer.setEditable(false);
        box = new JLabel();
        box.setBorder(new LineBorder(Color.BLACK));
        JPanel top = new JPanel(new GridLayout(3,1));
        top.add(label);
        top.add(timer);
        top.add(progressWhite);
        add(top,BorderLayout.NORTH);
        add(box,BorderLayout.CENTER);
      }
    }
    
    class ComputerInfo extends JPanel {
      JLabel label;
      JTextField timer;
      JTextArea box;
      JProgressBar progress;
      JScrollPane scrollPane;
      JPanel top;
      public void refresh() {
        this.removeAll();
        box = ChessComponent.getInstance().chessGame.board.think;
        progress = progressBlack;
        top = new JPanel(new GridLayout(4,1));
        top.add(label);
        top.add(timer);
        top.add(progress);
        top.add(stop);
        add(top,BorderLayout.NORTH);
        
        scrollPane = new JScrollPane(box,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane,BorderLayout.CENTER);
      }
      public ComputerInfo(boolean player) {
        super(new BorderLayout());
        //if(player)
        //  progress = progressWhite;
        //else
        //  progress = progressBlack;
        label = new JLabel("Player 2");
        timer = new JTextField("Timer: ");
        timer.setEditable(false);
        box = new JTextArea(3,40);
        //box = ChessComponent.getInstance().chessGame.think;
        box.setBorder(new LineBorder(Color.BLACK));
        box.setEditable(false);
        box.setLineWrap(true);
        box.setWrapStyleWord(true);
        scrollPane = new JScrollPane(box,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        top = new JPanel(new GridLayout(4,1));
        top.add(label);
        top.add(timer);
        //top.add(progress);
        top.add(stop);
        add(top,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
      }
    }
    
    public String toString() {
      return "  Info  ";
    }
    
  }
  
  class Notation extends JPanel {
    
    //public JList moves;
    public JTable moves;
    JButton analyze;
    JButton next;
    JButton previous;
    JButton first;
    JButton last;
    JButton play;
    public void paint(Graphics g) {
      super.paint(g);
      System.out.println("VALIDATE");
      movesPanel.repaint();
      moves.repaint();
      validate();
      revalidate();
      listScroller.repaint();
    }
    private JScrollPane listScroller;
    JPanel movesPanel;
    public Notation() {
      
      super(new BorderLayout());
      
      //Font f = new Font("Monospaced", Font.PLAIN, 6);
      
      // Create a list and put it in a scroll pane.
      //ChessGame g = ChessComponent.getInstance().chessGame;
      //moves = new JList(ChessComponent.getInstance().);
      
      //moves = new JList(game.clm);
      moves = new JTable(game.clm);
      moves.setShowGrid(false);
      //moves.setShowHorizontalLines(true);
      moves.setDragEnabled(false);    
      moves.setColumnSelectionAllowed(true);
      moves.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      moves.setDefaultRenderer(Object.class, new CustomCellRenderer());
      
      moves.removeMouseListener(moves.getMouseListeners()[2]);
      moves.removeMouseMotionListener(moves.getMouseMotionListeners()[2]);

      TableColumnModel colModel = moves.getColumnModel();
      colModel.getColumn(0).setMaxWidth(30);
      
      moves.addMouseMotionListener(new MouseMotionAdapter(){

        public void mouseDragged(MouseEvent e) {
    		int row = moves.rowAtPoint(e.getPoint());
      		int col = moves.columnAtPoint(e.getPoint());
      				
      		if (col==0 || (moves.getValueAt(row,col) == null) ||
      		     moves.getValueAt(row,col).equals("")) {
      			return;
      		} else {
      		  moves.removeEditor();
      		  moves.setRowSelectionInterval(row,row);
      		  moves.getColumnModel().getSelectionModel().setSelectionInterval(col,col);					
      		}
          
        }
      });
      
      moves.addMouseListener(new MouseAdapter() {
      	public void mouseReleased(MouseEvent e) {
      		int row = moves.rowAtPoint(e.getPoint());
      		int col = moves.columnAtPoint(e.getPoint());
      				
      		if (col==0 || (moves.getValueAt(row,col) == null) ||
      		     moves.getValueAt(row,col).equals("")) {
      			return;
      		} else {
      		  moves.removeEditor();
      		  moves.setRowSelectionInterval(row,row);
      		  moves.getColumnModel().getSelectionModel().setSelectionInterval(col,col);					
      		}
      	}
      });
      movesPanel = new JPanel(new BorderLayout());
      movesPanel.add(moves, BorderLayout.CENTER);
      listScroller = new JScrollPane(
          movesPanel,
          JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      
      add(listScroller,BorderLayout.CENTER);
      //add(moves,BorderLayout.CENTER);
      
      analyze = new JButton("Analyze");
      play = new JButton("Play animation");
      first = new JButton("<<"); 
      //first.setFont(f);
      previous = new JButton("<"); 
      //previous.setFont(f);
      next = new JButton(">"); 
      //next.setFont(f);
      last = new JButton(">>"); 
      //last.setFont(f);
      
      play.setEnabled(false);
      first.setEnabled(false);
      previous.setEnabled(false);
      next.setEnabled(false);
      last.setEnabled(false);
      
      JPanel tools = new JPanel(new GridLayout(1,4));
      tools.add(first);
      tools.add(previous);
      tools.add(next);
      tools.add(last);
      
      JPanel bottom = new JPanel(new GridLayout(4,1));
      //bottom.add(analyze);
      //bottom.add(play);
      //bottom.add(tools);

      
      add(bottom,BorderLayout.SOUTH);
    }
    
    public String toString() {
      return "Notation";
    }
  }
  
}
