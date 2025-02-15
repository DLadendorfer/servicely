// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui

import java.awt.Font
import javax.swing.UIManager

/**
 * The following declarations specify UI constants that are to be implemented consistently across
 * the entirety of the application's user interface.
 *
 * @author Daniel Ladendorfer
 */
object UiConstants {
    val DEFAULT_FONT: Font = Font("Segoe UI", Font.PLAIN, 12)
    val HEADER_FONT: Font = Font("Segoe UI", Font.BOLD, 16)

    init {
        // Set the global font
        UIManager.put("Label.font", DEFAULT_FONT)
        UIManager.put("Button.font", DEFAULT_FONT)
        UIManager.put("Table.font", DEFAULT_FONT)
        UIManager.put("TableHeader.font", DEFAULT_FONT)
        UIManager.put("TextField.font", DEFAULT_FONT)
        UIManager.put("ComboBox.font", DEFAULT_FONT)
        UIManager.put("CheckBox.font", DEFAULT_FONT)
        UIManager.put("RadioButton.font", DEFAULT_FONT)
        UIManager.put("TabbedPane.font", DEFAULT_FONT)
    }
}
