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
 * Created on Dec 10 2005
 *
 */

package chess.core;

import java.io.Serializable;

/**
 * Coord contains x and y coordinates for a piece's location in column and row,
 * respectively.
 * 
 * @author Arvydas Bancewicz
 */
public class Coord implements Constants, Serializable {
  
  public byte x;  // Column
  public byte y;  // Row
  
  /**
   *  Constructor to create coordinate 
   */
  public Coord(int x, int y) {
    setCoord(x, y);
  }
  
  /**
   * Set coordinate
   */
  public void setCoord(int x, int y) {
    this.x = (byte)x;
    this.y = (byte)y;
  }
  
  /**
   * Get coordinate notation 
   */
  public String toString() {
    return COORD_X[x]+(y+1);
  }
}