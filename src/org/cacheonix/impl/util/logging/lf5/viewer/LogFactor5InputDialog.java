/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cacheonix.impl.util.logging.lf5.viewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * LogFactor5InputDialog
 * <p/>
 * Creates a popup input dialog box so that users can enter a URL to open a log file from.
 *
 * @author Richard Hurst
 * @author Brad Marlborough
 */

// Contributed by ThoughtWorks Inc.

public final class LogFactor5InputDialog extends LogFactor5Dialog {

   private static final long serialVersionUID = -6388919243313911235L;

   //--------------------------------------------------------------------------
   //   Constants:
   //--------------------------------------------------------------------------
   public static final int SIZE = 30;
   //--------------------------------------------------------------------------
   //   Protected Variables:
   //--------------------------------------------------------------------------

   //--------------------------------------------------------------------------
   //   Private Variables:
   //--------------------------------------------------------------------------
   private final JTextField _textField;
   //--------------------------------------------------------------------------
   //   Constructors:
   //--------------------------------------------------------------------------


   /**
    * Configures an input dialog box using a defualt size for the text field. param jframe the frame where the dialog
    * will be loaded from. param title the title of the dialog box. param label the label to be put in the dialog box.
    */
   public LogFactor5InputDialog(final JFrame jframe, final String title, final String label) {
      this(jframe, title, label, SIZE);
   }


   /**
    * Configures an input dialog box. param jframe the frame where the dialog will be loaded from. param title the title
    * of the dialog box. param label the label to be put in the dialog box. param size the size of the text field.
    */
   public LogFactor5InputDialog(final JFrame jframe, final String title, final String label,
                                final int size) {
      super(jframe, title, true);

      final JPanel bottom = new JPanel();
      bottom.setLayout(new FlowLayout());

      final JPanel main = new JPanel();
      main.setLayout(new FlowLayout());
      main.add(new JLabel(label));
      _textField = new JTextField(size);
      main.add(_textField);

      addKeyListener(new KeyAdapter() {
         public void keyPressed(final KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
               hide();
            }
         }
      });

      final JButton ok = new JButton("Ok");
      ok.addActionListener(new ActionListener() {
         public void actionPerformed(final ActionEvent e) {
            hide();
         }
      });

      final JButton cancel = new JButton("Cancel");
      cancel.addActionListener(new ActionListener() {
         public void actionPerformed(final ActionEvent e) {
            hide();
            // set the text field to blank just in case
            // a file was selected before the Cancel
            // button was pressed.
            _textField.setText("");
         }
      });

      bottom.add(ok);
      bottom.add(cancel);
      getContentPane().add(main, BorderLayout.CENTER);
      getContentPane().add(bottom, BorderLayout.SOUTH);
      pack();
      centerWindow(this);
      show();
   }


   //--------------------------------------------------------------------------
   //   Public Methods:
   //--------------------------------------------------------------------------
   public final String getText() {
      final String s = _textField.getText();

      if (s != null && s.trim().isEmpty()) {
         return null;
      }

      return s;

   }

   //--------------------------------------------------------------------------
   //   Protected Methods:
   //--------------------------------------------------------------------------

   //--------------------------------------------------------------------------
   //   Private Methods:
   //--------------------------------------------------------------------------

   //--------------------------------------------------------------------------
   //   Nested Top-Level Classes or Interfaces
   //--------------------------------------------------------------------------
}