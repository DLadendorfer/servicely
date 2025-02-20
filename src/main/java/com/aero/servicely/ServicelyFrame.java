package com.aero.servicely;

import com.aero.servicely.core.os.invoker.WindowsServiceProvider;
import com.aero.servicely.data.win.services.WindowsServiceInfo;
import com.aero.servicely.ui.components.renderers.ServiceStatusCellRenderer;
import com.aero.servicely.ui.utils.IconButtonFactory;
import com.aero.servicely.ui.utils.TileFactory;
import com.github.weisj.darklaf.components.text.SearchTextField;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class ServicelyFrame extends JFrame {

  private final transient WindowsServiceProvider provider;
  private JPanel serviceInfoPanel = null;

  public ServicelyFrame() {
    provider = new WindowsServiceProvider();
    var services = provider.fetchCurrentServices();
    setTitle("Servicely - Service Manager");
    setSize(1200, 800);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    getContentPane().setBackground(new Color(43, 43, 43));

    var table = createMainTable(services);
    var searchBar = createSearchBar(table);

    var tablePanel = new JPanel(new BorderLayout());
    tablePanel.add(searchBar, BorderLayout.NORTH);
    tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
    add(TileFactory.createTile("Services", tablePanel), BorderLayout.CENTER);
  }

  @SneakyThrows
  private void createServiceInfoPanel(String internalName) {
    if (serviceInfoPanel != null) {
      remove(serviceInfoPanel);
    }
    var panel = new JPanel();

    panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
    panel.setPreferredSize(new Dimension(500, panel.getPreferredSize().height));
    panel.setMaximumSize(new Dimension(500, panel.getPreferredSize().height));

    var startButton = IconButtonFactory.create(IconButtonFactory.ButtonIcon.PLAY);
    var pauseButton = IconButtonFactory.create(IconButtonFactory.ButtonIcon.PAUSE);
    var stopButton = IconButtonFactory.create(IconButtonFactory.ButtonIcon.STOP);
    startButton.addActionListener(e -> System.out.println("start"));
    pauseButton.addActionListener(e -> System.out.println("pause"));
    stopButton.addActionListener(e -> System.out.println("stop"));

    panel.add(new JLabel(provider.fetchService(internalName).get().displayName()));
    panel.add(startButton);
    panel.add(pauseButton);
    panel.add(stopButton);

    serviceInfoPanel = TileFactory.createTile("Service Information", new JScrollPane(panel));
    add(serviceInfoPanel, BorderLayout.EAST);
    revalidate();
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
  private JTable createMainTable(List<WindowsServiceInfo> services) {
    // Column names for the table
    String[] columnNames = {"Display Name", "Status", "Start Type", "Internal Name"};

    // Convert List<ServiceInfo> to Object[][] for JTable
    Object[][] data = new Object[services.size()][5];
    for (int i = 0; i < services.size(); i++) {
      WindowsServiceInfo service = services.get(i);
      data[i][0] = service.displayName();
      data[i][1] =
          switch (Integer.parseInt(service.currentStatus())) {
            case 1 -> "Stopped";
            case 2 -> "Starting";
            case 3 -> "Stopping";
            case 4 -> "Running";
            case 5 -> "Paused";
            case 6 -> "Waiting for event";
            default -> "Unknown";
          };
      data[i][2] =
          switch (Integer.parseInt(service.startType())) {
            case 0 -> "Disabled.";
            case 1 -> "Manual";
            case 2 -> "Automatic";
            case 3 -> "Automatic (delayed)";
            default -> "Unknown";
          };
      data[i][3] = service.internalName();
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

    table.getColumnModel().getColumn(1).setCellRenderer(new ServiceStatusCellRenderer());
    table.getColumnModel().getColumn(1).setWidth(100);
    table.getColumnModel().getColumn(1).setMinWidth(100);
    table.getColumnModel().getColumn(1).setMaxWidth(100);
    table.getColumnModel().getColumn(2).setWidth(150);
    table.getColumnModel().getColumn(2).setMinWidth(150);
    table.getColumnModel().getColumn(2).setMaxWidth(150);
    table.getColumnModel().getColumn(3).setMinWidth(0);
    table.getColumnModel().getColumn(3).setMaxWidth(0);
    table.getColumnModel().getColumn(3).setWidth(0);

    table
        .getSelectionModel()
        .addListSelectionListener(
            e ->
                SwingUtilities.invokeLater(
                    () ->
                        createServiceInfoPanel(
                            (String) table.getModel().getValueAt(e.getLastIndex(), 3))));

    return table;
  }
}
