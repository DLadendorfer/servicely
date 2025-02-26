package com.aero.servicely.ui.components.renderers;

import com.aero.servicely.ui.components.Badge;
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
    var panel = new JPanel();
    var badge = new Badge(String.valueOf(value));
    panel.add(badge);

    return panel;
  }
}
