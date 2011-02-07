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
 * Created on Apr 27, 2005
 *
 */
package chess.core;

import java.io.*;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 
 * @author Arvydas Bancewicz
 *
 */
public class PGN {
  
  private static Vector clm;
  
  private static DataInputStream in;
  
  public static void main(String[] args) {
    
    String s = "E:\\myGame200.pgn";
    File file = new File(s);
    FileInputStream f_in;
    try {
      f_in = new FileInputStream(file);

      // Convert our input stream to a
      // DataInputStream
      in = new DataInputStream(f_in);
      
      boolean foundTag = false;
      boolean foundMoveIndication = false;
      boolean foundStatement = false;
      String notation = "";
      
      // Continue to read lines while 
      // there are still some left to read
      
      clm = new Vector();
    } catch (Exception e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    
    /*
    try {
      readFile();
      //setDefaultTags();
      //System.out.println(tagsToString());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    */
    
    String mov = "e2 e4";
    strToMove(mov);
    int siz = clm.size();
    for (int i=0;i<siz;i++) {
      System.out.println(i+". "+clm.elementAt(i));
    }
    //System.out.println(tagsToString());
  }
  
  private static void readFile() throws IOException {
    
    char ch;
    String line;
    int index;
    int len;
    //String token = new String();
    //StringTokenizer st = new StringTokenizer(in.readLine());
    while (in.available() !=0) {
      line = in.readLine();
      //System.out.println("line:"+line+" count:"+line.length());
      len = line.length();
      index = 0;
      while (index < len) {
        ch = line.charAt(index++);
        if (ch == '[') { // start of tag
          String name;
          String value;
          int end = line.indexOf("\"",index);
          name = line.substring(index,end)
                     .replaceAll(" ","");
          index = end+1;
          if (isTag(name)) {
            index = line.indexOf("\"",end)+1;
            end = line.indexOf("\"",index);
            value = line.substring(index,end);
            setTag(name,value); 
          }
          end = line.indexOf("]",end);
          index = end+1;
          //System.out.println("Token:"+token);
        } else {
          int start = line.indexOf(".",index)+1;
          int end = line.indexOf(".",start)-1;
          //System.out.println("--- start:"+start+" end:"+end+" index:"+index);
          if (start > 0) {
            if (end < 0)
              end = len;
            String token = line.substring(start,end);
            System.out.println("move:"+token);
            clm.add(token);
            strToMove(token);
            index = end;
            //System.out.println("   ind:"+index);
          }
        }
      }
    }
  }
  
  private static void strToMove(String str) {
    String white = "";
    String black = "";
    int mid = str.indexOf(" ");
    white = str.substring(0,mid);
    black = str.substring(mid+1,str.length());
    
    white = white.replace("x","").replace(" ","");
    black = black.replace("x","").replace(" ","");
    
    white = white.length()==3?white.substring(1,white.length()):white;
    black = white.length()==3?white.substring(1,white.length()):white;
    
    System.out.println("  TOKEN:"+str+" L:"+mid);
    System.out.println("white:"+white+"black:"+black+"...");
    
    int endCol = white.charAt(0)-97-1;
    

    int endRow = Integer.parseInt(white.charAt(1)+"")-1;
    
    System.out.println(" cssdfsa:"+endCol+":"+endRow);
    
  }
  
  
  static final String NAGStrings[] = {
      "",
      "!",
      "!!",
      "??",
      "!?",
      "?!"};
  
  static Hashtable tags = new Hashtable();
  
  static String tagNames[] = {
      "Event",
      "Site",
      "Date",
      "Round",
      "White",
      "Black",
      "Result",
      "WhiteElo",
      "BlackElo"
  };
  
  public static void setDefaultTags() {
    setTag("Event", "?");
    setTag("Site", "?");
    setTag("Date", "????.??.??");
    setTag("Round", "?");
    setTag("Black", "?");
    setTag("White", "?");
    setTag("Result", "?");
    setTag("WhiteElo", "?");
    setTag("BlackElo", "?");
  }
  
  private static boolean isTag(String name) {
    int siz = tagNames.length;
    for (int i=0;i<siz;i++) {
      if (name.equals(tagNames[i]))
        return true;
    }
    return false;
  }
  
  public static void setTag(String name, String value) {
    tags.put(name, value);
  }
  
  public String getTag(String name) {
    return (String) tags.get(name);
  }
  
  public static String tagsToString() {
    StringBuffer s = new StringBuffer();
    int siz = tagNames.length;
    for (int i=0;i<siz;i++) {
      String name = tagNames[i].toString();
      s.append("["+name+" \""+tags.get(name)+"\"]\n");
    }
    return s.toString();
  }
  
  public boolean hasTag(String name) {
    return tags.containsKey(name);
  }
}
