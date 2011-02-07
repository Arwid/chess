/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*                              Java Chess                                      *
*                 Copyright (C) 2005  Arvydas Bancewicz                        *
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

package chess.gui.window;

/*
 * Created on Mar 20, 2005
 *
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import chess.media.BoardMedia;

public class AboutDialog extends JDialog implements ActionListener {
  
  // Set up values <version>, <authors>, <date>
  String VER = "1.0.0";;
  String AUT = "Arvydas Bancewicz";
  String DAT = "May 16 2005";
  
  public AboutDialog(JFrame frame) {
    super(frame,"About",true);
    
    // Set up the components in the window
    createGUI();
    
    //setPreferredSize(new Dimension(500,500));
    setSize(new Dimension(500,380));
    //this.setSize(200,200);
    
    // Resize the window to the minimum size to satisfy the preferred size of
    // each of the components in the layout.
    //pack();
    
    // Compute the layout. Validate effectively redoes the layout if necessary
    // deciding on new sizes and locations of all the components in the conainer.
    validate();
    
    // Position the window to the center of the screen
    setLocationRelativeTo(null);
    
    // Prevent the window from resizing
    setResizable(false);
  }
  
  public void createGUI() {
    
    Container cpane = getContentPane();
    
    cpane.setLayout(new BorderLayout());
    
    // Create labels to display these values beside the appropriate heading
    JLabel version = new JLabel(" Version: "+VER);
    JLabel author = new JLabel(" Author: "+AUT);
    JLabel date = new JLabel(" Last Edited: "+DAT);
    
    JEditorPane infoPane = new JEditorPane();
    infoPane.setContentType("text/html");
    infoPane.setEditable(false);
    infoPane.setBorder(new EtchedBorder());
    
    infoPane.setText(
        "<pre><font color=#0000FF><strong> Version:    </strong></font>"+VER+"</pre>"+
        "<pre><font color=#0000FF><strong> Authors:    </strong></font>"+AUT+"</pre>"+
        "<pre><font color=#0000FF><strong> Last Edited:</strong></font>"+DAT+"</pre>");
    
    // Group the labels together by adding them to a panel
    JPanel info = new JPanel();
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
    info.add(version);
    info.add(author);
    info.add(date);
    
    // Lets also create a button to provide an alternative way to exit the 
    // window. Then, add it to a panel and give it a nice border.
    JButton ok = new JButton("Close");
    ok.addActionListener(this);
    JPanel bottom = new JPanel();
    //bottom.setBorder(new EtchedBorder());
    bottom.add(ok);
    
    JPanel center = new JPanel(new BorderLayout());
    //center.setBorder(new EtchedBorder());
    //center.setBackground(Color.WHITE);
    //center.add(logo,BorderLayout.WEST);
    //center.add(infoPane,BorderLayout.CENTER);
    //center.add(info,BorderLayout.CENTER);
    center.add(new AboutLabel(),BorderLayout.CENTER);
    
    // Finally, lets group the two panels together and add it to the window.
    JPanel about = new JPanel();
    about.setName("About");
    //about.setBackground(Color.WHITE);
    about.add(center);
    
    JPanel licence = new JPanel(new BorderLayout());
    licence.setName("Licence");
  
    //JTextArea licenceArea = new JTextArea();
    JEditorPane licenceArea = new JEditorPane();
    licenceArea.setContentType("text/html");
    licenceArea.setEditable(false);
    //licenceArea.setLineWrap(true);
    //licenceArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(licenceArea,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    licence.add(scrollPane,BorderLayout.CENTER);
    
    JPanel system = new JPanel();
    system.setName("System");
    
    JPanel contributions = new JPanel();
    contributions.setName("Contributions");
    contributions.setBackground(Color.WHITE);
    
    JEditorPane contributionsPane = new JEditorPane();
    contributionsPane.setContentType("text/html");
    contributionsPane.setEditable(false);
    
    contributionsPane.setText(
        "<p><strong>FH Look & Feel:</strong></font></p>"+
        "<pre>   (fhlaf.sourceforge.net)</pre>");
    
    contributions.add(contributionsPane);
    
    JTabbedPane pane = new JTabbedPane();
    pane.add(about,about.getName());
    pane.add(licence,licence.getName());
    pane.add(system,system.getName());
    pane.add(contributions,contributions.getName());
    pane.setEnabledAt(0,true);
    pane.setEnabledAt(1,true);
    pane.setEnabledAt(2,false);
    pane.setEnabledAt(3,false);
    
    cpane.add(pane,BorderLayout.CENTER);
    cpane.add(bottom,BorderLayout.SOUTH);
    
    
    String pkg = "chess/";
    File file = new File(pkg+"GNU.htm");
    String str = null;
    try {
      str = loadFileInput(file);
      licenceArea.setText(str);
      licenceArea.setCaretPosition(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  class AboutLabel extends JLabel {
    Image largeLogo;
    Image logo;
    Font font = new Font("Serif", Font.PLAIN, 12);
    Font font2 = new Font("Serif", Font.BOLD, 12);
    public AboutLabel() {
      setPreferredSize(new Dimension(450,270));
      logo = BoardMedia.getLogo();
      largeLogo = BoardMedia.getLargeLogo();
    }
    public void paint(Graphics g) {
      super.paint(g);
      
      // Text metrics
      FontMetrics metrics = g.getFontMetrics();
      Rectangle2D rect;
      
      // Draw white background
      g.setColor(Color.WHITE);
      g.fillRect(0,0,getWidth(),getHeight());
      
      int width = largeLogo.getWidth(null);
      int height = largeLogo.getHeight(null);
      
      g.drawImage(largeLogo,0,0,null);
      
      g.setColor(Color.LIGHT_GRAY);
      
      // large logo border
      g.drawRect(0,0,width-1,height-1);
      
      g.drawImage(logo, 
          getWidth()-logo.getWidth(null),
          getHeight()-logo.getHeight(null),
          null);
      
      
      //----------------- Draw Text -------------------------------------------
      
      g.setFont(font2);
      rect = metrics.getStringBounds("Version:", g);
      int hei = (int)(rect.getHeight());
      
      // Set the antialiasing value
      ((Graphics2D)g).setRenderingHint(
          RenderingHints.KEY_ANTIALIASING, 
          RenderingHints.VALUE_ANTIALIAS_ON);
      
      // text
      int fromY = 20;
      int hSpace = 5; // in between space
      
      g.setColor(new Color(2,107,236));
      g.drawString("Version:"     ,width,fromY+hei*0+hSpace*0);
      g.drawString("Author:"      ,width,fromY+hei*2+hSpace*1);
      g.drawString("Last Edited:",width,fromY+hei*4+hSpace*2);
      
      g.setColor(new Color(140,140,140));
      g.setFont(font);
      g.drawString(VER,width+10,fromY+hei*1+hSpace*0);
      g.drawString(AUT,width+10,fromY+hei*3+hSpace*1);
      g.drawString(DAT,width+10,fromY+hei*5+hSpace*2);
      
    }
  }
  
  private String loadFileInput (File f) throws Exception {
    FileInputStream fstream = new FileInputStream(f);

    int buffersize = (int)f.length();
    byte[] contents = new byte[buffersize];;

    long n = fstream.read(contents, 0, buffersize);
    fstream.close();
    fstream = null;

    if (n != f.length())
       System.out.println("Error in reading file ");
    else
       return (new String(contents));
    return null;
  }

  public void actionPerformed(ActionEvent e) {
    // Exit the window
    dispose();  
  }
}