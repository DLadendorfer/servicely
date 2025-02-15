// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.utils;

import com.aero.servicely.ui.components.RoundedPanel;
import java.awt.*;
import javax.swing.*;

public class TileFactory {

  /**
   * Creates a tile panel with a rounded border, a title header with a separator, and a content
   * component.
   *
   * @param title The title of the tile.
   * @param content The component to be placed inside the tile.
   * @return A JPanel styled as a tile.
   */
  public static JPanel createTile(String title, JComponent content) {
    JPanel outerPanel = new JPanel(new BorderLayout()); // Wrapper panel to handle margins
    outerPanel.setOpaque(false);
    outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margin of 10px

    JPanel tilePanel = new RoundedPanel(); // Rounded corners with 20px radius
    tilePanel.setLayout(new BorderLayout());
    tilePanel.setBackground(tilePanel.getBackground().brighter());

    // Header panel
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setOpaque(false);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    headerPanel.add(titleLabel, BorderLayout.CENTER);
    headerPanel.setBackground(headerPanel.getBackground().brighter());
    headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    headerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));

    // Separator line

    // Content panel
    JPanel contentWrapper = new JPanel(new BorderLayout());
    contentWrapper.setOpaque(false);
    contentWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    contentWrapper.add(content, BorderLayout.CENTER);

    // Assemble tile
    tilePanel.add(headerPanel, BorderLayout.NORTH);
    tilePanel.add(contentWrapper, BorderLayout.CENTER);

    // Wrap the tile in an outer panel to apply margins
    outerPanel.add(tilePanel, BorderLayout.CENTER);

    return outerPanel;
  }
}
