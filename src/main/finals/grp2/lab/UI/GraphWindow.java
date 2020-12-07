package main.finals.grp2.lab.UI;

import main.finals.grp2.lab.Graph;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// COMMITS: WINDOW CENTERED, LAYOUT FIXES, PROMPT FILE SELECTION (NO FUNCTIONALITY)



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

    protected JFrame mainFrame;

    // main panel for all components
    protected JPanel mainPanel, titleBarPanel;

    // sub-panels for controls and visualizer
    protected JPanel controlPanel;
    protected JPanel visualPanel;
    GraphVisualizerCanvas graphCanvas;

    // sub panels for controlPanel;
    protected JPanel inputPanel, tablePanel, actionPanel;

    // panels in tablePanel (input and output);
    protected JPanel inputTablePanel, pathwayTablePanel;

    // button controllers
    protected JButton inputFileButton;
    protected final JButton[] actionButtons = new JButton[]{
            new JButton("Play"),
            new JButton("<<"),
            new JButton("<"),
            new JButton(">"),
            new JButton(">>"),
    };

    protected JLabel inputLabel, pathwayLabel, algorithmLabel;

    protected JPanel inputMainPanel, pathwayMainPanel;

    protected JPanel inputLabelPanel, pathwayLabelPanel;

    protected JTable inputTable, pathwayTable;

    protected JScrollPane inputTableScrollPane, pathwayTableScrollPane;


    protected JPanel algoLabelPanel, algoSelectionPanel, playPanel, stepPanel;

    protected JComboBox<String> algoSelectionBox;

    protected File textFile;

    // THEME: Bento
    protected Color mainColor = new Color(0x2D394D);
    protected Color secondaryColor = new Color(0x4A768D);
    protected Color accentColor = new Color(0xF87A90);
    protected Color mainForeground = Color.WHITE;
    protected Color secondaryForeground = Color.BLACK;

    private Graph graph;

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

        initTheme();

        // only load the mainframe after inserting all components
        initMainFrame();

    }

    /**
     * Do not add any components into the main frame directly.
     * Add the component to its corresponding panel instead.
     */
    protected void initMainFrame() {
        mainFrame = new JFrame();

        mainFrame.add(mainPanel);

        mainFrame.validate();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        mainFrame.setUndecorated(true);
        mainFrame.pack();
        mainFrame.setResizable(true);
        mainFrame.setTitle("Graph Visualizer");
        mainFrame.setSize(new Dimension(1500, 800));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((d.width / 2) - mainFrame.getWidth() / 2, (d.height / 2) - mainFrame.getHeight() / 2);
        mainFrame.setVisible(true);
    }

    protected void initTheme() {
        setBackgrounds();
        setForegrounds();
        setButtons();
    }

    protected void setBackgrounds() {
        controlPanel.setBackground(mainColor);
        visualPanel.setBackground(secondaryColor);

        inputPanel.setBackground(mainColor);
        tablePanel.setBackground(mainColor);
        actionPanel.setBackground(mainColor);

        inputMainPanel.setBackground(mainColor);
        pathwayMainPanel.setBackground(mainColor);

        inputTablePanel.setBackground(mainColor);
        pathwayTablePanel.setBackground(mainColor);

        inputLabelPanel.setBackground(mainColor);
        pathwayLabelPanel.setBackground(mainColor);

        algoLabelPanel.setBackground(mainColor);
        algoSelectionPanel.setBackground(mainColor);
        playPanel.setBackground(mainColor);
        stepPanel.setBackground(mainColor);

        inputTable.setBackground(secondaryColor);
        inputTable.getTableHeader().setBackground(accentColor);
        pathwayTable.setBackground(secondaryColor);
        pathwayTable.getTableHeader().setBackground(accentColor);

        algoSelectionBox.setBackground(secondaryColor);
    }

    protected void setForegrounds() {
        inputLabel.setForeground(mainForeground);
        pathwayLabel.setForeground(mainForeground);
        algorithmLabel.setForeground(mainForeground);

        inputTable.getTableHeader().setForeground(mainForeground);
        pathwayTable.getTableHeader().setForeground(mainForeground);

        algoSelectionBox.setForeground(mainForeground);

    }

    protected void setButtons() {
        inputFileButton.setBackground(accentColor);
        inputFileButton.setForeground(mainForeground);
        inputFileButton.setFocusPainted(false);
        inputFileButton.setBorder(new EmptyBorder(5, 0, 5, 0));

        for (JButton button : actionButtons) {
            button.setBackground(secondaryColor);
            button.setForeground(mainForeground);
            button.setFocusPainted(false);
            button.setBorder(new EmptyBorder(0, 0, 0, 0));
        }
        actionButtons[0].setBackground(accentColor);
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
        controlPanel.setBorder(new EmptyBorder(35, 35, 60, 35));
        controlPanel.setPreferredSize(new Dimension(400, 800)); // makes the control panel smaller

        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(tablePanel, BorderLayout.CENTER);
        controlPanel.add(actionPanel, BorderLayout.SOUTH);
    }

    // TODO: INSERT VISUALIZER HERE
    protected void initVisualPanel() {
        visualPanel = new JPanel();
        visualPanel.setPreferredSize(new Dimension(1100, 800)); // makes the visual panel wider than controls
    }

    // TODO: FIX FORMATTING
    protected void initInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(0,100,0,0));
        inputPanel.setLayout(new GridLayout(1, 2));

        JLabel text = new JLabel();
        text.setHorizontalAlignment(SwingConstants.LEFT);

        // components in input panel
        inputFileButton = new JButton("Browse");
        inputFileButton.addActionListener((e) -> promptFileSelection());

        inputPanel.add(text);
        inputPanel.add(inputFileButton);
    }

    // TODO: CLEAN
    protected void initTablePanel() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2,1));

        inputLabelPanel = new JPanel(new GridLayout(1, 1));
        inputLabelPanel.setBorder(new EmptyBorder(0,10,0,0));
        inputLabel = new JLabel("Input Table");
        inputLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inputLabelPanel.add(inputLabel);

        pathwayLabelPanel = new JPanel(new GridLayout(1, 1));
        pathwayLabelPanel.setBorder(new EmptyBorder(0,10,0,0));
        pathwayLabel = new JLabel("Pathway Table");
        pathwayLabel.setHorizontalAlignment(SwingConstants.LEFT);
        pathwayLabelPanel.add(pathwayLabel);

        inputMainPanel = new JPanel();
        pathwayMainPanel = new JPanel();

        inputMainPanel.setLayout(new BoxLayout(inputMainPanel, BoxLayout.Y_AXIS));
        pathwayMainPanel.setLayout(new BoxLayout(pathwayMainPanel, BoxLayout.Y_AXIS));

        // add label panel and table panel
        inputMainPanel.add(inputLabelPanel);
        inputMainPanel.add(inputTablePanel);

        pathwayMainPanel.add(pathwayLabelPanel);
        pathwayMainPanel.add(pathwayTablePanel);

        tablePanel.add(inputMainPanel);
        tablePanel.add(pathwayMainPanel);
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
        inputTablePanel.setBorder(new EmptyBorder(5,0,10,0));
        inputTablePanel.setLayout(new GridLayout(1, 1));

        DefaultTableModel inputTableModel = new DefaultTableModel();
        inputTable = new JTable(inputTableModel);
        inputTable.getTableHeader().setFont(new Font("Default", Font.PLAIN, 11));
        inputTable.getTableHeader().setBorder(new LineBorder(accentColor, 1));
        inputTableScrollPane = new JScrollPane(inputTable);
        inputTableScrollPane.setBorder(new EmptyBorder(0 ,0 ,0 ,0));

        initializeTable(inputTable, inputTableModel, new String[]{"Weight", "Point A", "Point B"}, inputTableScrollPane);

        inputTablePanel.add(inputTableScrollPane);
    }

    protected void initPathwayTablePanel() {
        pathwayTablePanel = new JPanel();
        pathwayTablePanel.setBorder(new EmptyBorder(5,0,10,0));
        pathwayTablePanel.setLayout(new GridLayout(1, 1));

        DefaultTableModel pathwayTableModel = new DefaultTableModel();
        pathwayTable = new JTable(pathwayTableModel);
        pathwayTable.getTableHeader().setFont(new Font("Default", Font.PLAIN, 11));
        pathwayTable.getTableHeader().setBorder(new LineBorder(accentColor, 1));
        pathwayTableScrollPane = new JScrollPane(pathwayTable);
        pathwayTableScrollPane.setBorder(new EmptyBorder(0 ,0 ,0 ,0));

        initializeTable(pathwayTable, pathwayTableModel, new String[]{"Weight", "Point fA", "Point fB"}, pathwayTableScrollPane);

        pathwayTablePanel.add(pathwayTableScrollPane);
    }

    protected void initActionPanel() {
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(4, 1));

        algoLabelPanel = new JPanel(new GridLayout(1, 1));
        algoSelectionPanel = new JPanel(new GridLayout(1, 1));
        playPanel = new JPanel(new GridLayout(1, 1));
        stepPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        algoLabelPanel.setPreferredSize(new Dimension(100, 0));
        algoSelectionPanel.setPreferredSize(new Dimension(100, 20   ));
        playPanel.setPreferredSize(new Dimension(100, 40));
        stepPanel.setPreferredSize(new Dimension(100, 40));

        algoLabelPanel.setBorder(new EmptyBorder(10, 30, 0, 30));
        algoSelectionPanel.setBorder(new EmptyBorder(0,30,5,30));
        playPanel.setBorder(new EmptyBorder(0,30,5,30));
        stepPanel.setBorder(new EmptyBorder(0,30,5,30));

        algorithmLabel = new JLabel("Algorithm");
        algorithmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        algoLabelPanel.add(algorithmLabel);

        algoSelectionBox = new JComboBox<>(); // Dropdown list
        algoSelectionBox.setFocusable(false);
        algoSelectionBox.addItem("None");
        algoSelectionBox.addItem("Depth First Search");
        algoSelectionBox.addItem("Breadth First Search");
        algoSelectionBox.addItem("Dijkstra's Shortest Path");
        algoSelectionPanel.add(algoSelectionBox);

        setActionButtons(); // modify this to change button style

        playPanel.add(actionButtons[0]); // Play/Pause button
        stepPanel.add(actionButtons[1]); // Go to start Button
        stepPanel.add(actionButtons[2]); // Step backward Button
        stepPanel.add(actionButtons[3]); // Step forward Button
        stepPanel.add(actionButtons[4]); // Go to end Button

        // top: dropdown list, middle: wide play/pause button, bottom: steppers
        actionPanel.add(algoLabelPanel);
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

    private void promptFileSelection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        int choice = fileChooser.showOpenDialog(mainFrame);
        if (choice == JFileChooser.APPROVE_OPTION) { // initialize graph
            textFile = fileChooser.getSelectedFile();
            setVisualPanelProperties();
            System.out.println("CHOSEN: " + textFile.getName());
        } else {
            System.out.println("File Selection Aborted");
        }
    }

    private void setVisualPanelProperties() {
        graph = new Graph(new File("src/main/finals/grp2/lab/data/in.csv"));
        graphCanvas = new GraphVisualizerCanvas(graph, secondaryColor);
        graphCanvas.setPreferredSize(visualPanel.getPreferredSize());
        visualPanel.add(graphCanvas);
        visualPanel.repaint();
        visualPanel.revalidate();
    }

    // TODO: play pause action
    private void changeMode(boolean mode) {
        actionButtons[0].setText(mode ? "Pause" : "Play");
        this.mode = !mode;
    }
}