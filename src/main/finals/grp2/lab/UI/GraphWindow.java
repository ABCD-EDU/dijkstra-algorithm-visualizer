package main.finals.grp2.lab.UI;

import main.finals.grp2.lab.Graph;
import main.finals.grp2.lab.PairList;
import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.Dictionary;
import main.finals.grp2.util.List;
import main.finals.grp2.util.Queue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.security.InvalidParameterException;
import java.util.EmptyStackException;
import java.util.Stack;

// COMMITS: WINDOW CENTERED, LAYOUT FIXES, PROMPT FILE SELECTION (NO FUNCTIONALITY)



/**
 * Main structure:
 * Frame -> mainPanel -> control and visual panel V
 *                                                -> control panel
 *                                                      -> inputPanel -> local components
 *                                                      -> tablePanel -> local components
 *                                                      -> actionPanel -> local components
 *                                                -> control panel
 *                                                      ->
 * TODO: Add radiobox show/hide weights
 * TODO: Add JLabel to preview total cost for pathfind
 * TODO: JOptionPanes
 * TODO: Graph Visualizer Color Design
 * TODO: Change path of files to actual chosen file path
 * TODO: Disable control panel when file not yet selected
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

    // Table-related components:
    // dTable components are added into visualPanel
    protected JPanel inputTablePanel, pathwayTablePanel, dTablePanel;
    protected JTable inputTable, pathwayTable, dTable;
    protected JPanel inputMainPanel, pathwayMainPanel, dTableMainPanel;
    protected DefaultTableModel inputTableModel, pathwayTableModel, dTableModel;
    protected JScrollPane inputTableScrollPane, pathwayTableScrollPane, dTableScrollPane;
    protected JPanel inputLabelPanel, pathwayLabelPanel, dTableLabelPanel;
    protected JLabel inputLabel, pathwayLabel, dTableLabel;

    // button controllers
    protected JButton browseButton;
    protected final JButton[] actionButtons = new JButton[]{
            new JButton("Play"),
            new JButton("<<"),
            new JButton("<"),
            new JButton(">"),
            new JButton(">>"),
            new JButton("Set")
    };


    protected JLabel algorithmLabel;
    protected JPanel fromToPanel, algoLabelPanel, algoSelectionPanel, playPanel, stepPanel;

    protected JTextField fromField;
    protected JComboBox<String> toComboBox;

    protected JComboBox<String> algoSelectionBox;


    // THEME: Bento
    protected Color mainColor = new Color(0x2D394D);
    protected Color secondaryColor = new Color(0x4A768D);
    protected Color accentColor = new Color(0xF87A90);
    protected Color mainForeground = Color.WHITE;
    protected Color secondaryForeground = Color.BLACK;

    protected File textFile;
    protected PairList<String, String> edgeWeightPairList;
    protected final String EDGE_W_PAIRLIST_DELIMITER = "=Fr0Mt0=";

    private VisualizerThread visualizerThread;
    private boolean paused = true;
    private Graph graph;
    private List<Graph.Vertex> vertices;
    private ArrayList<String> verticesIDList;
    private Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>> pathQueue;
    private Stack<Dictionary.Node<Graph.Vertex, Graph.Vertex>> pathToShowStack;
    private ArrayList<Dictionary.Node<Graph.Vertex, Graph.Vertex>> pathShownList;
    private String from;
    private String to;
    private boolean dijkstraChosen = false;

    public GraphWindow() {
        // init title bar
//        initTitleBarPanel();

        // init tables
        initInputTablePanel();
        initPathwayTablePanel();
        initDTablePanel();

        //init control panel
        initInputPanel();
        initTablePanel();
        initActionPanel();

        initControlPanel();
        initVisualPanel();

        // controls
        setActionButtonsActionListeners();

        // init main panel after creating all needed components
        initMainPanel();

        initTheme();

        disableControlPanel();
        browseButton.setEnabled(true);
        actionButtons[0].setEnabled(false);
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

        fromToPanel.setBackground(mainColor);
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
        browseButton.setBackground(accentColor);
        browseButton.setForeground(mainForeground);
        browseButton.setFocusPainted(false);
        browseButton.setBorder(new EmptyBorder(5, 0, 5, 0));

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
        visualPanel.setPreferredSize(new Dimension(1100, 550)); // makes the visual panel wider than controls
    }

    // TODO: FIX FORMATTING
    protected void initInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(0,100,0,0));
        inputPanel.setLayout(new GridLayout(1, 2));

        JLabel text = new JLabel();
        text.setHorizontalAlignment(SwingConstants.LEFT);

        // components in input panel
        browseButton = new JButton("Browse");
        browseButton.addActionListener((e) -> promptFileSelection());

        inputPanel.add(text);
        inputPanel.add(browseButton);
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
        table.setForeground(Color.WHITE);
        table.setRowHeight(50);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        scrollPane.setVisible(true);
    }

    protected void initInputTablePanel() {
        inputTablePanel = new JPanel();
        inputTablePanel.setBorder(new EmptyBorder(5,0,10,0));
        inputTablePanel.setLayout(new GridLayout(1, 1));

        inputTableModel = new DefaultTableModel();
        inputTable = new JTable(inputTableModel);
        inputTable.getTableHeader().setFont(new Font("Default", Font.PLAIN, 11));
        inputTable.getTableHeader().setBorder(new LineBorder(accentColor, 1));
        inputTableScrollPane = new JScrollPane(inputTable);
        inputTableScrollPane.setBorder(new EmptyBorder(0 ,0 ,0 ,0));

        initializeTable(inputTable, inputTableModel, new String[]{"Weight", "Point A", "Point B"}, inputTableScrollPane);

        inputTablePanel.add(inputTableScrollPane);
    }

    protected void initDTablePanel() {
        dTableMainPanel = new JPanel(new GridLayout(2,1));
        dTableMainPanel.setPreferredSize(new Dimension(1100, 250));

        dTablePanel = new JPanel();
        dTablePanel.setBorder(new EmptyBorder(5,0,10,0));
        dTablePanel.setLayout(new GridLayout(1, 1));

        dTableModel = new DefaultTableModel();
        dTable = new JTable(dTableModel);
        dTable.getTableHeader().setFont(new Font("Default", Font.PLAIN, 11));
        dTable.getTableHeader().setBorder(new LineBorder(accentColor, 1));
        dTableScrollPane = new JScrollPane(dTable);
        dTableScrollPane.setBorder(new EmptyBorder(0 ,0 ,0 ,0));

        initializeTable(dTable, dTableModel, new String[]{"End", "Cost", "Path"}, dTableScrollPane);
        dTablePanel.add(dTableScrollPane);

        dTableLabelPanel = new JPanel(new GridLayout(1, 1));
        dTableLabelPanel.setBorder(new EmptyBorder(0,10,0,0));
        dTableLabel = new JLabel("Pathway to Every Node:");
        dTableLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dTableLabelPanel.add(dTableLabel);
        dTableMainPanel.setLayout(new BoxLayout(dTableMainPanel, BoxLayout.Y_AXIS));

        dTableMainPanel.add(dTableLabelPanel);
        dTableMainPanel.add(dTablePanel);
    }

    protected void initPathwayTablePanel() {
        pathwayTablePanel = new JPanel();
        pathwayTablePanel.setBorder(new EmptyBorder(5,0,10,0));
        pathwayTablePanel.setLayout(new GridLayout(1, 1));

        pathwayTableModel = new DefaultTableModel();
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
        actionPanel.setLayout(new GridLayout(5, 1));

        fromToPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        algoLabelPanel = new JPanel(new GridLayout(1, 1));
        algoSelectionPanel = new JPanel(new GridLayout(1, 1));
        playPanel = new JPanel(new GridLayout(1, 1));
        stepPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        fromField = new JTextField();
        toComboBox = new JComboBox<>();
        toComboBox.setEnabled(false);

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

        initializeAlgoSelectionBox();
        algoSelectionPanel.add(algoSelectionBox);

        setActionButtons(); // modify this to change button style

        fromToPanel.add(fromField);
        fromToPanel.add(toComboBox);
        fromToPanel.add(actionButtons[5]); // set
        playPanel.add(actionButtons[0]); // Play/Pause button
        stepPanel.add(actionButtons[1]); // Go to start Button
        stepPanel.add(actionButtons[2]); // Step backward Button
        stepPanel.add(actionButtons[3]); // Step forward Button
        stepPanel.add(actionButtons[4]); // Go to end Button

        // top: dropdown list, middle: wide play/pause button, bottom: steppers
        actionPanel.add(fromToPanel);
        actionPanel.add(algoLabelPanel);
        actionPanel.add(algoSelectionPanel);
        actionPanel.add(playPanel);
        actionPanel.add(stepPanel);
    }

    private void initializeAlgoSelectionBox() {
        algoSelectionBox = new JComboBox<String>(); // Dropdown list
        algoSelectionBox.setFocusable(false);
        algoSelectionBox.addItem("None");
        algoSelectionBox.addItem("Depth First Search");
        algoSelectionBox.addItem("Breadth First Search");
        algoSelectionBox.addItem("Dijkstra's Shortest Path");
        algoSelectionBox.addItemListener(e -> {
            visualPanel.remove(graphCanvas);
            String selection = algoSelectionBox.getSelectedItem()+"";
            if (selection.equals("Dijkstra's Shortest Path")) {
                dijkstraChosen = true;
                toComboBox.setEnabled(true);
                setVisualPanelProperties(dijkstraChosen);
            }else {
                toComboBox.setEnabled(false);
                dijkstraChosen = false;
                if (dTableMainPanel != null) {
                    visualPanel.remove(graphCanvas);
                    visualPanel.remove(dTableMainPanel);
                }
                setVisualPanelProperties(dijkstraChosen);
            }
            visualPanel.revalidate();
            visualPanel.repaint();
        });
    }


    private void initializeVerticesIDList() {
        verticesIDList = new ArrayList<>();
        for (int i = 0; i < vertices.getSize(); i++)
            verticesIDList.insert(vertices.getElement(i).ID);
    }

    private void updateToComboBox() {
        toComboBox.removeAll();
        for (int i = 0; i < verticesIDList.getSize(); i++) {
            toComboBox.addItem(verticesIDList.getElement(i));
        }
        //TODO: Combo box aesthetics
    }

    private void initializePathQueue() {
        // TODO: Validate input here
        from = fromField.getText();
        // TODO: edit canvas label output
        to = "TODO EDIT THIS";
        graphCanvas.setLabels(algoSelectionBox.getSelectedItem()+"", from, to);
        graphCanvas.repaint();
        visualizerThread = new VisualizerThread();
        switch (algoSelectionBox.getSelectedItem()+"") {
            case "None":
                pathQueue = null;
                break;
            case "Depth First Search":
                pathQueue = graph.depthFirstSearch(from);
                break;
            case "Breadth First Search":
                pathQueue = graph.breadthFirstSearch(from);
                break;
            default:
                System.out.println("Combo Box Invalid Item");
                break;
        }
    }

    private void initializePathQueueDijkstra() {
        // TODO: input validation
        from = fromField.getText();
        to = toComboBox.getSelectedItem()+"";
        System.out.println(graph.toString());
        PairList<String[], Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>>> pathsList = graph.dijkstra(from);
        PairList.Node<String[], Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>>> selectedPath
                = pathsList.getAt(verticesIDList.indexOf(to));
        pathQueue = selectedPath.val;
        updateDTable(pathsList, verticesIDList.indexOf(to));
        visualizerThread = new VisualizerThread();
    }

    private void updateDTable(
            PairList<String[], Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>>> pathsList
            , int chosenIdx) {
        dTableModel.setRowCount(0);
        for (int i = 0; i <pathsList.size(); i++)
            dTableModel.addRow(pathsList.getAt(i).key);
        dTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row == chosenIdx ? accentColor : secondaryColor);
                // TODO: change highlight color for higlighted row
                return c;
            }
        });
    }

    private void initializePathStackToShow() {
        Stack<Dictionary.Node<Graph.Vertex, Graph.Vertex>> temp = new Stack<>();
        pathToShowStack = new Stack<>();
        while (!pathQueue.isEmpty()) {
            temp.push(pathQueue.dequeue());
        }
        while (!temp.isEmpty()) {
            pathToShowStack.push(temp.pop());
        }
    }

    protected void setActionButtons() {
        for (JButton button : actionButtons) {
//            button.setBackground(Color.RED);
        }
    }

    private synchronized void setActionButtonsActionListeners() {
        actionButtons[5].addActionListener(e -> { // setFromTo
            if (!readyToSet()) return;
            if (dijkstraChosen)
                initializePathQueueDijkstra();
            else
                initializePathQueue(); // initialize data members (table content, path Queue)
            initializePathStackToShow(); // initialize path to be shown
            pathShownList = new ArrayList<>();  // create empty list of shown paths
            graphCanvas.setPath(pathShownList); // set visualizer's path to show
            updatePathTableValues(); // update path table - empty for now
        });

        actionButtons[0].addActionListener(e -> { // play
            if (!readyToVisualize()) return;
            changeMode(paused);
            if (!paused) {
                if (visualizerThread.getState().toString().equals("TERMINATED")){
                    visualizerThread = new VisualizerThread();
                    visualizerThread.start();
                }else {
                    visualizerThread.start();
                }
            }else {
                visualizerThread.interrupt();
            }
        });

        actionButtons[1].addActionListener(e -> { // skip to start
            if (!readyToVisualize()) return;
            for (int i = pathShownList.getSize()-1; i > -1; i--) {
                pathToShowStack.push(pathShownList.getElement(i));
                pathShownList.remove(i);
            }
            graphCanvas.setPath(pathShownList);
            updatePathTableValues();
            JOptionPane.showMessageDialog(mainFrame, "Beginning of path reached.",
                    "Successful", JOptionPane.INFORMATION_MESSAGE);
        });

        actionButtons[2].addActionListener(e -> { // backward
            if (!readyToVisualize()) return;
            try{
                pathToShowStack.push(pathShownList.getElement(pathShownList.getSize()-1));
            }catch (ArrayIndexOutOfBoundsException e1) {
                JOptionPane.showMessageDialog(mainFrame, "Beginning of path reached.",
                        "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
            pathShownList.remove(pathShownList.getSize()-1);
            graphCanvas.setPath(pathShownList);
            updatePathTableValues();
        });

        actionButtons[3].addActionListener(e -> { // forward
            if (!readyToVisualize()) return;
            try {
                pathShownList.insert(pathToShowStack.pop());
            }catch (EmptyStackException e1) {
                JOptionPane.showMessageDialog(mainFrame, "End of path reached.",
                        "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
            graphCanvas.setPath(pathShownList);
            updatePathTableValues();
        });

        actionButtons[4].addActionListener(e -> { // skip to end
            if (!readyToVisualize()) return;
            while (!pathToShowStack.isEmpty()) {
                pathShownList.insert(pathToShowStack.pop());
            }
            graphCanvas.setPath(pathShownList);
            updatePathTableValues();
            JOptionPane.showMessageDialog(mainFrame, "End of path reached.",
                    "Successful", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private boolean readyToVisualize() {
        if (pathToShowStack == null) {
            JOptionPane.showMessageDialog(mainFrame, "Please press the set button first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean readyToSet() {
        if (algoSelectionBox.getSelectedItem().toString().equals("None")) {
            JOptionPane.showMessageDialog(mainFrame, "Please select an algorithm first",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // check if inputted value in from exists
        String from = fromField.getText();
        boolean found = false;
        for (int i = 0; i < verticesIDList.getSize(); i++) {
            if (from.equals(verticesIDList.getElement(i))) {
                found = true;
                break;
            }
        }
        if (found) return true;
        JOptionPane.showMessageDialog(mainFrame, "Inputted starting vertex ID does not exist",
                "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    private void promptFileSelection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        int choice = fileChooser.showOpenDialog(mainFrame);
        if (choice == JFileChooser.APPROVE_OPTION) { // initialize graph
            textFile = fileChooser.getSelectedFile();
            // TODO: change path to actual textFile
            graph = new Graph(new File("src/main/finals/grp2/lab/data/in.csv"));
            vertices = graph.getVertices();
            initializeVerticesIDList();
            initializeEdgeWeightPairList();
            setVisualPanelProperties(dijkstraChosen);
            updateToComboBox();
            enableControlPanel();
            actionButtons[0].setEnabled(true);
            dTableModel.setRowCount(0);
            inputTableModel.setRowCount(0);
            pathwayTableModel.setRowCount(0);
            try {
                initializeInputTableValues();
            }catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("CHOSEN: " + textFile.getName());
        } else {
            System.out.println("File Selection Aborted");
        }
    }

    private void initializeEdgeWeightPairList() {
        edgeWeightPairList = new PairList<>();
        for (int i = 0; i < vertices.getSize(); i++) {
            Graph.Vertex v = vertices.getElement(i);
            for (int j = 0; j < v.edges.size(); j++) {
                String edge = v.ID + EDGE_W_PAIRLIST_DELIMITER + v.edges.getAt(j).key.ID;
                edgeWeightPairList.put(edge, String.valueOf(v.edges.getAt(j).val));
            }
        }
    }

    private void initializeInputTableValues() throws IOException {
        inputTableModel.setRowCount(0);
        BufferedReader reader = new BufferedReader(new FileReader("src/main/finals/grp2/lab/data/in.csv"));
        String line = "";
        reader.readLine();
        line = reader.readLine();
        while (line != null) {
            String[] data = line.split(",");
            inputTableModel.addRow(new Object[]{
                    data[0],
                    data[1],
                    data[2]
            });
            line = reader.readLine();
        }
    }

    private void updatePathTableValues() {
        pathwayTableModel.setRowCount(0);
        for (int i = 0; i < pathShownList.getSize(); i++) {
            Dictionary.Node<Graph.Vertex, Graph.Vertex> edge = pathShownList.getElement(i);
            String weight = "N/A";
            try {
                weight = edgeWeightPairList.get(edge.key.ID + EDGE_W_PAIRLIST_DELIMITER + edge.val.ID+"");
            }catch (InvalidParameterException e) {
//                e.printStackTrace();
            }
            pathwayTableModel.addRow(new Object[]{
                    weight,
                    edge.key.ID,
                    edge.val.ID
            });
        }
    }

    private void setVisualPanelProperties(boolean dTablePresent) {
        visualPanel.removeAll();
        graphCanvas = new GraphVisualizerCanvas(graph, secondaryColor);
        if (dTablePresent)
            visualPanel.setPreferredSize(new Dimension(1100,550));
        else
            visualPanel.setPreferredSize(new Dimension(1100,800));
        graphCanvas.setPreferredSize(visualPanel.getPreferredSize());
        visualPanel.add(graphCanvas);
        if (dTablePresent)
            visualPanel.add(dTableMainPanel);
        visualPanel.repaint();
        visualPanel.revalidate();
    }

    // TODO: play pause action
    private void changeMode(boolean mode) {
        actionButtons[0].setText(mode ? "Pause" : "Play");
        this.paused = !mode;
        if (!paused)
            disableControlPanel();
        else
            enableControlPanel();
    }

    private void disableControlPanel() {
        browseButton.setEnabled(false);
        for (int i = 1; i < actionButtons.length; i++) {
            actionButtons[i].setEnabled(false);
        }
        fromField.setEnabled(false);
        toComboBox.setEnabled(false);
        algoSelectionBox.setEnabled(false);
    }

    private void enableControlPanel() {
        browseButton.setEnabled(true);
        for (int i = 1; i < actionButtons.length; i++) {
            actionButtons[i].setEnabled(true);
        }
        algoSelectionBox.setEnabled(true);
        fromField.setEnabled(true);
        if (dijkstraChosen) toComboBox.setEnabled(true);
        else toComboBox.setEnabled(false);
    }

    private class VisualizerThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    pathShownList.insert(pathToShowStack.pop());
                }catch (EmptyStackException e) {
                    JOptionPane.showMessageDialog(mainFrame, "End of Path Already Reached",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    changeMode(paused);
                    return;
                }
                graphCanvas.setPath(pathShownList);
                updatePathTableValues();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    break;
                }
                if (pathToShowStack.isEmpty()) {
                    changeMode(paused);
                    JOptionPane.showMessageDialog(mainFrame, "End of Path Reached",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }

    }
}