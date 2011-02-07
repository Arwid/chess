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
 * Created on Mar 24, 2005
 *
 */

package chess.properties;

import java.io.Serializable;

public final class State implements Serializable {

  public static final State BEFOREINITIALIZATION = new State(0);
  public static final State INITIALIZED = new State(1);
  public static final State WAITING = new State(2);
  public static final State PLAYING = new State(3);
  public static final State PAUSED = new State(4);
  public static final State GAMEOVER = new State(5);
    
  private int state;
    
  /*
   * We do not want the user of this class to instantiate the class using 
   * the default constructor
   */
  private State() {}
    
  private State(int state) {
    this.state = state;
  }
  
  public int getState() {
    return state;
  }
    
  /**
   * Public class factory method
   */
  public static State newInstance() {
    return new State();
  }
    
  public void setState(State state) {
    this.state = state.getState();
  }
    
}
