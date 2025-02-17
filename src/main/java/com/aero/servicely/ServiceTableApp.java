package com.aero.servicely;

import com.aero.servicely.data.win.services.WindowsServiceInfo;
import com.aero.servicely.ui.utils.TileFactory;
import com.github.weisj.darklaf.components.text.SearchTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class ServiceTableApp extends JFrame {

  public ServiceTableApp(List<WindowsServiceInfo> services) {
    setTitle("Windows Services");
    setSize(1200, 800);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Column names for the table
    String[] columnNames = {"Name", "Display Name", "Status", "Start Type", "Actions"};

    // Convert List<ServiceInfo> to Object[][] for JTable
    Object[][] data = new Object[services.size()][5];
    for (int i = 0; i < services.size(); i++) {
      WindowsServiceInfo service = services.get(i);
      data[i][0] = service.internalName();
      data[i][1] = service.displayName();
      data[i][2] = service.currentStatus();
      data[i][3] = service.startType();
      data[i][4] = "Actions"; // Placeholder
    }

    // Create table model
    DefaultTableModel model =
        new DefaultTableModel(data, columnNames) {
          @Override
          public boolean isCellEditable(int row, int column) {
            return column == 4; // Make only the actions column editable
          }
        };

    JTable table = new JTable(model);
    table.setRowSorter(new TableRowSorter<>(model));
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));

    // Increase the row height by 1.5x the default height
    table.setRowHeight(table.getRowHeight() * 3 / 2); // 1.5x the default row height

    table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
    table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());

    // Search bar
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

    var tablePanel = new JPanel(new BorderLayout());
    tablePanel.add(searchField, BorderLayout.NORTH);
    tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
    add(TileFactory.createTile("Services", tablePanel), BorderLayout.CENTER);
  }

  /**
   * Custom TableCellRenderer to display circular buttons in the "Actions" column with desaturated
   * colors.
   */
  private static class ButtonRenderer extends JPanel implements TableCellRenderer {
    public ButtonRenderer() {
      setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
      add(createCircularButton("▶", new Color(76, 175, 80))); // Desaturated Green
      add(createCircularButton("⏸", new Color(33, 150, 243))); // Desaturated Blue
      add(createCircularButton("⏹", new Color(244, 67, 54))); // Desaturated Red
    }

    private JButton createCircularButton(String text, Color color) {
      JButton button =
          new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
              if (getModel().isPressed()) {
                g.setColor(color.darker());
              } else {
                g.setColor(color);
              }
              g.fillOval(0, 0, getWidth(), getHeight()); // Draw circle
              super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
              return new Dimension(30, 30); // Make button circular with a size of 30x30
            }
          };
      button.setForeground(Color.WHITE);
      button.setBorderPainted(false);
      button.setFocusPainted(false);
      return button;
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      return this;
    }
  }

  /**
   * Custom TableCellEditor to handle button clicks in the "Actions" column with desaturated colors.
   */
  private static class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

    public ButtonEditor() {
      panel.add(createCircularActionButton(new Color(86, 149, 91), "Start Service"));
      panel.add(createCircularActionButton(new Color(97, 139, 221), "Pause Service"));
      panel.add(createCircularActionButton(new Color(199, 78, 78), "Stop Service"));
    }

    private JButton createCircularActionButton(Color color, String actionCommand) {
      JButton button =
          new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
              if (getModel().isPressed()) {
                g.setColor(color.darker());
              } else {
                g.setColor(color);
              }
              g.fillOval(0, 0, getWidth(), getHeight()); // Draw circle
              super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
              return new Dimension(30, 30); // Circular size
            }
          };
      button.setActionCommand(actionCommand);
      button.addActionListener(this::handleButtonClick);
      return button;
    }

    private void handleButtonClick(ActionEvent e) {
      JOptionPane.showMessageDialog(null, e.getActionCommand() + " clicked!");
    }

    @Override
    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {
      return panel;
    }

    @Override
    public Object getCellEditorValue() {
      return "Actions";
    }
  }
}
