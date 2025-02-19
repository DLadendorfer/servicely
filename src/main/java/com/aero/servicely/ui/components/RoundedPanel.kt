// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.components

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel

/**
 * A custom [JPanel] implementation that features rounded corners.
 *
 *
 * This panel supports transparency and uses antialiasing to smoothly render the rounded corners.
 * The background is manually redrawn to ensure proper rendering of the clipped corners.
 *
 * @author Daniel Ladendorfer
 */
class RoundedPanel : JPanel() {
    /**
     * Constructs a `RoundedPanel` with a default corner radius.
     *
     *
     * The panel is set to be non-opaque to allow for proper rendering of the rounded edges.
     */
    init {
        isOpaque = false
    }

    /**
     * Paints the panel with rounded corners.
     *
     *
     * This method overrides [JPanel.paintComponent] to apply antialiasing and draw
     * a rounded rectangle as the background.
     *
     * @param g The [Graphics] object used for rendering.
     */
    override fun paintComponent(g: Graphics) {
        val g2 = g.create() as Graphics2D
        g2.color = background

        with(g2) {
            setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

            // border effect
            drawRoundRect(0, 0, width - 2, height - 2, CORNER_RADIUS, CORNER_RADIUS)
            drawRoundRect(1, 1, width - 3, height - 3, CORNER_RADIUS, CORNER_RADIUS)

            // rounded top
            fillRoundRect(0, 0, width, 30, CORNER_RADIUS, CORNER_RADIUS)

            // paints over rounded bottom corners
            fillRect(0, 20, width, 25)
        }

        g2.dispose()
        super.paintComponent(g)
    }

    companion object {
        private const val CORNER_RADIUS = 8
    }
}
