// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.commands

/**
 * A utility object containing predefined PowerShell command templates for managing Windows services.
 *
 * This object provides a set of commands that allow querying, starting, stopping, restarting, resuming,
 * and suspending Windows services using PowerShell. The commands are designed as string templates
 * where the service name can be inserted dynamically using `String.format()`.
 *
 * ### Usage Example:
 * ```kotlin
 * val command = String.format(CommandLib.START_SERVICE, "MyService")
 * ```
 *
 * @author Daniel Ladendorfer
 */
object CommandLib {

    /**
     * Retrieves a list of all Windows services and converts the output to JSON format.
     *
     * This command is useful for fetching details about all services currently installed on the system.
     * The output can be parsed into objects for further processing.
     */
    const val GET_SERVICES: String = "Get-Service | ConvertTo-Json"

    /**
     * Retrieves information about a specific Windows service by name.
     *
     * The `%s` placeholder should be replaced with the exact service name.
     * The command filters services based on their `Name` property.
     *
     * Example:
     * ```kotlin
     * val command = String.format(CommandLib.GET_SERVICE, "Spooler")
     * ```
     */
    const val GET_SERVICE: String = "Get-Service | ? {\$_.Name -eq \"%s\"} | ConvertTo-Json"

    /**
     * Starts a Windows service by name.
     *
     * This command attempts to start the specified service if it is currently stopped.
     * If the service is already running, no action is taken.
     *
     * Example:
     * ```kotlin
     * val command = String.format(CommandLib.START_SERVICE, "wuauserv")
     * ```
     */
    const val START_SERVICE: String = "Get-Service | ? {\$_.Name -eq \"%s\"} | Start-Service"

    /**
     * Restarts a Windows service by name.
     *
     * This command first stops the specified service (if running) and then starts it again.
     * It is useful when a service needs to be refreshed due to configuration changes or issues.
     */
    const val RESTART_SERVICE: String = "Get-Service | ? {\$_.Name -eq \"%s\"} | Restart-Service"

    /**
     * Stops a Windows service by name.
     *
     * If the specified service is running, this command attempts to stop it.
     * Some services may require administrative privileges to stop.
     */
    const val STOP_SERVICE: String = "Get-Service | ? {\$_.Name -eq \"%s\"} | Stop-Service"

    /**
     * Resumes a paused Windows service by name.
     *
     * This command is applicable only to services that support pausing and resuming.
     * If the service is currently paused, it will be resumed and continue running.
     */
    const val RESUME_SERVICE: String = "Get-Service | ? {\$_.Name -eq \"%s\"} | Resume-Service"

    /**
     * Suspends a running Windows service by name.
     *
     * This command pauses the specified service if it supports suspension.
     * Pausing a service allows it to be resumed later without fully stopping it.
     */
    const val SUSPEND_SERVICE: String = "Get-Service | ? {\$_.Name -eq \"%s\"} | Suspend-Service"
}
