package com.aero.servicely.ui.utils;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * A utility class for creating {@link JButton} instances with a scaled icon. This class provides a
 * static method to create a button with an icon loaded from a resource path. The icon is
 * automatically scaled to a specified size (20x20 pixels).
 *
 * <p>Example usage:
 *
 * <pre>
 *     JButton button = IconButtonFactory.create("/path/to/icon.png");
 * </pre>
 */
public class IconButtonFactory {

  @Getter
  public enum ButtonIcon {
    PLAY("/icons/play.png"),
    PAUSE("/icons/pause.png"),
    STOP("/icons/stop.png");

    private final String path;

    ButtonIcon(String path) {
      this.path = path;
    }
  }

  /**
   * Creates a {@link JButton} with an icon loaded from the specified resource path. The icon is
   * scaled to 20x20 pixels before being applied to the button.
   *
   * @param buttonIcon defines which type of icon should be loaded
   * @return a {@link JButton} with the scaled icon
   * @throws NullPointerException if the resource could not be found or is null
   * @throws IllegalArgumentException if the resource is not a valid image
   */
  public static JButton create(ButtonIcon buttonIcon) {
    // Load the icon from the specified resource path
    String path = buttonIcon.getPath();

    if (StringUtils.isBlank(path)) {
      throw new IllegalArgumentException("ButtonIcon path cannot be empty");
    }

    URL resource = IconButtonFactory.class.getResource(path);

    if (resource == null) {
      throw new IllegalArgumentException("ButtonIcon '%s' not found".formatted(path));
    }

    var icon = new ImageIcon(resource);

    // Check if the icon was loaded successfully
    if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
      throw new IllegalArgumentException(
          "The resource at %s is not a valid image.".formatted(path));
    }

    // Scale the image to 20x20 pixels
    var scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

    // Create a button with the scaled icon and return it
    return new JButton(new ImageIcon(scaledImage));
  }
}
