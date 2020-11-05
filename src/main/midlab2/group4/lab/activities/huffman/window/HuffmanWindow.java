package main.midlab2.group4.lab.activities.huffman.window;

import main.midlab2.group4.lab.activities.huffman.codec.Huffman;
import main.midlab2.group4.lab.activities.huffman.codec.HuffmanCodec;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class HuffmanWindow {
    private final JFrame frame = new JFrame();
    private final JPanel mainPanel = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();
    private JMenu aboutSubMenu;
    private JMenu themesSubMenu;

    private JPanel inputPanel;
    private JLabel inputTextLabel;
    private JTextArea inputTextArea;
    private JScrollPane textAreaScrollPane;
    private JButton textConvertButton;
    private JButton importFromTextFile;

    private JPanel outputPanel;
    private JLabel outputLabel;

    private JButton showHuffmanTreeButton;
    private JButton showDecodedTextButton;
    private DefaultTableModel freqTableDefTabMod;
    private JTable frequencyTable;
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

    private JFrame decodedTextFrame;
    private JLabel decodedTextLabel;
    private JTextArea decodedTextArea;
    private JScrollPane decodedTextScrollPane;

    private Color backgroundColor, headerColor, mainFieldColor, uneditableFieldColor, accentColor;
    private Color backgroundForeground, fieldForeground, buttonForeground, headerForeground;

    private File textFile;
    private Huffman huffman;
    private HuffmanCodec huffmanCodec;

    private GridBagConstraints gbc;

    public HuffmanWindow() {
        frame.setTitle("Huffman Coding");
        frame.setIconImage(new ImageIcon("src/main/midlab2/group4/lab/activities/huffman/asset/Tree.png").getImage());

        setMenuBar();
        setMainPanel();
        String INITIAL_THEME = "Light";
        setTheme(INITIAL_THEME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width / 2) - frame.getWidth() / 2, (d.height / 2) - frame.getHeight() / 2);
        frame.revalidate();
        frame.setVisible(true);
    }

    private void setMenuBar() {
        menuBar.setPreferredSize(new Dimension(10, 29));
        menuBar.setBorderPainted(false);

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

        JMenuItem darkTheme = new JMenuItem("Dark");
        darkTheme.setMnemonic(KeyEvent.VK_D);
        darkTheme.addActionListener(e -> setTheme("Dark"));
        themesSubMenu.add(darkTheme);

        JMenuItem sluTheme = new JMenuItem("SLU");
        sluTheme.setMnemonic(KeyEvent.VK_S);
        sluTheme.addActionListener(e -> setTheme("SLU"));
        themesSubMenu.add(sluTheme);

        JMenuItem draculaTheme = new JMenuItem("Dracula");
        draculaTheme.setMnemonic(KeyEvent.VK_A);
        draculaTheme.addActionListener(e -> setTheme("Dracula"));
        themesSubMenu.add(draculaTheme);

        JMenuItem gruvboxTheme = new JMenuItem("Gruvbox");
        gruvboxTheme.setMnemonic(KeyEvent.VK_G);
        gruvboxTheme.addActionListener(e -> setTheme("Gruvbox"));
        themesSubMenu.add(gruvboxTheme);

        JMenuItem godspeedTheme = new JMenuItem("Godspeed");
        godspeedTheme.setMnemonic(KeyEvent.VK_E);
        godspeedTheme.addActionListener(e -> setTheme("Godspeed"));
        themesSubMenu.add(godspeedTheme);

        JMenuItem Olive = new JMenuItem("Olive");
        Olive.setMnemonic(KeyEvent.VK_V);
        Olive.addActionListener(e -> setTheme("Olive"));
        themesSubMenu.add(Olive);

        JMenuItem halloweenTheme = new JMenuItem("Halloween");
        halloweenTheme.setMnemonic(KeyEvent.VK_H);
        halloweenTheme.addActionListener(e -> setTheme("Halloween"));
        themesSubMenu.add(halloweenTheme);
    }

    private void setMainPanel() {
        initializeInputPanel();
        initializeOutputPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(outputPanel);
        frame.add(mainPanel);
    }

    private void initializeInputPanel() {
        inputPanel = new JPanel(new GridBagLayout());
        inputTextLabel = new JLabel("  Input Text Here                          ");
        inputTextArea = new JTextArea();
        textAreaScrollPane = new JScrollPane(inputTextArea);
        textConvertButton = new JButton("Convert");
        importFromTextFile = new JButton("Import From File");

        setInputPanelComponentProperties();

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(inputTextLabel, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        inputPanel.add(importFromTextFile, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        inputPanel.add(textAreaScrollPane, gbc);
        gbc.insets = new Insets(5, 4, 3, 4);
        gbc.gridy = 2;
        inputPanel.add(textConvertButton, gbc);
    }

    private void setInputPanelComponentProperties() {
        modifyDecodedField(inputTextLabel, inputTextArea);
        textAreaScrollPane.setPreferredSize(new Dimension(0, 511));

        importFromTextFile.setPreferredSize(new Dimension(importFromTextFile.getPreferredSize().width, 30));
        importFromTextFile.setFocusPainted(false);
        importFromTextFile.addActionListener((e) -> promptFileSelection());

        textConvertButton.setPreferredSize(new Dimension(textConvertButton.getPreferredSize().width, 30));
        textConvertButton.setFocusPainted(false);
        textConvertButton.addActionListener((e) -> {
            if (inputTextArea.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "Text cannot be empty",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            commenceHuffmanProcesses(inputTextArea.getText());
        });
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputTextArea.setText(content);
    }

    private void initializeOutputPanel() {
        outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setBorder(new EmptyBorder(10, 2, 10, 10));
        JLabel spacing = new JLabel();
        spacing.setPreferredSize(new Dimension(170, 30));

        setOutputPanelComponentButtonProperties();
        initializeOutputTables();
        initializeFields();

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.weightx = gbc.weighty = 1;
        gbc.gridwidth = 3;
        outputPanel.add(outputLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        outputPanel.add(showDecodedTextButton, gbc);
        gbc.gridx = 4;
        outputPanel.add(spacing, gbc);
        gbc.gridx = 5;
        outputPanel.add(showHuffmanTreeButton, gbc);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 1;
        outputPanel.add(freqTableScrollPane, gbc);
        gbc.gridx = 3;
        outputPanel.add(codeTableScrollPane, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        outputPanel.add(encodedLabel, gbc);
        gbc.gridwidth = 5;
        gbc.gridx = 1;
        outputPanel.add(encodedOutputTextScrollPane, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        modifyLabel(origSizeLabel, origSizeField, compRateLabel, compRateField);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        modifyLabel(compSizeLabel, compSizeField, noLossLabel, noLossField);
    }

    private void modifyLabel(JLabel xLabel, JTextField xField, JLabel yLabel, JTextField yField) {
        outputPanel.add(xLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        outputPanel.add(xField, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        outputPanel.add(yLabel, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 4;
        outputPanel.add(yField, gbc);
    }

    private void setOutputPanelComponentButtonProperties() {
        outputLabel = new JLabel("  Output");
        outputLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        showDecodedTextButton = new JButton("Show Decoded Text");
        showDecodedTextButton.setPreferredSize(new Dimension(186, 30));
        showDecodedTextButton.setFocusPainted(false);
        showDecodedTextButton.addActionListener((e) -> {
            try {
                showDecodedText();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(frame, "Convert text first",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        showHuffmanTreeButton = new JButton("Show Huffman Tree");
        showHuffmanTreeButton.setPreferredSize(new Dimension(186, 30));
        showHuffmanTreeButton.setFocusPainted(false);
        showHuffmanTreeButton.addActionListener((e) -> showHuffmanTree());
    }

    private void initializeOutputTables() {
        freqTableDefTabMod = new DefaultTableModel();
        frequencyTable = new JTable(freqTableDefTabMod);
        freqTableScrollPane = new JScrollPane(frequencyTable);
        initializeTable(frequencyTable, freqTableDefTabMod, new String[]{"Character", "Frequency"},
                new int[]{50, 50}, freqTableScrollPane);

        codeTableDefTabMod = new DefaultTableModel();
        codeTable = new JTable(codeTableDefTabMod);
        codeTableScrollPane = new JScrollPane(codeTable);
        initializeTable(codeTable, codeTableDefTabMod, new String[]{"Character", "Code", "Bits"},
                new int[]{50, 50, 50}, codeTableScrollPane);
    }

    private void initializeFields() {
        encodedLabel = new JLabel("  Encoded:");
        encodedOutputTextArea = new JTextArea(3, 50);
        encodedOutputTextArea.setEditable(false);
        encodedOutputTextArea.setFont(new Font("", Font.PLAIN, 14));
        encodedOutputTextArea.setMargin(new Insets(2, 2, 2, 2));
        encodedOutputTextArea.setLineWrap(true);
        encodedOutputTextArea.setWrapStyleWord(true);
        encodedOutputTextScrollPane = new JScrollPane(encodedOutputTextArea);

        origSizeLabel = new JLabel("  Original Size:");
        origSizeField = new JTextField();
        origSizeField.setEditable(false);
        origSizeField.setColumns(30);
        origSizeField.setPreferredSize(new Dimension(1, 25));

        compSizeLabel = new JLabel("  Compressed Size:");
        compSizeField = new JTextField();
        compSizeField.setEditable(false);
        compSizeField.setColumns(30);
        compSizeField.setPreferredSize(new Dimension(1, 25));

        compRateLabel = new JLabel("  Compression Rate:");
        compRateField = new JTextField();
        compRateField.setEditable(false);
        compRateField.setColumns(30);

        noLossLabel = new JLabel("  Lossless Compression:");
        noLossField = new JTextField();
        noLossField.setEditable(false);
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

    private void commenceHuffmanProcesses(String text) {
        huffman = new Huffman();
        huffmanCodec = new HuffmanCodec(huffman);
        huffman.setText(text);
        huffman.generateTree();
        huffmanCodec.buildHuffmanCode();

        String encoded = huffmanCodec.encode();
        String decodedText = huffmanCodec.decode();
        float origSize = huffman.getText().length() * 7;
        float compressedSize = encoded.length();
        float compressionRate = ((compressedSize - origSize) / origSize) * 100;

        printConsoleOutput(encoded, decodedText, origSize, compressedSize, compressionRate);
        setGUIProperties(encoded, decodedText, origSize, compressedSize, compressionRate);
    }

    private void printConsoleOutput(String encoded, String decodedText, float origSize,
                                    float compressedSize, float compressionRate) {
        System.out.println("=============================================================");
        System.out.println("Frequency Values");
        System.out.println(huffman.toString());

        System.out.println(huffmanCodec.toString());

        System.out.println("=============================================================");
        System.out.print("Encoded Text: ");
        System.out.println(encoded);
        System.out.print("Original Text: ");
        System.out.println(huffman.getText());
        System.out.print("Decoded Text:  ");
        System.out.println(decodedText);

        System.out.println("\nIs it the same as the original: " + (huffman.getText().equals(decodedText)));
        System.out.println("\nOriginal Size:    " + (int) origSize + " bits");
        System.out.println("Compressed Size:  " + (int) compressedSize + " bits");
        System.out.println("Compression Rate: " + -compressionRate + "%");
        System.out.println("=============================================================");
    }

    private void setGUIProperties(String encoded, String decodedText, float origSize,
                                  float compressedSize, float compressionRate) {
        populateFreqValTable(huffman.returnFreqValuesAsTableArray());
        populatePairCharCodeTable(huffmanCodec.returnPairCharCodeAsArr());

        encodedOutputTextArea.setText(encoded);
        origSizeField.setText(" " + (int) origSize + " bits");
        compSizeField.setText(" " + (int) compressedSize + " bits");
        compRateField.setText(" " + (int) -compressionRate + "%");
        noLossField.setText((" " + huffman.getText().equals(decodedText)) + "");
    }

    private void populateFreqValTable(String[][] arr) {
        freqTableDefTabMod.setRowCount(0);
        for (String[] row : arr) {
            freqTableDefTabMod.addRow(row);
        }
    }

    private void populatePairCharCodeTable(String[][] arr) {
        codeTableDefTabMod.setRowCount(0);
        for (String[] row : arr) {
            codeTableDefTabMod.addRow(row);
        }
    }

    private void showHuffmanTree() {
        try {
            TreeVisualizerWindow w = new TreeVisualizerWindow(huffman.getRoot(), backgroundColor, accentColor, buttonForeground);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Convert text first",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDecodedText() {
        decodedTextFrame = new JFrame("Decoded Text: ");
        decodedTextFrame.getContentPane().setBackground(backgroundColor);
        decodedTextLabel = new JLabel("Decoded Text", SwingConstants.CENTER);
        decodedTextLabel.setForeground(backgroundForeground);
        decodedTextArea = new JTextArea(huffmanCodec.decode());
        decodedTextScrollPane = new JScrollPane(decodedTextArea);

        setDecodedFrameComponentProperties();

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridx = gbc.gridy = 0;

        decodedTextFrame.add(decodedTextLabel, gbc);
        gbc.gridy++;
        decodedTextFrame.add(decodedTextScrollPane, gbc);

        decodedTextFrame.setMinimumSize(new Dimension(377, 611));
        decodedTextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        decodedTextFrame.setLocationRelativeTo(null);
        decodedTextFrame.setResizable(true);
        decodedTextFrame.setVisible(true);
    }

    private void setDecodedFrameComponentProperties() {
        decodedTextFrame.setLayout(new GridBagLayout());

        modifyDecodedField(decodedTextLabel, decodedTextArea);
        decodedTextArea.setEditable(false);
        decodedTextScrollPane.setPreferredSize(new Dimension(0, 511));
        decodedTextScrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    private void modifyDecodedField(JLabel decodedTextLabel, JTextArea decodedTextArea) {
        decodedTextLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        decodedTextLabel.setVerticalAlignment(SwingConstants.CENTER);

        decodedTextArea.setMargin(new Insets(4, 4, 4, 4));
        decodedTextArea.setFont(new Font("", Font.PLAIN, 14));
        decodedTextArea.setLineWrap(true);
        decodedTextArea.setWrapStyleWord(true);
    }

    private void setTheme(String theme) {
        if (theme.equalsIgnoreCase("Light")) setWhiteThemeProperties();
        else if (theme.equalsIgnoreCase("Dark")) setDarkThemeProperties();
        else if (theme.equalsIgnoreCase("SLU")) setSLUThemeProperties();
        else if (theme.equalsIgnoreCase("Dracula")) setDraculaThemeProperties();
        else if (theme.equalsIgnoreCase("Godspeed")) setGodspeedThemeProperties();
        else if (theme.equalsIgnoreCase("Gruvbox")) setGruvboxThemeProperties();
        else if (theme.equalsIgnoreCase("Olive")) setOliveThemeProperties();
        else if (theme.equalsIgnoreCase("Halloween")) setHalloweenThemeProperties();

        UIManager.put("OptionPane.background", accentColor);
        UIManager.put("Panel.background", accentColor);
        UIManager.put("OptionPane.messageForeground", buttonForeground);
        UIManager.put("Button.background", backgroundColor);
        UIManager.put("Button.foreground", backgroundForeground);
        UIManager.put("Button.select", backgroundColor);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(4,8,4,8));
        UIManager.put("Button.focus", backgroundColor);

        setBackgrounds();
        setForegrounds();
        setButtonColors();
        setBorders();
        setTableColors();
    }

    private void setBackgrounds() {
        menuBar.setBackground(headerColor);
        mainPanel.setBackground(backgroundColor);
        inputPanel.setBackground(backgroundColor);
        outputPanel.setBackground(backgroundColor);
        inputTextArea.setBackground(mainFieldColor);

        encodedOutputTextArea.setBackground(uneditableFieldColor);
        origSizeField.setBackground(uneditableFieldColor);
        compSizeField.setBackground(uneditableFieldColor);
        compRateField.setBackground(uneditableFieldColor);
        noLossField.setBackground(uneditableFieldColor);
    }

    private void setForegrounds() {
        for (JMenu jMenu : Arrays.asList(aboutSubMenu, themesSubMenu)) {
            jMenu.setForeground(headerForeground);
        }

        inputTextArea.setForeground(fieldForeground);
        frequencyTable.setForeground(fieldForeground);
        codeTable.setForeground(fieldForeground);

        for (JLabel jLabel : Arrays.asList(inputTextLabel, outputLabel, encodedLabel, origSizeLabel, compSizeLabel, compRateLabel, noLossLabel)) {
            jLabel.setForeground(backgroundForeground);
        }

        encodedOutputTextArea.setForeground(fieldForeground);
        for (JTextField jTextField : Arrays.asList(origSizeField, compSizeField, compRateField, noLossField)) {
            jTextField.setForeground(fieldForeground);
        }
    }

    private void setBorders() {
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 2));
        textAreaScrollPane.setBorder(new LineBorder(backgroundColor));

        freqTableScrollPane.setBorder(new LineBorder(backgroundColor));
        codeTableScrollPane.setBorder(new LineBorder(backgroundColor));
        frequencyTable.getTableHeader().setBorder(new LineBorder(headerColor));
        codeTable.getTableHeader().setBorder(new LineBorder(headerColor));

        encodedOutputTextScrollPane.setBorder(BorderFactory.createEmptyBorder());
        origSizeField.setBorder(new LineBorder(uneditableFieldColor));
        compSizeField.setBorder(new LineBorder(uneditableFieldColor));
        compRateField.setBorder(new LineBorder(uneditableFieldColor));
        noLossField.setBorder(new LineBorder(uneditableFieldColor));
    }

    private void setTableColors() {
        modifyTableHeaders(frequencyTable, freqTableScrollPane);

        modifyTableHeaders(codeTable, codeTableScrollPane);

    }

    private void modifyTableHeaders(JTable table, JScrollPane scrollPane) {
        table.getTableHeader().setPreferredSize(new Dimension(1, 24));
        table.getTableHeader().setFont(new Font("", Font.BOLD, 12));
        table.setBackground(mainFieldColor);
        table.getTableHeader().setBackground(headerColor);
        table.getTableHeader().setForeground(headerForeground);
        scrollPane.setBackground(headerColor);
    }

    private void setButtonColors() {
        importFromTextFile.setBackground(accentColor);
        importFromTextFile.setForeground(buttonForeground);
        importFromTextFile.setBorderPainted(false);

        textConvertButton.setBackground(accentColor);
        textConvertButton.setForeground(buttonForeground);
        textConvertButton.setBorderPainted(false);

        showDecodedTextButton.setBackground(accentColor);
        showDecodedTextButton.setForeground(buttonForeground);
        showDecodedTextButton.setBorderPainted(false);

        showHuffmanTreeButton.setBackground(accentColor);
        showHuffmanTreeButton.setForeground(buttonForeground);
        showHuffmanTreeButton.setBorderPainted(false);
    }

    private void setWhiteThemeProperties() {
        backgroundColor = new Color(0xD9D9D9);
        headerColor = new Color(0x222222);
        mainFieldColor = new Color(0xFFFFFF);
        uneditableFieldColor = new Color(0xECECEC);
        accentColor = new Color(0x222222);

        backgroundForeground = Color.BLACK;
        headerForeground = Color.WHITE;
        fieldForeground = Color.BLACK;
        buttonForeground = Color.WHITE;
    }

    private void setDarkThemeProperties() {
        backgroundColor = new Color(0x333333);
        headerColor = new Color(0x000000);
        mainFieldColor = new Color(0x666666);
        uneditableFieldColor = new Color(0x4A4A4A);
        accentColor = new Color(0x000000);

        backgroundForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        fieldForeground = Color.WHITE;
        buttonForeground = Color.WHITE;
    }

    private void setSLUThemeProperties() {
        backgroundColor = new Color(0xF4D35E);
        headerColor = new Color(0x0D3B66);
        mainFieldColor = new Color(0xFFFFFF);
        uneditableFieldColor = new Color(0xFAF0CA);
        accentColor = new Color(0x0D3B66);

        backgroundForeground = Color.BLACK;
        headerForeground = Color.WHITE;
        fieldForeground = Color.BLACK;
        buttonForeground = Color.WHITE;
    }

    private void setDraculaThemeProperties() {
        backgroundColor = new Color(0x282A36);
        headerColor = new Color(0x6272A4);
        mainFieldColor = new Color(0x44475A);
        uneditableFieldColor = new Color(0x363848);
        accentColor = new Color(0xBD93F9);

        backgroundForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        fieldForeground = Color.WHITE;
        buttonForeground = Color.BLACK;
    }

    private void setGruvboxThemeProperties() {
        backgroundColor = new Color(0x282828);
        headerColor = new Color(0x689D6A);
        mainFieldColor = new Color(0x1D2021);
        uneditableFieldColor = new Color(0x3C3836);
        accentColor = new Color(0xDED1AD);

        backgroundForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        fieldForeground = Color.WHITE;
        buttonForeground = Color.BLACK;
    }

    private void setGodspeedThemeProperties() {
        backgroundColor = new Color(0x6A97B5);
        headerColor = new Color(0x5A5E61);
        mainFieldColor = new Color(0xEAE3D5);
        uneditableFieldColor = new Color(0xAABDC5);
        accentColor = new Color(0xFAEE69);

        backgroundForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        fieldForeground = Color.BLACK;
        buttonForeground = Color.BLACK;
    }

    private void setOliveThemeProperties() {
        backgroundColor = new Color(0xB6B09A);
        headerColor = new Color(0x2E2F33);
        mainFieldColor = new Color(0xD9D2C8);
        uneditableFieldColor = new Color(0xC7C1B1);
        accentColor = new Color(0x6F8C70);

        backgroundForeground = Color.BLACK;
        headerForeground = Color.WHITE;
        fieldForeground = Color.BLACK;
        buttonForeground = Color.WHITE;
    }

    private void setHalloweenThemeProperties() {
        backgroundColor = new Color(0x1B1B1B);
        headerColor = new Color(0x000000);
        mainFieldColor = new Color(0x363636);
        uneditableFieldColor = new Color(0x282828);
        accentColor = new Color(0xFF9900);

        backgroundForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        fieldForeground = Color.WHITE;
        buttonForeground = Color.BLACK;
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
                "   Description:   Data Structures\n" +
                        "   Instructor:   Roderick Makil\n" +
                        "   Class Code:   9413\n" +
                        "   Class #:   CS 211\n",
                "Course Specifications", JOptionPane.PLAIN_MESSAGE);
    }
}