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

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * CustomCellRenderer is a custom cell renderer for a JTable. 
 * (Used by the Move notation table) It changes the table row colors to 
 * alternate and formats the cell values (text).
 * 
 * @author Arvydas Bancewicz
 */
public class CustomCellRenderer extends JLabel implements TableCellRenderer {
  
  public CustomCellRenderer() {
    super();
    initialize();
  }

  public Component getTableCellRendererComponent(JTable table, Object value, 
      boolean isSelected, boolean hasFocus, int row, int column) {
    
    Color foreground = null;
    Color background = null;
    Font font = null;
    
    TableModel model = table.getModel();
    
    if (isSelected) {
      setForeground((foreground != null) ? foreground : table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground((foreground != null) ? foreground : table.getForeground());
      setBackground((background != null) ? background : table.getBackground());
      
      // Alternate the row background with two colors
      if (row % 2 == 1)
        setBackground(new Color(245,245,245));
        
      if (column==0) 
        setBackground(new Color(235,235,235));
      
    }
    
    setFont((font != null) ? font : table.getFont());
    Font a = new Font("Serif", Font.BOLD, 12);
    
    // Set the text allignment
    if (column==0) {
      setFont(a);
      setHorizontalAlignment(JLabel.RIGHT);
    } else 
      setHorizontalAlignment(JLabel.LEFT);
    
    setValue("   "+value);
    
    return this;
  }

  private void initialize() {
    setName("TextFormatCellRenderer");
    setOpaque(true);
    setText("TextFormatCellRenderer");
    setSize(125, 30);
  }

  private void setValue(Object value) {
    setText((value == null) ? "" : value.toString());
  }
}


