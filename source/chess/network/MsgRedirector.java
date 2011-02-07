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

import java.net.*;
import java.io.*;

/** This class redirects messages from the opponent to the
 * appropriate method.
 */
public class MsgRedirector implements Runnable {
	
	private Socket sock;
	
	private BufferedReader netIn;
	
	private NetworkEnd end;
	
	public MsgRedirector(Socket sock, NetworkEnd end) {
		this.end = end;
		this.sock = sock;
		try {
			netIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch(IOException e) {
			System.out.println("!!!!!Error: problem creating bufferedreader in MsgRedirector!!!!!");
		}
		new Thread(this).start();
	}
	
	/**
	 * Start the thread that redirects incoming packets...
	 */
	public void run() {
		try {
			while (true) {
				// wait for an incoming message
				String msg = netIn.readLine();
				
				// check if the message is a chat message
				if(msg.startsWith("MSG:")) {
					end.getChat().receive(msg);
				} else if(msg.startsWith("MOV:")) {
					end.getPlay().receive(msg);
				} else {
					System.out.println("!!!!!Error: malformed packet msg in MsgRedirector!!!!!");
				}
				
			}
		} catch(IOException e) {
			System.out.println("!!!!!Error: I/O problem in MsgRedirector!!!!!");
		}		
	}
}