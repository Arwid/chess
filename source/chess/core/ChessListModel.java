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
 * Created on Dec 6, 2004
 */

package chess.core;

import java.io.Serializable;
import java.util.Vector;
import javax.swing.AbstractListModel;

/**
 * ChessListModel is a custom list model specific for Move's
 * @author main
 */
public class ChessListModel extends AbstractListModel implements Serializable {

  protected Vector list;

  public ChessListModel() {
    list = new Vector();
  }

  public Object getElementAt(int index) {
    return ((Move) list.elementAt(index)).toString();
  }

  public int getSize() {
    return list.size();
  }
  
  public void add(int s) {
    list.add(String.valueOf(s));
  }
  
  public void add(Move m) {
    list.add(m);
    fireIntervalAdded(this, list.size(), list.size());
  }
  
  public void add(String s) {
    list.add(s);
    fireIntervalAdded(this, list.size(), list.size());
  }

}