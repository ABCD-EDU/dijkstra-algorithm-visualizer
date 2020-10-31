package main.midlab2.group4.lab.activities.huffman;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HuffmanWindow {
    private final JFrame frame = new JFrame();
    private final JPanel mainPanel = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();
    private JMenu aboutSubMenu;
    private JMenu themesSubMenu;

    private JPanel inputPanel;
    private JLabel inputTextLabel;
    private JTextArea inputTextArea;
    private JButton textConvertButton;
    private JScrollPane textAreaScrollPane;
    private JButton importFromTextFile;
    private File textFile;

    private JPanel outputPanel;
    private JLabel outputLabel;

    private JButton showHuffmanTreeButton;
    private DefaultTableModel freqTableDefTabMod;
    private JTable freqTable;
    private JScrollPane freqTableScrollPane;
    private DefaultTableModel codeTableDefTabMod;
    private JTable codeTable;
    private JScrollPane codeTableScrollPane;

    private JLabel encodedLabel;
    private JTextArea encodedOutputTextArea;
    private JScrollPane encodedOutputTextScrollPane;
    private JLabel origSizeLabel;
    private JTextField origSizeField;
    private JLabel compSizeLabel;
    private JTextField compSizeField;
    private JLabel compRateLabel;
    private JTextField compRateField;
    private JLabel noLossLabel;
    private JTextField noLossField;

    private Color backgroundColor, headerColor;
    private Color mainForeground, secondaryForeground;

    private TreeNode root;

    private GridBagConstraints gbc;

    public HuffmanWindow() {
        frame.setTitle("Huffman Coding");
        frame.setIconImage(new ImageIcon("src/assets/Tree.png").getImage());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        } // Prevent making Swing look like its from 1995

        setMenuBar();
        initializeInputPanel();
        initializeOutputPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(outputPanel);
        frame.add(mainPanel);
        setTheme("SLU");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
    }

    private void setMenuBar() {
        menuBar.setBorderPainted(false);
        menuBar.setPreferredSize(new Dimension(10, 25));

        JMenu menuItemSpacing1 = new JMenu();
        JMenu menuItemSpacing2 = new JMenu();
        menuItemSpacing1.setEnabled(false);
        menuItemSpacing2.setEnabled(false);

        initializeAboutSubMenu();
        initializeThemesSubMenu();

        menuBar.add(menuItemSpacing1);
        menuBar.add(aboutSubMenu);
        menuBar.add(menuItemSpacing2);
        menuBar.add(themesSubMenu);

        frame.setJMenuBar(menuBar);
    }

    private void initializeAboutSubMenu() {
        aboutSubMenu = new JMenu("About");

        JMenuItem groupMembers = new JMenuItem("Group Members");
        groupMembers.setMnemonic(KeyEvent.VK_G);
        groupMembers.addActionListener(e -> displayGroupMembers());
        aboutSubMenu.add(groupMembers);

        JMenuItem courseSpecification = new JMenuItem("Course Specifications");
        courseSpecification.setMnemonic(KeyEvent.VK_C);
        courseSpecification.addActionListener(e -> displayCourseSpecifications());
        aboutSubMenu.add(courseSpecification);
    }

    private void initializeThemesSubMenu() {
        themesSubMenu = new JMenu("Themes");

        JMenuItem lightTheme = new JMenuItem("Light");
        lightTheme.setMnemonic(KeyEvent.VK_L);
        lightTheme.addActionListener(e -> setTheme("Light"));
        themesSubMenu.add(lightTheme);

        JMenuItem sluTheme = new JMenuItem("SLU");
        sluTheme.setMnemonic(KeyEvent.VK_S);
        sluTheme.addActionListener(e -> setTheme("SLU"));
        themesSubMenu.add(sluTheme);

        JMenuItem darkTheme = new JMenuItem("Dark");
        darkTheme.setMnemonic(KeyEvent.VK_D);
        darkTheme.addActionListener(e -> setTheme("Dark"));
        themesSubMenu.add(darkTheme);
    }

    private void initializeInputPanel() {
        gbc = new GridBagConstraints();
        inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(3,3,3,3));
        inputTextLabel = new JLabel(" Input Text Here:                         ");
        inputTextArea = new JTextArea(29,35);
        textConvertButton = new JButton("Convert");
        textAreaScrollPane = new JScrollPane(inputTextArea);
        importFromTextFile = new JButton("Import From File");

        setInputPanelComponentProperties();

        gbc.insets = new Insets(3,3,3,3);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(inputTextLabel, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        inputPanel.add(importFromTextFile, gbc);
        gbc.insets = new Insets(3,3,5,3);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        inputPanel.add(textAreaScrollPane, gbc);
        gbc.insets = new Insets(3,3,3,3);
        gbc.gridy = 2;
        inputPanel.add(textConvertButton, gbc);
    }

    private void setInputPanelComponentProperties() {
        inputTextLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        inputTextLabel.setVerticalAlignment(SwingConstants.CENTER);

        inputTextArea.setMargin(new Insets(4,4,4,4));
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setFont(new Font("", Font.PLAIN, 14));

        textAreaScrollPane.setBorder(BorderFactory.createEmptyBorder());

        importFromTextFile.setFocusPainted(false);
        textConvertButton.setFocusPainted(false);

        textConvertButton.addActionListener((e) -> {
            if (inputTextArea.getText().equals("")){
                JOptionPane.showMessageDialog(frame, "Text cannot be empty",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            doTheMagic(inputTextArea.getText());
        });
        importFromTextFile.addActionListener((e) -> promptFileSelection());
    }

    private void promptFileSelection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        int choice = fileChooser.showOpenDialog(frame);
        if (choice == JFileChooser.APPROVE_OPTION) {
            textFile = fileChooser.getSelectedFile();
            readLinesFromFile();
            System.out.println("CHOSEN: " + textFile.getName());
        } else {
            System.out.println("File Selection Aborted");
        }
    }

    private void readLinesFromFile() {
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(textFile.getPath()));
            StringBuilder string = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                string.append(line);
                string.append(System.lineSeparator());
                line = reader.readLine();
            }
            content = string.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        inputTextArea.setText(content);
    }

    private void initializeOutputPanel() {
        outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setBorder(new EmptyBorder(3,3,3,3));
        outputLabel = new JLabel("Output: ");
        outputLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        showHuffmanTreeButton = new JButton("Show Huffman Tree");

        initializeOutputTables();
        initializeFields();

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3,3,3,3);
        gbc.weightx = gbc.weighty = 1;
        gbc.gridwidth = 2;
        outputPanel.add(outputLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        outputPanel.add(showHuffmanTreeButton, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        outputPanel.add(freqTableScrollPane, gbc);
        gbc.gridx = 2;
        outputPanel.add(codeTableScrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        outputPanel.add(encodedLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        outputPanel.add(encodedOutputTextScrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        outputPanel.add(origSizeLabel, gbc);
        gbc.gridx = 1;
        outputPanel.add(origSizeField, gbc);
        gbc.gridx  = 2;
        outputPanel.add(compRateLabel, gbc);
        gbc.gridx = 3;
        outputPanel.add(compRateField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        outputPanel.add(compSizeLabel, gbc);
        gbc.gridx = 1;
        outputPanel.add(compSizeField, gbc);
        gbc.gridx = 2;
        outputPanel.add(noLossLabel, gbc);
        gbc.gridx = 3;
        outputPanel.add(noLossField, gbc);
    }

    private void initializeOutputTables() {
        showHuffmanTreeButton.setFocusPainted(false);
        showHuffmanTreeButton.addActionListener((e) -> showHuffmanTree());

        freqTableDefTabMod = new DefaultTableModel();
        freqTable = new JTable(freqTableDefTabMod);
        freqTableScrollPane = new JScrollPane(freqTable);
        initializeTable(freqTable, freqTableDefTabMod, new String[]{"Character", "Frequency"},
                new int[]{50,50},freqTableScrollPane);

        codeTableDefTabMod = new DefaultTableModel();
        codeTable = new JTable(codeTableDefTabMod);
        codeTableScrollPane = new JScrollPane(codeTable);
        initializeTable(codeTable, codeTableDefTabMod, new String[]{"Character", "Code", "Bits"},
                new int[]{50,50,50}, codeTableScrollPane);
    }

    private void initializeFields() {
        encodedLabel = new JLabel("Encoded: ");
        encodedOutputTextArea = new JTextArea(3,50);
        encodedOutputTextArea.setFont(new Font("", Font.PLAIN, 14));
        encodedOutputTextArea.setMargin(new Insets(2,2,2,2));
        encodedOutputTextArea.setLineWrap(true);
        encodedOutputTextArea.setWrapStyleWord(true);
        encodedOutputTextScrollPane = new JScrollPane(encodedOutputTextArea);

        origSizeLabel = new JLabel("Original Size: ");
        origSizeField = new JTextField();
        origSizeField.setColumns(30);

        compSizeLabel = new JLabel("Compressed Size: ");
        compSizeField = new JTextField();
        compSizeField.setColumns(30);

        compRateLabel = new JLabel("Compression Rate: ");
        compRateField = new JTextField();
        compRateField.setColumns(30);

        noLossLabel = new JLabel("Lossless Compression: ");
        noLossField = new JTextField();
        noLossField.setColumns(30);
    }

    private void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames, int[] columnsWidth,
                                 JScrollPane scrollPane) {
        defaultTableModel.setColumnIdentifiers(columnNames);
        defaultTableModel.setRowCount(0);
        table.setEnabled(false);
        table.setCellSelectionEnabled(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(50);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));

        int i = 0;
        for (int width : columnsWidth) {
            TableColumn column = table.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setPreferredWidth(width);
        }
        scrollPane.setVisible(true);
    }

    // Temporary
    private void doTheMagic(String text) {
        Huffman h = new Huffman();
        HuffmanCodec t = new HuffmanCodec(h);
        h.setText(text);
        h.generateTree();
        this.root = h.getRoot();

        System.out.println("=============================================================");
        System.out.println("Frequency Values");
        System.out.println(h.toString());

        t.buildHuffmanCode();

        System.out.println(t.toString());

        String encoded = t.encode();
        String decoded = t.decode();
        System.out.println("=============================================================");
        System.out.print("Encoded Text: ");
        System.out.println(encoded);
        System.out.print("Original Text: ");
        System.out.println(h.getText());
        System.out.print("Decoded Text:  ");
        System.out.println(decoded);

        // ---- OUTPUT VALUES COMPUTATION ----
        float origSize = h.getText().length() * 7;
        float compressedSize = encoded.length();
        float compressionRate = ((compressedSize - origSize) / origSize) * 100;

        System.out.println("\nIs it the same as the original: " + (h.getText().equals(decoded)) );
        System.out.println("\nOriginal Size:    " + (int) origSize + " bits");
        System.out.println("Compressed Size:  " + (int) compressedSize + " bits");
        System.out.println("Compression Rate: " + -compressionRate + "%");
        System.out.println("=============================================================");

        populateFreqValTable(h.returnFreqValuesAsTableArray());
        populatePairCharCodeTable(t.returnPairCharCodeAsArr());

        encodedOutputTextArea.setText(encoded);
        origSizeField.setText((int) origSize + " bits");
        compSizeField.setText((int) compressedSize + " bits");
        compRateField.setText((int) -compressionRate + "%");
        noLossField.setText((h.getText().equals(decoded)) + "");
    }

    private void populateFreqValTable(String[][] arr) {
        freqTableDefTabMod.setRowCount(0);
        for (String[] row : arr) {
            freqTableDefTabMod.addRow(row);
        }
    }

    private void populatePairCharCodeTable(String[][] arr) {
        codeTableDefTabMod.setRowCount(0);
        for(String[] row : arr) {
            codeTableDefTabMod.addRow(row);
        }
    }

    private void showHuffmanTree() {
        try {
            TreeVisualizerWindow w = new TreeVisualizerWindow(root);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Convert text first!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //TODO: Add more Themes
    private void setTheme(String theme) {
        if (theme.equalsIgnoreCase("Light")) setWhiteThemeProperties();
        else if (theme.equalsIgnoreCase("Dark")) setDarkThemeProperties();
        else if (theme.equalsIgnoreCase("SLU")) setSLUThemeProperties();

        UIManager.put("OptionPane.background", headerColor);
        UIManager.put("Panel.background", headerColor);
        UIManager.put("OptionPane.messageForeground", secondaryForeground);
        UIManager.put("Button.background", backgroundColor);
        UIManager.put("Button.foreground", mainForeground);
        UIManager.put("Button.select", headerColor);
        UIManager.put("Button.focus", backgroundColor);

        mainPanel.setBackground(new Color(0, 150, 167));
        inputPanel.setBackground(new Color(0, 150, 167));
        outputPanel.setBackground(new Color(0, 150, 167));
    }

    private void setWhiteThemeProperties() {
        backgroundColor = Color.WHITE;
        headerColor = new Color(0x222222);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
    }

    private void setDarkThemeProperties() {
        backgroundColor = new Color(0x333333);
        headerColor = Color.BLACK;
        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
    }

    private void setSLUThemeProperties() {
        backgroundColor = new Color(0xF4D35E);
        headerColor = new Color(0x0D3B66);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
    }

    private void setMetropolisThemeProperties() {

    }

    private void displayGroupMembers() {
        JOptionPane.showMessageDialog(frame,
                "       Arevalo, Lance Gabrielle\n" +
                        "       Barana, Lance Matthew\n" +
                        "       Bayquen, Christian\n" +
                        "       Cayton, Arian Carl\n" +
                        "       De los Trinos, Jp",
                "Group 4", JOptionPane.PLAIN_MESSAGE);
    }

    private void displayCourseSpecifications() {
        JOptionPane.showMessageDialog(frame,
                "       Description :    Data Structures\n" +
                        "       Instructor    :    Roderick Makil\n" +
                        "       Class Code  :    9413\n" +
                        "       Class #         :    CS 211\n",
                "Course Specifications", JOptionPane.PLAIN_MESSAGE);
    }
}