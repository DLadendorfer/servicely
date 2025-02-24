package com.aero.servicely.core.os.invoker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class PowerShellInvoker {

  private static File extractScriptFromResources(String resourcePath) throws IOException {
    try (InputStream scriptStream = PowerShellInvoker.class.getResourceAsStream(resourcePath)) {
      if (scriptStream == null) {
        throw new FileNotFoundException("Resource not found: " + resourcePath);
      }

      // Create a temporary file with ".ps1" extension
      File tempScript = File.createTempFile("script", ".ps1");
      tempScript.deleteOnExit(); // Ensures the file is deleted on exit

      // Copy resource stream to the temporary file
      Files.copy(scriptStream, tempScript.toPath(), StandardCopyOption.REPLACE_EXISTING);
      return tempScript;
    }
  }

  @SneakyThrows
  public static String runScript(String scriptPath, String... args)
      throws IOException, InterruptedException {
    var scriptFile = extractScriptFromResources("/scripts/ps1/%s.ps1".formatted(scriptPath));
    ProcessBuilder processBuilder =
        new ProcessBuilder(
            "powershell", "-ExecutionPolicy", "Bypass", "-File", scriptFile.getAbsolutePath());
    processBuilder.redirectErrorStream(true);

    // Add arguments to the command
    for (String arg : args) {
      processBuilder.command().add(arg);
    }

    Process process = processBuilder.start();
    try (var inputStream = process.getInputStream();
        var reader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      String output = reader.lines().collect(Collectors.joining("\n"));

      // Wait for the process to complete
      process.waitFor();

      return output;
    }
  }
}
