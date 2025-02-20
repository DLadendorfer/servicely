package com.aero.servicely.ui.components.renderers;

import com.aero.servicely.ui.UiConstants;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 * A custom TableCellRenderer to change the foreground color of the "Status" column based on text.
 */
public class ServiceStatusCellRenderer implements TableCellRenderer {
  @Override
  public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    var badge =
        new JLabel(" " + value.toString() + " ") {
          @Override
          protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Get label size and draw a rounded rectangle as background
            int arc = 8; // Corner roundness
            int width = getWidth();
            int height = getHeight();
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, width, height, arc, arc);

            super.paintComponent(g);
            g2.dispose();
          }
        };

    badge.setOpaque(false);
    badge.setFont(UiConstants.INSTANCE.getDEFAULT_FONT().deriveFont(Font.BOLD));

    // Change the foreground color based on the value
    badge.setBackground(
        switch (value.toString()) {
          case "Running" -> new Color(58, 124, 72);
          case "Stopped" -> new Color(134, 47, 47);
          default -> new Color(50, 115, 133);
        });

    var panel = new JPanel();
    panel.add(badge);

    // Return the label component
    return panel;
  }
}
