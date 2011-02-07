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
 * Created on April 9, 2005
 *
 */

package chess.online;

import java.net.*;
import java.awt.Container;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;


public class HelloNet extends JFrame implements ActionListener, Runnable {
  
  //static char theLine;
  
 // private JEditorPane htmlPane;
 // private String font;
 // Timer timer = new Timer(1000, this);
  
  private Thread thread;
  /** this connection to the server */
  private Socket socket;
  /** connected to the socket */
  private static PrintWriter out;
  /** connected to the socket */
  private InputStreamReader in;
  
  
  protected String handle, passwd;
  
  /** the server you want to connect to */
  private String host;
  /** the port on the server you want to connect to */
  private int    port;
  /** is the user logged in */
  private boolean isLoggedIn;
  
  
  //private  final int port = 5000;
  //private  final String host = "64.71.131.140";//"freechess.org";
  
  public HelloNet () {
    thread = new Thread(this);
  }
  
  /** sets the user login handle.  This must be done before you try to
   *  connect to the server.
   */
  public void setHandle (String handle) {
     this.handle = handle;
  }
  
  /** returns the handle that the user is logged in as.  Note: this is the
   *  true handle as obtained from the server and may differ from the one 
   *  set before login.  Usually only the case is different.
   */
  public String getHandle () {
     return handle;
  }
  
  /** sets the password this user will use to connect.  This must be set
   *  before you attempt to log into the server.
   */
  public void setPassword (String password) {
     passwd = password;
  }

  /* getPassword ************************************************************/
  /** returns the password used to login.
   */
  public String getPassword () {
     return passwd;
  }
  
  /** is the program currently connected to the host.
   */
  public boolean isConnected () {
     if (socket == null)
        return false;
     return socket.isClosed();
  }
  
  /** is the user currently logged into the server.  This implies that 
   *  there is currently a connection to the server.
   */
  public boolean isLoggedIn () {
     return isLoggedIn;
  }
  
  
  // static BufferedReader theReader;

  // static BufferedReader input;
   
  // boolean _isConnected = false;
   
  /*
   public HelloNet1 () throws IOException {
     Container pane = getContentPane();
     
     htmlPane = new JEditorPane();

     add(htmlPane);
     
     setLocationRelativeTo(null);
     //pack(); //Perform the layout
     
     setVisible(true);
     
     pack();
     
   }
  */

  
  public void connect() {
	  if (!_isConnected) {
	  try {
		   

		  theSocket = new Socket("freechess.org", 5000);
		  theReader = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
		  out = new PrintWriter(new BufferedOutputStream(theSocket.getOutputStream()));
		  input = new BufferedReader(new InputStreamReader(System.in));
		  _isConnected = true;
		  System.out.println("Connected");
	  } catch (IOException connectionError) {
		  System.out.println("Failed to connect: Please check your Internet connection and/or adjust your firewall.");
		  System.out.println("Error: "+connectionError.getMessage());
		  _isConnected = false;
	  }
  } else {
	  System.out.println("Failed to connect: You are connected already.");
  }
}
   
  
  public void disconnect() throws IOException{
	  if (_isConnected) {
		  theReader.close();
		  out.close();
		  theSocket.close();
		  _isConnected = false;
	  } else {
		  System.out.println("Failed to disconnect: You are not connected to the server.");
	  }
  }
  
  
  public void loginAsGuest() throws IOException{
	  connect();
	  waitAndRead();
	  send("guest");
	  waitAndRead();
	  send("");        //Return
	  //waitAndRead();
	  //send("players");
	  //send();
	  timer.start();
	  readNew();
	  timer.start();
	  while (true) {
	    send ();
	  }
	  //readNonStop();
  }
  
  
  public void readNew() {
	  try {
	  while (theReader.ready()) {
	     theLine = (char)theReader.read();
	     System.out.print(theLine);
     }
 }catch (IOException connectionError) {
		  System.out.println("Error: "+connectionError.getMessage());
	  }
 }
 
 public void readNonStop() throws IOException{
	 do {
     while (!theReader.ready()) {
     }
	     theLine = (char)theReader.read();
	     System.out.print(theLine);
     } while (true);
 }
 
 public static void waitAndRead() throws IOException{
	 do {
     while (!theReader.ready()) {
     }
	     theLine = (char)theReader.read();
	     System.out.print(theLine);

     } while (theReader.ready());
 }
 
 public static void send() throws IOException{
   
   System.err.println("Send");
	 String command = input.readLine();
     System.out.println("->"+command);
     command = command+(char)10;
     out.print(command);
     out.flush();
 }
 
 
 public static void send(String command) {
     System.out.println("->"+command);
     command = command+(char)10;
     out.print(command);
     out.flush();
 }
	  
 public static void getPlayers() throws IOException {
  waitAndRead();
  send("players");
  waitAndRead();
  send();
 }
  
  public void actionPerformed(ActionEvent e) {
    //System.err.println("START _________________");
      readNew();
    }
  
  public static void sendCommand (String cmd, boolean echo) {
    if (echo) 
       System.out.println(cmd);

    //if (isBlockMode)
    //   out.println(1 + " " + cmd);
    //else
       out.println(cmd);
    out.flush();
}

public void sendCommand (String cmd) {
   sendCommand(cmd, true);
}
  
  protected static void setLoginVars () { 
    //sendCommand("iset block 1");
    //isBlockMode = true;
    sendCommand("set prompt", false);
    sendCommand("set style 12", false);
    sendCommand("iset ms 1", false);
    //sendCommand("set interface " + INTERFACE_NAME, false);
    sendCommand("set bell 0", false);
    System.out.println("out asdf");
 }

  
  public static void main(String [] args) throws IOException {
    HelloNet1 Telnet = new HelloNet1();
	  
    try {
       Telnet.loginAsGuest();
       setLoginVars();
   } catch (Exception theException) {
	   System.out.println(theException.getMessage());
   }
   //System.err.println("NOW IS THE TIME");
   //getPlayers();
    
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public void run() {
    // TODO Auto-generated method stub
    
  }
}
