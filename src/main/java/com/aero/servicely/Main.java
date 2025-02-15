package com.aero.servicely;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    try {
      // PowerShell command to fetch Windows services as JSON
      String command = "powershell.exe \"Get-Service | ConvertTo-Json\"";

      // Initialize ProcessBuilder to execute PowerShell command
      ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
      processBuilder.redirectErrorStream(true);
      Process process = processBuilder.start();

      // Capture the output
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      StringBuilder output = new StringBuilder();
      String line;

      // Read each line of the output
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }

      // Wait for the process to complete
      process.waitFor();

      Gson gson = new Gson();

      Type listType = new TypeToken<List<ServiceInfo>>() {}.getType();
      List<ServiceInfo> services = gson.fromJson(output.toString(), listType);

      LafManager.install(new OneDarkTheme());

      // Run Swing UI on Event Dispatch Thread (EDT)
      SwingUtilities.invokeLater(() -> new ServiceTableApp(services).setVisible(true));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
