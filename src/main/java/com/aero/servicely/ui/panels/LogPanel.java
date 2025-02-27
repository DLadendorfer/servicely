package com.aero.servicely.ui.panels;

import com.aero.servicely.core.utils.LogPanelAppender;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class LogPanel extends JPanel {
  private final JTextPane logPane;
  private final StyledDocument doc;

  public LogPanel() {
    setLayout(new BorderLayout());

    logPane = new JTextPane();
    logPane.setEditable(false);
    logPane.setFont(new Font("Consolas", Font.PLAIN, 12));

    doc = logPane.getStyledDocument();
    addStyles();

    JScrollPane scrollPane = new JScrollPane(logPane);
    add(scrollPane, BorderLayout.CENTER);

    // Register Log4j2 custom appender
    registerAppender();
  }

  private void addStyles() {
    addStyle("ERROR",  new Color(182, 51, 51));
    addStyle("WARN", new Color(182, 51, 51));
    addStyle("INFO", getForeground());
    addStyle("DEBUG", Color.BLUE);
    addStyle("TRACE", Color.GRAY);
  }

  private void addStyle(String level, Color color) {
    Style style = doc.addStyle(level, null);
    StyleConstants.setForeground(style, color);
  }

  private void registerAppender() {
    LogPanelAppender appender = new LogPanelAppender("GUI", null, null);
    LogPanelAppender.setLogPane(logPane);

    Logger rootLogger = (Logger) LogManager.getRootLogger();
    Configuration config = rootLogger.getContext().getConfiguration();
    LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

    loggerConfig.addAppender(appender, null, null);
    Configurator.reconfigure();
  }

}
