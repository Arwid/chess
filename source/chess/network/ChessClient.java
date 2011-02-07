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

public final class ChessClient extends NetworkEnd {
	private static final ChessClient client;
	
	static {
		client = new ChessClient();
	}
	
	private ChessClient() {}
	
	public static ChessClient getInstance() {
		return client;
	}
	
	/**
	 * Connect to the specified ip on the default port.
	 * 
	 * @param ip the ip of the opponent's computer
	 * @return true if connected; false otherwise
	 * @throws IOException if any i/o errors occur
	 */
	public boolean connectTo(String ip) throws IOException {
		return connectTo(ip, DEFAULT_PORT);
	}
	
	/**
	 * Connect to the specified ip on the specified port.
	 * 
	 * @param ip the ip of the opponent's computer
	 * @param port the port number to connect to
	 * @return true if connected; false otherwise
	 * @throws IOException if any i/o errors occur
	 */
	public boolean connectTo(String ip, int port) throws IOException {
		try {
			opponent = new Socket(ip, port);
			
			netIn  = new BufferedReader(new InputStreamReader(opponent.getInputStream()));
			netOut = new BufferedWriter(new OutputStreamWriter(opponent.getOutputStream()));
			
			// check if the server is a ChessServer
			if(!netIn.readLine().equals("ChessServer HELLO")) {
				netIn.close();
				netOut.close();
				opponent.close();
				return false;
			}
			
			netOut.write("ChessClient HELLO\n");
			netOut.flush();
			
			super.server = false;
			// start new msg redirector
			MsgRedirector redir = new MsgRedirector(opponent, this);
			return true;
		} catch(IOException e) {
			throw new IOException();
		}
	}
}	