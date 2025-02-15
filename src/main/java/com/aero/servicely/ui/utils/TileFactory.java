// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.utils;

import com.aero.servicely.ui.UiConstants;
import com.aero.servicely.ui.components.RoundedPanel;
import java.awt.*;
import javax.swing.*;

/**
 * The static factory class is utilized to generate UI "tiles," which consist of a header component
 * and a content component. These tiles are intended to serve as the primary components of the UI,
 * with the objective of ensuring a consistent visual and functional style across the entirety of
 * the application.
 *
 * @author Daniel Ladendorfer
 */
public class TileFactory {

  private static final int MARGIN_IN_PX = 10;

  private TileFactory() {
    // static factory class
  }

  /**
   * Creates a tile panel with a rounded border, a title header with a separator, and a content
   * component.
   *
   * @param title The title of the tile.
   * @param content The component to be placed inside the tile.
   * @return A JPanel styled as a tile.
   */
  public static JPanel createTile(String title, JComponent content) {
    // create panels
    var outerPanel = createMarginPanel();
    var tilePanel = createTilePanel();
    var headerPanel = createHeaderPanel(title);
    var contentWrapper = createContentPanel(content);

    // Assemble tile
    tilePanel.add(headerPanel, BorderLayout.NORTH);
    tilePanel.add(contentWrapper, BorderLayout.CENTER);

    // Wrap the tile in an outer panel to apply margins
    outerPanel.add(tilePanel, BorderLayout.CENTER);

    return outerPanel;
  }

  private static JPanel createMarginPanel() {
    var outerPanel = new JPanel(new BorderLayout());
    outerPanel.setOpaque(false);
    outerPanel.setBorder(
        BorderFactory.createEmptyBorder(MARGIN_IN_PX, MARGIN_IN_PX, MARGIN_IN_PX, MARGIN_IN_PX));
    return outerPanel;
  }

  private static RoundedPanel createTilePanel() {
    var tilePanel = new RoundedPanel();
    tilePanel.setLayout(new BorderLayout());
    tilePanel.setBackground(tilePanel.getBackground().brighter());
    return tilePanel;
  }

  private static JPanel createContentPanel(JComponent content) {
    var contentWrapper = createMarginPanel();
    contentWrapper.add(content, BorderLayout.CENTER);
    return contentWrapper;
  }

  private static JPanel createHeaderPanel(String title) {
    var titleLabel = new JLabel(title);
    titleLabel.setFont(UiConstants.HEADER_FONT);

    var headerPanel = createContentPanel(titleLabel);
    headerPanel.setBackground(headerPanel.getBackground().brighter());
    return headerPanel;
  }
}
