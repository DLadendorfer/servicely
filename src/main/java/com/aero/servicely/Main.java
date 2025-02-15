package com.aero.servicely;

import com.aero.servicely.core.os.invoker.WindowsServiceProvider;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import javax.swing.*;

public class Main {

  static {
    LafManager.install(new OneDarkTheme());
  }

  public static void main(String[] args) {
    var windowsServiceProvider = new WindowsServiceProvider();
    var services = windowsServiceProvider.fetchCurrentServices();

    // Run Swing UI on Event Dispatch Thread (EDT)
    SwingUtilities.invokeLater(() -> new ServiceTableApp(services).setVisible(true));
  }
}
