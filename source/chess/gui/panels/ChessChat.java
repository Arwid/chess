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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import chess.network.ChessClient;
import chess.network.ChessServer;

public class ChessChat extends JPanel implements ActionListener {
  
  private JButton send;
  private JTextField textField;
  private JEditorPane htmlPane;
  private JScrollPane scrollPane;
  
  private ChessServer server;
  private ChessClient client;

  /** 
   * Build the panel
   */
  public ChessChat() {   
    super(new BorderLayout());
    
    textField = new JTextField("");

    htmlPane = new JEditorPane();
    htmlPane.setContentType("text/html");
    htmlPane.setEditable(false);
    
    scrollPane = new JScrollPane(htmlPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Create the bottom panel
    JPanel textSend = new JPanel(new BorderLayout());
    send = new JButton("Send");
    textSend.add(send,BorderLayout.WEST);
    textSend.add(textField,BorderLayout.CENTER);
    
    // Create the chat box
    JPanel chatBox = new JPanel(new BorderLayout());
    chatBox.add(scrollPane,BorderLayout.CENTER);
    chatBox.add(textSend,BorderLayout.SOUTH);

    add(chatBox);
  }
  
  /* Action performed. */
  public void actionPerformed(ActionEvent e) {}
  
  private void startServer() {
	server = ChessServer.getInstance();
	server.listen();
	server.startChat(htmlPane, textField, "Server");
	server.startGame();
  }
  private void startClient(String ip) {
	client = ChessClient.getInstance();
	try {
	  client.connectTo(ip);
	} catch (IOException e) {
	  e.printStackTrace();
	}
	client.startChat(htmlPane, textField, "Client");
	client.startGame();
  }
  
  public void setChatParameters (boolean server,String ip) {
    if(server)
      startServer();
    else
      startClient(ip);
  }
  
}

