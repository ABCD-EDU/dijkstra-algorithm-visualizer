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
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.security.InvalidParameterException;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

// COMMITS: WINDOW CENTERED, LAYOUT FIXES, PROMPT FILE SELECTION (NO FUNCTIONALITY)



/**
 * Main structure:
 * Frame -> mainPanel -> control and visualizer panel V
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
    protected final String INITIAL_THEME = "Light";
    protected String currentTheme = INITIAL_THEME;
    protected Color mainColor;
    protected Color secondaryColor;
    protected Color highlightColor;
    protected Color headerColor;
    protected Color accentColor;
    protected Color vertexColor;
    protected Color edgesColor;

    protected Color mainForeground;
    protected Color secondaryForeground;
    protected Color mainButtonForeground;
    protected Color headerForeground;
    protected Color vertexForeground;

    protected JFrame mainFrame;

    protected final JMenuBar menuBar = new JMenuBar();
    protected JMenu aboutSubMenu;
    protected JMenu themesSubMenu;

    // main panel for all components
    protected JPanel mainPanel, titleBarPanel;

    // sub-panels for controls and visualizer
    protected JPanel controlPanel;
    protected JPanel visualizerPanel;
    GraphVisualizerCanvas graphCanvas;

    // sub panels for controlPanel;
    protected JPanel inputPanel, tablePanel, actionPanel;

    // Table-related components:
    // dTable components are added into visualizerPanel
    protected JPanel inputTablePanel, pathwayTablePanel, dTablePanel;
    protected JTable inputTable, pathwayTable, dTable;
    protected JPanel inputMainPanel, pathwayMainPanel, dTableMainPanel;
    protected DefaultTableModel inputTableModel, pathwayTableModel, dTableModel;
    protected JScrollPane inputTableScrollPane, pathwayTableScrollPane, dTableScrollPane;
    protected JPanel inputLabelPanel, pathwayLabelPanel, dTableLabelPanel;
    protected JLabel inputLabel, pathwayLabel, dTableLabel;

    // button controllers
    protected JButton inputFileButton = new JButton("Browse");
    protected JButton setButton = new JButton("Set");
    protected JButton playButton = new JButton("Play");
    protected JButton skipForwardButton = new JButton(">>");
    protected JButton skipBackwardButton = new JButton("<<");
    protected JButton incrementButton = new JButton(">");
    protected JButton decrementButton = new JButton("<");
    JButton[] actionButtonsArray = {playButton, setButton, inputFileButton, skipForwardButton,
            skipBackwardButton,incrementButton, decrementButton};

    protected JLabel algorithmLabel;
    protected JPanel fromToLabel, fromToPanel, algoLabelPanel, algoSelectionPanel, playPanel, stepPanel;

    protected JTextField fromField;
    protected JComboBox<String> toComboBox;

    protected JComboBox<String> algoSelectionBox;

    protected File textFile;
    protected PairList<String, String> edgeWeightPairList;
    protected final String EDGE_W_PAIRLIST_DELIMITER = "=Fr0Mt0=";

    protected VisualizerThread visualizerThread;
    protected boolean paused = true;
    protected Graph graph;
    protected List<Graph.Vertex> vertices;
    protected ArrayList<String> verticesIDList;
    protected Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>> pathQueue;
    protected Stack<Dictionary.Node<Graph.Vertex, Graph.Vertex>> pathToShowStack;
    protected ArrayList<Dictionary.Node<Graph.Vertex, Graph.Vertex>> pathShownList;
    protected String from;
    protected String to;
    protected boolean dijkstraChosen = false;

    public GraphWindow() {
        //TABLES
        initializeInputTablePanel();
        initializePathwayTablePanel();
        initializeDijsktraTablePanel();

        //CONTROL PANEL
        initializeInputPanel();
        initializeTablePanel();
        initializeActionPanel();

        initializeControlPanel();
        initializeVisualizerPanel();

        // controls
        setActionButtonsActionListeners();

        // init main panel after creating all needed components
        initializeMainPanel();

        disableControlPanel();
        inputFileButton.setEnabled(true);
        playButton.setEnabled(false);
        // only load the mainframe after inserting all components

        initializeMenuBar();

        initializeMainFrame();

        initializeTheme(INITIAL_THEME);
    }

    /**
     * Do not add any components into the main frame directly.
     * Add the component to its corresponding panel instead.
     */
    protected void initializeMainFrame() {
        mainFrame = new JFrame("Graph Visualizer");
        mainFrame.setIconImage(new ImageIcon("src/main/finals/grp2/lab/asset/Graph.png").getImage());
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(mainPanel);

        mainFrame.validate();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setResizable(true);
        mainFrame.setSize(new Dimension(1500, 900));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((d.width / 2) - mainFrame.getWidth() / 2, (d.height / 2) - mainFrame.getHeight() / 2);
        mainFrame.setVisible(true);
    }

    protected void initializeMenuBar() {
        menuBar.setPreferredSize(new Dimension(10, 29));
        menuBar.setBorderPainted(false);

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
    }

    protected void initializeAboutSubMenu() {
        aboutSubMenu = new JMenu("About");

        JMenuItem groupMembers = new JMenuItem("Group Members");
        groupMembers.setMnemonic(KeyEvent.VK_G);
        groupMembers.addActionListener(e -> displayGroupMembers());
        aboutSubMenu.add(groupMembers);

        JMenuItem courseSpecification = new JMenuItem("Course Specifications");
        courseSpecification.setMnemonic(KeyEvent.VK_C);
        courseSpecification.addActionListener(e -> displayCourseSpecifications());
        aboutSubMenu.add(courseSpecification);
    }

    protected void initializeThemesSubMenu() {
        themesSubMenu = new JMenu("Themes");

        JMenuItem lightTheme = new JMenuItem("Light");
        lightTheme.setMnemonic(KeyEvent.VK_L);
        lightTheme.addActionListener(e -> initializeTheme("Light"));
        themesSubMenu.add(lightTheme);

        JMenuItem darkTheme = new JMenuItem("Dark");
        darkTheme.setMnemonic(KeyEvent.VK_D);
        darkTheme.addActionListener(e -> initializeTheme("Dark"));
        themesSubMenu.add(darkTheme);

        JMenuItem sluTheme = new JMenuItem("SLU");
        sluTheme.setMnemonic(KeyEvent.VK_S);
        sluTheme.addActionListener(e -> initializeTheme("SLU"));
        themesSubMenu.add(sluTheme);

        JMenuItem bentoTheme = new JMenuItem("Bento");
        bentoTheme.setMnemonic(KeyEvent.VK_B);
        bentoTheme.addActionListener(e -> initializeTheme("Bento"));
        themesSubMenu.add(bentoTheme);

        JMenuItem draculaTheme = new JMenuItem("Dracula");
        draculaTheme.setMnemonic(KeyEvent.VK_A);
        draculaTheme.addActionListener(e -> initializeTheme("Dracula"));
        themesSubMenu.add(draculaTheme);

        JMenuItem gruvboxTheme = new JMenuItem("Gruvbox");
        gruvboxTheme.setMnemonic(KeyEvent.VK_G);
        gruvboxTheme.addActionListener(e -> initializeTheme("Gruvbox"));
        themesSubMenu.add(gruvboxTheme);

        JMenuItem godspeedTheme = new JMenuItem("Godspeed");
        godspeedTheme.setMnemonic(KeyEvent.VK_E);
        godspeedTheme.addActionListener(e -> initializeTheme("Godspeed"));
        themesSubMenu.add(godspeedTheme);

        JMenuItem olive = new JMenuItem("Olive");
        olive.setMnemonic(KeyEvent.VK_V);
        olive.addActionListener(e -> initializeTheme("Olive"));
        themesSubMenu.add(olive);

        JMenuItem christmas = new JMenuItem("Christmas");
        christmas.setMnemonic(KeyEvent.VK_H);
        christmas.addActionListener(e -> initializeTheme("Christmas"));
        themesSubMenu.add(christmas);
    }

    protected void initializeTheme(String theme) {
        if (theme.equalsIgnoreCase("Light")) setLightThemeProperties();
        else if (theme.equalsIgnoreCase("Dark")) setDarkThemeProperties();
        else if (theme.equalsIgnoreCase("SLU")) setSLUThemeProperties();
        else if (theme.equalsIgnoreCase("Bento")) setBentoThemeProperties();
        else if (theme.equalsIgnoreCase("Dracula")) setDraculaThemeProperties();
        else if (theme.equalsIgnoreCase("Godspeed")) setGodspeedThemeProperties();
        else if (theme.equalsIgnoreCase("Gruvbox")) setGruvboxThemeProperties();
        else if (theme.equalsIgnoreCase("Olive")) setOliveThemeProperties();
        else if (theme.equalsIgnoreCase("Christmas")) setChristmasThemeProperties();

        UIManager.put("Panel.background", mainColor);
        UIManager.put("OptionPane.background", mainColor);
        UIManager.put("OptionPane.messageForeground", mainForeground);
        UIManager.put("Button.background", headerColor);
        UIManager.put("Button.foreground", headerForeground);
        UIManager.put("Button.select", mainColor);
        UIManager.put("Button.focus", headerColor);
        UIManager.put("Button.border", new EmptyBorder(5, 10, 5, 10));

        setButtonColors();
        setBackgrounds();
        setForegrounds();
        setBorders();

        setVisualizerPanelProperties(dijkstraChosen);
    }

    protected void setBackgrounds() {
        menuBar.setBackground(headerColor);

        controlPanel.setBackground(mainColor);
        visualizerPanel.setBackground(secondaryColor);

        inputPanel.setBackground(mainColor);
        tablePanel.setBackground(mainColor);
        actionPanel.setBackground(mainColor);

        inputMainPanel.setBackground(mainColor);
        inputLabelPanel.setBackground(mainColor);
        inputTablePanel.setBackground(mainColor);
        inputTable.setBackground(secondaryColor);
        inputTable.getTableHeader().setBackground(headerColor);
        inputTableScrollPane.setBackground(headerColor);

        pathwayMainPanel.setBackground(mainColor);
        pathwayLabelPanel.setBackground(mainColor);
        pathwayTablePanel.setBackground(mainColor);
        pathwayTable.setBackground(secondaryColor);
        pathwayTable.getTableHeader().setBackground(headerColor);
        pathwayTableScrollPane.setBackground(headerColor);

        dTableMainPanel.setBackground(secondaryColor);
        dTable.setBackground(mainColor);
        dTable.getTableHeader().setBackground(headerColor);
        dTableScrollPane.setBackground(headerColor);

        fromToLabel.setBackground(mainColor);
        fromToPanel.setBackground(mainColor);
        algoLabelPanel.setBackground(mainColor);
        algoSelectionPanel.setBackground(mainColor);
        algoSelectionBox.setBackground(secondaryColor);
        playPanel.setBackground(mainColor);
        stepPanel.setBackground(mainColor);

    }

    protected void setForegrounds() {
        aboutSubMenu.setForeground(headerForeground);
        themesSubMenu.setForeground(headerForeground);

        inputLabel.setForeground(mainForeground);
        pathwayLabel.setForeground(mainForeground);
        algorithmLabel.setForeground(mainForeground);
        dTableLabel.setForeground(secondaryForeground);

        inputTable.setForeground(secondaryForeground);
        inputTable.getTableHeader().setForeground(headerForeground);
        pathwayTable.setForeground(secondaryForeground);
        pathwayTable.getTableHeader().setForeground(headerForeground);
        dTable.setForeground(mainForeground);
        dTable.getTableHeader().setForeground(headerForeground);

        fromToLabel.setForeground(mainForeground);
        algoSelectionBox.setForeground(mainForeground);
    }

    protected void setBorders() {
        controlPanel.setBorder(new EmptyBorder(25, 35, 60, 35));
        inputPanel.setBorder(new EmptyBorder(0,100,0,0));

        inputLabelPanel.setBorder(new EmptyBorder(0,10,0,0));
        inputTablePanel.setBorder(new EmptyBorder(5,0,10,0));
        inputTable.getTableHeader().setBorder(new LineBorder(headerColor, 1));
        inputTableScrollPane.setBorder(new EmptyBorder(0 ,0 ,0 ,0));

        pathwayLabelPanel.setBorder(new EmptyBorder(0,10,0,0));
        pathwayTablePanel.setBorder(new EmptyBorder(5,0,10,0));
        pathwayTable.getTableHeader().setBorder(new LineBorder(headerColor, 1));
        pathwayTableScrollPane.setBorder(new EmptyBorder(0 ,0 ,0 ,0));

        dTableMainPanel.setBorder(new EmptyBorder(0, 150, 0, 150));
        dTableLabelPanel.setBorder(new EmptyBorder(0,0,0,0));
        dTableLabel.setBorder(new EmptyBorder(10, 0, 5, 0));
        dTablePanel.setBorder(new EmptyBorder(0,0,0,0));
        dTable.getTableHeader().setBorder(new LineBorder(headerColor, 1));
        dTableScrollPane.setBorder(new EmptyBorder(0 ,0 ,0 ,0));

        algoLabelPanel.setBorder(new EmptyBorder(20, 35, 5, 35));
        algoSelectionPanel.setBorder(new EmptyBorder(0,35,5,35));
        playPanel.setBorder(new EmptyBorder(0,35,5,35));
        stepPanel.setBorder(new EmptyBorder(0,35,5,35));
    }

    protected void setButtonColors() {
        for (JButton button : actionButtonsArray) {
            if (button == inputFileButton || button == playButton) {
                button.setBackground(accentColor);
                button.setForeground(mainButtonForeground);
            } else {
                button.setBackground(secondaryColor);
                button.setForeground(secondaryForeground);
            }
            button.setFocusPainted(false);
            button.setBorder(new EmptyBorder(0, 0, 0, 0));
        }
        inputFileButton.setBorder(new EmptyBorder(6, 0, 6, 0));
    }


    protected void initializeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

//        mainPanel.add(titleBarPanel);
        mainPanel.add(controlPanel);
        mainPanel.add(visualizerPanel);
    }

    /**
     * creates the instance then sets the layout to borderlayout with preferred size of 366x768 (h,w)
     * <p>
     * if you want to add a new row of components create a panel first then insert it here
     */
    protected void initializeControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setMinimumSize(new Dimension(400, 900)); // makes the control panel smaller
        controlPanel.setMaximumSize(new Dimension(400, 900)); // makes the control panel smaller

        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(tablePanel, BorderLayout.CENTER);
        controlPanel.add(actionPanel, BorderLayout.SOUTH);
    }

    protected void initializeVisualizerPanel() {
        visualizerPanel = new JPanel();
        visualizerPanel.setMinimumSize(new Dimension(1100, 900)); // makes the visual panel wider than controls
        visualizerPanel.setMaximumSize(new Dimension(1100, 900)); // makes the visual panel wider than controls
    }

    protected void initializeInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1, 2));

        JLabel textSpacing = new JLabel();
        textSpacing.setHorizontalAlignment(SwingConstants.LEFT);
        String text = "<html> Accepts .csv & .txt files <br> File format: weight, pointA, pointB <br> First Line should indicate what kind of graph (Directed, Undirected) </html>";
        inputFileButton.setToolTipText(text);
        inputFileButton.addActionListener((e) -> {
            try {
                promptFileSelection();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        inputPanel.add(textSpacing);
        inputPanel.add(inputFileButton);
    }

    protected void promptFileSelection() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        int choice = fileChooser.showOpenDialog(mainFrame);
        if (choice == JFileChooser.APPROVE_OPTION) { // initialize graph
            textFile = fileChooser.getSelectedFile();
            if (!validFile()) {
                displayFileError();
                return;
            }
            graph = new Graph(new File(textFile.getPath()));
            vertices = graph.getVertices();
            initializeVerticesIDList();
            initializeEdgeWeightPairList();
            setVisualizerPanelProperties(dijkstraChosen);
            updateToComboBox();
            enableControlPanel();
            playButton.setEnabled(true);
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

    protected void initializeTablePanel() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2,1));

        inputLabelPanel = new JPanel(new GridLayout(1, 1));
        inputLabel = new JLabel("Input Table");
        inputLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inputLabelPanel.add(inputLabel);

        pathwayLabelPanel = new JPanel(new GridLayout(1, 1));
        pathwayLabel = new JLabel("Pathway Table");
        pathwayLabel.setHorizontalAlignment(SwingConstants.LEFT);
        pathwayLabelPanel.add(pathwayLabel);

        inputMainPanel = new JPanel();
        inputMainPanel.setLayout(new BoxLayout(inputMainPanel, BoxLayout.Y_AXIS));
        inputMainPanel.add(inputLabelPanel);
        inputMainPanel.add(inputTablePanel);

        pathwayMainPanel = new JPanel();
        pathwayMainPanel.setLayout(new BoxLayout(pathwayMainPanel, BoxLayout.Y_AXIS));
        pathwayMainPanel.add(pathwayLabelPanel);
        pathwayMainPanel.add(pathwayTablePanel);

        tablePanel.add(inputMainPanel);
        tablePanel.add(pathwayMainPanel);
    }

    protected void initializeInputTablePanel() {
        inputTablePanel = new JPanel();
        inputTablePanel.setLayout(new GridLayout(1, 1));

        inputTableModel = new DefaultTableModel();
        inputTable = new JTable(inputTableModel);
        inputTable.getTableHeader().setFont(new Font("Default", Font.PLAIN, 11));
        inputTableScrollPane = new JScrollPane(inputTable);

        initializeTable(inputTable, inputTableModel, new String[]{"Weight", "Point A", "Point B"}, inputTableScrollPane);

        inputTablePanel.add(inputTableScrollPane);
    }

    protected void initializePathwayTablePanel() {
        pathwayTablePanel = new JPanel();
        pathwayTablePanel.setLayout(new GridLayout(1, 1));

        pathwayTableModel = new DefaultTableModel();
        pathwayTable = new JTable(pathwayTableModel);
        pathwayTable.getTableHeader().setFont(new Font("Default", Font.PLAIN, 11));
        pathwayTableScrollPane = new JScrollPane(pathwayTable);

        initializeTable(pathwayTable, pathwayTableModel, new String[]{"Weight", "Point fA", "Point fB"}, pathwayTableScrollPane);

        pathwayTablePanel.add(pathwayTableScrollPane);
    }

    protected void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames,
                                   JScrollPane scrollPane) {
        defaultTableModel.setColumnIdentifiers(columnNames);
        defaultTableModel.setRowCount(0);
        table.setEnabled(false);
        table.setCellSelectionEnabled(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(Color.WHITE);
        table.setRowHeight(30);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(0);
        for (int i = 0; i < table.getColumnCount(); i++){
            table.setDefaultRenderer(table.getColumnClass(i),renderer);
        }

        table.updateUI();
        scrollPane.setVisible(true);
    }

    protected void initializeDijsktraTablePanel() {
        dTableMainPanel = new JPanel(new GridLayout(2,1));

        dTablePanel = new JPanel();
        dTablePanel.setLayout(new GridLayout(1, 1));
        dTablePanel.setPreferredSize(new Dimension(700,245));

        dTableModel = new DefaultTableModel();
        dTable = new JTable(dTableModel);
        dTable.getTableHeader().setFont(new Font("Default", Font.PLAIN, 11));
        dTableScrollPane = new JScrollPane(dTable);

        initializeTable(dTable, dTableModel, new String[]{"End", "Cost", "Path"}, dTableScrollPane);
        dTablePanel.add(dTableScrollPane);
        dTable.getColumnModel().getColumn(2).setPreferredWidth(400);

        dTableLabelPanel = new JPanel(new GridLayout(1, 1));
        dTableLabel = new JLabel("(Dijsktra) Pathway to Every Node:");
        dTableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dTableLabelPanel.add(dTableLabel);
        dTableMainPanel.setLayout(new BoxLayout(dTableMainPanel, BoxLayout.Y_AXIS));

        dTableMainPanel.add(dTableLabelPanel);
        dTableMainPanel.add(dTablePanel);

        dTableLabelPanel.setBackground(secondaryColor);
    }

    protected void initializeActionPanel() {
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(6, 1));

        fromToLabel = new JPanel(new GridLayout(1, 3, 9, 5));
        fromToPanel = new JPanel(new GridLayout(1, 3, 9, 5));
        algoLabelPanel = new JPanel(new GridLayout(1, 1));
        algoSelectionPanel = new JPanel(new GridLayout(1, 1));
        playPanel = new JPanel(new GridLayout(1, 1));
        stepPanel = new JPanel(new GridLayout(1, 4, 5, 5));

        fromField = new JTextField();
        toComboBox = new JComboBox<>();
        toComboBox.setEnabled(false);

        algoLabelPanel.setPreferredSize(new Dimension(100, 40));
        algorithmLabel = new JLabel("Algorithm");
        algorithmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        algoLabelPanel.add(algorithmLabel);

        initializeAlgoSelectionBox();
        algoSelectionPanel.add(algoSelectionBox);

        fromToLabel.add(new JLabel("         From                     To"));
        fromToPanel.add(fromField);
        fromToPanel.add(toComboBox);
        fromToPanel.add(setButton);
        playPanel.add(playButton);
        stepPanel.add(skipBackwardButton);
        stepPanel.add(skipForwardButton);
        stepPanel.add(decrementButton);
        stepPanel.add(incrementButton);

        // top: dropdown list, middle: wide play/pause button, bottom: steppers
        actionPanel.add(fromToLabel);
        actionPanel.add(fromToPanel);
        actionPanel.add(algoLabelPanel);
        actionPanel.add(algoSelectionPanel);
        actionPanel.add(playPanel);
        actionPanel.add(stepPanel);
    }

    protected void initializeAlgoSelectionBox() {
        algoSelectionBox = new JComboBox<String>(); // Dropdown list
        algoSelectionBox.setFocusable(false);
        algoSelectionBox.addItem("None");
        algoSelectionBox.addItem("Depth First Search");
        algoSelectionBox.addItem("Breadth First Search");
        algoSelectionBox.addItem("Dijkstra's Shortest Path");
        algoSelectionBox.addItemListener(e -> {
            visualizerPanel.remove(graphCanvas);
            String selection = algoSelectionBox.getSelectedItem()+"";
            if (selection.equals("Dijkstra's Shortest Path")) {
                dijkstraChosen = true;
                toComboBox.setEnabled(true);
                setVisualizerPanelProperties(dijkstraChosen);
            } else {
                toComboBox.setEnabled(false);
                dijkstraChosen = false;
                if (dTableMainPanel != null) {
                    visualizerPanel.remove(graphCanvas);
                    visualizerPanel.remove(dTableMainPanel);
                }
                setVisualizerPanelProperties(dijkstraChosen);
            }
            visualizerPanel.revalidate();
            visualizerPanel.repaint();
        });
    }

    protected void updateToComboBox() {
        toComboBox.removeAllItems();
        for (int i = 0; i < verticesIDList.getSize(); i++) {
            toComboBox.addItem(verticesIDList.getElement(i));
        }
    }

    protected void initializePathQueue() {
        from = fromField.getText();
        // TODO: edit canvas label output
        to = "";
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

    protected void initializePathQueueDijkstra() {
        from = fromField.getText();
        to = toComboBox.getSelectedItem()+"";
        graphCanvas.setLabels(algoSelectionBox.getSelectedItem()+"", from, to);
        PairList<String[], Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>>> pathsList = graph.dijkstra(from);
        PairList.Node<String[], Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>>> selectedPath
                = pathsList.getAt(verticesIDList.indexOf(to));
        pathQueue = selectedPath.val;
        updateDTable(pathsList, verticesIDList.indexOf(to));
        visualizerThread = new VisualizerThread();
    }

    protected void updateDTable(PairList<String[], Queue<Dictionary.Node<Graph.Vertex, Graph.Vertex>>> pathsList, int chosenIdx) {
        dTableModel.setRowCount(0);
        for (int i = 0; i <pathsList.size(); i++)
            dTableModel.addRow(pathsList.getAt(i).key);
        dTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row == chosenIdx ? highlightColor : mainColor);
                return c;
            }
        });
    }

    protected void initializePathStackToShow() {
        Stack<Dictionary.Node<Graph.Vertex, Graph.Vertex>> temp = new Stack<>();
        pathToShowStack = new Stack<>();
        while (!pathQueue.isEmpty()) {
            temp.push(pathQueue.dequeue());
        }
        while (!temp.isEmpty()) {
            pathToShowStack.push(temp.pop());
        }
    }

    protected synchronized void setActionButtonsActionListeners() {
        setButton.addActionListener(e -> { // setFromTo
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

        playButton.addActionListener(e -> {
            if (!readyToVisualize()) return;
            changeMode(paused);
            if (!paused) {
                if (visualizerThread.getState().toString().equals("TERMINATED"))
                    visualizerThread = new VisualizerThread();
                visualizerThread.start();
            }else {
                visualizerThread.interrupt();
            }
        });

        skipBackwardButton.addActionListener(e -> {
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

        skipForwardButton.addActionListener(e -> {
            if (!readyToVisualize()) return;
            while (!pathToShowStack.isEmpty()) {
                pathShownList.insert(pathToShowStack.pop());
            }
            graphCanvas.setPath(pathShownList);
            updatePathTableValues();
            JOptionPane.showMessageDialog(mainFrame, "End of path reached.",
                    "Successful", JOptionPane.INFORMATION_MESSAGE);
        });

        decrementButton.addActionListener(e -> {
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

        incrementButton.addActionListener(e -> {
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
    }

    protected void updatePathTableValues() {
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

    protected boolean readyToVisualize() {
        if (pathToShowStack == null) {
            JOptionPane.showMessageDialog(mainFrame, "Please press the set button first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    protected boolean readyToSet() {
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

    protected void initializeVerticesIDList() {
        verticesIDList = new ArrayList<>();
        for (int i = 0; i < vertices.getSize(); i++)
            verticesIDList.insert(vertices.getElement(i).ID);
    }

    protected void initializeEdgeWeightPairList() {
        edgeWeightPairList = new PairList<>();
        for (int i = 0; i < vertices.getSize(); i++) {
            Graph.Vertex v = vertices.getElement(i);
            for (int j = 0; j < v.edges.size(); j++) {
                String edge = v.ID + EDGE_W_PAIRLIST_DELIMITER + v.edges.getAt(j).key.ID;
                edgeWeightPairList.put(edge, String.valueOf(v.edges.getAt(j).val));
            }
        }
    }

    protected void initializeInputTableValues() throws IOException {
        inputTableModel.setRowCount(0);
        BufferedReader reader = new BufferedReader(new FileReader(textFile.getPath()));
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

    protected void setVisualizerPanelProperties(boolean dTablePresent) {
        visualizerPanel.removeAll();
        graphCanvas = new GraphVisualizerCanvas(graph, secondaryColor, secondaryForeground, vertexForeground, vertexColor, edgesColor);
        if (dTablePresent)
            visualizerPanel.setPreferredSize(new Dimension(1100,550));
        else
            visualizerPanel.setPreferredSize(new Dimension(1100,800));
        graphCanvas.setPreferredSize(visualizerPanel.getPreferredSize());
        visualizerPanel.add(graphCanvas);
        if (dTablePresent)
            visualizerPanel.add(dTableMainPanel);
        visualizerPanel.repaint();
        visualizerPanel.revalidate();
    }

    protected void changeMode(boolean mode) {
        playButton.setText(mode ? "Pause" : "Play");
        this.paused = !mode;
        if (!paused) disableControlPanel();
        else enableControlPanel();
    }

    protected void enableControlPanel() {
        inputFileButton.setEnabled(true);
        for (int i = 1; i < actionButtonsArray.length; i++) {
            actionButtonsArray[i].setEnabled(true);
        }
        algoSelectionBox.setEnabled(true);
        fromField.setEnabled(true);
        if (dijkstraChosen) toComboBox.setEnabled(true);
        else toComboBox.setEnabled(false);
    }

    protected void disableControlPanel() {
        inputFileButton.setEnabled(false);
        for (int i = 1; i < actionButtonsArray.length; i++) {
            actionButtonsArray[i].setEnabled(false);
        }
        fromField.setEnabled(false);
        toComboBox.setEnabled(false);
        algoSelectionBox.setEnabled(false);
    }

    protected class VisualizerThread extends Thread {

        @Override
        public void run() {
            int speed = 150;
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
                    Thread.sleep(speed);
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

    protected boolean validFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(textFile.getPath()));
        String line = "";
        line = reader.readLine();
        if (!line.equalsIgnoreCase("DIRECTED") && !line.equalsIgnoreCase("UNDIRECTED")) return false;
        line = reader.readLine();
        while (line != null) {
            String[] data = line.split(",");
            if (data.length != 3) {
                return false;
            }
            try {
                Integer.parseInt(data[0]);
            } catch (NumberFormatException nfe) {
                return false;
            }
            if (data[1].strip().isBlank() || data[2].strip().isBlank()) return false;
            line = reader.readLine();
        }
        return true;
    }

    protected void displayGroupMembers() {
        JOptionPane.showMessageDialog(mainFrame,
                "       Arevalo, Lance Gabrielle\n" +
                        "       Barana, Lance Matthew\n" +
                        "       Bayquen, Christian\n" +
                        "       Cayton, Arian Carl\n" +
                        "       De los Trinos, Jp",
                "Group 2", JOptionPane.PLAIN_MESSAGE);
    }

    protected void displayCourseSpecifications() {
        JOptionPane.showMessageDialog(mainFrame,
                "   Description:   Data Structures\n" +
                        "   Instructor:   Roderick Makil\n" +
                        "   Class Code:   9413\n" +
                        "   Class #:   CS 211\n",
                "Course Specifications", JOptionPane.PLAIN_MESSAGE);
    }

    protected void displayFileError() {
        JOptionPane.showMessageDialog(mainFrame, "- First line must be either Directed or Undirected\n " +
                "- File Format should follow (Weight(int), From, To)", "File Format Error", JOptionPane.ERROR_MESSAGE);
    }

    protected void setLightThemeProperties() {
        mainColor = new Color(0xD9D9D9);
        secondaryColor = new Color(0xFFFFFF);
        headerColor = new Color(0x222222);
        accentColor = new Color(0x222222);
        vertexColor = Color.BLACK;
        edgesColor = Color.BLACK;
        highlightColor = new Color(0xECECEC);

        mainForeground = Color.BLACK;
        secondaryForeground = Color.BLACK;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.WHITE;
        vertexForeground = Color.WHITE;

        currentTheme = "Light";
    }

    protected void setDarkThemeProperties() {
        mainColor = new Color(0x333333);
        secondaryColor = new Color(0x666666);
        headerColor = new Color(0x000000);
        accentColor = new Color(0x000000);
        vertexColor = Color.BLACK;
        edgesColor = Color.WHITE;
        highlightColor = new Color(0x999999);

        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.WHITE;
        vertexForeground = Color.WHITE;

        currentTheme = "Dark";
    }

    protected void setSLUThemeProperties() {
        mainColor = new Color(0xF4D35E);
        secondaryColor = new Color(0xFFFFFF);
        headerColor = new Color(0x0D3B66);
        accentColor = headerColor;
        vertexColor = headerColor;
        edgesColor = new Color(0x0D3B66);
        highlightColor = new Color(0xF9E9AE);

        mainForeground = Color.BLACK;
        secondaryForeground = Color.BLACK;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.WHITE;
        vertexForeground = Color.WHITE;

        currentTheme = "SLU";
    }

    protected void setBentoThemeProperties() {
        mainColor = new Color(0x2D394D);
        secondaryColor = new Color(0x4A768D);
        headerColor = new Color(0xF87A90);
        accentColor = headerColor;
        vertexColor = headerColor;
        edgesColor = Color.WHITE;
        highlightColor = new Color(0x969CA6);

        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.WHITE;
        vertexForeground = Color.WHITE;

        currentTheme = "Bento";
    }

    protected void setDraculaThemeProperties() {
        mainColor = new Color(0x282A36);
        secondaryColor = new Color(0x44475A);
        headerColor = new Color(0x6272A4);
        accentColor = new Color(0xBD93F9);
        vertexColor = accentColor;
        edgesColor = Color.WHITE;
        highlightColor = new Color(0x93949A);

        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.BLACK;
        vertexForeground = Color.BLACK;

        currentTheme = "Dracula";
    }

    protected void setGruvboxThemeProperties() {
        mainColor = new Color(0x282828);
        secondaryColor = new Color(0x1D2021);
        headerColor = new Color(0x689D6A);
        accentColor = new Color(0xDED1AD);
        vertexColor = accentColor;
        edgesColor = Color.WHITE;
        highlightColor = new Color(0x939393);

        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.BLACK;
        vertexForeground = Color.BLACK;

        currentTheme = "Gruvbox";
    }

    protected void setGodspeedThemeProperties() {
        mainColor = new Color(0x6A97B5);
        secondaryColor = new Color(0xEAE3D5);
        headerColor = new Color(0x5A5E61);
        accentColor = new Color(0xFAEE69);
        vertexColor = mainColor;
        edgesColor = Color.BLACK;
        highlightColor = new Color(0xB4CBDA);

        mainForeground = Color.WHITE;
        secondaryForeground = Color.BLACK;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.BLACK;
        vertexForeground = Color.WHITE;

        currentTheme = "Godspeed";
    }

    protected void setOliveThemeProperties() {
        mainColor = new Color(0xB6B09A);
        secondaryColor = new Color(0xD9D2C8);
        headerColor = new Color(0x2E2F33);
        accentColor = new Color(0x6F8C70);
        vertexColor = accentColor;
        edgesColor = Color.BLACK;
        highlightColor = new Color(0xDAD7CC);

        mainForeground = Color.BLACK;
        secondaryForeground = Color.BLACK;
        headerForeground = Color.WHITE;
        mainButtonForeground = Color.WHITE;
        vertexForeground = Color.WHITE;

        currentTheme = "Olive";
    }

    protected void setChristmasThemeProperties() {
        mainColor = new Color(0xCD1624);
        secondaryColor = new Color(0x23856D);
        headerColor = new Color(0xFAF8F8);
        accentColor = new Color(0xF8F272);
        vertexColor = mainColor;
        edgesColor = Color.WHITE;
        highlightColor = new Color(0xE68A91);

        mainForeground = Color.WHITE;
        secondaryForeground = Color.WHITE;
        headerForeground = Color.BLACK;
        mainButtonForeground = Color.BLACK;
        vertexForeground = Color.WHITE;

        currentTheme = "Christmas";
    }
}