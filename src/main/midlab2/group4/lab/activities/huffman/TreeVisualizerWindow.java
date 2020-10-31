package main.midlab2.group4.lab.activities.huffman;

import main.midlab2.group4.lab.util.ArrayList;
import main.midlab2.group4.lab.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class TreeVisualizerWindow {

    private JFrame frame;
    private List<GUITreeNode> nodes1D;
    private List<List<GUITreeNode>> nodes;
    private TreeCanvas treeCanvas;
    private List<Dictionary<Integer, Integer >> nodeCoords;
    private MyDictionary<Point, Point> lineCoordinates;
    private final int verticalGap = 50;
    private final int horizontalGap = 200;
    private final int xRad = 40;
    private final int yRad = 40;
    private final int initialXGap = (int)(xRad*.60);
    private final int initialYGap = (int)(yRad+yRad*.75);

    TreeVisualizerWindow(TreeNode root) {
        frame = new JFrame("Binary Tree Visualizer");

        completeBinaryTree(root);
        this.nodes1D = convertTreeToArr(root);
        this.nodes = parseLevels(nodes1D);

        int width = (int)(nodes.getElement(nodes.getSize()-1).getSize()*1.25)*xRad+(horizontalGap);
        int height = verticalGap *2 + nodes.getSize()*(initialYGap);
        frame.setMinimumSize(new Dimension(width,height));
        this.nodeCoords = generateNodeCoordinates(nodes);
        this.lineCoordinates = generateLineCoordinates(nodeCoords, nodes);
        System.out.println(lineCoordinates);

        treeCanvas = new TreeCanvas();
        treeCanvas.setBackground(Color.LIGHT_GRAY);

        frame.add(treeCanvas);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }



    private class TreeCanvas extends Canvas {

        @Override
        public void paint(Graphics g) {

            paintLines(g);
            paintCircles(g);

        }

        private void paintCircles(Graphics g) {

            g.translate(-xRad/2,-yRad/2);

            for (int i = 0; i < nodes.getSize(); i++) {
                Dictionary<Integer, Integer> coords = nodeCoords.getElement(i);
                for (int j = 0; j < nodes.getElement(i).getSize(); j++) {
                    if (nodes.getElement(i).getElement(j).getWeight() != -1) {
                        GUITreeNode currentNode = nodes.getElement(i).getElement(j);
                        Dictionary.Node<Integer, Integer> coordinate = coords.getAt(j);
                        g.setColor(Color.DARK_GRAY);
                        g.fillOval(coordinate.key, coordinate.val, xRad, yRad);
                        g.setColor(Color.WHITE);
                        if (currentNode.getCharacter().equals("\0")) {
                            g.drawString(nodes.getElement(i).getElement(j).toString(),
                                    coordinate.key+(xRad/2)-4, coordinate.val+(yRad/2)+3);
                        }else {
                            g.drawString(nodes.getElement(i).getElement(j).toString(),
                                    coordinate.key+(xRad/2)-11, coordinate.val+(yRad/2)+3);
                        }
                        System.out.println("Drawing " + i + "|" + j + " at: " + coordinate.key + ", " + coordinate.val);
                    }
                }
            }

        }

        private void paintLines(Graphics g) {

            g.translate(xRad/2, yRad/2);

            for (int i = 0; i < lineCoordinates.size(); i++) {
                MyDictionary.Node<Point, Point> point = lineCoordinates.getAt(i);
                g.setColor(Color.DARK_GRAY);
                g.drawLine(point.x.x, point.x.y, point.y.x, point.y.y);
            }

        }

    }

    // ------------------ MAGIC -----------------

    private List<Dictionary<Integer, Integer>> generateNodeCoordinates(List<List<GUITreeNode>> nodes) {
        List<Dictionary<Integer, Integer>> toReturn = new ArrayList<>();

        int xGap = computeInitialXGap(initialXGap);
        int yGap = initialYGap;

        Dictionary<Integer, Integer> coords = new Dictionary<>();
//        coords.put((frame.getWidth()/2)-xRad/2, verticalGap); // artificial translate
        coords.put((frame.getWidth()/2), verticalGap);
//        System.out.println("Frame.getWidth/2: " + frameey);
        toReturn.insert(coords);

        for (int i = 1; i < nodes.getSize(); i++) {
            coords = new Dictionary<>();
            Dictionary.Node<Integer, Integer> firstNode = toReturn.getElement(i-1).getAt(0);
            coords.put(firstNode.key-(xGap/4), firstNode.val+yGap);
            for (int j = 1; j < nodes.getElement(i).getSize(); j++) {
                Dictionary.Node<Integer, Integer> precedingNode = coords.getAt(j-1);
                coords.put(precedingNode.key+xGap/2, precedingNode.val);
            }
            toReturn.insert(coords);
            xGap = xGap/2;
        }

        return toReturn;
    }

    private MyDictionary<Point, Point> generateLineCoordinates(List<Dictionary<Integer, Integer>> nodeCoords,
                                                               List<List<GUITreeNode>> nodes) {
        MyDictionary<Point, Point> lineCoordinates = new MyDictionary<>();
        Dictionary.Node<Integer, Integer> parentNode = nodeCoords.getElement(0).getAt(0);

        for (int i = 1; i < nodeCoords.getSize(); i++) {
            int ctr = 1;
            int idxOfParent = 0;
            for (int j = 0; j < nodeCoords.getElement(i).size(); j++) {
                Point parentPoint = new Point(parentNode.key, parentNode.val);
                Point childPoint = new Point(nodeCoords.getElement(i).getAt(j).key, nodeCoords.getElement(i).getAt(j).val);
                if (nodes.getElement(i-1).getElement(idxOfParent).getWeight() != -1 // only generate coords of lines connecting to non filler nodes
                        && nodes.getElement(i).getElement(j).getWeight() != -1)
                    lineCoordinates.put(parentPoint, childPoint);
                if (j != nodeCoords.getElement(i).size() && ctr % 2 == 0) {
                    idxOfParent++;
                    parentNode = nodeCoords.getElement(i-1).getAt(idxOfParent);
                }
                ctr++;
            }
            parentNode = nodeCoords.getElement(i).getAt(0);
        }

        return lineCoordinates;
    }

    private int computeInitialXGap(int initialGap) {
//        int toReturn = (int)(xRad*0.50 ) + initialGap;
        int toReturn = initialGap;
        for (int i = 0; i < nodes.getSize(); i++) {
            toReturn += toReturn;
        }
        return toReturn;
    }

    /**
     * Passed TreeNode must be a complete binary tree
     */
    private List<List<GUITreeNode>> parseLevels(List<GUITreeNode> nodes1dArr) {
        List<List<GUITreeNode>> nodes2dArr = new ArrayList<>();
        List<Integer> nodesPerLevel = computeNumOfNodesPerLevel(nodes1dArr);

        for (int i = 0, toInsert = 0; i < nodesPerLevel.getSize(); i++) {
            List<GUITreeNode> level = new ArrayList<>();
            for (int j =  0; j < nodesPerLevel.getElement(i); j++, toInsert++) {
                level.insert(nodes1dArr.getElement(toInsert));
            }
            nodes2dArr.insert(level);
        }

        return nodes2dArr;
    }

    private List<Integer> computeNumOfNodesPerLevel(List<GUITreeNode> nodes) {
        List<Integer> qtyPerLevel = new ArrayList<>();
        for (int i = 0, j = 1; i < nodes.getSize(); i++) {
            if (i == Math.pow(2,j-1)) {
                qtyPerLevel.insert(i);
                j++;
            }
        }
        return qtyPerLevel;
    }

    private List<GUITreeNode> convertTreeToArr(TreeNode root) {
        List<GUITreeNode> list = new ArrayList<>();

        java.util.Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        // tutorialspoint: bfs
        while (!q.isEmpty()) {
            TreeNode tempNode = q.poll();
            list.insert(new GUITreeNode(String.valueOf(tempNode.letter), tempNode.weight));
            if (tempNode.left != null)
                q.add(tempNode.left);
            if (tempNode.right != null)
                q.add(tempNode.right);
        }
        return list;
    }

    private TreeNode completeBinaryTree(TreeNode root) {
        return makeComplete(root);
    }

    private TreeNode makeComplete(TreeNode node) {
        while (!isPerfect(node)) {
            insert(node, new TreeNode('\0',-1,null,null));
        }
        return node;
    }

    static void insert(TreeNode temp, TreeNode newNode)
    {
        java.util.Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(temp);
        // Do level order traversal until we find
        // an empty place.
        while (!q.isEmpty()) {
            temp = q.peek();
            q.remove();
            if (temp.left == null) {
                temp.left = newNode;
                break;
            }
            else
                q.add(temp.left);
            if (temp.right == null) {
                temp.right = newNode;
                break;
            }
            else
                q.add(temp.right);
        }
    }

    static boolean isPerfectRec(TreeNode root, int d, int level) {
        // An empty tree is perfect
        if (root == null)
            return true;

        // If leaf node, then its depth must be same as
        // depth of all other leaves.
        if (root.left == null && root.right == null)
            return (d == level+1);

        // If internal node and one child is empty
        if (root.left == null || root.right == null)
            return false;

        // Left and right subtrees must be perfect.
        return isPerfectRec(root.left, d, level+1) && isPerfectRec(root.right, d, level+1);
    }

    // Wrapper over isPerfectRec()
    static boolean isPerfect(TreeNode root) {
        int d = findADepth(root);
        return isPerfectRec(root, d, 0);
    }

    static int findADepth(TreeNode node) {
        int d = 0;
        while (node != null) {
            d++;
            node = node.left;
        }
        return d;
    }

    private int getHeight(TreeNode root) {
        if (root == null)
            return 0;
        else {
            /* compute the depth of each subtree */
            int lDepth = getHeight(root.left);
            int rDepth = getHeight(root.right);
            /* use the larger one */
            if (lDepth > rDepth)
                return (lDepth + 1);
            else
                return (rDepth + 1);
        }
    }

    //Do not execute: for debugging purposes only
    public static void main(String[] args) {

        TreeNode tree = new TreeNode('\0', 0, null, null);
        tree.left = new TreeNode('o', 1, null, null);
        tree.right = new TreeNode('o', 2, null, null);
        tree.left.left = new TreeNode('o', 3, null, null);
        tree.left.right = new TreeNode('o', 4, null, null);
        tree.left.left.left = new TreeNode('o', 5, null, null);
        tree.left.left.left.left = new TreeNode('o', 6, null, null);
        tree.left.left.left.left.left = new TreeNode('o', 7, null, null);
//        tree.left.left.left.left.left.left = new TreeNode('o', 8, null, null);

        try{
            TreeVisualizerWindow w = new TreeVisualizerWindow(tree);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
