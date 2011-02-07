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
 * Created on Mar 31, 2005
 *
 */

package chess.core;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * ChessFileFilter filters only the files with the recognized extension
 * @author Arvydas Bancewicz
 */
public class ChessFileFilter extends FileFilter {
  
  final static String filter = "ch3"; // the recognized extension
  
  /**
   * Accept all directories and all filtered files
   */
  public boolean accept(File f) {

    if (f.isDirectory()) {
      return true;
    }

    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i>0 && i<s.length()-1) {
      String extension = s.substring(i+1).toLowerCase();
      if (filter.equals(extension)) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }
  
  /**
   * The description of this filter
   */
  public String getDescription() {
      return "Chess Files(*."+filter+")";
  }
}
