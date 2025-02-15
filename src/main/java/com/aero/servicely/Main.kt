package com.aero.servicely

import com.aero.servicely.core.os.invoker.WindowsServiceProvider
import com.github.weisj.darklaf.LafManager
import com.github.weisj.darklaf.theme.OneDarkTheme
import javax.swing.SwingUtilities

object Main {
    init {
        LafManager.install(OneDarkTheme())
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val windowsServiceProvider = WindowsServiceProvider()
        val services = windowsServiceProvider.fetchCurrentServices()

        // Run Swing UI on Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater { ServiceTableApp(services).isVisible = true }
    }
}
