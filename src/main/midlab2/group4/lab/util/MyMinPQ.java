package main.midlab2.group4.lab.util;

public class MyMinPQ<T extends Comparable<T>> {

    Node<T> root;
    Node<T> last;
    int size;

    class Node<T>{
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        Node<T> prevLast;
        T data;
        Node(T data){
            this.data = data;
        }
    }


    void enqueue(T data){
        if (root==null){
            root = new Node<>(data);
            last=root;
        }
        else if (last.left == null){
            last.left = new Node<>(data);
            last.left.parent = last;
            moveUp(last.left);
        }

        else if (last.right == null){
            last.right = new Node<>(data);
            last.right.parent = last;
            moveUp(last.right);
            Node<T> prevLast = last;
            setLast(last);
            last.prevLast = prevLast;
        }
        size++;
    }

    public T dequeue(){
        if (isEmpty()) return null;
        if (size ==1)  {
            T temp = root.data;
            last = null;
            root = null;
            size--;
            return temp;
        }
        T temp = root.data;
        if (size==2){
            swapNodeData(root, root.left);
            root.left = null;
            size--;
            return temp;
        }

        if (isLeaf(last)){
            last = last.prevLast;
            swapNodeData(root,last.right);
            last.right =null;
        }
        else{
            if (last.right!=null){
                swapNodeData(root, last.right);
                last.right = null;
            }else{
                swapNodeData(root, last.left);
                last.left = null;
            }
        }
        size--;
        moveDown(root);
        return temp;
    }



    public boolean isEmpty(){
        return root==null;
    }

    void swapNodeData(Node<T> a, Node<T> b){
        T temp = a.data;
        a.data = b.data;
        b.data = temp;
    }


    void moveUp(Node<T> node){
        if(node.parent != null){
            if(node.data.compareTo(node.parent.data)<0){
                swapNodeData(node.parent, node);
                moveUp(node.parent);
            }
        }
    }

    void moveDown(Node<T> node){

        //  If both node and the left node are null then there can't be right node
        if (node==null||node.left==null){
            return;
        }

        Node<T> min = node.left;
        if (node.right != null && min.data.compareTo(node.right.data)>0){
            min = node.right;

        }if (min.data.compareTo(node.data)<0){
            swapNodeData(node, min);
            moveDown(min);
        }
    }

    void setLast(Node<T> node){
        //Go to the extreme left of the tree once a level is completed
        if (node.parent ==null){
            last = node;
            while (last.left!= null){
                last = last.left;
            }
        }
        /*
        Go to the right node if the current node is left LEFT NODE then from there proceed
        to the left most node
         */
        else if (node.parent.left==node){
            last = node.parent.right;
            while (last.left!=null){
                last = last.left;
            }
        }
        else if (node.parent.right==node) {
            setLast(node.parent);
        }
    }

    boolean isLeaf(Node<T> node){
        return node.left  ==null && node.right ==null;
    }

    public static void main(String[] args) {
        MyMinPQ myMinPQ;
        try {
            myMinPQ = new MyMinPQ();
            myMinPQ.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        micro1Test();
        micro2Test();
        micro3Test();
        small1Test();
        small2Test();
        small3Test();

        bigTest();
    }


    public void micro1Test(){
        System.out.println("+======================================================+");
        System.out.println("\t\t Micro Test 1");
        MyMinPQ<Integer> pq = new MyMinPQ<>();
        pq.enqueue(4);
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();

        System.out.println("Dequeue: "+pq.dequeue());
        System.out.println("Size: "+ pq.size);
        System.out.println();

    }
    public void micro2Test(){
        System.out.println("+======================================================+");
        System.out.println("\t\t Micro Test 2");
        MyMinPQ<Integer> pq = new MyMinPQ<>();
        pq.enqueue(4);
        pq.enqueue(2);


        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s", " ", pq.root.left.data);//2nd layer
        System.out.println();

        System.out.println("Dequeue: "+pq.dequeue());
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();

    }
    public void micro3Test(){
        System.out.println("+======================================================+");
        System.out.println("\t\t Micro Test 3");
        MyMinPQ<Integer> pq = new MyMinPQ<>();
        pq.enqueue(4);
        pq.enqueue(2);
        pq.enqueue(3);
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();

        System.out.printf("%-40s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);
        System.out.println();

        System.out.println("Dequeue: "+pq.dequeue());
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s", " ", pq.root.left.data);//2nd layer
        System.out.println();
        System.out.println(pq.last.data);
    }
    public void small1Test(){
        System.out.println("+======================================================+");
        System.out.println("\t\t Small Test 1");
        MyMinPQ<Integer> pq = new MyMinPQ<>();
        pq.enqueue(4);
        pq.enqueue(2);
        pq.enqueue(12);
        pq.enqueue(5);

        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);//2nd layer
        System.out.println();
        System.out.printf("%-30s %-25s ", " ", pq.root.left.left.data);//3rd layer
        System.out.println();


        System.out.println("Dequeue: "+pq.dequeue());
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s   %-60s", " ", pq.root.left.data, pq.root.right.data );//2nd layer
        System.out.println();
//        System.out.printf("%-30s %-25s %-35s %-25s", " ", pq.root.left.left.data, pq.root.left.right.data, pq.root.right.left.data+"\n");//3rd layer
        System.out.println();
        System.out.println();
    }

    public void small2Test(){
        System.out.println("+======================================================+");
        System.out.println("\t\t Small Test 2");
        MyMinPQ<Integer> pq = new MyMinPQ<>();
        pq.enqueue(4);
        pq.enqueue(2);
        pq.enqueue(12);
        pq.enqueue(5);
        pq.enqueue(3);
        pq.enqueue(6);

        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);//2nd layer
        System.out.println();
        System.out.printf("%-30s %-25s %-25s %-25s", " ", pq.root.left.left.data, pq.root.left.right.data, pq.root.right.left.data);//3rd layer
        System.out.println();


        System.out.println("Dequeue: "+pq.dequeue());
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);//2nd layer
        System.out.println();
        System.out.printf("%-30s %-25s %-35s ", " ", pq.root.left.left.data, pq.root.left.right.data);//3rd layer
        System.out.println();
    }

    public void small3Test(){
        System.out.println("+======================================================+");
        System.out.println("\t\t Small Test 3");
        MyMinPQ<Integer> pq = new MyMinPQ<>();
        pq.enqueue(4);
        pq.enqueue(2);
        pq.enqueue(12);
        pq.enqueue(5);
        pq.enqueue(3);
        pq.enqueue(13);
        pq.enqueue(10);

        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);//2nd layer
        System.out.println();
        System.out.printf("%-30s %-25s %-35s %-25s %-25s", " ", pq.root.left.left.data, pq.root.left.right.data, pq.root.right.left.data, pq.root.right.right.data+"\n");//3rd layer
        System.out.println();
        System.out.println();

        System.out.println("Dequeue: "+pq.dequeue());
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);//2nd layer
        System.out.println();
        System.out.printf("%-30s %-25s %-35s %-25s ", " ", pq.root.left.left.data, pq.root.left.right.data, pq.root.right.left.data+"\n");//3rd layer
        System.out.println();
    }

    public void bigTest(){
        System.out.println("+======================================================+");
        System.out.println("\t\t Big Test 1");
        MyMinPQ<Integer> pq = new MyMinPQ<>();
        pq.enqueue(4);
        pq.enqueue(2);
        pq.enqueue(12);
        pq.enqueue(5);
        pq.enqueue(3);
        pq.enqueue(13);
        pq.enqueue(14);
        pq.enqueue(6);
        pq.enqueue(7);
        pq.enqueue(9);
        pq.enqueue(1);
        pq.enqueue(35);
        pq.enqueue(23);
        pq.enqueue(21);
//        pq.enqueue(51);


        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);//2nd layer
        System.out.println();
        System.out.printf("%-30s %-25s %-35s %-25s %-25s", " ", pq.root.left.left.data, pq.root.left.right.data, pq.root.right.left.data, pq.root.right.right.data+"\n");//3rd layer
        System.out.println();
        System.out.printf("%-25s %-10s %-15s %-10s %-25s %-10s %-15s %-10s ", " ", pq.root.left.left.left.data, pq.root.left.left.right.data, pq.root.left.right.left.data, pq.root.left.right.right.data,
                pq.root.right.left.left.data, pq.root.right.left.right.data, pq.root.right.right.left.data);// pq.root.right.right.right.data

        System.out.println();
        System.out.println("Dequeue: "+pq.dequeue());
        System.out.println("Last Parent: "+pq.last.data);
        System.out.println("Size: "+ pq.size);
        System.out.printf("%-75s %-30s"," ", pq.root.data);
        System.out.println();
        System.out.printf("%-45s %-60s %-60s", " ", pq.root.left.data, pq.root.right.data);//2nd layer
        System.out.println();
        System.out.printf("%-30s %-25s %-35s %-25s %-25s", " ", pq.root.left.left.data, pq.root.left.right.data, pq.root.right.left.data, pq.root.right.right.data+"\n");//3rd layer
        System.out.println();
        System.out.printf("%-25s %-10s %-15s %-10s %-25s %-10s %-15s  ", " ", pq.root.left.left.left.data, pq.root.left.left.right.data, pq.root.left.right.left.data, pq.root.left.right.right.data,
                pq.root.right.left.left.data, pq.root.right.left.right.data);//, pq.root.right.right.left.data, pq.root.right.right.right.data %-10s %-15s
//

    }




}
