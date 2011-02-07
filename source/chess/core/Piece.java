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
 * Created on Dec 24 2004
 *
 */

package chess.core;

/**
 * Piece represents a chess piece
 * 
 * @author Arvydas Bancewicz
 *
 */
public class Piece implements Constants, java.io.Serializable {
  
  public byte type;       // Type of piece
  public boolean white;   // Is it white ?
  
  /**
   * Constructor sets up the piece values
   * @param t - type of piece
   * @param w - white ?
   */
  public Piece(byte t, boolean w) {
    white = w;
    setType(t);
  }
  
  /* Set the type of piece. */
  public void setType(byte type) {
    if(this.type!=type) {
      this.type = type;
    }
  }
  
  /* Cost of piece according to piece type */
  public int getCost() {
    return (white?1:-1)*cost[type];
  }
  
}