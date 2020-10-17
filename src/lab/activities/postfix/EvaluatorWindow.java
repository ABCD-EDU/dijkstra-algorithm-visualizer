package lab.activities.postfix;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class EvaluatorWindow {
    private final String INITIAL_THEME = "Light";

    private Color backgroundColor, headerColor, uneditableFieldColor;
    private Color mainForeground, secondaryForeground;

    private final JFrame frame;
    private final JPanel mainPanel;
    private JPanel panelsContainerPanel;

    private JMenuBar menuBar;
    private JMenu about;
    private JMenu themes;
    private JMenu mode;
    private boolean isInfixToPostfixMode = true;

    private JPanel infixToPostPanel;
    private JLabel infixToPostfixLabel;
    private JTextField infixToPostfixInputTextField;
    private JButton infixToPostfixButton;
    private DefaultTableModel infixToPostfixTableModel;
    private JTable infixToPostfixTable;
    private JLabel outputPostfixLabel;
    private JTextField outputPostfixTextField;
    private JScrollPane infixToPostfixScrollPane;

    private JPanel postfixEvaluationPanel;
    private JLabel postfixEvaluationLabel;
    private JTextField postfixEvaluationTextField;
    private JButton postfixEvaluationButton;
    private DefaultTableModel postfixEvaluationTableModel;
    private JTable postfixEvaluationTable;
    private JLabel outputEvaluatedLabel;
    private JTextField outputEvaluatedTextField;
    private JScrollPane evaluatedScrollPane;

    private String currentTheme = INITIAL_THEME;
    private GridBagLayout gridBagLayout;
    private GridBagConstraints gbc;

    public EvaluatorWindow() {
        frame = new JFrame("Converter");
        frame.setIconImage(new ImageIcon("src/lab/activities/postfix/asset/DINO.png").getImage());
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(800, 500));
        gbc = new GridBagConstraints();
        gbc.weightx = gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = gbc.gridwidth = 1;
        setMenuBar();
        setPanelsContainerPanel();
        setMode(true);
        setTheme("Light");
        mainPanel.add(panelsContainerPanel, gbc);
        frame.add(mainPanel);

        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBorderPainted(false);
        menuBar.setPreferredSize(new Dimension(10, 25));

        JMenu menuSpacing0 = new JMenu();
        JMenu menuSpacing1 = new JMenu();
        JMenu menuSpacing2 = new JMenu();

        menuSpacing0.setEnabled(false);
        menuSpacing1.setEnabled(false);
        menuSpacing2.setEnabled(false);

        initializeAboutSubMenu();
        initializeThemesSubMenu();
        initializeModesSubMenu();

        menuBar.add(menuSpacing0);
        menuBar.add(about);
        menuBar.add(menuSpacing1);
        menuBar.add(themes);
        menuBar.add(menuSpacing2);
        menuBar.add(mode);
        frame.setJMenuBar(menuBar);
    }

    private void initializeAboutSubMenu() {
        about = new JMenu("About");
        JMenuItem groupMembers = new JMenuItem("Group Members");
        groupMembers.addActionListener(e -> displayGroupMembers());
        about.add(groupMembers);
        JMenuItem courseSpecifications = new JMenuItem("Course Specification");
        courseSpecifications.addActionListener(e -> displayCourseSpecifications());
        about.add(courseSpecifications);
    }

    private void initializeThemesSubMenu() {
        themes = new JMenu("Themes");
        JMenuItem lightTheme = new JMenuItem("Light");
        lightTheme.addActionListener(e -> setTheme("Light"));
        themes.add(lightTheme);
        JMenuItem darkTheme = new JMenuItem("Dark");
        darkTheme.addActionListener(e -> setTheme("Dark"));
        themes.add(darkTheme);
        JMenuItem sluTheme = new JMenuItem("SLU");
        sluTheme.addActionListener(e -> setTheme("SLU"));
        themes.add(sluTheme);
    }

    private void initializeModesSubMenu() {
        mode = new JMenu("Mode");
        JMenuItem infixToPostfixMode = new JMenuItem("Convert Infix to Postfix Expression");
        infixToPostfixMode.addActionListener(e -> setMode(true));
        mode.add(infixToPostfixMode);
        JMenuItem postfixEvaluationMode = new JMenuItem("Evaluate Postfix Expression");
        postfixEvaluationMode.addActionListener(e -> setMode(false));
        mode.add(postfixEvaluationMode);
    }

    private void setMode(boolean isInfixToPostfixMode) {
        if (isInfixToPostfixMode) {
            this.isInfixToPostfixMode = true;
            infixToPostfixButton.setEnabled(true);
            infixToPostfixButton.setBackground(headerColor);
            infixToPostfixButton.setForeground(secondaryForeground);
            infixToPostfixInputTextField.setBackground(Color.WHITE);
            setComponentMode(infixToPostfixInputTextField, postfixEvaluationButton, postfixEvaluationTextField);
        } else {
            this.isInfixToPostfixMode = false;
            postfixEvaluationButton.setEnabled(true);
            postfixEvaluationButton.setBackground(headerColor);
            postfixEvaluationButton.setForeground(secondaryForeground);
            postfixEvaluationTextField.setBackground(Color.WHITE);
            postfixEvaluationTextField.setText(null);
            setComponentMode(postfixEvaluationTextField, infixToPostfixButton, infixToPostfixInputTextField);
        }
        outputPostfixTextField.setText(null);
        outputEvaluatedTextField.setText(null);
        clearTableContent();
    }

    private void setComponentMode(JTextField infixToPostfixInputTextField, JButton postfixEvaluationButton, JTextField postfixEvaluationTextField) {
        infixToPostfixInputTextField.setEditable(true);
        infixToPostfixInputTextField.setBorder(new LineBorder(headerColor, 2));
        postfixEvaluationButton.setEnabled(false);
        postfixEvaluationButton.setBackground(uneditableFieldColor);
        postfixEvaluationTextField.setBackground(uneditableFieldColor);
        postfixEvaluationTextField.setText(null);
        postfixEvaluationTextField.setEditable(false);
        postfixEvaluationTextField.setBorder(null);
    }

    private void clearTableContent() {
        for (int i = infixToPostfixTableModel.getRowCount() - 1; i >= 0; i--) {
            infixToPostfixTableModel.setRowCount(0);
        }
        for (int i = postfixEvaluationTableModel.getRowCount() - 1; i >= 0; i--) {
            postfixEvaluationTableModel.setRowCount(0);
        }
    }

    private void setTheme(String theme) {
        if (theme.equalsIgnoreCase("Light"))
            setWhiteThemeProperties();
        else if (theme.equalsIgnoreCase("Dark"))
            setDarkThemeProperties();
        else if (theme.equalsIgnoreCase("SLU"))
            setSLUThemeProperties();

        UIManager.put("OptionPane.background", headerColor);
        UIManager.put("Panel.background", headerColor);
        UIManager.put("OptionPane.messageForeground", secondaryForeground);
        UIManager.put("Button.background", backgroundColor);
        UIManager.put("Button.foreground", mainForeground);
        UIManager.put("Button.select", headerColor);
        UIManager.put("Button.focus", backgroundColor);

        setBackgrounds();
        setComponentPropertiesAccordingToMode(currentTheme);
        setForegrounds();
        setTableHeaders();
        setBorders();

    }

    private void setWhiteThemeProperties() {
        backgroundColor = Color.WHITE;
        headerColor = new Color(0x222222);
        uneditableFieldColor = new Color(0xE5E5E5);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
        currentTheme = "Light";
    }

    private void setDarkThemeProperties() {
        backgroundColor = new Color(0x333333);
        headerColor = Color.BLACK;
        uneditableFieldColor = new Color(0x4A4A4A);
        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
        currentTheme = "Dark";
    }

    private void setSLUThemeProperties() {
        backgroundColor = new Color(0xF4D35E);
        headerColor = new Color(0x0D3B66);
        uneditableFieldColor = new Color(0xFAF0CA);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
        currentTheme = "SLU";
    }

    private void setComponentPropertiesAccordingToMode(String currentMode) {
        if (isInfixToPostfixMode) {
            if (currentMode.equals("Dark")) infixToPostfixInputTextField.setBorder(null);
            else infixToPostfixInputTextField.setBorder(new LineBorder(headerColor, 2));
            infixToPostfixButton.setBackground(headerColor);
            infixToPostfixButton.setForeground(secondaryForeground);
            postfixEvaluationTextField.setForeground(mainForeground);
            postfixEvaluationButton.setBackground(uneditableFieldColor);
            postfixEvaluationTextField.setBackground(uneditableFieldColor);
        } else {
            if (currentMode.equals("Dark")) {
                postfixEvaluationTextField.setBorder(null);
                postfixEvaluationTextField.setForeground(Color.BLACK);
            } else {
                postfixEvaluationTextField.setBorder(new LineBorder(headerColor, 2));
                postfixEvaluationTextField.setForeground(mainForeground);
            }
            postfixEvaluationButton.setBackground(headerColor);
            postfixEvaluationButton.setForeground(secondaryForeground);
            infixToPostfixButton.setBackground(uneditableFieldColor);
            infixToPostfixInputTextField.setBackground(uneditableFieldColor);
        }
    }

    private void setBackgrounds() {
        mainPanel.setBackground(backgroundColor);
        menuBar.setBackground(headerColor);
        panelsContainerPanel.setBackground(backgroundColor);
        infixToPostPanel.setBackground(backgroundColor);
        outputPostfixTextField.setBackground(uneditableFieldColor);
        postfixEvaluationPanel.setBackground(backgroundColor);
        outputEvaluatedTextField.setBackground(uneditableFieldColor);
        infixToPostfixScrollPane.setBackground(headerColor);
        evaluatedScrollPane.setBackground(headerColor);
    }

    private void setForegrounds() {
        about.setForeground(secondaryForeground);
        themes.setForeground(secondaryForeground);
        mode.setForeground(secondaryForeground);
        infixToPostfixLabel.setForeground(mainForeground);
        outputPostfixLabel.setForeground(mainForeground);
        postfixEvaluationLabel.setForeground(mainForeground);
        outputEvaluatedLabel.setForeground(mainForeground);
        outputPostfixTextField.setForeground(mainForeground);
        outputEvaluatedTextField.setForeground(mainForeground);
    }

    private void setTableHeaders() {
        infixToPostfixTable.getTableHeader().setBackground(headerColor);
        infixToPostfixTable.getTableHeader().setForeground(secondaryForeground);
        infixToPostfixTable.getTableHeader().setBorder(new LineBorder(headerColor));
        postfixEvaluationTable.getTableHeader().setBackground(headerColor);
        postfixEvaluationTable.getTableHeader().setForeground(secondaryForeground);
        postfixEvaluationTable.getTableHeader().setBorder(new LineBorder(headerColor));
    }

    private void setBorders() {
        infixToPostfixScrollPane.setBorder(new LineBorder(headerColor, 3));
        evaluatedScrollPane.setBorder(new LineBorder(headerColor, 3));
    }

    public void setPanelsContainerPanel() {
        panelsContainerPanel = new JPanel();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        setInfixToPostPanel();
        gbc.gridx = 0;
        panelsContainerPanel.add(infixToPostPanel, gbc);
        setPostfixEvaluationPanel();
        gbc.gridx = 1;
        panelsContainerPanel.add(postfixEvaluationPanel, gbc);
    }

    private void setInfixToPostPanel() {
        infixToPostPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        infixToPostPanel.setLayout(gridBagLayout);
        gridBagLayout.rowHeights = new int[]{30, 500, 35};
        infixToPostPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        initializeInfixToPostPanelContents();
        setPanelGrid(infixToPostPanel, infixToPostfixLabel, infixToPostfixInputTextField, infixToPostfixButton, infixToPostfixScrollPane);
        gbc.gridx = 0;
        gbc.gridy++;
        infixToPostPanel.add(outputPostfixLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        infixToPostPanel.add(outputPostfixTextField, gbc);
    }

    private void setPanelGrid(JPanel infixToPostPanel, JLabel infixToPostfixLabel, JTextField infixToPostfixInputTextField, JButton infixToPostfixButton, JScrollPane infixToPostfixScrollPane) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        infixToPostPanel.add(infixToPostfixLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        infixToPostPanel.add(infixToPostfixInputTextField, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        infixToPostPanel.add(infixToPostfixButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        infixToPostPanel.add(infixToPostfixScrollPane, gbc);
        gbc.gridwidth = 1;
    }

    private void initializeInfixToPostPanelContents() {
        infixToPostfixLabel = new JLabel("Infix Expression:");
        infixToPostfixInputTextField = new JTextField(20);
        infixToPostfixInputTextField.addActionListener(e -> evaluateInfix());
        infixToPostfixButton = new JButton("Convert");
        infixToPostfixButton.setFocusPainted(false);
        infixToPostfixButton.addActionListener(e -> evaluateInfix());

        String[] COLUMN_NAMES = {"Symbol", "Postfix Expression", "Operator Stack"};
        int[] columnWidths = {60, 250, 150};
        infixToPostfixTable = new JTable();
        infixToPostfixTableModel = new DefaultTableModel();
        infixToPostfixScrollPane = new JScrollPane();
        initializeTable(infixToPostfixTable, infixToPostfixTableModel, COLUMN_NAMES, columnWidths, infixToPostfixScrollPane);

        outputPostfixLabel = new JLabel("Postfix Expression: ");
        outputPostfixTextField = new JTextField(20);
        outputPostfixTextField.addActionListener(e -> evaluatePostFix());
        outputPostfixTextField.setEditable(false);
        outputPostfixTextField.setFont(new Font("Arial", Font.BOLD, 12));
        outputPostfixTextField.setBorder(null);
    }

    private void setPostfixEvaluationPanel() {
        postfixEvaluationPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        postfixEvaluationPanel.setLayout(gridBagLayout);
        gridBagLayout.rowHeights = new int[]{35, 500, 35};
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        initializePostfixEvaluationPanelContents();
        setPanelGrid(postfixEvaluationPanel, postfixEvaluationLabel, postfixEvaluationTextField, postfixEvaluationButton, evaluatedScrollPane);
        gbc.gridy = 2;
        postfixEvaluationPanel.add(outputEvaluatedLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        postfixEvaluationPanel.add(outputEvaluatedTextField, gbc);
    }

    private void initializePostfixEvaluationPanelContents() {
        postfixEvaluationLabel = new JLabel("Postfix Expression: ");
        postfixEvaluationTextField = new JTextField(30);
        postfixEvaluationButton = new JButton("Evaluate");
        postfixEvaluationButton.setFocusPainted(false);
        postfixEvaluationButton.addActionListener(e -> evaluatePostFix());

        String[] COLUMN_NAMES = {"Token", "Operand 1", "Operand 2", "Operand 3", "Stack"};
        int[] columnsWidths = {50, 70, 70, 70, 170};
        postfixEvaluationTable = new JTable();
        postfixEvaluationTableModel = new DefaultTableModel();
        evaluatedScrollPane = new JScrollPane();
        initializeTable(postfixEvaluationTable, postfixEvaluationTableModel, COLUMN_NAMES, columnsWidths, evaluatedScrollPane);

        outputEvaluatedLabel = new JLabel("Evaluated Expression: ");
        outputEvaluatedTextField = new JTextField(30);
        outputEvaluatedTextField.setEditable(false);
        outputEvaluatedTextField.setFont(new Font("Arial", Font.BOLD, 12));
        outputEvaluatedTextField.setBorder(null);
    }

    private void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames, int[] columnsWidth,
                                 JScrollPane scrollPane) {
        table.setModel(defaultTableModel);
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
        scrollPane.setViewportView(table);
        scrollPane.setVisible(true);
    }

    private void populateTables() {
        infixToPostfixTableModel.setRowCount(0);
        postfixEvaluationTableModel.setRowCount(0);
        String infix = infixToPostfixInputTextField.getText();
        System.out.println(infix);
        String[][] postfixValues = InfixPostfixEvaluator.toPostfix(infix);
        String postfix = postfixValues[postfixValues.length - 1][1];
        String[][] evaluateValues = InfixPostfixEvaluator.computePostFix(postfix);
        for (String[] values : postfixValues) {
            infixToPostfixTableModel.addRow(new Object[]{
                    values[0],
                    values[1],
                    values[2],
            });
        }
        outputPostfixTextField.setText(postfix);
        addTableValues(evaluateValues);
        postfixEvaluationTextField.setText(postfix);
        outputEvaluatedTextField.setText(evaluateValues[evaluateValues.length - 1][4]);
    }

    private void addTableValues(String[][] tableValues) {
        for (int i = 0; i < tableValues.length - 1; i++) {
            String[] values = tableValues[i];
            postfixEvaluationTableModel.addRow(new Object[]{
                    values[0],
                    values[1],
                    values[2],
                    values[3],
                    values[4],
            });
        }
        String[] values = tableValues[tableValues.length - 1];
        postfixEvaluationTableModel.addRow(new Object[]{values[0], values[1],
                values[2], values[3], removeCommas(values[4]),
        });
    }

    private void populatePostfixEvaluationTable() {
        System.out.println("test postfix eval");
        postfixEvaluationTableModel.setRowCount(0);
        String postfix = postfixEvaluationTextField.getText();
        String[][] rowValues = InfixPostfixEvaluator.computePostFix(postfix);
        System.out.println("test postfix");
        addTableValues(rowValues);
        System.out.println("postfix sucess");
        outputEvaluatedTextField.setText(rowValues[rowValues.length - 1][4]);
    }

    /**
     * Helper method that removes the commas within the digits of a given string of numbers.
     */
    private String removeCommas(String string) {
        String[] stringArray = string.split("");
        StringBuilder noCommas = new StringBuilder();
        for (String s : stringArray) {
            if (!s.equals(","))
                noCommas.append(s);
        }
        return noCommas.toString();
    }

    private void evaluateInfix() {
        if (infixToPostfixInputTextField.getText().length() == 0) {
            JOptionPane.showMessageDialog(frame, "Infix Input Field is Empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            populateTables();
        } catch (NullPointerException NE) {
            JOptionPane.showMessageDialog(frame, "Infix expression malformed.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidInfixException IIE) {
            JOptionPane.showMessageDialog(frame, IIE.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void evaluatePostFix() {
        if (postfixEvaluationTextField.getText().length() == 0) {
            JOptionPane.showMessageDialog(frame, "Postfix Input Field is Empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            populatePostfixEvaluationTable();
        } catch (InvalidPostfixException IPE) {
            JOptionPane.showMessageDialog(frame, IPE.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
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