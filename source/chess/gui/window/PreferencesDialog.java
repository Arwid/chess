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
 * Created on Mar 16, 2005
 *
 */

package chess.gui.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

import chess.core.ComponentList;
import chess.gui.board.ChessBoard;
import chess.gui.board.ChessBoardSample;
import chess.gui.panels.ChessComponent;
import chess.properties.BoardParameters;

public class PreferencesDialog extends JDialog {
  private JList list;
  private JPanel centerPanel;
  private CardLayout cardLayout;
  
  //public void buildGeneralPane
  public PreferencesDialog(JFrame frame) {
    super(frame,"Menu",true);
    
    ((JComponent) getContentPane()).setPreferredSize(new Dimension(600,400));
    //setSize(new Dimension(600,400));
    
    
    buildGUI();
    
    //ResumeDialog resume = new ResumeDialog();
    //panel.add("resume",resume);
    //cardLayout.show(panel,"resume");
    //add(panel);
    pack();
    
    this.setLocationRelativeTo(null);
    this.setModal(true);
  }
  
  private void updateComponentTree() {
    SwingUtilities.updateComponentTreeUI(this);
    SwingUtilities.updateComponentTreeUI(ChessComponent.getInstance());
  }
  
  JPanel generalTab;  
  JPanel boardTab;
  
  private void addGeneralTab() {
	//-------------------------------------------------------------------------------
	//	General Tab
	//-------------------------------------------------------------------------------
    generalTab = new JPanel(new BorderLayout());
    generalTab.setName("General");
    generalTab.setBorder(new TitledBorder(generalTab.getName()));
    
    String disc1 = "Welcome to Chess preference setup. You can browse this setup window";
    String disc2 = "by selecting a category on the left";
    JPanel discription = new JPanel(new FlowLayout(FlowLayout.LEFT));
    discription.add(new JLabel(disc1));
    discription.add(new JLabel(disc2));
    
    final Choice  lookAndFeelList = new Choice();
    JLabel lookAndFeel = new JLabel("Look & Feel");
    
    JPanel config = new JPanel(new FlowLayout(FlowLayout.LEFT));
    config.add(lookAndFeel);
    config.add(lookAndFeelList);
    
    JPanel center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    center.add(discription);
    center.add(config);
    
    final Vector lookAndFeels = new Vector();
    lookAndFeels.add(new javax.swing.plaf.metal.MetalLookAndFeel());
    lookAndFeels.add(new com.sun.java.swing.plaf.motif.MotifLookAndFeel());
    lookAndFeels.add(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
    
    for (int i=0;i<lookAndFeels.size();i++) {
      lookAndFeelList.add(((LookAndFeel)lookAndFeels.elementAt(i)).getID());
    }
    
    lookAndFeelList.addItemListener(new ItemListener(){

      public void itemStateChanged(ItemEvent e) {
        System.out.println("ITEM CHANGE");
        int choice = lookAndFeelList.getSelectedIndex();
        try {
          UIManager.setLookAndFeel((LookAndFeel) lookAndFeels.elementAt(choice));
          updateComponentTree();
        } catch (UnsupportedLookAndFeelException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        
      }});
    
    generalTab.add(center,BorderLayout.CENTER);
    
    centerPanel.add(generalTab.getName(),generalTab);
  }

  private void addBoardTab() {
    boardTab = new JPanel(new BorderLayout());
    
    boardTab.setName("Board");
    boardTab.setBorder(new TitledBorder(boardTab.getName()));
    
    JPanel center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    
    final ChessBoardSample sample = new ChessBoardSample();
    sample.setPreferredSize(new Dimension(180,180));
    //center.add(sample);
    
    BoardParameters parameters = sample.parameters;
    
    JPanel lastMove = new JPanel();
    lastMove.setLayout(new BoxLayout(lastMove, BoxLayout.Y_AXIS));
    lastMove.setName("Last move highlight");
    lastMove.setBorder(new TitledBorder(lastMove.getName()));
    
    JLabel type = new JLabel("Type");
    final JCheckBox drawArrow = new JCheckBox("Draw arrow");
    lastMove.add(drawArrow);
    
    drawArrow.setSelected(parameters.showArrows());
    drawArrow.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        sample.parameters.setShowArrows(drawArrow.isSelected());
        sample.repaint();
      }});
    
    JPanel right = new JPanel();
    right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
    right.add(lastMove);
    
    JSlider a = new JSlider(1,20,10);
    a.setMajorTickSpacing(10);
    a.setMinorTickSpacing(5);
    a.setSnapToTicks(true);
    a.setPaintTicks(true);
    //JSlider(int orientation, int min, int max, int value) 
    right.add(a);
    
    JPanel top = new JPanel();
    top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
    top.add(sample);
    top.add(right);
    
    final JCheckBox snapCenter = new JCheckBox("Piece snap center");
    final JCheckBox dragPieces = new JCheckBox("Drag pieces");
    final JCheckBox slidePieces = new JCheckBox("Slide pieces");
    final JCheckBox mouseOver = new JCheckBox("Show mouse over");
    final JCheckBox cursorChange = new JCheckBox("Allow cursor change");
    
    
    mouseOver.setSelected(parameters.showMouseOver());
    mouseOver.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        sample.parameters.setShowMouseOver(mouseOver.isSelected());
        sample.repaint();
      }});
    
    cursorChange.setSelected(parameters.allowCursorChange());
    cursorChange.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        sample.parameters.setAllowCursorChange(cursorChange.isSelected());
        sample.repaint();
      }});
    
    JPanel moveInputList = new JPanel();
    moveInputList.setLayout(new BoxLayout(moveInputList, BoxLayout.Y_AXIS));
    moveInputList.add(snapCenter);
    moveInputList.add(dragPieces);
    moveInputList.add(slidePieces);
    moveInputList.add(mouseOver);
    moveInputList.add(cursorChange);
    
    JScrollPane movePane = new JScrollPane(moveInputList,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    JPanel moveInput = new JPanel();
    moveInput.setLayout(new BoxLayout(moveInput, BoxLayout.Y_AXIS));
    moveInput.setName("Move input");
    moveInput.setBorder(new TitledBorder(moveInput.getName()));
    moveInput.add(movePane);
    
    center.add(moveInput);
    
    boardTab.add(top,BorderLayout.NORTH);
    boardTab.add(center,BorderLayout.CENTER);
    
    centerPanel.add(boardTab.getName(),boardTab);
  }
  
  
  private void no() {
    generalTab = new JPanel(new BorderLayout());
    generalTab.setName("General");
    generalTab.setBorder(new TitledBorder(generalTab.getName()));
    
    //generalTab.add(center,BorderLayout.CENTER);
    
    centerPanel.add(generalTab.getName(),generalTab);
    
  }
  JPanel chessSetTab;
  private void addChessSetTab() {
    chessSetTab = new JPanel(new BorderLayout());
    chessSetTab.setName("Chess Set");
    chessSetTab.setBorder(new TitledBorder(chessSetTab.getName()));
    
    chessSetTab.add(new ChessSetDialog(null).getContentPane());
    
    centerPanel.add(chessSetTab.getName(),chessSetTab);
  }
  private void buildGUI() {
    Container cpane = getContentPane();
    cpane.setLayout(new BorderLayout());
    
    cardLayout = new CardLayout();
    centerPanel = new JPanel(cardLayout);
    
    addGeneralTab();
    addBoardTab();
    addChessSetTab();
	//-------------------------------------------------------------------------------
	//	Personal Details Tab
	//-------------------------------------------------------------------------------
    
    JPanel personalDetails = new JPanel(new BorderLayout());
    personalDetails.setName("Personal Details");
    personalDetails.setBorder(new TitledBorder(personalDetails.getName()));
    
    JPanel namePanel = new JPanel(new SpringLayout());
    JLabel nameLabel = new JLabel("Name: ");
    JTextField nameField = new JTextField(10);
    nameLabel.setLabelFor(nameField);
    namePanel.add(nameLabel,SpringLayout.WEST);
    personalDetails.add(namePanel,BorderLayout.NORTH);
    
    //centerPanel.add(personalDetails.getName(),personalDetails);
    
	//-------------------------------------------------------------------------------
	//	Startup Tab
	//-------------------------------------------------------------------------------
    
    JPanel startup = new JPanel(new BorderLayout());
    startup.setName("Startup");
    startup.setBorder(new TitledBorder(startup.getName()));
    
    //centerPanel.add(startup.getName(),startup); 
    
	//-------------------------------------------------------------------------------
	//	Network Tab
	//-------------------------------------------------------------------------------
    
    JPanel network = new JPanel(new BorderLayout());
    network.setName("Network");
    network.setBorder(new TitledBorder(network.getName()));
    
    //centerPanel.add(network.getName(),network);
    
    
	//-------------------------------------------------------------------------------
	//	Bottom
	//-------------------------------------------------------------------------------
    
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton ok = new JButton("Ok");
    JButton cancel = new JButton("Cancel");
    JButton apply = new JButton("Apply");
    bottomPanel.add(ok);
    bottomPanel.add(cancel);
    bottomPanel.add(apply);
    
    ok.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        dispose();  
      }});
    
    ok.setEnabled(true);
    cancel.setEnabled(false);
    apply.setEnabled(false);
    
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        dispose();
      }});
    
	//-------------------------------------------------------------------------------
	//	Tab List Selection
	//-------------------------------------------------------------------------------
    
    Object[] listData = { new priLabel(generalTab.getName()), 
                          //new altLabel(personalDetails.getName()), 
                          //new altLabel(startup.getName()),
                          //new priLabel(network.getName()),
                          //new altLabel("Chat"),
                          //new altLabel("Proxy"),
                          new priLabel(boardTab.getName()),
                          new altLabel(chessSetTab.getName())
                      };
    
    list = new ComponentList();
    list.setListData(listData);
    
    list.addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent e) {
        cardLayout.show(centerPanel,list.getSelectedValue().toString());
        
      }});

    int selectedIndex = 0;
    cardLayout.show(centerPanel,list.getComponent(selectedIndex).toString());
    list.setSelectedIndex(selectedIndex);
    
	//-------------------------------------------------------------------------------
	//	Add Components together
	//-------------------------------------------------------------------------------
    
    ///JTabbedPane centerPane = new JTabbedPane(JTabbedPane.LEFT);
    //centerPane.add(centerPanel,"Setup");
    
    cpane.add(list,BorderLayout.WEST);
    cpane.add(centerPanel,BorderLayout.CENTER);
    cpane.add(bottomPanel,BorderLayout.SOUTH);
  }
  
  class priLabel extends JPanel {
    private Font pri = new FontUIResource("SansSerif", Font.BOLD, 15);
    private boolean selected;
    private String name;
    public priLabel(String name) {
      setLayout(new FlowLayout(FlowLayout.LEFT));
      this.name = name;
      JLabel label = new JLabel(name);
      label.setFont(pri);
      add(label);
    }
    public String toString() {
      return name;
    }
  }
  class altLabel extends JPanel {
    private Font alt = new FontUIResource("SansSerif", Font.PLAIN, 12);
    private String name;
    public altLabel(String name) {
      setLayout(new FlowLayout(FlowLayout.LEFT));
      this.name = name;
      JLabel label = new JLabel("     "+name);
      label.setFont(alt);
      add(label);
    }
    public String toString() {
      return name;
    }
  }
  
  class Main extends JPanel implements ActionListener{
    Image back;
    public Main(){
      super(new BorderLayout());

      
      JTabbedPane tabbedPane = new JTabbedPane();
      JPanel preferences = new JPanel();
      tabbedPane.add(preferences,"Preferences");
      JPanel colors = new JPanel();
      tabbedPane.add(colors,"Colors");
      JPanel ai = new JPanel();
      tabbedPane.add(ai,"AI");
      JPanel chessSet = new JPanel();
      tabbedPane.add(chessSet,"ChessSet");
      
      setPreferredSize(new Dimension(500,400));
      
      add(tabbedPane);
    }
    
    class Center extends JPanel {
      public Center() {
      }
      
      public void init() {
        back = Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/menu.jpg"));
      }
      
      public void paint(Graphics g) {
        g.drawImage(back,0,0,getSize().width,getSize().height,this);
      }
    }

    public void actionPerformed(ActionEvent e) {
    }
  }
  }