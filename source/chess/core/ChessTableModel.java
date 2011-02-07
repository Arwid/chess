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
 * Created on Apr 3, 2005
 *
 */

package chess.core;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * ChessTableModel is a custom table model specific for Move's to
 * display move Notation
 * 
 * @author Arvydas Bancewicz
 */
public class ChessTableModel extends AbstractTableModel {
  
  // |Move index  |  white  |  black |
  private String[] columnNames = {
      "num",
      "white",
  	  "black"
      };
  // Values for the table
  private Vector[] data;
  
  /**
   * Constructor.
   *
   */
  public ChessTableModel() {
    data = new Vector[3];
    data[0] = new Vector(); // move index #
    data[1] = new Vector(); // white notation
    data[2] = new Vector(); // black notation
  }
  
  public int getColumnCount() {
    return data.length;
  }
  
  public int getRowCount() {
    return data[0].size();
  }
  
  public String getColumnName(int col) {
    return columnNames[col];
  }

  /**
   * Returns the cell value at row and column. Move notation as a string
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt(int row, int col) {
    
    // The object is a move
    try {
      Move m = ((Move)data[col].elementAt(row));
      return m.toString();
    } catch (Exception e) {}
    
    // The object is not a move, possibly a number
    try {
      if (data[col].elementAt(row)!=null)
        return data[col].elementAt(row);
    } catch (Exception e2) {}
    
    // The object is neither, return an empty string
    return "";
  }

  /**
   * JTable uses this method to determine the default renderer/
   * editor for each cell.
   */
  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }
  
  /**
   * JTable uses this method to determine if the cell is editable
   * @see javax.swing.table.TableModel#isCellEditable(int, int)
   */
  public boolean isCellEditable(int row, int col) {
    return false;
  }
  
  /**
   * This method is used to add a Move object to the data
   * @param m a Move object
   */
  public void add(Move m) {
    
    // First Move object
    if (data[1].size()==0) {
      data[0].add(data[1].size()+1+".");
      fireTableCellUpdated(data[0].size(),0);
      data[1].add(m);
      fireTableCellUpdated(0, 1);
      data[2].add("");
      fireTableCellUpdated(0, 2);
      return;
    }
    
    // Other Move objects
    if(data[2].elementAt(data[2].size()-1) instanceof Move) {
      data[0].add(data[1].size()+1+".");
      fireTableCellUpdated(data[0].size(),0);
      data[1].add(m);
      fireTableCellUpdated(data[1].size()-1, 1);
      data[2].add(null);
      fireTableCellUpdated(data[2].size()-1, 2);
    } else {
      data[2].set(data[2].size()-1, m);
      fireTableCellUpdated(data[2].size()-1, 2);
    }
  }
}
