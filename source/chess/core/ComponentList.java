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
 * Created on Mar 27, 2005
 *
 */

package chess.core;

import java.awt.*;
import javax.swing.*;

/**
 * ComponentList is a custom JList (eg. a list of JPanels)
 * 
 * @author Arvydas Bancewicz
 */
public class ComponentList extends JList {
  
  public ComponentList() {
    setCellRenderer(new CustomCellRenderer());
    this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  
  class CustomCellRenderer implements ListCellRenderer {
    public Component getListCellRendererComponent
     (JList list, Object value, int index,
      boolean isSelected,boolean cellHasFocus) {
      
      Component component = (Component)value;
      Color pri = new Color(230,230,230); //primary color
      component.setBackground (isSelected ? pri : Color.WHITE);
      component.setForeground (isSelected ? Color.WHITE : Color.BLACK);
      
      component.setEnabled(list.isEnabled());
      return component;
      }
    }

}