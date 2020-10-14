package lab.activities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorWindow {
    Color backgroundColor = new Color(0xF4D35E);
    Color headerColor = new Color(0x0D3B66);
    Color uneditableFieldColor = new Color(0xFAF0CA);
    Color mainForeground = Color.BLACK;
    Color secondaryForeground = Color.WHITE;

    JFrame frame;
    JPanel mainPanel;
    JPanel panelsContainerPanel;

    JMenuBar menuBar;
    JMenu themes;
    JMenu about;
    JMenuItem lightTheme;
    JMenuItem darkTheme;
    JMenuItem sluTheme;

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
        setPanelsContainerPanel();
        initializeMenuBar();
        initializeTheme();
        mainPanel.add(panelsContainerPanel, gbc);
        frame.add(mainPanel);

        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void initializeMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBorderPainted(false);

        themes = new JMenu("Themes");
        themes.setForeground(secondaryForeground);

        lightTheme = new JMenuItem("Light");
        lightTheme.addActionListener(new ButtonHandler());
        themes.add(lightTheme);

        darkTheme = new JMenuItem("Dark");
        darkTheme.addActionListener(new ButtonHandler());
        themes.add(darkTheme);

        sluTheme = new JMenuItem("SLU");
        sluTheme.addActionListener(new ButtonHandler());
        themes.add(sluTheme);

        about = new JMenu("About");
        about.setForeground(secondaryForeground);

        menuBar.add(themes);
        menuBar.add(about);
        frame.setJMenuBar(menuBar);
    }

    public void initializeTheme() {
        mainPanel.setBackground(backgroundColor);
        menuBar.setBackground(headerColor);
        panelsContainerPanel.setBackground(backgroundColor);
        infixToPostPanel.setBackground(backgroundColor);
        infixToPostfixButton.setBackground(headerColor);
        outputPostfixTextField.setBackground(uneditableFieldColor);
        postfixEvaluationPanel.setBackground(backgroundColor);
        postfixEvaluationTextField.setBackground(uneditableFieldColor);
        outputEvaluatedTextField.setBackground(uneditableFieldColor);

        infixToPostfixLabel.setForeground(mainForeground);
        infixToPostfixButton.setForeground(secondaryForeground);
        outputPostfixLabel.setForeground(mainForeground);
        postfixEvaluationLabel.setForeground(mainForeground);
        outputEvaluatedLabel.setForeground(mainForeground);
        outputPostfixTextField.setForeground(mainForeground);
        postfixEvaluationTextField.setForeground(mainForeground);
        outputEvaluatedTextField.setForeground(mainForeground);

        infixToPostfixTable.getTableHeader().setBackground(headerColor);
        infixToPostfixTable.getTableHeader().setForeground(secondaryForeground);
        infixToPostfixTable.getTableHeader().setBorder(new LineBorder(headerColor));

        postfixEvaluationTable.getTableHeader().setBackground(headerColor);
        postfixEvaluationTable.getTableHeader().setForeground(secondaryForeground);
        postfixEvaluationTable.getTableHeader().setBorder(new LineBorder(headerColor));

        infixToPostfixTable.setBorder(new LineBorder(headerColor, 2));
        postfixEvaluationTable.setBorder(new LineBorder(headerColor, 2));
        infixToPostfixScrollPane.setBorder(new LineBorder(backgroundColor));
        evaluatedScrollPane.setBorder(new LineBorder(backgroundColor));
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
        gridBagLayout.rowHeights = new int[]{30,400,35};
        infixToPostPanel.setBorder(new EmptyBorder(10,10,10,10));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill= GridBagConstraints.BOTH;
        gbc.weightx =1;
        gbc.weighty=1;
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
        infixToPostfixInputTextField.setBorder(null);
        infixToPostfixButton = new JButton("Convert");
        infixToPostfixButton.setFocusPainted(false);
        infixToPostfixButton.addActionListener(new ButtonHandler());

        String[] COLUMN_NAMES = {"Symbol","Postfix Expression","Operator Stack"};
        int[] columnWidths = {60,250,150};
        infixToPostfixTable = new JTable();
        infixToPostfixTableModel = new DefaultTableModel();
        infixToPostfixScrollPane = new JScrollPane();
        initializeTable(infixToPostfixTable, infixToPostfixTableModel, COLUMN_NAMES, columnWidths, infixToPostfixScrollPane);

        outputPostfixLabel = new JLabel("Postfix Expression: ");
        outputPostfixTextField = new JTextField(20);
        outputPostfixTextField.setFont(new Font("Arial", Font.BOLD, 12));
        outputPostfixTextField.setBorder(null);
        outputPostfixTextField.setEditable(false);
    }

    public void setPostfixEvaluationPanel(){
        postfixEvaluationPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        postfixEvaluationPanel.setLayout(gridBagLayout);
        gridBagLayout.rowHeights = new int[]{35,400,35};
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx =1;
        gbc.weighty=1;
        initializePostfixEvaluationPanelContents();
        gbc.gridx=0;
        gbc.gridy=0;
        postfixEvaluationPanel.add(postfixEvaluationLabel,gbc);
        gbc.gridx=1;
        gbc.gridwidth =2;
        postfixEvaluationPanel.add(postfixEvaluationTextField,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth = 4;
        postfixEvaluationPanel.add(evaluatedScrollPane,gbc);
        gbc.gridwidth=1;
        gbc.gridy=2;
        postfixEvaluationPanel.add(outputEvaluatedLabel,gbc);
        gbc.gridx=1;
        gbc.gridwidth=2;
        postfixEvaluationPanel.add(outputEvaluatedTextField,gbc);
    }

    public void initializePostfixEvaluationPanelContents(){
        postfixEvaluationLabel = new JLabel("Postfix Expression: ");
        postfixEvaluationTextField = new JTextField(30);
        postfixEvaluationTextField.setBorder(null);
        postfixEvaluationTextField.setEditable(false);

        String[] COLUMN_NAMES = {"Token", "Operand 1","Operand 2","Operand 3", "Stack"};
        int[] columnsWidths = {50,70,70,70,170};
        postfixEvaluationTable = new JTable();
        postfixEvaluationTableModel = new DefaultTableModel();
        evaluatedScrollPane = new JScrollPane();
        initializeTable(postfixEvaluationTable, postfixEvaluationTableModel, COLUMN_NAMES, columnsWidths, evaluatedScrollPane);

        outputEvaluatedLabel = new JLabel("Evaluated Expression: ");
        outputEvaluatedTextField = new JTextField(30);
        outputEvaluatedTextField.setFont(new Font("Arial", Font.BOLD, 12));
        outputEvaluatedTextField.setBorder(null);
        outputEvaluatedTextField.setEditable(false);
    }


    void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames, int[] columnsWidth,
                         JScrollPane scrollPane ){
        table.setModel(defaultTableModel);
        defaultTableModel.setColumnIdentifiers(columnNames);
        defaultTableModel.setRowCount(0);
        table.setEnabled(false);
        table.setDefaultRenderer(Object.class, new TableCellRender());
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
        for (String[] values: table2Values){
            postfixEvaluationTableModel.addRow(new Object[]{
                    values[0],
                    values[1],
                    values[2],
                    values[3],
                    values[4],
            });
        }
        postfixEvaluationTextField.setText(postfix);
        outputEvaluatedTextField.setText(table2Values[table2Values.length-1][3]);
    }

    class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==infixToPostfixButton){
                if (infixToPostfixInputTextField.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame, "Input Field is Empty.",
                            "Alert", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    populateTables();
                } catch (NullPointerException NE) {
                    JOptionPane.showMessageDialog(frame, "Infix expression malformed.",
                            "Alert", JOptionPane.WARNING_MESSAGE);
                } catch (InvalidInfixException IE) {
                    JOptionPane.showMessageDialog(frame, IE.getMessage(),
                            "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if (e.getSource() == lightTheme)
                setWindowLightTheme();
            else if (e.getSource() == darkTheme)
                setWindowDarkTheme();
            else if (e.getSource() == sluTheme)
                setWindowSluTheme();
        }

    }

    private void setWindowLightTheme() {
        backgroundColor = new Color(0xFFFFFF);
        headerColor = new Color(0x111111);
        uneditableFieldColor = new Color(0xE5E5E5);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
        infixToPostfixInputTextField.setBorder(new LineBorder(headerColor, 2));
        initializeTheme();
    }

    private void setWindowDarkTheme() {
        backgroundColor = new Color(0x333333);
        headerColor = new Color(0x000000);
        uneditableFieldColor = new Color(0x4A4A4A);
        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
        infixToPostfixInputTextField.setBorder(null);
        initializeTheme();
    }

    private void setWindowSluTheme() {
        backgroundColor = new Color(0xF4D35E);
        headerColor = new Color(0x0D3B66);
        uneditableFieldColor = new Color(0xFAF0CA);
        mainForeground = Color.BLACK;
        secondaryForeground = Color.WHITE;
        infixToPostfixInputTextField.setBorder(null);
        initializeTheme();
    }

    static class TableCellRender implements TableCellRenderer{
        DefaultTableCellRenderer DEFAULT_RENDER = new DefaultTableCellRenderer();
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = DEFAULT_RENDER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setBackground(Color.WHITE);
            return component;
        }
    }
}
