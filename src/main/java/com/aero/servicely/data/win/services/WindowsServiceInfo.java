// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.data.win.services;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record WindowsServiceInfo(
        @SerializedName("Name") String name,
        @SerializedName("DisplayName") String displayName,
        @SerializedName("Status") String status,
        @SerializedName("DependentServices") List<String> dependentServices,
        @SerializedName("ServicesDependedOn") List<String> servicesDependedOn,
        @SerializedName("ServiceType") String serviceType,
        @SerializedName("StartType") String startType,
        @SerializedName("CanPauseAndContinue") boolean canPauseAndContinue,
        @SerializedName("CanShutdown") boolean canShutdown,
        @SerializedName("CanStop") boolean canStop
) {}
