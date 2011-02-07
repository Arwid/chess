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
 * Created on Mar 23, 2005
 *
 */

package chess.gui.window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import chess.gui.panels.ChessChat;
import chess.gui.panels.ChessComponent;
import chess.network.NetworkEnd;

public class PeerConnectDialog extends JDialog {
  
  final private ChessComponent cc;
  final private ChessChat chat;
  
  public PeerConnectDialog (JFrame frame,ChessComponent cc){
    super(frame,"Peer to Peer Connection",true);
    setSize(new Dimension(400,400));
    
    this.cc = cc;
    chat = cc.chat;
    
    JLabel type = new JLabel("Choose Type");
    final JRadioButton server = new JRadioButton("Server");
    final JRadioButton client = new JRadioButton("Client");
    ButtonGroup group = new ButtonGroup();
    group.add(server);
    group.add(client);
    
    JPanel left = new JPanel(new GridLayout(3,1));
    left.setBorder(new EtchedBorder());
    left.add(type);
    left.add(server);
    left.add(client);
    
    JLabel conLB = new JLabel("    Connect to:");
    final JTextField ip = new JTextField();
    final JButton connect = new JButton("Connect");
    final JTextField success = new JTextField("no connection");
    success.setEditable(false);
    
    final JPanel center = new JPanel(new GridLayout(3,2));
    center.add(conLB);
    center.add(ip);
    center.add(connect);
    center.add(success);
    
    final JButton start = new JButton("Start");
    
    JPanel bottom = new JPanel(new BorderLayout());
    bottom.add(start,BorderLayout.CENTER);
    
    JPanel whole = new JPanel(new BorderLayout());
    whole.add(left,BorderLayout.WEST);
    whole.add(center,BorderLayout.CENTER);
    whole.add(bottom,BorderLayout.SOUTH);
    
    start.setEnabled(false);
    add(whole);
    
    client.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        connect.setEnabled(true);
        start.setEnabled(true);
      }
    });
    
    server.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        connect.setEnabled(false);
        start.setEnabled(true);
      }
    });
    
    start.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
      	NetworkEnd.server = server.isSelected();
        chat.setChatParameters(server.isSelected(),ip.getText());
      }
    });
    
    pack();
    
    setLocationRelativeTo(null); // Center window in screen
  }
}