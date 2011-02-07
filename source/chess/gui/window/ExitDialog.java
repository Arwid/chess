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

/*
 * Created on Mar 20, 2005
 *
 */
package chess.gui.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class ExitDialog extends JDialog implements ActionListener {
  
  private JLabel prompt;
  private JButton ok;
  private JButton cancel;
  
  public ExitDialog(JFrame frame) {
    super(frame,"Exit",true);
    
    // Set up the components in the window
    createGUI();
    
    // Resize the window to the minimum size to satisfy the preferred size of
    // each of the components in the layout.
    pack();
    
    // Compute the layout. Validate effectively redoes the layout if necessary
    // deciding on new sizes and locations of all the components in the conainer.
    validate();
    
    // Position the window to the center of the screen
    setLocationRelativeTo(null);
    
    // Prevent the window from resizing
    setResizable(false);
    
    Toolkit.getDefaultToolkit().beep();
  }
  
  private void createGUI() {
    prompt = new JLabel("Are you sure you want to exit?");
    JPanel center = new JPanel();
    center.add(prompt);
    
    ok = new JButton("Ok");
    ok.addActionListener(this);
    cancel = new JButton("Cancel");
    cancel.addActionListener(this);
    JPanel bottom = new JPanel();
    bottom.setBorder(new EtchedBorder());
    bottom.add(ok);
    bottom.add(cancel);
    
    JPanel whole = new JPanel(new BorderLayout());
    whole.add(center,BorderLayout.CENTER);
    whole.add(bottom,BorderLayout.SOUTH);
    
    getContentPane().add(whole);
  }
  
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()==ok) {
      System.exit(0);
    } else {
      dispose();
    }
  }

}

