// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.core.os.invoker;

import com.aero.servicely.data.win.services.ServiceInfo;
import java.util.List;
import java.util.Optional;

/**
 * Defines the internal API to fetch the current snapshot of windows' services.
 *
 * @author Daniel Ladendorfer
 */
public interface IOsDependentServiceProvider
{

  /**
   * Returns a list of all currently running Windows services.
   *
   * @return the list of service infos
   */
  List<ServiceInfo> fetchCurrentServices();

  /**
   * Returns {@link ServiceInfo information} about the specified service.
   *
   * @param internalName the internal service name
   * @return the specified service or {@link Optional#empty()} if null
   */
  Optional<ServiceInfo> fetchService(String internalName);
}
