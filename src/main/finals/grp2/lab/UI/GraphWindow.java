package main.finals.grp2.lab.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Main structure:
 * Frame -> mainPanel -> control and visual panel V
 *                                                -> control panel
 *                                                      -> inputPanel -> local components
 *                                                      -> tablePanel -> local components
 *                                                      -> actionPanel -> local components
 *                                                -> control panel
 *                                                      -> TODO: insert Graph Visualizer
 **/
public class GraphWindow {

    // main panel for all components
    protected JPanel mainPanel, titleBarPanel;

    // subpanels for controls and visualizer
    protected JPanel controlPanel, visualPanel;

    // sub panels for controlPanel;
    protected JPanel inputPanel, tablePanel, actionPanel;

    // panels in tablePanel (input and output);
    protected JPanel inputTablePanel, pathwayTablePanel;

    // button controllers
    protected JButton inputFileButton;
    protected final JButton[] actionButtons = new JButton[]{
            new JButton("Play"),
            new JButton("Step Backward"),
            new JButton("Step Forward"),
    };


    public GraphWindow() {
        // init title bar
//        initTitleBarPanel();

        // init tables
        initInputTablePanel();
        initPathwayTablePanel();

        //init control panel
        initInputPanel();
        initTablePanel();
        initActionPanel();

        // init two main sub panels
        initControlPanel();
        initVisualPanel();

        // controls
        controlHandling();

        // init main panel after creating all needed components
        initMainPanel();

        // only load the mainframe after inserting all components
        initMainFrame();

    }

    /**
     * Do not add any components into the main frame directly.
     * Add the component to its corresponding panel instead.
     */
    protected void initMainFrame() {
        JFrame mainFrame = new JFrame();

        mainFrame.add(mainPanel);

        mainFrame.validate();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        mainFrame.setUndecorated(true);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setTitle("Graph");
        mainFrame.setSize(new Dimension(1366, 768));
        mainFrame.setVisible(true);
    }

    protected void initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

//        mainPanel.add(titleBarPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(visualPanel);
    }

    /**
     * Custom title bar
     * Change the layout first in the initMainPanel() method before using.
     */
    protected void initTitleBarPanel() {
        titleBarPanel = new JPanel();
        titleBarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3));

        JButton[] titleButtons = new JButton[]{
                new JButton("X"),
                new JButton("-"),
                new JButton("_"),
        };

        for (JButton button : titleButtons) {
            buttonsPanel.add(button);
        }

        titleBarPanel.add(buttonsPanel);
    }

    /**
     * creates the instance then sets the layout to borderlayout with preferred size of 366x768 (h,w)
     * <p>
     * if you want to add a new row of components create a panel first then insert it here
     */
    protected void initControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(366, 768)); // makes the control panel smaller

        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(tablePanel, BorderLayout.CENTER);
        controlPanel.add(actionPanel, BorderLayout.SOUTH);
    }

    // TODO: INSERT VISUALIZER HERE
    protected void initVisualPanel() {
        visualPanel = new JPanel();
        visualPanel.setPreferredSize(new Dimension(1000, 768)); // makes the visual panel wider than controls
    }

    // TODO: FIX FORMATTING
    protected void initInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));

        JLabel text = new JLabel();
        text.setText("Browse file to input:");
        text.setHorizontalAlignment(SwingConstants.LEFT);

        // components in input panel
        inputFileButton = new JButton("Browse");

        inputPanel.add(text);
        inputPanel.add(inputFileButton);
    }


    protected void initTablePanel() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2, 1));

        tablePanel.add(inputTablePanel);
        tablePanel.add(pathwayTablePanel);
    }

    protected void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames,
                                 JScrollPane scrollPane) {
        defaultTableModel.setColumnIdentifiers(columnNames);
        defaultTableModel.setRowCount(0);
        table.setEnabled(false);
        table.setCellSelectionEnabled(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(10);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 12));
        scrollPane.setVisible(true);
    }

    protected void initInputTablePanel() {
        inputTablePanel = new JPanel();
        inputTablePanel.setLayout(new GridLayout(1, 1));

        DefaultTableModel inputTableModel = new DefaultTableModel();
        JTable inputTable = new JTable(inputTableModel);
        JScrollPane inputTableScrollPane = new JScrollPane(inputTable);

        initializeTable(inputTable, inputTableModel, new String[]{"Weight", "Point A", "Point B"}, inputTableScrollPane);

        inputTablePanel.add(inputTableScrollPane);
    }

    protected void initPathwayTablePanel() {
        pathwayTablePanel = new JPanel();
        pathwayTablePanel.setLayout(new GridLayout(1, 1));

        DefaultTableModel pathwayTableModel = new DefaultTableModel();
        JTable pathwayTable = new JTable(pathwayTableModel);
        JScrollPane pathwayTableScrollPane = new JScrollPane(pathwayTable);

        initializeTable(pathwayTable, pathwayTableModel, new String[]{"Weight", "Point fA", "Point fB"}, pathwayTableScrollPane);

        pathwayTablePanel.add(pathwayTableScrollPane);
    }

    protected void initActionPanel() {
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(3, 1));

        JPanel algoSelectionPanel = new JPanel(); // for the dropdown list

        JPanel playPanel = new JPanel(); // to occupy all space
        JPanel stepPanel = new JPanel(); // to split the space into two

        stepPanel.setLayout(new GridLayout(1, 2));
        playPanel.setLayout(new GridLayout(1, 1));
        algoSelectionPanel.setLayout(new GridLayout(1, 1));

        JComboBox<String> algoSelectionBox = new JComboBox<>(); // Dropdown list
        algoSelectionBox.addItem("None");
        algoSelectionBox.addItem("Depth First Search");
        algoSelectionBox.addItem("Breadth First Search");
        algoSelectionBox.addItem("Dijkstra's Shortest Path");
        algoSelectionPanel.add(algoSelectionBox);

        setActionButtons(); // modify this to change button style

        playPanel.add(actionButtons[0]); // Play/Pause button
        stepPanel.add(actionButtons[1]); // Step Backward Button
        stepPanel.add(actionButtons[2]); // Step Forward Button

        // top: dropdown list, middle: wide play/pause button, bottom: steppers
        actionPanel.add(algoSelectionPanel);
        actionPanel.add(playPanel);
        actionPanel.add(stepPanel);
    }

    protected void setActionButtons() {
        for (JButton button : actionButtons) {
//            button.setBackground(Color.RED);
        }
    }

    private boolean mode = true;

    private void controlHandling() {
        actionButtons[0].addActionListener(e -> {
            changeMode(mode);
        });
    }

    // TODO: play pause action
    private void changeMode(boolean mode) {
        actionButtons[0].setText(mode ? "Pause" : "Play");
        this.mode = !mode;
    }
}
