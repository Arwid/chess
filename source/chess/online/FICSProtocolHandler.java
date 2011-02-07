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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author main
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FICSProtocolHandler extends ICSProtocolHandler {
  
  /** the socket time-out is used to see if our connection is dropped
   ** and we're not told about it. The number is in milliseconds*/
public final static int SOCKET_TIMEOUT = 30 * 100 * 60;
/** this is the delay between tests to see if there's any info on the 
 ** socket in nanoseconds. Setting this to 0 will peg your CPU needlessly **/
protected int SOCKET_DELAY = 1000;
  
//FIXME: this stuff is WAY too server specific
protected String LOGIN_PROMPT   = "login:",
                 PASSWD_PROMPT  = "password:",
                 CMD_PROMPT     = "\nfics% ",
                 GUEST_PROMPT   = "Press return to enter the server as \"",
                 START_SESSION  = "**** Starting FICS session as ",
                 INVALID_PASSWD = "**** Invalid password! ****",
                 INTERFACE_NAME = 
		       "-=[ ictk ]=- v0.2 http://ictk.sourceforge.net";

//common regex phrases
protected final static String REGEX_handle    = "([\\w]+)",
                              REGEX_acct_type = "(\\(\\S*\\))?";


public FICSProtocolHandler () {
  host   = "64.71.131.140"; //defaults
  port   = 5000;
  
}

public void connect () 
throws UnknownHostException, IOException {

if (handle == null || passwd == null)
   throw new IllegalStateException(
  "Both handle and password must be set before login");


   socket = new Socket(host, port);

//socket.setSoTimeout(SOCKET_TIMEOUT);
try {
   socket.setKeepAlive(true);
}
catch (SocketException e) {
   //Log.error(Log.USER_WARNING, e.getMessage());
}

//FIXME: Should BufferedInputStream be used here?
in = new InputStreamReader(socket.getInputStream());
out = new PrintWriter(socket.getOutputStream());
thread.start();
}

/* doLogin ****************************************************************/
protected boolean doLogin () throws IOException {
   boolean seenLogin = false,
           seenPasswd = false;
   String tmp = null;
   int b = 0;
   char c = ' ';
   int mark = 0;
   
   Matcher match = null;
	 //Successful login start
   Pattern REGEX_sessionStart = Pattern.compile(
		     "^\\*\\*\\*\\* Starting FICS session as "
		     + REGEX_handle
		     + REGEX_acct_type
		     + " \\*\\*\\*\\*"
		      , Pattern.MULTILINE);

      while ((b = in.read()) != -1) {
      }
  return seenPasswd;
}

/* sendCommand ************************************************************/
/** send a command to the server.
 */
public void sendCommand (String cmd, boolean echo) {
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


/* disconnect *************************************************************/
public void disconnect () {
   sendCommand("exit");
}


/* run *********************************************************************/
public void run () {
    try {
	  isLoggedIn = doLogin();
	  if (isLoggedIn) {
          setLoginVars();
	     processServerOutput(); 
	  }
	  else {
	  //close socket etc
	  }
	  //end thread
	  if (socket != null && !socket.isClosed()) {}
	     //socket.close();
    }
    catch (IOException e) {
       e.printStackTrace();
    }
    //FIXME: might want to differentiate between bad end and normal end
    //dispatchConnectionEvent(new ICSConnectionEvent(this));
}

/* setLoginVars **********************************************************/
/** sets variables this connection will use.  Some are necessary
 *  for the correct parsing of server messages.
 */
protected void setLoginVars () { 
   //sendCommand("iset block 1");
   //isBlockMode = true;
   sendCommand("set prompt", false);
   sendCommand("set style 12", false);
   sendCommand("iset ms 1", false);
   sendCommand("set interface " + INTERFACE_NAME, false);
   sendCommand("set bell 0", false);
}

/* processServerOutput **************************************************/
/** processes output once the user is logged in
 */
protected void processServerOutput () {
   chunkByPrompt();
}

protected void chunkByPrompt () {
  int b    = -1;  //integer form of the character
  char c   = ' ';
  byte ptr = 0;   //pointer to a position in the prompt
  char[] prompt = CMD_PROMPT.toCharArray();
}

}
