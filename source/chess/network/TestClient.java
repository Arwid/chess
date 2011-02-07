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
 * Created on Feb 10, 2005
 *
 */

package chess.network;

//import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class TestClient extends JFrame {
	
	private JEditorPane  htmlPane;
	private JScrollPane scrollPane;
	private JTextField textField;
	
	Container cp;
	
	public TestClient() {
		setSize(300,400);
		setTitle("Client Chat");
		
		htmlPane = new JEditorPane();
		htmlPane.setContentType("text/html");
		scrollPane = new JScrollPane(htmlPane);
		textField = new JTextField(20);
		
		cp = getContentPane();
		
		cp.add(textField, BorderLayout.NORTH);
		cp.add(scrollPane, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws IOException {
		ChessClient client = ChessClient.getInstance();
		client.connectTo("127.0.0.1");
		TestClient t = new TestClient();
		t.setVisible(true);
		client.startChat(t.htmlPane, t.textField, "Client");
		System.out.println("good");
	}
}