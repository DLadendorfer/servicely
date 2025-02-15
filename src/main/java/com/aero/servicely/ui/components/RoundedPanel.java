// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.components;

import java.awt.*;
import javax.swing.*;

/**
 * A simple {@link JPanel} variant, which has rounded corners.
 *
 * @author Daniel Ladendorfer
 */
public class RoundedPanel extends JPanel {
  private static final int CORNER_RADIUS = 20;

  public RoundedPanel() {
    // Allows transparency, which is necessary to paint the component 'beneath' the clipped corners
    setOpaque(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Instead of clipping the panel, the panel background gets redrawn above the transparent
    // background
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);

    g2.dispose();
    super.paintComponent(g);
  }
}
