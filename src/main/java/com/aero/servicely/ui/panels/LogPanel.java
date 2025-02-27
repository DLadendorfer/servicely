package com.aero.servicely.ui.panels;

import com.aero.servicely.ui.UiConstants;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class LogPanel extends JPanel {
  private final JTextPane logPane;
  private final StyledDocument doc;

  public LogPanel() {
    setLayout(new BorderLayout());

    logPane = new JTextPane();
    logPane.setPreferredSize(new Dimension(getPreferredSize().width, 150));
    logPane.setEditable(false);
    logPane.setFont(UiConstants.INSTANCE.getDEFAULT_FONT());

    doc = logPane.getStyledDocument();
    addStyles();

    JScrollPane scrollPane = new JScrollPane(logPane);
    add(scrollPane, BorderLayout.CENTER);
  }

  private void addStyles() {
    addStyle("ERROR", new Color(182, 51, 51));
    addStyle("WARN", new Color(182, 51, 51));
    addStyle("INFO", getForeground());
    addStyle("DEBUG", Color.BLUE);
    addStyle("TRACE", Color.GRAY);
  }

  private void addStyle(String level, Color color) {
    Style style = doc.addStyle(level, null);
    StyleConstants.setForeground(style, color);
  }
}
