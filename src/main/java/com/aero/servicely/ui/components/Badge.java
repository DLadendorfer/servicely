// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.components;

import com.aero.servicely.ui.UiConstants;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Badge extends JLabel {

  private static final List<String> GREEN_BADGES = List.of("RUNNING");
  private static final List<String> STOPPED_BADGES = List.of("STOPPED");

  public Badge(String badgeText) {
    super(" %s ".formatted(badgeText.toUpperCase()));
    setOpaque(false);
    setFont(UiConstants.INSTANCE.getDEFAULT_FONT().deriveFont(Font.BOLD, 14));
    applyBackground(badgeText);
  }

  private void applyBackground(String badgeText) {
    if (GREEN_BADGES.contains(badgeText.toUpperCase())) {
      setBackground(new Color(60, 145, 79));
    } else if (STOPPED_BADGES.contains(badgeText.toUpperCase())) {
      setBackground(new Color(157, 48, 48));
    } else {
      setBackground(new Color(51, 131, 152));
    }
  }

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
}
