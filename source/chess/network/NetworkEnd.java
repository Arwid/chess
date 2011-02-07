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

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JTextField;

//this class represents an end-point on a network
public abstract class NetworkEnd {
	
	/** The default port to use */
	protected static final int DEFAULT_PORT = 3252;
	
	/** A socket connection to the opponent */
	protected Socket opponent;	
	
	/** A buffered input stream with the opponent */
	protected BufferedReader netIn;
	
	/** A buffered output stream with the opponent */
	protected BufferedWriter	netOut;
	
	/** A chat session */
	protected ChessChat chat;
	
	/** A chess game session */
	protected ChessNetPlay netPlay;
	
	/** true if this pc is server; false if client */
	public static boolean server;

	/**
	 * Start a chat session with the opponent. This method starts two threads:
	 * a ChatListener thread listens for incoming chat messages from the
	 * opponent, and a ChatSend thread which listens for user input and then
	 * sends that input to the opponents ChatListener thread.
	 * 
	 * @param doc the text area that holds the chat output.
	 * @param field the text field that holds the user input to send.
	 * @param userName some name to identify the user.
	 */	
	public void startChat(JEditorPane doc, JTextField field, String userName) {
		chat = new ChessChat(opponent, doc, field, userName);
	}
	
	public void startGame() {
		netPlay = new ChessNetPlay(opponent);
	}
	
	public void close() {
		try {
			netIn.close();
			netOut.close();
			opponent.close();
		} catch(IOException e) {
			System.out.println("Error: Closing problem");
		}
	}
	
	public ChessNetPlay getPlay() {
		return netPlay;
	}
	
	public ChessChat getChat() {
		return chat;
	}
}