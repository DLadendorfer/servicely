// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui

import com.github.weisj.darklaf.LafManager
import com.github.weisj.darklaf.theme.DarculaTheme
import java.awt.Font
import javax.swing.UIManager


/**
 * Defines UI-related constants to ensure consistent styling across the application's user interface.
 *
 * This object centralizes font definitions and applies them globally using {@link UIManager}.
 * It ensures that UI components maintain a uniform appearance throughout the application.
 *
 * Usage:
 *```kotlin
 *     val label = JLabel("Example")
 *     label.font = UiConstants.DEFAULT_FONT
 *```
 * @author Daniel Ladendorfer
 */
object UiConstants {

    /**
     * The default font used across standard UI components.
     */

    val DEFAULT_FONT: Font = Font("Segoe UI", Font.PLAIN, 14)

    /**
     * The font used for headers and prominent text elements.
     */
    val HEADER_FONT: Font = Font("Segoe UI", Font.BOLD, 16)

    init {
        installLookAndFeel()
        applyDefaultFont()

    }

    private fun applyDefaultFont() {
        // Apply the default font to commonly used UI components
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

    private fun installLookAndFeel() {
        LafManager.install(DarculaTheme())
    }
}
