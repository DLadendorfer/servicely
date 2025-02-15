// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui;

import java.awt.*;
import javax.swing.*;

/**
 * The following declarations specify UI constants that are to be implemented consistently across
 * the entirety of the application's user interface.
 *
 * @author Daniel Ladendorfer
 */
public class UiConstants {

  private UiConstants() {
    // constant declarations
  }

  public static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
  public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);

  static {
    // Set the global font
    UIManager.put("Label.font", DEFAULT_FONT);
    UIManager.put("Button.font", DEFAULT_FONT);
    UIManager.put("Table.font", DEFAULT_FONT);
    UIManager.put("TableHeader.font", DEFAULT_FONT);
    UIManager.put("TextField.font", DEFAULT_FONT);
    UIManager.put("ComboBox.font", DEFAULT_FONT);
    UIManager.put("CheckBox.font", DEFAULT_FONT);
    UIManager.put("RadioButton.font", DEFAULT_FONT);
    UIManager.put("TabbedPane.font", DEFAULT_FONT);
  }
}
