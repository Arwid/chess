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
import java.util.Date;
import java.util.Properties;

import chess.algorithms.MoveAlgorithm;
import chess.core.Constants;

public class GameParameters /*extends Parameters*/ implements Constants, Serializable {
  
  private String dateCreated = new Date().toString();
  private String  gameTitle = dateCreated;
  private long countDown = FIVE_MINUTES;
  
  private String dateCreatedName = "game.created";
  private String countDownName = "count.down";
  
  private PlayerParameters whitePlayer;
  private PlayerParameters blackPlayer;
  
  private MoveAlgorithm algoritm;
  private int algorithmDepth = 3;
  
  public GameParameters() {
    //super("chessGame.props", "Chess Game Properties");
    //getParameters();
    
    
  }

  protected void setDefaults(Properties defaults) {
    defaults.put(dateCreatedName, dateCreated);
    defaults.put(countDownName, new Long(countDown/ONE_SECOND).toString());
  }

  protected void updateSettingsFromProperties() {
    try {
      //dateCreated = properties.getProperty(dateCreatedName);
      //countDown = Long.parseLong(properties.getProperty(countDownName)) * ONE_SECOND;
    } catch (NumberFormatException e) {
      // we don't care if the property was of the wrong format,
      // they've all got default values. So catch the exception
      // and keep going.
    }
  }

  protected void updatePropertiesFromSettings() {
    //properties.put(dateCreatedName, dateCreated);
    //properties.put(countDownName, new Long(countDown/ONE_SECOND).toString());
  }

  public String toString() {
    return "["
           + "dateCreated=" + dateCreated + ","
           + "countDown=" + countDown
           + "]";
  }
  
  public String getDateCreated() {
    return dateCreated;
  }
  
  public String getTitle() {
    return gameTitle;
  }
  
  public void setTitle(String name) {
    gameTitle = name;
  }
}
