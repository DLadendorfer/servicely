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
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
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
  public static String runScript(String scriptPath, String... args) {
    var process = createProcess(scriptPath, args);
    return readProcessOutput(process);
  }

  @NotNull
  private static String readProcessOutput(Process process)
      throws IOException, InterruptedException {
    try (var inputStream = process.getInputStream();
        var reader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      String output = reader.lines().collect(Collectors.joining("\n"));

      // Wait for the process to complete
      process.waitFor();

      log.info(output);
      return output;
    }
  }

  @NotNull
  private static Process createProcess(String scriptPath, String[] args) throws IOException {
    log.info("{} ~ {}", scriptPath, Arrays.toString(args));
    var scriptFile = extractScriptFromResources("/scripts/ps1/%s.ps1".formatted(scriptPath));
    var processBuilder =
        new ProcessBuilder(
                "powershell", "-ExecutionPolicy", "Bypass", "-File", scriptFile.getAbsolutePath())
            .redirectErrorStream(true);
    Arrays.stream(args).forEach(processBuilder.command()::add);

    return processBuilder.start();
  }
}
