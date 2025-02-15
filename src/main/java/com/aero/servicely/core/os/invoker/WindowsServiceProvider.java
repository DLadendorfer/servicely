// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.core.os.invoker;

import com.aero.servicely.data.win.services.WindowsServiceInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import lombok.SneakyThrows;

/**
 * Implementation of the Windows service provider interface.
 *
 * @author Daniel Ladendorfer
 */
public class WindowsServiceProvider implements IWindowsServiceProvider {

  private static final Gson GSON = new Gson();
  private static final Type WINDOWS_SERVICE_INFO_LIST =
      new TypeToken<List<WindowsServiceInfo>>() {}.getType();
  private static final String PS_CMD = "powershell.exe \"%s\"";
  public static final String PS_GET_SERVICES = "Get-Service | ConvertTo-Json";

  @Override
  public List<WindowsServiceInfo> fetchCurrentServices() {
    return GSON.fromJson(invoke(PS_GET_SERVICES), WINDOWS_SERVICE_INFO_LIST);
  }

  @SneakyThrows
  private String invoke(String psCommand) {
    String command = PS_CMD.formatted(psCommand);

    // Initialize ProcessBuilder to execute PowerShell command
    var processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
    processBuilder.redirectErrorStream(true);
    var process = processBuilder.start();

    // Capture the output
    var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    var output = new StringBuilder();
    String line;

    // Read each line of the output
    while ((line = reader.readLine()) != null) {
      output.append(line).append(System.lineSeparator());
    }

    // Wait for the process to complete
    process.waitFor();

    return output.toString();
  }
}
