package com.aero.servicely

import com.aero.servicely.Application.runApplication

/**
 * The main entry point of the application.
 *
 * This object contains the {@code main} method, which starts the application by invoking
 * {@link Application#runApplication()}.
 *
 */
object Main {

    /**
     * The application's main method.
     *
     * This method delegates execution to {@link Application#runApplication()},
     * ensuring proper initialization and launch of the user interface.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    @JvmStatic
    fun main(args: Array<String>) = runApplication()
}
