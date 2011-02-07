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

import java.io.*;
import java.net.*;

public final class ChessServer extends NetworkEnd {
	
	private static final ChessServer server;
	
	// a server socket that listens on a certain port
	private static ServerSocket ss;
	
	static {
		server = new ChessServer();
	}
	
	private ChessServer() {		
	}
	
	public static ChessServer getInstance() {
		return server;
	}
	
	/**
	 * Listen on the default port for incoming connections.
	 * @see <listen>
	 * @throws IOException of any i/o errors occur
	 */
	public void listen() {
		listen(DEFAULT_PORT);
	}
	
	/**
	 * Listen on the specified port for incoming connections. If
	 * a client connects and is not a Chess client, it drops
	 * the connection and continues listening until a connection
	 * with a Chess client is established.
	 * 
	 * @param port the port to listen on
	 * @throws IOException if any i/o errors occur
	 */
	public void listen(int port) {
		try {
			
			//System.out.println("In ChessServer listen() method\nCreating new ServerSocket on port " + port); // debug msg
			ss = new ServerSocket(port);
			//System.out.println("A ServerSocket on port " + port + " has been created");
			// loop until the connected client is a Chess client
			while(true) {
				System.out.println("Waiting for incoming connections...");
				opponent = ss.accept();
				//System.out.println("Incoming connection.");
			  
				netOut = new BufferedWriter(new OutputStreamWriter(opponent.getOutputStream()));
				netIn  = new BufferedReader(new InputStreamReader(opponent.getInputStream()));
				
				netOut.write("ChessServer HELLO\n");
				netOut.flush();
			
				if(!netIn.readLine().equals("ChessClient HELLO")) {
					netIn.close();
					netOut.close();
					opponent.close();
				} else {
					break;
				}
			}
			super.server = true;
			// start new master thread
			MsgRedirector redir = new MsgRedirector(opponent, this);
		} catch(IOException e) {
			System.out.println("Error: I/O problem in ChessServer");
		}
	}
	
	
}