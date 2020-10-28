package main.midlab2.group4.lab.activities.huffman;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HuffmanWindow {
    private final JFrame frame = new JFrame();
    private final JPanel backgroundPanel = new JPanel(); // TRY frame.getContentPane.setBackground(color);
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

    private Color backgroundColor, headerColor, uneditableFieldColor;
    private Color mainForeground, secondaryForeground;

    private GridBagConstraints gbc;

    public HuffmanWindow() {
        frame.setTitle("Huffman Coding");
        frame.setIconImage(new ImageIcon("src/assets/Tree.png").getImage());
        frame.setMinimumSize(new Dimension(1280, 720));
        backgroundPanel.setLayout(new GridBagLayout());

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e) {
            e.printStackTrace();
        } // Prevent making Swing look like its from 1995

        setMenuBar();
        initializeMainPanel();

        gbc = new GridBagConstraints();
        gbc.weightx = gbc.weighty = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;

        backgroundPanel.add(mainPanel, gbc);
        frame.add(backgroundPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    private void initializeMainPanel() {
        gbc = new GridBagConstraints();

        initializeInputPanel();

        gbc.insets = new Insets(1,1,1,1);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        mainPanel.add(inputPanel, gbc);
    }

    private void initializeInputPanel() {
        gbc = new GridBagConstraints();
        inputPanel = new JPanel(new GridBagLayout());
        inputTextLabel = new JLabel("Input Text Here: ");
        inputTextArea = new JTextArea(28,35);
        textConvertButton = new JButton("Convert");
        textAreaScrollPane = new JScrollPane(inputTextArea);
        importFromTextFile = new JButton("Import From File");

        setInputPanelComponentProperties();

        gbc.insets = new Insets(2,4,2,4);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(inputTextLabel, gbc);
        gbc.gridy = 1;
        inputPanel.add(textAreaScrollPane, gbc);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        inputPanel.add(importFromTextFile, gbc);
        gbc.insets = new Insets(2,0,2,4);
        gbc.gridx = 1;
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

        textConvertButton.setMinimumSize(new Dimension(35,40));

        textConvertButton.addActionListener((e) -> {
            if (inputTextArea.getText().equals("")){
                JOptionPane.showMessageDialog(frame, "Text cannot be empty",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            doTheMagic(inputTextArea.getText());
        });

        importFromTextFile.addActionListener((e) -> promptFileSelection());

        inputPanel.setBackground(new Color(0, 150, 167));
    }

    private void promptFileSelection() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        int choice = fc.showOpenDialog(frame);
        if (choice == JFileChooser.APPROVE_OPTION) {
            textFile = fc.getSelectedFile();
            readLinesFromFile();
            System.out.println("CHOSEN: " + textFile.getName());
        }else {
            System.out.println("File Selection Aborted");
        }
    }

    private void readLinesFromFile() {
        String content = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(textFile.getPath()));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            content = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        inputTextArea.setText(content);
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
        groupMembers.addActionListener(e -> displayGroupMembers());
        aboutSubMenu.add(groupMembers);

        JMenuItem courseSpecification = new JMenuItem("Course Specifications");
        courseSpecification.addActionListener(e -> displayCourseSpecifications());
        aboutSubMenu.add(courseSpecification);
    }

    private void initializeThemesSubMenu() {
        themesSubMenu = new JMenu("Themes");
        JMenuItem lightTheme = new JMenuItem("Light");
        lightTheme.addActionListener(e -> setTheme("Light"));
        themesSubMenu.add(lightTheme);

        JMenuItem darkTheme = new JMenuItem("Dark");
        darkTheme.addActionListener(e -> setTheme("Dark"));
        themesSubMenu.add(darkTheme);

        JMenuItem sluTheme = new JMenuItem("SLU");
        sluTheme.addActionListener(e -> setTheme("SLU"));
        themesSubMenu.add(sluTheme);
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

    }

    private void setWhiteThemeProperties() {
        backgroundColor = Color.WHITE;
        headerColor = new Color(0x222222);
        uneditableFieldColor = new Color(0xE5E5E5);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
    }

    private void setDarkThemeProperties() {
        backgroundColor = new Color(0x333333);
        headerColor = Color.BLACK;
        uneditableFieldColor = new Color(0x4A4A4A);
        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
    }

    private void setSLUThemeProperties() {
        backgroundColor = new Color(0xF4D35E);
        headerColor = new Color(0x0D3B66);
        uneditableFieldColor = new Color(0xFAF0CA);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
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

    // Temporary
    private void doTheMagic(String text) {
        Huffman h = new Huffman();
        HuffmanCodec t = new HuffmanCodec(h);
        h.setText(text);
        h.generateTree();

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

        System.out.println("\nIs it the same as the original: " + (h.getText().equals(decoded)));
        System.out.println("\nOriginal Size:    " + (int) origSize + " bits");
        System.out.println("Compressed Size:  " + (int) compressedSize + " bits");
        System.out.println("Compression Rate: " + -compressionRate + "%");
        System.out.println("=============================================================");
    }

}