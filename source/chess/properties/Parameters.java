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

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public abstract class Parameters {

    private boolean DEBUG = false;
    private String propertiesFilename;
    private String propertiesDescription;

    protected Properties properties = null;

    protected Parameters(String propertiesFilename, String propertiesDescription) {
      this.propertiesFilename = propertiesFilename;
      this.propertiesDescription = propertiesDescription;
    }

    abstract protected void setDefaults(Properties defaults) ;
    abstract protected void updatePropertiesFromSettings() ;
    abstract protected void updateSettingsFromProperties() ;

    protected void getParameters() {
        Properties defaults = new Properties();
        FileInputStream in = null;

        setDefaults(defaults);

        properties = new Properties(defaults);

        updateSettingsFromProperties();
    }

    protected void saveParameters() {

      updatePropertiesFromSettings();
      
      if (DEBUG) {
        	System.out.println("Just set properties: " + propertiesDescription);
	    	System.out.println(toString());
      }
      
      FileOutputStream out = null;
      
      try {
        String folder = System.getProperty("user.home");
        System.out.println("folder: "+folder);
        String filesep = System.getProperty("file.separator");
        System.out.println("filesep: "+filesep);
        out = new FileOutputStream(folder 
		       + filesep
		       + propertiesFilename);
        properties.save(out, propertiesDescription);
      } catch (java.io.IOException e) {
        //ErrorMessages.error("Can't save properties.");
      } finally {
        if (out != null) {
          try { out.close(); } catch (java.io.IOException e) {}
          out = null;
        }
      }
    }
}