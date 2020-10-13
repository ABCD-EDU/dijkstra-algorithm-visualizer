package lab.activities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorWindow {

    JFrame frame;
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
    JTextField outputEvaluatedTexField;
    JScrollPane evaluatedScrollPane;

    GridBagLayout gridBagLayout;
    GridBagConstraints gbc;

    EvaluatorWindow(){
        frame = new JFrame("Converter");
        gridBagLayout = new GridBagLayout();
        frame.setMinimumSize(new Dimension(800,500));
        frame.setLayout(gridBagLayout);
        gbc = new GridBagConstraints();
        gbc.weightx = gbc.weighty =1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = gbc.gridwidth =1;
        setPanelsContainerPanel();
        frame.add(panelsContainerPanel,gbc);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        gridBagLayout.rowHeights = new int[]{30,400,30};

//        infixToPostPanel.setBorder(new EmptyBorder(10,10,10,10));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill= GridBagConstraints.BOTH;
        gbc.weightx =1;
        gbc.weighty=1;

//        initializeInfixToPostPanelContents();
        infixToPostfixLabel = new JLabel("Input Infix Expression:");
        infixToPostfixInputTextField = new JTextField(20);
        infixToPostfixButton = new JButton("Convert");
        infixToPostfixButton.addActionListener(new ButtonHandler());

        String[] COLUMN_NAMES = {"Symbol","Postfix Expression","Operator Stack"};
        int[] columnWidths = {80,150,150};
        infixToPostfixTable = new JTable();
        infixToPostfixTableModel = new DefaultTableModel();
        infixToPostfixScrollPane = new JScrollPane();
        initializeTable(infixToPostfixTable, infixToPostfixTableModel, COLUMN_NAMES, columnWidths, infixToPostfixScrollPane);

        outputPostfixLabel = new JLabel("Postfix Expression: ");
        outputPostfixTextField = new JTextField(20);
        outputPostfixTextField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy =0;
        infixToPostPanel.add(infixToPostfixLabel, gbc);
        gbc.gridx=1;
        gbc.gridwidth = 2;
        infixToPostPanel.add(infixToPostfixInputTextField,gbc);
        gbc.gridwidth=1;
        gbc.gridx=3;
        infixToPostPanel.add(infixToPostfixButton,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth = 4;
        infixToPostPanel.add(infixToPostfixScrollPane, gbc);
        gbc.gridwidth=1;
        gbc.gridx=0;
        gbc.gridy++;
        infixToPostPanel.add(outputPostfixLabel,gbc);
        gbc.gridx=1;
        gbc.gridwidth = 2;
        infixToPostPanel.add(outputPostfixTextField, gbc);
    }

    public void initializeInfixToPostPanelContents(){

    }

    public void setPostfixEvaluationPanel(){
        postfixEvaluationPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        gridBagLayout.rowHeights = new int[]{30,400,30};
        postfixEvaluationPanel.setLayout(gridBagLayout);

//        postfixEvaluationPanel.setBorder(new EmptyBorder(10,10,10,10));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx =1;
        gbc.weighty=1;
//        initializePostfixEvaluationPanelContents();
        postfixEvaluationLabel = new JLabel("Post Fix Expression: ");
        postfixEvaluationTextField = new JTextField(20);
        postfixEvaluationTextField.setEditable(false);

        String[] COLUMN_NAMES = {"Token", "Operand 1","Operand 2","Operand 3", "Stack"};
        int[] columnsWidths = {70,70,70,70,150};
        postfixEvaluationTable = new JTable();
        postfixEvaluationTableModel = new DefaultTableModel();
        evaluatedScrollPane = new JScrollPane();
        initializeTable(postfixEvaluationTable, postfixEvaluationTableModel, COLUMN_NAMES, columnsWidths, evaluatedScrollPane);

        outputEvaluatedLabel = new JLabel("Evaluated Expression: ");
        outputEvaluatedTexField = new JTextField(20);
        outputEvaluatedTexField.setEditable(false);

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
        postfixEvaluationPanel.add(outputEvaluatedTexField,gbc);
    }

    public void initializePostfixEvaluationPanelContents(){


    }


    void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames, int[] columnsWidth,
                         JScrollPane scrollPane ){
        table.setModel(defaultTableModel);
        defaultTableModel.setColumnIdentifiers(columnNames);
        defaultTableModel.setRowCount(0);
        table.setEnabled(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setRowHeight(20);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        table.getTableHeader().setBackground(new Color(85,203,211));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
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

    class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==infixToPostfixButton){

            }
        }
    }
}
