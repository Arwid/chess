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
 * Created on Undisclosed
 */

package chess.properties;

import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Hashtable;
import java.util.Map;
 
public final class ChessSettings {
  
  private final static ChessSettings set;
  
  public boolean pieceDragCenter = false;
  public boolean pieceSlide = true;
  
  public Object renderValue =  RenderingHints.VALUE_RENDER_QUALITY;
  
  private static Hashtable settings;
  
  public static void add(Map map, Object[] array) {
		for (int i=0; i<array.length; i +=2 )
			map.put(array[i], array[i+1]);
	}
  
  public boolean showMouseOverBlockHighlight = false;
  //private static boolean pieceDragCenter = false;
  //private static boolean pieceShowPossibleMoves = false;
  //private static boolean pieceSlide = false;
  //private static boolean showMouseOverBlockHighlight = false;
  private static boolean showSideBar = true;
  private static boolean showSafeZone = false;
  private static boolean showLegalMoveZone = true;
  private static boolean showDangerZone = false;
  
  static {
    System.out.println("hello there");
    set = new ChessSettings();
    System.out.println("hello there !");
    settings = new Hashtable();
    System.out.println("hello there !!");
    factorySettings();
    System.out.println("hello there !!!!");
  }
  
  private static void factorySettings() {
    settings.put("image.scale_quality",new Integer(Image.SCALE_SMOOTH)); // Scaled image quality
    settings.put("board.antialiasing", RenderingHints.VALUE_ANTIALIAS_ON);
  }
  
  public static int getImageScaleQuality() {
    System.out.println("VALUEEE");
    int value = Integer.parseInt(""+settings.get("image.scale_quality"));
    return value;
  }
  
  public static Object getBoardAntialiasing() {
    Object value = settings.get("board.antialiasing");
    return value;
  }
  
   
  public ChessSettings(){
    //tablet = new ChessColors();
  }
}