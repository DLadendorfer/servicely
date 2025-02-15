// -------------------------------------------------------------------------------
// Copyright (c) Ladendorfer Daniel.
// All Rights Reserved.  See LICENSE in the project root for license information.
// -------------------------------------------------------------------------------
package com.aero.servicely.ui.utils

import com.aero.servicely.ui.UiConstants
import com.aero.servicely.ui.components.RoundedPanel
import java.awt.BorderLayout
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
    private const val MARGIN_IN_PX = 10

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

        return outerPanel
    }

    private fun createMarginPanel(): JPanel {
        val outerPanel = JPanel(BorderLayout())
        outerPanel.isOpaque = false
        outerPanel.border = BorderFactory.createEmptyBorder(
            MARGIN_IN_PX, MARGIN_IN_PX, MARGIN_IN_PX, MARGIN_IN_PX
        )
        return outerPanel
    }

    private fun createTilePanel(): RoundedPanel {
        val tilePanel = RoundedPanel()
        tilePanel.layout = BorderLayout()
        tilePanel.background = tilePanel.background.brighter()
        return tilePanel
    }

    private fun createContentPanel(content: JComponent): JPanel {
        val contentWrapper = createMarginPanel()
        contentWrapper.add(content, BorderLayout.CENTER)
        return contentWrapper
    }

    private fun createHeaderPanel(title: String): JPanel {
        val titleLabel = JLabel(title)
        titleLabel.font = UiConstants.HEADER_FONT

        val headerPanel = createContentPanel(titleLabel)
        headerPanel.background = headerPanel.background.brighter()
        return headerPanel
    }
}
