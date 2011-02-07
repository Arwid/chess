/*
 * Created on Apr 19, 2005
 *
 */
package chess.gui.panels;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import chess.core.VisualClock;

public class ClockPanel extends JPanel {

  public ClockPanel() {
    VisualClock whiteClock = new VisualClock();
    VisualClock blackClock = new VisualClock();
    
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(whiteClock,BorderLayout.WEST);
    panel.add(blackClock,BorderLayout.EAST);
    
    add(panel);
  }
}
