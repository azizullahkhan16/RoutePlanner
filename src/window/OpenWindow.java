package window;

import toolbar.DrawingCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

// Singleton pattern for open window
public class OpenWindow {
    // Attributes
    private static JFrame frame;
    private int x, y, width, height;
    private static OpenWindow openWindow;
    private String selectedFilePath;

    /**
     * Private constructor for open window
     * @param x x position of open window on board
     * @param y y position of open window on board
     * @param width width of open window
     * @param height height of open window
     */
    private OpenWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialises first open window or displays already made window
     * @param x x position of open window on board
     * @param y y position of open window on board
     * @param width width of open window
     * @param height height of open window
     * @return window
     */
    public static OpenWindow getOpenWindow(int x, int y, int width, int height) {
        if (openWindow == null) {
            openWindow = new OpenWindow(x, y, width, height);
            return openWindow;
        }
        return null;
    }

    // Displays window
    public void showOpenDialog() {
        JDialog dialog = new JDialog(frame, "Open", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setBounds(x, y, width, height);
        dialog.setLayout(new BorderLayout());

        JPanel fileListPanel = new JPanel();
        fileListPanel.setLayout(new BorderLayout());

        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(fileListModel);
        fileList.setCellRenderer(new FileListCellRenderer());

        // Replace the directory path with your specific folder path
        File directory = new File("src/savefiles");
        if (directory.isDirectory()) {
            File[] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".ser");
                }
            });

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileListModel.addElement(file.getName());
                    }
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(fileList);
        fileListPanel.add(scrollPane, BorderLayout.CENTER);

        JButton openButton = new JButton("Open");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                if (selectedFile != null) {
                    selectedFilePath = directory.getAbsolutePath() + "/" + selectedFile;
                    // Perform the action of opening the file and displaying it on the screen
                    // You can implement your own logic here based on your requirements
                    DrawingCanvas.LoadFile(selectedFile);
                } else {
                    JOptionPane.showMessageDialog(dialog, "No file has been selected.");
                    return;
                }
                dialog.dispose();
            }
        });

        dialog.add(fileListPanel, BorderLayout.CENTER);
        dialog.add(openButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }


    // Used to display list of saved files
    private class FileListCellRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        // Icon for files
        private final Icon fileIcon = UIManager.getIcon("FileView.fileIcon");

        public FileListCellRenderer() {
            setOpaque(true);
            setIconTextGap(10);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            setFont(getFont().deriveFont(Font.PLAIN, 16));
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            // Get the default rendering component for the list cell
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Set the file icon for the label
            label.setIcon(fileIcon);
            return label;
        }
    }

}
