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
import java.util.Properties;

public class PlayerParameters /*extends Parameters*/ implements Serializable{

  private String playerNm = "Default";
  private String playerNmName = "player.name";
  
  private boolean isUser = true;
  private String isUserName = "player.user";
  
  public PlayerParameters() {
    //super("ChessPlayer.props", "Chess Player Properties");
    //getParameters();
  }
  
  public PlayerParameters(String name, boolean isUser) {
    //super("ChessPlayer.props", "Chess Player Properties");
    
    setName(name);
    setUser(isUser);
    
    //getParameters();
  }

  protected void setDefaults(Properties defaults) {
    defaults.put(playerNmName, playerNm);
  }

  protected void updateSettingsFromProperties() {
    try {
      //playerNm = properties.getProperty(playerNmName);
    } catch (NumberFormatException e) {
            // we don't care if the property was of the wrong format,
            // they've all got default values. So catch the exception
            // and keep going.
    }
  }

  protected void updatePropertiesFromSettings() {
    //properties.put(playerNmName, playerNm);
  }
  
  public String toString() {
    return "["
  	       + "playerName=" + playerNm
  	       + "]";
  }
  
  public void setName(String name) {
    playerNm = name;
  }
  
  public String getName() {
    return playerNm;
  }
  
  public void setUser(boolean isUser) {
    this.isUser = isUser;
  }
  
  public boolean isUser() {
    return isUser;
  }

}