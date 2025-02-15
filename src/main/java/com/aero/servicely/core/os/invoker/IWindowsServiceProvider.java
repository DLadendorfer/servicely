// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.core.os.invoker;

import com.aero.servicely.data.win.services.WindowsServiceInfo;
import java.util.List;

/**
 * Defines the internal API to fetch the current snapshot of windows' services.
 *
 * @author Daniel Ladendorfer
 */
public interface IWindowsServiceProvider {

  /**
   * Returns a list of all currently running Windows services.
   *
   * @return the list of service infos
   */
  List<WindowsServiceInfo> fetchCurrentServices();
}
