// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.core.os.invoker;

import com.aero.servicely.commands.CommandLib;
import com.aero.servicely.data.win.services.ServiceInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;

/**
 * Implementation of the Windows service provider interface.
 *
 * @author Daniel Ladendorfer
 */
public class WindowsServiceProvider implements IOsDependentServiceProvider {

  private static final Gson GSON = new Gson();

  private static final Type WINDOWS_SERVICE_INFO = new TypeToken<ServiceInfo>() {}.getType();
  private static final Type WINDOWS_SERVICE_INFO_LIST =
      new TypeToken<List<ServiceInfo>>() {}.getType();

  @Override
  public List<ServiceInfo> fetchCurrentServices() {
    return GSON.fromJson(invoke(CommandLib.GET_SERVICES), WINDOWS_SERVICE_INFO_LIST);
  }

  @Override
  public Optional<ServiceInfo> fetchService(String internalName) {
    return Optional.ofNullable(
        GSON.fromJson(invoke(CommandLib.GET_SERVICE, internalName), WINDOWS_SERVICE_INFO));
  }

  @SneakyThrows
  private String invoke(String script, String... args) {
    return PowerShellInvoker.runScript(script, args);
  }
}
