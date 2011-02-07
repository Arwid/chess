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
 * Created on Mar 28, 2005
 *
 */

package chess.properties;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import chess.core.Constants;
import chess.media.BoardMedia;

public class BoardParameters /*extends Parameters*/ implements Constants, Serializable {

  private Color primaryCell = new Color(80,124, 224);//Color.GRAY;
  private Color alternateCell = new Color(167,191,246);//Color.LIGHT_GRAY;

  private String primaryCellName = "board.primaryCell";
  private String alternateCellName = "board.alternateCell";
  
  private boolean willPieceDragCenter = true;
  private boolean willPieceSlide      = true;
  private boolean allowPieceDrag      = true;
  private boolean showArrows          = true;
  private boolean showActiveSpots     = true;
  private boolean showMouseOver       = true;
  private boolean allowCursorChange   = true;
  private boolean showLegend          = true;
  
  public boolean showLegend() {
    return showLegend;
  }
  
  public void setShowLegend(boolean show) {
    showLegend = show;
  }
  
  public BoardMedia boardImages;
  
  private String name = "default";
  
  private final static BoardParameters param1;
  private final static BoardParameters param2;
  private final static BoardParameters param3;
  private final static BoardParameters param4;
  private final static BoardParameters param5;
  
  //BoardImages im = BoardImages.;
  
  public static final Object[] FACTORY_SETTINGS = {
      "board.primaryCell", new Color(80,124, 224),
      "board.alternateCell", new Color(167,191,246),
      };
  
  public static final HashMap settings;
  
  public void setShowArrows(boolean show) {
    showArrows = show;
  }
  public void setShowMouseOver(boolean show) {
    showMouseOver = show;
  }
  public static Vector params;
  
  public void setAllowCursorChange(boolean show) {
    allowCursorChange = show;
  }
  public boolean allowCursorChange() {
    return allowCursorChange;
  }
  public boolean showMouseOver() {
    return showMouseOver;
  }
  public boolean willPieceDragCenter() {
    return willPieceDragCenter;
  }
  
  public void setShowActiveSpots(boolean show) {
    showActiveSpots = show;
  }
  public boolean showActiveSpots() {
    return showActiveSpots;
  }
  
  public boolean willPieceSlide() {
    return willPieceSlide;
  }
  
  public boolean showArrows() {
    return showArrows;
  }
  
  public boolean allowPieceDrag() {
    return allowPieceDrag;
  }
  
  public void setPieceSet(int set) {
    boardImages.setPieceSet(set);
  }
  
  public int getPieceSet() {
    return boardImages.getSet();
  }
  
  static {
    settings = new HashMap();
    settings.put("board.primaryCell", new Color(80,124,224));
    
    params = new Vector();
    
    param1 = new BoardParameters(); // Defaults
    param1.setName("Default");
    param2 = new BoardParameters();
    param2.setPrimaryCell(Color.YELLOW);
    param2.setAlternateCell(Color.GREEN);
    param2.setName("Yellow Green");
    param3 = new BoardParameters();
    param3.setPrimaryCell(Color.ORANGE);
    param3.setAlternateCell(Color.BLUE);
    param3.setName("Orange Blue");
    param4 = new BoardParameters();
    param4.setPrimaryCell(Color.RED);
    param4.setAlternateCell(Color.PINK);
    param4.setName("Red Pink");
    param5 = new BoardParameters();
    param5.setPrimaryCell(Color.GRAY);
    param5.setAlternateCell(Color.LIGHT_GRAY);
    param5.setName("Gray");
    
    params.add(param1);
    params.add(param2);
    params.add(param3);
    params.add(param4);
    params.add(param5);
  }
  
  public static BoardParameters getInstance() {
    return param1;
  }

  public BoardParameters() {
    //super("ChessBoard.props", "Chess Board Properties");
	//getParameters();
	boardImages = new BoardMedia(BoardMedia.set_5);
	//boardImages = new BoardImages();
  }

  protected void setDefaults(Properties defaults) {
	defaults.put(primaryCellName, new Integer(primaryCell.getRGB()).toString());
	defaults.put(alternateCellName, new Integer(alternateCell.getRGB()).toString());
  }

  protected void updateSettingsFromProperties() {

	try {
	    //primaryCell = new Color(Integer.parseInt(properties.getProperty(primaryCellName)));
	    //alternateCell = new Color(Integer.parseInt(properties.getProperty(alternateCellName)));
	} catch (NumberFormatException e) {
	  System.out.println(e);
	    // we don't care if the property was of the wrong format,
	    // they've all got default values. So catch the exception
	    // and keep going.
	}
  }

  protected void updatePropertiesFromSettings() {
	//properties.put(primaryCellName, new Integer(primaryCell.getRGB()).toString());
	//properties.put(alternateCellName, new Integer(primaryCell.getRGB()).toString());
  }

  public String toString() {
      return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public Color getPrimaryCell() {
    return primaryCell;
  }
  
  public Color getAlternateCell() {
    return alternateCell;
  }

  public void setPrimaryCell(Color color) {
    primaryCell = color;
  }
  
  public void setAlternateCell(Color color) {
    alternateCell = color;
  }
}

