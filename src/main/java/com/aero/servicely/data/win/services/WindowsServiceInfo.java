// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.data.win.services;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents information about a Windows service, including its name, status, dependencies, and
 * capabilities. This record is designed for serialization and deserialization using Gson.
 *
 * <p>Each field is annotated with {@link SerializedName} to map JSON properties to their
 * corresponding Java fields.
 *
 * @param internalName The internal name of the service.
 * @param displayName The human-readable display name of the service.
 * @param currentStatus The current status of the service (e.g., "Running", "Stopped").
 * @param dependentServices A list of services that depend on this service.
 * @param servicesDependedOn A list of services that this service depends on.
 * @param serviceType The type of service (e.g., "Win32OwnProcess", "Win32ShareProcess").
 * @param startType The startup type of the service (e.g., "Automatic", "Manual", "Disabled").
 * @param canPauseAndContinue Indicates whether the service supports pausing and resuming.
 * @param canShutdown Indicates whether the service can be notified of system shutdown.
 * @param canStop Indicates whether the service can be stopped manually.
 * @author Daniel Ladendorfer
 */
public record WindowsServiceInfo(
    @SerializedName("Name") String internalName,
    @SerializedName("DisplayName") String displayName,
    @SerializedName("Status") String currentStatus,
    @SerializedName("DependentServices") List<String> dependentServices,
    @SerializedName("ServicesDependedOn") List<String> servicesDependedOn,
    @SerializedName("ServiceType") String serviceType,
    @SerializedName("StartType") String startType,
    @SerializedName("CanPauseAndContinue") boolean canPauseAndContinue,
    @SerializedName("CanShutdown") boolean canShutdown,
    @SerializedName("CanStop") boolean canStop) {}
