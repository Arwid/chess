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

import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.util.*;

import chess.core.ChessGame;
import chess.core.Move;
import chess.gui.panels.ChessComponent;

/**
 * This class handles the Chess game session.
 */
public class ChessNetPlay {
	private Socket opponent;
	
	private BufferedWriter netOut;
	
	private BufferedReader netIn;
	
	private ChessGame chessGame;
	
	public ChessNetPlay(Socket opponent) {
	  chessGame = ChessComponent.getInstance().chessGame;
		this.opponent = opponent;
		try {
			netIn		= new BufferedReader(new InputStreamReader(opponent.getInputStream()));
			netOut	= new BufferedWriter(new OutputStreamWriter(opponent.getOutputStream()));
		} catch(IOException e) {}		
	}
	
	/**
	 * The method called by MsgRedirector....
	 */
	public void receive(String msg) {		
		msg = msg.substring("MOV:".length());
				
		StringTokenizer st = new StringTokenizer(msg, ":");

		int fromCol = Integer.parseInt(st.nextToken());
		int fromRow = Integer.parseInt(st.nextToken());
		int toCol 	= Integer.parseInt(st.nextToken());
		int toRow 	= Integer.parseInt(st.nextToken());

		chessGame.movePiece(new Move(fromCol, fromRow, toCol, toRow));
	}
	
	/**
	 * Move the chess piece from row <fromRow> and
	 * column <fromCol> to row <toRow> and column
	 * <toCol>. Checking must be done beforehand if the
	 * move is legal.
	 */
	public void move(int fromCol, int fromRow,
			                   int toCol,   int toRow) {
		try {
			String msg = "MOV:" + fromCol + ":" + fromRow  + ":" + toCol + ":" + toRow + "\n";
			System.out.println("message=" + msg);
			netOut.write(msg);		
			netOut.flush();
		} catch(IOException e) {
			
		}
	}
}