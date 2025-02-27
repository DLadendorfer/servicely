package com.aero.servicely.core.utils;

import java.awt.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.text.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;

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

  @Override
  public void append(LogEvent event) {
    if (logPane == null) return;

    SwingUtilities.invokeLater(
        () -> {
          try {
            String level = event.getLevel().name();
            String message = new String(getLayout().toByteArray(event));

            Style style = doc.getStyle(level);
            if (style == null) {
              style = doc.addStyle(level, null);
              StyleConstants.setForeground(style, getColor(event.getLevel()));
            }

            doc.insertString(doc.getLength(), message, style);
            logPane.setCaretPosition(doc.getLength());
          } catch (BadLocationException e) {
            e.printStackTrace();
          }
        });
  }

  private static Color getColor(Level level) {
    switch (level.name()) {
      case "ERROR":
        return Color.RED;
      case "WARN":
        return Color.ORANGE;
      case "INFO":
        return Color.BLACK;
      case "DEBUG":
        return Color.BLUE;
      case "TRACE":
        return Color.GRAY;
      default:
        return Color.BLACK;
    }
  }
}
