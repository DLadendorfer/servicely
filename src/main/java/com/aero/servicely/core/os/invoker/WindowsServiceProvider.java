// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.core.os.invoker;

import com.aero.servicely.commands.CommandLib;
import com.aero.servicely.data.win.services.WindowsServiceInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;

/**
 * Implementation of the Windows service provider interface.
 *
 * @author Daniel Ladendorfer
 */
public class WindowsServiceProvider implements IWindowsServiceProvider {

  private static final Gson GSON = new Gson();
  private static final String PS_CMD = "powershell.exe \"%s\"";

  private static final Type WINDOWS_SERVICE_INFO = new TypeToken<WindowsServiceInfo>() {}.getType();
  private static final Type WINDOWS_SERVICE_INFO_LIST =
      new TypeToken<List<WindowsServiceInfo>>() {}.getType();

  @Override
  public List<WindowsServiceInfo> fetchCurrentServices() {
    return GSON.fromJson(invoke(CommandLib.GET_SERVICES), WINDOWS_SERVICE_INFO_LIST);
  }

  @Override
  public Optional<WindowsServiceInfo> fetchService(String internalName) {
    return GSON.fromJson(
        invoke(CommandLib.GET_SERVICE.formatted(internalName)), WINDOWS_SERVICE_INFO);
  }

  @SneakyThrows
  private String invoke(String psCommand) {
    String command = PS_CMD.formatted(psCommand);

    // Initialize ProcessBuilder to execute PowerShell command
    var processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
    processBuilder.redirectErrorStream(true);
    var process = processBuilder.start();

    // Capture the output
    try (var inputStream = process.getInputStream();
        var reader = new BufferedReader(new InputStreamReader(inputStream))) {
      var output = new StringBuilder();
      String line;

      // Read each line of the output
      while ((line = reader.readLine()) != null) {
        output.append(line).append(System.lineSeparator());
      }

      // Wait for the process to complete
      process.waitFor();

      return output.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
