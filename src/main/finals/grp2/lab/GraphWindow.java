package main.finals.grp2.lab;

import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.Stack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;

public class GraphWindow {

    private final JFrame frame = new JFrame();
    private JMenuBar menuBar;
    private JPanel mainPanel;
    private GridBagLayout gridBaglayout;
    private GridBagConstraints gbc;


    private JPanel menuPanel;
    private GraphVisualizerWindow visualizerPanel; //PUT VISUALIZER HERE
    private JPanel fileSelectionPanel;
    private JPanel graphDirectionPanel;
    private JPanel adjacencyRepresentationPanel;
    private JPanel algorithmPanel;
    private JPanel animationControllerPanel;

    //Options Labels
    private JLabel fileSelectionsLabel;
    private JLabel graphDirectionLabel;
    private JLabel adjacencyRepresentationLabel;
    private JLabel algorithmLabel;

    //File Selection Components
    private JComboBox fileSelectionCBox;
    private JButton browseButton;

    //Radio Buttons
    //Direction Button Group
    private ButtonGroup dirBGroup = new ButtonGroup();
    private JRadioButton undirectedRadioButton;
    private JRadioButton directedRadioButton;

//    private ButtonGroup adjacencyBGroup = new ButtonGroup();
//    private JRadioButton adjacencyListRadioButton;
//    private JRadioButton adjacencyMatrixRadioButton;

    private ButtonGroup algorithmBGroup = new ButtonGroup();
    private JRadioButton breadthFirstSearchRadioButton;
    private JRadioButton depthFirstSearchRadioButton;
    private JRadioButton dijkstraButton;

    //Animation Control Buttons
    private JButton runButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stepBackwardButton;
    private JButton stepForwardButton;
    private JButton skipBackwardButton;
    private JButton skipForwardButton;


    private JTable graphTable;
    private DefaultTableModel graphTabMod;
    private JScrollPane graphTableScrollPane;

    private String[] algorithms = {"BFS","DFS","Dijkstra's Algo"};
    private String currentAlgo = algorithms[0];

    private ArrayList<Graph> graphs;
    private Graph currentlyOpenedGraph;


    public GraphWindow(){
        frame.setTitle("Graph");
        gridBaglayout = new GridBagLayout();
        gbc = new GridBagConstraints();
        frame.setLayout(gridBaglayout);
        gbc.fill = GridBagConstraints.BOTH;
        initializeMainPanel();
        frame.add(mainPanel,gbc);
        frame.setMinimumSize(new Dimension(1392,769));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width / 2) - frame.getWidth() / 2, (d.height / 2) - frame.getHeight() / 2);
//
        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
    }


    private void initializeMainPanel(){
        mainPanel = new JPanel(gridBaglayout);
        initializeMenuPanel();
        initializeVisualizerPanel();

        gbc.gridx=0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        mainPanel.add(menuPanel,gbc);

        gbc.gridx =4;
        gbc.gridwidth = 3;
        mainPanel.add(visualizerPanel,gbc);
    }

    private void initializeVisualizerPanel(){
        visualizerPanel = new GraphVisualizerWindow(currentlyOpenedGraph);
    }


    private void initializeMenuPanel(){
        menuPanel = new JPanel(gridBaglayout);

        initializeFileSelectionPanel();
        initializeGraphDirectionMenuPanel();
        initializeAdjacencyRepresentationMenuPanel();
        initializeAlgorithmMenuPanel();
        initializeAnimationControllerPanel();

        gbc.gridx =0;
        gbc.gridy =0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5,5,5,5);
        menuPanel.add(fileSelectionPanel, gbc);
        gbc.gridy= 3;
        menuPanel.add(graphDirectionPanel,gbc);
        gbc.gridy=6;
        menuPanel.add(adjacencyRepresentationPanel,gbc);
        gbc.gridy=16;
        menuPanel.add(algorithmPanel,gbc);
        gbc.gridy=20;
        menuPanel.add(animationControllerPanel,gbc);
    }

    private void initializeFileSelectionPanel(){
        fileSelectionPanel = new JPanel(gridBaglayout);
//        parseCSVFilesToGraph();
        fileSelectionsLabel = new JLabel("File: ");
        fileSelectionCBox = new JComboBox(algorithms);
        browseButton = new JButton("Browse");

        gbc.gridx = gbc.gridy =0;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        fileSelectionPanel.add(fileSelectionsLabel,gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 5;
        fileSelectionPanel.add(fileSelectionCBox,gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 1;
        fileSelectionPanel.add(browseButton,gbc);
    }

    private void initializeGraphDirectionMenuPanel(){
        graphDirectionPanel = new JPanel(gridBaglayout);
        graphDirectionLabel = new JLabel("Graph Direction: ");
        undirectedRadioButton = new JRadioButton("Undirected");
        directedRadioButton = new JRadioButton("Directed");
        dirBGroup.add(undirectedRadioButton);
        dirBGroup.add(directedRadioButton);

        gbc.gridx = gbc.gridy =0;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        graphDirectionPanel.add(graphDirectionLabel,gbc);

        gbc.gridx = gbc.gridy=1;
        gbc.gridwidth = 3;
        graphDirectionPanel.add(undirectedRadioButton,gbc);

        gbc.gridy = 2;
        graphDirectionPanel.add(directedRadioButton,gbc);
    }

    private void initializeAdjacencyRepresentationMenuPanel(){
        adjacencyRepresentationPanel = new JPanel(gridBaglayout);
        adjacencyRepresentationLabel = new JLabel("Adjacency Representation: ");

        graphTabMod = new DefaultTableModel();
        graphTable = new JTable(graphTabMod);
        graphTableScrollPane = new JScrollPane(graphTable);
        currentlyOpenedGraph = new Graph(new File("src/main/finals/grp2/lab/data/in.csv"));
        initializeTable(graphTable, graphTabMod, new String[]{"Weight","Start Vertex","End Vertex"}, graphTableScrollPane);

        gbc.gridx = gbc.gridy =0;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        adjacencyRepresentationPanel.add(adjacencyRepresentationLabel, gbc);

        gbc.gridy =1;
        gbc.gridheight = 4;
        adjacencyRepresentationPanel.add(graphTableScrollPane, gbc);
    }


    private void initializeTable(JTable table, DefaultTableModel defaultTableModel, String[] columnNames,
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
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 12));
        scrollPane.setVisible(true);
    }

    private void populateTable(){

    }

    private void initializeAlgorithmMenuPanel(){
        algorithmPanel = new JPanel(gridBaglayout);
        algorithmLabel = new JLabel("Algorithm: ");
        breadthFirstSearchRadioButton = new JRadioButton("Breadth-First Search");
        depthFirstSearchRadioButton = new JRadioButton("Depth-First Search");
        dijkstraButton = new JRadioButton("Dijkstra's Shortest Path");

        algorithmBGroup.add(breadthFirstSearchRadioButton);
        algorithmBGroup.add(depthFirstSearchRadioButton);
        algorithmBGroup.add(dijkstraButton);

        gbc.gridx = gbc.gridy =0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        algorithmPanel.add(algorithmLabel,gbc);

        gbc.gridx = gbc.gridy =1;
        algorithmPanel.add(breadthFirstSearchRadioButton,gbc);
        gbc.gridy=2;
        algorithmPanel.add(depthFirstSearchRadioButton, gbc);
        gbc.gridy =3;
        algorithmPanel.add(dijkstraButton, gbc);
    }

    private void initializeAnimationControllerPanel(){
        animationControllerPanel = new JPanel(gridBaglayout);
        runButton = new JButton("RUN");
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        skipForwardButton = new JButton("Skip Forward");
        skipBackwardButton = new JButton("Skip Backward");
        stepForwardButton = new JButton(">");
        stepBackwardButton = new JButton("<");

        gbc.gridx=2;
        gbc.gridy =0;
        gbc.gridheight =2;
        gbc.gridwidth =2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,5,5,5);
        animationControllerPanel.add(runButton,gbc);

        gbc.gridx = 0;
        animationControllerPanel.add(pauseButton, gbc);

        gbc.gridx = 5;
        animationControllerPanel.add(playButton,gbc);

        gbc.gridy = 2;
        gbc.gridx =0;
        animationControllerPanel.add(skipBackwardButton,gbc);

        gbc.gridx = 5;
        animationControllerPanel.add(skipForwardButton,gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        animationControllerPanel.add(stepBackwardButton, gbc);

        gbc.gridx=3;
        animationControllerPanel.add(stepForwardButton,gbc);
    }

//    private void parseCSVFilesToGraph(){
//        graphs = new ArrayList<>();
//        File path = new File("main\\finals\\grp2\\lab\\data");
//        File[] filesToRead = path.listFiles();
//        for (int i=0;i<filesToRead.length;i++){
//            if (filesToRead[i].isFile()){
//            graphs.insert(new Graph(filesToRead[i]));
//            }
//
//        }
//    }

    public static void main(String[] args){
        try {
            new GraphWindow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
