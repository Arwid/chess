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
 * Created on Apr 2, 2005
 *
 */

package chess.algorithms;

import chess.core.Board;

/**
 * This is simply a MoveAlgorithm tester.
 * 
 * @author Arvydas Bancewicz
 *
 */
public class Tester {
  
  public static void main (String[] args) {
    
    /*
     * Follow the simple pattern bellow. NOTE: The default depth is 3. Setting
     * the depth to 1 or 5 will only set it for the object used, which
     * below is for MiniMax and not for any other algorithm.
     * 
     * ma.replay(true)   - to reply for white
     * ma.replay(false)  - to reply for black
     * 
     */
    
    MoveAlgorithm ma;
    
    ma = new MiniMax(new Board());
    ma.setDepth(1);
    ma.reply(true);
    ma.reply(false);
    
    ma = new AlfaBeta(new Board());
    ma.reply(true);
    ma.reply(false);
    
    ma = new NegaScout(new Board());
    ma.reply(true);
    ma.reply(false);
    
    ma = new PrincipalVariation(new Board());
    ma.reply(true);
    ma.reply(false);
    
    ma = new RandomGen(new Board());
    ma.reply(true);
    ma.reply(false);
    
  }
}