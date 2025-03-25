// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.utils

import com.aero.servicely.ui.UiConstants
import com.aero.servicely.ui.components.RoundedPanel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * The static factory class is utilized to generate UI "tiles," which consist of a header component
 * and a content component. These tiles are intended to serve as the primary components of the UI,
 * with the objective of ensuring a consistent visual and functional style across the entirety of
 * the application.
 *
 * @author Daniel Ladendorfer
 */
object TileFactory {
    private const val MARGIN_IN_PX = 4

    /**
     * Creates a tile panel with a rounded border, a title header with a separator, and a content
     * component.
     *
     * @param title The title of the tile.
     * @param content The component to be placed inside the tile.
     * @return A JPanel styled as a tile.
     */
    @JvmStatic
    fun createTile(title: String, content: JComponent): JPanel {
        // create panels
        val outerPanel = createMarginPanel()
        val tilePanel = createTilePanel()
        val headerPanel = createHeaderPanel(title)
        val contentWrapper = createContentPanel(content)

        // Assemble tile
        tilePanel.add(headerPanel, BorderLayout.NORTH)
        tilePanel.add(contentWrapper, BorderLayout.CENTER)

        // Wrap the tile in an outer panel to apply margins
        outerPanel.add(tilePanel, BorderLayout.CENTER)
        outerPanel.isOpaque = true
        return outerPanel
    }

    private fun createMarginPanel(): JPanel = JPanel(BorderLayout()).apply {
        isOpaque = false
        border = BorderFactory.createEmptyBorder(
            MARGIN_IN_PX, MARGIN_IN_PX, MARGIN_IN_PX, MARGIN_IN_PX
        )
    }

    private fun createTilePanel(): RoundedPanel = RoundedPanel().apply {
        layout = BorderLayout()
        background = Color(38, 38, 38)
        foreground = Color(144, 144, 144)
    }

    private fun createContentPanel(content: JComponent): JPanel =
        createMarginPanel().apply { add(content, BorderLayout.CENTER) }

    private fun createHeaderPanel(title: String): JPanel {
        val titleLabel = JLabel(title).apply {
            font = UiConstants.HEADER_FONT
        }

        return createContentPanel(titleLabel)
    }
}
