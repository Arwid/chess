/*
 * Created on May 4, 2005
 *
 */
package chess.gui.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Arvydas Bancewicz
 *
 */
public class ErrorMessage extends JDialog {

  public ErrorMessage(JFrame frame, String message) {
    super(frame,"Error",true);
    
    JTextArea textPanel = new JTextArea(message);
    JPanel buttonPanel = new JPanel(new FlowLayout());
    JButton close = new JButton("Close");
    buttonPanel.add(close);
    
    close.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        System.exit(-1);
      }
    });
    
    Component cpane = getContentPane();
	
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(textPanel, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    
    // Resize the window to the minimum size to satisfy the preferred size of
    // each of the components in the layout.
    pack();
    
    // Compute the layout. Validate effectively redoes the layout if necessary
    // deciding on new sizes and locations of all the components in the conainer.
    validate();
    
    // Position the window to the center of the screen
    setLocationRelativeTo(null);
    
    // Prevent the window from resizing
    //setResizable(false);
    
    
    setVisible(true);
  }
}
