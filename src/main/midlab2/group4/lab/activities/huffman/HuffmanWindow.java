package main.midlab2.group4.lab.activities.huffman;

import javax.swing.*;
import java.awt.*;

public class HuffmanWindow {
    private final JFrame frame = new JFrame();
    private final JPanel backgroundPanel = new JPanel(); // TRY frame.getContentPane.setBackground(color);
    private final JPanel mainPanel = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();
    private JMenu aboutSubMenu;
    private JMenu themesSubMenu;

    private Color backgroundColor, headerColor, uneditableFieldColor;
    private Color mainForeground, secondaryForeground;

    private GridBagConstraints gbc;

    public HuffmanWindow() {
        frame.setTitle("Huffman Coding");
        frame.setIconImage(new ImageIcon("src/assets/Tree.png").getImage());
        frame.setMinimumSize(new Dimension(800, 500));
        backgroundPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.weightx = gbc.weighty = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;

        setMenuBar();
        setMainPanel();
//        setTheme("SLU");

        backgroundPanel.add(mainPanel, gbc);
        frame.add(backgroundPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    private void setMainPanel() {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(1,1,1,1);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1;
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
}