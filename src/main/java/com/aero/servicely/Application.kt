// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely

import com.aero.servicely.ui.UiConstants
import javax.swing.SwingUtilities

/**
 * Entry point for launching the application.
 * This object initializes UI constants and ensures the graphical user interface
 * is started on the Event Dispatch Thread (EDT) for thread safety.
 *
 * Usage:
 *
 *      Application.runApplication()
 *
 * @author Daniel Ladendorfer
 */
object Application {

    /**
     * Starts the application by applying UI configurations and invoking the main UI.
     * This method ensures that UI constants are set up before launching the graphical interface.
     */
    fun runApplication() {
        setupApp()
        invokeApp()
    }

    /**
     * Applies global UI constants for consistent styling across the application.
     */
    private fun setupApp() {
        UiConstants.apply { }
    }

    /**
     * Launches the Swing UI on the Event Dispatch Thread (EDT).
     * This ensures that the UI is initialized and updated in a thread-safe manner.
     */
    private fun invokeApp() {
        SwingUtilities.invokeLater { ServicelyFrame().isVisible = true }
    }
}
