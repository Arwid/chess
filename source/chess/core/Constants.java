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
 * Created on Dec 2 2005
 *
 */

package chess.core;

/**
 * Constants is an interface consisting of useful values.
 * 
 * @author Arvydas Bancewicz
 */
public interface Constants {
  
  // Piece types
  byte TYPE_PAWN = 0;
  byte TYPE_ROOK = 1;
  byte TYPE_KNIGHT = 2;
  byte TYPE_BISHOP = 3;
  byte TYPE_QUEEN = 4;
  byte TYPE_KING = 5;
  
  // Move notation 
  String[] NAME = {"Pa", "Kn", "Bi", "Ro", "Qu", "Ki"};
  String[] TYPE = {"", "N", "B", "R", "Q", "K"};
  String[] COORD_X = {"a", "b", "c", "d", "e", "f", "g", "h"};
  int[] cost = {1,5,5,10,40,100};
  
  // Time
  long ONE_SECOND = 1000;
  long FIVE_SECONDS = ONE_SECOND * 5;
  long TWENTY_SECONDS = ONE_SECOND * 20;
  long ONE_MINUTE = ONE_SECOND * 60;
  long FIVE_MINUTES = ONE_MINUTE * 5;
  long TEN_MINUTES = ONE_MINUTE * 10;
  long TWENTY_MINUTES = ONE_MINUTE * 20;
}