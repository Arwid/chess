/*
 * Created on Apr 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package chess.online;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.regex.Matcher;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FICSProtocol extends JFrame implements  Runnable {
  
  private JTextArea outputArea;
  private JScrollPane scrollPane;
  
  private JTextField inputField;
  
  private static Thread thread;
  /** this connection to the server */
  private static Socket socket;
  /** connected to the socket */
  private static PrintWriter out;
  /** connected to the socket */
  private static InputStreamReader in;
  
  protected static String handle, passwd;
  
  /** the server you want to connect to */
  private static String host;
  /** the port on the server you want to connect to */
  private static int    port;
  /** is the user logged in */
  private boolean isLoggedIn;
  
  private String LOGIN_PROMPT  = "login:",
                 PASSWD_PROMPT = "password:",
                 CMD_PROMPT    = "\nfics% ",
                 GUEST_PROMPT   = "Press return to enter the server as \"",
                 START_SESSION  = "**** Starting FICS session as ",
                 INVALID_PASSWD = "**** Invalid password! ****";
  
  public FICSProtocol () {
    thread = new Thread(this);
    host   = "freechess.org";//"64.71.131.140";
    port   = 5000;
    handle = "guest";
    passwd = "";
    //System.out.println("set");
    
    outputArea = new JTextArea();
    outputArea.setFont(new Font("monospaced",Font.TRUETYPE_FONT,12));
    outputArea.setEditable(false);
    scrollPane = new JScrollPane(outputArea, 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    inputField = new JTextField();
    inputField.addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent arg0) {
        sendCommand(inputField.getText());
        inputField.setText("");
        
      }});
    
    setLayout(new BorderLayout());
    add(scrollPane,BorderLayout.CENTER);
    add(inputField,BorderLayout.SOUTH);
  }
  
  public static void connect() 
  throws UnknownHostException, IOException {
    
    if (handle == null || passwd == null)
      throw new IllegalStateException(
      "Both handle and password must be set before login");
    
    socket = new Socket(host, port);
    
    //socket.setSoTimeout(1000);
    
    try {
      socket.setKeepAlive(true);
    } catch (SocketException e) {
      e.printStackTrace();
    }
    
    in = new InputStreamReader(socket.getInputStream());
    out = new PrintWriter(socket.getOutputStream());
    thread.start();
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
  
  private void setLoginVars () { 
    //sendCommand("iset block 1");
    //isBlockMode = true;
    sendCommand("set prompt", false);
    sendCommand("set style 12", false);
    sendCommand("iset ms 1", false);
    //sendCommand("set interface " + INTERFACE_NAME, false);
    sendCommand("set bell 0", false);
    //System.out.println("out asdf");
 }

  
  public static void main(String [] args) throws IOException {
    FICSProtocol Telnet = new FICSProtocol();
    // TODO a
    Telnet.setSize(400,400);
    Telnet.setLocationRelativeTo(null);
    Telnet.setVisible(true);
    connect();

   //System.out.println("connect)");
  }
  
  CharBuffer buffer = CharBuffer.allocate(128 * 1048);
  
  private void print(String str) {
    outputArea.setText(outputArea.getText()+str);
    //System.out.print(str);
    //System.out.flush();
  }
  
  private boolean doLogin () throws IOException {
    boolean seenLogin = false,
            seenPasswd = false;
    Matcher match = null;
    
    int b = 0;
    char c = ' ';
    String tmp = null;
    int mark = 0;
    
    while ((b = in.read()) != -1) {
      c = (char) b;
      //System.out.print((char)c);
      if (c == '\r') {} // get rid of these chars
      else {
        buffer.put(c);
        //System.out.print((char)c);
        if (c == '\n' && !seenPasswd) {
          buffer.limit(buffer.position());
          buffer.rewind();
          //System.out.print(buffer.toString());
          print(buffer.toString());
          
		  //System.out.flush();
		  buffer.clear();
        } else if (c == ':') {
          mark = buffer.position();
          buffer.limit(buffer.position());
          buffer.rewind();
          tmp = buffer.toString();
          //buffer.clear();
          //System.out.println(" => >>>>>>"+tmp+"<<<<<< <=");
          //System.out.println("\nyes");
          
          //login prompt
          if (!seenLogin
              && tmp.lastIndexOf(LOGIN_PROMPT) > -1) {
	        //System.out.print(tmp);
	        //System.out.print(" ");
		    //System.out.flush();
            print(tmp);
            
	        buffer.rewind();
	        buffer.clear();
	        
            sendCommand(handle);
            seenLogin = true;
            //System.out.println("\n there'==");
          
          //password prompt
          } else if (seenLogin && !seenPasswd
              && tmp.lastIndexOf(PASSWD_PROMPT) > -1) {
            //System.out.print(tmp);
	        //System.out.print(" ");
			//System.out.flush();
            print(tmp);
            
		    buffer.rewind();
		    buffer.clear();
		    
            sendCommand(passwd, false);
            seenPasswd = true;
            
            System.out.println();
            //System.out.println("\nhello ----------");
          } else if (seenLogin && !seenPasswd 
	          && tmp.lastIndexOf(GUEST_PROMPT) > -1) {
	        //System.out.print(tmp);
		    //System.out.flush();
            print(tmp);
            
	        buffer.rewind();
	        buffer.clear();

	        sendCommand("");
		    seenPasswd = true;
	      } else {
	        buffer.limit(buffer.capacity());
	        buffer.position(mark);
	      }
        //looking for a response from the password
        } else if (c == '\n' && seenPasswd) {
          mark = buffer.position();
          buffer.limit(mark);
          buffer.rewind();
          tmp = buffer.toString();
          
          //Invalid password
          if (tmp.lastIndexOf(INVALID_PASSWD) > -1) {
		    //System.out.print(tmp);
		    //System.out.flush();
            print(tmp);
		    buffer.rewind();
		    buffer.clear();
		    
		    return false;
		    
		  //Successful Login
          } else if (tmp.lastIndexOf(START_SESSION) > -1) {
            //System.err.println("\n** SUCCESS **");
            return true;
          } else {
            buffer.limit(buffer.capacity());
            buffer.position(mark);
          }
        }    
      }
    }
    return false;
  }
  
  public void run() {
    
    System.out.println("run");
    try {
      isLoggedIn = doLogin();
      if (isLoggedIn) {
        //System.out.println("\n*****START****");
        setLoginVars();
        processServerOutput();
        //System.out.println("\n*****DONE****");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("done");
  }
  
  private void processServerOutput () {
    int b    = -1;  //integer form of the character
    char c   = ' ';
    byte ptr = 0;   //pointer to a position in the prompt
    char[] prompt = CMD_PROMPT.toCharArray();
    //boolean first = true;
    try {
      
      while ((b = in.read()) != -1) {
        //if (first) {
        //sendCommand("help");
        //first = false;
        //}
        //out of range invisible characters
        if (b!= 10 && b!=13 && (b < 32 || b > 126)) {
        }
        
        //normal character
        else {
          c = (char) b;
          if (c != '\r') { //get rid of these
            //buffer.put(c);
            //System.out.print((char)c);
            print(""+(char)c);
            if (c == prompt[ptr]) {  //look for the prompt
              ptr++;
              if (prompt.length == ptr) { //found prompt
                //buffer.limit(buffer.position() - prompt.length);
                //buffer.rewind();
                //send to parser for processing
                //parse(buffer);
                //buffer.clear();
                
                //System.err.println("** PROMPT **");
                ptr = 0;
                
              }
            } 
            else if (ptr == 1 && c == prompt[0]) {
              //got \n before prompt -- do nothing
            } else {  //not prompt
              ptr = 0;
              //buffer.clear();
            }
            
          }
        }
      }
      //purge remaining buffer
      if (buffer.position() > 0) {
        //buffer.limit(buffer.position());
        //buffer.rewind();
        parse(buffer);
      }
    }
    catch (IOException e) {
      // e.printStackTrace();
    }
  }
  /* parse *****************************************************************/
  /** The 'datagram' or message chunk has already been establish, now we
   *  just gotta figure out what the message is and send it to the right
   *  listeners.
   */
  protected void parse (CharSequence str) {
     //ICSEvent icsEvent = null;
     Matcher matcher = null;
     boolean found = false;

     /*
     for (int i=0; i < eventFactories.length && !found; i++) {

        if ((matcher = eventFactories[i].match(str)) != null) {
	    icsEvent = eventFactories[i].createICSEvent(matcher); 
	    //assert icsEvent != null : "parser matched, but event null?";
	    icsEvent.setServer(this);
	    router.dispatch(icsEvent);

	    found = true;
	 }
     }
     

     //SEEK_CLEAR is followed by many seek ads usually
     //SEEK_ADs are not necessarily one per chunk
     CharSequence more = str;
     while (matcher != null
	     && icsEvent != null 
	     && (icsEvent.getEventType() == ICSEvent.SEEK_CLEAR_EVENT
		|| icsEvent.getEventType() == ICSEvent.SEEK_AD_EVENT)) {

	 matcher = FICSSeekAdParser.getInstance().match(
	    more = more.subSequence(matcher.end(), more.length()));

	 if (matcher != null) {
	    icsEvent = FICSSeekAdParser.getInstance().createICSEvent(matcher); 
	    //assert icsEvent != null : "parser matched, but event null?";
	    icsEvent.setServer(this);
	    router.dispatch(icsEvent);
	 }
     }
     */
     if (!found)
        System.out.println(str);

     //what's after the match?
     if (matcher != null) {
       int end = matcher.end();
       if (end < str.length() && str.charAt(end) == '\n') 
	   end++;
       if (end < str.length())
          System.out.println(str.subSequence(end, str.length()));
     }
  }
}

