package com.aero.servicely;

import com.aero.servicely.core.os.invoker.WindowsServiceProvider;
import com.aero.servicely.data.win.services.WindowsServiceInfo;
import com.aero.servicely.ui.components.renderers.ServiceStatusCellRenderer;
import com.aero.servicely.ui.utils.IconButtonFactory;
import com.aero.servicely.ui.utils.TileFactory;
import com.github.weisj.darklaf.components.text.SearchTextField;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import org.jetbrains.annotations.NotNull;

public class ServicelyFrame extends JFrame {

  public ServicelyFrame() {
    var windowsServiceProvider = new WindowsServiceProvider();
    var services = windowsServiceProvider.fetchCurrentServices();
    setTitle("Servicely - Service Manager");
    setSize(1200, 800);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    var table = createMainTable(services);
    var searchBar = createSearchBar(table);

    var tablePanel = new JPanel(new BorderLayout());
    tablePanel.add(searchBar, BorderLayout.NORTH);
    tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
    add(TileFactory.createTile("Services", tablePanel), BorderLayout.CENTER);
  }

  @NotNull
  private static SearchTextField createSearchBar(JTable table) {
    var searchField = new SearchTextField();
    searchField.setPreferredSize(new Dimension(200, 30));
    searchField.addKeyListener(
        new java.awt.event.KeyAdapter() {
          @Override
          public void keyReleased(java.awt.event.KeyEvent e) {
            String text = searchField.getText();
            ((TableRowSorter<?>) table.getRowSorter())
                .setRowFilter(text.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
          }
        });
    return searchField;
  }

  @NotNull
  private static JTable createMainTable(java.util.List<WindowsServiceInfo> services) {
    // Column names for the table
    String[] columnNames = {"Display Name", "Name", "Status", "Start Type", "Actions"};

    // Convert List<ServiceInfo> to Object[][] for JTable
    Object[][] data = new Object[services.size()][5];
    for (int i = 0; i < services.size(); i++) {
      WindowsServiceInfo service = services.get(i);
      data[i][0] = service.displayName();
      data[i][1] = service.internalName();
      data[i][2] =
          switch (Integer.parseInt(service.currentStatus())) {
            case 1 -> "Stopped";
            case 2 -> "Starting";
            case 3 -> "Stopping";
            case 4 -> "Running";
            case 5 -> "Paused";
            case 6 -> "Waiting for event";
            default -> "Unknown";
          };
      data[i][3] =
          switch (Integer.parseInt(service.startType())) {
            case 0 -> "Disabled.";
            case 1 -> "Manual";
            case 2 -> "Automatic";
            case 3 -> "Automatic (delayed)";
            default -> "Unknown";
          };
      data[i][4] = "Actions"; // Placeholder
    }

    DefaultTableModel model =
        new DefaultTableModel(data, columnNames) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return false;
          }
        };

    JTable table = new JTable(model);
    table.setRowSorter(new TableRowSorter<>(model));
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));

    // Increase the row height by 1.5x the default height
    table.setRowHeight(table.getRowHeight() * 3 / 2); // 1.5x the default row height

    table.setRowSelectionAllowed(false);

    table.getColumnModel().getColumn(2).setCellRenderer(new ServiceStatusCellRenderer());
    table.getColumnModel().getColumn(2).setWidth(100);
    table.getColumnModel().getColumn(2).setMinWidth(100);
    table.getColumnModel().getColumn(2).setMaxWidth(100);
    table.getColumnModel().getColumn(3).setWidth(150);
    table.getColumnModel().getColumn(3).setMinWidth(150);
    table.getColumnModel().getColumn(3).setMaxWidth(150);
    table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
    table.getColumnModel().getColumn(4).setWidth(80);
    table.getColumnModel().getColumn(4).setMinWidth(80);
    table.getColumnModel().getColumn(4).setMaxWidth(80);
    return table;
  }

  /**
   * Custom TableCellRenderer to display circular buttons in the "Actions" column with desaturated
   * colors.
   */
  private static class ButtonRenderer extends JPanel implements TableCellRenderer {
    public ButtonRenderer() {
      setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

      var startButton = IconButtonFactory.create(IconButtonFactory.ButtonIcon.PLAY);
      var pauseButton = IconButtonFactory.create(IconButtonFactory.ButtonIcon.PAUSE);
      var stopButton = IconButtonFactory.create(IconButtonFactory.ButtonIcon.STOP);

      add(startButton);
      startButton.addActionListener(e -> System.out.println("start"));
      add(pauseButton);
      add(stopButton);
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      return this;
    }
  }
}
