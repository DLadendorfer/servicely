package com.aero.servicely.core.utils;

import java.awt.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.text.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "GUI", category = "Core", elementType = "appender", printObject = true)
public class LogPanelAppender extends AbstractAppender {
  private static JTextPane logPane;
  private static StyledDocument doc;

  public LogPanelAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
    super(name, filter, layout, true);
  }

  public static void setLogPane(JTextPane textPane) {
    logPane = textPane;
    doc = textPane.getStyledDocument();
  }

  @PluginFactory
  public static LogPanelAppender createAppender(
      @PluginAttribute("name") final String name,
      @PluginElement("Filters") final Filter filter,
      @PluginElement("Layout") Layout<?> layout) {

    if (name == null) {
      LOGGER.error("No name provided for StubAppender");
      return null;
    }

    if (layout == null) {
      layout = PatternLayout.createDefaultLayout();
    }
    return new LogPanelAppender(name, filter, layout);
  }

  @Override
  public void append(LogEvent event) {
    if (logPane == null) return;

    SwingUtilities.invokeLater(
        () -> {
          try {
            String level = event.getLevel().name();
            if (getLayout() != null) {
              String message = new String(getLayout().toByteArray(event));

              Style style = doc.getStyle(level);
              if (style == null) {
                style = doc.addStyle(level, null);
                StyleConstants.setForeground(style, logPane.getForeground());
              }

              doc.insertString(doc.getLength(), message, style);
              logPane.setCaretPosition(doc.getLength());
            }
          } catch (BadLocationException e) {
            e.printStackTrace();
          }
        });
  }
}
