// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.components;

import java.awt.*;
import javax.swing.*;

/**
 * A custom {@link JPanel} implementation that features rounded corners.
 *
 * <p>This panel supports transparency and uses antialiasing to smoothly render the rounded
 * corners. The background is manually redrawn to ensure proper rendering of the clipped corners.
 *
 * <p><strong>Usage:</strong>
 *
 * <pre>
 *     RoundedPanel panel = new RoundedPanel();
 *     panel.setBackground(Color.LIGHT_GRAY);
 * </pre>
 *
 * @author Daniel Ladendorfer
 */
public class RoundedPanel extends JPanel {
  private static final int CORNER_RADIUS = 20;

  /**
   * Constructs a {@code RoundedPanel} with a default corner radius.
   *
   * <p>The panel is set to be non-opaque to allow for proper rendering of the rounded edges.
   */
  public RoundedPanel() {
    setOpaque(false);
  }

  /**
   * Paints the panel with rounded corners.
   *
   * <p>This method overrides {@link JPanel#paintComponent(Graphics)} to apply antialiasing and
   * draw a rounded rectangle as the background.
   *
   * @param g The {@link Graphics} object used for rendering.
   */
  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Draw the rounded background
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);

    g2.dispose();
    super.paintComponent(g);
  }
}
