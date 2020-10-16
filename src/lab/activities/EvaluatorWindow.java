package lab.activities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorWindow {
    Color backgroundColor, headerColor, uneditableFieldColor;
    Color mainForeground, secondaryForeground;

    JFrame frame;
    JPanel mainPanel;
    JPanel panelsContainerPanel;

    JMenuBar menuBar;
    JMenu about, themes, mode, menuSpacing0, menuSpacing1, menuSpacing2;
    JMenuItem groupMembers, courseSpecifications;
    JMenuItem lightTheme, darkTheme, sluTheme;
    JMenuItem infixToPostfixMode, postfixEvaluationMode;
    boolean isInfixToPostfixMode = true;

    JPanel infixToPostPanel;
    JLabel infixToPostfixLabel;
    JTextField infixToPostfixInputTextField;
    JButton infixToPostfixButton;
    DefaultTableModel infixToPostfixTableModel;
    JTable infixToPostfixTable;
    JLabel outputPostfixLabel;
    JTextField outputPostfixTextField;
    JScrollPane infixToPostfixScrollPane;

    JPanel postfixEvaluationPanel;
    JLabel postfixEvaluationLabel;
    JTextField postfixEvaluationTextField;
    JButton postfixEvaluationButton;
    DefaultTableModel postfixEvaluationTableModel;
    JTable postfixEvaluationTable;
    JLabel outputEvaluatedLabel;
    JTextField outputEvaluatedTextField;
    JScrollPane evaluatedScrollPane;

    GridBagLayout gridBagLayout;
    GridBagConstraints gbc;

    EvaluatorWindow(){
        frame = new JFrame("Converter");
        frame.setIconImage(new ImageIcon("src/DINO.png").getImage());
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(800,500));
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

    public void setMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBorderPainted(false);
        menuBar.setPreferredSize(new Dimension(10, 25));

        menuSpacing0 = new JMenu();
        menuSpacing1 = new JMenu();
        menuSpacing2 = new JMenu();

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
        groupMembers = new JMenuItem("Group Members");
        groupMembers.addActionListener(e -> displayGroupMembers());
        about.add(groupMembers);
        courseSpecifications = new JMenuItem("Course Specification");
        courseSpecifications.addActionListener(e -> displayCourseSpecifications());
        about.add(courseSpecifications);
    }

    private void initializeThemesSubMenu() {
        themes = new JMenu("Themes");
        lightTheme = new JMenuItem("Light");
        lightTheme.addActionListener(e -> setTheme("Light"));
        themes.add(lightTheme);
        darkTheme = new JMenuItem("Dark");
        darkTheme.addActionListener(e -> setTheme("Dark"));
        themes.add(darkTheme);
        sluTheme = new JMenuItem("SLU");
        sluTheme.addActionListener(e -> setTheme("SLU"));
        themes.add(sluTheme);
    }

    private void initializeModesSubMenu() {
        mode = new JMenu("Mode");
        infixToPostfixMode = new JMenuItem("Convert Infix to Postfix Expression");
        infixToPostfixMode.addActionListener(e -> setMode(true));
        mode.add(infixToPostfixMode);
        postfixEvaluationMode = new JMenuItem("Evaluate Postfix Expression");
        postfixEvaluationMode.addActionListener(e -> setMode(false));
        mode.add(postfixEvaluationMode);
    }

    public void setMode(boolean isInfixToPostfixMode) {
        if (isInfixToPostfixMode) {
            this.isInfixToPostfixMode = true;
            infixToPostfixButton.setEnabled(true);
            infixToPostfixButton.setBackground(headerColor);
            infixToPostfixButton.setForeground(secondaryForeground);
            infixToPostfixInputTextField.setBackground(Color.WHITE);
            infixToPostfixInputTextField.setEditable(true);
            infixToPostfixInputTextField.setBorder(new LineBorder(headerColor, 2));
            postfixEvaluationButton.setEnabled(false);
            postfixEvaluationButton.setBackground(uneditableFieldColor);
            postfixEvaluationTextField.setBackground(uneditableFieldColor);
            postfixEvaluationTextField.setText(null);
            postfixEvaluationTextField.setEditable(false);
            postfixEvaluationTextField.setBorder(null);
        } else {
            this.isInfixToPostfixMode = false;
            postfixEvaluationButton.setEnabled(true);
            postfixEvaluationButton.setBackground(headerColor);
            postfixEvaluationButton.setForeground(secondaryForeground);
            postfixEvaluationTextField.setBackground(Color.WHITE);
            postfixEvaluationTextField.setText(null);
            postfixEvaluationTextField.setEditable(true);
            postfixEvaluationTextField.setBorder(new LineBorder(headerColor, 2));
            infixToPostfixButton.setEnabled(false);
            infixToPostfixButton.setBackground(uneditableFieldColor);
            infixToPostfixInputTextField.setBackground(uneditableFieldColor);
            infixToPostfixInputTextField.setText(null);
            infixToPostfixInputTextField.setEditable(false);
            infixToPostfixInputTextField.setBorder(null);
        }
        outputPostfixTextField.setText(null);
        outputEvaluatedTextField.setText(null);
        clearTableContent();
    }

    public void clearTableContent() {
        for( int i = infixToPostfixTableModel.getRowCount() - 1; i >= 0; i-- ) {
            infixToPostfixTableModel.setRowCount(0);
        }
        for( int i = postfixEvaluationTableModel.getRowCount() - 1; i >= 0; i-- ) {
            postfixEvaluationTableModel.setRowCount(0);
        }
    }

    public void setTheme(String theme) {
        String currentMode = "Light";
        if (theme.equalsIgnoreCase("Light")) {
            backgroundColor = Color.WHITE;
            headerColor = new Color(0x222222);
            uneditableFieldColor = new Color(0xE5E5E5);
            mainForeground = Color.BLACK;
            secondaryForeground = Color.WHITE;
            currentMode = "Light";
        } else if (theme.equalsIgnoreCase("Dark")) {
            backgroundColor = new Color(0x333333);
            headerColor = Color.BLACK;
            uneditableFieldColor = new Color(0x4A4A4A);
            mainForeground = Color.WHITE;
            secondaryForeground = Color.WHITE;
            currentMode = "Dark";
        } else if (theme.equalsIgnoreCase("SLU")) {
            backgroundColor = new Color(0xF4D35E);
            headerColor = new Color(0x0D3B66);
            uneditableFieldColor = new Color(0xFAF0CA);
            mainForeground = Color.BLACK;
            secondaryForeground = Color.WHITE;
            currentMode = "SLU";
        }
        UIManager.put("OptionPane.background", headerColor);
        UIManager.put("Panel.background", headerColor);
        UIManager.put("OptionPane.messageForeground", secondaryForeground);
        UIManager.put("Button.background", backgroundColor);
        UIManager.put("Button.foreground", mainForeground);
        UIManager.put("Button.select", headerColor);
        UIManager.put("Button.focus", backgroundColor);

        setBackgrounds();
        setComponentPropertiesAccordingToMode(currentMode);
        setForeGrounds();
        setTableHeaders();
        setBorders();

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
    }

    private void setForeGrounds() {
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

    public void setPanelsContainerPanel(){
        panelsContainerPanel = new JPanel();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(1,1,1,1);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx =1;
        gbc.weighty=1;

        setInfixToPostPanel();
        gbc.gridx=0;
        panelsContainerPanel.add(infixToPostPanel,gbc);
        setPostfixEvaluationPanel();
        gbc.gridx=1;
        panelsContainerPanel.add(postfixEvaluationPanel,gbc);
    }

    void setInfixToPostPanel(){
        infixToPostPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        infixToPostPanel.setLayout(gridBagLayout);
        gridBagLayout.rowHeights = new int[]{30,500,35};
        infixToPostPanel.setBorder(new EmptyBorder(10,10,10,10));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        initializeInfixToPostPanelContents();
        gbc.gridx = 0;
        gbc.gridy = 0;
        infixToPostPanel.add(infixToPostfixLabel, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        infixToPostPanel.add(infixToPostfixInputTextField,gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        infixToPostPanel.add(infixToPostfixButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        infixToPostPanel.add(infixToPostfixScrollPane, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        infixToPostPanel.add(outputPostfixLabel,gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        infixToPostPanel.add(outputPostfixTextField, gbc);
    }

    public void initializeInfixToPostPanelContents(){
        infixToPostfixLabel = new JLabel("Input Infix Expression:");
        infixToPostfixInputTextField = new JTextField(20);
        infixToPostfixInputTextField.addActionListener(e -> evaluateInfix());
        infixToPostfixButton = new JButton("Convert");
        infixToPostfixButton.setFocusPainted(false);
        infixToPostfixButton.addActionListener(e -> evaluateInfix());

        String[] COLUMN_NAMES = {"Symbol","Postfix Expression","Operator Stack"};
        int[] columnWidths = {60,250,150};
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

    public void setPostfixEvaluationPanel(){
        postfixEvaluationPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        postfixEvaluationPanel.setLayout(gridBagLayout);
        gridBagLayout.rowHeights = new int[]{35,500,35};
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        initializePostfixEvaluationPanelContents();
        gbc.gridx = 0;
        gbc.gridy = 0;
        postfixEvaluationPanel.add(postfixEvaluationLabel,gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        postfixEvaluationPanel.add(postfixEvaluationTextField,gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        postfixEvaluationPanel.add(postfixEvaluationButton,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        postfixEvaluationPanel.add(evaluatedScrollPane,gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        postfixEvaluationPanel.add(outputEvaluatedLabel,gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        postfixEvaluationPanel.add(outputEvaluatedTextField,gbc);
    }

    public void initializePostfixEvaluationPanelContents(){
        postfixEvaluationLabel = new JLabel("Postfix Expression: ");
        postfixEvaluationTextField = new JTextField(30);
        postfixEvaluationButton = new JButton("Evaluate");
        postfixEvaluationButton.setFocusPainted(false);
        postfixEvaluationButton.addActionListener(e -> evaluatePostFix());

        String[] COLUMN_NAMES = {"Token", "Operand 1","Operand 2","Operand 3", "Stack"};
        int[] columnsWidths = {50,70,70,70,170};
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

    void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames, int[] columnsWidth,
                         JScrollPane scrollPane ){
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

    public void populateTables(){
        infixToPostfixTableModel.setRowCount(0);
        postfixEvaluationTableModel.setRowCount(0);
        String infix= infixToPostfixInputTextField.getText();
        String[][] table1Values = InfixPostfixEvaluator.toPostfix(infix);
        String postfix = table1Values[table1Values.length-1][1];
        String[][] table2Values = InfixPostfixEvaluator.computePostFix(postfix);
        for (String[] values: table1Values){
            infixToPostfixTableModel.addRow(new Object[]{
                    values[0],
                    values[1],
                    values[2],
            });
        }
        outputPostfixTextField.setText(postfix);
        for (int i = 0; i < table2Values.length-1; i++){
            String[] values = table2Values[i];
            postfixEvaluationTableModel.addRow(new Object[]{
                    values[0],
                    values[1],
                    values[2],
                    values[3],
                    values[4],
            });
        }
        String[] values = table2Values[table2Values.length-1];
        postfixEvaluationTableModel.addRow(new Object[]{values[0], values[1],
                values[2], values[3], removeCommas(values[4]),
        });
        postfixEvaluationTextField.setText(postfix);
        outputEvaluatedTextField.setText(table2Values[table2Values.length-1][4]);
    }

    public void populatePostfixEvaluationTable(){
        postfixEvaluationTableModel.setRowCount(0);
        String postfix = postfixEvaluationTextField.getText();
        String[][] rowValues =  InfixPostfixEvaluator.computePostFix(postfix);
        for (int i = 0; i < rowValues.length-1; i++){
            String[] values = rowValues[i];
            postfixEvaluationTableModel.addRow(new Object[]{
                    values[0],
                    values[1],
                    values[2],
                    values[3],
                    values[4],
            });
        }
        String[] values = rowValues[rowValues.length-1];
        postfixEvaluationTableModel.addRow(new Object[]{ values[0], values[1],
                values[2], values[3], removeCommas(values[4]),
        });
        outputEvaluatedTextField.setText(rowValues[rowValues.length-1][4]);
    }

    /**
     * Helper method that removes the commas within the digits of a given string of numbers.
     */
    private String removeCommas(String string){
        String[] stringArray = string.split("");
        String noCommas = "";
        for (String s : stringArray) {
            if (!s.equals(","))
                noCommas += s;
        }
        return noCommas;
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
        if (postfixEvaluationTextField.getText().length() == 0){
            JOptionPane.showMessageDialog(frame, "Postfix Input Field is Empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            populatePostfixEvaluationTable();
        }catch (InvalidPostfixException IPE) {
            JOptionPane.showMessageDialog(frame, IPE.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayGroupMembers() {
        JOptionPane.showMessageDialog(frame,"     Arevalo Lance\n     Barana Lance Matthew\n     Bayquen Christian" +
                "\n     Cayton Arian Carl\n     De los Trinos Jp","Group 4", JOptionPane.PLAIN_MESSAGE);
    }

    private void displayCourseSpecifications() {
        JOptionPane.showMessageDialog(null, "   Description:    Data Structures\n   Instructor:" +
                "       Roderick Makil\n   Class Code:     9413\n   Class #:           CS 211",
                "Course Specifications", JOptionPane.PLAIN_MESSAGE, null);
    }
}