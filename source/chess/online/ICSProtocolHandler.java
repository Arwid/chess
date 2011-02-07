/*
 * Created on Apr 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package chess.online;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/** The generic connection object.  This handles logins, disconnects
 *  and chunking the server messages.
 */
public abstract class ICSProtocolHandler implements Runnable{
  
  protected Thread thread;
  /** this connection to the server */
  protected Socket socket;
     /** connected to the socket */
  protected PrintWriter out;
     /** connected to the socket */
  protected InputStreamReader in;
  
  protected String handle, passwd;
  
  /** the server you want to connect to */
  protected String host;
  /** the port on the server you want to connect to */
  protected int    port;
  /** is the user logged in */
  protected boolean isLoggedIn;
  
  public ICSProtocolHandler () {
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
  
  /* setHost ****************************************************************/
  /** set the host (server) you want to connect to.  Setting a bad host will
   *  not throw an error until you try to connect.
   */
  public void setHost (String host) {
     this.host = host;
  }

  /* getHost ****************************************************************/
  /** gets the name of the host that you want to / or have connected to.
   */
  public String getHost () {
     return host;
  }

  /* setPort ****************************************************************/
  /** sets the port number you want to connect to.
   */
  public void setPort (int port) {
     this.port = port;
  }

  /* getPort ****************************************************************/
  /** gets the port number you want to / or have connected to.
   */
  public int getPort () {
     return port;
  }
  
  /* isConnected ************************************************************/
  /** is the program currently connected to the host.
   */
  public boolean isConnected () {
     if (socket == null)
        return false;
     return socket.isClosed();
  }

  /* isLoggedIn *************************************************************/
  /** is the user currently logged into the server.  This implies that 
   *  there is currently a connection to the server.
   */
  public boolean isLoggedIn () {
     return isLoggedIn;
  }
  
//connection////////////////////////////////////////////////
  abstract public void connect ()
     throws UnknownHostException, IOException;

  abstract public void disconnect ();

  abstract public void sendCommand (String cmd);
  abstract public void sendCommand (String cmd, boolean echo);
  
}
