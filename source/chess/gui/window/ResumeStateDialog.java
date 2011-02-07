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
 * Created on Mar 30, 2005
 *
 */

package chess.gui.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ResumeStateDialog extends JPanel implements ActionListener{
  
  private JButton resume;
  private JButton cancel;
  private JLabel field;
  
  public ResumeStateDialog() {
    super(new BorderLayout());
    
    field = new JLabel("Do you want to resume the previous game state?");
    resume = new JButton("OKay");
    cancel = new JButton("Cancel");
    setBorder(new TitledBorder(" Menu "));
    JPanel group = new JPanel();
    group.add(resume);
    group.add(cancel);
    add(field,BorderLayout.CENTER);
    add(group,BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent e) {}
}