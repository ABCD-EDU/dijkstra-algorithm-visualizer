package lab.activities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class EvaluatorWindow {
    Color backgroundColor = new Color(0xF4D35E);
    Color accentColor = new Color(0x0D3B66);
    Color accentColor2 = new Color(0xFAF0CA);
    JFrame frame;
    JPanel mainPanel;
    JPanel panelsContainerPanel;

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
        mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(800,500));
        gbc = new GridBagConstraints();
        gbc.weightx = gbc.weighty =1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = gbc.gridwidth =1;
        setPanelsContainerPanel();
        mainPanel.add(panelsContainerPanel, gbc);
        frame.add(mainPanel);

        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setPanelsContainerPanel(){
        panelsContainerPanel = new JPanel();
        panelsContainerPanel.setBackground(backgroundColor);
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
        infixToPostPanel.setBackground(backgroundColor);
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
        infixToPostfixLabel.setForeground(Color.black);
        infixToPostfixInputTextField = new JTextField(20);
        infixToPostfixInputTextField.setBorder(null);
        infixToPostfixButton = new JButton("Convert");
        infixToPostfixButton.setBackground(accentColor);
        infixToPostfixButton.setForeground(Color.white);
        infixToPostfixButton.setFocusPainted(false);
        infixToPostfixButton.addActionListener(new ButtonHandler());

        String[] COLUMN_NAMES = {"Symbol","Postfix Expression","Operator Stack"};
        int[] columnWidths = {60,300,100};
        infixToPostfixTable = new JTable();
        infixToPostfixTableModel = new DefaultTableModel();
        infixToPostfixScrollPane = new JScrollPane();
        initializeTable(infixToPostfixTable, infixToPostfixTableModel, COLUMN_NAMES, columnWidths, infixToPostfixScrollPane);

        outputPostfixLabel = new JLabel("Postfix Expression: ");
        outputPostfixLabel.setForeground(Color.black);
        outputPostfixTextField = new JTextField(20);
        outputPostfixTextField.setFont(new Font("Arial", Font.BOLD, 12));
        outputPostfixTextField.setBackground(accentColor2);
        outputPostfixTextField.setBorder(null);
        outputPostfixTextField.setEditable(false);
    }

    public void setPostfixEvaluationPanel(){
        postfixEvaluationPanel = new JPanel();
        postfixEvaluationPanel.setBackground(backgroundColor);
        gridBagLayout = new GridBagLayout();
        postfixEvaluationPanel.setLayout(gridBagLayout);
        gridBagLayout.rowHeights = new int[]{35,400,35};
//        postfixEvaluationPanel.setBorder(new EmptyBorder(10,10,10,10));
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
        postfixEvaluationLabel.setForeground(Color.black);
        postfixEvaluationTextField = new JTextField(30);
        postfixEvaluationTextField.setBackground(accentColor2);
        postfixEvaluationTextField.setBorder(null);
        postfixEvaluationTextField.setEditable(false);

        String[] COLUMN_NAMES = {"Token", "Operand 1","Operand 2","Operand 3", "Stack"};
        int[] columnsWidths = {50,70,70,70,170};
        postfixEvaluationTable = new JTable();
        postfixEvaluationTableModel = new DefaultTableModel();
        evaluatedScrollPane = new JScrollPane();
        initializeTable(postfixEvaluationTable, postfixEvaluationTableModel, COLUMN_NAMES, columnsWidths, evaluatedScrollPane);

        outputEvaluatedLabel = new JLabel("Evaluated Expression: ");
        outputEvaluatedLabel.setForeground(Color.black);
        outputEvaluatedTextField = new JTextField(30);
        outputEvaluatedTextField.setFont(new Font("Arial", Font.BOLD, 12));
        outputEvaluatedTextField.setBackground(accentColor2);
        outputEvaluatedTextField.setBorder(null);
        outputEvaluatedTextField.setEditable(false);
    }


    void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames, int[] columnsWidth,
                         JScrollPane scrollPane ){
        table.setModel(defaultTableModel);
        defaultTableModel.setColumnIdentifiers(columnNames);
        defaultTableModel.setRowCount(0);
        table.setEnabled(false);
        table.setDefaultRenderer(Object.class,new TableCellRender());
        table.getTableHeader().setReorderingAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setRowHeight(10);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(accentColor);
        table.getTableHeader().setForeground(Color.white);
        table.getTableHeader().setFont(new Font("Arial", Font.ITALIC, 12));
        table.setRowHeight(50);

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
                populateTables();
            }
        }
    }

    class TableCellRender implements TableCellRenderer{

        DefaultTableCellRenderer DEFAULT_RENDER = new DefaultTableCellRenderer();
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = DEFAULT_RENDER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row % 2 == 0) {
                c.setBackground(Color.WHITE);
            } else {
                c.setBackground(new Color(0xF4D35E));
            }
            return c;
        }
    }
}
